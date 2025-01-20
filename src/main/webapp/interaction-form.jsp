<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Interaction Form</title>
    <style>
        /* Add your CSS styling here */
        * {
            margin: 0;
            padding: 0;
            font-family: "Nunito Sans", sans-serif;
            box-sizing: border-box;
        }

        body {
            background-image: url('background.jpg');
            background-position: center;
            background-size: cover;
            padding: 20px;
        }

        .container {
            background: rgba(255, 255, 255, 0.9);
            padding: 20px;
            border-radius: 10px;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #ff9a24;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        label {
            font-weight: bold;
            font-size: 16px;
        }

        input, textarea, button {
            padding: 10px;
            font-size: 16px;
            border-radius: 5px;
            border: 1px solid #ddd;
        }

        input[type="number"], input[type="datetime-local"], textarea {
            width: 100%;
        }

        button {
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        button:hover {
            background-color: #218838;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }

        .btn-back {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-align: center;
            text-decoration: none;
            border-radius: 5px;
        }

        .btn-back:hover {
            background-color: #0056b3;
        }

    </style>
</head>
<body>
    <div class="container">
        <h1>${action == 'add' ? 'Add New Interaction' : 'Edit Interaction'}</h1>

        <!-- Display error message if any -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <form action="interactions" method="post">
            <input type="hidden" name="action" value="${action}">

            <!-- If editing, include the hidden interactionId field -->
            <c:if test="${action == 'edit'}">
                <input type="hidden" name="interactionId" value="${interaction.interactionId}">
            </c:if>

            <!-- Shelter ID field -->
            <label for="shelterId">Shelter ID:</label>
            <input type="number" name="shelterId" value="${action == 'edit' ? interaction.shelterId : ''}" required>

            <!-- Adopter ID field -->
            <label for="adopterId">Adopter ID:</label>
            <input type="number" name="adopterId" value="${action == 'edit' ? interaction.adopterId : ''}" required>

            <!-- Message field -->
            <label for="message">Message:</label>
            <textarea name="message" required>${action == 'edit' ? interaction.message : ''}</textarea>

            <!-- Timestamp field -->
            <label for="timestamp">Timestamp:</label>
            <input type="datetime-local" name="timestamp" value="${action == 'edit' ? interaction.timestamp : ''}" required>

            <!-- Submit button -->
            <button type="submit">${action == 'add' ? 'Add Interaction' : 'Update Interaction'}</button>
        </form>

        <!-- Back to interaction list button -->
        <a href="interactions" class="btn-back">Back to Interaction List</a>
    </div>
</body>
</html>
