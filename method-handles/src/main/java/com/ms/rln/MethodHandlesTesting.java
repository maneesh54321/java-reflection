package com.ms.rln;

import com.ms.rln.model.Person;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class MethodHandlesTesting {

	public static void main(String[] args)
			throws Throwable {
		Lookup lookup = MethodHandles.lookup();

		Class<?> clazz = lookup.findClass("com.ms.rln.model.Person");
		System.out.println(clazz.getSimpleName());

		MethodType parameterizedConstructorMethodType = MethodType.methodType(void.class, String.class, int.class);
		MethodHandle parameterizedConstructorMethodHandle = lookup.findConstructor(clazz, parameterizedConstructorMethodType);
		Person ram = (Person) parameterizedConstructorMethodHandle.invoke("Ram", 32);
		System.out.println("Ram made using parameterized constructor: " + ram);

		MethodType emptyConstructorMethodType = MethodType.methodType(void.class);
		MethodHandle emptyConstructorMethodHandle = lookup.findConstructor(clazz, emptyConstructorMethodType);
		Person mohan = (Person) emptyConstructorMethodHandle.invoke();
		System.out.println("Mohan made using empty constructor before setting fields: " + mohan);

		MethodType setNameMethodType = MethodType.methodType(void.class, String.class);
		MethodHandle setNameMethodHandle = lookup.findVirtual(clazz, "setName", setNameMethodType);

		MethodType setAgeMethodType = MethodType.methodType(void.class, int.class);
		MethodHandle setAgeMethodHandle = lookup.findVirtual(clazz, "setAge", setAgeMethodType);

		setNameMethodHandle.invoke(mohan, "Mohan");
		setAgeMethodHandle.invoke(mohan, 23);

		MethodType getNameMethodType = MethodType.methodType(String.class);
		MethodHandle getNameMethodHandle = lookup.findVirtual(clazz, "getName", getNameMethodType);

		MethodType getAgeMethodType = MethodType.methodType(int.class);
		MethodHandle getAgeMethodHandle = lookup.findVirtual(clazz, "getAge", getAgeMethodType);

		String name = (String) getNameMethodHandle.invoke(mohan);
		int age = (int) getAgeMethodHandle.invoke(mohan);
		System.out.printf(
				"Mohan made using empty constructor after setting fields: Person{name=%s, age=%d}%n", name, age);

		Person sita = (Person) emptyConstructorMethodHandle.invoke();

		Lookup privateLookup = MethodHandles.privateLookupIn(clazz, lookup);

		MethodHandle nameSetter = privateLookup.findSetter(clazz, "name", String.class);
		MethodHandle ageSetter = privateLookup.findSetter(clazz, "age", int.class);

		nameSetter.invoke(sita, "Sita");
		ageSetter.invoke(sita, 22);

		MethodHandle nameGetter = privateLookup.findGetter(clazz, "name", String.class);
		MethodHandle ageGetter = privateLookup.findGetter(clazz, "age", int.class);

		name = (String) nameGetter.invoke(sita);
		age = (int) ageGetter.invoke(sita);
		System.out.printf(
				"Sita made using empty constructor after setting fields: Person{name=%s, age=%d}%n", name, age);

	}
}
