module orm.framework {
	requires orm.cdi;
	requires java.sql;
	requires com.h2database;
	exports com.ms.orm.core;
	opens com.ms.orm.core;
	exports com.ms.orm.database.connection;
}