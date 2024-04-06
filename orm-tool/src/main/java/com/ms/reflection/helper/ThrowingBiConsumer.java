package com.ms.reflection.helper;

@FunctionalInterface
public interface ThrowingBiConsumer<T, R, Ex extends Throwable> {

	void accept(T t, R r) throws Ex;
}