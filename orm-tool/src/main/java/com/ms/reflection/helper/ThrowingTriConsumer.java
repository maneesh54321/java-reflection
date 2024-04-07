package com.ms.reflection.helper;

public interface ThrowingTriConsumer<T, V, X, Ex extends IllegalAccessException> {
	void accept(T t, V v, X x) throws Ex;
}
