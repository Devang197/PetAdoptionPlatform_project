<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Update Pet</title>
</head>
<body>
    <h2>Update Pet Details</h2>
    <form action="pets" method="post">
        <!-- Hidden action parameter for updating pet -->
        <input type="hidden" name="action" value="update">
        
        <!-- Hidden Pet ID to identify the pet to update -->
        <input type="hidden" name="id" value="${pet.petId}">

        <!-- Pet Name -->
        <label for="name">Pet Name:</label>
        <input type="text" id="name" name="name" value="${pet.name}" required><br><br>

        <!-- Breed -->
        <label for="breed">Breed:</label>
        <input type="text" id="breed" name="breed" value="${pet.breed}" required><br><br>

        <!-- Age -->
        <label for="age">Age:</label>
        <input type="number" id="age" name="age" value="${pet.age}" required><br><br>

        <!-- Status with dynamic selection -->
        <label for="status">Status:</label>
        <select id="status" name="status">
            <option value="Available" 
                <c:if test="${pet.status == 'Available'}">selected</c:if>>Available</option>
            <option value="Adopted" 
                <c:if test="${pet.status == 'Adopted'}">selected</c:if>>Adopted</option>
        </select><br><br>

        <!-- Submit Button -->
        <button type="submit">Update Pet</button>
    </form>
</body>
</html>
