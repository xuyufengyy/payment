/**
 * @(#)TestController.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.controller;

import com.payment.domain.paybean.PayParameter;
import com.payment.domain.paybean.QueryOrderStatus;
import com.payment.route.PaymentService;
import com.payment.service.common.CommonService;
import com.payment.utils.Tool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Controller - 测试
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Controller
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/test/index", method= RequestMethod.GET)
    public String pay(){
        return "test/index";
    }

    /**
     * 支付--将支付参数加密
     * @return
     */
    @RequestMapping(value = "/test/encryption", method= RequestMethod.POST)
    public String encryption(PayParameter payParameter, Model model){

        Map<String, String> map = Tool.transBean2Map(payParameter);
        String urlParamter = Tool.createLinkString(map, "&");//数组排序后生成字符串
        logger.info("String before encryption:" + urlParamter);
        if(StringUtils.isBlank(urlParamter)){
            model.addAttribute("message", "加密参数不存在");
            return "payment/pay_error";
        }

        //加密
        String encryptionText = commonService.encryption(urlParamter);
        logger.info("Encrypted string:" + encryptionText);

        model.addAttribute("encryptionText", encryptionText);
        return "test/pay";
    }

    @RequestMapping(value = "/test/query", method= RequestMethod.GET)
    public String query(){
        return "test/query";
    }

    /**
     * 支付--订单状态查询
     * @return
     */
    @RequestMapping(value = "/test/query_order_status", method= RequestMethod.POST)
    public String queryOrder(String payChannel, String payProduct, String orderNumber, Model model){


        QueryOrderStatus queryOrderStatus = paymentService.queryOrderStatus(payChannel, payProduct, orderNumber);
        String message = "";
        if(queryOrderStatus.isSuccess()){
            message = "订单支付成功！";
        }else{
            message = "订单支付失败！";
            model.addAttribute("errorCode", queryOrderStatus.getErrorCode());
        }
        model.addAttribute("message", message);
        return "test/query_result";
    }
}
