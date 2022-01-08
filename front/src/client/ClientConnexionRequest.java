package client;

import com.google.gson.Gson;
import frontobjects.FrontUser;
import utils.Utils;

import java.io.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ClientConnexionRequest {

    private Socket socket;
    private PrintWriter out; //Write
    public ServerListener serverListener;
    Map<String, String> authPayload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*--------------*/
    public final static String HOST = "127.0.0.1";
    public static final int PORT = 9090;
    public static FrontUser connectedUser = null;

    public ClientConnexionRequest(String username, String password){
        try {
            System.out.println("\n-*-*[CONNECTING...]*-*-\n");
            this.socket = new Socket(HOST, PORT);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            /* IMPLEMENTING SERVER UPDATES LISTENER */
            this.serverListener = new ServerListener(this.socket);
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
            } else {
                Utils.errorWindow("An error has occurred","Connexion Error");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return this.socket;
    }
    public PrintWriter getOut(){
        return this.out;
    }
    public ServerListener getServerListener() {
        return this.serverListener;
    }
}
