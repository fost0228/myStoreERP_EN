package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.OrderDetail;
import com.southwind.mapper.OrderDetailMapper;
import com.southwind.mo.OrderDetailMO;
import com.southwind.service.OrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public boolean checkBatchNo(String batchNoStr) {
        QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        String[] split = batchNoStr.split(",");
        ArrayList<String> batchNoList = new ArrayList<>();
        for(String batchNo : split){
            batchNoList.add(batchNo);
//            orderDetailQueryWrapper.eq("batch_no", batchNo);
//            List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(orderDetailQueryWrapper);
//            if(orderDetailList.size() > 0) return false;
        }
        OrderDetailMO orderDetailMO = new OrderDetailMO();
        orderDetailMO.setBatchNoList(batchNoList);
        int count = this.orderDetailMapper.checkBatchNo(orderDetailMO);
        if(count == 0) return true;
        return false;
    }

    @Override
    public boolean checkBatchNo(String orderNo, String batchNoStr) {
        String[] split = batchNoStr.split(",");
        ArrayList<String> batchNoList = new ArrayList<>();
        for(String batchNo : split){
            QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
            orderDetailQueryWrapper.eq("batch_no", batchNo);
            List<OrderDetail> orderDetailList = this.orderDetailMapper.selectList(orderDetailQueryWrapper);
            for (OrderDetail orderDetail : orderDetailList) {
                if(orderDetail.getOrderNo().equals(orderNo)) return false;
            }
        }
        return true;
    }

    @Override
    public List<OrderDetail> orderDetailList() {

        return this.orderDetailMapper.orderDetailList();
    }

    @Override
    public List<OrderDetail> saleReturnOrdersDetailList() {
        return this.orderDetailMapper.saleReturnOrdersDetailList();

    }
}
