package fr.istic.m2gl.covoiturage;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class User {
	
	private int id;	
	private String name;
	private Collection<Car> car;

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

	@OneToMany(mappedBy="owner")
	public Collection<Car> getCar() {
		return car;
	}

	public void setCar(Collection<Car> car) {
		this.car = car;
	}

}
