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
    <h3>查询订单</h3>
    <div class="mrmain">
        <form name="subForm" action="/v1/test/query_order_status" method="post" style="margin:0px">
            <table>
                <tbody>
                <tr>
                    <th>支付渠道【payChannel】:</th>
                    <td><input style="background:#ADADAD" type=text class="wtxt" name="payChannel" value="03"/> <span>*</span></td>
                </tr>
                <tr>
                    <th>支付产品【payProduct】：</th>
                    <td><input style="background:#ADADAD" type=text class="wtxt" name="payProduct" value="03"/> <span>*</span></td>
                </tr>
                <tr>
                    <th>订单号【orderNumber】：</th>
                    <td ><input type="text" class="wtxt" name="orderNumber" value=""/> <span>*</span></td>
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