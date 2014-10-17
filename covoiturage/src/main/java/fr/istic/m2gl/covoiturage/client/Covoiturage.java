package fr.istic.m2gl.covoiturage.client;


import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import fr.istic.m2gl.covoiturage.shared.IEvent;
import fr.istic.m2gl.covoiturage.shared.IEvents;
import fr.istic.m2gl.covoiturage.shared.IUser;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Covoiturage implements EntryPoint {

	private static final String REST_API_URL = GWT.getHostPageBaseURL() + "covoiturage/rest";

	private CellList<IEvent> eventsList;	
	private LayoutPanel layout_right;
	private CellList<IUser> usersList;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get();

		LayoutPanel layoutPanel = new LayoutPanel();		
		layoutPanel.setSize("100%", "600px");

		ProvidesKey<IEvent> eventKeyProvider = new ProvidesKey<IEvent>() {
			public Object getKey(IEvent item) {
				return (item == null) ? null : item.getId();
			}
		};    
		// Create a CellList using the keyProvider, for the events list
		eventsList = new CellList<IEvent>(new EventCell(), eventKeyProvider);
		eventsList.setWidth("25%");		
		final SingleSelectionModel<IEvent> selectionModel = new SingleSelectionModel<IEvent>();		
		eventsList.setSelectionModel(selectionModel);
		eventsList.addStyleName("left");

		layoutPanel.add(eventsList);	

		// Create the right panel (event detail)
		layout_right = new LayoutPanel();
		layout_right.setStyleName("right");
		layoutPanel.add(layout_right);
		layoutPanel.setWidgetLeftWidth(layout_right, 25.0, Unit.PCT, 75.0, Unit.PCT);
		layoutPanel.setWidgetTopHeight(layout_right, 0.0, Unit.PX, 100.0, Unit.PCT);


		/* ------- Right view (event detail) ------- */
		Label event_title_label = new Label();
		event_title_label.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		event_title_label.setStyleName("event-detail-title");
		layout_right.add(event_title_label);
		layout_right.setVisible(false);
		layout_right.setWidgetLeftRight(event_title_label, 0, Unit.PX, 0, Unit.PX);

		// Two 50% panels for users list and cars list
		LayoutPanel layoutP_users = new LayoutPanel();
		layoutP_users.setStyleName("event-users-lp");
		LayoutPanel layoutP_cars = new LayoutPanel();
		layoutP_cars.setStyleName("event-cars-lp");
		layout_right.add(layoutP_users);
		layout_right.add(layoutP_cars);
		layout_right.setWidgetTopHeight(layoutP_users, 35, Unit.PX, 560, Unit.PX);
		layout_right.setWidgetTopHeight(layoutP_cars, 35, Unit.PX, 560, Unit.PX);
		layout_right.setWidgetLeftWidth(layoutP_users, 0, Unit.PX, 50.0, Unit.PCT);
		layout_right.setWidgetLeftWidth(layoutP_cars, 50, Unit.PCT, 50.0, Unit.PCT);		
		Label event_users_label = new Label("Participants (TODO : Liste des participants + suppr participant + ajout passager)");
		event_users_label.setStyleName("event-detail-users-label");
		event_users_label.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutP_users.add(event_users_label);		
		Label event_cars_label = new Label("Voitures (TODO : Liste des voitures + suppr voiture + ajout conducteur avec sa voiture)");
		event_cars_label.setStyleName("event-detail-cars-label");
		event_cars_label.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutP_cars.add(event_cars_label);

		// Two scrollPanels for users and cars lists
		ScrollPanel usersScrollPanel = new ScrollPanel();
		layoutP_users.add(usersScrollPanel);
		ScrollPanel carsScrollPanel = new ScrollPanel();
		layoutP_cars.add(carsScrollPanel);		

		// KeyProvider for the users of the selected event
		ProvidesKey<IUser> usersKeyProvider = new ProvidesKey<IUser>() {
			public Object getKey(IUser item) {
				return (item == null) ? null : item.getId();
			}
		};    
		// Create a CellList using the keyProvider, for the events list
		usersList = new CellList<IUser>(new UserCell(), usersKeyProvider);
		final SingleSelectionModel<IUser> selectionModelUsers = new SingleSelectionModel<IUser>();		
		usersList.setSelectionModel(selectionModelUsers);
		usersScrollPanel.add(usersList);
		layoutP_users.setWidgetTopHeight(usersScrollPanel, 25, Unit.PX, 500, Unit.PX);

		// User : BUTTON Delete user from event
		final Button delUserFromEventButton = new Button("Supprimer");
		delUserFromEventButton.setEnabled(false);
		layoutP_users.add(delUserFromEventButton);
		layoutP_users.setWidgetLeftWidth(delUserFromEventButton, 3, Unit.PX, 100, Unit.PCT);
		layoutP_users.setWidgetTopHeight(delUserFromEventButton, 537, Unit.PX, 23, Unit.PX);

		// User : BUTTON Add user to the event
		Button addUserToEventButton = new Button("Ajouter");
		layoutP_users.add(addUserToEventButton);
		layoutP_users.setWidgetLeftWidth(addUserToEventButton, 80, Unit.PX, 100, Unit.PCT);
		layoutP_users.setWidgetTopHeight(addUserToEventButton, 537, Unit.PX, 23, Unit.PX);


		rootPanel.add(layoutPanel);
		

		/* -------- CLICK EVENTS -------- */

		// Events when an element is selected in the list of events
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {	    	
			public void onSelectionChange(SelectionChangeEvent event) {
				IEvent selected = selectionModel.getSelectedObject();
				if (selected != null) {
					((Label) layout_right.getWidget(0)).setText("Evènement " + selected.getPlace());
					delUserFromEventButton.setEnabled(false);
					layout_right.setVisible(true);
					loadEventUsers(selected);

				}
			}	      
		});

		// Events when an element is selected in the list of the users of an event
		selectionModelUsers.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {	    	
			public void onSelectionChange(SelectionChangeEvent event) {
				delUserFromEventButton.setEnabled(true);
			}	      
		});
		
		// Click event on Delete user button		
		delUserFromEventButton.addClickHandler(new ClickHandler () {
			public void onClick(ClickEvent event) {
				IEvent selectedEvent = selectionModel.getSelectedObject();
				IUser selectedUser = selectionModelUsers.getSelectedObject();
				if (selectedEvent != null && selectedUser != null) {
					Window.alert("TODO : Delete " + selectedUser.getName() + " (" + selectedUser.getId() +
							") from the event " + selectedEvent.getPlace() + " (" + selectedEvent.getId() + ")");	
				}						
			}			
		});

		// Load all of the events from the server
		loadAllEvents();
	}


	/**
	 * Launch an HTTP request to load all of the events, and if success fills the events list
	 */
	private void loadAllEvents() {	
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, URL.encode(REST_API_URL + "/events"));
		rb.setCallback(new RequestCallback() {
			public void onError(Request req, Throwable th) {
				Window.alert("Error while retrieving the list of events =(");
			}
			public void onResponseReceived(Request req, Response res) {				
				if (res.getStatusCode() == 200) {	
					IEvents e = EventsJsonConverter.getInstance().deserializeFromJson(res.getText());
					eventsList.setRowCount(e.getEvents().size(), true);
					eventsList.setRowData(0, e.getEvents());
				}
			}			
		});

		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while retrieving the list of events =(");
		}
	}

	/**
	 * Load all the users which participates to the event ev
	 * @param ev The event on which we will load users
	 */
	private void loadEventUsers(IEvent ev) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, URL.encode(REST_API_URL + "/events/"+ev.getId()+"/users"));
		rb.setCallback(new RequestCallback() {
			public void onError(Request req, Throwable th) {
				Window.alert("Error while retrieving the list of users =(");
			}
			public void onResponseReceived(Request req, Response res) {				
				if (res.getStatusCode() == 200) {
					List<IUser> users = UsersJsonConverter.getInstance().deserializeFromJson(res.getText()).getUsers();
					usersList.setRowCount(users.size(), true);
					usersList.setRowData(0, users);
				}
			}			
		});

		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while retrieving the list of users =(");
		}
	}
}