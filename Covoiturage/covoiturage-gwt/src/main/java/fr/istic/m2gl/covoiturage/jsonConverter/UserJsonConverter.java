package fr.istic.m2gl.covoiturage.jsonConverter;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;

import fr.istic.m2gl.covoiturage.shared.IUser;
import fr.istic.m2gl.covoiturage.shared.JsonFactory;


public class UserJsonConverter {

	private UserJsonConverter() {

	}

	private static UserJsonConverter instance = new UserJsonConverter();


	// Instantiate the factory
	JsonFactory factory = GWT.create(JsonFactory.class);
	// In non-GWT code, use AutoBeanFactorySource.create(MyFactory.class);

	public IUser makeUser() {
		// Construct the AutoBean
		AutoBean<IUser> user = factory.user();

		// Return the Book interface shim
		return user.as();
	}

	String serializeToJson(IUser user) {
		// Retrieve the AutoBean controller
		AutoBean<IUser> bean = AutoBeanUtils.getAutoBean(user);

		return AutoBeanCodex.encode(bean).getPayload();
	}

	IUser deserializeFromJson(String json) {
		AutoBean<IUser> bean = AutoBeanCodex.decode(factory, IUser.class, json);
		return bean.as();
	}

	public static UserJsonConverter getInstance() {
		return instance;
	}
}
