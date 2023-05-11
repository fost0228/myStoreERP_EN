package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.OrderDetail;
import com.southwind.entity.Orders;
import com.southwind.entity.Supplier;
import com.southwind.form.OrdersSearchForm;
import com.southwind.mapper.OrderDetailMapper;
import com.southwind.mapper.OrdersMapper;
import com.southwind.mapper.SupplierMapper;
import com.southwind.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.util.PageObject;
import com.southwind.vo.OrdersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//    @Autowired
//    private OrderDetailMapper orderDetailMapper;
//    @Autowired
//    private SupplierMapper supplierMapper;

    @Override
    public PageObject ordersList(PageObject pageObject, OrdersSearchForm form) {
//        ArrayList<OrdersVO> ordersVOS = new ArrayList<>();
//        List<Orders> orders = this.ordersMapper.selectList(null);
//        for(Orders order : orders){
//            OrdersVO ordersVO = new OrdersVO();
//            BeanUtils.copyProperties(order, ordersVO);
//            Supplier supplier = this.supplierMapper.selectById(order.getSupplierId());
//            ordersVO.setSupplierName(supplier.getSupplierName());
//            QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
//            queryWrapper.eq("order_no", order.getOrderNo());
//            List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(queryWrapper);
//            OrderDetail orderDetail = orderDetailList.get(0);
//            BeanUtils.copyProperties(orderDetail, ordersVO);
//            ordersVOS.add(ordersVO);
//    }
        Long index = (pageObject.getCurrent() -1)*pageObject.getSize();
        Long length = pageObject.getSize();
        List<OrdersVO> ordersVOList = this.ordersMapper.ordersVOList(index, length, form);
        PageObject result = new PageObject();
        result.setData(ordersVOList);
        result.setSize(pageObject.getSize());
        result.setCurrent(pageObject.getCurrent());
        result.setTotal(this.ordersMapper.ordersVOCount(form));
        return result;
    }
}
