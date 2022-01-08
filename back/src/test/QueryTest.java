package test;

import api.Server;
import frontobjects.FrontGroup;
import utils.Utils;
import dbobjects.*;

import static main.mainBack.gson;

public class QueryTest {
    public static void main(String[] args) {
        System.out.println("Debut QueryTest");
        showDbUsers();
        System.out.println("[OK] showDbUsers");
        showDbGroups();
        System.out.println("[OK] showDbGroups");
        showDbThreads();
        System.out.println("[OK] showDbThreads");
        showDbMessages();
        System.out.println("[OK] showDbMessages");
        showDbMessagesFromAUser();
        System.out.println("[OK] showDbMessagesFromAUser");
        showDbThreadsForAUser();
        System.out.println("[OK] showDbThreadsForAUser");
        getMessageStatus();
        System.out.println("[OK] getMessageStatus");
        changeStatusOfMessagesInThread();
        System.out.println("[OK] changeStatusOfMessagesInThread");
        createGroupAndAddUserToIt();
        System.out.println("[OK] createGroupAndAddUserToIt");
        createUser();
        System.out.println("[OK] createUser");
        getAllDatabaseGroups();
        System.out.println("[OK] getAllDatabaseGroups");
        getAllConnectedUsers();
        System.out.println("[OK] getAllConnectedUsers");
        getAllDatabaseUsers();
        System.out.println("[OK] getAllDatabaseUsers");
        removeAUser();
        System.out.println("[OK] removeAUser");
        removeAGroup();
        System.out.println("[OK] removeAGroup");
        System.out.println("Fin QueryTest");

    }

    private static void showDbUsers() {
        // SQL call
        String jsonString = Server.treatQuery("SELECT * FROM dbUser;");
        System.out.println("\nshowDbUsers :\n" + jsonString);
        // Serialization from DB
        DbUser[] objectList = gson.fromJson(jsonString, DbUser[].class); // deserializes json into objectList
    }

    private static void showDbGroups() {
        String jsonString = Server.treatQuery("SELECT * FROM dbGroup;");
        System.out.println("\nshowDbGroups :\n" + jsonString);
        DbGroup[] objectList = gson.fromJson(jsonString, DbGroup[].class);
    }

    private static void showDbThreads() {
        String jsonString = Server.treatQuery("SELECT * FROM dbThread;");
        System.out.println("\nshowDbThreads :\n" + jsonString);
        DbThread[] objectList = gson.fromJson(jsonString, DbThread[].class);
    }

    private static void showDbMessages() {
        String jsonString = Server.treatQuery("SELECT * FROM dbMessage;");
        System.out.println("\nshowDbMessages :\n" + jsonString);
    }

    private static void showDbMessagesFromAUser() {
        int idUser = 11;
        String jsonString = Server.treatQuery("SELECT * FROM dbMessage WHERE authorId=" + idUser + ";");
        System.out.println("\nshowDbMessagesFromAUser :\n" + jsonString);
    }

    private static void showDbThreadsForAUser() {
        int idUser = 11;

        System.out.println("showDbThreadsForAUser :\n" + gson.toJson(Server.getAllThreadForUser(idUser)));
    }

    private static void getMessageStatus() {
        System.out.println("getMessageStatus SEEN :\n" + Server.getStatusFromMessageId(22));
        System.out.println("getMessageStatus NOT_SEEN :\n" + Server.getStatusFromMessageId(21));
        System.out.println("getMessageStatus NOT_SEEN :\n" + Server.getStatusFromMessageId(20));
    }

    private static void changeStatusOfMessagesInThread() {
        int threadId = 30;
        int userId = 11;

        System.out.println("changeStatusOfMessagesInThread :\n" + gson.toJson(Server.updateMessagesStatus(userId, threadId)));
    }

    private static void createGroupAndAddUserToIt() {
        int userId = 11;

        FrontGroup group = Server.createGroup("unNouveauGroupeTest" + Utils.createRandomId());
        System.out.println("createGroupAndAddUserToIt :\n" + gson.toJson(Server.addUserToGroup(group.id, userId)));
    }

    private static void createUser() {
        int i = Utils.createRandomId();

        System.out.println("createGroupAndAddUserToIt :\n" + gson.toJson(Server.createUser("toto" + i, "T" + i, "Valentin" + i, "p" + i, false)));
    }

    private static void getAllDatabaseGroups() {
        System.out.println("getAllDatabaseGroups :\n" + gson.toJson(Server.getAllDatabaseGroups()));
    }

    private static void getAllConnectedUsers() {
        System.out.println("getAllConnectedUsers :\n" + gson.toJson(Server.getAllConnectedUsers()));
    }

    private static void getAllDatabaseUsers() {
        System.out.println("getAllDatabaseUsers :\n" + gson.toJson(Server.getAllDatabaseUsers()));
    }

    private static void removeAUser() {
        System.out.println("removeAUser :\n" + gson.toJson(Server.deleteUser(11)));
    }

    private static void removeAGroup() {
        System.out.println("removeAUser :\n" + gson.toJson(Server.deleteGroup(93)));
    }
}
