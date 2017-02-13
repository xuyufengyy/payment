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
        table th {
            text-align: right;
        }
        input {
            background-color: #eeeeee;
            border: 0px;
        }
    </style>
</head>
<body>
<div >
    <h3>首次支付请求</h3>
    <div>
        <form name="subForm" action="/payment/generate_pay_url" method="get">
            <table>
                <tbody>
                <tr>
                    <th>加密后参数【encryptionText】:</th>
                    <td>
                        <input type="hidden" name="encryptionText" value="${encryptionText}" />
                        <textarea readonly cols="40" rows="10" style="resize: none;">${encryptionText}</textarea>
                    </td>
                </tr>
                <tr>
                    <th></th>
                    <td><input type="submit" value="提交" style="background-color: #e30045;" /></td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</html>