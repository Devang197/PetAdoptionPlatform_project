<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Pet List</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            font-family: "Nunito Sans", sans-serif;
            box-sizing: border-box;
        }

        table {
            width: 80%;
            margin: 20px auto;
            border-collapse: collapse;
        }

        table, th, td {
            border: 1px solid black;
        }

        th, td {
            padding: 10px;
            text-align: center;
        }

        .btn {
            padding: 5px 10px;
            text-decoration: none;
            color: white;
            background-color: #007BFF;
            border: none;
            border-radius: 5px;
        }

        .btn-delete {
            background-color: #dc3545;
        }

        .container {
            width: 100%;
            height: 100vh;
            background-image: url(background.jpg);
            background-position: center;
            background-size: cover;
            position: relative;
        }

        .content {
            padding: 20px;
            text-align: center;
            background: rgba(255, 255, 255, 0.8);
            border-radius: 8px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="content">
            <h1>Pet List</h1>

            <!-- Display success or error message -->
            <c:if test="${not empty message}">
                <div class="success">${message}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <a href="pet-form.jsp" class="btn" style="margin: 20px;">Add New Pet</a>
            <table>
                <thead>
                    <tr>
                        <th>Shelter ID</th>
                        <th>Name</th>
                        <th>Breed</th>
                        <th>Age</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterate over the petList and display the pet details -->
                    <c:forEach var="pet" items="${petList}">
                        <tr>
                            <td>${pet.shelterId}</td>
                            <td>${pet.name}</td>
                            <td>${pet.breed}</td>
                            <td>${pet.age}</td>
                            <td>${pet.status}</td>
                            <td>
                                <a href="pets?action=list" class="btn">List Pets</a>
                                <a href="pets?action=edit&id=${pet.petId}" class="btn">Edit</a>
                                <a href="pets?action=delete&id=${pet.petId}" class="btn btn-delete">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
