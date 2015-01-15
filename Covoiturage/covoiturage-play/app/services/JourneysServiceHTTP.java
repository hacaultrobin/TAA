package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.libs.F;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import services.models.Car;
import services.models.Event;
import services.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * JourneyService implementation that delegates to a third party JSON Web service
 */
public class JourneysServiceHTTP implements JourneysService {

	/** The HTTP client used to communicate with the Web service */
	final WSClient client;
	/** A JSON object mapper to handle JSON serialization/deserialization */
	final ObjectMapper mapper;
	/** The Web service base URL */
	final static String API_URL = "http://localhost:8080";

	public JourneysServiceHTTP(WSClient client) {
		this.client = client;
		mapper = new ObjectMapper();
	}

	@Override
	public F.Promise<List<Event>> allEvents() {
		return client.url(API_URL + "/covoiturage/rest/events")
				.get()
				.map(r -> mapper.readValue(r.getBody(), new TypeReference<List<Event>>() {}));
	}

	@Override
	public F.Promise<Event> getEvent(Long id) {
		return client.url(API_URL + "/covoiturage/rest/events/"+id)
				.get()
				.map(r -> mapper.readValue(r.getBody(), new TypeReference<Event>() {}));
	}
	
	@Override
	public F.Promise<List<User>> allUsers(Long id) {
		return client.url(API_URL + "/covoiturage/rest/events/"+id+"/users")
				.get()
				.map(r -> mapper.readValue(r.getBody(), new TypeReference<List<User>>() {}));
	}

	@Override
	public F.Promise<List<Car>> allCars(Long id) {
		return client.url(API_URL + "/covoiturage/rest/events/"+id+"/cars")
				.get()
				.map(r -> mapper.readValue(r.getBody(), new TypeReference<List<Car>>() {}));
	}

	@Override
	public F.Promise<WSResponse> addEvent(String place, String desc) {
    	String date = Calendar.getInstance().getTime().toGMTString();
		return client.url(API_URL + "/covoiturage/rest/events")
	            .setContentType("application/x-www-form-urlencoded; charset=utf-8")
				.post("date="+date+"&place="+place+"&desc="+desc);
	}
	
	@Override
	public F.Promise<WSResponse> addPassenger(Long id, String nom) {
		return client.url(API_URL + "/covoiturage/rest/events/"+id+"/join")
	            .setContentType("application/x-www-form-urlencoded; charset=utf-8")
				.post("username="+nom);
	}

	@Override
	public F.Promise<WSResponse> addDriver(Long id, String nom, String model, int nbSeat) {
		return client.url(API_URL + "/covoiturage/rest/events/"+id+"/joindriver")
	            .setContentType("application/x-www-form-urlencoded; charset=utf-8")
				.post("username="+nom+"&modelcar="+model+"&nbseatscar="+nbSeat);
	}

}
