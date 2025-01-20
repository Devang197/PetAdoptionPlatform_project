package com.jdbc.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceTest {
    private IPetDAO mockPetDAO;
    private PetService petService;

    @BeforeEach
    void setUp() {
        mockPetDAO = Mockito.mock(IPetDAO.class);
        petService = new PetService(mockPetDAO);
    }

    @Test
    void testAddPetSuccess() {
        Pet pet = new Pet();
        pet.setName("Fluffy");

        petService.addPet(pet);
        verify(mockPetDAO, times(1)).addPet(pet);
    }

    @Test
    void testAddPetInvalidName() {
        Pet pet = new Pet();

        Exception exception = assertThrows(IllegalArgumentException.class, () -> petService.addPet(pet));
        assertEquals("Pet name cannot be null or empty", exception.getMessage());
    }

    @Test
    void testGetPetByIdSuccess() {
        Pet pet = new Pet();
        pet.setPetId(1);
        pet.setName("Fluffy");

        when(mockPetDAO.getPetById(1)).thenReturn(pet);

        Pet retrievedPet = petService.getPetById(1);
        assertNotNull(retrievedPet);
        assertEquals("Fluffy", retrievedPet.getName());
    }

    @Test
    void testGetPetByIdNotFound() {
        when(mockPetDAO.getPetById(1)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> petService.getPetById(1));
        assertEquals("Pet not found with ID: 1", exception.getMessage());
    }

    @Test
    void testGetAllPets() {
        Pet pet1 = new Pet();
        pet1.setName("Fluffy");

        Pet pet2 = new Pet();
        pet2.setName("Buddy");

        List<Pet> petList = Arrays.asList(pet1, pet2);

        when(mockPetDAO.getAllPets()).thenReturn(petList);

        List<Pet> result = petService.getAllPets();
        assertEquals(2, result.size());
    }
}
