<%--
  Created by IntelliJ IDEA.
  User: HR
  Date: 2018/4/12
  Time: 17:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport"/>

    <title>Login...</title>
    <script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>

    <style type="text/css">
        button {
            background: #5994E6;
            outline: none;
            width: 50%;
            height: 30px;
            display: block;
            margin: 30px auto;
            color: #FFF;
        }

        div {
            color: red;
            font-size: 12px;
            margin: 10px auto;
            text-align: center;
        }
    </style>

</head>
<body>
<h4>Login...</h4>


<p> ${requestScope.param} </p>


<section>
    <button class="button1">允许登录...</button>
    <br/>
    <button class="button2">拒绝登录...</button>
    <%--<div>点击按钮发送数据...</div>--%>
</section>

<script type="text/javascript">

    $(document).ready(function () {

        var param = '${requestScope.param}';
        //获取button 按钮
        $('button').click(function () {
            var data;
            if ($(this).attr('class') == 'button1') {
                data = {
                    param: param,
                    verify: '0'
                }
            } else if ($(this).attr('class') == 'button2') {
                data = {
                    param: param,
                    verify: '1'
                }
            }
            $('div').html('正在发送' + data.param + ':' + data.verify);
            setTimeout(function () {
                //发送数据
                $.ajax({
                    url: 'http://qq.tunnel.qydev.com/task/loginVerify',
                    type: 'GET',
                    dataType: 'json',
                    // dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
                    data: data,
                })
                        .done(function (data) {
                            if (data.result.equals("0")) {
                                console.log("success");
                                $('div').html('登录成功！');
                            } else {
                                console.log("success");
                                $('div').html('拒绝登录！');
                            }

                            window.opener = null;
                            window.open('', '_self');
                            window.close();
                        })
                        .fail(function () {
                            $('div').html('发送失败！');
                            window.opener = null;
                            window.open('', '_self');
                            window.close();

                        })
                        .always(function () {
                            console.log("结束！");
                            window.opener = null;
                            window.open('', '_self');
                            window.close();

                        });
            }, 2000)
        })

    });
</script>


</body>
</html>
