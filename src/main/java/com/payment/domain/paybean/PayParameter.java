/**
 * @(#)PayParameter.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain.paybean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity - 支付入参
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
public class PayParameter {

    /** 支付渠道 */
    private String payChannel;

    /** 支付产品 */
    private String payProduct;

    /** 订单号 */
    private String orderNumber;

    /** 订单金额(元) */
    private BigDecimal amount;

    /** 描述 */
    private String content;

    /** 支付回调地址 */
    private String payCallbackUrl;

    /** 参数内容 */
    private Map<String, Object> parameters = new HashMap<>();

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
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

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
