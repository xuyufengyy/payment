/**
 * @(#)PaySettingDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.repository;

import com.payment.domain.PaySetting;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dao - 支付设置
 *
 * @author Xu minghua on 2017/02/12
 * @version 1.0
 */
public interface PaySettingDao extends JpaRepository<PaySetting, Long> {

    /**
     * 查询系统设置
     * @param propertyKey 属性key
     * @param isActive 是否生效
     * @return PaySetting
     */
    PaySetting findByPropertyKeyAndIsActive(String propertyKey, Integer isActive);
}
