package front.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import front.frontobjects.FrontUser;
import front.main.mainFront;
import front.utils.Utils;

import java.io.*;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class ClientConnexion {
    public final static String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    private Socket socket;
    private BufferedReader in; //Read
    private PrintWriter out; //Write
    Map<String, String> authPayload = new HashMap<>();
    private static final Gson gson = new Gson();
    FrontUser frontUser = null;


    public ClientConnexion(String username, String password){
        try {
            this.socket = new Socket(HOST, PORT);

            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            authPayload.put("username", username);
            authPayload.put("password", password);
            // Sending Payload
            ServerRequest serverRequest = new ServerRequest("/connect", authPayload);
            // Serealize Data
            String request = gson.toJson(serverRequest);
            System.out.println("[CLIENT] Do request to server" + request);
            // Sending Request
            out.println(request);
            // Waiting for response
            String serverResponseString = in.readLine();
            // Deserialize Data
            ServerResponse serverPayload = gson.fromJson(serverResponseString, ServerResponse.class);
            System.out.println("[CLIENT] Response from server :\n" + serverPayload.payload);
            if(serverPayload.payload == null) {
                Utils.credentialsErrorMessage();
            } else {
                JsonElement fileElement = JsonParser.parseString(serverPayload.payload);
                JsonObject fileObject = fileElement.getAsJsonObject();
                // Extracting the fields
                int userId = fileObject.get("id").getAsInt();
                String name = fileObject.get("name").getAsString();
                String surname = fileObject.get("surname").getAsString();
                boolean isAdmin = fileObject.get("isAdmin").getAsBoolean();
                this.frontUser = new FrontUser(name,surname,userId, isAdmin);
            }

        } catch (IOException e) {
            mainFront.utils.closeAll(socket, in, out);
        }
    }

    public FrontUser getFrontUser(){
        return this.frontUser;
    }



}
