package com.ms.orm;

import com.ms.orm.core.EntityManager;
import com.ms.orm.core.EntityManagerImpl;
import com.ms.orm.database.connection.H2JdbcConnectionProvider;
import com.ms.orm.model.Person;
import com.ms.reflection.BeanManager;

import java.sql.SQLException;
import java.util.List;

public class QueryTesting {
	public static void main(String[] args) throws SQLException {

//		try (Connection connection =
//					 DriverManager.getConnection(PropertiesLoader.loadProperties().getProperty("db.h2.connection.url"))
//		) {
//			EntityManager entityManager = new EntityManagerImpl(connection);
		BeanManager beanManager = new BeanManager(List.of(H2JdbcConnectionProvider.class));
		EntityManager entityManager = beanManager.getInstance(EntityManagerImpl.class);
			Person person = entityManager.find(Person.class, 2L);
			System.out.println(person);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}
}
