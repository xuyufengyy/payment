/**
 * @(#)PaySetting.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Entity - 支付设置
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Entity
@Table(name = "pay_setting")
@SequenceGenerator(name = "sequenceGenerator", sequenceName = "pay_setting_sequence")
public class PaySetting extends BaseEntity  {

    /** 属性key */
    private String propertyKey;

    /** 属性值 */
    private String propertyValue;

    /** 是否生效 0否1是 */
    private Integer isActive;

    /**
     * 获取 属性Key
     * @return 属性Key
     */
    public String getPropertyKey() {
        return propertyKey;
    }

    /**
     * 设置 属性Key
     * @param propertyKey
     *          属性Key
     */
    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    /**
     * 获取 属性值
     * @return 属性值
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * 设置 属性值
     * @param propertyValue
     *          属性值
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    /**
     * 获取 生效标示
     * @return 生效标示
     */
    public Integer getIsActive() {
        return isActive;
    }

    /**
     * 设置 生效标示
     * @param isActive
     *          生效标示
     */
    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}

