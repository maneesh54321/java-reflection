package com.ms.orm.core;

import java.sql.SQLException;
import java.util.List;

public interface EntityManager {
	<T> void persist(T obj);

	<T, P> T find(Class<T> clazz, P p) throws SQLException;
}
