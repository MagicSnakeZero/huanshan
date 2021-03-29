<%--
  Created by IntelliJ IDEA.
  User: MagicSnake
  Date: 2021/3/29
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script>
        var userList = new Array();
        userList.push({name:"one",age:18});
        userList.push({name:"two",age:20});
        userList.push({name:"three",age:30});
        $.ajax({
            type:"post",
            url:"${pageContext.request.contextPath}/user/attest16",
            data:JSON.stringify(userList),
            contentType:"application/json;charset=utf-8"
        });
    </script>
</head>
<body>

</body>
</html>
