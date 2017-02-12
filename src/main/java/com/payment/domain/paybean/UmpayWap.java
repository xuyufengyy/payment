/**
 * @(#)UmpayWap.java
 * Description:
 * Version :	1.0
 * Copyright:	Copyright (c) 苗方清颜 版权所有
 */
package com.payment.domain.paybean;

import java.io.Serializable;

/**
 * Entity - 联动优势wap
 *
 * @author	xuminghua 2016/5/13
 * @version	1.0
 */
public class UmpayWap implements Serializable {

    private static final long serialVersionUID = 2868653443686472433L;

    /** 协议参数 */
    private String service;//接口名称,变长32，pay_req _h5_frontpage。Y
    private String charset;//参数字符编码集，变长16，商户网站使用的编码格式，支持UTF-8、GBK、GB2312、GB18030。Y
    private String mer_id;//商户编号，变长8，由平台统一分配合作商户唯一标识。Y
    private String sign_type;//签名方式,定长8，暂只支持RSA必须大写。Y
    private String sign;//签名,变长256。Y
    private String notify_url;//服务器异步通知页面路径,变长128，交易完成后，联动优势支付平台会按照此地址将交易结果以后台的方式发送到商户网站
    //因前端响应受用户网络波动因素较大，所以需要以后台结果通知接受订单支付结果。Y
    private String version;//版本号,定长3，定值 4.0。Y
    private String ret_url;//页面跳转同步通知页面路径,变长128，交易完成后，联动优势支付平台会按照此地址将用户的交易结果页面重定向到商户网站。
    // 为保证商户能收到交易的结果，则必须至少提交ret_url，notify_url其中之一。N
    private String res_format;//响应数据格式,变长16，支持HTML、STRING 格式见附录(默认为HTML)。N

    /** 业务参数 */
    private String order_id;//商户唯一订单号,变长32，订单号码支持数字、英文字母、-、_、*、+、#，其他字符将不支持，不能小于4位。Y
    private String mer_date;//商户订单日期,定长8，商户生成订单的日期，格式YYYYMMDD。订单日期必须为当日。Y
    private String amount;//付款金额,变长13，人民币，以分为单位。Y
    private String amt_type;//付款币种,变长8，取值范围：RMB。Y
    private String goods_id;//商品号,变长8，支持数字，字母该参数为唯一一个对账文件返回的商户保留字段。N
    private String goods_inf;//商品描述信息,变长64，如果传递中文，需确保该字段在传入接口前，编码正确，建议使用UTF-8编码（根据商户服务器编码环境而定）。N
    private String card_id;//卡号,变长，使用联动公钥进行RSA加密后，BASE64(GBK编码)加密该字段。N
    private String identity_type;//证件类型,定长1，身份证取值：1（证件类型、证件号码需同时传有值或者同时不传）
    private String identity_code;//证件号,变长256，使用联动公钥进行RSA加密后，BASE64(GBK编码)加密该字段（证件类型、证件号码需同时传有值或者同时不传）
    private String card_holder;//持卡人姓名，变长256，使用联动公钥进行RSA加密后，BASE64(GBK编码)加密该字段
    private String mer_cust_id;//商户用户标识,变长32,用户在商户端的唯一标识。若用户为首次支付，传mer_cust_id，则绑定银行卡到该用户ID，
    // 并在支付结果通知时返回支付协议号,不传则注册支付协议号。首次支付N，协议支付Y
    /** 协议支付时使用支付协议号*/
    private String usr_pay_agreement_id;//支付协议号,变长64,用户的支付协议号，协议支付时必填。Y
    private String mer_priv;//商户私有域,变长128,联动优势支付平台原样返回，用于商户的私有信息。N
    private String user_ip;//用户IP地址,变长 16,用户在创建交易时，该用户当前所使用机器的 IP。 用作防钓鱼校验。N
    private String expand;//业务扩展信息,变长128,主要保存扩展字段的信息,详见附录。N
    private String expire_time;//订单过期时长,变长 8,单位为分钟，默认1440分钟（24小时）。N
    private String risk_expand;//风控扩展信息,变长512,格式：代码:内容#代码:内容……如：A0001:二级商户号#A0002:二级商户名称……
    //(此字段要使用UTF-8 urlencode编码)，内容说明请看 特殊字段说明。N
    private String can_modify_flag;//是否允许用户修改支付要素,定长1,如果传入card_id、card_holder和卡号，是否允许用户在联动平台上修改。N

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getMer_id() {
        return mer_id;
    }

    public void setMer_id(String mer_id) {
        this.mer_id = mer_id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRet_url() {
        return ret_url;
    }

    public void setRet_url(String ret_url) {
        this.ret_url = ret_url;
    }

    public String getRes_format() {
        return res_format;
    }

    public void setRes_format(String res_format) {
        this.res_format = res_format;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMer_date() {
        return mer_date;
    }

    public void setMer_date(String mer_date) {
        this.mer_date = mer_date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmt_type() {
        return amt_type;
    }

    public void setAmt_type(String amt_type) {
        this.amt_type = amt_type;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_inf() {
        return goods_inf;
    }

    public void setGoods_inf(String goods_inf) {
        this.goods_inf = goods_inf;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getIdentity_type() {
        return identity_type;
    }

    public void setIdentity_type(String identity_type) {
        this.identity_type = identity_type;
    }

    public String getIdentity_code() {
        return identity_code;
    }

    public void setIdentity_code(String identity_code) {
        this.identity_code = identity_code;
    }

    public String getCard_holder() {
        return card_holder;
    }

    public void setCard_holder(String card_holder) {
        this.card_holder = card_holder;
    }

    public String getMer_cust_id() {
        return mer_cust_id;
    }

    public void setMer_cust_id(String mer_cust_id) {
        this.mer_cust_id = mer_cust_id;
    }

    public String getUsr_pay_agreement_id() {
        return usr_pay_agreement_id;
    }

    public void setUsr_pay_agreement_id(String usr_pay_agreement_id) {
        this.usr_pay_agreement_id = usr_pay_agreement_id;
    }

    public String getMer_priv() {
        return mer_priv;
    }

    public void setMer_priv(String mer_priv) {
        this.mer_priv = mer_priv;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public String getExpire_time() {
        return expire_time;
    }

    public void setExpire_time(String expire_time) {
        this.expire_time = expire_time;
    }

    public String getRisk_expand() {
        return risk_expand;
    }

    public void setRisk_expand(String risk_expand) {
        this.risk_expand = risk_expand;
    }

    public String getCan_modify_flag() {
        return can_modify_flag;
    }

    public void setCan_modify_flag(String can_modify_flag) {
        this.can_modify_flag = can_modify_flag;
    }
}
