package fr.istic.m2gl.covoiturage.shared;

import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface JsonFactory extends AutoBeanFactory {
	
  AutoBean<IEvent> event();
  AutoBean<IEvents> events();
  
  AutoBean<IUser> user();
  AutoBean<IUsers> users();
  
}