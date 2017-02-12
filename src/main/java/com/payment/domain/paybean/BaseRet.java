/**
 * @(#)BaseRet.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.domain.paybean;

/**
 * Entity - 通用结果返回
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
public class BaseRet {

    /** 是否成功，0：失败，1：成功 */
    private boolean isSuccess;

    /** 错误代码 */
    private String errorCode;

    /** 错误描述 */
    private String errorText;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
}
