/**
 * @(#)PayCallbackLogDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.repository;

import com.payment.domain.PayCallbackLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao - 支付回调日志
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0 
 */
public interface PayCallbackLogDao extends JpaRepository<PayCallbackLog, Long> {

    /**
     * 查询支付回调日志
     * @param orderNumber 订单号
     * @return PayCallbackLog
     */
    PayCallbackLog findByOrderNumber(String orderNumber);
}
