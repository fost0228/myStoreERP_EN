package com.southwind.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class OrderDetail implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "order_detail_id", type = IdType.AUTO)
      private Integer orderDetailId;

    private String orderNo;

    private Integer materialId;

    private String materialCode;

    private String materialName;

    private String style;

    @JsonProperty("materialUnit")
    private String unitName;

    private String orderId;

    private String batchNo;

    private BigDecimal orderCount;

    private String orderFlag;

    private Integer storageId;

    private String storageName;


}
