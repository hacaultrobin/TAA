package jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.istic.m2gl.covoiturage.domain.Car;
import fr.istic.m2gl.covoiturage.domain.User;

public class JpaTest {

	private EntityManager manager;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();
		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();

		try {

			// Test : Creation de 5 utilisateurs
			List<User> users = new ArrayList<User>();
			for (int i = 1; i <= 5; i++) {
				User user = new User("user_" + i);
				manager.persist(user);
				users.add(user);
			}
			
			// Test : Creation d'1 voiture de 5 places
			Car car = new Car("Supercar", 5);
			manager.persist(car);
			car.setDriver(users.get(0));
			users.get(0).setCar(car);
			
			users.get(1).setCar(car); // 2 users dans la voiture
									
			System.err.println("Voiture " + car.getId() + " : Modèle " + car.getModel() + ", " + car.getNbSeat() + " places, " +
							   "conduite par " + car.getDriver().getName());
			
			System.err.println(users.get(0).getName() + " est conducteur : " + users.get(0).isDriver());
						
			tx.commit(); // Sauvegarde
			manager.close();
			
			/* Recuperation d'objets depuis la base */
			EntityManager em = factory.createEntityManager();
			Car car1 = em.find(Car.class, 1);
			System.err.println(car1.getUsersInCar().size() + " utilisateurs dans la voiture");
			em.close();
			
			
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		factory.close();
		
	}

}
