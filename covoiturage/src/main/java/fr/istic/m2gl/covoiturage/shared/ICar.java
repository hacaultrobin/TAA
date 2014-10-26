package fr.istic.m2gl.covoiturage.shared;

public interface ICar {
	
	public int getId();

	public void setId(int id);

	public String getModel();

	public void setModel(String model);

	public int getNbSeat();

	public void setNbSeat(int nbSeat);
	
	public int getNbAvailableSeat();
	
}