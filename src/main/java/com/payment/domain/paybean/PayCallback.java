/**
 * @(#)PayCallback.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.domain.paybean;

import java.util.HashMap;
import java.util.Map;

/**
 * Entity - 回调数据处理结果
 *
 * @author	xuminghua 2016/5/13
 * @version	1.0
 */
public class PayCallback extends BaseRet {

    /** 订单号 */
    private String orderNumber;

    /** 回调类型 */
    private String callbackType;

    /** 支付结果*/
    private Integer payStatus;

    /** 参数内容 */
    private Map<String, Object> parameters = new HashMap<>();

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(String callbackType) {
        this.callbackType = callbackType;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
