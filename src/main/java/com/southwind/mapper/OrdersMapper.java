package com.southwind.mapper;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.southwind.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.form.OrdersSearchForm;
import com.southwind.mo.OrdersMO;
import com.southwind.vo.OrdersVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<OrdersVO> ordersVOList(Long index, Long length, OrdersSearchForm form);
    public List<OrdersVO> ordersReturnVOList(Long index, Long length, OrdersSearchForm form);
    public Long ordersVOCount(OrdersSearchForm form);
    public Long ordersReturnVOCount(OrdersSearchForm form);
    public int batchDelete(OrdersMO ordersMO);
    public int batchVerify(OrdersMO ordersMO);
    public int batchInvalid(OrdersMO ordersMO);
    public List<OrdersVO> saleVOList(Long index, Long length, OrdersSearchForm form);
    public Long saleVOCount(OrdersSearchForm form);
}
