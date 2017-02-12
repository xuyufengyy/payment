/**
 * @(#)LogService.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.service.log;

import com.payment.domain.PayCallbackLog;
import com.payment.domain.PayLog;
import com.payment.domain.PayResultLog;
import com.payment.repository.PayCallbackLogDao;
import com.payment.repository.PayLogDao;
import com.payment.repository.PayResultLogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

/**
 * Service - 支付日志
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Service
public class LogService {

    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    @Autowired
    private PayLogDao payLogDao;

    @Autowired
    private PayResultLogDao payResultLogDao;

    @Autowired
    private PayCallbackLogDao payCallbackLogDao;

    /**
     * 保存支付日志
     * @param payChannel            支付渠道
     * @param payProduct            支付产品
     * @param orderNumber           订单号
     * @param amount                订单金额
     * @param content               描述
     * @param payCallbackUrl        支付回调地址
     * @param parameter             参数集合
     */
    @Transactional
    public void payLog(String payChannel, String payProduct, String orderNumber, Long amount, String content, String payCallbackUrl, String parameter){
        PayLog payLog = payLogDao.findByOrderNumber(orderNumber);
        if (payLog == null) {
            save(payChannel, payProduct, orderNumber, amount, content, payCallbackUrl, parameter);
        }else{
            payLog.setModifyDate(new Date());
            logger.info(" update pay log success ! ");
        }
    }

    /**
     * 保存支付日志
     * @param payChannel            支付渠道
     * @param payProduct            支付产品
     * @param orderNumber           订单号
     * @param amount                订单金额
     * @param content               描述
     * @param payCallbackUrl        支付回调地址
     * @param parameter             参数集合
     */
    @Transactional
    public PayLog save(String payChannel, String payProduct, String orderNumber, Long amount, String content, String payCallbackUrl, String parameter){
        PayLog payLog = new PayLog();
        payLog.setPayChannel(payChannel);
        payLog.setPayProduct(payProduct);
        payLog.setOrderNumber(orderNumber);
        payLog.setPayAmount(amount);
        payLog.setPayNote(content);
        payLog.setPayCallbackUrl(payCallbackUrl);
        payLog.setPayParameter(parameter);
        payLogDao.save(payLog);
        logger.info(" save pay log success ! ");
        return payLog;
    }

    /**
     * 保存支付回调日志
     * @param payProduct            支付产品
     * @param orderNumber           订单号
     * @param parameter             参数集合
     */
    @Transactional
    public void payCallbackLog(String payProduct, String orderNumber, String parameter){
        PayCallbackLog payCallbackLog = payCallbackLogDao.findByOrderNumber(orderNumber);
        if (payCallbackLog == null) {
            savePayCallbackLog(payProduct, orderNumber, parameter);
        } else {
            payCallbackLog.setModifyDate(new Date());
            payCallbackLog.setPayProduct(payProduct);
            payCallbackLog.setOrderNumber(orderNumber);
            payCallbackLog.setParameters(parameter);
            logger.info(" update pay callback log success ! ");
        }
    }

    /**
     * 保存支付回调日志
     * @param payProduct            支付产品
     * @param orderNumber           订单号
     * @param parameter             参数集合
     */
    @Transactional
    public void savePayCallbackLog(String payProduct, String orderNumber, String parameter){
        PayCallbackLog payCallbackLog = new PayCallbackLog();
        payCallbackLog.setPayProduct(payProduct);
        payCallbackLog.setOrderNumber(orderNumber);
        payCallbackLog.setParameters(parameter);
        payCallbackLogDao.save(payCallbackLog);
        logger.info(" save pay callback log success ! ");
    }

    /**
     * 保存支付结果日志
     * @param payChannel            支付渠道
     * @param payProduct            支付产品
     * @param orderNumber           订单号
     * @param amount                订单金额
     * @param content               描述
     * @param parameter             参数集合
     * @param isSuccess             支付状态
     * @param errorCode             错误代码
     * @param errorDescription      错误描述
     * @param type                  日志状态
     */
    @Transactional
    public void payResultLog(String payChannel, String payProduct, String orderNumber, Long amount, String content, String parameter,
                                 Integer isSuccess, String errorCode, String errorDescription, Integer type){
        PayResultLog payResultLog = payResultLogDao.findByOrderNumber(orderNumber);
        if (payResultLog == null) {
            savePayResultLog(payChannel, payProduct, orderNumber, amount, content, parameter, isSuccess, errorCode, errorDescription, type);
        } else {
            payResultLog.setModifyDate(new Date());
            payResultLog.setPayChannel(payChannel);
            payResultLog.setPayProduct(payProduct);
            payResultLog.setOrderNumber(orderNumber);
            payResultLog.setPayAmount(amount);
            payResultLog.setPayNote(content);
            payResultLog.setPayParameter(parameter);
            payResultLog.setIsSuccess(isSuccess);
            payResultLog.setErrorCode(errorCode);
            payResultLog.setErrorDescription(errorDescription);
            logger.info(" update pay result log success ! ");
        }
    }

    /**
     * 保存支付结果日志
     * @param payChannel            支付渠道
     * @param payProduct            支付产品
     * @param orderNumber           订单号
     * @param amount                订单金额
     * @param content               描述
     * @param parameter             参数集合
     * @param isSuccess             支付状态
     * @param errorCode             错误代码
     * @param errorDescription      错误描述
     * @param type                  日志状态
     */
    @Transactional
    public void savePayResultLog(String payChannel, String payProduct, String orderNumber, Long amount, String content, String parameter,
                                 Integer isSuccess, String errorCode, String errorDescription, Integer type){
        PayResultLog payResultLog = new PayResultLog();
        payResultLog.setPayChannel(payChannel);
        payResultLog.setPayProduct(payProduct);
        payResultLog.setOrderNumber(orderNumber);
        payResultLog.setPayAmount(amount);
        payResultLog.setPayNote(content);
        payResultLog.setPayParameter(parameter);
        payResultLog.setIsSuccess(isSuccess);
        payResultLog.setErrorCode(errorCode);
        payResultLog.setErrorDescription(errorDescription);
        payResultLogDao.save(payResultLog);
        logger.info(" save pay result log success ! ");
    }
}
