/**
 * @(#)UmpayService.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.service.wx;

import com.payment.domain.paybean.*;
import com.payment.service.VenderPayService;
import com.payment.utils.DateUtils;
import com.payment.utils.SSLClient;
import com.payment.utils.Tool;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service - 微信app支付
 *
 * @author	Xu minghua 2017/02/14
 * @version	1.0
 */
@Service
public class WxpayAppService implements VenderPayService {

    private static final Logger logger = LoggerFactory.getLogger(WxpayAppService.class);

    @Value("${wxpay.app.app.id}")
    private String wxpayAppAppId;

    @Value("${wxpay.app.partner.id}")
    private String wxpayAppPartnerId;

    @Value("${wxpay.app.secret.key}")
    private String wxpayAppSecretKey;

    @Override
    public VenderPayServiceAnnounce getAnnounce() {
        VenderPayServiceAnnounce venderPayServiceAnnounce = new VenderPayServiceAnnounce();
        venderPayServiceAnnounce.setPayId("0102");//接口标识，要区分谁调用
        venderPayServiceAnnounce.setHasCallback(true);//必须回调
        venderPayServiceAnnounce.setCallbackUrl("0102");

        return venderPayServiceAnnounce;
    }

    @Override
    public VenderGeneratePayUrl generatePayUrl(String orderNumber, Long amount, String content, Map<String, String> optionalParameter) {
        logger.info("*************************VenderGeneratePayUrl*******************************");
        VenderGeneratePayUrl venderGeneratePayUrl = new VenderGeneratePayUrl();
        Map<String, String> params = ListOrderedMap.decorate(new HashMap());

        try {
            venderGeneratePayUrl.setPayChannel("01");
            venderGeneratePayUrl.setPayProduct("02");

            WxpayApp wxpayApp = new WxpayApp();

            /** 协议参数 */
            wxpayApp.setAppid(wxpayAppAppId);
            wxpayApp.setPartnerid(wxpayAppPartnerId);
            wxpayApp.setPrepayid(optionalParameter.get("prepay_id"));
            wxpayApp.setPackage("Sign=WXPay");
            wxpayApp.setNoncestr(UUID.randomUUID().toString().replace("-", ""));
            wxpayApp.setTimestamp(String.valueOf(System.currentTimeMillis()));

            params = Tool.transBean2Map(wxpayApp);
            String signTemp = Tool.createLinkString(params) + "key=" + wxpayAppSecretKey;

            String sign = DigestUtils.md5Hex(signTemp.getBytes(Tool.getInputCharset())).toUpperCase();
            logger.info("**************sign**************：" + sign);

            venderGeneratePayUrl.getParameters().put("sign", sign);
            venderGeneratePayUrl.setSuccess(true);
        } catch (Exception e) {
            logger.error("===========VenderGeneratePayUrl Exception !============", e);
            venderGeneratePayUrl.setSuccess(false);
            e.printStackTrace();
        }
        return venderGeneratePayUrl;
    }

