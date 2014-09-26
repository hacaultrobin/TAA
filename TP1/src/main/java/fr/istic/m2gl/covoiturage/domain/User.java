package fr.istic.m2gl.covoiturage.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * The class User save in database.
 * @author Anthony LHOMME & Robin HACAULT
 *
 */
@Entity
public class User {
	
	private int id;
	
	/* Surname and first name of the user */
	private String name;
	
	/* The user is the driver of the car */
	private Car driver;

	/* The user is passenger of a car */
	private Car passenger;
	
	/* The event whose user is involved */
	private Event event;

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
	
	@OneToOne
	public Car getDriver() {
		return driver;
	}

	public void setDriver(Car driver) {
		this.driver = driver;
	}

	@ManyToOne
	public Car getPassenger() {
		return passenger;
	}

	public void setPassenger(Car passenger) {
		this.passenger = passenger;
	}

	@ManyToOne
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

}
