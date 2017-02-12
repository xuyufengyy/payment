<!DOCTYPE html>

<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;">
    <meta name="apple-mobile-web-app-capable" content="no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title></title>
    <link type="text/css" rel="stylesheet" href="/css/pay/pay.css">
</head>
<!-- END HEAD -->
<body>
<div class="dummy"></div>
<div class="title">
    <p>
        <img src="/img/pay/payFail.png"/>${message}
    </p>
</div>
<div class="dummy"></div>
<div class="button_pad">
    <input id="button" type="button" class="button_sub" onclick="jump()" value="返回"/>
</div>
<script src="/js/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/client.interaction.js"></script>
<script type="text/javascript">
    function publicMethod (cmd, para) {
        return　commonMethod(cmd, para);
    }

    function jump(){
        if(v_operatingSystem == "android"){
            JavaScriptInterface.onRequest("PaySuccess", "${flag}");
        }
        if(v_operatingSystem == "ios"){
           window.location.href = "objc::PaySuccess::"+"${flag}";
        }
    }
</script>
</body>
</html>
