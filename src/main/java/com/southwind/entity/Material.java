package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author jiangH
 * @create 01-05-2023 5:38 PM
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Material implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "material_id", type = IdType.AUTO)
    private Integer materialId;

    private String materialCode;

    private String materialName;

    private String style;

    private String remark;

    private String materialUnit;

    private String bagUnit;

    private Integer prodTypeId;

    private Integer materialTypeId;

    private String formula;


}
