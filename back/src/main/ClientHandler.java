package main;

import api.Server;
import backobjects.groups.IGroup;
import backobjects.thread.IMessage;
import backobjects.thread.IThread;
import backobjects.users.IUser;
import com.google.gson.Gson;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson = new Gson();
    private int clientId = -1;
    private boolean clientIsAdmin = false;

    public ClientHandler(Socket clientSocket) throws IOException {
        client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        String request = "";
        try {
            while (request != null) {
                request = in.readLine();
                System.out.println("[SERVER] Received request from client (" + clientId + ") => " + request);

                if(request != null) {
                    ClientRequest serverPayload = gson.fromJson(request, ClientRequest.class);
                    ServerResponse response = treatRequest(serverPayload);

                    // Close connection if user is not connected and requests anything else than a connection
                    if(response != null) {
                        System.out.println("[SERVER] Sending response to client (" + clientId + ") => " + response.payload);
                        out.println(gson.toJson(response));
                        doUpdateIfNeeded(serverPayload);
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            Utils.closeAll(this.client, this.in, this.out, this.clientId);

            if(clientId != -1){
                System.out.println("[SERVER] Disconnecting client " + clientId);
                Server.disconnect(this);
                updateAdminsExceptSelf(treatRequest(new ClientRequest("/user/getAllConnectedUsers", new HashMap<>())));
            }
            mainBack.clients.remove(this);
        }
    }

    private void updateAdminsExceptSelf(ServerResponse serverResponse) {
        ArrayList<ClientHandler> clients = new ArrayList<>(mainBack.clients);
        for(ClientHandler client : clients) {
            boolean isAdmin = client.getClientIsAdmin();

            if(isAdmin && client.getClientId() != this.clientId) {
                updateSingleClient(serverResponse, client);
            }
        }
    }

    private void updateAdminsOnly(String request) {
        ArrayList<ClientHandler> clients = new ArrayList<>(mainBack.clients);
        for(ClientHandler client : clients) {
            boolean isAdmin = client.getClientIsAdmin();

            if(isAdmin) {
                updateSingleClient(treatRequest(new ClientRequest(request, new HashMap<>())), client);
            }
        }
    }

    private void updateAllClients(String request) {
        ArrayList<ClientHandler> clients = new ArrayList<>(mainBack.clients);
        for(ClientHandler client : clients) {
            updateSingleClient(treatRequest(new ClientRequest(request, new HashMap<>())), client);
        }
    }

    private void updateClientsOnly(String request) {
        ArrayList<ClientHandler> clients = new ArrayList<>(mainBack.clients);
        for(ClientHandler client : clients) {
            boolean isAdmin = client.getClientIsAdmin();

            if(!isAdmin) {
                Map<String, String> payload = new HashMap<>();
                payload.put("id", ""+client.getClientId());
                updateSingleClient(treatRequest(new ClientRequest(request, payload)), client);
            }
        }
    }

    private void treatSingleRequest(ServerResponse serverResponse, ClientHandler client) {
        ServerResponse update = new ServerResponse(serverResponse.address, serverResponse.payload, "update");
        System.out.println("[SERVER] Sending update to client (" + client.getClientId() + ") => " + update.payload);
        client.getPrinterOut().println(gson.toJson(update));
    }

    private void updateSingleClient(ServerResponse serverResponse, ClientHandler client) {
        treatSingleRequest(serverResponse, client);
    }

    private ServerResponse treatRequest(ClientRequest request) {
        String address = request.address;
        Map<String,String> payload = request.payload;

        // Don't answer if client is not connected and not requesting a connexion
        if(clientId == -1 && !address.equals("/connect")) {
            return null;
        }

        String toClient = switch (address) {
            // CONNECTIVITY
            // { "username": String, "password": String }
            // Updates
            case "/connect" -> Server.connect(this, payload);

            // USER
            // { "id": int }
            case "/user/getUserById" -> IUser.getUserById(payload);

            // { "username": String, "name": String, "surname": String, "password": String, "isAdmin": boolean }
            // Updates
            case "/user/createUser" -> IUser.createUser(payload);

            // { "id": int }
            // Updates
            case "/user/deleteUser", "/user/deleteConnectedUser" -> IUser.deleteUser(payload);

            // {}
            case "/user/getAllConnectedUsers" -> IUser.getAllConnectedUsers();

            // {}
            case "/user/getAllDatabaseUsers" -> IUser.getAllDatabaseUsers();

            // THREAD
            // { "id" : int }
            case "/thread/getAllThreadsForUser" -> IThread.getAllThreadForUser(payload);

            // { "authorId": id, "groupId": int, "title": String, "content": String }
            // Updates
            case "/thread/createThread" -> IThread.createThread(payload);

            // { "id": int }
            // Updates
            case "/thread/deleteThread" -> IThread.deleteThread(payload);

            // { "clientId": int, "threadId": int }
            // Updates
            case "/thread/updateMessagesOfThread" -> IThread.updateMessagesStatus(payload);

            // { "clientId": int }
            // Updates the messages when the client just connected
            case "/thread/clientGetThreadsAtConnection" -> IThread.clientGetThreadsAtConnection(payload);


            // MESSAGE
            // { "authorId": int, "content": String, "threadId": int }
            // Updates
            case "/message/createMessage" -> IMessage.createMessage(payload);

            // { "id": int }
            // Updates
            case "/message/deleteMessage" -> IMessage.deleteMessage(payload);


            // GROUP
            // { "name": String }
            // Updates
            case "/group/createGroup" -> IGroup.createGroup(payload);

            // { "id": int }
            // Updates
            case "/group/deleteGroup" -> IGroup.deleteGroup(payload);

            // { "groupId": int, "userId": int }
            // Updates
            case "/group/addUserToGroup" -> IGroup.addUserToGroup(payload);

            // { "groupId": int, "userId": int }
            // Updates
            case "/group/removeUserFromGroup" -> IGroup.removeUserFromGroup(payload);

            // {}
            case "/group/getAllDatabaseGroups" -> IGroup.getAllDatabaseGroups();

            // { "id": int }
            case "/group/getGroupsOfUserById" -> IGroup.getGroupsOfUserById(payload);

            default -> "\"null\"";
        };

        return new ServerResponse(address, toClient, "response");
    }

    private void doUpdateIfNeeded(ClientRequest request) throws InterruptedException {
        String address = request.address;

        switch (address) {
            // CONNECTIVITY
            // Sends the new Database Connected User List to admins
            case "/connect" -> updateAdminsOnly("/user/getAllConnectedUsers");

            // USER
            // Sends the new Database User List to admins
            case "/user/createUser" -> updateAdminsOnly("/user/getAllDatabaseUsers");
            // TODO only for affected users
            case "/user/deleteUser"-> {
                // Sends the new Database User List to admins
                updateAdminsOnly("/user/getAllDatabaseUsers");
                // Sends recalculated threads to clients
                updateClientsOnly("/thread/getAllThreadsForUser");
            }

            case "/user/deleteConnectedUser" -> {
                // Disconnect the user first
                int i = 0;
                while(mainBack.clients.get(i).getClientId() != Integer.parseInt(request.payload.get("id"))) { i++; }
                System.out.println(mainBack.clients.get(i).clientId);
                mainBack.clients.get(i).closePrinterOut();
                sleep(1000);
                // Sends the new Database User List to admins
                updateAdminsOnly("/user/getAllDatabaseUsers");
                // Sends recalculated threads to clients
                updateClientsOnly("/thread/getAllThreadsForUser");
            }

                // THREAD & MESSAGE & addUserToGroup
            // TODO only for affected users
            // Sends recalculated threads to clients
            case "/thread/createThread", "/thread/deleteThread", "/thread/updateMessagesOfThread", "/message/createMessage", "/message/deleteMessage", "/thread/clientGetThreadsAtConnection", "/group/removeUserFromGroup"
                    -> updateClientsOnly("/thread/getAllThreadsForUser");

            // GROUP
            // Sends the new Database Group List to admins
            case "/group/createGroup" ->
                    updateAllClients("/group/getAllDatabaseGroups");

            // TODO only for affected users
            case "/group/deleteGroup" -> {
                // Sends the new Database Group List to admins
                updateAllClients("/group/getAllDatabaseGroups");
                // Sends recalculated threads to clients
                updateClientsOnly("/thread/getAllThreadsForUser");
            }
            case "/group/addUserToGroup" -> {
                // Sends new threads to the selected user IF CONNECTED
                String clientIdString = request.payload.get("userId");
                ClientHandler client = clientIsConnected(Integer.parseInt(clientIdString));
                if(client != null) {
                    Map<String, String> payload = new HashMap<>();
                    payload.put("id", clientIdString);
                    updateSingleClient(treatRequest(new ClientRequest("/thread/getAllThreadsForUser", payload)), client);
                }
            }
        }
    }

    private ClientHandler clientIsConnected(int clientId) {
        if(mainBack.clients.stream().anyMatch((client) -> client.getClientId() == clientId)) {
            return mainBack.clients.stream().filter(a -> a.getClientId() == clientId).toList().get(0);
        }

        return null;
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

    public int getClientId() {
        return this.clientId;
    }

    public PrintWriter getPrinterOut() {
        return this.out;
    }

    public void closePrinterOut() {
        this.out.close();
    }

    public void setClientIsAdmin(boolean bool) {
        this.clientIsAdmin = bool;
    }

    public boolean getClientIsAdmin() {
        return this.clientIsAdmin;
    }
}
