package com.jdbc.demo;
import java.sql.Timestamp;
import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


class User {
    private int userId;
    private String name;
    private String email;
    private String password;
    private String role; // Admin, Shelter, Adopter

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
class Pet {
    private int petId;
    private int shelterId;
    private String name;
    private String breed;
    private int age;
    private String status; // Available, Adopted

    // Getters and Setters
    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class Application {
    private int applicationId;
    private int adopterId;
    private int petId;
    private String status; // Pending, Approved, Rejected
    private Timestamp applicationDate;

    // Getters and Setters
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(int adopterId) {
        this.adopterId = adopterId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }
}
class Interaction {
    private int interactionId;
    private int shelterId;
    private int adopterId;
    private String message;
    private Timestamp timestamp;

    // Getters and Setters
    public int getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(int interactionId) {
        this.interactionId = interactionId;
    }

    public int getShelterId() {
        return shelterId;
    }

    public void setShelterId(int shelterId) {
        this.shelterId = shelterId;
    }

    public int getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(int adopterId) {
        this.adopterId = adopterId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

public class OnlinePetAdoption {
    public static void main(String[] args) {
        System.out.println("Welcome to the Online Pet Adoption Platform!");
        try (Connection conn = JDBCConnector.getConnection()) {
            UserDAOImpl userDAO = new UserDAOImpl(conn);
            User user = new User();

            // Using setter methods to set the properties
            user.setUserId(1);
            user.setName("Alice");
            user.setEmail("alice@example.com");
            user.setPassword("securePassword123");
            user.setRole("Adopter");
            userDAO.addUser(user);
            System.out.println("User added successfully!");



            PetDAOImpl petDAO = new PetDAOImpl(conn);
            Pet newPet = new Pet();
            
            newPet.setShelterId(1); // Assume this shelter ID exists in the Users table
            newPet.setName("Fluffy");
            newPet.setBreed("Golden Retriever");
            newPet.setAge(3);
            newPet.setStatus("Available");

            petDAO.addPet(newPet);
            System.out.println("Pet added successfully!");

            ApplicationDAOImpl applicationDAO = new ApplicationDAOImpl(conn);
            Application newApplication = new Application();

            newApplication.setApplicationId(1);
            newApplication.setAdopterId(1); // Assume this adopter ID exists
            newApplication.setPetId(1); // Assume this pet ID exists
            newApplication.setStatus("Pending");

            applicationDAO.addApplication(newApplication);
            System.out.println("Application added successfully!");

            InteractionDAOImpl interactionDAO = new InteractionDAOImpl(conn);
            Interaction newInteraction = new Interaction();
            newInteraction.setShelterId(1); // Assume this shelter ID exists
            newInteraction.setAdopterId(1); // Assume this adopter ID exists
            newInteraction.setMessage("Hello, we are reviewing your application.");

            interactionDAO.addInteraction(newInteraction);
            System.out.println("Interaction added successfully!");

        } catch (SQLException e){
            System.out.println("Error while interacting with database " + e.getMessage());
        }
    }
}
