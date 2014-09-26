package fr.istic.m2gl.covoiturage.service;

import fr.istic.m2gl.covoiturage.domain.Car;
import fr.istic.m2gl.covoiturage.domain.Event;

public interface UserService {

	public void addUser(int id, String name);
	public void removeUser();
	
	public void addCarAsDriver(Car c);
	public void removeCarAsDriver(Car c);
	
	public void addCarAsPassenger(Car c);
	public void removeCarAsPassenge(Car c);
	
	public void addEventAsParticipant(Event e);
	public void removeEventAsParticipant(Event e);
	
}
