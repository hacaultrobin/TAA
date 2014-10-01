package fr.istic.m2gl.covoiturage.service;

import java.util.Collection;
import java.util.Date;

import fr.istic.m2gl.covoiturage.domain.Event;

public interface EventService {
	
	/**
	 * @param id is the event to get
	 * @return the event designed by id
	 */
	public Event getEvent (int id);
	
	/**
	 * @return a collection of the available events
	 */
	public Collection<Event> getEvents();

	/**
	 * Add a new event
	 * @param d is the date of the event
	 * @param lieu indicates where it takes place
	 * @param desc is a short description of the event
	 */
	public void addEvent(Date d, String lieu, String desc);
	
	/**
	 * Remove an event, all users leaves it
	 * @param e is the event to remove
	 */
	public void removeEvent(int idEvent);	
	
	/**
	 * Add passenger to the event e if cars are available
	 * @param e is the event to join
	 * @param passenger is the user to add to the event
	 */
	public void joinEvent (int idEvent, int idDriver);
	
	/**
	 * Add driver to the event e, who is the driver of the car c
	 * @param e is the event to join
	 * @param driver is the user to add to the event and who will drive the car c
	 * @param c is the car added to the event
	 */
	public void joinEventWithCar (int idEvent, int idDriver, int idCar);
	
	/**
	 * Delete user from e, and user's car if he's the driver 
	 * @param e The event to leave
	 * @param user is the participant (driver or passenger) who leave the event
	 */
	public void leaveEvent (int idEvent, int idUser);
	
}
