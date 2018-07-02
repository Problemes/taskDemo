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

<img src="/dist/images/favicon-16x16.png" />

<h4> Test index </h4>
<form action="${pageContext.request.contextPath}/test/test">
    <input type="text" name="username" value="ready:">
    <input type="text" name="schId"/>
    <button type="submit"> submit</button>
</form>

<form action="${pageContext.request.contextPath}/test/goods">
    <input type="text" name="goodsId"/>
    <button type="submit"> submit</button>
</form>

<form action="${pageContext.request.contextPath}/test/user">
    <input type="text" name="user"/>
    <button type="submit"> submit</button>
</form>

<form action="${pageContext.request.contextPath}/test/change">
    <input type="text" name="userId"/>
    <input type="text" name="password"/>
    <button type="submit"> submit</button>
</form>

<br/><br/>

<form action="${pageContext.request.contextPath}/test/role">
    Role: <input type="text" name="role"/>
    Desc: <input type="text" name="description"/>
    Available: <input type="radio" name="available"/>
    <button type="submit"> submit</button>
</form>

<br/><br/><br/><br/>

<form action="test/deleteUR" method="get" style="border:1px solid red;">
    <table>
        <tr>
            <td colspan="2">这个表单演示了对象数据绑定的方法，<%-- 以及对象中的Set，List，Array数据绑定（三者类似） --%></td>
        </tr>
        <tr>
            <td>用户名：</td>
            <%--<td><input type="text" name="userName" value="张三"></td>--%>
        </tr>
        <tr>
            <td>用户地址：</td>
            <%--<td><input type="text" name="address" value="江苏省无锡市新区菱湖大道200号"><br></td>--%>
        </tr>
        <tr>
            <td>手机品牌1：</td>
            <td>
                <input type="text" name="userRoles[0].userId" value=""><br>
                <input type="text" name="userRoles[0].roleId" value=""><br>
            </td>
        </tr>
        <%--<tr>--%>
        <%--<td>手机品牌2：</td>--%>
        <%--<td>--%>
        <%--<input type="text" name="userRoles[1].userId" value=""><br>--%>
        <%--<input type="text" name="userRoles[1].roleId" value=""><br>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td>手机品牌3：</td>--%>
        <%--<td>--%>
        <%--<input type="text" name="userRoles[2].userId" value=""><br>--%>
        <%--<input type="text" name="userRoles[2].roleId" value=""><br>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td colspan="2" style="text-align: right;">
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>

<br/><br/><br/><br/>

<form action="${pageContext.request.contextPath}/test/findUser">
    User: <input type="text" name="username"/>
    <button type="submit"> submit</button>
</form>

<form action="${pageContext.request.contextPath}/test/findRoles">
    Role: <input type="text" name="username"/>
    <button type="submit"> submit</button>
</form>

<form action="${pageContext.request.contextPath}/test/findPermissions">
    Permission: <input type="text" name="username"/>
    <button type="submit"> submit</button>
</form>

</body>
</html>
