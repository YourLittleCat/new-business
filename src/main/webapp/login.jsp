<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/3
  Time: 20:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录~</title>
</head>
<body>
<h1>大佬，赶紧登录</h1>



<form action="user" method="get">

    <table>
        <tr><td>用户名</td> <td> <input type="text" name="username"> </td>  </tr>
        <tr><td>密码</td> <td> <input type="password" name="password"> </td>  </tr>
        <tr><input type="hidden" name="operation" value="1"></tr>
        <tr><td> <input type="submit" value="登录"></td> <td><a href="register.jsp">注册</a></td><td><a href="findpwd.jsp">忘记密码</a></td> </tr>
    </table>




</form>
</body>
</html>
