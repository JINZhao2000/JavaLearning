<%--
  User: Zhao JIN 
  Date: 06/03/2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
    <script type="text/javascript">
        let user = {
            name:"user",
            age:18,
            sex:"N"
        };
        let userJson = JSON.stringify(user);
        let obj = JSON.parse(userJson);
        console.log(user);
        console.log(userJson);
        console.log(obj);
    </script>
</head>
<body>

</body>
</html>
