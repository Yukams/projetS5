package front.client;

import com.google.gson.Gson;
import front.frontobjects.FrontGroup;
import front.frontobjects.FrontUser;
import front.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


public class RootRequest {
    public final static String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    private Socket socket;
    private BufferedReader in; //Read
    private PrintWriter out; //Write
    Map<String, String> payload = new HashMap<>();
    private static final Gson gson = new Gson();
    /*Front Struct Attributes (AL = Array List)*/
    public ArrayList<FrontUser> allUsersAL = new ArrayList<>();
    public ArrayList<FrontUser> connectedUsersAL = new ArrayList<>();
    public ArrayList<FrontUser> disconectedUsersAL;
    private ArrayList<FrontUser> allUsersWithoutInstance; // All users without connected User
    public ArrayList<FrontGroup> frontGroupsAL = new ArrayList<>();

    public FrontUser createdUser;

    public RootRequest(){
        try {
            System.out.println("\n-*-*[ADMIN CONNEXION]*-*-\n");
            this.socket = new Socket(HOST, PORT);

            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
        } catch (IOException e) {
            Utils.closeAll(socket, in, out);
        }
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
    public String[] askGroupsFromServer(){
        ServerResponse serverPayload = this.sendRequest("/group/getAllDatabaseGroups",null);
        // Deserialize Data
        FrontGroup[] frontGroups = gson.fromJson(serverPayload.payload, FrontGroup[].class);
        ArrayList<String> groups = new ArrayList<>();
        for(FrontGroup frontGroup : frontGroups){
            this.frontGroupsAL.add(frontGroup);
            groups.add(frontGroup.name);
        }
        return Arrays.copyOf(groups.toArray(), groups.size(),String[].class);
    }
}
