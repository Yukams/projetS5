package back.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import back.api.Server;
import back.backobjects.users.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class mainBack {
	private static final String DB_URL_MULTI_QUERY = "jdbc:mysql://localhost:3306/projetS5?allowMultiQueries=true";
	private static final String DB_URL_SINGLE_QUERY = "jdbc:mysql://localhost:3306/projetS5";
	private static final String USER = "root";
	private static final String PASS = "toor";
	private static final int PORT = 9090;
	private static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(4);
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) throws IOException {
		// TODO
		// Etape 1 : Creer la database
		createDatabase();
		// Etape 2 : Peupler la database
		fillDatabase();
		// Etape 3 : Lancer l'application
		lancerApplication();
	}

	// Lance l'application
	private static void lancerApplication() throws IOException {
		ServerSocket incoming = new ServerSocket(PORT);

		while(true) {
			System.out.println("[SERVER] Waiting for client connection");
			Socket client = incoming.accept();
			System.out.println("[SERVER] Connected to client !");
			ClientHandler clientThread = new ClientHandler(client);
			clients.add(clientThread);

			pool.execute(clientThread);
		}

	}
	
	private static int connexionUser(String username, String password) {
		User user = (User) Server.connect(username, password);
		return user.getId();
	}

	// Filling database with default values
	private static void fillDatabase() {
		try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			String sqlFillDb = new String(Files.readAllBytes(Paths.get("ProjetS5/src/back/main/database_fill.sql")));
			stmt.execute(sqlFillDb);

			System.out.println("Database filled successfully.");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	// Cree la base de donnee
	private static void createDatabase() {
		// Open a connection
		try(Connection conn = DriverManager.getConnection(DB_URL_SINGLE_QUERY, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			// Create empty database
			stmt.execute("DROP DATABASE IF EXISTS projetS5;");
			stmt.execute("CREATE DATABASE projetS5;");

			System.out.println("Database created successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			// Create empty tables
			String sql = new String(Files.readAllBytes(Paths.get("ProjetS5/src/back/main/database_setup.sql")));
			stmt.execute(sql);

			System.out.println("Tables created successfully.");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
