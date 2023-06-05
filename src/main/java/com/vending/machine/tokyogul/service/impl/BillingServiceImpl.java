package com.vending.machine.tokyogul.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vending.machine.tokyogul.dto.UserDTO;
import com.vending.machine.tokyogul.entity.Menu;
import com.vending.machine.tokyogul.entity.OrderDetails;
import com.vending.machine.tokyogul.entity.User;
import com.vending.machine.tokyogul.entity.UserHistory;
import com.vending.machine.tokyogul.exception.UserNotFoundException;
import com.vending.machine.tokyogul.service.BillingService;
import com.vending.machine.tokyogul.service.MenuService;
import com.vending.machine.tokyogul.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class BillingServiceImpl implements BillingService {

	@Autowired
	private User user;

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Transactional
	@Override
	public UserDTO calculateTotalBill(OrderDetails orderDetails, String phoneNumber) {

		// Check if the User is present
		if (userService.isPresent(phoneNumber) == false) {
			throw new UserNotFoundException("User not present");
		}

		double billBeforeDiscount = 0;
		HashMap<String, Double> selectedItems = new HashMap<>();

		this.user = userService.getUserByPhoneNumber(phoneNumber);
		
		System.out.println(orderDetails.toString());

		// Save the total amount in a variable and save the items+prices in a hashmap
		List<String> itemList = new ArrayList<>();
		String[] itemArray = null;
		for(String buffer: orderDetails.getItems()) {
			itemArray = buffer.split(",");
		}
		for(int i=0;i<itemArray.length;i++) {
			itemList.add(itemArray[i]);
		}
		for (String item : itemList) {
			System.out.println(item+"................");
			for (Menu menuItem : menuService.getAllMenu()) {
				if (item.equalsIgnoreCase(menuItem.getItem())) {
					billBeforeDiscount += menuItem.getPrice();
					selectedItems.put(item, menuItem.getPrice());
				}
			}
		}

		// Calculating total Bill
		double discountPercent = calculateDiscount(this.user.getUserHistory());
		double discountAmount = (discountPercent / 100) * billBeforeDiscount;
		double billBeforeTax = billBeforeDiscount - discountAmount;
		double finalBill = billBeforeTax + calculateTaxAmount(billBeforeTax);

		UserDTO userDTO = new UserDTO(this.user.getName(), phoneNumber, this.user.getEmail(), finalBill, discountAmount,
				calculateTaxAmount(billBeforeTax), selectedItems);

		UserHistory userHistory = this.user.getUserHistory();
		// Update user history the already exceeded 12%
		userHistory.setLastDiscount(calculateDiscount(this.user.getUserHistory()));
		if (userHistory.getLastDiscount() == 12) {
			userHistory.setLastDiscount(0);
			userHistory.setTotalBill(0);
			userHistory.setTotalVisits(0);
			userService.deleteAllOrderDetails(this.user.getId());
		}
		userHistory.setLastBill(finalBill);
		userHistory.setTotalBill(finalBill + this.user.getUserHistory().getTotalBill());
		userHistory.setTotalVisits(this.user.getUserHistory().getTotalVisits() + 1);
		userHistory.setLastDiscount((int) discountPercent);

		return userDTO;
	}

	@Transactional
	@Override
	public int calculateDiscount(UserHistory userHistory) {
		if ((userHistory.getTotalBill() > 30000 && userHistory.getLastBill() > 700
				&& userHistory.getLastDiscount() == 10) || userHistory.getTotalVisits() > 100) {
			return 12;
		}
		if (userHistory.getTotalBill() > 20000 && userHistory.getLastBill() > 700 && userHistory.getTotalBill() < 30000
				&& userHistory.getLastDiscount() == 7) {
			return 10;
		}
		if (userHistory.getTotalBill() > 10000 && userHistory.getLastBill() > 700 && userHistory.getTotalBill() < 20000
				&& userHistory.getLastDiscount() == 5) {
			return 7;
		}
		if (userHistory.getTotalBill() > 5000 && userHistory.getLastBill() > 1000 && userHistory.getTotalBill() < 10000
				&& userHistory.getLastDiscount() == 0) {
			return 5;
		}
		return 0;
	}

	@Override
	public double calculateTaxAmount(double billBeforeTax) {
		return billBeforeTax * 0.20;
	}

}
