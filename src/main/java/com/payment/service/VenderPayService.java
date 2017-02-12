/**
 * @(#)PayService.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.service;

import com.payment.domain.paybean.PayCallback;
import com.payment.domain.paybean.VenderGeneratePayUrl;
import com.payment.domain.paybean.VenderPayServiceAnnounce;
import com.payment.domain.paybean.VenderQueryOrderStatus;
import java.util.Map;

/**
 * Service - 支付服务接口
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
public interface VenderPayService {

    /**
     * 厂家支付服务声明
     *
     * @return 厂家服务支付服务声明信息
     */
    VenderPayServiceAnnounce getAnnounce();

    /**
     * 生成支付地址
     * @param orderNumber       订单号
     * @param amount            金额（单位:分）
     * @param content           付款内容
     * @param optionalParameter 可选参数
     * @return 生成支付地址结果
     */
    VenderGeneratePayUrl generatePayUrl(String orderNumber, Long amount, String content, Map<String, String> optionalParameter);

    /**
     * 订单状态查询
     * @param orderNumber 订单号
     * @return 订单状态查询结果
     */
    VenderQueryOrderStatus queryOrderStatus(String orderNumber);

    /**
     * 回调数据解析
     * @param map http传入参数
     * @return 回调数据处理结果
     */
    PayCallback handleCallback(Map<String, Object> map);
}
