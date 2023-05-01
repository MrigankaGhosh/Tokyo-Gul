package com.vending.machine.tokyogul.service;

import org.springframework.stereotype.Service;

import com.vending.machine.tokyogul.dto.UserDTO;
import com.vending.machine.tokyogul.entity.OrderDetails;
import com.vending.machine.tokyogul.entity.UserHistory;

@Service
public interface BillingService {

	public UserDTO calculateTotalBill(OrderDetails orderDetails, String phoneNumber);

	public int calculateDiscount(UserHistory userHistory);
	
	public double calculateTaxAmount(double billBeforeTax);

}
