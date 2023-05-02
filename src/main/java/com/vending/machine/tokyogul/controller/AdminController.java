package com.vending.machine.tokyogul.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vending.machine.tokyogul.entity.Menu;
import com.vending.machine.tokyogul.service.MenuService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MenuService menuService;
	
	@GetMapping("/menu")
	public List<Menu> getAllMenu() {
		return menuService.getAllMenu();
	}
	
	@PostMapping("/menu")
	public ResponseEntity<HttpStatus> addMenu(@RequestBody Menu menu) {
		menuService.addMenuItem(menu);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@DeleteMapping("/menu")
	public ResponseEntity<HttpStatus> deleteMenuItem(@RequestParam int id) {
		menuService.deleteMenuItem(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
