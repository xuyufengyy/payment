/**
 * @(#)VenderPayManageListener.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.listener;

import com.payment.domain.paybean.VenderPayManage;
import com.payment.service.VenderPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;

/**
 *
 *
 * @author	xuminghua 2016/7/21
 * @version	1.0
 */
public class VenderPayManageListener implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(VenderPayManageListener.class);

    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            VenderPayManage venderPayManage = event.getApplicationContext().getBean(VenderPayManage.class);
            VenderPayService venderPayService = event.getApplicationContext().getBean(VenderPayService.class);
            venderPayManage.bindVenderPay(venderPayService, new HashMap());
        }
    }
}
