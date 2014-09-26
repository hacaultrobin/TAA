package fr.istic.m2gl.covoiturage.service;

import fr.istic.m2gl.covoiturage.domain.User;

public interface CarService {

	public void addCar(int id, String model, int nbSeat);
	public void removeCar();
	
	public void addUserAsDriver(User driver);
	public void removeUserAsDriver(User driver);
	
	public void addUserAsPassenger(User passenger);
	public void removeUserAsPassenger(User passenger);
	
}
