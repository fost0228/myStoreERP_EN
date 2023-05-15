package com.southwind.service;

import com.southwind.entity.OrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
public interface OrderDetailService extends IService<OrderDetail> {
    public boolean checkBatchNo(String batchNoStr);
    public boolean checkBatchNo(String orderNo, String batchNoStr);
}
