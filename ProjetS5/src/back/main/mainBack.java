package back.main;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import back.users.Utilisateur;


public class mainBack {
	private static final String DB_URL_MULTI_QUERY = "jdbc:mysql://localhost:3306/projetS5?allowMultiQueries=true";
	private static final String DB_URL_SINGLE_QUERY = "jdbc:mysql://localhost:3306/projetS5";
	private static final String USER = "root";
	private static final String PASS = "root";
	private static Object FileUtils;

	public static void main(String[] args) {
		// TODO
		// Etape 1 : Creer la database
		createDatabase();
		// Etape 2 : Peupler la database
		fillDatabase();
		// Etape 3 : Lancer l'application
		lancerApplication();
		// test branche
	}

	// Lance l'application
	private static void lancerApplication() {
		// Etape 1 : En mode ecoute pasive
		// Etape 2 : Recevoir une demande du l'UI
		// Etape 3 : Traiter la demande
		/* ex :
		int id = 0;
		Utilisateur user = (Utilisateur) IUtilisateur.getUtilisateur(id);
		Set<Integer> set = user.getGroupesId();
		for (int groupeId : set) {
			Groupe groupe = (Groupe) IGroupe.getGroupe(id);
			groupe.getNom();
		}*/
	}
	
	private static int connexionUtilisateur(String username, String password) {
		Utilisateur user = (Utilisateur) back.api.Serveur.connect(username, password);
		return user.getId();
	}

	// Filling database with default values
	private static void fillDatabase() {
		try(Connection conn = DriverManager.getConnection(DB_URL_MULTI_QUERY, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			String sqlFillDb = new String(Files.readAllBytes(Paths.get("projetS5/ProjetS5/src/back/main/database_fill.sql")));
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
			String sql = new String(Files.readAllBytes(Paths.get("projetS5/ProjetS5/src/back/main/database_setup.sql")));
			stmt.execute(sql);

			System.out.println("Tables created successfully.");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}
