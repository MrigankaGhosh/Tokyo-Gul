package com.vending.machine.tokyogul.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vending.machine.tokyogul.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByPhoneNumber(String phoneNumber);

	public User findByName(String name);

	public void deleteAllOrderDetailsById(int id);
}
