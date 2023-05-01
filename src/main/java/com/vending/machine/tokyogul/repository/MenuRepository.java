package com.vending.machine.tokyogul.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vending.machine.tokyogul.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

	public List<Menu> findByItem(String item);

	public List<Menu> findByCategory(String category);

}
