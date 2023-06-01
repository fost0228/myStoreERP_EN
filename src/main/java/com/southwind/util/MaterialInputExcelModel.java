package com.southwind.util;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author jiangH
 * @create 01-05-2023 5:16 PM
 */

@Data
public class MaterialInputExcelModel {
    @ExcelProperty("供应商代码")
    private String supplierCode;

    @ExcelProperty("到货日期")
    private String orderDate;

    @ExcelProperty("仓库代码")
    private String storageCode;

    @ExcelProperty("物料编码")
    private String materialCode;

    @ExcelProperty("物料名称")
    private String materialName;
    @ExcelProperty("规格型号")
    private String style;
    @ExcelProperty("计量单位")
    private String unitName;
    @ExcelProperty("采购单号")
    private String orderId;
    @ExcelProperty("生产批号")
    private String batchNo;
    @ExcelProperty("数量")
    private BigDecimal orderCount;
}
