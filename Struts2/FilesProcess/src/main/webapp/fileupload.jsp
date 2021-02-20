<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 20/02/2021
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>File Upload</title>
</head>
<body>
<form action="upload.action" method="post" enctype="multipart/form-data">
    File:<input type="file" name="file"><br/>
    <input type="submit" value="upload">
</form>
</body>
</html>
