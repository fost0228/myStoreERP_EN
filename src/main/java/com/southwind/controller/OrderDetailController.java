package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.OrderDetail;
import com.southwind.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
@Controller
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/getOrderDetail")
    @ResponseBody
    public List<OrderDetail> getOrderDetail(String orderNo){
        QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.eq("order_no", orderNo);
        List<OrderDetail> list = this.orderDetailService.list(orderDetailQueryWrapper);
        return list;
    }

    @PostMapping("/checkBatchNoForEdit")
    @ResponseBody
    public String checkBatchNoForEdit(String orderNo, String batchNoStr){
        boolean b = this.orderDetailService.checkBatchNo(orderNo, batchNoStr);
        if(b) return "success";
        return "fail";
    }
}

