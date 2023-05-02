package com.vending.machine.tokyogul.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vending.machine.tokyogul.entity.Menu;

@Service
public interface MenuService {

	public List<Menu> getMenuByItem(String item);

	public List<Menu> getMenuByCategory(String category);
	
	public List<Menu> getAllMenu();
	
	public void addMenuItem(Menu menu);
	
	public void deleteMenuItem(int id);
}
