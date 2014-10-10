package fr.istic.m2gl.covoiturage.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
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
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Covoiturage implements EntryPoint {

	private CellList<String> cellList;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
				
		// Create a cell to render each value.
		TextCell textCell = new TextCell();

		RootPanel rootPanel = RootPanel.get();
		
		LayoutPanel layoutPanel = new LayoutPanel();
		rootPanel.add(layoutPanel);
		layoutPanel.setSize("100%", "600px");
			
		cellList = new CellList<String>(textCell);
		cellList.setWidth("35%");
		
		layoutPanel.add(cellList);	
		
		LayoutPanel layoutPanel_1 = new LayoutPanel();
		layoutPanel_1.setStyleName("right");
		layoutPanel.add(layoutPanel_1);
		layoutPanel.setWidgetLeftWidth(layoutPanel_1, 35.0, Unit.PCT, 65.0, Unit.PCT);
		layoutPanel.setWidgetTopHeight(layoutPanel_1, 0.0, Unit.PX, 100.0, Unit.PCT);
		
		// Get all of the events
		testGetREST();
	}
	
	private void testGetREST() {		
		RequestBuilder rb = new RequestBuilder(RequestBuilder.GET, URL.encode("https://public.opencpu.org/ocpu/library/"));
		rb.setCallback(new RequestCallback() {

			public void onError(Request req, Throwable th) {
				// TODO Auto-generated method stub
			}

			public void onResponseReceived(Request req, Response res) {
				// TODO Auto-generated method stub
				if (res.getStatusCode() == 200) {					
					List<String > events = Arrays.asList("Event 1", "Event 2",
							"Event 3", "Event 4", "Event 5", "Event 6", "Event 7");					
					cellList.setRowCount(events.size(), true);
				    cellList.setRowData(0, events);					
				}
			}
			
		});
		
		try {
			rb.send();
		} catch (RequestException e) {
			e.printStackTrace();
		}
		
	}
}
