package fr.istic.m2gl.covoiturage.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.istic.m2gl.covoiturage.domain.Car;
import fr.istic.m2gl.covoiturage.domain.Event;
import fr.istic.m2gl.covoiturage.domain.User;

@Path("/events")
public class EventServiceImpl implements EventService {
	
	/**
	 * @return An Entity manager used to map objets from the database
	 */
	private EntityManager getEntityManager () {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		return manager;
	}
	
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Event> getEvents() {
		EntityManager manager = getEntityManager();
		List<Event> events = manager.createQuery("SELECT e FROM Event e").getResultList();
		events.get(0).setParticipants(null); // TODO : Problème de cycle (relation bidirect)
		return events;
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Event getEvent(@PathParam("id") int id) {
		EntityManager manager = getEntityManager();
		Event event = manager.find(Event.class, id);
		event.setParticipants(null); // TODO : Problème de cycle (relation bidirect)
		return event;
	}

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void addEvent(Date d, String lieu, String desc) {
		// TODO Auto-generated method stub

	}

	@DELETE
	@Path("/{id}")
	public void removeEvent(Event e) {
		// TODO Auto-generated method stub

	}

	@POST
	@Path("/join")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void joinEvent(Event e, User passenger) {
		// TODO Auto-generated method stub

	}

	@POST
	@Path("/joindriver")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void joinEventWithCar(Event e, User driver, Car c) {
		// TODO Auto-generated method stub

	}

	@DELETE
	@Path("/leave/{id}")
	public void leaveEvent(Event e, User user) {
		// TODO Auto-generated method stub

	}

}
