package com.jdbc.demo;

import java.util.List;

public class ApplicationService {
    private final IApplicationDAO applicationDAO;

    public ApplicationService(IApplicationDAO applicationDAO) {
        this.applicationDAO = applicationDAO;
    }

    public void applyForPet(Application application) {
        if (application.getAdopterId() == 0 || application.getPetId() == 0) {
            throw new IllegalArgumentException("Invalid application data");
        }
        applicationDAO.addApplication(application);
    }

    public boolean approveApplication(int applicationId) {
        Application application = applicationDAO.getApplicationById(applicationId);
        if (application == null || "Approved".equals(application.getStatus()) || "Rejected".equals(application.getStatus())) {
            throw new IllegalArgumentException("Application not found or already processed");
        }
        application.setStatus("Approved");
        applicationDAO.updateApplication(application);
        return true;
    }

    public boolean rejectApplication(int applicationId) {
        Application application = applicationDAO.getApplicationById(applicationId);
        if (application == null || "Approved".equals(application.getStatus()) || "Rejected".equals(application.getStatus())) {
            throw new IllegalArgumentException("Application not found or already processed");
        }
        application.setStatus("Rejected");
        applicationDAO.updateApplication(application);
        return true;
    }

    public List<Application> getAllApplications() {
        return applicationDAO.getAllApplications();
    }

    public Application getApplicationById(int applicationId) {
        Application application = applicationDAO.getApplicationById(applicationId);
        if (application == null) {
            throw new IllegalArgumentException("Application not found with ID: " + applicationId);
        }
        return application;
    }
}
