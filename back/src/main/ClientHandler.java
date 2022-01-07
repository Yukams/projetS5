package main;

import api.Server;
import backobjects.groups.IGroup;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } catch (IOException e) {
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
        for(ClientHandler client : mainBack.clients) {
            boolean isAdmin = client.getClientIsAdmin();

            if(isAdmin && client.getClientId() != this.clientId) {
                updateSingleClient(serverResponse, client);
            }
        }
    }

    private void updateAdminsOnly(ServerResponse serverResponse) {
        for(ClientHandler client : mainBack.clients) {
            boolean isAdmin = client.getClientIsAdmin();

            if(isAdmin) {
                updateSingleClient(serverResponse, client);
            }
        }
    }

    private void updateClientsOnly(ServerResponse serverResponse) {
        for(ClientHandler client : mainBack.clients) {
            boolean isAdmin = client.getClientIsAdmin();

            if(!isAdmin) {
                updateSingleClient(serverResponse, client);
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
            case "/user/deleteUser" -> IUser.deleteUser(payload);

            // {}
            case "/user/getAllConnectedUsers" -> IUser.getAllConnectedUsers();

            // {}
            case "/user/getAllDatabaseUsers" -> IUser.getAllDatabaseUsers();

            // THREAD
            // {}
            case "/thread/getAllThreadsForUser" -> IThread.getAllThreadForUser(this.clientId);

            // { "id" : int }
            case "/thread/getAllThreadsForUserById" -> IThread.getAllThreadForUserById(payload);

            // { "groupId": int, "title": String, "content": String }
            // Updates
            case "/thread/createThread" -> IThread.createThread(this.clientId, payload);

            // { "id": int }
            // Updates
            case "/thread/deleteThread" -> IThread.deleteThread(payload);

            // { "threadId": int }
            // Updates
            case "/thread/updateMessagesOfThread" -> IThread.updateMessages(this.clientId, payload);


            // MESSAGE
            // { "content": String, "threadId": int }
            // Updates
            case "/message/createMessage" -> IMessage.createMessage(this.clientId, payload);

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

            // {}
            case "/group/getAllDatabaseGroups" -> IGroup.getAllDatabaseGroups();

            // { "userId": int }
            case "/group/getGroupsOfUserById" -> IGroup.getGroupsOfUserById(payload);

            // {}
            case "/group/getGroupsOfUser" -> IGroup.getGroupsOfUser(this.clientId);

            default -> "\"null\"";
        };

        return new ServerResponse(address, toClient, "response");
    }

    private void doUpdateIfNeeded(ClientRequest request) {
        String address = request.address;

        switch (address) {
            // CONNECTIVITY
            // Sends the new Database Connected User List to admins
            case "/connect" -> updateAdminsOnly(treatRequest(new ClientRequest("/user/getAllConnectedUsers", new HashMap<>())));

            // USER
            // Sends the new Database User List to admins
            case "/user/createUser" -> updateAdminsOnly(treatRequest(new ClientRequest("/user/getAllDatabaseUsers", new HashMap<>())));
            // TODO only for affected users
            case "/user/deleteUser" -> {
                // Sends the new Database User List to admins
                updateAdminsOnly(treatRequest(new ClientRequest("/user/getAllDatabaseUsers", new HashMap<>())));
                // Sends recalculated threads to clients
                updateClientsOnly(treatRequest(new ClientRequest("/thread/getAllThreadsForUser", new HashMap<>())));
            }

            // THREAD & MESSAGE & addUserToGroup
            // TODO only for affected users
            // Sends recalculated threads to clients
            case "/thread/createThread", "/thread/deleteThread", "/thread/updateMessagesOfThread", "/message/createMessage", "/message/deleteMessage"
                    -> updateClientsOnly(treatRequest(new ClientRequest("/thread/getAllThreadsForUser", new HashMap<>())));

            // GROUP
            // Sends the new Database Group List to admins
            case "/group/createGroup" ->
                    updateAdminsOnly(treatRequest(new ClientRequest("/group/getAllDatabaseGroups", new HashMap<>())));

            // TODO only for affected users
            case "/group/deleteGroup" -> {
                // Sends the new Database Group List to admins
                updateAdminsOnly(treatRequest(new ClientRequest("/group/getAllDatabaseGroups", new HashMap<>())));
                // Sends recalculated threads to clients
                updateClientsOnly(treatRequest(new ClientRequest("/thread/getAllThreadsForUser", new HashMap<>())));
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

    public void setClientIsAdmin(boolean bool) {
        this.clientIsAdmin = bool;
    }

    public boolean getClientIsAdmin() {
        return this.clientIsAdmin;
    }
}
