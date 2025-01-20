package com.jdbc.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class InteractionDAOImpl implements IInteractionDAO {
    private final Connection conn;

    public InteractionDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addInteraction(Interaction interaction) {
        String sql = "INSERT INTO Interactions (shelter_id, adopter_id, message, timestamp) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, interaction.getShelterId());
            stmt.setInt(2, interaction.getAdopterId());
            stmt.setString(3, interaction.getMessage());
            stmt.setTimestamp(4, interaction.getTimestamp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while adding interaction: " + e.getMessage());
        }
    }

    @Override
    public void updateInteraction(Interaction interaction) {
        String sql = "UPDATE Interactions SET shelter_id = ?, adopter_id = ?, message = ?, timestamp = ? WHERE interaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, interaction.getShelterId());
            stmt.setInt(2, interaction.getAdopterId());
            stmt.setString(3, interaction.getMessage());
            stmt.setTimestamp(4, interaction.getTimestamp());
            stmt.setInt(5, interaction.getInteractionId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while updating interaction: " + e.getMessage());
        }
    }

    @Override
    public void deleteInteraction(int interactionId) {
        String sql = "DELETE FROM Interactions WHERE interaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, interactionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error while deleting interaction: " + e.getMessage());
        }
    }

    @Override
    public Interaction getInteractionById(int interactionId) {
        String sql = "SELECT * FROM Interactions WHERE interaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, interactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Interaction interaction = new Interaction();
                interaction.setInteractionId(rs.getInt("interaction_id"));
                interaction.setShelterId(rs.getInt("shelter_id"));
                interaction.setAdopterId(rs.getInt("adopter_id"));
                interaction.setMessage(rs.getString("message"));
                interaction.setTimestamp(rs.getTimestamp("timestamp"));
                return interaction;
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving interaction: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Interaction> getAllInteractions() {
        List<Interaction> interactions = new ArrayList<>();
        String sql = "SELECT * FROM Interactions"; // Example SQL to select all interactions
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Interaction interaction = new Interaction();
                interaction.setInteractionId(rs.getInt("interaction_id"));
                interaction.setShelterId(rs.getInt("shelter_id"));
                interaction.setAdopterId(rs.getInt("adopter_id"));
                interaction.setMessage(rs.getString("message"));
                interaction.setTimestamp(rs.getTimestamp("timestamp"));
                interactions.add(interaction); // Add interaction to the list
            }
        } catch (SQLException e) {
            System.out.println("Error while retrieving interactions: " + e.getMessage());
        }
        return interactions;
    }
}
