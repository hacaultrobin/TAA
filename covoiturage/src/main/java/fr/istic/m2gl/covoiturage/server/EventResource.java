package fr.istic.m2gl.covoiturage.server;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.Response;

import fr.istic.m2gl.covoiturage.db.Car;
import fr.istic.m2gl.covoiturage.db.Event;
import fr.istic.m2gl.covoiturage.db.User;

@Path("/events")
public class EventResource implements EventService {
	
	EntityManager manager;
	
	public EventResource () {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		manager = factory.createEntityManager();
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<Event> getEvents() {
		@SuppressWarnings("unchecked")
		List<Event> events = manager.createQuery("SELECT e FROM Event e").getResultList();		
		return events;
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON})
	public Event getEvent(@PathParam("id") int id) {
		Event event = manager.find(Event.class, id);
		return event;
	}
	
	@GET
	@Path("/{id}/users")
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<User> getEventUsers(@PathParam("id") int id) {
		Event event = manager.find(Event.class, id);
		if (event != null) return event.getParticipants();
		return null;
	}
	
	@GET
	@Path("/{id}/cars")
	@Produces({MediaType.APPLICATION_JSON})
	public Collection<Car> getEventCars(@PathParam("id") int id) {
		Event event = manager.find(Event.class, id);
		if (event != null) {
			Collection<User> participants = event.getParticipants();
			Map<Integer, Car> eventCarMap = new HashMap<Integer, Car>();
			for (User u : participants) {
				eventCarMap.putIfAbsent(u.getCarId(), u.getCar());
			}
			return eventCarMap.values();
		}
		return null;
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public void addEvent(@QueryParam("date") Date d, @QueryParam("lieu") String lieu, @QueryParam("desc") String desc) {
		// TODO Auto-generated method stub

	}

	@DELETE
	@Path("/{id}")
	public void removeEvent(@PathParam("id") int idEvent) {
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Event event = manager.find(Event.class, idEvent);
		Collection<User> participants = event.getParticipants();
		for (User user : participants) {
			user.setEvent(null);
		}
		manager.remove(event);
		tx.commit();
	}

	@POST
	@Path("/{id}/join")
	@Consumes({MediaType.APPLICATION_JSON})
	public void joinEvent(@PathParam("id") int idEvent, @QueryParam("userid") int idPassenger) {
		// TODO Auto-generated method stub

	}

	@POST
	@Path("/{id}/joindriver")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void joinEventWithCar(@PathParam("id") int idEvent, @QueryParam("userid") int idDriver, @QueryParam("carid") int idCar) {
		// TODO Auto-generated method stub

	}

	@DELETE
	@Path("/{id}/removeuser/{userid}")
	public Response leaveEvent(@PathParam("id") int idEvent, @PathParam("userid") int idUser) {
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		Event event = manager.find(Event.class, idEvent);
		User user = manager.find(User.class, idUser);
		
		if (event != null && user != null && user.getEvent().getId() == idEvent) {
			
			boolean isDriver = user.isDriver();
			Car car = user.getCar();
			
			// If user is driver but there are other passengers in his car : The others have to leave first !
			if (isDriver && car.getUsersInCar().size() > 1) {
				// Response : Accepted but NOT DELETED
				tx.commit();
				return Response.status(202).tag("The user is a driver, but there are"
						+ " other passengers in his car. They have to leave first").build();
			}
			
			// Here, user is not a driver, or is a driver but he's alone in the car	
			
			// Remove user from the participants of the event, and from the car
			event.getParticipants().remove(user);
			user.getCar().getUsersInCar().remove(user);			
			user.setEvent(null);
			user.setCar(null);
			
			if (isDriver) { // Removes the car
				manager.remove(car);
			}
						
			// Ok response, user deleted, or user + car deleted if user was alone in the car and driver
			tx.commit();
			return Response.status(200).build();
			
		}		
		// Error response : Event not found or user to delete not in the event
		tx.commit();
		return Response.status(500).tag("Event not found or user to delete not in the event").build();
	}

}
