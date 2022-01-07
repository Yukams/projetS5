package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainBack {
	/* Database connection */
	public static final String DB_URL_MULTI_QUERY = "jdbc:mysql://localhost:3306/projetS5?allowMultiQueries=true";
	private static final String DB_BEFORE_CREATE = "jdbc:mysql://localhost:3306";
	public static final String USER = "root";
	public static final String PASS = "";

	/* Client connection */
	private static final int PORT = 9090;
	static ArrayList<ClientHandler> clients = new ArrayList<>();
	private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public static void main(String[] args) throws IOException {
		// Create database
		createDatabase();
		// Fill database with tables and default values
		fillDatabase();
		// Application is launching
		launchApplication();
	}

	// Entry point for the program
	private static void launchApplication() throws IOException {
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

	// Filling database with default values
	private static void fillDatabase() {
		try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			System.out.println("[SERVER] Filling database if needed");
			InputStream file = mainBack.class.getResourceAsStream("database_setup.sql");
			assert file != null;
			Scanner sc = new Scanner(file);
			StringBuilder sb = new StringBuilder();
			while(sc.hasNext()){
				sb.append(sc.nextLine());
			}
			String sql = sb.toString();
			stmt.execute(sql);

			System.out.println("Tables created successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Create and fill the database if it doesn't exist
	private static void createDatabase() {
		try(Connection conn = DriverManager.getConnection(DB_BEFORE_CREATE, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			System.out.println("[SERVER] Creating database if needed");
			// TODO => DROP FOR TESTING PURPOSE, COMMENT IT OTHERWISE
			stmt.execute("DROP DATABASE IF EXISTS projetS5;");
			stmt.execute("CREATE DATABASE IF NOT EXISTS projetS5;");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
