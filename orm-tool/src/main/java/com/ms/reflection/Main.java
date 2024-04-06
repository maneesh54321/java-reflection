package com.ms.reflection;

import com.ms.reflection.core.EntityManager;
import com.ms.reflection.core.EntityManagerImpl;
import com.ms.reflection.model.Person;
import com.ms.reflection.util.ColumnField;
import com.ms.reflection.util.MetaDataModel;
import com.ms.reflection.util.PrimaryKeyField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import org.h2.tools.Server;

public class Main {

	public static void main(String[] args) throws SQLException {
		Person ram = new Person(1, "Ram", 23);
		Class<? extends Person> clazz = ram.getClass();

		MetaDataModel metaDataModel = MetaDataModel.of(clazz);

		PrimaryKeyField primaryKeyField = metaDataModel.getPrimaryKeyField();
		System.out.printf("PrimaryKeyField:\nname: %s, type: %s%n", primaryKeyField.getName(), primaryKeyField.getType().getSimpleName());

		List<ColumnField> columnFields = metaDataModel.getColumnFields();
		System.out.println("Column Fields:");
		columnFields.forEach(cf -> System.out.printf("name: %s, type: %s%n",
				cf.getName(), cf.getType().getSimpleName()));

		// start database
//		Server.main("-ifNotExists");
		Server.main();

		Person sita = new Person("Sita", 22);
		Person mohan = new Person("Mohan", 32);
		Person suresh = new Person("Suresh", 29);

		// Build entity manager
//		try(Connection connection = DriverManager.getConnection("jdbc:h2:/Users/maneesh/Work/h2/data", "sa", "")){
		try(Connection connection = DriverManager.getConnection("jdbc:h2:C:\\Users\\manee\\work\\h2", "sa", "")){
			EntityManager entityManager = new EntityManagerImpl(connection);

			entityManager.persist(ram);
			entityManager.persist(sita);
			entityManager.persist(mohan);
			entityManager.persist(suresh);

			Person person = entityManager.find(Person.class, 2L);

			System.out.println(person);
		}
	}
}