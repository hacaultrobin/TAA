package fr.istic.m2gl.covoiturage.service;

import fr.istic.m2gl.covoiturage.domain.User;

public interface CarService {

	public void addCar(int id, String model, int nbSeat);
	public void removeCar(int id);
	
	public void setDriver(User driver);
	public void removeDriver();
	
}
