/**
 * @(#)WxpayApp.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain.paybean;

import java.io.Serializable;

/**
 * Entity - 微信app支付
 *
 * @author	Xu minghua 2017/02/14
 * @version	1.0
 */
public class WxpayApp implements Serializable {

    private static final long serialVersionUID = -2485410658725673666L;

    /** 请求参数 */
    private String appid;//微信开放平台审核通过的应用APPID
    private String partnerid;//微信支付分配的商户号
    private String prepayid;//微信返回的支付交易会话ID
    private String Package;//暂填写固定值Sign=WXPay
    private String noncestr;//随机字符串，不长于32位
    private String timestamp;//时间戳
    private String sign;//签名

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String aPackage) {
        Package = aPackage;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
