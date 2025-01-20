package com.jdbc.demo;

import java.util.List;

public class PetService {
    private final IPetDAO petDAO;

    public PetService(IPetDAO petDAO) {
        this.petDAO = petDAO;
    }

    public void addPet(Pet pet) {
        if (pet.getName() == null || pet.getName().isEmpty()) {
            throw new IllegalArgumentException("Pet name cannot be null or empty");
        }
        petDAO.addPet(pet);
    }

    public List<Pet> getAllPets() {
        return petDAO.getAllPets();
    }

    public Pet getPetById(int petId) {
        Pet pet = petDAO.getPetById(petId);
        if (pet == null) {
            throw new IllegalArgumentException("Pet not found with ID: " + petId);
        }
        return pet;
    }
}
