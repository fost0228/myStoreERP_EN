package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.OrderDetail;
import com.southwind.entity.Orders;
import com.southwind.entity.Supplier;
import com.southwind.form.OrdersAddForm;
import com.southwind.form.OrdersSearchForm;
import com.southwind.mapper.OrderDetailMapper;
import com.southwind.mapper.OrdersMapper;
import com.southwind.mapper.SupplierMapper;
import com.southwind.mo.OrdersMO;
import com.southwind.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.util.CommonUtils;
import com.southwind.util.PageObject;
import com.southwind.vo.OrdersVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private OrderDetailMapper   orderDetailMapper;

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

    @Override
    public boolean batchDelete(String orderNoArr) {
        String[] split = orderNoArr.split(",");
        List<String> orderNoList = new ArrayList<>();
        for(String orderNo: split){
//            QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
//            ordersQueryWrapper.eq("order_no", orderNo);
//            int delete = this.ordersMapper.delete(ordersQueryWrapper);
//            if(delete != 1) return false;
//            QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
//            orderDetailQueryWrapper.eq("order_no", orderNo);
//            int delete1 = this.orderDetailMapper.delete(orderDetailQueryWrapper);
//            if(delete1 != 1) return false;
            orderNoList.add(orderNo);
        }
        OrdersMO ordersMO = new OrdersMO();
        ordersMO.setOrderNos(orderNoList);
        int i = this.ordersMapper.batchDelete(ordersMO);
        if(i == 0) return false;
        int i1 = this.orderDetailMapper.batchDelete(ordersMO);
        if(i1 == 0) return false;
        return true;
    }

    @Override
    public boolean batchVerify(String orderNoArr) {
        String[] split = orderNoArr.split(",");
        List<String> orderNoList = new ArrayList<>();
        for(String orderNo: split){
            orderNoList.add(orderNo);
        }
        OrdersMO ordersMO = new OrdersMO();
        ordersMO.setOrderNos(orderNoList);
        int i = this.ordersMapper.batchVerify(ordersMO);
        if(i == 0) return false;
        return true;
    }

    @Override
    public boolean batchInvalid(String orderNoArr) {
        String[] split = orderNoArr.split(",");
        List<String> orderNoList = new ArrayList<>();
        for(String orderNo: split){
            orderNoList.add(orderNo);
        }
        OrdersMO ordersMO = new OrdersMO();
        ordersMO.setOrderNos(orderNoList);
        int i = this.ordersMapper.batchInvalid(ordersMO);
        if(i == 0) return false;
        return true;
    }

    @Override
    public boolean save(OrdersAddForm ordersAddForm) {
        //save orders
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersAddForm, orders);
        Integer count = this.ordersMapper.selectCount(null);
        orders.setOrderNo(CommonUtils.createOrderNo(count,ordersAddForm.getOrderType()));
        orders.setEmployeeName("Jack");
        if(StringUtils.isNotBlank(ordersAddForm.getOrderDate())){
            orders.setOrderDate(CommonUtils.parseString2(ordersAddForm.getOrderDate()));
        }else{
            orders.setOrderDate(LocalDateTime.now());
        }
        return false;
    }
}
