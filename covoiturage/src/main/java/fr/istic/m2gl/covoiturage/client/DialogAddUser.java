package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.istic.m2gl.covoiturage.shared.IEvent;

public class DialogAddUser extends DialogBox {
	
	Covoiturage view;
	IEvent ev;

	TextBox tModel;
	IntegerBox ibNbSeats;

	public DialogAddUser(final boolean isDriver, final Covoiturage view, final IEvent event) {
		
		this.view = view;
		this.ev = event;

		/* Title of the dialog box */
		if (isDriver) {
			setText("Ajout d'un conducteur");
		} else {
			setText("Ajout d'un passager");
		}		

		setAnimationEnabled(true);
		setGlassEnabled(true);
		setAutoHideEnabled(true);

		/* Name field */
		Label nameLabel = new Label("Nom");
		final TextBox tName = new TextBox();
		tName.setWidth("120px");
		HorizontalPanel namePanel = new HorizontalPanel();
		namePanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		namePanel.setSpacing(3);
		namePanel.add(nameLabel);
		namePanel.add(tName);

		/* Add all in a vertical panel */
		VerticalPanel panel = new VerticalPanel();
		panel.setHeight("100");
		panel.setWidth("300");
		panel.setSpacing(10);
		panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		panel.add(namePanel);

		if (isDriver) {
			/* Car - Title */
			Label carTitle = new Label("Voiture");
			carTitle.getElement().getStyle().setFontWeight(FontWeight.BOLD);

			/* Car - Model */
			Label carModelLabel = new Label("Modèle");
			tModel = new TextBox();
			tModel.setWidth("110px");
			HorizontalPanel carModelPanel = new HorizontalPanel();
			carModelPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			carModelPanel.setSpacing(3);
			carModelPanel.add(carModelLabel);
			carModelPanel.add(tModel);

			/* Car - Nb seats */
			Label carNbSeatsLabel = new Label("Nombre de sièges");
			ibNbSeats = new IntegerBox();
			ibNbSeats.setWidth("50px");
			HorizontalPanel carNbSeatsPanel = new HorizontalPanel();
			carNbSeatsPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
			carNbSeatsPanel.setSpacing(5);
			carNbSeatsPanel.add(carNbSeatsLabel);
			carNbSeatsPanel.add(ibNbSeats);



			/* Add all in the vertical panel */
			panel.add(carTitle);
			panel.add(carModelPanel);
			panel.add(carNbSeatsPanel);
		}

		/* OK button */
		Button ok = new Button("Valider");
		ok.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				String userName = tName.getText().trim();
				if (userName == "") {
					Window.alert("Entrez un nom d'utilisateur correct");
				} else if (isDriver) {
					String carModel = tModel.getText().trim();
					String carNbSeats = ibNbSeats.getText().trim();
					if (carModel == "") {
						Window.alert("Entrez un modèle de voiture correct");
					} else {
						try {
							int nbSeats = Integer.parseInt(carNbSeats);
							if (nbSeats < 1) {
								Window.alert("Entrez un nombre de sièges correct");
							} else {
								view.addDriverToEvent(ev, userName, carModel, nbSeats);
							}
						} catch (NumberFormatException e) {
							Window.alert("Entrez un nombre de sièges correct");
						}        			  
					}
				} else {
					view.addPassengerToEvent(ev, userName);
					DialogAddUser.this.hide();
				}              
			}
		});

		/* Add validate button */
		panel.add(ok);

		setWidget(panel);
	}
	
	

}
