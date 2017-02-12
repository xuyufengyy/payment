/**
 * @(#)PayResultLogDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.repository;

import com.payment.domain.PayResultLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dto - 支付结果日志
 *
 * @author	xuminghua 2016/7/26
 * @version	1.0 
 */
public interface PayResultLogDao extends JpaRepository<PayResultLog, Long> {

    /**
     * 查询支付结果日志
     * @param orderNumber 订单号
     * @param type 日志状态 0 支付 1 回调
     * @param state 删除标志
     * @return PayResultLog
     */
    PayResultLog findByOrderNumberAndTypeAndState(String orderNumber, Integer type, Integer state);
}
