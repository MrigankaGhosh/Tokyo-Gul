package com.vending.machine.tokyogul.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserHistory {

	@Column(nullable = false, columnDefinition = "double default 0")
	private double lastBill;

	@Column(nullable = false, columnDefinition = "double default 0")
	private double totalBill;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private int totalVisits;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private int lastDiscount;

	@Override
	public String toString() {
		return String.format("UserHistory [lastBill=%s, totalBill=%s, totalVisits=%s, lastDiscount=%s]", lastBill,
				totalBill, totalVisits, lastDiscount);
	}

	public UserHistory() {
		super();
	}

	public UserHistory(double lastBill, double totalBill, int totalVisits, int lastDiscount) {
		super();
		this.lastBill = lastBill;
		this.totalBill = totalBill;
		this.totalVisits = totalVisits;
		this.lastDiscount = lastDiscount;
	}

	public double getLastBill() {
		return lastBill;
	}

	public void setLastBill(double lastBill) {
		this.lastBill = lastBill;
	}

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}

	public int getTotalVisits() {
		return totalVisits;
	}

	public void setTotalVisits(int totalVisits) {
		this.totalVisits = totalVisits;
	}

	public int getLastDiscount() {
		return lastDiscount;
	}

	public void setLastDiscount(int lastDiscount) {
		this.lastDiscount = lastDiscount;
	}

}
