package com.xyz.form.beans;

import javax.validation.constraints.Size;

public class UpdateInputBean {
	@Size(min = 2, max = 30)
	private String firstName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Size(min = 2, max = 30)
	private String lastName;

	@Override
	public String toString() {
		return "UpdateInputBean [firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}
}
