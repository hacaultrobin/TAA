package fr.istic.m2gl.covoiturage.domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The class Event - Mapped with the database.
 * @authors Anthony LHOMME & Robin HACAULT
 *
 */
@Entity
public class Event {
	
	private int id;
	
	/* The date on which the event takes place */
	private Date date;
	
	/* The place of the event */
	private String place; // add coords gps ?
	
	/* The list of participants of the event */
	private Collection<User> participants;

	@GeneratedValue
	@Id
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.DATE)
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@OneToMany(mappedBy="event") /* Relation bidirect */
	public Collection<User> getParticipants() {
		return participants;
	}

	public void setParticipants(Collection<User> participants) {
		this.participants = participants;
	}
 
}
