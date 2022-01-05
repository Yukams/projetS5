package front.client;

import back.backobjects.groups.IGroup;
import back.backobjects.thread.IMessage;
import back.backobjects.thread.IThread;
import back.backobjects.users.IUser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import front.frontobjects.FrontUser;
import front.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static back.main.mainBack.gson;

public class ServerListener implements Runnable {
    private Socket server;
    private BufferedReader in;

    public ServerListener(Socket s) throws IOException {
        this.server = s;
        this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String serverResponse = "";

            while(serverResponse != null) {
                serverResponse = in.readLine();

                if(serverResponse != null) {
                    ServerResponse serverPayload = gson.fromJson(serverResponse, ServerResponse.class);
                    treatResponse(serverPayload);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void treatResponse(ServerResponse serverResponse) throws IOException {
        String address = serverResponse.address;
        String payload = serverResponse.payload;

        System.out.println(serverResponse.type);
        if(serverResponse.type.equals("update")) {
            System.out.println("[CLIENT] Receiving update to request -> " + address + "\n with payload -> " + payload);
        } else {
            System.out.println("[CLIENT] Receiving response for request -> " + address + "\n as -> " + payload);
        }

        switch (address) {
            // CONNECTIVITY
            case "/connect" -> {
                // Deserialize Data
                if(payload == null) {
                    Utils.errorWindow("Wrong Username or Password","Error Credentials");
                } else {
                    JsonElement fileElement = JsonParser.parseString(payload);
                    JsonObject fileObject = fileElement.getAsJsonObject();
                    // Extracting the fields
                    int userId = fileObject.get("id").getAsInt();
                    String name = fileObject.get("name").getAsString();
                    String surname = fileObject.get("surname").getAsString();
                    boolean isAdmin = fileObject.get("isAdmin").getAsBoolean();
                    ClientConnexion.connectedUser = new FrontUser(name,surname,userId, isAdmin);
                }
            }


            // USER
            case "/user/getUserById" -> System.out.println("/user/getUserById");
            case "/user/createUser" -> System.out.println("/user/createUser");
            case "/user/deleteUser" -> System.out.println("/user/deleteUser");
            case "/user/getAllConnectedUsers" -> System.out.println("/user/getAllConnectedUsers");
            case "/user/getAllDatabaseUsers" -> {
                FrontUser[] serverUsers = gson.fromJson(payload, FrontUser[].class);
                ClientCommunication.allUsers = new ArrayList<>();
                ClientCommunication.allUsers.addAll(Arrays.asList(serverUsers));
            }

            // THREAD
            case "/thread/getAllThreadsForUser" -> System.out.println("/thread/getAllThreadsForUser");
            case "/thread/createThread" -> System.out.println("/thread/createThread");
            case "/thread/deleteThread" -> System.out.println("/thread/deleteThread");
            case "/thread/updateMessagesOfThread" -> System.out.println("/thread/updateMessagesOfThread");


            // MESSAGE
            case "/message/createMessage" -> System.out.println("/message/createMessage");
            case "/message/deleteMessage" -> System.out.println("/message/deleteMessage");


            // GROUP
            case "/group/createGroup" -> System.out.println("/group/createGroup");
            case "/group/deleteGroup" -> System.out.println("/group/deleteGroup");
            case "/group/addUserToGroup" -> System.out.println("/group/addUserToGroup");
            case "/group/getAllDatabaseGroups" -> System.out.println("/group/getAllDatabaseGroups");
            case "/group/getGroupsOfUserById" -> System.out.println("/group/getGroupsOfUserById");
            case "/group/getGroupsOfUser" -> System.out.println("/group/getGroupsOfUser");
        }
    }
}
