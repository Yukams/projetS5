package front.fil;

import java.util.Date;
import java.util.List;

import front.users.Utilisateur;

public class Message {
	private Utilisateur author;
	private int id;
	private String contenu;
	private String date;
	private String color;

	public Message(Utilisateur author, int id, String contenu, String date, String color) {
		this.author = author;
		this.id = id;
		this.contenu = contenu;
		this.date = date;
		this.color = color;
	}

	public static Message buildMessageFromJSON(String jsonMessage) {
		// Construire le message a partir du JSON
		// Construction de l'auteur
		String jsonAuthor = null;
		Utilisateur author = Utilisateur.buildUserFromJSON(jsonAuthor);
		return null;
	}

	public Utilisateur getAuthor() {
		return author;
	}

	public void setAuthor(Utilisateur author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenu() {
		return contenu;
	}

	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	
}
