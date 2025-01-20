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
import java.util.List;

@WebServlet("/applications")
public class ApplicationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ApplicationDAOImpl applicationDAO;

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
            applicationDAO = new ApplicationDAOImpl(conn);
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new ServletException("Database connection initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "list":
                    listApplications(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteApplication(request, response);
                    break;
                default:
                    listApplications(request, response);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error processing request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the action parameter
        String action = request.getParameter("action");

        // Handle Add/Update/Delete actions
        switch (action) {
            case "add":
                addApplication(request, response);
                break;
            case "update":
                updateApplication(request, response);
                break;
            case "delete":
                deleteApplication(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action.");
                break;
        }
    }

    private void listApplications(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Application> applications = applicationDAO.getAllApplications();
        request.setAttribute("applicationList", applications);
        request.getRequestDispatcher("application-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int applicationId = Integer.parseInt(request.getParameter("id"));
        Application existingApplication = applicationDAO.getApplicationById(applicationId);
        request.setAttribute("application", existingApplication);
        request.getRequestDispatcher("application-form.jsp").forward(request, response);
    }

    private void addApplication(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int adopterId = Integer.parseInt(request.getParameter("adopterId"));
        int petId = Integer.parseInt(request.getParameter("petId"));
        String status = request.getParameter("status");
        String applicationDateStr = request.getParameter("applicationDate");

        // Ensure the date string is valid
        if (applicationDateStr != null && !applicationDateStr.isEmpty()) {
            try {
                // Ensure correct format: yyyy-MM-dd HH:mm:ss
                applicationDateStr = applicationDateStr.replace("T", " "); // Convert T to space for proper format
                java.sql.Timestamp applicationDate = java.sql.Timestamp.valueOf(applicationDateStr);

                Application application = new Application();
                application.setAdopterId(adopterId);
                application.setPetId(petId);
                application.setStatus(status);
                application.setApplicationDate(applicationDate);

                applicationDAO.addApplication(application);
                response.sendRedirect("applications?action=list");
            } catch (IllegalArgumentException e) {
                // Log the error and show a message to the user
                System.out.println("Invalid date format: " + e.getMessage());
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid date format");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Application date is missing");
        }
    }


    private void updateApplication(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int applicationId = Integer.parseInt(request.getParameter("id"));
        int adopterId = Integer.parseInt(request.getParameter("adopterId"));
        int petId = Integer.parseInt(request.getParameter("petId"));
        String status = request.getParameter("status");
        java.sql.Timestamp applicationDate = java.sql.Timestamp.valueOf(request.getParameter("applicationDate"));

        Application application = new Application();
        application.setApplicationId(applicationId);
        application.setAdopterId(adopterId);
        application.setPetId(petId);
        application.setStatus(status);
        application.setApplicationDate(applicationDate);

        applicationDAO.updateApplication(application);
        response.sendRedirect("applications?action=list");
    }

    private void deleteApplication(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int applicationId = Integer.parseInt(request.getParameter("id"));
        applicationDAO.deleteApplication(applicationId);
        response.sendRedirect("applications?action=list");
    }

    @Override
    public void destroy() {
        // Close database connection if necessary
        // Typically, connection pools handle this automatically
    }
}
