package back.main;

import back.api.Server;
import back.backobjects.groups.IGroup;
import back.backobjects.thread.IMessage;
import back.backobjects.thread.IThread;
import back.backobjects.users.IUser;
import com.google.gson.Gson;
import back.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson = new Gson();
    private int clientId = -1;
    private boolean clientIsAdmin = false;
    private boolean doUpdate = false;

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
                updateAllClients(treatRequest(new ClientRequest("/user/getAllConnectedUsers", new HashMap<>())), true, false, true);
            }
            mainBack.clients.remove(this);
        }
    }

    private void updateAllClients(ServerResponse serverResponse, boolean adminOnly, boolean usersOnly, boolean withoutSelf) {
        for(ClientHandler client : mainBack.clients) {
            boolean isAdmin = client.getClientIsAdmin();

            if((!adminOnly || isAdmin) && (!usersOnly || !isAdmin) && (!withoutSelf || client.getClientId() != this.clientId)) {
                ServerResponse update = new ServerResponse(serverResponse.address, serverResponse.payload, "update");
                System.out.println("[SERVER] Sending update to client (" + client.getClientId() + ") => " + update.payload);
                client.getPrinterOut().println(gson.toJson(update));
            }
        }
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

            // { "authorId": int, "groupId": int, "title": String, "content": String }
            // Updates
            case "/thread/createThread" -> IThread.createThread(payload);

            // { "id": int }
            // Updates
            case "/thread/deleteThread" -> IThread.deleteThread(payload);

            // { "userId": int, "threadId": int }
            // Updates
            case "/thread/updateMessagesOfThread" -> IThread.updateMessages(payload);


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

            // {}
            case "/group/getAllDatabaseGroups" -> IGroup.getAllDatabaseGroups();

            // { "userId": int }
            case "/group/getGroupsOfUserById" -> IGroup.getGroupsOfUserById(payload);

            case "/group/getGroupsOfUser" -> IGroup.getGroupsOfUser(this.clientId);

            default -> "\"null\"";
        };

        return new ServerResponse(address, toClient, "response");
    }

    private void doUpdateIfNeeded(ClientRequest request) {
        String address = request.address;

        switch (address) {
            // CONNECTIVITY
            case "/connect" -> updateAllClients(treatRequest(new ClientRequest("/user/getAllConnectedUsers", new HashMap<>())), true, false, false);

            // USER
            case "/user/createUser" -> updateAllClients(treatRequest(new ClientRequest("/user/getAllDatabaseUsers", new HashMap<>())), true, false, false);
            case "/user/deleteUser" -> {
                updateAllClients(treatRequest(new ClientRequest("/user/getAllDatabaseUsers", new HashMap<>())), true, false, false);
                // TODO only for affected users
                updateAllClients(treatRequest(new ClientRequest("/thread/getAllThreadsForUser", new HashMap<>())), false, true, false);
            }

            // THREAD & MESSAGE & addUserToGroup
            // TODO only for affected users
            case "/thread/createThread", "/thread/deleteThread", "/thread/updateMessagesOfThread", "/message/createMessage", "/message/deleteMessage"
                    -> updateAllClients(treatRequest(new ClientRequest("/thread/getAllThreadsForUser", new HashMap<>())), false, true, false);

            // GROUP
            case "/group/createGroup" ->
                    updateAllClients(treatRequest(new ClientRequest("/group/getAllDatabaseGroups", new HashMap<>())), true, false, false);
            case "/group/deleteGroup" -> {
                // TODO only for affected users
                updateAllClients(treatRequest(new ClientRequest("/thread/getAllThreadsForUser", new HashMap<>())), false, true, false);
                updateAllClients(treatRequest(new ClientRequest("/group/getAllDatabaseGroups", new HashMap<>())), true, false, false);
            }
            // TODO do it only for the selected user
            case "/group/addUserToGroup" -> {
                updateAllClients(treatRequest(new ClientRequest("/group/getGroupsOfUser", new HashMap<>())), false, true, false);
            }
        }
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
