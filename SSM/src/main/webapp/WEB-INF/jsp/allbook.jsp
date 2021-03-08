<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  User: Zhao JIN 
  Date: 07/03/2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>AllBook</title>
    <meta charset="UTF-8">
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <small style="font-size: 100px">Books List</small>
            </div>
        </div>
        <div class="row">
            <div class="col-md-8 column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">Add Book</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/allbook">All Book</a>
            </div>
            <div class="col-md-4 column">
                <form action="${pageContext.request.contextPath}/book/queryBook" method="post" style="float: right" class="form-inline">
                    <input type="text" placeholder="Book to Search" class="form-control" name="queryBookName">
                    <input type="submit" value="Search" class="btn btn-primary">
                </form>
            </div>
        </div>
    </div>
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>Num</th>
                    <th>Name</th>
                    <th>Stock</th>
                    <th>Details</th>
                    <th>Operation</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="book" items="${list}">
                    <tr>
                        <td>${book.bookId}</td>
                        <td>${book.bookName}</td>
                        <td>${book.bookStock}</td>
                        <td>${book.bookDetails}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/book/toUpdateBook/${book.bookId}">Modify</a>&nbsp;|&nbsp;
                            <a href="${pageContext.request.contextPath}/book/deleteBook/${book.bookId}">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
