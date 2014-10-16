package fr.istic.m2gl.covoiturage.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import fr.istic.m2gl.covoiturage.shared.IEvent;
import fr.istic.m2gl.covoiturage.shared.IEvents;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Covoiturage implements EntryPoint {

	private static final String REST_API_URL = "http://localhost:8080/covoiturage/rest";

	private CellList<IEvent> cellList;
	private LayoutPanel layout_right;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {

		RootPanel rootPanel = RootPanel.get();

		LayoutPanel layoutPanel = new LayoutPanel();		
		layoutPanel.setSize("100%", "600px");

		ProvidesKey<IEvent> keyProvider = new ProvidesKey<IEvent>() {
			public Object getKey(IEvent item) {
				return (item == null) ? null : item.getId();
			}
		};    
		// Create a CellList using the keyProvider, for the events list
		cellList = new CellList<IEvent>(new EventCell(), keyProvider);
		cellList.setWidth("25%");		
		final SingleSelectionModel<IEvent> selectionModel = new SingleSelectionModel<IEvent>();		
		cellList.setSelectionModel(selectionModel);
		cellList.addStyleName("left");

		layoutPanel.add(cellList);	

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

		rootPanel.add(layoutPanel);

		// Events when an element is selected in the list of events
		selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {	    	
			public void onSelectionChange(SelectionChangeEvent event) {
				IEvent selected = selectionModel.getSelectedObject();
				if (selected != null) {
					((Label) layout_right.getWidget(0)).setText("Evènement " + selected.getPlace());
					layout_right.setVisible(true);
					Window.alert("TODO ------> Pb parsing JSON recupération des participants d'un event");
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
					cellList.setRowCount(e.getEvent().size(), true);
					cellList.setRowData(0, e.getEvent());
				}
			}			
		});

		try {
			rb.send();
		} catch (RequestException e) {
			Window.alert("Error while retrieving the list of events =(");
		}
		
		
		/* Test get all users (UserService) --> OK */
		
//		RequestBuilder rb2 = new RequestBuilder(RequestBuilder.GET, URL.encode(REST_API_URL + "/users"));
//		rb2.setCallback(new RequestCallback() {
//			public void onError(Request req, Throwable th) {
//				Window.alert("Error while retrieving the list of users =(");
//			}
//			public void onResponseReceived(Request req, Response res) {				
//				if (res.getStatusCode() == 200) {	
//					IUsers e = UsersJsonConverter.getInstance().deserializeFromJson(res.getText());
//					Window.alert("USERS --> " + e.getUser());
//				}
//			}			
//		});
//
//		try {
//			rb2.send();
//		} catch (RequestException e) {
//			Window.alert("Error while retrieving the list of users =(");
//		}	

	}
}