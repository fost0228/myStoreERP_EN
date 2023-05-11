package com.southwind.service;

import com.southwind.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.form.OrdersSearchForm;
import com.southwind.util.PageObject;
import com.southwind.vo.OrdersVO;

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
    public PageObject ordersList(PageObject pageObject, OrdersSearchForm form);
}
