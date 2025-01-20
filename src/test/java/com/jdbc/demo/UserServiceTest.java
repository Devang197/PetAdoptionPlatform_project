package com.jdbc.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private IUserDAO mockUserDAO;
    private UserService userService;

    @BeforeEach
    void setUp() {
        mockUserDAO = Mockito.mock(IUserDAO.class);
        userService = new UserService(mockUserDAO);
    }

    @Test
    void testAddUser() {
        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("password123");
        user.setRole("Adopter");

        when(mockUserDAO.addUser(user)).thenReturn(true);
        boolean result = userService.addUser(user);
        assertTrue(result);
    }

    @Test
    void testAddUserIncompleteInfo() {
        User user = new User();
        user.setName("Alice");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.addUser(user));
        assertEquals("User information is incomplete", exception.getMessage());
    }

    @Test
    void testGetUserByIdSuccess() {
        User user = new User();
        user.setUserId(1);
        user.setName("Alice");

        when(mockUserDAO.getUserById(1)).thenReturn(user);

        User retrievedUser = userService.getUserById(1);
        assertNotNull(retrievedUser);
        assertEquals("Alice", retrievedUser.getName());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(mockUserDAO.getUserById(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1));
        assertEquals("User not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateUser() {
        User user = new User();
        user.setUserId(1);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("password123");
        user.setRole("Adopter");

        userService.updateUser(user);
        verify(mockUserDAO, times(1)).updateUser(user);
    }

    @Test
    void testDeleteUser() {
        int userId = 1;
        userService.deleteUser(userId);
        verify(mockUserDAO, times(1)).deleteUser(userId);
    }
}
