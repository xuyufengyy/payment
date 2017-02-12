<!DOCTYPE html>
<head>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <title>支付测试</title>
    <style>
        input{
            width: 400px;
            height: 35px;
        }
    </style>
</head>
<body>
<div class="mright">
    <h3>首次支付请求参数加密</h3>
    <div class="mrmain">
        <form name="subForm" action="/v1/test/encryption" method="get" style="margin:0px">
            <table>
                <tbody>
                <tr>
                    <th>订单号【orderNumber】：</th>
                    <td ><input type="text" class="wtxt" name="orderNumber" value=""/> <span>*</span></td>
                </tr>
                <tr>
                    <th>订单金额【amount】：</th>
                    <td ><input style="background:#ADADAD" readonly type="text" class="wtxt" name="amount" value="0.01"/> <span>*</span></td>
                </tr>
                <tr>
                    <th>描述【content】：</th>
                    <td><input style="background:#ADADAD" type="text" class="wtxt" name="content" value="订单支付"/></td>
                </tr>
                <tr>
                    <th>支付回调地址【payCallbackUrl】：</th>
                    <td><input style="background:#ADADAD" class="wtxt" name="payCallbackUrl" /> <span>*</span></td>
                </tr>
                <tr>
                    <th>参数【map】：</th>
                    <td><input style="background:#ADADAD" type="text" class="wtxt" name="parameters" /> <span>*</span></td>
                </tr>
                <tr>
                    <th></th>
                    <td><div class="pbutton2 pline"><input type="submit" value="提交" class="button" /></div></td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</html>