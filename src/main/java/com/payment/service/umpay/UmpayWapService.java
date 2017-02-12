/**
 * @(#)UmpayService.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) Xu minghua 版权所有
 */
package com.payment.service.umpay;

import com.payment.domain.paybean.*;
import com.payment.service.VenderPayService;
import com.payment.utils.DateUtils;
import com.payment.utils.SSLClient;
import com.payment.utils.Tool;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;
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
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Service - 联动优势wap支付
 *
 * @author	Xu minghua 2017/02/12
 * @version	1.0
 */
@Service
public class UmpayWapService implements VenderPayService {

    private static final Logger logger = LoggerFactory.getLogger(UmpayWapService.class);

    @Value("${umpay.wap.mer.id}")
    private String umpayWapMerId;

    @Value("${umpay.wap.sign.type}")
    private String umpayWapSignType;

    @Value("${umpay.wap.notify.url}")
    private String umpayWapNotifyUrl;

    @Value("${umpay.wap.ret.url}")
    private String umpayWapRetUrl;

    @Value("${umpay.wap.pay.url.name}")
    private String umpayWapPayUrl;

    @Override
    public VenderPayServiceAnnounce getAnnounce() {
        // TODO Auto-generated method stub
        VenderPayServiceAnnounce venderPayServiceAnnounce = new VenderPayServiceAnnounce();
        venderPayServiceAnnounce.setPayId("0303");//接口标识，要区分谁调用
        venderPayServiceAnnounce.setHasCallback(true);//必须回调
        venderPayServiceAnnounce.setCallbackUrl("0303");

        return venderPayServiceAnnounce;
    }

    @Override
    public VenderGeneratePayUrl generatePayUrl(String orderNumber, Long amount, String content, Map<String, String> optionalParameter) {
        logger.info("*************************VenderGeneratePayUrl*******************************");
        VenderGeneratePayUrl venderGeneratePayUrl = new VenderGeneratePayUrl();
        Map<String, String> params = ListOrderedMap.decorate(new HashMap());

        try {
            venderGeneratePayUrl.setPayChannel("03");
            venderGeneratePayUrl.setPayProduct("03");

            UmpayWap umpayWap = new UmpayWap();

            /** 协议参数 */
            umpayWap.setService("pay_req_h5_frontpage");
            umpayWap.setCharset(Tool.getInputCharset());
            umpayWap.setMer_id(umpayWapMerId);
            umpayWap.setNotify_url(umpayWapNotifyUrl);
            umpayWap.setRet_url(umpayWapRetUrl);
            umpayWap.setVersion("4.0");

            /** 业务参数 */
            umpayWap.setOrder_id(orderNumber);
            umpayWap.setMer_date(DateUtils.toString(new Date(), "yyyyMMdd"));
            umpayWap.setAmount(amount.toString());//单位分
            umpayWap.setAmt_type("RMB");
            //可选参数
//            umpayWap.setGoodsId("");
//            umpayWap.setGoodsInf("");
//            umpayWap.setCardId("");
//            umpayWap.setIdentityType("");
//            umpayWap.setIdentityCode("");
//            umpayWap.setCardolder("");
//            umpayWap.setMerCustId("");
//            umpayWap.setUsrPayAgreementId("");

            params = Tool.transBean2Map(umpayWap);
            ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(params);
            String sign = reqDataPost.getSign(); //显示签名
            String post_url = reqDataPost.getUrl();   //post请求地址
            logger.info("----------post_url：" + post_url);

            umpayWap.setNotify_url(URLEncoder.encode(umpayWapNotifyUrl, Tool.getInputCharset()));
            umpayWap.setRet_url(URLEncoder.encode(umpayWapRetUrl, Tool.getInputCharset()));
            umpayWap.setSign_type(umpayWapSignType);
            params = Tool.transBean2Map(umpayWap);
            String signContent = Tool.createLinkString(params, "&");//数组排序后生成字符串

            sign = URLEncoder.encode(sign, Tool.getInputCharset());

            String parameter = signContent + "&sign=" + sign;
            logger.info("URLEncoder=========================" + parameter);

            venderGeneratePayUrl.getParameters().put(umpayWapPayUrl, post_url + "?" + parameter);
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
            umpayWap.setMer_id(umpayWapMerId);
            umpayWap.setVersion("4.0");

            /** 业务参数 */
            umpayWap.setOrder_id(orderNumber);
            umpayWap.setMer_date(DateUtils.toString(new Date(), "yyyyMMdd"));

            params = Tool.transBean2Map(umpayWap);
            ReqData reqDataPost = Mer2Plat_v40.makeReqDataByPost(params);
            String sign = reqDataPost.getSign(); //显示签名
            String post_url = reqDataPost.getUrl();   //post请求地址
            System.out.println("----------post请求方式："+post_url);

            umpayWap.setSign_type(umpayWapSignType);
            params = Tool.transBean2Map(umpayWap);
            String signContent = Tool.createLinkString(params, "&");//数组排序后生成字符串

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