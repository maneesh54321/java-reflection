package com.ms.reflection.helper;

public interface ThrowingBiFunction<T, V, R, Ex extends Throwable> {
	R apply(T t, V v) throws Ex;
}