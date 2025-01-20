-- Create Database for Online Pet Adoption
DROP DATABASE IF EXISTS online_pet_adoption;
CREATE DATABASE online_pet_adoption;
USE online_pet_adoption;

-- Users Table
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role ENUM('Admin', 'Shelter', 'Adopter') NOT NULL
);

-- Pets Table
CREATE TABLE Pets (
    pet_id INT PRIMARY KEY AUTO_INCREMENT,
    shelter_id INT,
    name VARCHAR(100) NOT NULL,
    breed VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    status ENUM('Available', 'Adopted') DEFAULT 'Available',
    FOREIGN KEY (shelter_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Applications Table
CREATE TABLE Applications (
    application_id INT PRIMARY KEY AUTO_INCREMENT,
    adopter_id INT,
    pet_id INT,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (adopter_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (pet_id) REFERENCES Pets(pet_id) ON DELETE CASCADE
);

-- Interactions Table
CREATE TABLE Interactions (
    interaction_id INT PRIMARY KEY AUTO_INCREMENT,
    shelter_id INT,
    adopter_id INT,
    message TEXT NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (shelter_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (adopter_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

SELECT * FROM Users;
SELECT * FROM Pets;
SELECT * FROM Applications;
SELECT * FROM Interactions;