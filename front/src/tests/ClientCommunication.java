package tests;

import com.google.gson.Gson;
import client.ServerListener;
import client.ServerRequest;
import frontobjects.FrontUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientCommunication {
    public static final Gson gson = new Gson();
    public static List<FrontUser> allUsers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String serverIp = "localhost";
        int serverPort = 9090;

        Socket s = new Socket(serverIp, serverPort);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        /* IMPLEMENTING SERVER UPDATES LISTENER */
        ServerListener serverConn = new ServerListener(s);
        new Thread(serverConn).start();

        // Connect to the server
        Map<String, String> payload = new HashMap<>();
        payload.put("username", "Jean31");
        payload.put("password", "123");
        ServerRequest serverRequestConnect = new ServerRequest("/connect", payload);
        String request = gson.toJson(serverRequestConnect);
        out.println(request);
/*
        // getAllDatabaseUsers
        payload = new HashMap<>();
        ServerRequest serverRequestUsers = new ServerRequest("/user/getAllDatabaseUsers", payload);
        request = gson.toJson(serverRequestUsers);
        System.out.println("[CLIENT] Do request to server /user/getAllDatabaseUsers" + request);
        out.println(request);

        // Create a new user (update should be received after it IF an admin is connected)
        payload = new HashMap<>();
        payload.put("username", "testUser");
        payload.put("name", "Valentin");
        payload.put("surname", "Tahon");
        payload.put("password", "123");
        payload.put("isAdmin", "false");
        ServerRequest serverRequestAddUser = new ServerRequest("/user/createUser", payload);
        request = gson.toJson(serverRequestAddUser);
        System.out.println("[CLIENT] Do request to server /user/createUser" + request);
        out.println(request);


        System.out.println("[CLIENT] Entering waiting state...");
        while(true);
*/
        //out.close();
    }
}
