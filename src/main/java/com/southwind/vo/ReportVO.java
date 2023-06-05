package com.southwind.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jiangH
 * @create 31-05-2023 8:13 PM
 */

@Data
public class ReportVO {
    @ExcelProperty("单据编号")
    private String orderNo;
    @ExcelProperty("单据类型")
    private Integer orderType;
    @ExcelProperty("供应商")
    private String supplierName;
    @ExcelProperty("开单人")
    private String employeeName;
    @ExcelProperty("开单时间")
    private LocalDateTime orderDate;
    @ExcelProperty("物料编码")
    private String materialCode;
    @ExcelProperty("批号")
    private String batchNo;
    @ExcelProperty("物料名称")
    private String materialName;
    @ExcelProperty("采购编码")
    private String orderId;
    @ExcelProperty("数量")
    private BigDecimal orderCount;
    @ExcelProperty("冲红")
    private String orderFlag;
    @ExcelProperty("仓库")
    private String storageName;
}
