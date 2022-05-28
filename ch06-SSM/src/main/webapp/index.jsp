<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName()
            +":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <title>SSM</title>
    <base href="<%=basePath%>">
</head>
<body>
    <div align="center">
        <p>SSM整合开发的例子</p>
        <table>
            <tr>
                <td><a href="addStudent.jsp">注册学生</a> </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td><a href="listStudent.jsp">查看学生</a> </td>
            </tr>
        </table>
    </div>
</body>
</html>
