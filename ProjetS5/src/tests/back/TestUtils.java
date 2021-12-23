package tests.back;

import com.google.gson.Gson;

import java.sql.*;

class TestUtils {
    private static final String DB_URL_MULTI_QUERY = "jdbc:mysql://localhost:3306/projetS5?allowMultiQueries=true";
    private static final String DB_URL_SINGLE_QUERY = "jdbc:mysql://localhost:3306/projetS5";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static String treatQuery(String queryString) {
        try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
            Statement stmt = conn.createStatement()
        ) {
            ResultSet resultSet = stmt.executeQuery(queryString);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            StringBuilder jsonString = new StringBuilder();

            jsonString.append("[");
            while (resultSet.next()) {
                jsonString.append("  { ");
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) jsonString.append(", ");
                    String columnValue = resultSet.getString(i);
                    jsonString.append("\"" + rsmd.getColumnName(i) + "\": \"" + columnValue + "\"");
                }
                if(resultSet.isLast()) { jsonString.append(" }"); }
                else { jsonString.append(" },"); }
            }
            jsonString.append("]");

            return jsonString.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}