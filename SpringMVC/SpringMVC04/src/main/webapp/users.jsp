<%--
  User: Zhao JIN 
  Date: 08/03/2021
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User List</title>
    <script
            src="https://code.jquery.com/jquery-3.6.0.js"
            integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
            crossorigin="anonymous"></script>
    <script>
        $(function (){
            $("#btn").click(function () {
                $.post("${pageContext.request.contextPath}/test3",function (data){
                    let html = "";
                    for (let i = 0; i < data.length; i++) {
                        html+="<tr>";
                        html+="<td>"+data[i].name+"</td>";
                        html+="<td>"+data[i].age+"</td>";
                        html+="<td>"+data[i].sex+"</td>";
                        html+="</tr>"
                    }
                    $("#tbody").html(html);
                },"json");
            });
        });
    </script>
</head>
<body>
<input type="button" value="GET" id="btn">
<table>
    <tr>
        <td>Name</td>
        <td>Age</td>
        <td>Sex</td>
    </tr>
    <tbody id="tbody">
    </tbody>
</table>
</body>
</html>
