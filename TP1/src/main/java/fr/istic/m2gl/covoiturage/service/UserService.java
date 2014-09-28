package fr.istic.m2gl.covoiturage.service;

import fr.istic.m2gl.covoiturage.domain.Car;

public interface UserService {

	public void addUser(int id, String name);
	public void removeUser();
	
	public void setCar(Car c);
	public void removeCar();
	
}
