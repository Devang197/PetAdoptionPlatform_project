package com.jdbc.demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.sql.*;

class ApplicationDAOImplTest {

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private ApplicationDAOImpl applicationDAO;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        applicationDAO = new ApplicationDAOImpl(connection);
    }

    

    @Test
    void testGetApplicationById() throws SQLException {
        Application expectedApplication = new Application();
        expectedApplication.setApplicationId(1);
        expectedApplication.setAdopterId(1);
        expectedApplication.setPetId(1);
        expectedApplication.setStatus("Pending");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("application_id")).thenReturn(1);
        when(resultSet.getInt("adopter_id")).thenReturn(1);
        when(resultSet.getInt("pet_id")).thenReturn(1);
        when(resultSet.getString("status")).thenReturn("Pending");

        Application actualApplication = applicationDAO.getApplicationById(1);

        assertEquals(expectedApplication.getApplicationId(), actualApplication.getApplicationId());
        assertEquals(expectedApplication.getAdopterId(), actualApplication.getAdopterId());
        assertEquals(expectedApplication.getPetId(), actualApplication.getPetId());
        assertEquals(expectedApplication.getStatus(), actualApplication.getStatus());
    }

    @Test
    void testDeleteApplication() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        applicationDAO.deleteApplication(1);

        verify(preparedStatement, times(1)).executeUpdate();
    }

    @Test
    void testUpdateApplication() throws SQLException {
        Application application = new Application();
        application.setApplicationId(1);
        application.setAdopterId(1);
        application.setPetId(1);
        application.setStatus("Approved");

        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);

        applicationDAO.updateApplication(application);

        verify(preparedStatement, times(1)).executeUpdate();
    }
}
