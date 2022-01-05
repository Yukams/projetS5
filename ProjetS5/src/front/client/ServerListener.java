package front.client;

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
                    if(serverPayload.type.equals("update")) {
                        treatUpdate(serverPayload);
                    } else {
                        treatResponse(serverPayload);
                    }
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

    private void treatUpdate(ServerResponse serverResponse) {
        String address = serverResponse.address;
        String payload = serverResponse.payload;

        System.out.println("[CLIENT] Receiving updates as request -> " + address + "\n with payload -> " + payload);
    }


    private void treatResponse(ServerResponse serverResponse) throws IOException {
        String address = serverResponse.address;
        String payload = serverResponse.payload;

        System.out.println("[CLIENT] Receiving response for request -> " + address + "\n as -> " + payload);

        switch (address) {
            // CONNECTIVITY
            case "/connect":
                System.out.println("/connect");
                break;

            // USER
            case "/user/getUserById":
                System.out.println("/user/getUserById");
                break;

            case "/user/getAllConnectedUsers":
                System.out.println("/user/getAllConnectedUsers");
                break;

            case "/user/getAllDatabaseUsers":
                FrontUser[] serverUsers = gson.fromJson(serverResponse.payload, FrontUser[].class);
                ClientCommunication.allUsers = new ArrayList<>();
                ClientCommunication.allUsers.addAll(Arrays.asList(serverUsers));

                // Verifying print
                for(FrontUser user: ClientCommunication.allUsers) {
                    System.out.println("user => " + gson.toJson(user));
                }
                break;

            // THREAD
            case "/thread/getThreadsByUserId":
                System.out.println("/thread/getThreadsByUserId");
                break;

            case "/thread/updateMessagesOfThread":
                System.out.println("/thread/updateMessagesOfThread");
                break;


            // GROUP
            case "/group/addUserToGroup":
                System.out.println("/group/addUserToGroup");
                break;

            case "/group/getAllDatabaseGroups":
                System.out.println("/group/getAllDatabaseGroups");
                break;
        };

        System.out.println("[CLIENT] received -> " + payload);
    }
}
