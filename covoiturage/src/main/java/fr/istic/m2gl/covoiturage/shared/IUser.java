package fr.istic.m2gl.covoiturage.shared;

/*
 * { "id":1, "name":"Toto" }
 * GWT JSON <--> AUTOBEAN : Getter/setter suffix equivalent to the JSON field name
 */

public interface IUser {
	
	public int getId();

	public void setId(int id);

	public String getName();

	public void setName(String name);
	
	//public Car getCar();

	//public void setCar(Car c);

	//public IEvent getEvent();

	//public void setEvent(IEvent event);

	//public boolean isDriver();

}
