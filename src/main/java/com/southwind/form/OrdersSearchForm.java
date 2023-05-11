package com.southwind.form;

import lombok.Data;

/**
 * @author jiangH
 * @create 10-05-2023 2:11 AM
 */
@Data
public class OrdersSearchForm {
    private Integer supplierId;
    private Integer invalid;
    private Integer status;
    private String employeeName;
    private String orderDate1;
    private String orderDate2;
}
