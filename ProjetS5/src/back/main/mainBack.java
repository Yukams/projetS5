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
	private static final String DB_URL = "jdbc:mysql://localhost:3306/projetS5";
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

	// Remplis la base de donnee
	private static void fillDatabase() {
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			String sql = new String(Files.readAllBytes(Paths.get("/home/gecko/Code/Fac/Projet/projetS5/ProjetS5/src/back/main/database_setup.sql")));
			stmt.execute(sql);
			System.out.println("Database filled successfully...");
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	// Cree la base de donnee
	private static void createDatabase() {
		// Open a connection
		try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement stmt = conn.createStatement()
		) {
			String sqlDelete = "DROP DATABASE IF EXISTS projetS5;";
			stmt.execute(sqlDelete);
			String sqlCreate = "CREATE DATABASE projetS5;";
			stmt.execute(sqlCreate);
			System.out.println("Database created successfully...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
