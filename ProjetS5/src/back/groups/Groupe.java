package back.groups;

import java.util.List;

public abstract class Groupe implements IGroupe {
	private String nom;
	private int id;
	private GroupeType groupeType;
	private List<Integer> etudiantsId;

	protected Groupe(String nom, GroupeType groupeType) {
		this.nom = nom;
		this.groupeType = groupeType;
	}

	@Override
	public List<Integer> getEtudiantsId() {
		return this.etudiantsId;
	}

	@Override
	public int getNbEtudiants() throws EmptyListException {
		if (this.isEmpty()) {
			throw new EmptyListException();
		}
		return this.etudiantsId.size();
	}

	@Override
	public String getNom() {
		return this.nom;
	}

	@Override
	public GroupeType getGroupeType() {
		return this.groupeType;
	}

	@Override
	public boolean isEmpty() {
		return this.etudiantsId.isEmpty();
	}

	@Override
	public int getId() {
		return this.id;
	}

}
