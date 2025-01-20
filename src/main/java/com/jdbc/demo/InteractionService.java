package com.jdbc.demo;

import java.util.List;

public class InteractionService {
    private final IInteractionDAO interactionDAO;

    public InteractionService(IInteractionDAO interactionDAO) {
        this.interactionDAO = interactionDAO;
    }

    public void addInteraction(Interaction interaction) {
        if (interaction.getMessage() == null || interaction.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
        interactionDAO.addInteraction(interaction);
    }

    public void updateInteraction(Interaction interaction) {
        interactionDAO.updateInteraction(interaction);
    }

    public void deleteInteraction(int interactionId) {
        interactionDAO.deleteInteraction(interactionId);
    }

    public Interaction getInteractionById(int interactionId) {
        Interaction interaction = interactionDAO.getInteractionById(interactionId);
        if (interaction == null) {
            throw new IllegalArgumentException("Interaction not found with ID: " + interactionId);
        }
        return interaction;
    }

    
}
