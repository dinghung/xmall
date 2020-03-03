package cn.exrick.manager.dto;

import cn.exrick.common.utils.MD5Util;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PayParamDto implements java.io.Serializable{

    //@NotNull(message = "商户Key[payKey]不能为空")
    @JSONField(serialize = false)
    public String payKey;
    //@NotNull(message = "商品名称[productName]不能为空")
    public String productName;
    //@NotNull(message = "商品订单号[orderNo]不能为空")
    public String orderNo;
    //@NotNull(message = "订单金额[orderPrice]不能为空")
    public BigDecimal orderPrice;
    //@NotNull(message = "订单IP[orderIp]不能为空")
    public String orderIp;
    //@NotNull(message = "订单日期[orderDate]不能为空")
    public String orderDate;
    //@NotNull(message = "订单时间[orderTime]不能为空")
    public String orderTime;
    //@NotNull(message = "订单有效期[orderPeriod]不能为空")
    public Integer orderPeriod;
    //@NotNull(message = "页面跳转地址[returnUrl]不能为空")
    public String returnUrl;
    //@NotNull(message = "异步通知地址[notifyUrl]不能为空")
    public String notifyUrl;
    //@NotNull(message = "签名[sign]不能为空")
    public String sign;
    public String payUrl;

    public void singData(String paySecret){
        SortedMap<String, Object> smap = new TreeMap<String, Object>(conventToMap());
        StringBuffer stringBuffer = new StringBuffer();
        for (Map.Entry<String, Object> m : smap.entrySet()) {
            Object value = m.getValue();
            if (!"sign".equals(m.getKey())&&value != null && StringUtils.isNotBlank(String.valueOf(value))){
                stringBuffer.append(m.getKey()).append("=").append(m.getValue()).append("&");
            }
        }
        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());


        String argPreSign = stringBuffer.append("&paySecret=").append(paySecret).toString();
        String signStr = MD5Util.encode(argPreSign).toUpperCase();
        this.sign = signStr;
    }

    public SortedMap<String, Object> conventToMap(){
        SortedMap<String, Object> result = new TreeMap<String, Object>();
        Field[] fields = this.getClass().getFields();
        try {
            for(Field field : fields){
                String filedName = field.getName();
                if("payUrl".equals(filedName))continue;//payUrl字段不参与签名
                Object value = field.get(this);
                if(value==null)continue;
                result.put(filedName,value);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }  catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        PayParamDto payDto = new PayParamDto();
        payDto.productName = "辉暘工作室支付";
        payDto.orderNo = "1";
        payDto.orderPrice = new BigDecimal(2);
        try {
            payDto.orderIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        payDto.orderPeriod = 5;
        //returnUrl
        //notifyUrl
        Date orderTime = new Date();//订单时间
        String orderDateStr = new SimpleDateFormat("yyyyMMdd").format(orderTime);// 订单日期
        String orderTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(orderTime);// 订单时间
        payDto.orderDate = orderDateStr;
        payDto.orderTime = orderTimeStr;
        payDto.returnUrl = "XX";
        payDto.notifyUrl = "YY";
        payDto.payUrl = "ZZ";
        payDto.singData("123");
    }
}
