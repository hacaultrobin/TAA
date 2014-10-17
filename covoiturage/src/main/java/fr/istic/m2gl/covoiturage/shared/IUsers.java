package fr.istic.m2gl.covoiturage.shared;

import java.util.List;

/*
 * { "user": [ ....] }
 * GWT JSON <--> AUTOBEAN : Getter/setter suffix equivalent to the JSON field name
 */

public interface IUsers {
	
	void setUsers(List<IUser> user);
	
	List<IUser> getUsers();

}
