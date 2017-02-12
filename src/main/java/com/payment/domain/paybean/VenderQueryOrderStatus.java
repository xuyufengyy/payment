/**
 * @(#)VenderQueryOrderStatus.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.domain.paybean;

/**
 * Entity - 厂家订单状态查询结果
 *
 * @author	xuminghua 2016/5/13
 * @version	1.0
 */
public class VenderQueryOrderStatus extends BaseRet {

    /** 支付结果 */
    private Integer payStatus;

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

}
