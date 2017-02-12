/**
 * @(#)SysSettingDaoImpl.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.repository.impl;

import com.payment.repository.SysSettingDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DaoImpl - 系统设置
 *
 * @author xuminghua on 2016/11/28.
 * @version 1.0
 */
@Repository
public class SysSettingDaoImpl implements SysSettingDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 查询系统设置
     * @param propertyKey 键
     * @param isActive 是否生效
     * @param state 删除标识
     * @return
     */
    public Map<String, Object> findByPropertyKeyAndIsActiveAndState(String propertyKey, Integer isActive, Integer state){
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT a.property_value AS propertyValue FROM ex_sys_setting AS a WHERE 1=1 ");
        List<Object> list = new ArrayList<Object>();
        if(StringUtils.isNotBlank(propertyKey)){
            sql.append(" AND a.property_key = ? ");
            list.add(propertyKey);
        }
        if(isActive != null){
            sql.append(" AND a.is_active = ? ");
            list.add(isActive);
        }
        if(state != null){
            sql.append(" AND a.state = ? ");
            list.add(state);
        }
        return jdbcTemplate.queryForMap(sql.toString(), list.toArray());
    }
}
