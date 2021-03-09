<%--
  User: Zhao JIN 
  Date: 08/03/2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login</h1>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input type="text" name="uname">
    <input type="password" name="pwd">
    <input type="submit" value="submit">
</form>
</body>
</html>
