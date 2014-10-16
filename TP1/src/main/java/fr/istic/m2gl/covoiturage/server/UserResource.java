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

import fr.istic.m2gl.covoiturage.shared.Car;
import fr.istic.m2gl.covoiturage.shared.Event;
import fr.istic.m2gl.covoiturage.shared.User;

@Path("/users")
public class UserResource implements UserService {

	EntityManager manager;

	public UserResource() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		manager = factory.createEntityManager();
	}

	@GET
	@Path("search/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public User getUser(@PathParam("id") int id) {
		User user = manager.find(User.class, id);

		// break cycle
		breakCycle(user);

		return user;
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<User> getUsers() {
		@SuppressWarnings("unchecked")
		Collection<User> users = manager.createQuery("select u from User u").getResultList();
		
		// break cycle
		for (User user : users) {
			breakCycle(user);
		}
		
		return users;
	}
	
	private void breakCycle(User user) {
		// cycle event
		Event e = user.getEvent();
		if (e != null) {
			e.setParticipants(null);
		}
		
		// cycle car
		Car c = user.getCar();
		if (c != null) {
			if (user.isDriver()) {
				c.setDriver(null);
			} else {
				c.setUsersInCar(null);
			}
		}
	}

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void addUser(@QueryParam("name") String name) {
		User user = new User(name);

		EntityTransaction t = manager.getTransaction();
		t.begin();
		manager.persist(user);
		t.commit();
	}

	@DELETE
	@Path("delete/{id}")
	public void removeUser(@PathParam("id") int id) {
		User user = manager.find(User.class, id);
		if (user != null) {
			Event event = user.getEvent();
			Car car = user.getCar();
			
			EntityTransaction t = manager.getTransaction();
			t.begin();
			if (event != null) {
				event.removeParticipant(user);
			}
			if (car != null) {
				if (user.isDriver()) {
					car.setDriver(null);
				} else {
					car.removePassenger(user);
				}
			}
			manager.remove(user);
			t.commit();
		}
	}

	@POST
	@Path("event/")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void joinEvent(@QueryParam("idUser") int idUser, @QueryParam("idEvent") int idEvent) {
		User user = manager.find(User.class, idUser);
		Event event = manager.find(Event.class, idEvent);

		if (user != null && event != null) {
			EntityTransaction t = manager.getTransaction();
			t.begin();
			user.setEvent(event);
			event.addParticipant(user);
			t.commit();
		}
	}

	@DELETE
	@Path("event/delete/{id}")
	public void leaveEvent(@PathParam("id") int idUser) {
		User user = manager.find(User.class, idUser);
		if (user != null) {
			Event event = user.getEvent();

			EntityTransaction t = manager.getTransaction();
			t.begin();
			if (event != null) { event.removeParticipant(user); }
			user.setEvent(null);
			t.commit();
		}
	}

	@POST
	@Path("car/")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void joinCar(@QueryParam("idUser") int idUser, @QueryParam("idCar") int idCar, @QueryParam("isDriver") boolean isDriver) {
		User user = manager.find(User.class, idUser);
		Car car = manager.find(Car.class, idCar);

		if (user != null && car != null) {
			EntityTransaction t = manager.getTransaction();
			t.begin();
			user.setCar(car);
			if (isDriver) {
				car.setDriver(user);
			} else {
				car.addPassenger(user);
			}
			t.commit();
		}
	}

	@DELETE
	@Path("car/delete/{id}")
	public void leaveCar(@PathParam("id") int idUser) {
		User user = manager.find(User.class, idUser);
		if (user != null) {
			Car car = user.getCar();

			EntityTransaction t = manager.getTransaction();
			t.begin();
			if (car != null) { car.removePassenger(user); }
			user.setCar(null);;
			t.commit();
		}
	}
}
