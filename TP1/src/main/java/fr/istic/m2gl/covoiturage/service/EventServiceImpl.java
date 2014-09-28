package fr.istic.m2gl.covoiturage.service;

import java.util.Collection;
import java.util.Date;

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

	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Event getEvent(@PathParam("id") int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Event> getEvents() {
		// TODO Auto-generated method stub
		return null;
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
