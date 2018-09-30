<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/4
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>${ex.msg}</h1>

<span id="warn"> ${ex.warn}</span>



<script>

window.onload=function () {

var count=3;

   window.setInterval(function () {
       count--;

       if (count ==0){

          window.location.href="${ex.url}";
          window.clearInterval();
       }

       document.getElementById("warn").innerHTML=count+"s后将自动跳转到登录页面..."


   },1000)


}



</script>
</body>
</html>
