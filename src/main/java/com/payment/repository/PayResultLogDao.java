/**
 * @(#)PayResultLogDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.repository;

import com.payment.domain.PayResultLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao - 支付结果日志
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0 
 */
public interface PayResultLogDao extends JpaRepository<PayResultLog, Long> {

    /**
     * 查询支付结果日志
     * @param orderNumber 订单号
     * @param type 日志状态 0 支付 1 回调
     * @return PayResultLog
     */
    PayResultLog findByOrderNumberAndType(String orderNumber, Integer type);
}
