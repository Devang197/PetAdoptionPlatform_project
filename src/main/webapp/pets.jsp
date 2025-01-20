<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pet List</title>
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
    </style>
</head>
<body>
    <div class="container">
        <h1>Pet List</h1>

        <!-- Display error message if exists -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">
                ${errorMessage}
            </div>
        </c:if>

        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Breed</th>
                    <th>Age</th>
                    <th>Shelter ID</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <!-- Loop through each pet in petList -->
                <c:forEach var="pet" items="${petList}">
                    <tr>
                        <td>${pet.name}</td>
                        <td>${pet.breed}</td>
                        <td>${pet.age}</td>
                        <td>${pet.shelterId}</td>
                        <td>${pet.status}</td>
                        <td class="action-buttons">
                            <a href="pets?action=edit&id=${pet.id}"><button class="btn-edit">Edit</button></a>
                            <a href="pets?action=delete&id=${pet.id}" onclick="return confirm('Are you sure you want to delete this pet?');"><button class="btn-delete">Delete</button></a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Add new pet button -->
        <a href="pets?action=add" class="btn-add">Add New Pet</a>
    </div>
</body>
</html>
