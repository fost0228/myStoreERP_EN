package com.southwind.form;

import lombok.Data;

@Data
public class ReportForm {
    private Integer storageId;
    private Integer orderType;
    private String materialName;
    private String orderDate;
    private String orderDate2;
}
