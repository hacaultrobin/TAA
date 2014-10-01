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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import fr.istic.m2gl.covoiturage.domain.Event;

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
		@SuppressWarnings("unchecked")
		List<Event> events = manager.createQuery("SELECT e FROM Event e").getResultList();
		for (Event event : events) {
			event.setParticipants(null); // TODO : Problème de cycle (relation bidirect)
		}
		return events;
	}

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Event getEvent(@PathParam("id") int id) {
		EntityManager manager = getEntityManager();
		Event event = manager.find(Event.class, id);
		if (event != null) event.setParticipants(null); // TODO : Problème de cycle (relation bidirect)		
		return event;
	}

	@POST
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public void addEvent(@QueryParam("date") Date d, @QueryParam("lieu") String lieu, @QueryParam("desc") String desc) {
		// TODO Auto-generated method stub

	}

	@DELETE
	@Path("/{id}")
	public void removeEvent(@PathParam("id") int idEvent) {
		// TODO Auto-generated method stub

	}

	@POST
	@Path("/{id}/join")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
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
	public void leaveEvent(@PathParam("id") int idEvent, @PathParam("userid") int idUser) {
		// TODO Auto-generated method stub

	}

}
