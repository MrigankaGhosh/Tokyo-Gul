package com.vending.machine.tokyogul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vending.machine.tokyogul.dto.StringListDTO;
import com.vending.machine.tokyogul.dto.UserDTO;
import com.vending.machine.tokyogul.dto.UserInfoDTO;
import com.vending.machine.tokyogul.entity.Menu;
import com.vending.machine.tokyogul.entity.OrderDetails;
import com.vending.machine.tokyogul.entity.User;
import com.vending.machine.tokyogul.entity.UserHistory;
import com.vending.machine.tokyogul.service.BillingService;
import com.vending.machine.tokyogul.service.MenuService;
import com.vending.machine.tokyogul.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private BillingService billingService;

	@Autowired
	private UserDTO userDTO;

	@PostMapping("/register")
	public ResponseEntity<HttpStatus> registerUser(@RequestBody UserInfoDTO userInfoDTO) {
		User user = new User(userInfoDTO.getName(), userInfoDTO.getPhoneNumber(), userInfoDTO.getEmail());
		if (userService.isPresent(user.getPhoneNumber()) == false) {
			UserHistory userHistory = new UserHistory(0, 0, 0, 0);
			user.setUserHistory(userHistory);
		} else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		userService.addUser(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/menu")
	public List<Menu> showMenu() {
		return menuService.getAllMenu();
	}

	@PostMapping("/menu")
	public ResponseEntity<HttpStatus> saveOrderByUser(@RequestBody StringListDTO stringListDTO,
			@RequestParam String phoneNumber) {
		List<String> items = stringListDTO.getStrings();
		System.out.println(items);
		OrderDetails orderDetails = new OrderDetails(items);
		userDTO = billingService.calculateTotalBill(orderDetails, phoneNumber);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/bill")
	public UserDTO showBill() {
		return userDTO;
	}

}
