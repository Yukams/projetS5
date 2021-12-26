package tests.back;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import back.api.Server;
import back.backobjects.groups.IGroup;
import back.frontobjects.FrontMessage;
import back.frontobjects.FrontThread;
import back.frontobjects.FrontUser;
import com.google.gson.Gson;
import back.dbobjects.*;
import front.fil.Message;

import static back.main.mainBack.gson;

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

    private static void showDbThreadsForAUser() {
        int idUser = 11;

        // Get Groups for the user
        String jsonString = Server.treatQuery("SELECT g.id, g.name FROM dbLinkUserGroup JOIN dbGroup g ON id WHERE userId=" + idUser + " AND dbLinkUserGroup.groupId=g.id;");
        System.out.println(jsonString);
        DbGroup[] objectList = gson.fromJson(jsonString, DbGroup[].class);

        // Transform groups into a JSON List
        StringBuilder groupList = new StringBuilder("(");
        for(DbGroup group: objectList) {
            groupList.append("'").append(group.id).append("',");
        }
        groupList.setLength(groupList.length() - 1);
        groupList.append(")");

        System.out.println("\nshowDbMessagesFromAUser groupList :\n" + groupList);

        // Get all Threads for the said User
        jsonString = Server.treatQuery("SELECT * FROM dbThread WHERE groupId IN " + groupList + " OR authorId=" + idUser);
        DbThread[] dbThreadList = gson.fromJson(jsonString, DbThread[].class);

        List<FrontThread> threadsList = new ArrayList<>();
        // Construct each thread
        for(DbThread thread: dbThreadList) {
            List<FrontMessage> messagesList = new ArrayList<>();
            // Get all messages related to the thread in date order (older first)
            jsonString = Server.treatQuery("SELECT m.id, m.authorId, m.text, m.date FROM dbLinkMessageThread JOIN dbMessage m ON id WHERE threadId=" + thread.id + " ORDER BY m.date;");
            System.out.println("\nshowDbMessagesForAThread :\n" + jsonString);
            DbMessage[] dbMessageList = gson.fromJson(jsonString, DbMessage[].class);

            // Construct each message
            for(DbMessage message: dbMessageList) {
                // Get message user
                jsonString = Server.treatQuery("SELECT * FROM dbUser WHERE id=" + message.authorId + ";");
                DbUser[] dbUser = gson.fromJson(jsonString, DbUser[].class);

                // Construct author
                FrontUser user = new FrontUser(dbUser[0].name, dbUser[0].surname, dbUser[0].id);

                // Build status (later)
                String status = "NOT_READ";

                FrontMessage m = new FrontMessage(message.id, user, message.text, message.date, status);
                messagesList.add(m);
            }

            // Add the thread to the thread list
            threadsList.add(new FrontThread(thread.id, thread.title, messagesList));
        }

        System.out.println("\nshowDbThreadsForAUser :\n" + gson.toJson(threadsList));
        //Gson gson = new Gson();
        //DbMessage[] objectList = gson.fromJson(jsonString, DbMessage[].class);
    }
}
