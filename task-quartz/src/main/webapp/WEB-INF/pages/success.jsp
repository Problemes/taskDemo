<%--
  Created by IntelliJ IDEA.
  User: HR
  Date: 2017/9/19
  Time: 15:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Success page</title>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy.js"></script>
</head>

<script type="text/javascript">
    var goEasy = new GoEasy({appkey: 'BC-d3a3a7f09f3f4aee95fe82934c20bd4c'});
    goEasy.subscribe({
        channel: 'channelX',
        onMessage: function(message){
            alert('接收到消息:' + message.content);//拿到了信息之后，你可以做你任何想做的事
        }
    });
</script>

<body>

    <h4> success page: </h4>
    <p> school: ${school.schName} </p>
    <br/><br/>
    <p> goods: ${goods.goodsName}</p>
    <br/><br/>
    <p> username: ${username}</p>
    <br/><br/>

    <p> User: ${user}</p>
    <br/><br/>
    <p> User: ${user}</p>

    <br/><br/>
    <p> Success: ${key} </p>

    <br/><br/>
    <p> User: ${user.id} -- ${user.username} -- ${user.password} -- ${user.salt} -- ${user.locked} </p>

    <br/><br/>
    <p> Role: ${role}</p>

</body>
</html>
