package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import fr.istic.m2gl.covoiturage.shared.IUser;

/**
 * Renders a Cell which represents an user, in the users list
 */
public class UserCell extends AbstractCell<IUser> {

	/**
	 * The HTML templates used to render a Cell representing an user
	 */
	interface Templates extends SafeHtmlTemplates {		
		@SafeHtmlTemplates.Template("<div class='users-list'>"
								  	+ "<div class='user-name'>{0}</div>"
								  	+ "<div class='user-id'> ({1})</div>"
								  	+ "<div class='user-car'>Voiture {2}</div>"
								  + "</div>")
		SafeHtml makeUserCell(String username, int userid, int usercar);
	}

	/**
	 * Create singleton instances of the templates used to render the cell.
	 */
	private static Templates templates = GWT.create(Templates.class);

	@Override
	public void render(Context context, IUser value, SafeHtmlBuilder sb) {
		if (value != null && value.getCarId() != null) {
			// Use the template to create the HTML of an user Cell
			SafeHtml user_cell_rendered = templates.makeUserCell(value.getName(), value.getId(), value.getCarId());
			sb.append(user_cell_rendered);

		}		
	}
}