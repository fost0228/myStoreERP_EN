package com.southwind.service;

import com.southwind.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.mo.ReportVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
public interface OrdersService extends IService<Orders> {
    public List<ReportVO> reportList();

}
