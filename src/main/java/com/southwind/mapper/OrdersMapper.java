package com.southwind.mapper;

import com.southwind.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.vo.OrdersVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
public interface OrdersMapper extends BaseMapper<Orders> {
    public List<OrdersVO> ordersVOList(Long index, Long length);
    public Long ordersVOCount();

}
