package services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.libs.F;
import play.libs.ws.WSClient;
import rx.Observable;
import scala.NotImplementedError;
import services.models.Attendee;
import services.models.Car;
import services.models.Event;
import services.models.User;

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
	public F.Promise<Boolean> join(Long journeyId, Long driverId, String attendeeName) {
		throw new NotImplementedError();
	}

	@Override
	public F.Promise<Boolean> attend(Long journeyId, String attendeeName, Integer availableSeats) {
		throw new NotImplementedError();
	}

	@Override
	public Observable<Attendee> attendees(Long journeyId) {
		throw new NotImplementedError();
	}

}
