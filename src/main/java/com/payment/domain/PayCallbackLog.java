/**
 * @(#)PayCallbackLog.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.domain;

import javax.persistence.*;

/**
 * Entity - 支付回调日志
 *
 * @author	xuminghua 2016/5/11
 * @version	1.0
 */
@Entity
@Table(name = "ex_pay_callback_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ex_pay_callback_log_sequence")
public class PayCallbackLog extends BaseEntity{

    private static final long serialVersionUID = -5148741456140731337L;

    /** 状态 0 正常 1删除 */
    private Integer state;

    /** 订单号 */
    private String orderNumber;

    /** 回调参数 */
    private String parameters;

    /** 支付产品 00 测试，01 支付宝，02 微支付，03 联动优势收银台 */
    private String payProduct;

    /**
     * 获取 状态
     * @return 状态
     */
    @Column(columnDefinition="INT default 0")
    public Integer getState() {
        return state;
    }

    /**
     * 设置 状态
     * @param state
     *          状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * 获取 订单号
     * @return 订单号
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 设置 订单号
     * @param orderNumber
     *          订单号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取 回调参数
     * @return 回调参数
     */
    @Lob
    public String getParameters() {
        return parameters;
    }

    /**
     * 设置 回调参数
     * @param parameters
     *          回调参数
     */
    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    /**
     * 获取 支付产品
     * @return 支付产品
     */
    public String getPayProduct() {
        return payProduct;
    }

    /**
     * 设置 支付产品
     * @param payProduct
     *          支付产品
     */
    public void setPayProduct(String payProduct) {
        this.payProduct = payProduct;
    }
}
