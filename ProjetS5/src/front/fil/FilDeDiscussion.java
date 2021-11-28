package front.fil;

import java.util.List;

import front.groups.Groupe;
import front.users.Utilisateur;

public class FilDeDiscussion {
	private int id;
	private String titre;
	private Groupe groupe;
	private Utilisateur author;
	private List<Message> messages;

	// Data sent by clients
	public FilDeDiscussion(int id, String titre, Groupe groupe, Utilisateur author, List<Message> messages) {
		this.id = id;
		this.titre = titre;
		this.groupe = groupe;
		this.author = author;
		this.messages = messages;
	}
	
	public static List<FilDeDiscussion> buildFilDeDiscussionListFromJSON(String json) {
		// Construire la liste a partir du JSON
		// Construction de l'auteur
		String jsonAuthor = null;
		Utilisateur author = Utilisateur.buildUserFromJSON(jsonAuthor);
		
		// Construction du groupe
		String jsonGroup = null;
		Groupe groupe = Groupe.buildGroupFromJSON(jsonGroup);
		
		// Construction d'un message
		String jsonMessage = null;
		Message message = Message.buildMessageFromJSON(jsonMessage);
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Groupe getGroupe() {
		return groupe;
	}

	public void setGroupe(Groupe groupe) {
		this.groupe = groupe;
	}

	public Utilisateur getAuthor() {
		return author;
	}

	public void setAuthor(Utilisateur author) {
		this.author = author;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
}
