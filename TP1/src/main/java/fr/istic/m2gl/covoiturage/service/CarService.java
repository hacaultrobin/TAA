package fr.istic.m2gl.covoiturage.service;

import java.util.Collection;

import fr.istic.m2gl.covoiturage.domain.Car;

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
