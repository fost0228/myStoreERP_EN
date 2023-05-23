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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
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
    private OrderDetailMapper orderDetailMapper;

//    @Autowired
//    private OrderDetailMapper orderDetailMapper;
//    @Autowired
//    private SupplierMapper supplierMapper;

    @Override
    public PageObject ordersList(PageObject pageObject, OrdersSearchForm form) {
        Long index = (pageObject.getCurrent() - 1) * pageObject.getSize();
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
    public PageObject ordersReturnList(PageObject pageObject, OrdersSearchForm form) {
        Long index = (pageObject.getCurrent() - 1) * pageObject.getSize();
        Long length = pageObject.getSize();
        List<OrdersVO> ordersVOList = this.ordersMapper.ordersReturnVOList(index, length, form);
        PageObject result = new PageObject();
        result.setData(ordersVOList);
        result.setSize(pageObject.getSize());
        result.setCurrent(pageObject.getCurrent());
        result.setTotal(this.ordersMapper.ordersReturnVOCount(form));
        return result;
    }

    @Override
    public boolean batchDelete(String orderNoArr) {
        String[] split = orderNoArr.split(",");
        List<String> orderNoList = new ArrayList<>();
        for (String orderNo : split) {
            orderNoList.add(orderNo);
        }
        OrdersMO ordersMO = new OrdersMO();
        ordersMO.setOrderNos(orderNoList);
        int i = this.ordersMapper.batchDelete(ordersMO);
        if (i == 0) return false;
        int i1 = this.orderDetailMapper.batchDelete(ordersMO);
        if (i1 == 0) return false;
        return true;
    }

    @Override
    public boolean batchVerify(String orderNoArr) {
        String[] split = orderNoArr.split(",");
        List<String> orderNoList = new ArrayList<>();
        for (String orderNo : split) {
            orderNoList.add(orderNo);
        }
        OrdersMO ordersMO = new OrdersMO();
        ordersMO.setOrderNos(orderNoList);
        int i = this.ordersMapper.batchVerify(ordersMO);
        if (i == 0) return false;
        return true;
    }

    @Override
    public boolean batchInvalid(String orderNoArr) {
        String[] split = orderNoArr.split(",");
        List<String> orderNoList = new ArrayList<>();
        for (String orderNo : split) {
            orderNoList.add(orderNo);
        }
        OrdersMO ordersMO = new OrdersMO();
        ordersMO.setOrderNos(orderNoList);
        int i = this.ordersMapper.batchInvalid(ordersMO);
        if (i == 0) return false;
        return true;
    }

    @Override
    public boolean delete(String orderNo) {
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("order_no", orderNo);
        int delete = this.ordersMapper.delete(ordersQueryWrapper);
        if (delete == 0) return false;
        QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.eq("order_no", orderNo);
        int delete1 = this.orderDetailMapper.delete(orderDetailQueryWrapper);
        if (delete1 == 0) return false;
        return true;
    }

    @Override
    public boolean verify(String orderNo) {
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("order_no", orderNo);
        Orders orders = this.ordersMapper.selectOne(ordersQueryWrapper);
        orders.setStatus(1);
        int update = this.ordersMapper.update(orders, ordersQueryWrapper);
        if(update == 0) return false;
        return true;
    }

    @Override
    public boolean invalid(String orderNo) {
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("order_no", orderNo);
        Orders orders = this.ordersMapper.selectOne(ordersQueryWrapper);
        orders.setInvalid(0);
        int update = this.ordersMapper.update(orders, ordersQueryWrapper);
        if(update == 0) return false;
        return true;
    }

    @Override
    public boolean save(OrdersAddForm ordersAddForm) {
        //save orders
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersAddForm, orders);
        Integer count = this.ordersMapper.selectCount(null);
        orders.setOrderNo(CommonUtils.createOrderNo(count, ordersAddForm.getOrderType()));
        orders.setEmployeeName("Jack");
        if (StringUtils.isNotBlank(ordersAddForm.getOrderDate())) {
            orders.setOrderDate(CommonUtils.parseString2(ordersAddForm.getOrderDate()));
        } else {
            orders.setOrderDate(LocalDateTime.now());
        }
        int insert = this.ordersMapper.insert(orders);
        if (insert == 0) return false;
        //save orderDetail
        String orderDetailsStr = ordersAddForm.getOrderDetailsStr();
        String[] split = orderDetailsStr.split("&");
        for (String orderDetailStr : split) {
            String[] split1 = orderDetailStr.split(",");
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMaterialId(Integer.parseInt(split1[0]));
            orderDetail.setMaterialCode(split1[1]);
            orderDetail.setMaterialName(split1[2]);
            orderDetail.setStyle(split1[3]);
            orderDetail.setUnitName(split1[4]);
            orderDetail.setOrderId(split1[5]);
            orderDetail.setBatchNo(split1[6]);
            orderDetail.setOrderFlag(split1[8]);
            if (orderDetail.getOrderFlag().equals("冲红")) {
                orderDetail.setOrderCount(new BigDecimal("-" + split1[7]));
            } else {
                orderDetail.setOrderCount(new BigDecimal(split1[7]));
            }
            orderDetail.setStorageId(Integer.parseInt(split1[9]));
            orderDetail.setStorageName(split1[10]);
            orderDetail.setOrderNo(orders.getOrderNo());
            int insert1 = this.orderDetailMapper.insert(orderDetail);
            if (insert1 == 0) return false;
        }
        return true;
    }

    @Override
    public boolean update(OrdersAddForm ordersAddForm) {
        //update orders
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersAddForm, orders);
        Integer count = this.ordersMapper.selectCount(null);

        orders.setEmployeeName("Jack");
        if (StringUtils.isNotBlank(ordersAddForm.getOrderDate())) {
            orders.setOrderDate(CommonUtils.parseString2(ordersAddForm.getOrderDate()));
        } else {
            orders.setOrderDate(LocalDateTime.now());
        }
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("order_no", orders.getOrderNo());
        int updateById = this.ordersMapper.update(orders, ordersQueryWrapper);
        if (updateById == 0) return false;
        //update orderDetail
        QueryWrapper<OrderDetail> orderDetailQueryWrapper1 = new QueryWrapper<>();
        orderDetailQueryWrapper1.eq("order_no", orders.getOrderNo());
        int delete = this.orderDetailMapper.delete(orderDetailQueryWrapper1);
        if (delete == 0) return false;
        String orderDetailsStr = ordersAddForm.getOrderDetailsStr();
        String[] split = orderDetailsStr.split("&");
        for (String orderDetailStr : split) {
            String[] split1 = orderDetailStr.split(",");
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMaterialId(Integer.parseInt(split1[0]));
            orderDetail.setMaterialCode(split1[1]);
            orderDetail.setMaterialName(split1[2]);
            orderDetail.setStyle(split1[3]);
            orderDetail.setUnitName(split1[4]);
            orderDetail.setOrderId(split1[5]);
            orderDetail.setBatchNo(split1[6]);
            orderDetail.setOrderFlag(split1[8]);
            if (orderDetail.getOrderFlag().equals("冲红")) {
                orderDetail.setOrderCount(new BigDecimal("-" + split1[7]));
            } else {
                orderDetail.setOrderCount(new BigDecimal(split1[7]));
            }
            orderDetail.setStorageId(Integer.parseInt(split1[9]));
            orderDetail.setStorageName(split1[10]);
            orderDetail.setOrderNo(orders.getOrderNo());
            QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
            orderDetailQueryWrapper.eq("order_no", orders.getOrderNo());
            int insert1 = this.orderDetailMapper.update(orderDetail, orderDetailQueryWrapper);
            if (insert1 == 0) return false;
        }
        return true;
    }

    @Override
    public boolean ordersReturn(OrdersAddForm ordersAddForm) {
        //save orders
        Orders orders = new Orders();
        BeanUtils.copyProperties(ordersAddForm, orders);
        Integer count = this.ordersMapper.selectCount(null);
        orders.setOrderNo(CommonUtils.createOrderNo(count, ordersAddForm.getOrderType()));
        orders.setEmployeeName("Jack");
        if (StringUtils.isNotBlank(ordersAddForm.getOrderDate())) {
            orders.setOrderDate(CommonUtils.parseString2(ordersAddForm.getOrderDate()));
        } else {
            orders.setOrderDate(LocalDateTime.now());
        }
        int insert = this.ordersMapper.insert(orders);
        if (insert == 0) return false;
        //save orderDetail
        String orderDetailsStr = ordersAddForm.getOrderDetailsStr();
        String[] split = orderDetailsStr.split("&");
        for (String orderDetailStr : split) {
            String[] split1 = orderDetailStr.split(",");
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setMaterialId(Integer.parseInt(split1[0]));
            orderDetail.setMaterialCode(split1[1]);
            orderDetail.setMaterialName(split1[2]);
            orderDetail.setStyle(split1[3]);
            orderDetail.setUnitName(split1[4]);
            orderDetail.setOrderId(split1[5]);
            orderDetail.setBatchNo(split1[6]);
            orderDetail.setOrderFlag(split1[8]);
            if (orderDetail.getOrderFlag().equals("冲红")) {
                orderDetail.setOrderCount(new BigDecimal("-" + split1[7]));
            } else {
                orderDetail.setOrderCount(new BigDecimal(split1[7]));
                //update storage
                BigDecimal orderCount = this.orderDetailMapper.getOrderCount(split1[6], 1);
                BigDecimal subtract = orderCount.subtract(new BigDecimal(split1[7]));
                int i = this.orderDetailMapper.updateOrderCount(subtract, split1[6], 1);
                if(i == 0) return false;
            }
            orderDetail.setStorageId(Integer.parseInt(split1[9]));
            orderDetail.setStorageName(split1[10]);
            orderDetail.setOrderNo(orders.getOrderNo());
            int insert1 = this.orderDetailMapper.insert(orderDetail);
            if (insert1 == 0) return false;
        }
        return true;
    }

    @Override
    public PageObject saleList(PageObject pageObject, OrdersSearchForm form) {
        Long index = (pageObject.getCurrent() - 1) * pageObject.getSize();
        Long length = pageObject.getSize();
        List<OrdersVO> ordersVOList = this.ordersMapper.saleVOList(index, length, form);
        PageObject result = new PageObject();
        result.setData(ordersVOList);
        result.setSize(pageObject.getSize());
        result.setCurrent(pageObject.getCurrent());
        result.setTotal(this.ordersMapper.saleVOCount(form));
        return result;
    }
}
