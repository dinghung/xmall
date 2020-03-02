package cn.exrick.manager.dto;

import cn.exrick.common.utils.MD5Util;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class PayParamDto {

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
        Method[] methods = this.getClass().getMethods();
        try {
            for (Method method : methods) {
                Class<?>[] paramClass = method.getParameterTypes();
                if (paramClass.length > 0) { // 如果方法带参数，则跳过
                    continue;
                }
                String methodName = method.getName() ;
                if (methodName.startsWith("get")) {
                    Object value = method.invoke(this);
                    result.put(methodName, value);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return result;
    }

}
