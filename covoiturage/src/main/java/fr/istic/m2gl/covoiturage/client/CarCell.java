package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import fr.istic.m2gl.covoiturage.shared.ICar;

/**
 * Renders a Cell which represents an user, in the users list
 */
public class CarCell extends AbstractCell<ICar> {

	/**
	 * The HTML templates used to render a Cell representing an user
	 */
	interface Templates extends SafeHtmlTemplates {		
		@SafeHtmlTemplates.Template("<div class='cars-list'>"
								  	+ "<div class='car-id'>Voiture {0}</div>"
								  	+ "<div class='car-seats'> ({1}, {2} sièges)</div>"
								  	+ "<div class='car-available-seats'>Places disponibles : {3}</div>"
								  + "</div>")
		SafeHtml makeCarCell(int idCar, String carModel, int carNbSeat, int carNbAvailableSeat);
	}

	/**
	 * Create singleton instances of the templates used to render the cell.
	 */
	private static Templates templates = GWT.create(Templates.class);

	@Override
	public void render(Context context, ICar value, SafeHtmlBuilder sb) {
		if (value != null) {
			// Use the template to create the HTML of an user Cell
			SafeHtml user_cell_rendered = templates.makeCarCell(value.getId(), value.getModel(), value.getNbSeat(), value.getNbAvailableSeat());
			sb.append(user_cell_rendered);
		}		
	}
}