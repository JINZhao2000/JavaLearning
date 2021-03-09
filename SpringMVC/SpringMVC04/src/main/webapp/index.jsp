<%--
  User: Zhao JIN 
  Date: 08/03/2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
    <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <script>
        function test(){
            $.post({
                url: "${pageContext.request.contextPath}/test2",
                data: {"name":$("#uname").val()},
                success: function (data,status) {
                    alert(data);
                    console.log(status);
                }
            });
        }
    </script>
</head>
<body>
Name:<input type="text" id="uname" onblur="test()">
</body>
</html>
