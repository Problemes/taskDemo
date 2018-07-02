<%--
  Created by IntelliJ IDEA.
  User: HR
  Date: 2018/4/12
  Time: 11:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login...</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy.js"></script>
    <script type="text/javascript" src="http://cdn.staticfile.org/jquery.qrcode/1.0/jquery.qrcode.min.js"></script>
    <%--<script type="text/javascript" src="./js/jquery.qrcode.min.js"></script>--%>

    <script>
        function uuid() {

            var s = [];

            var hexDigits = "0123456789abcdef";

            for (var i = 0; i < 36; i++) {

                s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);

            }

            s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010

            s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01

            s[8] = s[13] = s[18] = s[23] = "-";


            var uuid = s.join("");

            return uuid;
        }
    </script>

    <script type="text/javascript">
        var uuid = uuid()
        var goEasy = new GoEasy({appkey: 'BC-d3a3a7f09f3f4aee95fe82934c20bd4c'});
        goEasy.subscribe({
            channel: uuid,
            onMessage: function (message) {

                var obj = JSON.parse(message.content);
                if (obj.result == '0') {
//                    window.location.href="http://www.baidu.com/";
                    window.location.href = "https://weibo.com/u/1767150692?is_all=1&openid=" + obj.openid;
                }

                //alert('接收到消息:' + message.content);//拿到了信息之后，你可以做你任何想做的事
            }
        });
    </script>


</head>
<body>

<p> QRCode: </p>

<div id="qrcodeCanvas"></div>

<div id="uuid"></div>

<div id="weixinLogin">

    <button onclick="login()">微信登录</button>
</div>

<script> function login() {
    $.ajax({
        type: "POST", //这个路径根据自己的情况填写
        url: "/wechat/getCode", success: function (data) {
            console.log(data);
            if (data.code == 200) {
                window.location.href = (data.result);
            } else {
                alert("认证失败");
            }
        }, error: function (data) {
            alert("认证失败");
        }
    });
}
</script>


<script>
    jQuery('#qrcodeCanvas').qrcode({
        width: 300,
        height: 300,
//        text: "http://www.baidu.com/"
//        text: "http://qq.tunnel.qydev.com/task/login?param1=" + uuid  //http://qq.tunnel.qydev.com
        text: "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxe0828ea2f2e4648f&redirect_uri=http://qq.tunnel.qydev.com/task/wechat/gz/getCode?uuid="+ uuid +"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect "
    });

//    console.log(uuid)


</script>

</body>
</html>