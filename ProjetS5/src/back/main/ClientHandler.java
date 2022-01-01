package back.main;

import back.api.Server;
import back.backobjects.groups.IGroup;
import back.backobjects.thread.IMessage;
import back.backobjects.thread.IThread;
import back.backobjects.users.IUser;
import com.google.gson.Gson;
import front.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private Gson gson = new Gson();
    private int clientId = -1;

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
                System.out.println("request -> " + request);

                if(request != null) {
                    System.out.println("[SERVER] Received request from client => " + request);
                    ClientRequest serverPayload = gson.fromJson(request, ClientRequest.class);
                    String response = treatRequest(serverPayload);
                    System.out.println("[SERVER] Sending response to client => " + response);
                    out.println(response);
                }
            }
        } catch (IOException e) {
            Utils.closeAll(this.client,this.in,this.out);
        } finally {
            if(clientId != -1){
                Server.disconnect(this);
            }
        }
    }

    public String treatRequest(ClientRequest request) {
        String address = request.address;
        Map<String,String> payload = request.payload;
        String toClient = switch (address) {
            // CONNECTIVITY
            // { "username": String, "password": String }
            case "/connect" -> Server.connect(this ,payload);


            // USER
            // { "id": int }
            case "/user/getUserById" -> IUser.getUserById(payload);

            // { "username": String, "name": String, "surname": String, "password": String }
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

        return "{ \"payload\": " + gson.toJson(toClient) + "}";
    }

    public void setClientId(int id) {
        this.clientId = id;
    }

    public int getClientId() {
        return this.clientId;
    }
}
