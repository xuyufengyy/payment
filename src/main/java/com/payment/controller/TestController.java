/**
 * @(#)TestController.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.controller;

import com.payment.domain.paybean.QueryOrderStatus;
import com.payment.route.PaymentService;
import com.payment.service.setting.SysSettingService;
import com.payment.utils.Base64;
import com.payment.utils.DesUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller - 测试
 *
 * @author	xuminghua 2016/7/22
 * @version	1.0
 */
@Controller
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SysSettingService sysSettingService;

//    @RequestMapping(value = "/v1/test/pay", method= RequestMethod.GET)
    public String pay(){
        return "test/encryption";
    }

    /**
     * 支付--将支付参数加密
     * @author xuminghua 2016/05/11
     * @return
     */
//    @RequestMapping(value = "/v1/test/encryption", method= RequestMethod.GET)
    public String encryption(HttpServletRequest request, Model model){

        Map<String, String> map2 = sysSettingService.findByPropertyKeyAndIsActiveAndState("pay", 1, 0);
        if(map2.isEmpty()){
            model.addAttribute("message", "加密key不存在");
            return "payment/pay_error";
        }

        String secretKey = map2.get("secretKey");
        byte[] enk = DesUtils.hex(secretKey);
        String urlParamter = request.getQueryString();
        logger.info("String before encryption:" + urlParamter);
        if(StringUtils.isBlank(urlParamter)){
            model.addAttribute("message", "加密参数不存在");
            return "payment/pay_error";
        }
        byte[] encoded = DesUtils.encryptMode(enk, urlParamter.getBytes());
        String encryptionText = Base64.encode(encoded);
        logger.info("Encrypted string:" + encryptionText);

        model.addAttribute("encryptionText", encryptionText);
        return "test/pay";
    }

//    @RequestMapping(value = "/v1/test/query", method= RequestMethod.GET)
    public String query(){
        return "test/query";
    }

    /**
     * 支付--订单状态查询
     * @author xuminghua 2016/05/13
     * @return
     */
//    @RequestMapping(value = "/v1/test/query_order_status", method= RequestMethod.POST)
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
