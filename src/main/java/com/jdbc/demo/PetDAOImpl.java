package com.jdbc.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class PetDAOImpl implements IPetDAO {
    private final Connection conn;

    public PetDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addPet(Pet pet) {
        String sql = "INSERT INTO Pets (shelter_id, name, breed, age, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pet.getShelterId());
            stmt.setString(2, pet.getName());
            stmt.setString(3, pet.getBreed());
            stmt.setInt(4, pet.getAge());
            stmt.setString(5, pet.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // SQLState for foreign key constraint violation
                System.out.println("Error: Invalid Shelter ID. Please ensure the shelter exists. " + e.getMessage());
            } else {
                System.out.println("Error while adding pet: " + e.getMessage());
            }
        }
    }

    @Override
    public void updatePet(Pet pet) {
        String sql = "UPDATE Pets SET shelter_id = ?, name = ?, breed = ?, age = ?, status = ? WHERE pet_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pet.getShelterId());
            stmt.setString(2, pet.getName());
            stmt.setString(3, pet.getBreed());
            stmt.setInt(4, pet.getAge());
            stmt.setString(5, pet.getStatus());
            stmt.setInt(6, pet.getPetId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            if (e.getSQLState().equals("23000")) { // SQLState for foreign key constraint violation
                System.out.println("Error: Invalid Shelter ID. Please ensure the shelter exists. " + e.getMessage());
            } else {
                System.out.println("Error while updating pet: " + e.getMessage());
            }
        }
    }

    @Override
    public void deletePet(int petId) {
        String sql = "DELETE FROM Pets WHERE pet_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting pet: " + e.getMessage());
        }
    }

    @Override
    public Pet getPetById(int petId) {
        String sql = "SELECT * FROM Pets WHERE pet_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pet pet = new Pet();
                pet.setPetId(rs.getInt("pet_id"));
                pet.setShelterId(rs.getInt("shelter_id"));
                pet.setName(rs.getString("name"));
                pet.setBreed(rs.getString("breed"));
                pet.setAge(rs.getInt("age"));
                pet.setStatus(rs.getString("status"));
                return pet;
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving pet by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM Pets";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setPetId(rs.getInt("pet_id"));
                pet.setShelterId(rs.getInt("shelter_id"));
                pet.setName(rs.getString("name"));
                pet.setBreed(rs.getString("breed"));
                pet.setAge(rs.getInt("age"));
                pet.setStatus(rs.getString("status"));
                pets.add(pet);
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving all pets: " + e.getMessage());
        }
        return pets;
    }
}
