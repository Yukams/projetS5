package front.client;

import com.google.gson.Gson;
import front.frontobjects.FrontGroup;
import front.frontobjects.FrontUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class RootRequest{
    /* Server Attributes Declaration */
    private PrintWriter out; //Write
    Map<String, String> payload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*Front Struct Attributes (AL = Array List)*/
    public static ArrayList<FrontUser> allUsersAL = new ArrayList<>();
    public static ArrayList<FrontUser> connectedUsersAL = new ArrayList<>();
    public static ArrayList<FrontUser> disconectedUsersAL;
    public static ArrayList<FrontGroup> frontGroupsAL = new ArrayList<>();
    public static FrontUser createdUser;

    public RootRequest(ClientConnexionRequest clientConnexionRequest){
        System.out.println("\n-*-*[Admin Services]*-*-\n");
        this.out = clientConnexionRequest.getOut();
        /* IMPLEMENTING SERVER UPDATES LISTENER */
        new Thread(clientConnexionRequest.getServerListener()).start();
        this.askGroupsFromServer();
        this.setUsersLists();
    }
    // Sends request to server, returns the Response
    private void sendRequest(String adress, Map<String, String> payload){
        /*Sending Request*/
        ServerRequest serverRequest = new ServerRequest(adress,payload);
        String request = gson.toJson(serverRequest);
        System.out.println("[ROOT] Do request to server" + request);
        out.println(request);
    }

    /*-------------- USER MANAGEMENT --------------*/
    public void createUser(Map<String,String> payload){
        this.sendRequest("/user/createUser",payload);
    }

    public void addUserToGroup(Map<String,String> payload){
        this.sendRequest("/group/addUserToGroup",payload);
    }

    public void setUsersLists(){
        // Getting All Users
        this.sendRequest("/user/getAllDatabaseUsers",null);
        // Getting All Connected Users
        this.sendRequest("/user/getAllConnectedUsers",null);
    }

    public void removeUser(Map<String,String> payload) {
        // TODO CHECK IF USER IS NOT CONNECTED BEFORE DELETING
        this.sendRequest("/user/deleteUser",payload);
    }
    /*-------------- GROUP MANAGEMENT --------------*/
    public void askGroupsFromServer(){
        this.sendRequest("/group/getAllDatabaseGroups",null);
    }
    public void removeGroup(Map<String,String> payload){
        this.sendRequest("/group/deleteGroup",payload);
    }
    public void createGroup(Map<String,String> payload){
        this.sendRequest("/group/createGroup",payload);
    }
}
