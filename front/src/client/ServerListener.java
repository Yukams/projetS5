package client;

import affichage.ChatWindow;
import affichage.ConnexionWindow;
import frontobjects.FrontGroup;
import frontobjects.FrontThread;
import frontobjects.FrontUser;
import server.ServerInterface;
import utils.Utils;
import main.mainFront;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import static tests.ClientCommunication.gson;

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
                ClientConnexionRequest.out.close();
                if(ConnexionWindow.serverInterface != null) ConnexionWindow.serverInterface.setVisible(false);
                if(ConnexionWindow.chatWindow != null) ConnexionWindow.chatWindow.setVisible(false);
                mainFront.reconnect();
            } catch (IOException | InterruptedException e) {
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
                if(!RootRequest.allUsersAL.contains(RootRequest.createdUser)) {
                    RootRequest.allUsersAL.add(RootRequest.createdUser); // Adds the created user to the list of users
                    RootRequest.disconectedUsersAL.add(RootRequest.createdUser);
                    /* Associating the user to an initial default group if said user is not Admin */
                    if (!ServerInterface.isUserAdminCheckBox.isSelected()) {
                        FrontUser user = RootRequest.createdUser; // User just created
                        ServerInterface.addUserToGroupFromComboBox(user, ServerInterface.selectedDefaultGroup);
                    }
                    FrontUser frontUser = ServerInterface.usrListComboBox.getItemAt(ServerInterface.usrListComboBox.getSelectedIndex());
                    RootRequest.getGroupsOfUser(frontUser);
                    RootRequest.askGroupsFromServer();
                    Utils.informationWindow("User Successfully created !", "Information");
                    this.updateUsers();
                }
            }
            case "/user/deleteUser", "/user/deleteConnectedUser"-> {
                FrontUser removedUser = gson.fromJson(payload, FrontUser.class);
                RootRequest.allUsersAL.remove(removedUser);
                RootRequest.disconectedUsersAL.remove(removedUser);
                FrontUser frontUser = ServerInterface.usrListComboBox.getItemAt(ServerInterface.usrListComboBox.getSelectedIndex());
                RootRequest.getGroupsOfUser(frontUser);
                RootRequest.askGroupsFromServer();
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
            case "/thread/getAllThreadsForUser" -> {
                FrontThread[] frontThreads = gson.fromJson(payload, FrontThread[].class);
                ArrayList<FrontThread> tempList = new ArrayList<>(Arrays.asList(frontThreads));
                if(!tempList.equals(ChatWindow.userThreads)) {
                    System.out.println("c'est pas egal");
                    ChatWindow.userThreads = new ArrayList<>();
                    ChatWindow.userThreads.addAll(Arrays.asList(frontThreads));

                    ChatWindow.updateTree();

                }
            }
            case "/thread/createThread" -> {
                gson.fromJson(payload,FrontThread.class);
            }
            case "/thread/deleteThread" -> System.out.println("/thread/deleteThread");
            case "/thread/updateMessagesOfThread" -> {

            }

            // MESSAGE
            case "/message/createMessage" -> {

                System.out.println("/message/createMessage");
            }
            case "/message/deleteMessage" -> System.out.println("/message/deleteMessage");

            // GROUP
            case "/group/createGroup" -> System.out.println("/group/createGroup");
            case "/group/deleteGroup" -> System.out.println("/group/deleteGroup");
            case "/group/addUserToGroup" -> {
                FrontGroup frontGroup = gson.fromJson(payload, FrontGroup.class);
                ServerInterface.selectedUserFrontGroups.add(frontGroup);
                RootRequest.askGroupsFromServer();
            }
            case "/group/removeUserFromGroup" -> {
                FrontGroup frontGroup = gson.fromJson(payload, FrontGroup.class);
                ServerInterface.selectedUserFrontGroups.remove(frontGroup);
                RootRequest.askGroupsFromServer();
                //if(ChatWindow.panelListMessage != null) ChatWindow.panelListMessage.setVisible(false);
            }
            case "/group/getAllDatabaseGroups" -> {
                FrontGroup[] frontGroups = gson.fromJson(payload, FrontGroup[].class);
                ArrayList<FrontGroup> frontGroupsMU = new ArrayList<>();
                boolean isIn = false;
                for(FrontGroup frontGroup : frontGroups){
                    if(ServerInterface.selectedUserFrontGroups != null && !ServerInterface.selectedUserFrontGroups.isEmpty()) {
                        isIn = ServerInterface.selectedUserFrontGroups.stream().anyMatch(group -> group.id == frontGroup.id);
                    }
                    frontGroupsMU.add(new FrontGroup(frontGroup.id, frontGroup.name,isIn));
                }
                FrontGroup[] frontGroupsArray = frontGroupsMU.toArray(new FrontGroup[0]);

                if(ServerInterface.grpSelectAddUser != null) ServerInterface.grpSelectAddUser.setModel(new DefaultComboBoxModel<>(frontGroups));
                if(ServerInterface.grpListComboBoxMU != null) ServerInterface.grpListComboBoxMU.setModel(new DefaultComboBoxModel<>(frontGroupsArray));
                if(ServerInterface.grpListToRemove != null) ServerInterface.grpListToRemove.setModel(new DefaultComboBoxModel<>(frontGroups));
                if(ServerInterface.allFrontGroups != null) ServerInterface.allFrontGroups = frontGroups;
                if(ChatWindow.comboBoxGroup != null) ChatWindow.comboBoxGroup.setModel(new DefaultComboBoxModel<>(frontGroups));
                if(ChatWindow.allFrontGroup != null) ChatWindow.allFrontGroup = frontGroups;
            }
            case "/group/getGroupsOfUserById" -> {
                FrontGroup[] frontGroups = gson.fromJson(payload, FrontGroup[].class);
                if(frontGroups.length != 0) {
                    ServerInterface.selectedUserFrontGroups = new ArrayList<>();
                    ServerInterface.selectedUserFrontGroups.addAll(Arrays.asList(frontGroups));
                    RootRequest.askGroupsFromServer();
                } else {
                    ServerInterface.selectedUserFrontGroups = new ArrayList<>();
                    RootRequest.askGroupsFromServer();
                }
            }
        }
    }

    public BufferedReader getIn() {
        return this.in;
    }

}
