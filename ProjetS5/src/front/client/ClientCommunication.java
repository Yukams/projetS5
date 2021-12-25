package front.client;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCommunication {
    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        String serverIp = "localhost";
        int serverPort = 9090;

        Socket s = new Socket(serverIp, serverPort);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        // Get User By Id example
        ServerRequest serverRequest = new ServerRequest("/user/getUserById", new ClientPayload(10));
        //ServerRequest request = new ServerRequest("/user/getUserById", "11");
        String request = gson.toJson(serverRequest);
        System.out.println("[CLIENT] Do request to server" + request);
        out.println(request);

        String serverResponseString = in.readLine();
        ServerResponse serverPayload = gson.fromJson(serverResponseString, ServerResponse.class);
        System.out.println(serverPayload.payload);
        while(true);
    }
}
