package com.vending.machine.tokyogul.service;

import com.vending.machine.tokyogul.entity.User;
import com.vending.machine.tokyogul.entity.UserHistory;
import com.vending.machine.tokyogul.repository.UserRepository;
import com.vending.machine.tokyogul.service.impl.UserServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @ParameterizedTest
    @CsvSource(value = {
            "           |false",
            "present    |true",
            "absent     |false",
    }, delimiter = '|')
    void testIsPresent(String phoneNumber, boolean expected) {
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(expected ? new User() : null);
        assertEquals(expected, userService.isPresent(phoneNumber));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "           |phone1     |email1     |false",
            "name1      |           |email1     |false",
            "name1      |phone1     |           |false",
            "name1      |phone1     |email1     |false",
            "name2      |phone2     |email2     |true",
    }, delimiter = '|')
    void testAddUser(String name, String phoneNumber, String email, boolean hasUserHistory) {
        when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        User user = new User(name, phoneNumber, email, hasUserHistory ? new UserHistory() : null);
        userService.addUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "           |phone1     |email1     |false",
            "name1      |           |email1     |false",
            "name1      |phone1     |           |false",
            "name1      |phone1     |email1     |false",
            "name2      |phone2     |email2     |true",
    }, delimiter = '|')
    void testUpdateUser(String name, String phoneNumber, String email, boolean hasUserHistory) {
        when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        User user = new User(name, phoneNumber, email, hasUserHistory ? new UserHistory() : null);
        userService.updateUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "           |phone1     |email1     |false",
            "name1      |           |email1     |false",
            "name1      |phone1     |           |false",
            "name1      |phone1     |email1     |false",
            "name2      |phone2     |email2     |true",
    }, delimiter = '|')
    void testDeleteUser(String name, String phoneNumber, String email, boolean hasUserHistory) {
        User user = new User(name, phoneNumber, email, hasUserHistory ? new UserHistory() : null);
        assertFalse(userService.deleteUser(user));
        verify(userRepository, times(1)).delete(user);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "           |false",
            "present    |true",
            "absent     |false",
    }, delimiter = '|')
    void testGetUserByPhoneNumber(String phoneNumber, boolean isUserExpected) {
        User user = new User("user", "number", "mail");
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(isUserExpected ? user : null);
        assertEquals(isUserExpected ? user : null, userService.getUserByPhoneNumber(phoneNumber));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "           |false  |false  |       |       |   |   ",
            "present    |true   |true   |10.6   |40.8   |7  |8  ",
            "present    |true   |false  |       |       |   |   ",
            "absent     |false  |false  |       |       |   |   ",
    }, delimiter = '|')
    void testGetUserHistory(String phoneNumber, boolean isUserExpected, boolean isUserHistoryNotNull,
                            Double lastBill, Double totalBill, Integer totalVisits, Integer lastDiscount) {
        UserHistory userHistory =
                isUserHistoryNotNull ? new UserHistory(lastBill, totalBill, totalVisits, lastDiscount) : null;
        User user = new User("user", phoneNumber, "mail", userHistory);
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(isUserExpected ? user : null);
        assertEquals(userHistory, userService.getUserHistory(phoneNumber));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "           |false  |false  |       |       |   |   ",
            "present    |true   |true   |10.6   |40.8   |7  |8  ",
            "present    |true   |false  |       |       |   |   ",
            "absent     |false  |false  |       |       |   |   ",
    }, delimiter = '|')
    void testUpdateUserHistory(String phoneNumber, boolean isUserExpected, boolean isUserHistoryNotNull,
                               Double lastBill, Double totalBill, Integer totalVisits, Integer lastDiscount) {
        UserHistory userHistory =
                isUserHistoryNotNull ? new UserHistory(lastBill, totalBill, totalVisits, lastDiscount) : null;
        User user = new User("user", phoneNumber, "mail",
                new UserHistory(0, 0, 0, 0));
        when(userRepository.findByPhoneNumber(phoneNumber)).thenReturn(isUserExpected ? user : null);
        assertEquals(isUserExpected, userService.updateUserHistory(userHistory, phoneNumber));
        if(isUserExpected){
            assertEquals(userHistory, user.getUserHistory());
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
            "12345",
            "98077",
    }, delimiter = '|')
    void testDeleteAllOrderDetails(int id) {
        userService.deleteAllOrderDetails(id);
        verify(userRepository, times(1)).deleteAllOrderDetailsById(id);
    }
}
