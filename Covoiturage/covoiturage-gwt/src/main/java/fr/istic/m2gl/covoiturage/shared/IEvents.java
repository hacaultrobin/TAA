package fr.istic.m2gl.covoiturage.shared;

import java.util.List;

/*
 * { "events": [ ... ] }
 */

public interface IEvents {
	
	void setEvents(List<IEvent> event);
	
	List<IEvent> getEvents();
	
}