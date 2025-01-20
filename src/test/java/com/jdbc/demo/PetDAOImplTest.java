package com.jdbc.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetDAOImplTest {
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private PetDAOImpl petDAO;

    @BeforeEach
    void setUp() throws Exception {
        mockConn = Mockito.mock(Connection.class);
        mockStmt = Mockito.mock(PreparedStatement.class);
        mockRs = Mockito.mock(ResultSet.class);
        petDAO = new PetDAOImpl(mockConn);
    }

    @Test
    void testAddPet() throws Exception {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        Pet pet = new Pet();
        pet.setShelterId(1);
        pet.setName("Fluffy");
        pet.setBreed("Golden Retriever");
        pet.setAge(3);
        pet.setStatus("Available");

        petDAO.addPet(pet);

        verify(mockStmt, times(1)).setInt(1, pet.getShelterId());
        verify(mockStmt, times(1)).setString(2, pet.getName());
        verify(mockStmt, times(1)).setString(3, pet.getBreed());
        verify(mockStmt, times(1)).setInt(4, pet.getAge());
        verify(mockStmt, times(1)).setString(5, pet.getStatus());
        verify(mockStmt, times(1)).executeUpdate();
    }

    @Test
    void testGetPetById() throws Exception {
        when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("pet_id")).thenReturn(1);
        when(mockRs.getString("name")).thenReturn("Fluffy");
        when(mockRs.getString("breed")).thenReturn("Golden Retriever");
        when(mockRs.getInt("age")).thenReturn(3);
        when(mockRs.getString("status")).thenReturn("Available");

        Pet pet = petDAO.getPetById(1);

        assertNotNull(pet);
        assertEquals(1, pet.getPetId());
        assertEquals("Fluffy", pet.getName());
        assertEquals("Golden Retriever", pet.getBreed());
        assertEquals(3, pet.getAge());
        assertEquals("Available", pet.getStatus());
    }
}
