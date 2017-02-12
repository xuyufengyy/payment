/**
 * @(#)PaymentService.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.route;

import com.google.gson.Gson;
import com.payment.domain.paybean.*;
import com.payment.service.VenderPayService;
import com.payment.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Hashtable;
import java.util.Map;

/**
 * Service - 统一支付
 *
 * @author	xuminghua 2016/5/12
 * @version	1.0
 */
@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private VenderPayManage venderPayManage;

    @Autowired
    private LogService logService;

    private Gson gson = new Gson();

    /**
     * 生成支付地址
     * @param payChannel        支付渠道标识
     * @param payProduct        支付产品标识
     * @param orderNumber       订单号
     * @param amount            金额（单位:分）
     * @param content           付款内容
     * @param payCallbackUrl       支付回调地址
     * @param map               可选参数
     * @return 生成支付订单结果
     */
    public GeneratePayUrl generatePayUrl(String payChannel, String payProduct, String orderNumber, Long amount, String content, String payCallbackUrl, Map<String, String> map) {

        logService.payLog(payChannel, payProduct, orderNumber, amount, content, payCallbackUrl, gson.toJson(map));

        GeneratePayUrl generatePayUrl = new GeneratePayUrl();
        VenderGeneratePayUrl venderGeneratePayUrl = new VenderGeneratePayUrl();

        String payId = payChannel + payProduct;
        if (venderPayManage.hasVenderPayService(payId)) {
            VenderPayService payService = venderPayManage.getVenderPayService(payId);

            //增加系统参数
            Map<String, String> parameter;
            if (map != null) {
                parameter = map;
            } else {
                parameter = new Hashtable<>();
            }
            venderGeneratePayUrl = payService.generatePayUrl(orderNumber, amount, content, parameter);

            generatePayUrl.setSuccess(venderGeneratePayUrl.isSuccess());
            generatePayUrl.setErrorCode(venderGeneratePayUrl.getErrorCode());
            generatePayUrl.setErrorText(venderGeneratePayUrl.getErrorText());
            generatePayUrl.setPayChannel(venderGeneratePayUrl.getPayChannel());
            generatePayUrl.setPayProduct(venderGeneratePayUrl.getPayProduct());
            generatePayUrl.getParameters().putAll(venderGeneratePayUrl.getParameters());

            logService.payResultLog(payChannel, payProduct, orderNumber, amount, content, gson.toJson(venderGeneratePayUrl.getParameters()),
                    generatePayUrl.isSuccess() ? 1 : 0, generatePayUrl.getErrorCode(), generatePayUrl.getErrorText(), 0);
        }else {
            generatePayUrl.setSuccess(false);
            generatePayUrl.setErrorCode("666");
            generatePayUrl.setErrorText("无效的支付接口标识");
        }
        return generatePayUrl;
    }

    /**
     * 订单状态查询
     * @param payChannel        支付渠道标识
     * @param payProduct        支付产品标识
     * @param payOrderSn       订单号
     * @return 订单状态
     */
    public QueryOrderStatus queryOrderStatus(String payChannel, String payProduct, String payOrderSn) {

        QueryOrderStatus queryOrderStatus = new QueryOrderStatus();

        String payId = payChannel + payProduct;
        if (venderPayManage.hasVenderPayService(payId)) {
            VenderPayService payService = venderPayManage.getVenderPayService(payId);
            VenderQueryOrderStatus venderQueryOrderStatus = payService.queryOrderStatus(payOrderSn);

            queryOrderStatus.setSuccess(venderQueryOrderStatus.isSuccess());
            queryOrderStatus.setErrorCode(venderQueryOrderStatus.getErrorCode());
            queryOrderStatus.setErrorText(venderQueryOrderStatus.getErrorText());
            queryOrderStatus.setPayStatus(venderQueryOrderStatus.getPayStatus());
        }else {
            queryOrderStatus.setSuccess(false);
            queryOrderStatus.setErrorCode("666");
            queryOrderStatus.setErrorText("无效的支付接口标识");
        }
        return queryOrderStatus;
    }

    /**
     * 支付回调
     * @param payChannel 支付渠道
     * @param payProduct 支付产品
     * @param content   付款内容
     * @param map 回调返回参数集合
     * @return
     */
    public PayCallback payCallback(String payChannel, String payProduct, String content, Map<String, Object> map) {

        logService.payCallbackLog(payProduct, map.get("order_id").toString(), gson.toJson(map));
        map.remove("payChannel");
        map.remove("payProduct");

        PayCallback payCallback = new PayCallback();

        String payId = payChannel + payProduct;
        if (venderPayManage.hasCallbackUrl(payId)) {
            VenderPayService payService = venderPayManage.getVenderPayServiceByCallbackUrl(payId);
            payCallback = payService.handleCallback(map);
            long amount = 0L;
            try{
                amount = Long.valueOf(payCallback.getParameters().get("amount").toString());
            }catch (Exception ex){
                ex.printStackTrace();
            }

            logService.payResultLog(payChannel, payProduct, payCallback.getOrderNumber(), amount, content, gson.toJson(payCallback.getParameters()),
                    payCallback.isSuccess() ? 1 : 0, payCallback.getErrorCode(), payCallback.getErrorText(), 1);
        }else {
            payCallback.setSuccess(false);
            payCallback.setErrorCode("666");
            payCallback.setErrorText("无效的回调接口标识");
            return payCallback;
        }
        return payCallback;
    }
}
