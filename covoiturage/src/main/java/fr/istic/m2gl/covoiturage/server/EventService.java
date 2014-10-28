package fr.istic.m2gl.covoiturage.server;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.core.Response;

import fr.istic.m2gl.covoiturage.db.Car;
import fr.istic.m2gl.covoiturage.db.Event;
import fr.istic.m2gl.covoiturage.db.User;

public interface EventService {
	
	/**
	 * @param id is the event to get
	 * @return the event designed by id
	 */
	public Event getEvent (int id);
	
	/**
	 * @param id is the event to get users
	 * @return the users of the event designed by id
	 */
	public Collection<User> getEventUsers (int id);
	
	/**
	 * @param id is the event to get cars
	 * @return the cars of the event designed by id
	 */
	public Collection<Car> getEventCars (int id);
	
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
	 * Create a new user and add him as a PASSENGER to the event if seats are available
	 * @param idEvent is the id of the event to join
	 * @param namePassenger is the name of the user to create and to add to the event as a passenger
	 * @return an HTTP Response, status 200 (added) or 202 (not added -> no seats)
	 */
	public Response joinEvent (int idEvent, String namePassenger);	
	
	/**
	 * Create a new user and a new car and add him as a DRIVER to the event, with his new car
	 * @param idEvent is the id of the event to join
	 * @param nameDriver is the name of the user to create and to add to the event as a driver
	 * @param modelCar is the model of the car drived by nameDriver to create and to add to the event
	 * @param nbSeatsCar is the nbSeats of the car
	 * @return an HTTP Response, status 200 (added) or 202 (not added -> no seats)
	 */
	public Response joinEventWithCar (int idEvent, String nameDriver, String modelCar, int nbSeatsCar);
	
	/**
	 * Delete user from e, and user's car if he's the driver 
	 * @param e The event to leave
	 * @param user is the participant (driver or passenger) who leave the event
	 */
	public Response leaveEvent (int idEvent, int idUser);
	
}
