package com.ms.reflection.core;

import java.sql.SQLException;
import java.util.List;

public interface EntityManager {
	<T> void persist(T obj);

	<T> List<T> getAll();

	<T, P> T find(Class<T> clazz, P p) throws SQLException;
}
