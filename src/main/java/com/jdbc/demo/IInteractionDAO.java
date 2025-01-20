package com.jdbc.demo;

import java.sql.SQLException;
import java.util.List;

interface IInteractionDAO {
    void addInteraction(Interaction interaction);
    void updateInteraction(Interaction interaction);
    void deleteInteraction(int interactionId);
    Interaction getInteractionById(int interactionId);
    List<Interaction> getAllInteractions() throws SQLException;
}
