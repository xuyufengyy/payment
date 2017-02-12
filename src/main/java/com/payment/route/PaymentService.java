/**
 * @(#)PaymentService.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.route;

import com.google.gson.Gson;
import com.payment.domain.paybean.*;
import com.payment.service.VenderPayService;
import com.payment.service.log.LogService;
import com.payment.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Service - 统一支付
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private VenderPayManage venderPayManage;

    @Value("${pay.flag}")
    private boolean payFlag;

    @Autowired
    private LogService logService;

    private Gson gson = new Gson();

    /**
     * 生成支付地址
     * @param payParameter     支付入参
     * @return 生成支付订单结果
     */
    public GeneratePayUrl generatePayUrl(PayParameter payParameter) {

        String payChannel = payParameter.getPayChannel();
        String payProduct = payParameter.getPayProduct();
        String orderNumber = payParameter.getOrderNumber();
        String amount = payParameter.getAmount();
        String content = payParameter.getContent();
        String payCallbackUrl = payParameter.getPayCallbackUrl();
        Map<String, Object> parameters = payParameter.getParameters();

        //将元转成分
        BigDecimal actualAmount = new BigDecimal(amount);
        Double totalAmountDouble = actualAmount.doubleValue()*100;
        long totalAmount = 1L;
        logger.info("payFlag============="+payFlag);
        //当payFlag为false时为1分钱
        if(payFlag){
            totalAmount = totalAmountDouble.longValue();//订单实付款
        }

        try{
            //将内容与回调地址decode
            content = URLDecoder.decode(content, Tool.getInputCharset());
            payCallbackUrl = URLDecoder.decode(payCallbackUrl, Tool.getInputCharset());
        }catch (Exception ex){
            ex.printStackTrace();
        }

        Map<String, String> map = new HashMap<String, String>();
        Object object = parameters.get("parameters");
        if(object != null && !"".equals(object) && !"null".equals(object)){
            map = (Map) object;
        }

        logService.payLog(payChannel, payProduct, orderNumber, totalAmount, content, payCallbackUrl, gson.toJson(map));

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
            venderGeneratePayUrl = payService.generatePayUrl(orderNumber, totalAmount, content, parameter);

            generatePayUrl.setSuccess(venderGeneratePayUrl.isSuccess());
            generatePayUrl.setErrorCode(venderGeneratePayUrl.getErrorCode());
            generatePayUrl.setErrorText(venderGeneratePayUrl.getErrorText());
            generatePayUrl.setPayChannel(venderGeneratePayUrl.getPayChannel());
            generatePayUrl.setPayProduct(venderGeneratePayUrl.getPayProduct());
            generatePayUrl.getParameters().putAll(venderGeneratePayUrl.getParameters());

            logService.payResultLog(payChannel, payProduct, orderNumber, totalAmount, content, gson.toJson(venderGeneratePayUrl.getParameters()),
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
