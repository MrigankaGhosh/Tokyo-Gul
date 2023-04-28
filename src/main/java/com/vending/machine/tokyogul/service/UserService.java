package com.vending.machine.tokyogul.service;

import org.springframework.stereotype.Service;

import com.vending.machine.tokyogul.entity.User;
import com.vending.machine.tokyogul.entity.UserHistory;

@Service
public interface UserService {
	
	public boolean isPresent(String phoneNumber);

	public boolean addUser(User user);
	
	public boolean deleteUser(User user);
	
	public User getUserByPhoneNumber(String phoneNumber);
	
	public UserHistory getUserHistory(String phoneNumber);
	
	public boolean updateUserHistory(UserHistory userHistory);

}
