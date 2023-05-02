package com.vending.machine.tokyogul.dto;

import java.util.HashMap;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDTO {

	private String name;
	private String phoneNumber;
	private String email;
	private double finalBill;
	private double discount;
	private double taxDeduction;
	private HashMap<String, Double> selectedItems = new HashMap<String, Double>();

	@Override
	public String toString() {
		return String.format(
				"UserDTO [name=%s, phoneNumber=%s, email=%s, finalBill=%s, discount=%s, taxDeduction=%s, selectedItems=%s]",
				name, phoneNumber, email, finalBill, discount, taxDeduction, selectedItems);
	}

	public UserDTO() {
		super();
	}

	public UserDTO(String name, String phoneNumber, String email, double totalBill, double discount,
			double taxDeduction, HashMap<String, Double> selectedItems) {
		super();
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.finalBill = totalBill;
		this.discount = discount;
		this.taxDeduction = taxDeduction;
		this.selectedItems = selectedItems;
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

	public double getFinalBill() {
		return finalBill;
	}

	public double getDiscount() {
		return discount;
	}

	public double getTaxDeduction() {
		return taxDeduction;
	}

	public HashMap<String, Double> getSelectedItems() {
		return selectedItems;
	}

}