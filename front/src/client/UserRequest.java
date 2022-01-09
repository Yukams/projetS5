package client;

import affichage.ChatWindow;
import affichage.ConnexionWindow;
import com.google.gson.Gson;
import frontobjects.FrontGroup;
import frontobjects.FrontThread;
import frontobjects.FrontUser;

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

    /*------------- MESSAGE MANAGEMENT -------------*/
    public void createMessage(int authorId,String content,int threadId){
        Map<String,String> payload = new HashMap<>();
        payload.put("authorId",""+authorId);
        payload.put("content",content);
        System.out.println("content:"+content);
        payload.put("threadId",""+threadId);
        sendRequest("/message/createMessage",payload);

    }

    /*-------------- GROUP MANAGEMENT --------------*/
    public void askGroupsFromServer(){
        sendRequest("/group/getAllDatabaseGroups",null);
    }

    /*-------------- THREAD MANAGEMENT --------------*/
    public void askThreadsFromServer(FrontUser frontUser){
        /*
        Map<String,String> payload = new HashMap<>();
        payload.put("id",""+frontUser.id);
        sendRequest("/thread/getAllThreadsForUser",payload);
        */
        ChatWindow.userRequest.clientGetThreadsAtConnection();
    }

    public void createThread(int authorId,int groupId, String title, String content) {
        Map<String,String> payload = new HashMap<>();
        payload.put("authorId",""+authorId);
        payload.put("groupId",""+groupId);
        payload.put("title",title);
        payload.put("content",content);
        sendRequest("/thread/createThread",payload);
        ChatWindow.isCreatorThread=true;
    }

    public void updateMessagesOfThread(FrontThread frontThread) {
        Map<String,String> payload = new HashMap<>();
        if(frontThread.id != 0) {
            payload.put("clientId", "" + ChatWindow.connectedUser.id);
            payload.put("threadId", "" + frontThread.id);
            sendRequest("/thread/updateMessagesOfThread", payload);
        }
    }

    public void clientGetThreadsAtConnection() {
        Map<String,String> payload = new HashMap<>();
        payload.put("clientId", "" + ChatWindow.connectedUser.id);
        sendRequest("/thread/clientGetThreadsAtConnection", payload);
    }
}
