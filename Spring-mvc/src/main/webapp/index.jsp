<%--
  Created by IntelliJ IDEA.
  User: pyao
  Date: 2022/5/10
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>请求方式</title>
    <script type="text/javascript" src="js/jquery-3.4.1.js"></script>
    <script type="text/javascript">
        $(function(){
            //绑定事件
            $("#btnAjax").on("click",function(){
                $.ajax({
                    // url:"return-void-ajax.do",
                    //url:"doStudentJson.do",
                    url:"doListJsonArray.do",
                    // url:"doStringData.do",
                    data:{
                        name:"lisi",
                        age:20
                    },
                    dataType:"json",
                    // dataType:"text",
                    success:function(resp){
                        // resp = XMLHttpRequest.responseText;
                        //alert("resp==="+resp.name+"====="+resp.age);
                        //$.each(resp,function(i,n){
                        // n  {"name":"李四同学","age":26}
                        // alert("n name="+n.name+"====age="+n.age);
                        //})

                        alert("resp=="+resp);

                    }
                })
            })
        })
    </script>
</head>
<body>
    <a href="some.do">发起some的get请求</a>
    <br/>
    <br/>

    <form action="other.do" method="post">
        姓名：<input type="text" name="name"> <br/>
        年龄：<input type="text" name="age"> <br/>
        <input type="submit" value="post请求">
    </form>

    <br/>
    <br/>
    <p>逐个接收请求参数</p>
    <form action="received-property.do" method = "post">
        姓名：<input type="text" name="name"> <br/>
        年龄：<input type="text" name="age"> <br/>
        <input type="submit" value ="提交参数">
    </form>

    <br/>
    <br/>

    <button id="btnAjax">发起Ajax请求</button>






</body>
</html>
