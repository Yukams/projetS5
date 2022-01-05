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
import java.util.Arrays;
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
            }
            mainBack.clients.remove(this);
        }
    }

    private void updateAllClients(ServerResponse serverResponse, boolean adminOnly) {
        for(ClientHandler client : mainBack.clients) {
            if(!adminOnly || this.clientIsAdmin) {
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
            case "/connect" -> Server.connect(this, payload);

            // USER
            // { "id": int }
            case "/user/getUserById" -> IUser.getUserById(payload);

            // { "username": String, "name": String, "surname": String, "password": String, "isAdmin": boolean }
            case "/user/createUser" -> IUser.createUser(payload);

            // { "id": int }
            case "/user/deleteUser" -> IUser.deleteUser(payload);

            // {}
            case "/user/getAllConnectedUsers" -> IUser.getAllConnectedUsers();

            // {}
            case "/user/getAllDatabaseUsers" -> IUser.getAllDatabaseUsers();

            // THREAD
            // { "id": int }
            case "/thread/getThreadsByUserId" -> IThread.getAllThreadForUser(payload);

            // { "authorId": int, "groupId": int, "title": String, "content": String }
            case "/thread/createThread" -> IThread.createThread(payload);

            // { "id": int }
            case "/user/deleteThread" -> IThread.deleteThread(payload);

            // { "userId": int, "threadId": int }
            case "/thread/updateMessagesOfThread" -> IThread.updateMessages(payload);


            // MESSAGE
            // { "authorId": int, "content": String, "threadId": int }
            case "/message/createMessage" -> IMessage.createMessage(payload);

            // { "id": int }
            case "/message/deleteMessage" -> IMessage.deleteMessage(payload);


            // GROUP
            // { "name": String }
            case "/group/createGroup" -> IGroup.createGroup(payload);

            // { "id": int }
            case "/group/deleteGroup" -> IGroup.deleteGroup(payload);

            // { "groupId": int, "userId": int }
            case "/group/addUserToGroup" -> IGroup.addUserToGroup(payload);

            // {}
            case "/group/getAllDatabaseGroups" -> IGroup.getAllDatabaseGroups();

            default -> "\"null\"";
        };

        return new ServerResponse(address, toClient, "response");
    }

    private void doUpdateIfNeeded(ClientRequest request) {
        String address = request.address;

        switch (address) {
            // CONNECTIVITY
            case "/connect" -> {
                updateAllClients(treatRequest(new ClientRequest("/user/getAllConnectedUsers", new HashMap<>())), true);
            }

            // USER
            case "/user/createUser" -> {
                updateAllClients(treatRequest(new ClientRequest("/user/getAllDatabaseUsers", new HashMap<>())), true);
            }
        }
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

    public int getClientId() {
        return this.clientId;
    }

    public Socket getSocket() {
        return this.client;
    }

    public PrintWriter getPrinterOut() {
        return this.out;
    }

    public void setClientIsAdmin(boolean bool) {
        this.clientIsAdmin = bool;
    }
}
