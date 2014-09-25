package fr.istic.m2gl.covoiturage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class User {
	
	private int id;	
	private String name;
	private boolean isConducteur;
	private Car car;

	@GeneratedValue
	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isConducteur() {
		return isConducteur;
	}

	public void setConducteur(boolean isConducteur) {
		this.isConducteur = isConducteur;
	}
	
	@OneToOne
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

}
