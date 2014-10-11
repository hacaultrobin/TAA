package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.covoiturage.shared.IEvent;
import fr.istic.m2gl.covoiturage.shared.JsonFactory;


public class EventJsonConverter {

	private EventJsonConverter() {

	}

	private static EventJsonConverter instance = new EventJsonConverter();


	// Instantiate the factory
	JsonFactory factory = GWT.create(JsonFactory.class);
	// In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	public IEvent makeEvent() {
		// Construct the AutoBean
		AutoBean<IEvent> event = factory.event();

		// Return the Book interface shim
		return event.as();
	}

	String serializeToJson(IEvent event) {
		// Retrieve the AutoBean controller
		AutoBean<IEvent> bean = AutoBeanUtils.getAutoBean(event);

		return AutoBeanCodex.encode(bean).getPayload();
	}

	IEvent deserializeFromJson(String json) {
		AutoBean<IEvent> bean = AutoBeanCodex.decode(factory, IEvent.class, json);
		return bean.as();
	}

	public static EventJsonConverter getInstance() {
		return instance;
	}
}
