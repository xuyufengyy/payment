/**
 * @(#)PayLogDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.repository;

import com.payment.domain.PayLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dto - 支付日志
 *
 * @author	xuminghua 2016/5/13
 * @version	1.0 
 */
public interface PayLogDao extends JpaRepository<PayLog, Long>{

    /**
     * 查询支付日志
     * @param orderNumber 订单号
     * @param state 删除标志
     * @return PayLog
     */
    PayLog findByOrderNumberAndState(String orderNumber, Integer state);
}
