package back.groups;

import java.util.List;

public interface IGroupe {
	public String getNom();

	public List<Integer> getEtudiantsId();

	public int getNbEtudiants() throws EmptyListException;

	public GroupeType getGroupeType();

	public boolean isEmpty();

	public int getId();

	public static IGroupe createGroupe(String nom, GroupeType groupeType, List<Integer> etudiantsId) {
		return back.api.Serveur.createGroupe(nom, groupeType, etudiantsId);
	}

	public static IGroupe modifyGroupe(List<Integer> etudiantsId, int groupeId) {
		return back.api.Serveur.modifyGroupe(etudiantsId, groupeId);
	}

	public static IGroupe deleteGroupe(int groupeId) {
		return back.api.Serveur.deleteGroupe(groupeId);
	}

	public static IGroupe getGroupe(int groupeId) {
		return back.api.Serveur.getGroupe(groupeId);
	}
}
