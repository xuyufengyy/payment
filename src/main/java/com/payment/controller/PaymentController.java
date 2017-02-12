/**
 * @(#)PaymentController.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.controller;

import com.payment.domain.paybean.GeneratePayUrl;
import com.payment.domain.paybean.PayCallback;
import com.payment.domain.paybean.PayParameter;
import com.payment.domain.paybean.QueryOrderStatus;
import com.payment.route.PaymentService;
import com.payment.service.setting.PaySettingService;
import com.payment.utils.Tool;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller - 订单支付
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Controller
public class PaymentController {

    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${umpay.wap.pay.url.name}")
    private String umpayWapPayUrl;

    @Autowired
    private PaySettingService paySettingService;

    @Autowired
    private PaymentService paymentService;

    /**
     * 支付--生成支付地址
     * @return
     */
    @RequestMapping(value = "/payment/generate_pay_url", method= RequestMethod.GET)
    public String generatePayUrl(HttpServletRequest request, Model model){

        //获取参数
        String urlParamter = request.getQueryString();
        logger.info("String before encryption:" + urlParamter);
        if(StringUtils.isBlank(urlParamter)){
            model.addAttribute("message", "加密参数不存在");
            return "payment/pay_error";
        }

        //将字符串转成map
        Map<String, Object> paramters = Tool.stringConvertMap(urlParamter, Tool.getInputCharset());
        String encryptionText = paramters.get("encryptionText").toString();

        //解密--生成请求参数串
        String decryptText = paySettingService.decrypt(encryptionText);

        Map<String, Object> map = Tool.stringConvertMap(decryptText, null);
        logger.info("map===============" + map.toString());

        PayParameter payParameter = new PayParameter();
        try{
            BeanUtils.populate(payParameter, map);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        GeneratePayUrl generatePayUrl = paymentService.generatePayUrl(payParameter);

        String url = "";
        if(generatePayUrl.isSuccess()){
            url = generatePayUrl.getParameters().get(umpayWapPayUrl).toString();
        }else{
            model.addAttribute("message", generatePayUrl.getErrorText());
            return "payment/pay_error";
        }
        return "redirect:"+url;
    }

    /**
     * 支付--异步回调
     * @return
     */
    @RequestMapping(value = "/v1/payment/notify_callback", method= RequestMethod.GET)
    public String notifyCallback(HttpServletRequest request, Model model){
        String urlParamter = request.getQueryString();
        logger.info("notify_callback============urlParamter========="+urlParamter);
        if(StringUtils.isBlank(urlParamter)){
            model.addAttribute("message", "参数不存在");
            return "payment/pay_error";
        }

        Map<String, Object> map = Tool.stringConvertMap(urlParamter, Tool.getInputCharset());
        String payChannel = map.get("payChannel").toString();
        String payProduct = map.get("payProduct").toString();
        String payOrderSn = map.get("order_id").toString();

        PayCallback payCallback = paymentService.payCallback(payChannel, payProduct, "订单支付回调", map);

        if(payCallback.isSuccess()){
            String updateOrderUrl = paySettingService.payCallbackUrl(payOrderSn);
            return "redirect:" + updateOrderUrl;
        }else{
            //回调失败后，调用查询接口
            QueryOrderStatus queryOrderStatus = paymentService.queryOrderStatus(payChannel, payProduct, payOrderSn);
            if(queryOrderStatus.isSuccess()){
                String updateOrderUrl = paySettingService.payCallbackUrl(payOrderSn);
                return "redirect:" + updateOrderUrl;
            }
        }
        return "payment/callback_result";
    }

    /**
     * 支付--同步回调
     * @return
     */
    @RequestMapping(value = "/v1/payment/ret_callback", method= RequestMethod.POST)
    public String retCallback(HttpServletRequest request, Model model){
        String payOrderSn = request.getParameter("order_id");
        logger.info("ret_callback_payOrderSn======"+payOrderSn);
        String flag = payOrderSn.substring(3,4);
        model.addAttribute("flag", flag);
        return "payment/pay_success";
    }
}
