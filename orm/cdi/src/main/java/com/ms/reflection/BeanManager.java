package com.ms.reflection;

import com.ms.reflection.annotation.Inject;
import com.ms.reflection.annotation.Provides;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BeanManager {

	private final List<Class<?>> allClasses;

	private final Map<Class<?>, Supplier<Object>> providers;

	public BeanManager(List<Class<?>> allClasses) {
		this.allClasses = allClasses;
		this.providers = new HashMap<>();
		init();
	}

	private void init() {
		for (Class<?> clazz : allClasses) {
			Method[] methods = clazz.getMethods();
			Arrays.stream(methods).forEach(m -> {
				Provides provides = m.getAnnotation(Provides.class);
				if (provides != null) {
					providers.put(m.getReturnType(), () -> {
						try {
							if (Modifier.isStatic(m.getModifiers())) {
								return m.invoke(null);
							} else {
								Object obj = clazz.getConstructor().newInstance();
								return m.invoke(obj);
							}
						} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
								 NoSuchMethodException e) {
							throw new RuntimeException(e);
						}
					});
				}
			});
		}
	}

	public <T> T getInstance(Class<T> clazz) {
		T obj;
		try {
			obj = clazz.getConstructor().newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field: fields){
				Inject inject = field.getAnnotation(Inject.class);
				if(inject != null) {
					field.setAccessible(true);
					if(providers.containsKey(field.getType())){
						field.set(obj, providers.get(field.getType()).get());
					} else {
						field.set(obj, getInstance(field.getType()));
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
}
