package test;

import api.Server;
import frontobjects.FrontGroup;
import utils.Utils;
import dbobjects.*;

import static main.mainBack.gson;

public class QueryTest {
    public static void main(String[] args) {
        
        showDbUsers();
        
        showDbGroups();
        
        showDbThreads();
        
        showDbMessages();
        
        showDbMessagesFromAUser();
        
        showDbThreadsForAUser();
        
        getMessageStatus();
        
        changeStatusOfMessagesInThread();
        
        createGroupAndAddUserToIt();
        
        createUser();
        
        getAllDatabaseGroups();
        
        getAllConnectedUsers();
        
        getAllDatabaseUsers();
        
        removeAUser();
        
        removeAGroup();
        
        

    }

    private static void showDbUsers() {
        // SQL call
        String jsonString = Server.treatQuery("SELECT * FROM dbUser;");
        
        // Serialization from DB
        DbUser[] objectList = gson.fromJson(jsonString, DbUser[].class); // deserializes json into objectList
    }

    private static void showDbGroups() {
        String jsonString = Server.treatQuery("SELECT * FROM dbGroup;");
        
        DbGroup[] objectList = gson.fromJson(jsonString, DbGroup[].class);
    }

    private static void showDbThreads() {
        String jsonString = Server.treatQuery("SELECT * FROM dbThread;");
        
        DbThread[] objectList = gson.fromJson(jsonString, DbThread[].class);
    }

    private static void showDbMessages() {
        String jsonString = Server.treatQuery("SELECT * FROM dbMessage;");
        
    }

    private static void showDbMessagesFromAUser() {
        int idUser = 11;
        String jsonString = Server.treatQuery("SELECT * FROM dbMessage WHERE authorId=" + idUser + ";");
        
    }

    private static void showDbThreadsForAUser() {
        int idUser = 11;

        
    }

    private static void getMessageStatus() {
        
        
        
    }

    private static void changeStatusOfMessagesInThread() {
        int threadId = 30;
        int userId = 11;

        
    }

    private static void createGroupAndAddUserToIt() {
        int userId = 11;

        FrontGroup group = Server.createGroup("unNouveauGroupeTest" + Utils.createRandomId());
        
    }

    private static void createUser() {
        int i = Utils.createRandomId();

        
    }

    private static void getAllDatabaseGroups() {
        
    }

    private static void getAllConnectedUsers() {
        
    }

    private static void getAllDatabaseUsers() {
        
    }

    private static void removeAUser() {
        
    }

    private static void removeAGroup() {
        
    }
}
