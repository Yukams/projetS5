package tests.back;

import java.sql.*;

class MainBackTest {
    private static final String DB_URL_MULTI_QUERY = "jdbc:mysql://localhost:3306/projetS5?allowMultiQueries=true";
    private static final String DB_URL_SINGLE_QUERY = "jdbc:mysql://localhost:3306/projetS5";
    private static final String USER = "root";
    private static final String PASS = "root";

    public static void treatQuery(String queryString) {
        try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
            Statement stmt = conn.createStatement()
        ) {
            ResultSet resultSet = stmt.executeQuery(queryString);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            System.out.println("[");
            while (resultSet.next()) {
                System.out.print("  { ");
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                if(resultSet.isLast()) { System.out.println(" }"); }
                else { System.out.println(" },"); }
            }
            System.out.println("]");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}