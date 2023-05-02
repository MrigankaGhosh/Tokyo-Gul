package com.vending.machine.tokyogul.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vending.machine.tokyogul.entity.Menu;
import com.vending.machine.tokyogul.repository.MenuRepository;
import com.vending.machine.tokyogul.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuRepository repository;
	
	@Override
	public List<Menu> getMenuByItem(String item) {
		return repository.findByItem(item);
	}

	@Override
	public List<Menu> getMenuByCategory(String category) {
		return repository.findByCategory(category);
	}
	
	@Override
	public List<Menu> getAllMenu() {
		return repository.findAll();
	}

	@Override
	public void addMenuItem(Menu menu) {
		repository.save(menu);
		
	}

	@Override
	public void deleteMenuItem(int id) {
		repository.deleteById(id);
		
	}


}
