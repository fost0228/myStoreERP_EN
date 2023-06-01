package com.southwind.mo;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jiangH
 * @create 31-05-2023 8:09 PM
 */
@Data
public class ReportVO {
    private String orderNo;
    private Integer orderType;
    private String supplierName;
    private String employeeName;
    private LocalDateTime orderDate;
    private String materialCode;
    private String batchNo;
    private String materialName;
    private String orderId;
    private BigDecimal orderCount;
    private String orderFlag;
    private String storageName;
}
