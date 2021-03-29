<%--
  Created by IntelliJ IDEA.
  User: MagicSnake
  Date: 2021/3/29
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>
    <form action="${pageContext.request.contextPath}/user/attest15" method="post">
        第一个用户的姓名：<input type="text" name="userList[0].name"/><br />
        第一个用户的年龄：<input type="text" name="userList[0].age"/><br />
        第二个用户的姓名：<input type="text" name="userList[1].name"/><br />
        第二个用户的年龄：<input type="text" name="userList[1].age"/><br />
        第三个用户的姓名：<input type="text" name="userList[2].name"/><br />
        第三个用户的年龄：<input type="text" name="userList[2].age"/><br />
        <input type="submit" value="提交">
    </form>



</body>
</html>
