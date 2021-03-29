<%--
  Created by IntelliJ IDEA.
  User: MagicSnake
  Date: 2021/3/29
  Time: 19:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/user/attest23" method="post" enctype="multipart/form-data">
        用户名：<input type="text" name="name"><br />
        文件：<input type="file" name="uploadFile"><br />
        文件：<input type="file" name="uploadFile2"><br />
        文件：<input type="file" name="uploadFile3"><br />
        <input type="submit" value="提交">
    </form>
</body>
</html>
