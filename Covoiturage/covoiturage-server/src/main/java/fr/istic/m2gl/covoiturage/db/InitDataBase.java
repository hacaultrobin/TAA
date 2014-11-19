package fr.istic.m2gl.covoiturage.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class InitDataBase {

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("dev");
		EntityManager manager = factory.createEntityManager();

		// Ouverture d'une transaction
		EntityTransaction tx = manager.getTransaction();
		tx.begin();
		
		// Creation de 5 utilisateurs
		List<User> users = new ArrayList<User>();
		for (int i = 1; i <= 5; i++) {
			User user = new User("user_" + i);
			manager.persist(user);
			users.add(user);
		}
		
		// Creation d'1 voiture de 5 places
		Car car = new Car("Supercar", 5);
		manager.persist(car);
		
		// Ajout d'1 conducteur et 2 passagers
		car.setDriver(users.get(0));
		users.get(0).setCar(car);
		users.get(1).setCar(car);
		
		// Création d'un event avec 3 membres dans la même voiture
		Event e1 = new Event(new Date(), "Rennes", "Super event !!");
		manager.persist(e1);
		users.get(0).setEvent(e1);
		users.get(1).setEvent(e1);
		users.get(2).setEvent(e1);
		users.get(0).setCar(car);
		users.get(1).setCar(car);
		users.get(2).setCar(car);
		car.setDriver(users.get(0));
		
		// Fermeture de la transaction
		tx.commit();
		
		manager.close();
		factory.close();
	}
}
