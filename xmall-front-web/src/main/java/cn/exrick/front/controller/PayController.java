package cn.exrick.front.controller;

import cn.exrick.common.pojo.Result;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.manager.dto.PayParamDto;
import cn.exrick.manager.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "订单")
public class PayController {

    @Autowired
    private PayService payService;

    @RequestMapping(value = "/pay/payment",method = RequestMethod.GET)
    @ApiOperation(value = "支付")
    public Result<Object> payment(@RequestParam(defaultValue = "")String orderId,
                                  @RequestParam(defaultValue = "")String payType){
        PayParamDto payDto = payService.pay(orderId,payType);
        return new ResultUtil<Object>().setData(payDto);
    }
}
