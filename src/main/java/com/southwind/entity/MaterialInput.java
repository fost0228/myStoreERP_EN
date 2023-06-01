package com.southwind.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author Forest
 * @since 2023-05-01
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class MaterialInput implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "material_input_id", type = IdType.AUTO)
      private Integer materialInputId;

    private Integer materialId;

    private String materialCode;

    private String materialName;

    private String batchNo;

    private String orderId;

    private Integer supplierId;

    private String supplierName;

    private Integer storageId;

    private String storageName;

    private String style;

    private String unitName;

    private BigDecimal orderCount;

    private String userName;

    private LocalDateTime orderDate;

    private Integer status;

    private String orderNo;


}
