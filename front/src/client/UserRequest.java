package client;

import affichage.ChatWindow;
import affichage.ConnexionWindow;
import com.google.gson.Gson;
import frontobjects.FrontGroup;
import frontobjects.FrontThread;
import frontobjects.FrontUser;
import utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserRequest {
    /* Server Attributes Declaration */
    private PrintWriter out; //Write
    private static final Gson gson = new Gson();
    private static final int MIN_MESSAGE_CHARS = 1;
    private static final int MAX_MESSAGE_CHARS = 3000;
    private static final int MIN_THREAD_CHARS = 1;
    private static final int MAX_THREAD_CHARS = 300;

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

    public static boolean isValidStringForMessage(String str){
        return checkValidStringForUser(str, MIN_MESSAGE_CHARS, MAX_MESSAGE_CHARS);
    }

    public static boolean isValidStringForThread(String str){
        return checkValidStringForUser(str, MIN_THREAD_CHARS, MAX_THREAD_CHARS);
    }

    private static boolean checkValidStringForUser(String str, int minThreadChars, int maxThreadChars) {
        String specialCharactersString = "\"";
        return checkValidString(str, minThreadChars, maxThreadChars, specialCharactersString);
    }

    public static boolean checkValidString(String str, int minThreadChars, int maxThreadChars, String specialCharactersString) {
        if(str.length()>= minThreadChars && str.length() <= maxThreadChars) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if(specialCharactersString.contains(Character.toString(c))){ return false; }
            }
            return true;
        }
        return false;
    }

    /*------------- MESSAGE MANAGEMENT -------------*/
    public boolean createMessage(int authorId,String content,int threadId){
        if(isValidStringForMessage(content)) {
            Map<String, String> payload = new HashMap<>();
            payload.put("authorId", "" + authorId);
            payload.put("content", content);
            System.out.println("content:" + content);
            payload.put("threadId", "" + threadId);
            sendRequest("/message/createMessage", payload);
            ChatWindow.isCreatorThread = true;
            ChatWindow.hasWrittenMessage = true;
            return true;
        } else {
            String msg = "Message must contain between " + MIN_MESSAGE_CHARS +" and " + MAX_MESSAGE_CHARS +" characters !\nUnauthorized chars: \"";
            Utils.warningWindow(msg, "Information");
            return false;
        }
    }

    /*-------------- GROUP MANAGEMENT --------------*/
    public void askGroupsFromServer(){
        sendRequest("/group/getAllDatabaseGroups",null);
    }

    /*-------------- THREAD MANAGEMENT --------------*/
    public void askThreadsFromServer(FrontUser frontUser){
        ChatWindow.userRequest.clientGetThreadsAtConnection();
    }

    public void createThread(int authorId,int groupId, String title, String content) {
        if(isValidStringForThread(title)) {
            Map<String, String> payload = new HashMap<>();
            payload.put("authorId", "" + authorId);
            payload.put("groupId", "" + groupId);
            payload.put("title", title);
            payload.put("content", content);
            sendRequest("/thread/createThread", payload);
            ChatWindow.isCreatorThread = true;
        } else {
            String msg = "Thread title must contain between " + MIN_THREAD_CHARS +" and " + MAX_THREAD_CHARS +" characters !\nUnauthorized chars: \"";
            Utils.warningWindow(msg, "Information");
        }
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
