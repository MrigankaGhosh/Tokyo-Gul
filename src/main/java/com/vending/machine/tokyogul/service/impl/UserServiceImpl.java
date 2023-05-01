package com.vending.machine.tokyogul.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.vending.machine.tokyogul.entity.User;
import com.vending.machine.tokyogul.entity.UserHistory;
import com.vending.machine.tokyogul.repository.UserRepository;
import com.vending.machine.tokyogul.service.UserService;

public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public boolean isPresent(String phoneNumber) {
		if (repository.findByPhoneNumber(phoneNumber) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void addUser(User user) {
		if (isPresent(user.getPhoneNumber()) == true) {
			updateUser(user);
		}
		repository.save(user);
	}

	@Override
	public void updateUser(User user) {
		repository.save(user);
	}

	@Override
	public boolean deleteUser(User user) {
		repository.delete(user);
		return false;
	}

	@Override
	public User getUserByPhoneNumber(String phoneNumber) {
		if (isPresent(phoneNumber) == true) {
			return repository.findByPhoneNumber(phoneNumber);
		}
		return null;
	}

	@Override
	public UserHistory getUserHistory(String phoneNumber) {
		if (isPresent(phoneNumber) == true) {
			User user = getUserByPhoneNumber(phoneNumber);
			UserHistory userHistory = user.getUserHistory();
			return userHistory;
		}
		return null;
	}

	@Override
	public boolean updateUserHistory(UserHistory userHistory, String phoneNumber) {
		if (isPresent(phoneNumber) == true) {
			User user = getUserByPhoneNumber(phoneNumber);
			user.setUserHistory(userHistory);
			return true;
		}
		return false;
	}

	@Override
	public void deleteAllOrderDetails(int id) {
		repository.deleteAllOrderDetailsById(id);
		
	}

}
