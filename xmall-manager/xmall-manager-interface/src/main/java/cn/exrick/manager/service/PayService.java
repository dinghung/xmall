package cn.exrick.manager.service;

import cn.exrick.manager.dto.PayParamDto;

/**
 *
 */
public interface PayService {

    /**
     * 支付
     * @param dto
     * @return
     */
    public PayParamDto pay(PayParamDto dto);

    public PayParamDto pay(String orderId, String payType);
}
