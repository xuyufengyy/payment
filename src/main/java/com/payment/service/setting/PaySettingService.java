/**
 * @(#)PaySettingService.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.service.setting;

import com.google.gson.Gson;
import com.payment.domain.PayLog;
import com.payment.domain.PaySetting;
import com.payment.repository.PayLogDao;
import com.payment.repository.PaySettingDao;
import com.payment.utils.Base64;
import com.payment.utils.DesUtils;
import com.payment.utils.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Service - 支付设置
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0 
 */
@Service
public class PaySettingService {

    private static Logger logger = LoggerFactory.getLogger(PaySettingService.class);

    @Autowired
    private PaySettingDao paySettingDao;

    @Autowired
    private PayLogDao payLogDao;

    /**
     * 查询系统设置
     * @param propertyKey 键
     * @param isActive 是否生效
     * @param state 删除标识
     * @return
     */
    @Transactional(readOnly = true)
    public Map<String, String> findByPropertyKeyAndIsActiveAndState(String propertyKey, Integer isActive, Integer state){
        PaySetting paySetting = paySettingDao.findByPropertyKeyAndIsActive(propertyKey, isActive);
        if(paySetting != null){
            Gson gson = new Gson();
            return gson.fromJson(paySetting.getPropertyValue(), Map.class);
        }
        return null;
    }

    /**
     * 加密--生成请求参数串
     * @param beforEncryptionText 待加密参数串
     * @return
     */
    @Transactional(readOnly = true)
    public String encryption(String beforEncryptionText){
        logger.info("beforEncryptionText:" + beforEncryptionText);
        Map<String, String> map = this.findByPropertyKeyAndIsActiveAndState("pay", 1, 0);
        String secretKey = map.get("secretKey");
        byte[] enk = DesUtils.hex(secretKey);
        byte[] encoded = DesUtils.encryptMode(enk, beforEncryptionText.getBytes());
        String encryptionText = Base64.encode(encoded);
        logger.info("String after encryption 加密:" + encryptionText);
        return encryptionText;
    }

    /**
     * 解密--生成请求参数串
     * @param encryptionText 加密参数串
     * @return
     */
    @Transactional(readOnly = true)
    public String decrypt(String encryptionText){
        logger.info("encryptionText:" + encryptionText);
        Map<String, String> map = this.findByPropertyKeyAndIsActiveAndState("pay", 1, 0);
        String secretKey = map.get("secretKey");
        byte[] enk = DesUtils.hex(secretKey);
        byte[] decryptBytes = Base64.decode(encryptionText);
        String decryptText = new String(DesUtils.decryptMode(enk, decryptBytes));
        logger.info("String after decryption 解密:" + decryptText);
        return decryptText;
    }

    /**
     * 加密--生成修改订单请求地址
     * @param orderNumber 订单号
     * @return
     */
    @Transactional(readOnly = true)
    public String payCallbackUrl(String orderNumber){
        PayLog payLog = payLogDao.findByOrderNumber(orderNumber);
        String payCallbackUrl = payLog.getPayCallbackUrl();
        Map<String, String> map = this.findByPropertyKeyAndIsActiveAndState("pay", 1, 0);
        String secretKey = map.get("secretKey");
        byte[] enk = DesUtils.hex(secretKey);
        byte[] encoded = DesUtils.encryptMode(enk, orderNumber.getBytes());
        String encryptionText = Base64.encode(encoded);
        logger.info("Encrypted string:" + encryptionText);
        try{
            return payCallbackUrl + "?encryptionText=" + URLEncoder.encode(encryptionText, Tool.getInputCharset());
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
