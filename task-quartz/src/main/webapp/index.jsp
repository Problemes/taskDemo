<%--
  Created by IntelliJ IDEA.
  User: HR
  Date: 2017/9/19
  Time: 12:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>HOME PAGE</title>
</head>
<body>
    <h4>  Test index  </h4>
    <form action="${pageContext.request.contextPath}/test/test">
        <input type="text" name="username" value="ready:">
        <input type="text" name="schId" />
        <button type="submit"> submit </button>
    </form>

    <form action="${pageContext.request.contextPath}/test/goods">
        <input type="text" name="goodsId" />
        <button type="submit"> submit </button>
    </form>

</body>
</html>
