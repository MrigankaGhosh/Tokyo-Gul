package com.vending.machine.tokyogul.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String category;
	private String item;
	private double price;

	public Menu() {
		super();
	}

	public Menu(String category, String item, double price) {
		super();
		this.category = category;
		this.item = item;
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("Menu [id=%s, category=%s, item=%s, price=%s]", id, category, item, price);
	}

	public int getId() {
		return id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
