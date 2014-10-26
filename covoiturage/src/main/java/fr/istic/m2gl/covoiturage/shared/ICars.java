package fr.istic.m2gl.covoiturage.shared;

import java.util.List;

/*
 * { "cars": [ ... ] }
 */
public interface ICars {

	void setCars(List<ICar> event);

	List<ICar> getCars();

}