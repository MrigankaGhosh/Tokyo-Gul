package com.vending.machine.tokyogul.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Entity
public class User {

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private int id;

	@Column(name = "user_name", nullable = false)
	@Size(min = 3, message = "Name should have atleast 3 characters")
	private String name;

	@Column(name = "phone_number", nullable = false)
	@Size(min = 10, max = 10, message = "Phone Number should have exactly 10 digits")
	private String phoneNumber;

	@Column(nullable = false)
	@Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
			+ "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
	private String email;

	@Embedded
	private UserHistory userHistory;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetails> orderdetails = new ArrayList<>();

	public User() {
		super();
	}

	public User(String name, String phoneNumber, String email, UserHistory userHistory) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.userHistory = userHistory;
	}

	@Override
	public String toString() {
		return String.format("User [id=%s, name=%s, phoneNumber=%s, email=%s, userHistory=%s, orderdetails=%s]", id,
				name, phoneNumber, email, orderdetails);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserHistory getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(UserHistory userHistory) {
		this.userHistory = userHistory;
	}

	public List<OrderDetails> getOrderdetails() {
		return orderdetails;
	}

	public void setOrderdetails(List<OrderDetails> orderdetails) {
		this.orderdetails = orderdetails;
	}

}
