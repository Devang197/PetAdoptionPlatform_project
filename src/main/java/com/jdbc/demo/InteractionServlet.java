package com.jdbc.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.*;
import java.sql.*;
import java.util.List;

@WebServlet("/interactions")
public class InteractionServlet extends HttpServlet {
    private IInteractionDAO interactionDAO;

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
            interactionDAO = new InteractionDAOImpl(conn);
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

        if (action == null) {
            // Show all interactions
           
            request.setAttribute("message", "No interactions to display.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("interaction-list.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("edit")) {
            // Show a single interaction for editing
            int interactionId = Integer.parseInt(request.getParameter("id"));
            Interaction interaction = interactionDAO.getInteractionById(interactionId);
            request.setAttribute("interaction", interaction);
            RequestDispatcher dispatcher = request.getRequestDispatcher("interaction-form.jsp");
            dispatcher.forward(request, response);
        } else if (action.equals("delete")) {
            // Delete the interaction
            int interactionId = Integer.parseInt(request.getParameter("id"));
            interactionDAO.deleteInteraction(interactionId);
            response.sendRedirect("interactions");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int shelterId = Integer.parseInt(request.getParameter("shelterId"));
        int adopterId = Integer.parseInt(request.getParameter("adopterId"));
        String message = request.getParameter("message");
        Timestamp timestamp = Timestamp.valueOf(request.getParameter("timestamp"));

        Interaction interaction = new Interaction();
        String action = request.getParameter("action");

        if (action.equals("add")) {
            interactionDAO.addInteraction(interaction);
        } else if (action.equals("update")) {
            int interactionId = Integer.parseInt(request.getParameter("interactionId"));
            interaction.setInteractionId(interactionId);
            interactionDAO.updateInteraction(interaction);
        }

        response.sendRedirect("interactions");
    }
}
