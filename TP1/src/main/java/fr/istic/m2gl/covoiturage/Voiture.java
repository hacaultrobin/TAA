package fr.istic.m2gl.covoiturage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//import javax.persistence.OneToOne;


@Entity
public class Voiture {
	
	private int id;	
	private String model;	
	private int nbPlaces;	
	//private User conducteur;

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

	// TODO One To One - foreign key conducteur
	/*public User getConducteur() {
		return conducteur;
	}

	public void setConducteur(User conducteur) {
		this.conducteur = conducteur;
	}*/

}
