package com.vending.machine.tokyogul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vending.machine.tokyogul.dto.UserDTO;
import com.vending.machine.tokyogul.entity.Menu;
import com.vending.machine.tokyogul.entity.OrderDetails;
import com.vending.machine.tokyogul.entity.User;
import com.vending.machine.tokyogul.service.BillingService;
import com.vending.machine.tokyogul.service.MenuService;
import com.vending.machine.tokyogul.service.UserService;

@RestController
public class Controller {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private BillingService billingService;

	@Autowired
	private UserDTO userDTO;

	@PostMapping("/register")
	public void registerUser(@RequestBody User user) {
		userService.addUser(user);
	}

	@GetMapping("/menu")
	public List<Menu> showMenu() {
		return menuService.getAllMenu();
	}

	@PostMapping("/menu")
	public void saveOrderByUser(@RequestBody OrderDetails orderDetails, @RequestBody String phoneNumber) {
		userDTO = billingService.calculateTotalBill(orderDetails, phoneNumber);
	}

	@GetMapping("/bill")
	public UserDTO showBill() {
		return userDTO;
	}

}
