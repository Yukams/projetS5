package tests.back;

public class QueryTest {
    public static void main(String[] args) {
        showDbUsers();
    }

    private static void showDbUsers() {
        MainBackTest.treatQuery("SELECT * FROM dbUser;");
    }
}
