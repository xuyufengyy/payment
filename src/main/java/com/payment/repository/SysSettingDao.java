/**
 * @(#)SysSettingDao.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.repository;

import java.util.Map;

/**
 * Dao - 系统设置
 *
 * @author xuminghua on 2016/11/28.
 * @version 1.0
 */
public interface SysSettingDao {

    /**
     * 查询系统设置
     * @param propertyKey 键
     * @param isActive 是否生效
     * @param state 删除标识
     * @return
     */
    Map<String, Object> findByPropertyKeyAndIsActiveAndState(String propertyKey, Integer isActive, Integer state);
}
