package com.vending.machine.tokyogul.service;

import org.springframework.stereotype.Service;

import com.vending.machine.tokyogul.entity.Menu;

@Service
public interface MenuService {

	public Menu getMenuByItem(String item);

	public Menu getMenuByCategory(String category);
	
}
