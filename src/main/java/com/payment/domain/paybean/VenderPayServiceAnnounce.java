/**
 * @(#)VenderPayServiceAnnounce.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain.paybean;

/**
 * 厂家服务支付服务声明信息
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
public class VenderPayServiceAnnounce {

    /** 支付接口标识 */
    private String payId;

    /** 是否需要提供回调 */
    private boolean hasCallback;

    /** 回调地址 */
    private String callbackUrl;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public boolean isHasCallback() {
        return hasCallback;
    }

    public void setHasCallback(boolean hasCallback) {
        this.hasCallback = hasCallback;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}


