package fr.istic.m2gl.covoiturage.service;

import java.util.Date;

import fr.istic.m2gl.covoiturage.domain.User;

public interface EventService {

	public void addEvent(int id, Date d, String lieu);
	public void removeEvent();
	
	public void addUserAsParticipant(User p);
	public void removeUserAsParticipant(User p);
	
}
