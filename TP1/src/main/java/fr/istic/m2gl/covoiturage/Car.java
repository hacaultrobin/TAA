package fr.istic.m2gl.covoiturage;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Car {
	
	private int id;	
	private String model;	
	private int nbPlaces;	
	private User conducteur;
	private List<User> passagers;

	@GeneratedValue
	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getNbPlaces() {
		return nbPlaces;
	}

	public void setNbPlaces(int nbPlaces) {
		this.nbPlaces = nbPlaces;
	}

	@OneToOne /* Relation 1 --> 1 */
	public User getConducteur() {
		return conducteur;
	}

	/**
	 * @param conducteur
	 */
	public void setConducteur(User conducteur) {
		this.conducteur = conducteur;
	}
	
	/**
	 * @return Liste des passagers
	 */
	@OneToMany /* Relation 0..1 --> 0..* */
	public List<User> getPassagers() {
		return passagers;
	}

	/**
	 * Set la liste des passagers
	 * @param passagers : Nouvelle liste de passagers
	 */
	public void setPassagers(List<User> passagers) {
		this.passagers = passagers;
	}
	
	// + : addPassager, removePassager ...

}