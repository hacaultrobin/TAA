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
 * The class Event save in database.
 * @author Anthony LHOMME & Robin HACAULT
 *
 */
@Entity
public class Event {
	
	private int id;
	
	/* The date on which the event takes place */
	private Date date;
	
	/* The place of event */
	private String place; // add coords gps ?
	
	/* The list of participant of event */
	private Collection<User> participant;

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
	
	public String getPLace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}

	@OneToMany(mappedBy="events")
	public Collection<User> getParticipant() {
		return participant;
	}

	public void setParticipant(Collection<User> participant) {
		this.participant = participant;
	}
 
}
