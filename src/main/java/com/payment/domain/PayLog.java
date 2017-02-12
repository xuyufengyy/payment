/**
 * @(#)PayLog.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain;

import javax.persistence.*;

/**
 * Entity - 支付日志
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Entity
@Table(name = "pay_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pay_log_sequence")
public class PayLog extends BaseEntity{

    private static final long serialVersionUID = 1427491389928068090L;

    /** 支付渠道 01 ios客户端， 02 android客户端， 03 wap，04 web */
    private String payChannel;

    /** 支付产品 00 测试，01 支付宝，02 微支付，03 联动优势收银台 */
    private String payProduct;

    /** 订单号（商家订单号） */
    private String orderNumber;

    /** 支付金额（单位：分） */
    private Long payAmount;

    /** 支付回调地址 */
    private String payCallbackUrl;

    /** 支付备注 */
    private String payNote;

    /** 可选参数 json键值对 */
    private String payParameter;

    /**
     * 获取 支付渠道
     * @return 支付渠道
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * 设置 支付渠道
     * @param payChannel
     *          支付渠道
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
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
     * 获取 支付金额
     * @return 支付金额
     */
    public Long getPayAmount() {
        return payAmount;
    }

    /**
     * 设置 支付金额
     * @param payAmount
     *          支付金额
     */
    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    /**
     * 获取 支付回调地址
     * @return 支付回调地址
     */
    public String getPayCallbackUrl() {
        return payCallbackUrl;
    }

    /**
     * 设置 支付回调地址
     * @param payCallbackUrl
     *          支付回调地址
     */
    public void setPayCallbackUrl(String payCallbackUrl) {
        this.payCallbackUrl = payCallbackUrl;
    }

    /**
     * 获取 支付备注
     * @return 支付备注
     */
    public String getPayNote() {
        return payNote;
    }

    /**
     * 设置 支付备注
     * @param payNote
     *          支付备注
     */
    public void setPayNote(String payNote) {
        this.payNote = payNote;
    }

    /**
     * 获取 可选参数
     * @return 可选参数
     */
    @Lob
    public String getPayParameter() {
        return payParameter;
    }

    /**
     * 设置 可选参数
     * @param payParameter
     *          可选参数
     */
    public void setPayParameter(String payParameter) {
        this.payParameter = payParameter;
    }
}
