package com.ms.orm.helper;

public interface ThrowingBiFunction<T, V, R, Ex extends Throwable> {
	R apply(T t, V v) throws Ex;
}