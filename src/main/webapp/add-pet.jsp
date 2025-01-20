<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Pet</title>
</head>
<body>
    <h2>Add a New Pet</h2>
    <form action="pets" method="post">
        <input type="hidden" name="action" value="add"> <!-- Hidden action parameter for adding pet -->

        <label for="name">Pet Name:</label>
        <input type="text" id="name" name="name" required><br><br>

        <label for="breed">Breed:</label>
        <input type="text" id="breed" name="breed" required><br><br>

        <label for="age">Age:</label>
        <input type="number" id="age" name="age" required><br><br>

        <label for="status">Status:</label>
        <select id="status" name="status">
            <option value="Available">Available</option>
            <option value="Adopted">Adopted</option>
        </select><br><br>

        <button type="submit">Add Pet</button>
    </form>
</body>
</html>
