package com.southwind.mapper;

import com.southwind.entity.MaterialInput;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.southwind.mo.MaterialInputMO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Forest
 * @since 2023-05-01
 */
public interface MaterialInputMapper extends BaseMapper<MaterialInput> {
    public int verify(MaterialInputMO materialInputMO);
}
