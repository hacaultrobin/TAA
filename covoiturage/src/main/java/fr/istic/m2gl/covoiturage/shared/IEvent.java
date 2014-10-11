package fr.istic.m2gl.covoiturage.shared;

import java.util.Date;

/*
 * { "id":1, "place":"Rennes", ... }
 * GWT JSON <--> AUTOBEAN : Getter/setter suffix equivalent to the JSON field name
 */

public interface IEvent {
	
	public int getId();
	
	public void setId(int id);
	
	public Date getDate();
	
	public void setDate(Date date);
	
	public String getPlace();
	
	public void setPlace(String place);
	
	public String getDescription();

	public void setDescription(String description);

	public IUsers getParticipants();

	public void setParticipants(IUsers user);

	//public void addParticipant(IUser participant);
	
	//public void removeParticipant(IUser participant);

}
