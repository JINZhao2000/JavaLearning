<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 21/02/2021
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
    <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
    <script type="text/javascript">
        $(function (){
           $("#uname").blur(function (){
               $.post("checkName.action",{"username":$(this).val()},function (data) {
                   if("true"==data){
                       $("#uname").css("border","1px solid red");
                   }else {
                       $("#uname").css("border","1px solid green");
                   }
               });
           });
        });
    </script>
</head>
<body>
Username: <input type="text" name="username" id="uname"><br>
</body>
</html>
