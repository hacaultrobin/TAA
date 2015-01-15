package services;

import play.libs.F;
import play.libs.ws.WSResponse;
import services.models.Car;
import services.models.Event;
import services.models.User;

import java.util.List;

/**
 * Asynchronous API for the journeys service
 */
public interface JourneysService {

    /**
     * @return A list of all the journeys handled by the service
     */
    F.Promise<List<Event>> allEvents();
    F.Promise<Event> getEvent(Long id);
    F.Promise<List<User>> allUsers(Long id);
    F.Promise<List<Car>> allCars(Long id);

    F.Promise<WSResponse> addEvent(String place, String desc);
    
    /**
     * Registers a new attendee joining a driver already attending to a journey.
     *
     * @param id of the journey to attend to
     * @param nom Name of the passenger joining the journey
     */
    F.Promise<WSResponse> addPassenger(Long id, String nom);

    /**
     * Registers a new attendee as a driver.
     *
     * @param id of the journey to attend to
     * @param nom Name of the driver joining the journey
     * @param nbSeat Available seats in the vehicule
     */
    F.Promise<WSResponse> addDriver(Long id, String nom, String modele, int nbSeat);
}
