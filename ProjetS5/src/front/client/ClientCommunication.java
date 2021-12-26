package front.client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientCommunication {
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        String serverIp = "localhost";
        int serverPort = 9090;

        Socket s = new Socket(serverIp, serverPort);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        // Get User By Id example
        Map<String, String> payload = new HashMap<>();
        //payload.put("id", "11");
        //ServerRequest serverRequest = new ServerRequest("/user/getUserById", payload);
        //ServerRequest serverRequest = new ServerRequest("/thread/getThreadsByUserId", payload);
        //payload.put("username", "Jean31");
        //payload.put("password", "123");
        //ServerRequest serverRequest = new ServerRequest("/connect", payload);
        payload.put("authorId", "11");
        payload.put("content", "je suis un nouveau message");
        //payload.put("threadId", "30");
        //ServerRequest serverRequest = new ServerRequest("/message/createMessage", payload);
        payload.put("groupId", "92");
        payload.put("title", "je suis un nouveau thread");
        ServerRequest serverRequest = new ServerRequest("/thread/createThread", payload);
        String request = gson.toJson(serverRequest);
        System.out.println("[CLIENT] Do request to server" + request);
        out.println(request);

        String serverResponseString = in.readLine();
        ServerResponse serverPayload = gson.fromJson(serverResponseString, ServerResponse.class);
        System.out.println("[CLIENT] Response from server :\n" + serverPayload.payload);
        //while(true);
    }
}
