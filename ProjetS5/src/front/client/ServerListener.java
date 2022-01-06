package front.client;

import front.frontobjects.FrontGroup;
import front.frontobjects.FrontUser;
import front.server.ServerInterface;

import javax.swing.*;
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

    /* UPDATE FUNCTIONS */
    private void updateUsers(){
        // UPDATE COMBO BOX
        FrontUser[] frontUsersArray = new FrontUser[RootRequest.allUsersAL.size()];
        ServerInterface.usrListComboBox.setModel(new DefaultComboBoxModel<>(RootRequest.allUsersAL.toArray(frontUsersArray)));
        // UPDATE LIST OF USERS
        ServerInterface.setUsrsList();
    }
    /*------------------*/

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
            // USER
            case "/user/getUserById" -> System.out.println("/user/getUserById");
            case "/user/createUser" -> {
                RootRequest.createdUser = gson.fromJson(payload, FrontUser.class);
                RootRequest.allUsersAL.add(RootRequest.createdUser); // Adds the created user to the list of users
                RootRequest.disconectedUsersAL.add(RootRequest.createdUser);
                this.updateUsers();
            }
            case "/user/deleteUser" -> {
                FrontUser removedUser = gson.fromJson(payload, FrontUser.class);
                RootRequest.allUsersAL.remove(removedUser);
                RootRequest.disconectedUsersAL.remove(removedUser);
                this.updateUsers();
            }
            case "/user/getAllConnectedUsers" -> {
                FrontUser[] connectedUsers = gson.fromJson(payload,FrontUser[].class);

                RootRequest.connectedUsersAL = new ArrayList<>();
                RootRequest.connectedUsersAL.addAll(Arrays.asList(connectedUsers));
                // Setting All Disconnected Users
                RootRequest.disconectedUsersAL = new ArrayList<>(RootRequest.allUsersAL);
                RootRequest.disconectedUsersAL.removeAll(RootRequest.connectedUsersAL);
                this.updateUsers();

            }
            case "/user/getAllDatabaseUsers" -> {
                FrontUser[] serverUsers = gson.fromJson(payload, FrontUser[].class);
                RootRequest.allUsersAL = new ArrayList<>();
                RootRequest.allUsersAL.addAll(Arrays.asList(serverUsers));
                ServerInterface.usrListComboBox.setModel(new DefaultComboBoxModel<>(serverUsers));
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
            case "/group/getAllDatabaseGroups" -> {
                FrontGroup[] frontGroups = gson.fromJson(payload, FrontGroup[].class);

                ServerInterface.grpSelectAddUser.setModel(new DefaultComboBoxModel<>(frontGroups));
                ServerInterface.grpListAdd2Usr.setModel(new DefaultComboBoxModel<>(frontGroups));
                ServerInterface.grpListToRemove.setModel(new DefaultComboBoxModel<>(frontGroups));
            }
            case "/group/getGroupsOfUserById" -> System.out.println("/group/getGroupsOfUserById");
            case "/group/getGroupsOfUser" -> System.out.println("/group/getGroupsOfUser");
        }
    }

    public BufferedReader getIn() {
        return this.in;
    }

}
