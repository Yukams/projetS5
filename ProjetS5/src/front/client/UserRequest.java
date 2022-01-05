package front.client;

import com.google.gson.Gson;
import front.frontobjects.FrontGroup;
import front.frontobjects.FrontThread;
import front.frontobjects.FrontUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRequest {
    /* Server Attributes Declaration */
    private BufferedReader in; //Read
    private PrintWriter out; //Write
    private static final Gson gson = new Gson();

    public UserRequest(ClientConnexion clientConnexion){
        System.out.println("\n-*-*[User Services]*-*-\n");
        this.in = clientConnexion.getIn();
        this.out = clientConnexion.getOut();
    }
    // Sends request to server, returns the Response
    private ServerResponse sendRequest(String adress, Map<String, String> payload){
        /*Sending Request*/
        ServerRequest serverRequest = new ServerRequest(adress,payload);
        String request = gson.toJson(serverRequest);
        System.out.println("[USER] Do request to server" + request);
        out.println(request);
        /*Wait for response*/
        String serverResponseString = null;
        try {
            serverResponseString = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur readline");
        }
        ServerResponse serverPayload = gson.fromJson(serverResponseString, ServerResponse.class);
        System.out.println("[USER] Response from server :\n" + serverPayload.payload);
        return serverPayload;
    }

    /*-------------- GROUP MANAGEMENT --------------*/
    public FrontGroup[] askGroupsFromServer(){
        ServerResponse serverPayload = this.sendRequest("/group/getAllDatabaseGroups",null);
        FrontGroup[] frontGroups = gson.fromJson(serverPayload.payload, FrontGroup[].class);
        return frontGroups;
    }
    /*-------------- THREAD MANAGEMENT --------------*/
    public FrontThread[] askThreadsFromServer(FrontUser frontUser){
        Map<String,String> payload = new HashMap<>();
        payload.put("id",""+frontUser.id);
        ServerResponse serverPayload = this.sendRequest("/thread/getThreadsByUserId",payload);
        FrontThread[] frontThreads = gson.fromJson(serverPayload.payload, FrontThread[].class);
        return frontThreads;
    }

}
