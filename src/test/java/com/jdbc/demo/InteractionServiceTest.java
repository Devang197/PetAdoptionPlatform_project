package com.jdbc.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InteractionServiceTest {

    private IInteractionDAO mockInteractionDAO;
    private InteractionService interactionService;

    @BeforeEach
    void setUp() {
        mockInteractionDAO = Mockito.mock(IInteractionDAO.class);
        interactionService = new InteractionService(mockInteractionDAO);
    }

    @Test
    void testAddInteractionSuccess() {
        Interaction interaction = new Interaction();
        interaction.setShelterId(1);
        interaction.setAdopterId(2);
        interaction.setMessage("Reviewing your application");
        interaction.setTimestamp(Timestamp.valueOf("2025-01-17 10:10:10"));

        interactionService.addInteraction(interaction);
        verify(mockInteractionDAO, times(1)).addInteraction(interaction);
    }

    @Test
    void testAddInteractionInvalidMessage() {
        Interaction interaction = new Interaction();
        interaction.setShelterId(1);
        interaction.setAdopterId(2);
        interaction.setMessage(""); // Invalid message

        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactionService.addInteraction(interaction));
        assertEquals("Message cannot be null or empty", exception.getMessage());
    }

    @Test
    void testGetInteractionByIdSuccess() {
        Interaction interaction = new Interaction();
        interaction.setInteractionId(1);
        interaction.setMessage("Message");

        when(mockInteractionDAO.getInteractionById(1)).thenReturn(interaction);

        Interaction retrievedInteraction = interactionService.getInteractionById(1);
        assertNotNull(retrievedInteraction);
        assertEquals("Message", retrievedInteraction.getMessage());
    }

    @Test
    void testGetInteractionByIdNotFound() {
        when(mockInteractionDAO.getInteractionById(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> interactionService.getInteractionById(1));
        assertEquals("Interaction not found with ID: 1", exception.getMessage());
    }

    
}
