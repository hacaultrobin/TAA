package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.covoiturage.shared.IEvents;
import fr.istic.m2gl.covoiturage.shared.JsonFactory;


public class EventsJsonConverter {

	private EventsJsonConverter() {

	}

	private static EventsJsonConverter instance = new EventsJsonConverter();


	// Instantiate the factory
	JsonFactory factory = GWT.create(JsonFactory.class);
	// In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	public IEvents makeEvents() {
		// Construct the AutoBean
		AutoBean<IEvents> events = factory.events();

		// Return the Book interface shim
		return events.as();
	}

	String serializeToJson(IEvents events) {
		// Retrieve the AutoBean controller
		AutoBean<IEvents> bean = AutoBeanUtils.getAutoBean(events);

		return AutoBeanCodex.encode(bean).getPayload();
	}

	IEvents deserializeFromJson(String json) {
		AutoBean<IEvents> bean = AutoBeanCodex.decode(factory, IEvents.class, "{\"events\":" + json + "}");
		return bean.as();
	}

	public static EventsJsonConverter getInstance() {
		return instance;
	}
}
