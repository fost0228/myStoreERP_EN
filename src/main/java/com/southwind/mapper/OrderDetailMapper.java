package com.southwind.mapper;

import com.southwind.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.mo.OrderDetailMO;
import com.southwind.mo.OrdersMO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    public int batchDelete(OrdersMO ordersMO);
    public int checkBatchNo(OrderDetailMO orderDetailMO);
}
