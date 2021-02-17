<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 17/02/2021
  Time: 23:20
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Save</title>
</head>
<body>
<form action="./temps.jsp" method="post">
    <s:token></s:token>
    Username: <input type="text" name="userI.uname"><br/>
    Age: <input type="number" name="userI.age"><br/>
    <input type="submit" value="submit">
</form>
</body>
</html>
