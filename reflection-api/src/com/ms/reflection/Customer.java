package com.ms.reflection;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Customer extends Person {
	private LocalDateTime dob;

	public String address;

	public Customer(String name, int age, LocalDateTime dob) {
		super(name, age);
		this.dob = dob;
	}

	public static Customer of(String name, int age, LocalDateTime dob){
		return new Customer(name, age, dob);
	}

	private long calculateAge(LocalDateTime dob){
		return Duration.between(LocalDateTime.now(), dob).get(ChronoUnit.YEARS);
	}

	public LocalDateTime getDob() {
		return dob;
	}

	public void setDob(LocalDateTime dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
