package com.southwind.util;

import lombok.Data;

import java.util.List;

/**
 * @author jiangH
 * @create 03-05-2023 12:00 AM
 */
@Data
public class PageObject {
    private Long current = 1L;
    private Long size = 5L;
    private Long total;
    private List data;
}
