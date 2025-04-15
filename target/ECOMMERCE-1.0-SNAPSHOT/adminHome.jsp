<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@page import="Service_Implement.*, Service.*,Beans.*,java.util.*,jakarta.servlet.ServletOutputStream,java.io.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Home</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 5 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Font Awesome for Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <!-- Custom CSS -->
    <link rel="stylesheet" href="css/changes.css">
</head>
<body style="background-color: #E6F9E6;">
<%
    /* Checking the user credentials */
    String userType = (String) session.getAttribute("usertype");
    String userName = (String) session.getAttribute("username");
    String password = (String) session.getAttribute("password");

    if (userType == null || !userType.equals("admin")) {
        response.sendRedirect("login.jsp?message=Access Denied, Login as admin!!");
    } else if (userName == null || password == null) {
        response.sendRedirect("login.jsp?message=Session Expired, Login Again!!");
    }
%>

<jsp:include page="header.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <div class="card shadow rounded">
                <div class="card-header bg-success text-white text-center">
                    <h3><i class="fas fa-user-cog"></i> Admin Dashboard</h3>
                </div>
                <div class="card-body text-center" style="background-color: #F9FFF9;">
                    <form>
                        <div class="d-grid gap-3">
                            <button type="submit" class="btn btn-outline-primary btn-lg" formaction="adminViewProduct.jsp">
                                <i class="fas fa-eye"></i> View Products
                            </button>
                            <button type="submit" class="btn btn-outline-success btn-lg" formaction="addProduct.jsp">
                                <i class="fas fa-plus-circle"></i> Add Products
                            </button>
                            <button type="submit" class="btn btn-outline-danger btn-lg" formaction="removeProduct.jsp">
                                <i class="fas fa-trash-alt"></i> Remove Products
                            </button>
                            <button type="submit" class="btn btn-outline-warning btn-lg" formaction="updateProductById.jsp">
                                <i class="fas fa-edit"></i> Update Products
                            </button>
                        </div>
                    </form>
                </div>
                <div class="card-footer text-center text-muted">
                    <small>Manage your product inventory with ease.</small>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.html"%>
</body>
</html>
