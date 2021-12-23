package tests.back;

import java.sql.SQLException;

import com.google.gson.Gson;
import back.dbobjects.DbUser;

public class QueryTest {
    public static void main(String[] args) throws SQLException {
        System.out.println("Debut QueryTest");
        showDbUsers();
        System.out.println("[OK] showDbUsers");
        System.out.println("Fin QueryTest");
    }

    private static void showDbUsers() throws SQLException {
        // SQL call
        String jsonString = TestUtils.treatQuery("SELECT * FROM dbUser;");
        // Serialization from DB
        Gson gson = new Gson(); // Or use new GsonBuilder().create();
        DbUser[] objectList = gson.fromJson(jsonString, DbUser[].class); // deserializes json into objectList
    }
}
