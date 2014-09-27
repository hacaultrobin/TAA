package fr.istic.m2gl.covoiturage.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * The class User - Mapped with the database.
 * @authors Anthony LHOMME & Robin HACAULT
 *
 */
@Entity
public class User {
	
	private int id;
	
	/* Surname and first name of the user */
	private String name;
	
	/* The car mapped to the user */
	private Car car;
	
	/* The event for which the user is involved */
	private Event event;
	
	public User () {}
	
	public User (String name) {
		this.name = name;
	}

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
	
	@ManyToOne
	public Car getCar() {
		return car;
	}

	public void setCar(Car c) {
		this.car = c;
	}

	@ManyToOne
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	/**
	 * @return Tell if the user is a driver
	 */
	@Transient /* --> No effect on the mapping */
	public boolean isDriver() {
		if (car != null) {
			User carDriver = car.getDriver();
			return carDriver != null && carDriver.getId() == this.id;
		}
		return false;
	}

}
