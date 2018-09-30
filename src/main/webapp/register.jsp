<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/9/4
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>注册注册....</h1>

<form action="user" method="get">
    <table>
        <tr><td>用户名</td> <td> <input type="text" name="username"> </td>  </tr>
        <tr><td>密码</td> <td> <input type="password" name="password"> </td>  </tr>
        <tr><td>邮箱</td> <td> <input type="text" name="email"> </td>  </tr>
        <tr><td>手机号码</td> <td> <input type="text" name="phone"> </td>  </tr>
        <tr><td>密码找回问题</td> <td> <input type="text" name="question"> </td>  </tr>
        <tr><td>密码找回答案</td> <td> <input type="text" name="answer"> </td>  </tr>
        <tr><td> <input type="hidden" name="lever" value="1"><input type="hidden" name="operation" value="2"> </td>  </tr>
        <tr><td> <input type="submit" value="提交"> </td>  </tr>
    </table>



</form>

</body>
</html>
