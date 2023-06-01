package com.southwind.service.impl;

import com.southwind.entity.Material;
import com.southwind.mapper.MaterialMapper;
import com.southwind.service.MaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Forest
 * @since 2023-05-01
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

}
