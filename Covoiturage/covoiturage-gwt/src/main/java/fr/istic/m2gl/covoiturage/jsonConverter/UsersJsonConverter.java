package fr.istic.m2gl.covoiturage.jsonConverter;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.covoiturage.shared.IUsers;
import fr.istic.m2gl.covoiturage.shared.JsonFactory;


public class UsersJsonConverter {

	private UsersJsonConverter() {

	}

	private static UsersJsonConverter instance = new UsersJsonConverter();


	// Instantiate the factory
	JsonFactory factory = GWT.create(JsonFactory.class);
	// In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	public IUsers makeUser() {
		// Construct the AutoBean
		AutoBean<IUsers> user = factory.users();

		// Return the Book interface shim
		return user.as();
	}

	String serializeToJson(IUsers user) {
		// Retrieve the AutoBean controller
		AutoBean<IUsers> bean = AutoBeanUtils.getAutoBean(user);

		return AutoBeanCodex.encode(bean).getPayload();
	}

	public IUsers deserializeFromJson(String json) {
		AutoBean<IUsers> bean = AutoBeanCodex.decode(factory, IUsers.class, "{\"users\":" + json + "}");
		return bean.as();
	}

	public static UsersJsonConverter getInstance() {
		return instance;
	}
}
