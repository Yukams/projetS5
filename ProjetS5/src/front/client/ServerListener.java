package front.client;

import back.backobjects.groups.IGroup;
import back.backobjects.thread.IMessage;
import back.backobjects.thread.IThread;
import back.backobjects.users.IUser;
import front.frontobjects.FrontUser;

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
            case "/connect" -> System.out.println("/connect");


            // USER
            case "/user/getUserById" -> System.out.println("/user/getUserById");
            case "/user/createUser" -> System.out.println("/user/createUser");
            case "/user/deleteUser" -> System.out.println("/user/deleteUser");
            case "/user/getAllConnectedUsers" -> System.out.println("/user/getAllConnectedUsers");
            case "/user/getAllDatabaseUsers" -> {
                FrontUser[] serverUsers = gson.fromJson(payload, FrontUser[].class);
                ClientCommunication.allUsers = new ArrayList<>();
                ClientCommunication.allUsers.addAll(Arrays.asList(serverUsers));

                // Verifying print
                for (FrontUser user : ClientCommunication.allUsers) {
                    System.out.println("user => " + gson.toJson(user));
                }
            }

            // THREAD
            case "/thread/getThreadsByUserId" -> System.out.println("/thread/getThreadsByUserId");
            case "/thread/createThread" -> System.out.println("/thread/createThread");
            case "/user/deleteThread" -> System.out.println("/user/deleteThread");
            case "/thread/updateMessagesOfThread" -> System.out.println("/thread/updateMessagesOfThread");


            // MESSAGE
            case "/message/createMessage" -> System.out.println("/message/createMessage");
            case "/message/deleteMessage" -> System.out.println("/message/deleteMessage");


            // GROUP
            case "/group/createGroup" -> System.out.println("/group/createGroup");
            case "/group/deleteGroup" -> System.out.println("/group/deleteGroup");
            case "/group/addUserToGroup" -> System.out.println("/group/addUserToGroup");
            case "/group/getAllDatabaseGroups" -> System.out.println("/group/getAllDatabaseGroups");
        }
    }
}
