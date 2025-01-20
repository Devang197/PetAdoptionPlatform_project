<%@ page contentType="text/html;charset=UTF-8" language="java" %>%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Interaction List</title>
    <style>
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
            max-width: 1000px;
            margin: auto;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #ff9a24;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        table, th, td {
            border: 1px solid #ddd;
        }

        th, td {
            padding: 10px;
            text-align: center;
        }

        th {
            background-color: #ff9a24;
            color: white;
        }

        .action-buttons button {
            padding: 5px 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-edit {
            background-color: #007bff;
            color: white;
        }

        .btn-delete {
            background-color: #dc3545;
            color: white;
        }

        .btn-add {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
        }

        .btn-add:hover {
            background-color: #218838;
        }

        a {
            text-decoration: none;
            color: white;
        }

        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 20px;
        }

        .success-message {
            color: green;
            text-align: center;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Interaction List</h1>

        <!-- Display error or success message -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="success-message">
                ${successMessage}
            </div>
        </c:if>

        <!-- Interaction Table -->
        <table>
            <thead>
                <tr>
                    <th>Interaction ID</th>
                    <th>Shelter ID</th>
                    <th>Adopter ID</th>
                    <th>Message</th>
                    <th>Timestamp</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Loop through interactions and display each -->
                <c:forEach var="interaction" items="${interactionList}">
                    <tr>
                        <td>${interaction.interactionId}</td>
                        <td>${interaction.shelterId}</td>
                        <td>${interaction.adopterId}</td>
                        <td>${interaction.message}</td>
                        <td>${interaction.timestamp}</td> <!-- Removed escapeXml -->
                        <td class="action-buttons">
                            <a href="interactions?action=edit&id=${interaction.interactionId}">
                                <button class="btn-edit">Edit</button>
                            </a>
                            <a href="interactions?action=delete&id=${interaction.interactionId}" onclick="return confirm('Are you sure you want to delete this interaction?');">
                                <button class="btn-delete">Delete</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Add New Interaction Button -->
        <a href="interaction-form.jsp?action=add" class="btn-add">Add New Interaction</a>
    </div>
</body>
</html>
