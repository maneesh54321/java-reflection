package com.ms.reflection.model;

import com.ms.reflection.annotation.Column;
import com.ms.reflection.annotation.PrimaryKey;
import com.ms.reflection.annotation.Table;

@Table(name="person")
public class Person {

	@PrimaryKey
	private long id;

	@Column
	private String name;

	@Column
	private int age;

	public Person() {
	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Person(long id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person{" +
				"id=" + id +
				", name='" + name + '\'' +
				", age=" + age +
				'}';
	}
}
