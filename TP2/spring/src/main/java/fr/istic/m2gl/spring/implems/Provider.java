package fr.istic.m2gl.spring.implems;

import fr.istic.m2gl.spring.interfaces.IProvider;


public class Provider implements IProvider {

	public void send(String string, String string2) {
		System.err.println(string + " " + string2);
		
	}

}
