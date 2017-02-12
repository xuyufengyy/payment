/**
 * @(#)PayCallbackResultLogDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.repository;

import com.payment.domain.PayCallbackResultLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao - 支付回调结果日志
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0 
 */
public interface PayCallbackResultLogDao extends JpaRepository<PayCallbackResultLog, Long> {

    /**
     * 查询支付结果日志
     * @param orderNumber 订单号
     * @return PayCallbackResultLog
     */
    PayCallbackResultLog findByOrderNumber(String orderNumber);
}
