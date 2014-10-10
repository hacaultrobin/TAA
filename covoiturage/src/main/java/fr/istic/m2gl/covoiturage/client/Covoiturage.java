package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Covoiturage implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		// Create a text
		final TextBox area = new TextBox();
		area.setValue("2");
		RootPanel.get().add(area);

	}
}
