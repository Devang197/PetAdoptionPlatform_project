package com.jdbc.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDAOImplTest {
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private UserDAOImpl userDAO;

    @BeforeEach
    void setUp() throws Exception {
        mockConn = Mockito.mock(Connection.class);
        mockStmt = Mockito.mock(PreparedStatement.class);
        mockRs = Mockito.mock(ResultSet.class);
        userDAO = new UserDAOImpl(mockConn);
    }

    @Test
    void testAddUser() throws Exception {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        User user = new User();
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("password123");
        user.setRole("Adopter");

        userDAO.addUser(user);

        verify(mockStmt, times(1)).setString(1, user.getName());
        verify(mockStmt, times(1)).setString(2, user.getEmail());
        verify(mockStmt, times(1)).setString(3, user.getPassword());
        verify(mockStmt, times(1)).setString(4, user.getRole());
        verify(mockStmt, times(1)).executeUpdate();
    }

    @Test
    void testGetUserById() throws Exception {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("user_id")).thenReturn(1);
        when(mockRs.getString("name")).thenReturn("Alice");
        when(mockRs.getString("email")).thenReturn("alice@example.com");
        when(mockRs.getString("password")).thenReturn("password123");
        when(mockRs.getString("role")).thenReturn("Adopter");

        User user = userDAO.getUserById(1);

        assertNotNull(user);
        assertEquals(1, user.getUserId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("Adopter", user.getRole());
    }

    @Test
    void testUpdateUser() throws Exception {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        User user = new User();
        user.setUserId(1);
        user.setName("Alice");
        user.setEmail("alice@example.com");
        user.setPassword("newPassword123");
        user.setRole("Adopter");

        userDAO.updateUser(user);

        verify(mockStmt, times(1)).setString(1, user.getName());
        verify(mockStmt, times(1)).setString(2, user.getEmail());
        verify(mockStmt, times(1)).setString(3, user.getPassword());
        verify(mockStmt, times(1)).setString(4, user.getRole());
        verify(mockStmt, times(1)).setInt(5, user.getUserId());
        verify(mockStmt, times(1)).executeUpdate();
    }

    @Test
    void testDeleteUser() throws Exception {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        int userId = 1;
        userDAO.deleteUser(userId);

        verify(mockStmt, times(1)).setInt(1, userId);
        verify(mockStmt, times(1)).executeUpdate();
    }

}
