package com.vending.machine.tokyogul.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class UserHistory {

	private double lastBill;
	private double totalBill;
	private int totalVisits;

	@Override
	public String toString() {
		return String.format("UserHistory [lastBill=%s, totalBill=%s, totalVisits=%s]", lastBill, totalBill,
				totalVisits);
	}

	public UserHistory() {
		super();
	}

	public UserHistory(double lastBill, double totalBill, int totalVisits) {
		super();
		this.lastBill = lastBill;
		this.totalBill = totalBill;
		this.totalVisits = totalVisits;
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

}
