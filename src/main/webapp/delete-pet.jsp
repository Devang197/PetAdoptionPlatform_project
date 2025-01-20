<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Delete Pet</title>
</head>
<body>
    <h2>Delete Pet</h2>

    <!-- Confirm deletion message -->
    <p>Are you sure you want to delete the pet with ID: <strong>${pet.petId}</strong>?</p>

    <form action="pets" method="post">
        <!-- Hidden action parameter for deleting pet -->
        <input type="hidden" name="action" value="delete">
        
        <!-- Hidden Pet ID to identify the pet to delete -->
        <input type="hidden" name="id" value="${pet.petId}">
        
        <!-- Submit Button to delete pet -->
        <button type="submit">Delete Pet</button>
    </form>

    <!-- Link to go back to pet list -->
    <a href="pets?action=list">Back to Pet List</a>
</body>
</html>
