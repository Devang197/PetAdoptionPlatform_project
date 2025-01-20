package com.jdbc.demo;

import java.util.List;

public class UserService {
    private final IUserDAO userDAO;

    public UserService(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean addUser(User user) {
        if (user.getName() == null || user.getEmail() == null || user.getPassword() == null) {
            throw new IllegalArgumentException("User information is incomplete");
        }
        return userDAO.addUser(user);
    }

    public User getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
        if (user.getUserId() == 0) {
            throw new IllegalArgumentException("User ID must be provided");
        }
        userDAO.updateUser(user);
    }

    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }
}
