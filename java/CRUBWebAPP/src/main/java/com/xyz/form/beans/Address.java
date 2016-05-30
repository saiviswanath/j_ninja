package com.xyz.form.beans;

import org.hibernate.validator.constraints.NotEmpty;

import com.xyz.validators.LongNotEmpty;

public class Address {
	@NotEmpty
	private String houseNo;
	@NotEmpty
	private String street;
	@NotEmpty
	private String area;
	@NotEmpty
	private String city;
	@LongNotEmpty
	private Long pin;

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getPin() {
		return pin;
	}

	public void setPin(Long pin) {
		this.pin = pin;
	}
}
