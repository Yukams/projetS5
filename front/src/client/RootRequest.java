package client;

import com.google.gson.Gson;
import frontobjects.FrontGroup;
import frontobjects.FrontUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class RootRequest{
    /* Server Attributes Declaration */
    private static PrintWriter out; //Write
    Map<String, String> payload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*Front Struct Attributes (AL = Array List)*/
    public static ArrayList<FrontUser> allUsersAL = new ArrayList<>();
    public static ArrayList<FrontUser> connectedUsersAL = new ArrayList<>();
    public static ArrayList<FrontUser> disconectedUsersAL;
    public static FrontUser createdUser;

    public RootRequest(ClientConnexionRequest clientConnexionRequest){
        System.out.println("\n-*-*[Admin Services]*-*-\n");
        out = clientConnexionRequest.getOut();
        /* IMPLEMENTING SERVER UPDATES LISTENER */
        new Thread(clientConnexionRequest.getServerListener()).start();
        this.askGroupsFromServer();
        this.setUsersLists();
    }
    // Sends request to server, returns the Response
    private static void sendRequest(String adress, Map<String, String> payload){
        /*Sending Request*/
        ServerRequest serverRequest = new ServerRequest(adress,payload);
        String request = gson.toJson(serverRequest);
        System.out.println("[ROOT] Do request to server" + request);
        out.println(request);
    }

    /*-------------- USER MANAGEMENT --------------*/
    public void createUser(Map<String,String> payload){
        sendRequest("/user/createUser",payload);
    }

    public static void addUserToGroup(Map<String, String> payload){ sendRequest("/group/addUserToGroup",payload); }
    public static void removeUserFromGroup(Map<String, String> payload){ sendRequest("/group/removeUserFromGroup",payload); }

    public void setUsersLists(){
        // Getting All Users
        sendRequest("/user/getAllDatabaseUsers",null);
        // Getting All Connected Users
        sendRequest("/user/getAllConnectedUsers",null);
    }

    public void removeUser(Map<String,String> payload) {
        sendRequest("/user/deleteUser",payload);
    }
    /*-------------- GROUP MANAGEMENT --------------*/
    public void askGroupsFromServer(){ sendRequest("/group/getAllDatabaseGroups",null); }
    public void removeGroup(Map<String,String> payload){
        sendRequest("/group/deleteGroup",payload);
    }
    public void createGroup(Map<String,String> payload){
        sendRequest("/group/createGroup",payload);
    }
    public void getGroupsOfUser(FrontUser selectedUser) {
        Map<String,String> payload = new HashMap<>();
        payload.put("id",""+selectedUser.id);
        sendRequest("/group/getGroupsOfUserById",payload);
    }

}
