package com.vending.machine.tokyogul.service;

import com.vending.machine.tokyogul.dto.UserDTO;
import com.vending.machine.tokyogul.entity.Menu;
import com.vending.machine.tokyogul.entity.OrderDetails;
import com.vending.machine.tokyogul.entity.User;
import com.vending.machine.tokyogul.entity.UserHistory;
import com.vending.machine.tokyogul.exception.UserNotFoundException;
import com.vending.machine.tokyogul.service.impl.BillingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BillingServiceImplTest {
	
	@Mock
	UserService userService;
	
	@Mock
	MenuService menuService;
	
	@InjectMocks
	BillingServiceImpl billingService;
	
	@Test
	void testCalculateTotalBillThrowsExceptionWhenUserNotFound() {
		when(userService.isPresent(anyString())).thenReturn(false);
		assertThrows(UserNotFoundException.class,
				() -> billingService.calculateTotalBill(new OrderDetails(), anyString()));
		verify(userService, times(1)).isPresent(anyString());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"chocolate cake |rice           |pudding    |1728.54",
			"cake           |rice           |pudding    |648.54",
			"rice           |rice           |pudding    |757.08",
			"rice           |PUDDING        |           |648.54",
			"               |               |           |0",
	}, delimiter = '|')
	void testCalculateTotalBillWhenDiscountIsLessThan12(String item1, String item2, String item3,
			double expectedFinalBill) {
		when(userService.isPresent(anyString())).thenReturn(true);
		when(menuService.getAllMenu()).thenReturn(
				List.of(new Menu("category", "rice", 100.50),
						new Menu("category", "pudding", 500),
						new Menu("category", "chocolate cake", 1000)));
		
		User user = new User("name", "number", "email",
				new UserHistory(900, 25000, 40, 7));
		when(userService.getUserByPhoneNumber(anyString())).thenReturn(user);
		
		List<String> items = getItems(item1, item2, item3);
		UserDTO userDTO = billingService
				.calculateTotalBill(new OrderDetails(items), anyString());
		assertEquals(expectedFinalBill, getActualFinalBillTwoDecimal(userDTO));
		assertEquals(10, user.getUserHistory().getLastDiscount());
		assertEquals(25000, user.getUserHistory().getTotalBill());
		assertEquals(40, user.getUserHistory().getTotalVisits());
		
		verify(userService, times(1)).isPresent(anyString());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"chocolate cake |rice           |pudding    |1690.13",
			"cake           |rice           |pudding    |634.13",
			"rice           |rice           |pudding    |740.26",
			"rice           |PUDDING        |           |634.13",
			"               |               |           |0",
	}, delimiter = '|')
	void testCalculateTotalBillWhenDiscountIs12(String item1, String item2, String item3, double expectedFinalBill) {
		when(userService.isPresent(anyString())).thenReturn(true);
		when(menuService.getAllMenu()).thenReturn(
				List.of(new Menu("category", "rice", 100.50),
						new Menu("category", "pudding", 500),
						new Menu("category", "chocolate cake", 1000)));
		
		User user = new User("name", "number", "email",
				new UserHistory(900, 25000, 101, 10));
		when(userService.getUserByPhoneNumber(anyString())).thenReturn(user);
		
		List<String> items = getItems(item1, item2, item3);
		UserDTO userDTO = billingService
				.calculateTotalBill(new OrderDetails(items), anyString());
		assertEquals(expectedFinalBill, getActualFinalBillTwoDecimal(userDTO));
		assertEquals(0, user.getUserHistory().getLastDiscount());
		assertEquals(0, user.getUserHistory().getTotalBill());
		assertEquals(0, user.getUserHistory().getTotalVisits());
		
		verify(userService, times(1)).isPresent(anyString());
	}
	
	@ParameterizedTest
	@CsvSource(value = {
	
	//  TotalBill   |LastBill|LastDis|Visits |discount
			"34000   |710    |10     |5      |12",
			"34000   |710    |7      |5      |0",
			"64000   |100    |10     |5      |0",
			"64000   |100    |10     |101    |12",
			"6000    |100    |10     |101    |12",
			"6000    |100    |10     |100    |0",
			"26000   |780    |7      |5      |10",
			"26000   |780    |10     |5      |0",
			"26000   |780    |5      |5      |0",
			"26000   |180    |7      |5      |0",
			"16000   |780    |5      |5      |7",
			"16000   |780    |7      |5      |0",
			"16000   |780    |0      |5      |0",
			"16000   |180    |5      |5      |0",
			"9600000 |780    |5      |5      |0",
			"16000   |1780   |0      |5      |0",
			"6000    |1780   |0      |5      |5",
			"6000    |1780   |5      |5      |0",
			"6000    |1780   |7      |5      |0",
			"6000    |780    |5      |5      |0",
	}, delimiter = '|')
	void testCalculateDiscount(int totalBill, int lastBill, int lastDiscount, int visits, int expectedDiscount) {
		UserHistory userHistory = new UserHistory(lastBill, totalBill, visits, lastDiscount);
		assertEquals(expectedDiscount, billingService.calculateDiscount(userHistory));
	}
	
	@Test
	void testCalculateTaxAmount(){
		double billBeforeTax = Math.random();
		assertEquals(billBeforeTax * 0.20, billingService.calculateTaxAmount(billBeforeTax));
	}
	private List<String> getItems(String item1, String item2, String item3) {
		List<String> items= new ArrayList<>();
		items.add(item1);
		items.add(item2);
		items.add(item3);
		return items;
	}
	
	private double getActualFinalBillTwoDecimal(UserDTO userDTO) {
		DecimalFormat dcf = new DecimalFormat("#.##");
		double actualFinalBill = Double.parseDouble(dcf.format(userDTO.getFinalBill()));
		return actualFinalBill;
	}
	
}
