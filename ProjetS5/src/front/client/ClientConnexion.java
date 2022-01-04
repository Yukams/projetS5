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

    private static final int PORT = 9090;
    private Socket socket;
    private BufferedReader in; //Read
    private PrintWriter out; //Write
    Map<String, String> authPayload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*--------------*/
    public final static String HOST = "127.0.0.1";
    public FrontUser connectedUser = null;

    public ClientConnexion(String username, String password){
        try {
            System.out.println("\n-*-*[CONNECTING...]*-*-\n");
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
                Utils.errorWindow("Wrong Username or Password","Error Credentials");
            } else {
                JsonElement fileElement = JsonParser.parseString(serverPayload.payload);
                JsonObject fileObject = fileElement.getAsJsonObject();
                // Extracting the fields
                int userId = fileObject.get("id").getAsInt();
                String name = fileObject.get("name").getAsString();
                String surname = fileObject.get("surname").getAsString();
                String isAdmin = fileObject.get("isAdmin").getAsString();
                this.connectedUser = new FrontUser(name,surname,userId, isAdmin.equals("1"));
            }

        } catch (IOException e) {
            Utils.closeAll(socket, in, out);
        }
    }



}
