<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 21/02/2021
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User List Ajax</title>
    <script type="text/javascript" src="js/jquery-3.5.1.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function (){
                $.post("list.action",function (data) {
                    let html="";
                    for (let i=0; i<data.length; i++){
                        html+="<tr>";
                        html+="<td>";
                        html+=data[i].uid;
                        html+="</td>";
                        html+="<td>";
                        html+=data[i].uname;
                        html+="</td>";
                        html+="<td>";
                        html+=data[i].age;
                        html+="</td>";
                        html+="</tr>"
                    }
                    $("#content").html(html);
                },"json");
            });
        });
    </script>
</head>
<body>
<button id="btn">Get List</button>
<table width="80%" align="center">
    <thead>
    <tr>
        <td>UID</td>
        <td>UNAME</td>
        <td>AGE</td>
    </tr>
    </thead>
    <tbody id="content"></tbody>
</table>
</body>
</html>
