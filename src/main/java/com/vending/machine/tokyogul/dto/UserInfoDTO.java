package com.vending.machine.tokyogul.dto;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserInfoDTO {

	private String name;
	private String phoneNumber;
	private String email;

	public UserInfoDTO() {
		super();
	}

	public UserInfoDTO(String name, String phoneNumber, String email) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmail() {
		return email;
	}

}
