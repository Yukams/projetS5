package front.client;

import com.google.gson.Gson;
import front.frontobjects.FrontGroup;
import front.frontobjects.FrontThread;
import front.frontobjects.FrontUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserRequest {
    /* Server Attributes Declaration */
    private PrintWriter out; //Write
    private static final Gson gson = new Gson();

    public UserRequest(ClientConnexionRequest clientConnexionRequest){
        System.out.println("\n-*-*[User Services]*-*-\n");
        this.out = clientConnexionRequest.getOut();
        /* IMPLEMENTING SERVER UPDATES LISTENER */
        new Thread(clientConnexionRequest.getServerListener()).start();
        this.askGroupsFromServer();
        //this.askThreadsFromServer(ClientConnexionRequest.connectedUser);
    }
    // Sends request to server, returns the Response
    private void sendRequest(String adress, Map<String, String> payload){
        /*Sending Request*/
        ServerRequest serverRequest = new ServerRequest(adress,payload);
        String request = gson.toJson(serverRequest);
        System.out.println("[USER] Do request to server" + request);
        out.println(request);
    }

    /*-------------- GROUP MANAGEMENT --------------*/
    public void askGroupsFromServer(){
        sendRequest("/group/getAllDatabaseGroups",null);
    }
    /*-------------- THREAD MANAGEMENT --------------*/
    public void askThreadsFromServer(FrontUser frontUser){
        Map<String,String> payload = new HashMap<>();
        payload.put("id",""+frontUser.id);
        this.sendRequest("/thread/getAllThreadsForUser",payload);
    }

}
