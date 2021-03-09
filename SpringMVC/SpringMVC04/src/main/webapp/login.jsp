<%--
  User: Zhao JIN 
  Date: 08/03/2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <script>
        function validateName(){
            $.post({
                url: "${pageContext.request.contextPath}/validate",
                data: {"name":$("#uname").val()},
                success: function (data) {
                    document.getElementById("userInfo").innerHTML = data;
                }
            })
        }
        function validatePwd(){
            $.post({
                url: "${pageContext.request.contextPath}/validate",
                data: {"pwd":$("#pwd").val()},
                success: function (data) {
                    document.getElementById("pwdInfo").innerText = data;
                }
            })
        }
    </script>
</head>
<body>
<p>
    Username:<input type="text" id="uname" onblur="validateName()">
    <span id="userInfo"></span>
</p>
<p>
    Password:<input type="password" id="pwd" onblur="validatePwd()">
    <span id="pwdInfo"></span>
</p>
</body>
</html>
