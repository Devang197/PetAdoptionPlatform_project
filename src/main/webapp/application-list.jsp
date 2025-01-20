<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<!DOCTYPE html>
<html>
<head>
    <title>Application List</title>
    <style>
        *{
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
            background-image: url('background.jpg');
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

        h1 {
            color: #ff9a24;
            font-size: 30px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="content">
            <h1>Application List</h1>
            <a href="application-form.jsp" class="btn" style="margin: 20px;">Add New Application</a>

            <!-- Display success or error messages -->
            <c:if test="${not empty message}">
                <div class="message" style="color: green; text-align: center;">
                    ${message}
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message" style="color: red; text-align: center;">
                    ${errorMessage}
                </div>
            </c:if>

            <!-- Application table -->
            <table>
                <thead>
                    <tr>
                        <th>Application ID</th>
                        <th>Adopter ID</th>
                        <th>Pet ID</th>
                        <th>Status</th>
                        <th>Application Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <!-- Iterate over the applicationList and display each application -->
                    <c:forEach var="application" items="${applicationList}">
                        <tr>
                            <td>${application.applicationId}</td>
                            <td>${application.adopterId}</td>
                            <td>${application.petId}</td>
                            <td>${application.status}</td>

                            <!-- Format application date using SimpleDateFormat -->
                            <td>
                                <c:set var="formattedDate" value="${application.applicationDate}" />
                                <c:choose>
                                    <c:when test="${not empty formattedDate}">
                                        <fmt:formatDate value="${formattedDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                                    </c:when>
                                    <c:otherwise>
                                        N/A
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <a href="applications?action=edit&id=${application.applicationId}" class="btn">Edit</a>
                                <a href="applications?action=delete&id=${application.applicationId}" class="btn btn-delete" 
                                   onclick="return confirm('Are you sure you want to delete this application?');">
                                    Delete
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>
