<!DOCTYPE html>
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>支付测试</title>
</head>
<body>
<div class="mright">
    <h3>>订单回调</h3>
    <div class="mrmain">
        <table>
            <tbody>
            <tr>
                <th>回调结果:</th>
                <td>${message}</td>
            </tr>
            <#if errorCode??>
                <tr>
                    <th>错误码：</th>
                    <td>${errorCode}</td>
                </tr>
            </#if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>