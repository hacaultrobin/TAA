package controllers;

import play.Routes;
import play.data.validation.Constraints;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Security;
import scala.NotImplementedError;
import services.JourneysService;
import services.JourneysServiceHTTP;
import services.models.Event;

import java.util.function.Function;

/**
 * Controller grouping actions related to the journeys service.
 */
@Security.Authenticated(Authenticator.class)
public class Journeys extends Controller {

	/**
	 * The entry point to the service implementation.
	 */
	//static JourneysService service = new JourneysServiceStub(Akka.system());
	static JourneysService service = new JourneysServiceHTTP(play.libs.ws.WS.client());

	/**
	 * Show all visible journeys
	 */
	public static F.Promise<Result> journeys() {
		return service.allEvents().map(events -> ok(views.html.index.render(Authentication.username(), events)) );
	}

	/**
	 * Show the details of the journey with the given id
	 */
	public static Result journey(Long id) {
		// TODO: remplacer tiemout par promise & verification ID
		return ok( views.html.journey.render(service.getEvent(id).get(500), service.allUsers(id).get(500), service.allCars(id).get(500)) );
	}

	/**
	 * Attend to the journey with the given id
	 */
	public static F.Promise<Result> attend(Long journeyId) {
		throw new NotImplementedError();
	}

	/**
	 * Attend to a journey by joining a driver already attending
	 */
	public static F.Promise<Result> join(Long journeyId) {
		throw new NotImplementedError();
	}

	/**
	 * Show a page displaying journey’s attendee
	 */
	public static F.Promise<Result> observe(Long journeyId) {
		return withJourney(journeyId, journey -> ok(views.html.observe.render(journeyId)));
	}

	/**
	 * Stream of participation notifications for the journey with the given id
	 */
	public static Result attendees(Long journeyId) {
		throw new NotImplementedError();
	}

	/**
	 * JavaScript resource defining a reverse router allowing us to compute URLs of the application from client-side
	 */
	public static Result javaScriptRouter() {
		return ok(Routes.javascriptRouter("routes", routes.javascript.Journeys.attendees()));
	}

	/**
	 * Form model for attending as a driver
	 */
	public static class Attend {
		@Constraints.Required
		public Integer availableSeats;
	}

	/**
	 * Form model for attending by joining a driver
	 */
	public static class Join {
		@Constraints.Required
		public Long driverId;
	}

	static F.Promise<Result> withJourney(Long id, Function<Event, Result> f) {
		return service.allEvents().map(journeys -> {
			return journeys.stream().
					filter(journey -> journey.id.equals(id)).
					findFirst().
					map(f::apply).
					orElseGet(Results::notFound);
		});
	}

}
