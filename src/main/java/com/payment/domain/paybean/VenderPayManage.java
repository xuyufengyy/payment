/**
 * @(#)VenderPayManage.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain.paybean;

import com.payment.service.VenderPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 绑定,解绑service
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Component
public class VenderPayManage {

    private static final Logger logger = LoggerFactory.getLogger(VenderPayManage.class);

    private final Map<String, VenderPayService> payServiceMap = new ConcurrentHashMap<>();
    private final Map<String, VenderPayService> callbackUrlMap = new ConcurrentHashMap<>();

    public boolean hasVenderPayService(String payId) {
        return payServiceMap.containsKey(payId);
    }

    public VenderPayService getVenderPayService(String payId) {
        return payServiceMap.get(payId);
    }

    public boolean hasCallbackUrl(String CallbackUrl) {
        return callbackUrlMap.containsKey(CallbackUrl);
    }

    public VenderPayService getVenderPayServiceByCallbackUrl(String CallbackUrl) {
        return callbackUrlMap.get(CallbackUrl);
    }

    public void bindVenderPay(VenderPayService payService, Map attrs) {
        VenderPayServiceAnnounce announce = Objects.requireNonNull(payService.getAnnounce(), "found null announce");
        logger.debug("Bind VenderPayService, payid = {}, ", announce.getPayId());
        payServiceMap.put(announce.getPayId(), payService);
        if (announce.isHasCallback()) {
            callbackUrlMap.put(announce.getCallbackUrl(), payService);
        }
        logger.debug("Bind VenderPayService, payService size = {}, callbackUrl size = {}", payServiceMap.size(), callbackUrlMap.size());
    }

    public void unbindVenderPay(VenderPayService payService) {
        if (payService != null) {
            VenderPayServiceAnnounce announce = payService.getAnnounce();
            if (announce != null) {
                logger.debug("Unbind VenderPayService, payid = {}", announce.getPayId());
                if (announce.isHasCallback()) {
                    callbackUrlMap.remove(announce.getCallbackUrl());
                }
                payServiceMap.remove(announce.getPayId());
            }
            logger.debug("Unbind VenderPayService,payService size = {}, callbackUrl size = {}", payServiceMap.size(), callbackUrlMap.size());
        }
    }
}
