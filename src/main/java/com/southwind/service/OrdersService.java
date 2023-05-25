package com.southwind.service;

import com.southwind.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.form.OrdersAddForm;
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
    public PageObject ordersReturnList(PageObject pageObject, OrdersSearchForm form);
    public boolean batchDelete(String orderNoArr);
    public boolean batchVerify(String orderNoArr);
    public boolean batchInvalid(String orderNoArr);
    public boolean delete(String orderNo);
    public boolean verify(String orderNo);
    public boolean invalid(String orderNo);
    public boolean save(OrdersAddForm ordersAddForm);
    public boolean update(OrdersAddForm ordersAddForm);
    public boolean ordersReturn(OrdersAddForm ordersAddForm);
    public PageObject saleList(PageObject pageObject, OrdersSearchForm form);
    public boolean saleSave(OrdersAddForm ordersAddForm);
    public PageObject saleReturnList(PageObject pageObject, OrdersSearchForm form);

}
