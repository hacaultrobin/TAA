package fr.istic.m2gl.covoiturage.server;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.istic.m2gl.covoiturage.db.Car;
import fr.istic.m2gl.covoiturage.db.User;

@Path("/cars")
public class CarResource implements CarService {

	EntityManager manager;
	
	public CarResource() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		manager = factory.createEntityManager();
	}

	@GET
	@Path("search/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Car getCar(@PathParam("id") int id) {
		Car car = manager.find(Car.class, id);
		return car;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<Car> getCars() {
		@SuppressWarnings("unchecked")
		Collection<Car> cars = manager.createQuery("select c from Car c").getResultList();		
		return cars;
	}

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void addCar(@QueryParam("model") String model, @QueryParam("nbSeat") int nbSeat) {
		Car c = new Car(model, nbSeat);

		EntityTransaction t = manager.getTransaction();
		t.begin();
		manager.persist(c);
		t.commit();
	}

	@DELETE
	@Path("delete/{id}")
	public void removeCar(@PathParam("id") int id) {
		Car c = manager.find(Car.class, id);
		if (c != null) {
			User driver = c.getDriver();
			Collection<User> passengers = c.getUsersInCar();
			
			EntityTransaction t = manager.getTransaction();
			t.begin();
			if (driver != null) { 
				driver.setCar(null);
			}
			if (passengers != null) {
				for (User p : passengers) {
					p.setCar(null);
				}
			}
			manager.remove(c);
			t.commit();
		}
	}

	@GET
	@Path("driver/")
	@Produces({MediaType.APPLICATION_JSON})
	public User getDriver(int id) {
		Car car = manager.find(Car.class, id);
		if (car != null) {
			User driver = car.getDriver();
			
			if (driver != null) {
				// break cycle
				driver.setCar(null);
				driver.setEvent(null);

				return driver;
			}
		}
		return null;		
	}
	
	@POST
	@Path("driver/")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void setDriver(@QueryParam("idCar") int idCar, @QueryParam("idDriver") int idDriver) {
		Car car = manager.find(Car.class, idCar);
		User driver = manager.find(User.class, idDriver);

		if (car != null && driver != null) {
			EntityTransaction t = manager.getTransaction();
			t.begin();
			car.setDriver(driver);
			driver.setCar(car);
			t.commit();
		}
	}

	@DELETE
	@Path("driver/delete/{id}")
	public void removeDriver(@PathParam("id") int idCar) {
		Car car = manager.find(Car.class, idCar);
		if (car != null) {
			User driver = car.getDriver();

			EntityTransaction t = manager.getTransaction();
			t.begin();
			car.setDriver(null);
			if (driver != null) { driver.setCar(null); }
			t.commit();
		}
	}

	@GET
	@Path("passenger/")
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<User> getPassengers(int id) {
		Car car = manager.find(Car.class, id);
		if (car != null) {
			Collection<User> passengers = car.getUsersInCar();
			
			if (passengers != null) {
				// break cycle
				for (User p : passengers) {
					p.setCar(null);
					p.setEvent(null);
				}
				
				return passengers;
			}
		}		
		return null;		
	}

	@POST
	@Path("passenger/")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void joinCar(@QueryParam("idCar") int idCar, @QueryParam("idUser") int idUser) {
		Car car = manager.find(Car.class, idCar);
		User passenger = manager.find(User.class, idUser);
		
		if (car != null && passenger != null) {
			EntityTransaction t = manager.getTransaction();
			t.begin();
			car.addPassenger(passenger);
			passenger.setCar(car);
			t.commit();
		}
	}

	@POST
	@Path("passenger/leave/")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void leaveCar(@QueryParam("idCar") int idCar, @QueryParam("idUser") int idUser) {
		Car car = manager.find(Car.class, idCar);
		User passenger = manager.find(User.class, idUser);
		
		if (car != null && passenger != null) {
			EntityTransaction t = manager.getTransaction();
			t.begin();
			car.removePassenger(passenger);
			passenger.setCar(null);
			t.commit();
		}
	}
}
