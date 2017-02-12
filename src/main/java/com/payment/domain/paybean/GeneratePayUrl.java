/**
 * @(#)GeneratePayUrl.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain.paybean;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity - 生成支付地址结果
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
public class GeneratePayUrl extends BaseRet {

    /** 支付渠道 */
    private String payChannel;

    /** 支付产品 */
    private String payProduct;

    /** 参数内容 */
    private final Map<String, Object> parameters = new HashMap<>();

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

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
