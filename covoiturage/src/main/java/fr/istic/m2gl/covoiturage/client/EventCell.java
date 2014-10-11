package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.safehtml.client.SafeHtmlTemplates;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

import fr.istic.m2gl.covoiturage.shared.IEvent;

/**
 * Renders a Cell which represents an event, in the events list
 */
public class EventCell extends AbstractCell<IEvent> {

	/**
	 * The HTML templates used to render a Cell representing an event
	 */
	interface Templates extends SafeHtmlTemplates {		
		@SafeHtmlTemplates.Template("<div>"
								  + 	"<span class='event-place'>{0}</span> - <span>{1}</span>"
								  + "</div>"
								  + "<div class='event-desc'>{2}</div>")
		SafeHtml makeEventCell(String place, String date, String desc);		
	}

	/**
	 * Create a singleton instance of the templates used to render the cell.
	 */
	private static Templates templates = GWT.create(Templates.class);

	@Override
	public void render(Context context, IEvent value, SafeHtmlBuilder sb) {
		if (value != null) {
			// Use the template to create the HTML of an event Cell
			String date = DateTimeFormat.getFormat("dd/MM/yyyy à HH:mm").format(value.getDate());
			SafeHtml event_cell_rendered = templates.makeEventCell(value.getPlace(), date, value.getDescription());
			sb.append(event_cell_rendered);
		}		
	}
}