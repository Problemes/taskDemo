<%--
  Created by IntelliJ IDEA.
  User: HR
  Date: 2017/9/25
  Time: 18:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Message Page</title>
</head>
<body>

    <h4> Message Page </h4>

    <div id="message">

    </div>

    <script type="text/javascript" src="js/jquery-1.12.2.min.js"></script>
    <script type="text/javascript" src="js/sockjs.min.js"></script>

    <script type="text/javascript">

        $(function(){
            //建立socket连接
            var sock;
            var basePath = window.location.protocol + "://" + window.location.host  + "${pageContext.request.contextPath}";
            var wsPath = "ws://" + window.location.host  + "${pageContext.request.contextPath}";
            if ('WebSocket' in window) {
                sock = new WebSocket(wsPath + "/socketServer");
            } else if ('MozWebSocket' in window) {
                sock = new MozWebSocket(wsPath + "/socketServer");
            } else {
                sock = new SockJS(basePath + "/sockjs/socketServer");
            }
            sock.onopen = function (e) {
                console.log(e);
            };
            sock.onmessage = function (e) {
                console.log(e)
                $("#message").append("<p><font color='red'>"+e.data+"</font>")
            };
            sock.onerror = function (e) {
                console.log(e);
            };
            sock.onclose = function (e) {
                console.log(e);
            }
        });

    </script>

</body>
</html>
