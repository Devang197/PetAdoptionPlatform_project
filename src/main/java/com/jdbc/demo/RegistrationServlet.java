package com.jdbc.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAOImpl userDAO;

    @Override
    public void init() throws ServletException {
        // Initialize database connection
        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/online_pet_adoption",
                    "root",
                    "Devang@MySQL"
            );
            userDAO = new UserDAOImpl(conn);
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new ServletException("Database connection initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Display the registration form (registration.jsp)
        request.getRequestDispatcher("registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get user input
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // Validate input
        if (name == null || email == null || password == null || role == null ||
                name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("registration.jsp").forward(request, response);
            return;
        }

        // Create User object
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);

        // Save user to database
        if (userDAO.addUser(user)) {
            request.setAttribute("message", "Registration successful!");
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
        }

        // Forward back to the registration page
        request.getRequestDispatcher("registration.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        // Close database connection if necessary
        // Typically, connection pools handle this automatically
    }
}
