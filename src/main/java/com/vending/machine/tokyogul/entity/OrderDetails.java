package com.vending.machine.tokyogul.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

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

	private List<String> items;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonManagedReference
	private User user;

	public OrderDetails() {
		super();
	}

	public OrderDetails(List<String> item) {
		super();
		this.items = item;
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}

}
