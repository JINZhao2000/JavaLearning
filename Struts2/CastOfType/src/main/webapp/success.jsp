<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 13/11/2020
  Time: 20:52
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success</title>
</head>
<body>
(${point.x},${point.y})<br/>
${point}<br/>
<s:property value="point"/>
</body>
</html>
