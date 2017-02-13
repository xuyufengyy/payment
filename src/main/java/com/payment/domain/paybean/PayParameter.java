/**
 * @(#)PayParameter.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain.paybean;

/**
 * Entity - 支付入参
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
public class PayParameter {

    /** 支付渠道 01 ios客户端， 02 android客户端， 03 wap，04 web */
    private String payChannel;

    /** 支付产品 00 测试，01 支付宝，02 微支付，03 联动优势收银台 */
    private String payProduct;

    /** 订单号 */
    private String orderNumber;

    /** 订单金额(元) */
    private String amount;

    /** 描述 */
    private String content;

    /** 支付回调地址 */
    private String payCallbackUrl;

    /** 参数内容json */
    private String parameters;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayProduct() {
        return payProduct;
    }

    public void setPayProduct(String payProduct) {
        this.payProduct = payProduct;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }
}
