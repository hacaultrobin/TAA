package fr.istic.m2gl.covoiturage.client;


import java.util.Date;
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
import com.google.gwt.i18n.client.DateTimeFormat;
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

import fr.istic.m2gl.covoiturage.shared.ICar;
import fr.istic.m2gl.covoiturage.shared.IEvent;
import fr.istic.m2gl.covoiturage.shared.IEvents;
import fr.istic.m2gl.covoiturage.shared.IUser;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Covoiturage implements EntryPoint {

	private static final String REST_API_URL = GWT.getHostPageBaseURL() + "covoiturage/rest";

	private CellList<IEvent> eventsList;	
	private LayoutPanel layout_left, layout_right;
	private CellList<IUser> usersList;
	private CellList<ICar> carsList;

	private IEvent selectedEvent;

	private Button supprEventButton;
	private Button delUserFromEventButton;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get();

		LayoutPanel layoutPanel = new LayoutPanel();		
		layoutPanel.setSize("100%", "600px");
		
		// Create the left panel (event list)
		layout_left = new LayoutPanel();
		layout_left.setStyleName("left");
		layoutPanel.add(layout_left);
		layoutPanel.setWidgetTopHeight(layout_left, 0.0, Unit.PX, 100.0, Unit.PCT);
		
		// ScrollPanel for the events list
		ScrollPanel eventsScrollPanel = new ScrollPanel();
		layout_left.add(eventsScrollPanel);
		layout_left.setWidgetTopHeight(eventsScrollPanel, 0.0, Unit.PX, 550, Unit.PX);
		layout_left.setWidgetLeftWidth(eventsScrollPanel, 0.0, Unit.PX, 100, Unit.PCT);

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
		eventsScrollPanel.add(eventsList);
		
		// Create delete/add event Buttons
		supprEventButton = new Button("Suppr. Evènement");
		Button addEventButton = new Button("Ajout. Evènement");
		layout_left.add(supprEventButton);
		layout_left.add(addEventButton);
		layout_left.setWidgetLeftWidth(supprEventButton, 3, Unit.PX, 100, Unit.PCT);
		layout_left.setWidgetTopHeight(supprEventButton, 570, Unit.PX, 25, Unit.PX);
		layout_left.setWidgetLeftWidth(addEventButton, 131, Unit.PX, 100, Unit.PCT);
		layout_left.setWidgetTopHeight(addEventButton, 570, Unit.PX, 25, Unit.PX);
		addEventButton.addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {
				DialogAddEvent dialog = new DialogAddEvent(Covoiturage.this);
				dialog.center();
			}
		});	
		supprEventButton.addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {
				if (selectedEvent != null) {
					deleteEvent(selectedEvent);
				}
			}
		});	
		supprEventButton.setEnabled(false);

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
		Label event_users_label = new Label("Participants");
		event_users_label.setStyleName("event-detail-users-label");
		event_users_label.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		layoutP_users.add(event_users_label);		
		Label event_cars_label = new Label("Voitures");
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
		// Create a CellList using the keyProvider, for the users list
		usersList = new CellList<IUser>(new UserCell(), usersKeyProvider);
		final SingleSelectionModel<IUser> selectionModelUsers = new SingleSelectionModel<IUser>();		
		usersList.setSelectionModel(selectionModelUsers);
		usersScrollPanel.add(usersList);
		layoutP_users.setWidgetTopHeight(usersScrollPanel, 25, Unit.PX, 500, Unit.PX);

		// User : BUTTON Delete user from event
		delUserFromEventButton = new Button("Suppr. participant");
		delUserFromEventButton.setEnabled(false);
		layoutP_users.add(delUserFromEventButton);
		layoutP_users.setWidgetLeftWidth(delUserFromEventButton, 3, Unit.PX, 100, Unit.PCT);
		layoutP_users.setWidgetTopHeight(delUserFromEventButton, 535, Unit.PX, 25, Unit.PX);

		// User : BUTTON Add passenger to the event
		Button addPassengerToEventButton = new Button("Ajout passager");
		layoutP_users.add(addPassengerToEventButton);
		layoutP_users.setWidgetLeftWidth(addPassengerToEventButton, 125, Unit.PX, 100, Unit.PCT);
		layoutP_users.setWidgetTopHeight(addPassengerToEventButton, 535, Unit.PX, 25, Unit.PX);
		addPassengerToEventButton.addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {
				// Instantiate the dialog box and show it.
				DialogAddUser dialog = new DialogAddUser(false, Covoiturage.this, selectedEvent);
				dialog.center();
			}
		});

		// User : BUTTON Add passenger to the event
		Button addDriverToEventButton = new Button("Ajout conducteur");
		layoutP_users.add(addDriverToEventButton);
		layoutP_users.setWidgetLeftWidth(addDriverToEventButton, 232, Unit.PX, 100, Unit.PCT);
		layoutP_users.setWidgetTopHeight(addDriverToEventButton, 535, Unit.PX, 25, Unit.PX);
		addDriverToEventButton.addClickHandler(new ClickHandler() {			
			public void onClick(ClickEvent event) {
				// Instantiate the dialog box and show it.
				DialogAddUser dialog = new DialogAddUser(true, Covoiturage.this, selectedEvent);
				dialog.center();
			}
		});

		// KeyProvider for the cars of the selected event
		ProvidesKey<ICar> carsKeyProvider = new ProvidesKey<ICar>() {
			public Object getKey(ICar item) {
				return (item == null) ? null : item.getId();
			}
		};    
		// Create a CellList using the keyProvider, for the users list
		carsList = new CellList<ICar>(new CarCell(), carsKeyProvider);
		final SingleSelectionModel<ICar> selectionModelCars = new SingleSelectionModel<ICar>();		
		carsList.setSelectionModel(selectionModelCars);
		carsScrollPanel.add(carsList);
		layoutP_cars.setWidgetTopHeight(carsScrollPanel, 25, Unit.PX, 500, Unit.PX);
		
		rootPanel.add(layoutPanel);


		/* -------- CLICK EVENTS -------- */

		// Events when an element is selected in the list of events
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {	    	
			public void onSelectionChange(SelectionChangeEvent event) {
				selectedEvent = selectionModel.getSelectedObject();
				if (selectedEvent != null) {
					supprEventButton.setEnabled(true);
					((Label) layout_right.getWidget(0)).setText("Evènement " + selectedEvent.getPlace());
					delUserFromEventButton.setEnabled(false);
					layout_right.setVisible(true);
					loadEventUsers(selectedEvent);
					loadEventCars(selectedEvent);

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
					deleteUserFromEvent(selectedUser, selectedEvent);
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

	/**
	 * Load all the cars which participates to the event ev
	 * @param ev The event on which we will load cars
	 */
	private void loadEventCars(IEvent ev) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, URL.encode(REST_API_URL + "/events/"+ev.getId()+"/cars"));
		rb.setCallback(new RequestCallback() {
			public void onError(Request req, Throwable th) {
				Window.alert("Error while retrieving the list of cars =(");
			}
			public void onResponseReceived(Request req, Response res) {				
				if (res.getStatusCode() == 200) {
					List<ICar> cars = CarsJsonConverter.getInstance().deserializeFromJson(res.getText()).getCars();
					carsList.setRowCount(cars.size(), true);
					carsList.setRowData(0, cars);					
				}
			}			
		});

		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while retrieving the list of cars =(");
		}
	}

	private void deleteUserFromEvent(final IUser user, final IEvent ev) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.DELETE, URL.encode(REST_API_URL + "/events/"+ev.getId()+"/removeuser/"+user.getId()));
		rb.setCallback(new RequestCallback() {			
			public void onError(Request request, Throwable exception) {
				Window.alert("Error while deleting the user from the event =(");			
			}
			public void onResponseReceived(Request req, Response res) {
				switch(res.getStatusCode()) {
				case 200: /* Ok, user deleted from the event */
					// Reload the list of the event users
					delUserFromEventButton.setEnabled(false);
					loadEventUsers(ev);
					loadEventCars(ev);
					break;
				case 202: /* Error : User is a driver and there are other passengers in his car */
					Window.alert(user.getName() + " (" + user.getId() + ") ne peut quitter l'évènement. \n\n"
							+ "Il conduit la voiture n°" + user.getCarId() + " qui contient d'autres passagers.\n\n"
							+ "Ils doivent s'en aller d'abord ;-)");
					break;
				}
			}			
		});
		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while deleting the user from the event =(");
		}		
	}

	protected void addPassengerToEvent(final IEvent ev, final String userName) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, URL.encode(REST_API_URL + "/events/"+ev.getId()+"/join"));
		rb.setHeader("Content-Type","application/x-www-form-urlencoded");
		rb.setRequestData(URL.encode("username=" + userName));
		rb.setCallback(new RequestCallback() {			
			public void onResponseReceived(Request req, Response res) {
				switch(res.getStatusCode()) {
				case 200:
					loadEventUsers(ev);
					loadEventCars(ev);
					break;
				case 202:
					Window.alert(userName + " n'a pu rejoindre l'évènement car il n'y a pas de sièges disponibles");
					break;
				}
			}			
			public void onError(Request request, Throwable exception) {
				Window.alert("Error while adding the passenger to the event =(");			
			}
		});
		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while adding the passenger to the event =(");	
		}
	}

	protected void addDriverToEvent(final IEvent ev, String userName, String carModel, int carNbSeats) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, URL.encode(REST_API_URL + "/events/"+ev.getId()+"/joindriver"));
		rb.setHeader("Content-Type","application/x-www-form-urlencoded");
		rb.setRequestData(URL.encode("username=" + userName + "&modelcar=" + carModel + "&nbseatscar=" + carNbSeats));
		rb.setCallback(new RequestCallback() {			
			public void onResponseReceived(Request req, Response res) {
				if (res.getStatusCode() == 200) {
					loadEventUsers(ev);
					loadEventCars(ev);				
				}
			}			
			public void onError(Request request, Throwable exception) {
				Window.alert("Error while adding the driver to the event =(");			
			}
		});
		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while adding the driver to the event =(");	
		}
	}
	
	private void deleteEvent(IEvent ev) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.DELETE, URL.encode(REST_API_URL + "/events/"+ev.getId()));
		rb.setCallback(new RequestCallback() {			
			public void onError(Request request, Throwable exception) {
				Window.alert("Error while deleting the event =(");			
			}
			public void onResponseReceived(Request req, Response res) {
				selectedEvent = null;
				supprEventButton.setEnabled(false);
				layout_right.setVisible(false);
				loadAllEvents();
			}			
		});
		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while deleting the event =(");
		}		
	}
	
	protected void addEvent(Date date, String place, String desc) {
		RequestBuilder rb = new RequestBuilder(RequestBuilder.POST, URL.encode(REST_API_URL + "/events"));
		rb.setHeader("Content-Type","application/x-www-form-urlencoded");
		DateTimeFormat df = DateTimeFormat.getFormat("yyyy MMM dd");
		rb.setRequestData(URL.encode("date=" + df.format(date) + "&place=" + place + "&desc=" + desc));
		rb.setCallback(new RequestCallback() {			
			public void onResponseReceived(Request req, Response res) {
				if (res.getStatusCode() == 200) {
					loadAllEvents();
				}				
			}			
			public void onError(Request request, Throwable exception) {
				Window.alert("Error while adding the event =(");			
			}
		});
		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while adding the event =(");	
		}
	}
}