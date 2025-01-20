<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito+Sans:wght@400;600&display=swap" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            font-family: "Nunito Sans", sans-serif;
            box-sizing: border-box;
        }

        .container {
            width: 100%;
            height: 100vh;
            background-image: url('background.jpg');
            background-position: center;
            background-size: cover;
            position: relative;
        }

        .form-box {
            width: 90%;
            max-width: 450px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: #fff;
            padding: 50px 60px 70px;
            text-align: center;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }

        .form-box h1 {
            font-size: 30px;
            margin-bottom: 60px;
            color: #ff9a24;
            position: relative;
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
            transform: translate(-50%);
        }

        .input-field {
            background: #eaeaea;
            margin: 10px 0;
            border-radius: 3px;
            display: flex;
            align-items: center;
            max-height: 65px;
            transition: max-height 0.5s;
            overflow: hidden;
        }

        input {
            width: 100%;
            background: transparent;
            border: 0;
            outline: 0;
            padding: 18px 15px;
        }

        .input-field i {
            margin-left: 10px;
            color: #999;
        }

        form p {
            text-align: left;
            font-size: 13px;
        }

        form p a {
            text-decoration: none;
            color: #ff9a24;
        }

        .btn-field {
            width: 100%;
            display: flex;
            justify-content: space-between;
        }

        .btn-field button {
            flex-basis: 48%;
            background: #ff9a24;
            color: #fff;
            height: 40px;
            border-radius: 20px;
            border: 0;
            outline: 0;
            cursor: pointer;
            transition: background 1s;
        }

        .input-group {
            height: 230px;
        }

        .btn-field button.disable {
            background: #eaeaea;
            color: #555;
        }

        .error, .success {
            text-align: center;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }

        .error {
            color: red;
            background-color: #ffe6e6;
            border: 1px solid red;
        }

        .success {
            color: green;
            background-color: #e6ffe6;
            border: 1px solid green;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="form-box">
            <h1>User Registration</h1>

            <!-- Display error message from the backend using JSTL -->
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>

            <!-- Display success message from the backend using JSTL -->
            <c:if test="${not empty message}">
                <p class="success">${message}</p>
            </c:if>

            <!-- Registration form -->
            <form action="register" method="post">
                <div class="input-field">
                    <input type="text" id="name" name="name" placeholder="Name" required>
                </div>
                <div class="input-field">
                    <input type="email" id="email" name="email" placeholder="Email" required>
                </div>
                <div class="input-field">
                    <input type="password" id="password" name="password" placeholder="Password" required>
                </div>
                <div class="input-field">
                    <input type="text" id="role" name="role" placeholder="Role (Shelter, Adopter, Admin)" required>
                </div>
                <div class="btn-field">
                    <button type="submit">Register</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
