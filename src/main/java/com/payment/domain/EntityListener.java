/**
 * @(#)EntityListener.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Listener - 创建日期、修改日期处理
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
public class EntityListener {

    /**
     * 保存前处理
     *
     * @param entity
     *            基类
     */
    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
    }

    /**
     * 更新前处理
     *
     * @param entity
     *            基类
     */
    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setModifyDate(new Date());
    }

}