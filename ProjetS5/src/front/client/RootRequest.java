package front.client;

import com.google.gson.Gson;
import front.frontobjects.FrontGroup;
import front.frontobjects.FrontUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


public class RootRequest{
    /* Server Attributes Declaration */
    private BufferedReader in; //Read
    private PrintWriter out; //Write
    Map<String, String> payload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*Front Struct Attributes (AL = Array List)*/
    public ArrayList<FrontUser> allUsersAL = new ArrayList<>();
    public ArrayList<FrontUser> connectedUsersAL = new ArrayList<>();
    public ArrayList<FrontUser> disconectedUsersAL;
    public FrontUser createdUser;

    public RootRequest(ClientConnexion clientConnexion){
        System.out.println("\n-*-*[Admin Services]*-*-\n");
        this.in = clientConnexion.getIn();
        this.out = clientConnexion.getOut();
    }
    // Sends request to server, returns the Response
    private ServerResponse sendRequest(String adress, Map<String, String> payload){
        /*Sending Request*/
        ServerRequest serverRequest = new ServerRequest(adress,payload);
        String request = gson.toJson(serverRequest);
        System.out.println("[ROOT] Do request to server" + request);
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
        System.out.println("[ROOT] Response from server :\n" + serverPayload.payload);
        return serverPayload;
    }

    /*-------------- USER MANAGEMENT --------------*/
    public void createUser(Map<String,String> payload){
        ServerResponse serverPayload = this.sendRequest("/user/createUser",payload);
        this.createdUser = gson.fromJson(serverPayload.payload, FrontUser.class);
        this.allUsersAL.add(createdUser); // Adds the created user to the list of users
    }

    public void addUserToGroup(Map<String,String> payload){
        this.sendRequest("/group/addUserToGroup",payload);
    }

    public void setUsersLists(){
        // Getting All Users
        ServerResponse getAllUsersPayload = this.sendRequest("/user/getAllDatabaseUsers",null);
        FrontUser[] frontUsers = gson.fromJson(getAllUsersPayload.payload, FrontUser[].class);
        this.allUsersAL.clear();
        Collections.addAll(this.allUsersAL,frontUsers);
        // Getting All Connected Users
        ServerResponse connectedUsersPayload = this.sendRequest("/user/getAllConnectedUsers",null);
        FrontUser[] connectedUsers = gson.fromJson(connectedUsersPayload.payload,FrontUser[].class);
        this.connectedUsersAL.clear();
        Collections.addAll(this.connectedUsersAL,connectedUsers);
        // Setting All Disconnected Users
        this.disconectedUsersAL = new ArrayList<>(this.allUsersAL);
        this.disconectedUsersAL.removeAll(this.connectedUsersAL);

    }


    public void removeUser(Map<String,String> payload) {
        ServerResponse getAllUsersPayload = this.sendRequest("/user/deleteUser",payload);
        /* UPDATE ARRAY LISTS */
        FrontUser frontUser = gson.fromJson(getAllUsersPayload.payload, FrontUser.class);
        this.allUsersAL.remove(frontUser);
        this.connectedUsersAL.remove(frontUser);
        this.disconectedUsersAL.remove(frontUser);
    }
    /*-------------- GROUP MANAGEMENT --------------*/
    public FrontGroup[] askGroupsFromServer(){
        ServerResponse serverPayload = this.sendRequest("/group/getAllDatabaseGroups",null);
        FrontGroup[] frontGroups = gson.fromJson(serverPayload.payload, FrontGroup[].class);
        return frontGroups;
    }
    public void removeGroup(Map<String,String> payload){
        this.sendRequest("/group/deleteGroup",payload);
    }
    public void createGroup(Map<String,String> payload){
        this.sendRequest("/group/createGroup",payload);
    }
}
