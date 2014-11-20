package services;

import play.libs.F;
import rx.Observable;
import services.models.Attendee;
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
    
    /**
     * Registers a new attendee joining a driver already attending to a journey.
     *
     * @param journeyId Id of the journey to attend to
     * @param driverId Id of the driver to attend to
     * @param attendeeName Name of the passenger joining the driver
     * @return A boolean value indicating if the operation was successful or not
     */
    F.Promise<Boolean> join(Long journeyId, Long driverId, String attendeeName);

    /**
     * Registers a new attendee as a driver.
     *
     * @param journeyId Id of the journey to attend to
     * @param attendeeName Name of the attendee
     * @param availableSeats Available seats in the attendee’s vehicule
     * @return A boolean value indicating if the operation was successful or not
     */
    F.Promise<Boolean> attend(Long journeyId, String attendeeName, Integer availableSeats);

    /**
     * @param journeyId Id of the observed journey
     * @return A stream of attendees of the given journey
     */
    Observable<Attendee> attendees(Long journeyId);

}
