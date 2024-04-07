package com.ms.reflection.database;

import org.h2.tools.Server;

import java.sql.SQLException;

public class H2DatabaseLauncher {
	public static void main(String[] args) throws SQLException {
		// start database
		Server.main("-ifNotExists");
	}
}
