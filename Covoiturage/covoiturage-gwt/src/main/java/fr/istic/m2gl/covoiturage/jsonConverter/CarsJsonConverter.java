package fr.istic.m2gl.covoiturage.jsonConverter;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.covoiturage.shared.ICars;
import fr.istic.m2gl.covoiturage.shared.JsonFactory;


public class CarsJsonConverter {

	private CarsJsonConverter() {

	}

	private static CarsJsonConverter instance = new CarsJsonConverter();


	// Instantiate the factory
	JsonFactory factory = GWT.create(JsonFactory.class);
	// In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	public ICars makeCars() {
		// Construct the AutoBean
		AutoBean<ICars> cars = factory.cars();

		// Return the Book interface shim
		return cars.as();
	}

	String serializeToJson(ICars cars) {
		// Retrieve the AutoBean controller
		AutoBean<ICars> bean = AutoBeanUtils.getAutoBean(cars);

		return AutoBeanCodex.encode(bean).getPayload();
	}

	public ICars deserializeFromJson(String json) {
		AutoBean<ICars> bean = AutoBeanCodex.decode(factory, ICars.class, "{\"cars\":" + json + "}");
		return bean.as();
	}

	public static CarsJsonConverter getInstance() {
		return instance;
	}
}
