<%--
  User: Zhao JIN 
  Date: 08/03/2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Addbook</title>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>New Book</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/book/addbook" method="post">
        <div class="form-group">
            <label for="bkname">Book Name: </label>
            <input type="text" class="form-control" id="bkname" name="bookName" required>
        </div>
        <div class="form-group">
            <label for="bkstock">Book Stock: </label>
            <input type="text" class="form-control" id="bkstock" name="bookStock" required>
        </div>
        <div class="form-group">
            <label for="bkdetails">Book Details: </label>
            <input type="text" class="form-control" id="bkdetails" name="bookDetails" required>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="save">
        </div>
    </form>
</div>
</body>
</html>
