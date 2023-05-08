package com.southwind.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jiangH
 * @create 08-05-2023 11:06 AM
 */
@Data
public class OrdersVO {
    private Integer orderType;
    private String orderNo;
    private String supplierName;
    private String employeeName;
    private LocalDateTime orderDate;
    private Integer invalid;
    private Integer status;
    private String materialCode;
    private String materialName;
    private String batchNo;
    private String style;
    private String storageName;
    private String unitName;
    private BigDecimal orderCount;
    private String orderFlag;
    private String verifyPerson;
    private LocalDateTime verifyDate;


}
