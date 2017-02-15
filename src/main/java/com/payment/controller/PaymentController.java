/**
 * @(#)PaymentController.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.controller;

import com.payment.exception.BaseException;
import com.payment.domain.paybean.GeneratePayUrl;
import com.payment.domain.paybean.PayCallback;
import com.payment.domain.paybean.PayParameter;
import com.payment.domain.paybean.QueryOrderStatus;
import com.payment.route.PaymentService;
import com.payment.service.common.CommonService;
import com.payment.service.log.LogService;
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
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Controller - 订单支付
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${umpay.wap.pay.url.name}")
    private String umpayWapPayUrl;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private LogService logService;

    /**
     * 支付--组装支付请求报文
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/generate_pay", method= RequestMethod.GET)
    public GeneratePayUrl generatePayUrl(HttpServletRequest request){

        //获取参数
        String encryptionText = request.getQueryString();
        logger.info("String before encryption:" + encryptionText);
        if(StringUtils.isBlank(encryptionText)){
            throw new BaseException(" Request parameter does not exist ");
        }

        //解密--生成请求参数串
        String decryptText = commonService.decrypt(encryptionText);

        Map<String, Object> map = Tool.stringConvertMap(decryptText, null);
        logger.info("map===============" + map.toString());

        PayParameter payParameter = new PayParameter();
        try{
            BeanUtils.populate(payParameter, map);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        GeneratePayUrl generatePayUrl = paymentService.generatePayUrl(payParameter);
        return generatePayUrl;
    }

    /**
     * 支付--异步回调
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/notify_callback", method= RequestMethod.GET)
    public String notifyCallback(HttpServletRequest request){
        String urlParamter = request.getQueryString();
        logger.info("notify_callback============urlParamter========="+urlParamter);
        if(StringUtils.isBlank(urlParamter)){
            throw new BaseException(" Request parameter does not exist ");
        }

        Map<String, Object> map = Tool.stringConvertMap(urlParamter, Tool.getInputCharset());
        String payChannel = map.get("payChannel").toString();
        String payProduct = map.get("payProduct").toString();
        String payOrderSn = map.get("order_id").toString();

        //查询订单号是否支付成功,如果支付成功就不需要回调(异步回调多次)
        boolean isSuccess = logService.findPayCallbackResultLog(payOrderSn);
        if(!isSuccess){
            PayCallback payCallback = paymentService.payCallback(payChannel, payProduct, "订单支付回调", map);

            if(payCallback.isSuccess()){
                String updateOrderUrl = commonService.payCallbackUrl(payOrderSn);
                return "redirect:" + updateOrderUrl;
            }else{
                //回调失败后，调用查询接口
                QueryOrderStatus queryOrderStatus = paymentService.queryOrderStatus(payChannel, payProduct, payOrderSn);
                if(queryOrderStatus.isSuccess()){
                    String updateOrderUrl = commonService.payCallbackUrl(payOrderSn);
                    return "redirect:" + updateOrderUrl;
                }
            }
        }
        return null;
    }

    /**
     * 支付--同步回调
     * @return
     */
    @RequestMapping(value = "/ret_callback", method= RequestMethod.POST)
    public String retCallback(HttpServletRequest request, Model model){
        String payOrderSn = request.getParameter("order_id");
        logger.info("ret_callback_payOrderSn======"+payOrderSn);
        String flag = payOrderSn.substring(3,4);
        model.addAttribute("flag", flag);
        return "payment/pay_success";
    }
}
