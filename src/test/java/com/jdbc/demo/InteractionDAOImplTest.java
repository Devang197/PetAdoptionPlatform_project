package com.jdbc.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InteractionDAOImplTest {

    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private InteractionDAOImpl interactionDAO;

    @BeforeEach
    void setUp() throws SQLException {
        mockConn = Mockito.mock(Connection.class);
        mockStmt = Mockito.mock(PreparedStatement.class);
        mockRs = Mockito.mock(ResultSet.class);
        interactionDAO = new InteractionDAOImpl(mockConn);
    }

    @Test
    void testAddInteraction() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        Interaction interaction = new Interaction();
        interaction.setShelterId(1);
        interaction.setAdopterId(2);
        interaction.setMessage("Hello, we are reviewing your application.");
        interaction.setTimestamp(Timestamp.valueOf("2025-01-17 10:10:10"));

        interactionDAO.addInteraction(interaction);

        verify(mockStmt, times(1)).setInt(1, interaction.getShelterId());
        verify(mockStmt, times(1)).setInt(2, interaction.getAdopterId());
        verify(mockStmt, times(1)).setString(3, interaction.getMessage());
        verify(mockStmt, times(1)).setTimestamp(4, interaction.getTimestamp());
        verify(mockStmt, times(1)).executeUpdate();
    }

    @Test
    void testGetInteractionById() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("interaction_id")).thenReturn(1);
        when(mockRs.getInt("shelter_id")).thenReturn(1);
        when(mockRs.getInt("adopter_id")).thenReturn(2);
        when(mockRs.getString("message")).thenReturn("Hello");
        when(mockRs.getTimestamp("timestamp")).thenReturn(Timestamp.valueOf("2025-01-17 10:10:10"));

        Interaction result = interactionDAO.getInteractionById(1);

        assertNotNull(result);
        assertEquals(1, result.getInteractionId());
        assertEquals("Hello", result.getMessage());
        assertEquals(2, result.getAdopterId());
        assertEquals(Timestamp.valueOf("2025-01-17 10:10:10"), result.getTimestamp());
    }

    @Test
    void testDeleteInteraction() throws SQLException {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        int interactionId = 1;
        interactionDAO.deleteInteraction(interactionId);

        verify(mockStmt, times(1)).setInt(1, interactionId);
        verify(mockStmt, times(1)).executeUpdate();
    }

    @Test
    void testGetAllInteractions() throws SQLException {
        List<Interaction> interactions = Arrays.asList(
            new Interaction(),
            new Interaction()
        );

        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true, true, false); // Two interactions returned
        when(mockRs.getInt("interaction_id")).thenReturn(1, 2);
        when(mockRs.getInt("shelter_id")).thenReturn(1, 1);
        when(mockRs.getInt("adopter_id")).thenReturn(2, 3);
        when(mockRs.getString("message")).thenReturn("Message 1", "Message 2");
        when(mockRs.getTimestamp("timestamp")).thenReturn(Timestamp.valueOf("2025-01-17 10:10:10"), Timestamp.valueOf("2025-01-17 10:20:20"));

        List<Interaction> result = interactionDAO.getAllInteractions();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Message 1", result.get(0).getMessage());
        assertEquals("Message 2", result.get(1).getMessage());
    }
}
