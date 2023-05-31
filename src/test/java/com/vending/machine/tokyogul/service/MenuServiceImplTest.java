package com.vending.machine.tokyogul.service;

import com.vending.machine.tokyogul.entity.Menu;
import com.vending.machine.tokyogul.repository.MenuRepository;
import com.vending.machine.tokyogul.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MenuServiceImplTest {
	@Mock
	private MenuRepository menuRepository;
	
	@InjectMocks
	private MenuServiceImpl menuService;
	
	@ParameterizedTest
	@CsvSource(value = {
			"category   |availableItem  |10.0   |ture   |false",
			"category   |availableItem  |10.0   |ture   |true",
			"           |unavailableItem|       |false  |false",
			"           |               |       |false  |false",
	}, delimiter = '|')
	void testGetMenuByItem(String category, String item, Double price, boolean isAvailable, boolean moreThanOneMenu) {
		ArrayList<Menu> menuList =
				getMenus(category, item, price, isAvailable, moreThanOneMenu);
		when(menuRepository.findByItem(item)).thenReturn(menuList);
		assertEquals(menuList, menuService.getMenuByItem(item));
		verify(menuRepository, times(1)).findByItem(item);
	}
	@ParameterizedTest
	@CsvSource(value = {
			"availableCategory  |item      |10.0   |ture   |false",
			"availableCategory  |item      |10.0   |ture   |true",
			"unavailableCategory|          |       |false  |false",
			"                   |          |       |false  |false",
	}, delimiter = '|')
	void testGetMenuByCategory(String category, String item, Double price, boolean isAvailable, boolean moreThanOneMenu) {
		ArrayList<Menu> menuList =
				getMenus(category, item, price, isAvailable, moreThanOneMenu);
		when(menuRepository.findByCategory(category)).thenReturn(menuList);
		assertEquals(menuList, menuService.getMenuByCategory(category));
		verify(menuRepository, times(1)).findByCategory(category);
	}
	
	@Test
	void testGetAllMenu(){
		List<Menu> menus = List.of(new Menu());
		when(menuRepository.findAll()).thenReturn(menus);
		assertEquals(menus, menuService.getAllMenu());
		verify(menuRepository, times(1)).findAll();
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"category   |item   |10.9",
			"           |item   |10.9",
			"category   |       |10.9",
	}, delimiter = '|')
	void testAddMenuItem(String category, String item, double price){
		Menu menu = new Menu(category, item, price);
		when(menuRepository.save(menu)).thenReturn(menu);
		menuService.addMenuItem(menu);
		verify(menuRepository, times(1)).save(menu);
	}
	
	@Test
	void testDeleteMenuItem(){
		int id = anyInt();
		menuService.deleteMenuItem(id);
		verify(menuRepository, times(1)).deleteById(id);
	}
	
	private ArrayList<Menu> getMenus(String category, String item, Double price, boolean isAvailable,
			boolean moreThanOneMenu) {
		ArrayList<Menu> menuList= null;
		if(isAvailable) {
			menuList = new ArrayList<>();
			Menu menu1 = new Menu(category, item, price);
			menuList.add(menu1);
			if(moreThanOneMenu) {
				Menu menu2 = new Menu(category, item, price);
				menuList.add(menu2);
			}
		}
		return menuList;
	}
}
