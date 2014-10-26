package fr.istic.m2gl.covoiturage.client;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.covoiturage.shared.ICar;
import fr.istic.m2gl.covoiturage.shared.JsonFactory;


public class CarJsonConverter {

	private CarJsonConverter() {

	}

	private static CarJsonConverter instance = new CarJsonConverter();


	// Instantiate the factory
	JsonFactory factory = GWT.create(JsonFactory.class);
	// In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	public ICar makeCar() {
		// Construct the AutoBean
		AutoBean<ICar> car = factory.car();

		// Return the Book interface shim
		return car.as();
	}

	String serializeToJson(ICar car) {
		// Retrieve the AutoBean controller
		AutoBean<ICar> bean = AutoBeanUtils.getAutoBean(car);

		return AutoBeanCodex.encode(bean).getPayload();
	}

	ICar deserializeFromJson(String json) {
		AutoBean<ICar> bean = AutoBeanCodex.decode(factory, ICar.class, json);
		return bean.as();
	}

	public static CarJsonConverter getInstance() {
		return instance;
	}
}
