package fr.istic.m2gl.covoiturage.domain;

import java.beans.Transient;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class Car - Mapped with the database
 * @author Anthony LHOMME & Robin HACAULT
 *
 */
@Entity
@XmlRootElement
public class Car {
	
	private int id;
	
	/* The model of car */
	private String model;
	
	/* The number of seat in the car (include seat of driver) */
	private int nbSeat;
	
	/* The driver */
	private User driver;
	
	/* The list of all users in the car, including thr driver */
	private Collection<User> usersInCar;
	
	public Car () {}
	
	public Car (String model, int nbSeat) {
		this.model = model;
		this.nbSeat = nbSeat;
	}

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

	@OneToOne
	public User getDriver() {
		return driver;
	}

	public void setDriver(User driver) {
		this.driver = driver;
	}

	/**
	 * @return Collection of all the users in the car, including the driver
	 */
	@OneToMany(mappedBy="car") /* Relation bidirect */
	public Collection<User> getUsersInCar() {
		return usersInCar;
	}

	public void setUsersInCar(Collection<User> usersInCar) {
		this.usersInCar = usersInCar;
	}
	
	@Transient
	public void addPassenger(User passenger) {
		usersInCar.add(passenger);
	}
	
	@Transient
	public void removePassenger(User passenger) {
		usersInCar.remove(passenger);
	}
}