package back.main;

import back.api.Server;
import back.backobjects.thread.IMessage;
import back.backobjects.thread.IThread;
import back.backobjects.users.IUser;
import com.google.gson.Gson;

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

    public ClientHandler(Socket clientSocket) throws IOException {
        client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    @Override
    public void run() {
        String request = null;
        try {
            while (request == null) {
                request = in.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert request != null;

            System.out.println("[SERVER] Received request from client => " + request);
            ClientRequest serverPayload = gson.fromJson(request, ClientRequest.class);
            String response = treatRequest(serverPayload);
            System.out.println("[SERVER] Sending response to client => " + response);
            out.println(response);

            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String treatRequest(ClientRequest request) {
        String address = request.address;
        Map<String,String> payload = request.payload;
        String toClient = switch (address) {
            // {"id": int}
            case "/user/getUserById" -> IUser.getUserById(payload);
            // {"id": int}
            case "/thread/getThreadsByUserId" -> IThread.getAllThreadForUser(payload);
            // {"username": String, "password": String}
            case "/connect" -> Server.connect(payload);
            // {"authorId": int, "content": String, "threadId": int}
            case "/message/createMessage" -> IMessage.createMessage(payload);
            // {"authorId": int, "groupId": int, "title": String, "content": String}
            case "/thread/createThread" -> IThread.createThread(payload);
            // {"userId": int, "threadId": int}
            case "/thread/updateMessagesOfThread" -> IThread.updateMessages(payload);
            default -> "\"null\"";
        };

        return "{ \"payload\": " + gson.toJson(toClient) + "}";
    }
}
