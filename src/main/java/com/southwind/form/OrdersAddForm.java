package com.southwind.form;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jiangH
 * @create 14-05-2023 3:15 PM
 */
@Data
public class OrdersAddForm {
    private String orderNo;
    private String orderDate;
    private Integer supplierId;
    private Integer orderType;
    private Integer invalid;
    private Integer status;
    private String remark;
    private String orderDetailsStr;
}
