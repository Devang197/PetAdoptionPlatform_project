<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error</h1>

    <!-- Display the error message if it exists -->
    <c:if test="${not empty error}">
        <p>${error}</p>
    </c:if>

    <!-- Link to go back to the pet list -->
    <a href="pets">Go Back</a>
</body>
</html>
