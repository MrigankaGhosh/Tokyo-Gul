package com.vending.machine.tokyogul.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String item;

	@Column(name = "user_id")
	private int userId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonManagedReference
	private User user;

	public OrderDetails() {
		super();
	}

	public OrderDetails(String item, int userId) {
		super();
		this.item = item;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
