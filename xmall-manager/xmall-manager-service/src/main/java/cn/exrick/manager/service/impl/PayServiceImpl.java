package cn.exrick.manager.service.impl;

import cn.exrick.manager.dto.PayParamDto;
import cn.exrick.manager.service.PayService;
import cn.exrick.manager.wapdbdto.TbOrder;
import com.itranswarp.warpdb.WarpDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class PayServiceImpl extends AbstractDbService implements PayService {

    @Value("${payKey}")
    private String payKey;
    @Value("${paySecret}")
    private String paySecret;
    @Value("${payUrl}")
    private String payUrl;
    @Value("${returnUrl}")
    private String returnUrl;
    @Value("${notifyUrl}")
    private String notifyUrl;
    @Value("${orderPeriod}")
    private int orderPeriod;


    public PayParamDto pay(PayParamDto dto){
        dto.payKey = payKey;
        dto.payUrl = payUrl;
        dto.singData(paySecret);
        logger.info("pay sign :" + dto.sign);
        return dto;
    }

    @Override
    public PayParamDto pay(String orderId, String payType) {
        TbOrder tbOrder = db.get(TbOrder.class,orderId);

        PayParamDto payDto = new PayParamDto();
        payDto.productName = "辉暘工作室支付";
        payDto.orderNo = orderId.toString();
        payDto.orderPrice = tbOrder.payment;
        try{
            payDto.orderIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        payDto.orderPeriod = orderPeriod;
        //returnUrl
        //notifyUrl
        Date orderTime = new Date();//订单时间
        String orderDateStr = new SimpleDateFormat("yyyyMMdd").format(orderTime);// 订单日期
        String orderTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(orderTime);// 订单时间
        payDto.orderDate = orderDateStr;
        payDto.orderTime = orderTimeStr;
        pay(payDto);
        return payDto;
    }
}
