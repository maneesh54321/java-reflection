package com.ms.reflection.model;

import com.ms.reflection.annotation.Column;
import com.ms.reflection.annotation.PrimaryKey;
import com.ms.reflection.annotation.Table;

@Table(name="person")
public class Person {

	@PrimaryKey
	private int id;

	@Column
	private String name;

	@Column
	private int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public Person(int id, String name, int age) {
		this.id = id;
		this.name = name;
		this.age = age;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
}
