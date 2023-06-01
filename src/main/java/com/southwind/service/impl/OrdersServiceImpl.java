package com.southwind.service.impl;

import com.southwind.entity.Orders;
import com.southwind.mapper.OrdersMapper;
import com.southwind.mo.ReportVO;
import com.southwind.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Override
    public List<ReportVO> reportList() {
        return this.ordersMapper.reportList();
    }
}
