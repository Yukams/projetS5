package tests.back;

import java.sql.SQLException;

import back.api.Server;
import com.google.gson.Gson;
import back.dbobjects.*;

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
        System.out.println("Fin QueryTest");
    }

    private static void showDbUsers() {
        // SQL call
        String jsonString = Server.treatQuery("SELECT * FROM dbUser;");
        System.out.println("\nshowDbUsers :\n" + jsonString);
        // Serialization from DB
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        DbUser[] objectList = gson.fromJson(jsonString, DbUser[].class); // deserializes json into objectList
    }

    private static void showDbGroups() {
        String jsonString = Server.treatQuery("SELECT * FROM dbGroup;");
        System.out.println("\nshowDbGroups :\n" + jsonString);
        Gson gson = new Gson();
        DbGroup[] objectList = gson.fromJson(jsonString, DbGroup[].class);
    }

    private static void showDbThreads() {
        String jsonString = Server.treatQuery("SELECT * FROM dbThread;");
        System.out.println("\nshowDbThreads :\n" + jsonString);
        Gson gson = new Gson();
        DbThread[] objectList = gson.fromJson(jsonString, DbThread[].class);
    }

    private static void showDbMessages() {
        String jsonString = Server.treatQuery("SELECT * FROM dbMessage;");
        System.out.println("\nshowDbMessages :\n" + jsonString);
        Gson gson = new Gson();
        DbMessage[] objectList = gson.fromJson(jsonString, DbMessage[].class);
    }

    private static void showDbMessagesFromAUser() {
        int idUser = 11;
        String jsonString = Server.treatQuery("SELECT * FROM dbMessage WHERE authorId=" + idUser + ";");
        System.out.println("\nshowDbMessagesFromAUser :\n" + jsonString);
        Gson gson = new Gson();
        DbMessage[] objectList = gson.fromJson(jsonString, DbMessage[].class);
    }
}
