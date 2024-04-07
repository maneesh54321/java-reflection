package com.ms.orm;

import com.ms.orm.core.EntityManagerImpl;
import com.ms.reflection.BeanManager;
import com.ms.orm.core.EntityManager;
import com.ms.orm.database.connection.H2JdbcConnectionProvider;
import com.ms.orm.model.Person;

import java.sql.SQLException;
import java.util.List;

public class PersistenceTesting {

	public static void main(String[] args) throws SQLException {
		Person ram = new Person(1, "Ram", 23);
		Person sita = new Person("Sita", 22);
		Person mohan = new Person("Mohan", 32);
		Person suresh = new Person("Suresh", 29);

		System.out.println("Before persistence");
		System.out.println(ram);
		System.out.println(sita);
		System.out.println(mohan);
		System.out.println(suresh);

		// Build entity manager
//		try(Connection connection = DriverManager.getConnection("jdbc:h2:/Users/maneesh/Work/h2/data", "sa", "")){

//		try (Connection connection =
//					 DriverManager.getConnection(
//							 PropertiesLoader.loadProperties().getProperty("db.h2.connection.url"))
//		) {

		BeanManager beanManager = new BeanManager(List.of(H2JdbcConnectionProvider.class));
		EntityManager entityManager = beanManager.getInstance(EntityManagerImpl.class);

		System.out.println("Persisting all persons...");
		entityManager.persist(ram);
		entityManager.persist(sita);
		entityManager.persist(mohan);
		entityManager.persist(suresh);

		System.out.println(ram);
		System.out.println(sita);
		System.out.println(mohan);
		System.out.println(suresh);
	}
}