    @Override
    public VenderQueryOrderStatus queryOrderStatus(String orderNumber) {
        logger.info("*************************VenderQueryOrderStatus*******************************");
        VenderQueryOrderStatus venderQueryOrderStatus = new VenderQueryOrderStatus();
        Map<String, String> params = ListOrderedMap.decorate(new HashMap());

        HttpClient httpClient = null;
        HttpGet httpget = null;
        HttpResponse response = null;

        try {
            UmpayWap umpayWap = new UmpayWap();

            /** 协议参数 */
            umpayWap.setService("query_order");
            umpayWap.setCharset(Tool.getInputCharset());
//            umpayWap.setMer_id(umpayWapMerId);
            umpayWap.setVersion("4.0");

            /** 业务参数 */
            umpayWap.setOrder_id(orderNumber);
            umpayWap.setMer_date(DateUtils.toString(new Date(), "yyyyMMdd"));

            params = Tool.transBean2Map(umpayWap);
            ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(params);
            String sign = reqDataPost.getSign(); //显示签名
            String post_url = reqDataPost.getUrl();   //post请求地址
            System.out.println("----------post请求方式："+post_url);

//            umpayWap.setSign_type(umpayWapSignType);
            params = Tool.transBean2Map(umpayWap);
            String signContent = Tool.createLinkString(params);//数组排序后生成字符串

            sign = URLEncoder.encode(sign, Tool.getInputCharset());

            String parameter = signContent + "&sign=" + sign;
            logger.info("URLEncoder=========================" + parameter);

            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();//设置请求和传输超时时间

            httpget = new HttpGet(post_url + "?" + parameter);
            httpget.setConfig(requestConfig);
            httpClient = SSLClient.getInstance();
            response = httpClient.execute(httpget);

            if (response.getStatusLine().getStatusCode() == 200) {
                String html = EntityUtils.toString(response.getEntity());
                Document doc = Jsoup.parse(html);
                Element content = doc.head();
                Elements links = content.getElementsByTag("META");
                for (Element link : links) {
                    String linkText = link.attr("CONTENT");
                    String[] array = linkText.split("&");
                    Map<String, String> map = new HashMap<String, String>();
                    for(String str:array){
                        String[] ms = str.split("=");
                        if(ms.length == 2){
                            map.put(ms[0], ms[1]);
                        }
                    }
                    logger.info("map==============" + map.toString());
                    String ret_code = map.get("ret_code").toString();
                    String trade_state = map.get("trade_state").toString();
                    logger.info("ret_code==============" + ret_code);
                    logger.info("trade_state==============" + trade_state);

                    if(ret_code.equals("0000")){
                        if(trade_state.equals("TRADE_SUCCESS")){
                            venderQueryOrderStatus.setPayStatus(1);
                            venderQueryOrderStatus.setErrorText("查询成功");
                            venderQueryOrderStatus.setSuccess(true);
                        }else{
                            venderQueryOrderStatus.setPayStatus(0);
                            venderQueryOrderStatus.setErrorCode(trade_state);
                            venderQueryOrderStatus.setErrorText(trade_state);
                            venderQueryOrderStatus.setSuccess(false);
                        }
                    } else{
                        venderQueryOrderStatus.setPayStatus(0);
                        venderQueryOrderStatus.setErrorCode(ret_code);
                        venderQueryOrderStatus.setErrorText(ret_code);
                        venderQueryOrderStatus.setSuccess(false);
                    }
                }
            }else {
                venderQueryOrderStatus.setSuccess(false);
            }
        } catch (Exception e) {
            venderQueryOrderStatus.setSuccess(false);
            venderQueryOrderStatus.setErrorText(e.getMessage());
            logger.error("===========VenderQueryOrderStatus Exception !===========", e);
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response = null;
                }
                if (httpget != null) {
                    httpget.abort();
                    httpget = null;
                }
            } catch (Exception e) {
                logger.error("=====******======VenderQueryOrderStatus Exception !=======******====", e.getMessage());
                e.printStackTrace();
            }
        }
        return venderQueryOrderStatus;
    }

    //回调
    @Override
    public PayCallback handleCallback(Map<String, Object> parameters) {
        logger.info("*************************PayCallback*******************************");
        PayCallback payCallback = new PayCallback();

        Map<String, String> params1 = Tool.getParameterMap(parameters);

        if (params1 != null) {
            Map reqData = new HashMap();
            Map resData = new HashMap();
            String sign_msg = "";

            //                for (Map.Entry<String, String> entry : params1.entrySet()) {
            //                    logger.info("key= " + entry.getKey() + " and value= " + entry.getValue());
            //                }
            try {
                reqData = Plat2Mer_v40.getPlatNotifyData(params1);
                logger.info("reqData======================="+reqData.toString());
                if (!reqData.isEmpty()) {
                    resData.put("ret_code", "0000");
                    sign_msg = "平台通知数据验签成功";
                }
            } catch (Exception e) {
                resData.put("ret_code", "1111");
                sign_msg = ("验证签名发生异常: " + e);
                logger.error("===========handleCallback Exception !=================", e);
                e.printStackTrace();
            }
            payCallback.setOrderNumber((String) reqData.get("order_id"));
            payCallback.getParameters().putAll(reqData);
            if ("0000".equals(resData.get("ret_code"))) {
//                Set set = reqData.keySet();
//                Iterator iter = set.iterator();
//                while (iter.hasNext()) {
//                    String key = (String) iter.next();
//                    String value = (String) reqData.get(key);
//                    if ("sign".equals(key)) {
//
//                    }
//                }

                resData.put("mer_id", reqData.get("mer_id"));
                resData.put("sign_type", reqData.get("sign_type"));
                resData.put("version", reqData.get("version"));
                resData.put("ret_msg", "success");
                String data = Mer2Plat_v40.merNotifyResData(resData);
                String str = "<META NAME='MobilePayPlatform' CONTENT="+ data +" />";

                String trade_state = reqData.get("trade_state").toString();
                logger.info("trade_state==============" + trade_state);
                if(trade_state.equals("TRADE_SUCCESS")){
                    payCallback.setPayStatus(1);
                    payCallback.setSuccess(true);
                    logger.info("======================Callback Success !==============================");
                }else {
                    payCallback.setPayStatus(0);
                    payCallback.setSuccess(false);
                    logger.info("======================Callback Fail !==============================");
                }
            } else {
                payCallback.setPayStatus(0);
                payCallback.setSuccess(false);
                logger.info("======================Callback Fail !==============================");
            }
        }
        return payCallback;
    }
}