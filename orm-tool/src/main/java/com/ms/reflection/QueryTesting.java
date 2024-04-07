package com.ms.reflection;

import com.ms.reflection.core.EntityManager;
import com.ms.reflection.core.EntityManagerImpl;
import com.ms.reflection.model.Person;
import com.ms.reflection.util.PropertiesLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class QueryTesting {
	public static void main(String[] args) {

		try (Connection connection =
					 DriverManager.getConnection(PropertiesLoader.loadProperties().getProperty("db.h2.connection.url"))
		) {
			EntityManager entityManager = new EntityManagerImpl(connection);
			Person person = entityManager.find(Person.class, 2L);
			System.out.println(person);
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e);
		}
	}
}
