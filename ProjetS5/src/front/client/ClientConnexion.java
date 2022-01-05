package front.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import front.frontobjects.FrontUser;
import front.utils.Utils;

import java.io.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ClientConnexion {


    private Socket socket;
    private PrintWriter out; //Write
    Map<String, String> authPayload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*--------------*/
    public final static String HOST = "127.0.0.1";
    public static final int PORT = 9090;
    public static FrontUser connectedUser = null;

    public ClientConnexion(String username, String password){
        try {
            System.out.println("\n-*-*[CONNECTING...]*-*-\n");
            this.socket = new Socket(HOST, PORT);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            /* IMPLEMENTING SERVER UPDATES LISTENER */
            ServerListener serverConn = new ServerListener(this.socket);
            new Thread(serverConn).start();
            authPayload.put("username", username);
            authPayload.put("password", password);
            // Sending Payload
            ServerRequest serverRequest = new ServerRequest("/connect", authPayload);
            // Serealize Data
            String request = gson.toJson(serverRequest);
            System.out.println("[CLIENT] Do request to server" + request);
            // Sending Request
            out.println(request);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PrintWriter getOut(){
        return this.out;
    }
}
