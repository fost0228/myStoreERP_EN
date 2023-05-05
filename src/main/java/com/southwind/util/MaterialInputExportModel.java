package com.southwind.util;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jiangH
 * @create 05-05-2023 12:32 PM
 */
@Data
public class MaterialInputExportModel {

    @ExcelProperty("物料编码")
    private String materialCode;

    @ExcelProperty("物料名称")
    private String materialName;

    @ExcelProperty("生产批号")
    private String batchNo;

    @ExcelProperty("采购编号")
    private String orderId;

    @ExcelProperty("供应商")
    private String supplierName;

    @ExcelProperty("仓库")
    private String storageName;

    @ExcelProperty("规格型号")
    private String style;

    @ExcelProperty("计量单位")
    private String unitName;

    @ExcelProperty("数量")
    private BigDecimal orderCount;

    @ExcelProperty("操作员")
    private String userName;

    @ExcelProperty("订单日期")
    private String orderDate;

    @ExcelProperty("订单状态")
    private String status;

    @ExcelProperty("订单号")
    private String orderNo;

}
