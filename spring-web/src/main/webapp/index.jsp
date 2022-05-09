<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加学生</title>
</head>
<body>
<div align="center">
    <p>添加学生</p>
    <form action="add" method="post">
        姓名：<input type="text" name="name"> <br/>
        年龄：<input type="text" name="age"> <br/>
        <input type="submit" value="注册学生">
    </form>

    <br/>
    <br/>
    <p>查询学生</p>
    <form action="queryStudent" method="get">
        学生id：<input type="text" name="stuid"> <br/>
        <input type="submit" value="查询学生">
    </form>
</div>
</body>
</html>
