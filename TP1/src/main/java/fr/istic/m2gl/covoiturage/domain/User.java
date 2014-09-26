package fr.istic.m2gl.covoiturage.domain;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class User {
	
	private int id;	
	private String name;
	private Car driver;
	private Car passenger;
	private Collection<Event> events;

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

	@ManyToMany
	public Collection<Event> getEvents() {
		return events;
	}

	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

}
