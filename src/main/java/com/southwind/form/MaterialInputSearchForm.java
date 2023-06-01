package com.southwind.form;

import lombok.Data;

/**
 * @author jiangH
 * @create 03-05-2023 5:26 PM
 */
@Data
public class MaterialInputSearchForm {
    private Integer supplierId;
    private String materialName;
    private String batchNo;
    private Integer status;
    private String orderDate1;
    private String orderDate2;
}
