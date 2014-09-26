package fr.istic.m2gl.covoiturage.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * The class Car save in database.
 * @author Anthony LHOMME & Robin HACAULT
 *
 */
@Entity
public class Car {
	
	private int id;
	
	/* The model of car */
	private String model;
	
	/* The number of seat in the car (include seat of driver) */
	private int nbSeat;
	
	/* The driver */
	private User driver;
	
	/* The list of passenger in the car */
	private Collection<User> passenger;

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

	@OneToOne(mappedBy="driver")
	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	@OneToMany(mappedBy="passenger")
	public Collection<User> getPassenger() {
		return passenger;
	}

	public void setPassenger(Collection<User> passenger) {
		this.passenger = passenger;
	}
}