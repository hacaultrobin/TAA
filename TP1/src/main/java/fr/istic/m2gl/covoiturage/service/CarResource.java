package fr.istic.m2gl.covoiturage.service;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.istic.m2gl.covoiturage.domain.Car;

@Path("/cars")
public class CarResource implements CarService {

	EntityManager manager;
	
	public CarResource() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		manager = factory.createEntityManager();
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Car getCar(int id) {
		return manager.find(Car.class, id);
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<Car> getCars() {
		return manager.createQuery("SELECT c FROM Car c").getResultList();
	}

	public void addCar(String model, int nbSeat) {
		// TODO Auto-generated method stub
		
	}

	public void removeCar(int id) {
		// TODO Auto-generated method stub
		
	}

	public void setDriver(int idCar, int idDriver) {
		// TODO Auto-generated method stub
		
	}

	public void removeDriver(int idCar) {
		// TODO Auto-generated method stub
		
	}

	public void joinCar(int idCar, int idUser) {
		// TODO Auto-generated method stub
		
	}

	public void leaveCar(int idCar, int idUser) {
		// TODO Auto-generated method stub
		
	}

}
