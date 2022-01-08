package client;

import com.google.gson.Gson;
import frontobjects.FrontUser;
import utils.Utils;

import java.io.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientConnexionRequest {

    public static Socket socket;
    public static PrintWriter out; //Write
    public ServerListener serverListener;
    Map<String, String> authPayload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*--------------*/
    public final static String HOST = "127.0.0.1";
    public static final int PORT = 9090;
    public static FrontUser connectedUser = null;
    public static boolean connected = false;
    public static boolean clientExists = true;
    public static boolean isReconnecting = false;

    public ClientConnexionRequest(String username, String password){
        try {
            System.out.println("\n-*-*[CONNECTING...]*-*-\n");
            socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            /* IMPLEMENTING SERVER UPDATES LISTENER */
            this.serverListener = new ServerListener(socket);
            authPayload.put("username", username);
            authPayload.put("password", password);
            // Sending Payload
            ServerRequest serverRequest = new ServerRequest("/connect", authPayload);
            // Serealize Data
            String request = gson.toJson(serverRequest);
            System.out.println("[CLIENT] Do request to server" + request);
            // Sending Request
            out.println(request);
            // Wait Response
            String serverResponseString = this.serverListener.getIn().readLine();
            ServerResponse serverPayload = gson.fromJson(serverResponseString, ServerResponse.class);
            System.out.println("[USER] Response from server :\n" + serverPayload.payload);
            // Deserialize Data
            if(serverPayload.payload != null) {
                connectedUser = gson.fromJson(serverPayload.payload, FrontUser.class);
                connected = true;
                clientExists = true;
            } else {
                clientExists = false;
                if(!isReconnecting) Utils.errorWindow("An error has occurred","Connexion Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public PrintWriter getOut(){
        return out;
    }
    public ServerListener getServerListener() {
        return this.serverListener;
    }
}
