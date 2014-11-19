package fr.istic.m2gl.covoiturage.client;

import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class DialogAddEvent extends DialogBox {

	Covoiturage view;

	public DialogAddEvent(Covoiturage c) {
		
		this.view = c;

		/* Title of the dialog box */
		setText("Ajout d'un évènement");

		setAnimationEnabled(true);
		setGlassEnabled(true);
		setAutoHideEnabled(true);

		/* Place field */
		Label placeLabel = new Label("Lieu");
		final TextBox tPlace = new TextBox();
		tPlace.setWidth("120px");
		HorizontalPanel placePanel = new HorizontalPanel();
		placePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		placePanel.setSpacing(3);
		placePanel.add(placeLabel);
		placePanel.add(tPlace);

		/* Date fields */
		Label dateLabel = new Label("Date");		
		final DateBox date = new DateBox();
		date.setWidth("100px");
		HorizontalPanel datePanel = new HorizontalPanel();
		datePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		datePanel.setSpacing(3);
		datePanel.add(dateLabel);
		datePanel.add(date);

		/* Description */
		Label descLabel = new Label("Description");
		final TextArea tDesc = new TextArea();
		tDesc.setWidth("150px");
		tDesc.setHeight("45px");
		HorizontalPanel descPanel = new HorizontalPanel();
		descPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		descPanel.setSpacing(3);
		descPanel.add(descLabel);
		descPanel.add(tDesc);

		/* Add all in a vertical panel */
		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("100");
		panel.setWidth("300");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(placePanel);
		panel.add(datePanel);
		panel.add(descPanel);


		/* OK button */
		Button ok = new Button("Valider");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String eventPlace = tPlace.getText().trim();
				Date eventDate = date.getValue();
				String eventDesc = tDesc.getText().trim();				
				if (eventPlace == "") {
					Window.alert("Entrez un lieu correct");
				} else if (eventDate == null) {
					Window.alert("Entrez une date correcte");
				} else if (eventDesc == "") {
					Window.alert("Entrez une description correcte");
				} else {
					view.addEvent(eventDate, eventPlace, eventDesc);
					DialogAddEvent.this.hide();
				}          
			}
		});

		/* Add validate button */
		panel.add(ok);

		setWidget(panel);
	}



}
