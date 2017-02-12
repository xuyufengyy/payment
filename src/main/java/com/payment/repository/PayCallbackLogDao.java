/**
 * @(#)PayCallbackLogDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.repository;

import com.payment.domain.PayCallbackLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dto - 支付回调日志
 *
 * @author	xuminghua 2016/5/13
 * @version	1.0 
 */
public interface PayCallbackLogDao extends JpaRepository<PayCallbackLog, Long> {

    /**
     * 查询支付回调日志
     * @param orderNumber 订单号
     * @param state 删除标志
     * @return PayLog
     */
    PayCallbackLog findByOrderNumberAndState(String orderNumber, Integer state);
}
