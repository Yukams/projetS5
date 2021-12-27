package front.client;

import com.google.gson.Gson;
import front.main.mainFront;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RootRequest {
    public final static String HOST = "127.0.0.1";
    private static final int PORT = 9090;
    private Socket socket;
    private BufferedReader in; //Read
    private PrintWriter out; //Write
    Map<String, String> payload = new HashMap<>();
    private static final Gson gson = new Gson();


    public RootRequest(){
        try {
            this.socket = new Socket(HOST, PORT);

            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(this.socket.getOutputStream(), true);

        } catch (IOException e) {
            mainFront.utils.closeAll(socket, in, out);
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
        }
        ServerResponse serverPayload = gson.fromJson(serverResponseString, ServerResponse.class);
        System.out.println("[ROOT] Response from server :\n" + serverPayload.payload);
        return serverPayload;
    }

    public String[] askGroupsFromServer(){
        ArrayList<String> groups = new ArrayList<>();
        ServerResponse serverResponse = this.sendRequest("/group/getAllDatabaseGroups",null);
        return Arrays.copyOf(groups.toArray(), groups.size(),String[].class);
    }



}
