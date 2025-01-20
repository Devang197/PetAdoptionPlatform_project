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

@WebServlet("/pets")
public class PetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PetDAOImpl petDAO;

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
            petDAO = new PetDAOImpl(conn);
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBC Driver not found", e);
        } catch (SQLException e) {
            throw new ServletException("Database connection initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the action parameter, default to "list" if null or empty
        String action = request.getParameter("action");
        if (action == null || action.isEmpty()) {
            action = "list";  // Default action
        }

        try {
            switch (action) {
                case "list":
                    listPets(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deletePet(request, response);
                    break;
                default:
                    listPets(request, response);
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

        try {
            if (action == null || action.isEmpty()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing or invalid.");
            } else if (action.equals("add")) {
                addPet(request, response);
            } else if (action.equals("update")) {
                updatePet(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
            }
        } catch (Exception e) {
            throw new ServletException("Error processing POST request", e);
        }
    }

    private void listPets(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Pet> pets = petDAO.getAllPets();
        request.setAttribute("petList", pets);
        request.getRequestDispatcher("pet-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int petId = Integer.parseInt(request.getParameter("id"));
        Pet existingPet = petDAO.getPetById(petId);
        request.setAttribute("pet", existingPet);
        request.getRequestDispatcher("pet-form.jsp").forward(request, response);
    }

    private void addPet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String name = request.getParameter("name");
        String breed = request.getParameter("breed");
        String shelterIdStr = request.getParameter("shelterId");
        String ageStr = request.getParameter("age");
        String status = request.getParameter("status");

        if (name == null || breed == null || shelterIdStr == null || ageStr == null || status == null ||
                name.isEmpty() || breed.isEmpty() || shelterIdStr.isEmpty() || ageStr.isEmpty() || status.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("pet-form.jsp").forward(request, response);
            return;
        }

        Pet pet = new Pet();
        pet.setName(name);
        pet.setBreed(breed);
        pet.setAge(Integer.parseInt(ageStr));
        pet.setShelterId(Integer.parseInt(shelterIdStr));
        pet.setStatus(status);

        try {
            petDAO.addPet(pet); // No boolean return value expected
            request.setAttribute("message", "Pet added successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            request.setAttribute("error", "Failed to add pet. Please try again.");
        }

        // Forward back to the list of pets
        request.getRequestDispatcher("pets?action=list").forward(request, response);
    }


    private void updatePet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int petId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String breed = request.getParameter("breed");
        String ageStr = request.getParameter("age");
        String shelterIdStr = request.getParameter("shelterId");
        String status = request.getParameter("status");

        if (name == null || breed == null || ageStr == null || shelterIdStr == null || status == null ||
                name.isEmpty() || breed.isEmpty() || ageStr.isEmpty() || shelterIdStr.isEmpty() || status.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("pet-form.jsp").forward(request, response);
            return;
        }

        Pet pet = new Pet();
        pet.setPetId(petId);
        pet.setName(name);
        pet.setBreed(breed);
        pet.setAge(Integer.parseInt(ageStr));
        pet.setShelterId(Integer.parseInt(shelterIdStr));
        pet.setStatus(status);

        try {
            petDAO.updatePet(pet); // No boolean return value expected
            request.setAttribute("message", "Pet updated successfully!");
        } catch (Exception e) {
            e.printStackTrace(); // Log the error
            request.setAttribute("error", "Failed to update pet. Please try again.");
        }

        request.getRequestDispatcher("pet-form.jsp").forward(request, response);
    }

    private void deletePet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int petId = Integer.parseInt(request.getParameter("id"));

        try {
            // Execute the delete operation
            petDAO.deletePet(petId);

            // If no exception occurs, assume success and redirect
            response.sendRedirect("pets?action=list");
        } catch (Exception e) {
            // Log the exception for debugging purposes
            e.printStackTrace();

            // Send an error message to the client
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete pet. Please try again.");
        }
    }

}
