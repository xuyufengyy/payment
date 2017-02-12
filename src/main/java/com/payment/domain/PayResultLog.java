/**
 * @(#)PayResultLog.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.domain;

import javax.persistence.*;

/**
 * Entity - 支付结果日志
 *
 * @author	xuminghua 2016/5/11
 * @version	1.0
 */
@Entity
@Table(name = "ex_pay_result_log")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "ex_pay_result_log_sequence")
public class PayResultLog extends BaseEntity{

    private static final long serialVersionUID = 1427491389928068090L;

    /** 状态 0 正常 1删除 */
    private Integer state;

    /** 支付渠道 01 ios客户端， 02 android客户端， 03 wap，04 web */
    private String payChannel;

    /** 支付产品 00 测试，01 支付宝，02 微支付，03 联动优势收银台 */
    private String payProduct;

    /** 订单号 */
    private String orderNumber;

    /** 支付金额（单位：分） */
    private Long payAmount;

    /** 支付备注 */
    private String payNote;

    /** 可选参数 json键值对 */
    private String payParameter;

    /** 支付状态 0 失败 1 成功 */
    private Integer isSuccess;

    /** 失败错误码 */
    private String errorCode;

    /** 失败错误描述 */
    private String errorDescription;

    /** 日志状态 0 支付 1 回调 */
    private Integer type;

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

    /**
     * 获取 支付状态
     * @return 支付状态
     */
    public Integer getIsSuccess() {
        return isSuccess;
    }

    /**
     * 设置 支付状态
     * @param isSuccess
     *          支付状态
     */
    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    /**
     * 获取 失败错误码
     * @return 失败错误码
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * 设置 失败错误码
     * @param errorCode
     *          失败错误码
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取 失败错误描述
     * @return 失败错误描述
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * 设置 失败错误描述
     * @param errorDescription
     *          失败错误描述
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * 获取 日志状态
     * @return 日志状态
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置 日志状态
     * @param type
     *          日志状态
     */
    public void setType(Integer type) {
        this.type = type;
    }
}
