package fr.istic.m2gl.covoiturage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Car {
	
	private int id;	
	private String model;	
	private int nbSeat;
	private User owner;

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

	public int getNbSeat() {
		return nbSeat;
	}

	public void setNbSeat(int nbSeat) {
		this.nbSeat = nbSeat;
	}

	@ManyToOne
	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
}