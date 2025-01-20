package com.jdbc.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationServiceTest {
    private IApplicationDAO mockApplicationDAO;
    private ApplicationService applicationService;

    @BeforeEach
    void setUp() {
        mockApplicationDAO = Mockito.mock(IApplicationDAO.class);
        applicationService = new ApplicationService(mockApplicationDAO);
    }

    @Test
    void testApplyForPetSuccess() {
        Application application = new Application();
        application.setAdopterId(1);
        application.setPetId(1);
        application.setStatus("Pending");

        applicationService.applyForPet(application);
        verify(mockApplicationDAO, times(1)).addApplication(application);
    }

    @Test
    void testApplyForPetInvalidApplication() {
        Application application = new Application();
        
        // Missing adopter ID or pet ID
        Exception exception = assertThrows(IllegalArgumentException.class, () -> applicationService.applyForPet(application));
        assertEquals("Invalid application data", exception.getMessage());
    }

    @Test
    void testApproveApplicationSuccess() {
        Application application = new Application();
        application.setApplicationId(1);
        application.setAdopterId(1);
        application.setPetId(1);
        application.setStatus("Pending");

        when(mockApplicationDAO.getApplicationById(1)).thenReturn(application);

        boolean result = applicationService.approveApplication(1);
        assertTrue(result);
        assertEquals("Approved", application.getStatus());
        verify(mockApplicationDAO, times(1)).updateApplication(application);
    }

    @Test
    void testApproveApplicationNotFound() {
        when(mockApplicationDAO.getApplicationById(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> applicationService.approveApplication(1));
        assertEquals("Application not found or already processed", exception.getMessage());
    }

    @Test
    void testRejectApplicationSuccess() {
        Application application = new Application();
        application.setApplicationId(1);
        application.setAdopterId(1);
        application.setPetId(1);
        application.setStatus("Pending");

        when(mockApplicationDAO.getApplicationById(1)).thenReturn(application);

        boolean result = applicationService.rejectApplication(1);
        assertTrue(result);
        assertEquals("Rejected", application.getStatus());
        verify(mockApplicationDAO, times(1)).updateApplication(application);
    }

    @Test
    void testRejectApplicationNotFound() {
        when(mockApplicationDAO.getApplicationById(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> applicationService.rejectApplication(1));
        assertEquals("Application not found or already processed", exception.getMessage());
    }

    @Test
    void testGetAllApplications() {
        Application application1 = new Application();
        application1.setApplicationId(1);
        application1.setStatus("Pending");

        Application application2 = new Application();
        application2.setApplicationId(2);
        application2.setStatus("Approved");

        List<Application> applicationList = Arrays.asList(application1, application2);

        when(mockApplicationDAO.getAllApplications()).thenReturn(applicationList);

        List<Application> result = applicationService.getAllApplications();
        assertEquals(2, result.size());
        assertEquals("Pending", result.get(0).getStatus());
    }
}
