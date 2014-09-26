package fr.istic.m2gl.covoiturage;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Event {
	
	private int id;
	private Date date;
	private String lieu; // add coords gps ?
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
	
	public String getLieu() {
		return lieu;
	}
	
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	@ManyToMany(mappedBy="events")
	public Collection<User> getParticipant() {
		return participant;
	}

	public void setParticipant(Collection<User> participant) {
		this.participant = participant;
	}
 
}
