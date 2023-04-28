package com.vending.machine.tokyogul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vending.machine.tokyogul.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

	public Menu findByItem(String item);

	public Menu findByCategory(String category);

}
