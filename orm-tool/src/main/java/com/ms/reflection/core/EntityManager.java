package com.ms.reflection.core;

import java.util.List;

public interface EntityManager {
	<T> void persist(T obj);

	<T> List<T> getAll();
}
