package controllers;

import play.Routes;
import play.data.Form;
import play.data.validation.Constraints;
import play.data.validation.ValidationError;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import services.JourneysService;
import services.JourneysServiceHTTP;
import static play.data.Form.form;

import java.util.ArrayList;
import java.util.List;


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
	public static Result journeys() {
		//return service.allEvents().map(events -> ok(views.html.index.render(Authentication.username(), events)) );
		return ok( views.html.index.render(Authentication.username(), service.allEvents().get(500), form(Event.class)) );
	}

	/**
	 * Show the details of the journey with the given id
	 */
	public static Result journey(Long id) {
		// TODO: remplacer timeout par promise & verification ID
		return ok( views.html.journey.render(service.getEvent(id).get(500), service.allUsers(id).get(500), service.allCars(id).get(500), form(Passenger.class), form(Driver.class)) );
	}
	
	/**
	 * Add journey
	 */
	public static Result addEvent() {
        // Read the data of the form submission
        Form<Event> eventForm = form(Event.class).bindFromRequest();
        
        if (eventForm.hasErrors()) {
        	// Failure -> reply with a 400 status code (Bad Request) and show the form with the validation errors
    		return badRequest( views.html.index.render(Authentication.username(), service.allEvents().get(500), form(Event.class)) );
        } else {
        	service.addEvent(eventForm.get().place, eventForm.get().desc);
        	
        	//redirect to the Journeys.journeys action
        	return redirect(routes.Journeys.journeys());        	
        }
	}

	/**
	 * Add passenger to the journey with the given id
	 */
	public static Result addPassenger(Long id) {
        // Read the data of the form submission
        Form<Passenger> passengerForm = form(Passenger.class).bindFromRequest();
        
        if (passengerForm.hasErrors()) {
        	// Failure -> reply with a 400 status code (Bad Request) and show the form with the validation errors
        	return badRequest( views.html.journey.render(service.getEvent(id).get(500), service.allUsers(id).get(500), service.allCars(id).get(500), passengerForm, form(Driver.class)) );
        } else {
			service.addPassenger(id, passengerForm.get().nom);
			
        	//redirect to the Journeys.journeys action
        	return redirect(routes.Journeys.journey(id));
        }
	}

	/**
	 * Add driver to the journey with the given id
	 */
	public static Result addDriver(Long id) {
        // Read the data of the form submission
        Form<Driver> driverForm = form(Driver.class).bindFromRequest();
        
        if (driverForm.hasErrors()) {
        	// Failure -> reply with a 400 status code (Bad Request) and show the form with the validation errors
        	return badRequest( views.html.journey.render(service.getEvent(id).get(500), service.allUsers(id).get(500), service.allCars(id).get(500), form(Passenger.class), driverForm) );
        } else {
        	service.addDriver(id, driverForm.get().nom, driverForm.get().modele, driverForm.get().nbSeat);
        	
        	//redirect to the Journeys.journeys action
        	return redirect(routes.Journeys.journey(id));        	
        }
	}


	/**
	 * Form model for event
	 */
	public static class Event {
		@Constraints.Required
		public String place;
		@Constraints.Required
		public String desc;

		public List<ValidationError> validate() {
			List<ValidationError> errors = new ArrayList<ValidationError>();

			if (place.equals("")) {
				errors.add(new ValidationError("place", "Champs vide impossible"));
			}
			if (desc.equals("")) {
				errors.add(new ValidationError("desc", "Champs vide impossible"));
			}
			
			return errors.isEmpty() ? null : errors;
		}
	}
	
	/**
	 * Form model for passenger
	 */
	public static class Passenger {
		@Constraints.Required
		public String nom;

		public List<ValidationError> validate() {
			List<ValidationError> errors = new ArrayList<ValidationError>();
		
			if (nom.equals("")) {
				errors.add(new ValidationError("nom", "Champs vide impossible"));
			}
			
			return errors.isEmpty() ? null : errors;
		}
	}

	/**
	 * Form model for driver
	 */
	public static class Driver {
		@Constraints.Required
		public String nom;
		@Constraints.Required
		public String modele;
		@Constraints.Required
		public int nbSeat;
		
		public List<ValidationError> validate() {
			List<ValidationError> errors = new ArrayList<ValidationError>();
		
			if (nom.equals("")) {
				errors.add(new ValidationError("nom", "Champs vide impossible"));
			}
			if (modele.equals("")) {
				errors.add(new ValidationError("model", "Champs vide impossible"));
			}
			if (nbSeat < 1) {
				errors.add(new ValidationError("nom", "Le nombre de sièges doit être supérieur à 1"));
			}
			
			return errors.isEmpty() ? null : errors;
		}
	}
	
	

	/**
	 * JavaScript resource defining a reverse router allowing us to compute URLs of the application from client-side
	 */
	public static Result javaScriptRouter() {
		return ok(Routes.javascriptRouter("routes", routes.javascript.Journeys.journeys()));
	}
//	
//	
//	/**
//	 * Show a page displaying journey’s attendee
//	 */
//	public static F.Promise<Result> observe(Long journeyId) {
//		throw new NotImplementedError();
//		//return withJourney(journeyId, journey -> ok(views.html.observe.render(journeyId)));
//	}
//
//	/**
//	 * Stream of participation notifications for the journey with the given id
//	 */
//	public static Result attendees(Long journeyId) {
//		throw new NotImplementedError();
//	}
//	
//	static F.Promise<Result> withJourney(Long id, Function<Event, Result> f) {
//		return service.allEvents().map(journeys -> {
//			return journeys.stream().
//					filter(journey -> journey.id.equals(id)).
//					findFirst().
//					map(f::apply).
//					orElseGet(Results::notFound);
//		});
//	}

}
