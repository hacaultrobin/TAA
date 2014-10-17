package fr.istic.m2gl.covoiturage.shared;

import java.util.List;

/*
 * { "event": [ ... ] }
 * Getter/setter suffix equivalent to the field name
 */

public interface IEvents {
	
	void setEvents(List<IEvent> event);
	
	List<IEvent> getEvents();
	
}