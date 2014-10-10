package fr.istic.m2gl.covoiturage.server;

import java.util.Collection;

import fr.istic.m2gl.covoiturage.shared.Car;
import fr.istic.m2gl.covoiturage.shared.User;

/**
 * The interface CarService
 * @authors Anthony LHOMME & Robin HACAULT
 *
 */
public interface CarService {

	/**
	 * @param id is the car to get
	 * @return the car designed by id
	 */
	public Car getCar(int id);
	
	/**
	 * @return a collection of all the available car
	 */
	public Collection<Car> getCars();
	
	/**
	 * Add a new car
	 * @param model is a short description of the car
	 * @param nbSeat indicates the number of seat available in the car, including the driver's seat
	 */
	public void addCar(String model, int nbSeat);
	
	/**
	 * Remove a car
	 * @param id is the car to remove
	 */
	public void removeCar(int id);
	
	/**
	 * Get the driver of car
	 * @param id is the car to get the driver
	 * @return the driver of car designed by id
	 */
	public User getDriver(int id);
	
	/**
	 * Add a user as driver of the car
	 * @param idCar is the car to add driver
	 * @param idDriver is the user who drives the car
	 */
	public void setDriver(int idCar, int idDriver);
	
	/**
	 * Remove the driver of the car
	 * @param idCar is the car to remove driver
	 */
	public void removeDriver(int idCar);
	
	/**
	 * Get the list of passenger, of the car
	 * @param id is the car to get the list of passenger
	 * @return the list of passenger,of the car designed by id
	 */
	public Collection<User> getPassengers(int id);
	
	/**
	 * Add a passenger to the car
	 * @param idCar is the car to add passenger
	 * @param idUser the passenger of the car
	 */
	public void joinCar(int idCar, int idUser);
	
	/**
	 * Remove a passenger of the car
	 * @param idCar is the car to remove passenger
	 * @param idUser the passenger of the car
	 */
	public void leaveCar(int idCar, int idUser);
	
}
