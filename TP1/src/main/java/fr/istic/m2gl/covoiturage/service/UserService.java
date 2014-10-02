package fr.istic.m2gl.covoiturage.service;

import java.util.Collection;

import fr.istic.m2gl.covoiturage.domain.User;

/**
 * The interface UserService
 * @authors Anthony LHOMME & Robin HACAULT
 *
 */
public interface UserService {

	/**
	 * @param id is the user to get
	 * @return the user designed by id
	 */
	public User getUser(int id);
	
	/**
	 * @return a collection of all users 
	 */
	public Collection<User> getUsers();
	
	/**
	 * Add a new user
	 * @param name of the user
	 */
	public void addUser(String name);
	
	/**
	 * Remove a car
	 * @param id is the user to remove
	 */
	public void removeUser(int id);
	
	/**
	 * Add an event to the user
	 * @param idUser is the user who join an event
	 * @param idEvent is the event in question
	 */
	public void joinEvent(int idUser, int idEvent);
	
	/**
	 * Remove the event of user
	 * @param idUser is the user who leave the event
	 */
	public void leaveEvent(int idUser);
	
	/**
	 * Add a car to the user (as a driver or as a passenger)
	 * @param idUser is the user who join a car
	 * @param idCar is the car in question
	 * @param isDriver designed if the user is driver or not
	 */
	public void joinCar(int idUser, int idCar, boolean isDriver);
	
	/**
	 * Remove the car of the user
	 * @param idUser is the user who leave the car
	 */
	public void leaveCar(int idUser);
	
}
