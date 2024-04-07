package com.ms.orm.database.connection;

import com.ms.orm.util.PropertiesLoader;
import com.ms.reflection.annotation.Provides;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2JdbcConnectionProvider {

	@Provides
	public static Connection buildConnection() throws IOException, SQLException {
		return DriverManager.getConnection(
						PropertiesLoader.loadProperties().getProperty("db.h2.connection.url"));
	}
}
