<%--
  Created by IntelliJ IDEA.
  User: Zhao JIN
  Date: 13/11/2020
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Data Process</title>
</head>
<body>
<form action="save.action" method="post">
    username:<input type="text" name="user.username"/><s:fielderror fieldName="user.username"/><br>
    sex:<input type="text" name="user.sex"/><br>
    age:<input type="number" name="user.age"/><br>
    <input type="submit" value="save"/>
</form>
</body>
</html>
