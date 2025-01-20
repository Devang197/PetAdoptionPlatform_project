<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pet Form</title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            font-family: "Nunito Sans", sans-serif;
            box-sizing: border-box;
        }

        body, html {
            height: 100%;
        }

        .container {
            width: 100%;
            height: 100%;
            background-image: url('background.jpg');
            background-position: center;
            background-size: cover;
            position: relative;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .form-box {
            width: 100%;
            max-width: 500px;
            background: rgba(255, 255, 255, 0.9);
            border-radius: 10px;
            padding: 40px 30px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .form-box h1 {
            font-size: 36px;
            margin-bottom: 30px;
            color: #ff9a24;
            font-weight: 600;
        }

        .form-box h1::after {
            content: '';
            width: 30px;
            height: 4px;
            border-radius: 3px;
            background: #ff9a24;
            position: absolute;
            bottom: -12px;
            left: 50%;
            transform: translateX(-50%);
        }

        .input-field {
            background: #eaeaea;
            margin: 12px 0;
            border-radius: 8px;
            display: flex;
            align-items: center;
            height: 50px;
            padding: 0 15px;
            transition: all 0.3s ease;
        }

        .input-field input, .input-field select {
            width: 100%;
            background: transparent;
            border: none;
            outline: none;
            padding: 12px;
            font-size: 16px;
            color: #555;
        }

        .input-field input:focus, .input-field select:focus {
            background-color: #fff3e0;
            border-radius: 8px;
        }

        .btn-field {
            width: 100%;
            margin-top: 20px;
        }

        .btn-field button {
            width: 100%;
            background: #ff9a24;
            color: #fff;
            height: 50px;
            border-radius: 25px;
            border: none;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .btn-field button:hover {
            background-color: #ff7f00;
        }

        .form-footer {
            margin-top: 20px;
            font-size: 14px;
        }

        .form-footer a {
            text-decoration: none;
            color: #ff9a24;
        }

        .form-footer a:hover {
            text-decoration: underline;
        }

        /* Responsive design */
        @media (max-width: 768px) {
            .form-box {
                padding: 30px 20px;
            }

            .form-box h1 {
                font-size: 28px;
            }

            .input-field input, .input-field select {
                font-size: 14px;
            }

            .btn-field button {
                font-size: 16px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-box">
            <!-- Display form title based on whether the pet object exists -->
            <h1><c:choose>
                    <c:when test="${not empty pet}">Edit Pet</c:when>
                    <c:otherwise>Add New Pet</c:otherwise>
                </c:choose></h1>

            <form action="pets" method="post">
                <!-- Hidden action field to specify the operation (add or update) -->
                <input type="hidden" name="action" value="${pet != null ? 'update' : 'add'}">

                <!-- Shelter ID field -->
                <div class="input-field">
                    <input type="number" id="shelterId" name="shelterId" value="${pet != null ? pet.shelterId : 0}" min="0" placeholder="Shelter ID" required>
                </div>

                <!-- Name field (should be unique identifier for editing/updating) -->
                <div class="input-field">
                    <input type="text" id="name" name="name" value="${pet != null ? pet.name : ''}" placeholder="Pet Name" required>
                </div>

                <!-- Breed field -->
                <div class="input-field">
                    <input type="text" id="breed" name="breed" value="${pet != null ? pet.breed : ''}" placeholder="Breed" required>
                </div>

                <!-- Age field -->
                <div class="input-field">
                    <input type="number" id="age" name="age" value="${pet != null ? pet.age : 0}" min="0" placeholder="Age" required>
                </div>

                <!-- Status field -->
                <div class="input-field">
                    <select id="status" name="status" required>
                        <option value="Available" ${pet != null && pet.status == 'Available' ? 'selected' : ''}>Available</option>
                        <option value="Adopted" ${pet != null && pet.status == 'Adopted' ? 'selected' : ''}>Adopted</option>
                    </select>
                </div>

                <!-- Submit button -->
                <div class="btn-field">
                    <button type="submit"><c:choose>
                            <c:when test="${not empty pet}">Update Pet</c:when>
                            <c:otherwise>Add Pet</c:otherwise>
                        </c:choose></button>
                </div>
            </form>

            <div class="form-footer">
                <p><a href="pets?action=list">Back to Pet List</a></p>
            </div>
        </div>
    </div>
</body>
</html>
