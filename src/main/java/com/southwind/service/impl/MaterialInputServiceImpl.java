package com.southwind.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.MaterialInput;
import com.southwind.mapper.MaterialInputMapper;
import com.southwind.mapper.MaterialMapper;
import com.southwind.service.MaterialInputService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.util.ImportResult;
import com.southwind.util.MaterialInputExcelModel;
import com.southwind.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Forest
 * @since 2023-05-01
 */
@Service
public class MaterialInputServiceImpl extends ServiceImpl<MaterialInputMapper, MaterialInput> implements MaterialInputService {

    @Autowired
    private MaterialInputMapper materialInputMapper;

    @Autowired
    private MaterialMapper materialMapper;


    @Override
    public ImportResult excelImport(InputStream inputStream) {
        //parse Excel to a list
        List<MaterialInputExcelModel> list = new ArrayList<>();
        try {
            EasyExcel.read(inputStream)
                    .head(MaterialInputExcelModel.class)
                    .sheet()
                    .registerReadListener(new AnalysisEventListener<MaterialInputExcelModel>() {
                        @Override
                        public void invoke(MaterialInputExcelModel excelData, AnalysisContext analysisContext) {
                            list.add(excelData);
                        }

                        @Override
                        public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        }
                    }).doRead();
        } catch (Exception e) {
            e.getStackTrace();
        }
        //save into database
        ImportResult result = new ImportResult();
        int row = 0;
        for (MaterialInputExcelModel materialInputExcelModel : list) {
            row++;
            MaterialInput materialInput = new MaterialInput();
            BeanUtils.copyProperties(materialInputExcelModel, materialInput);
            //search material ID
            QueryWrapper<Material> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("material_code", materialInput.getMaterialCode());
            Material material = this.materialMapper.selectOne(queryWrapper);
            if (material == null) {
                result.setCode(-1);
                result.setMsg("【Row " + row + " is wrong】" + "Material is not exist!");
                return result;
            }
            materialInput.setMaterialId(material.getMaterialId());
            return null;
        }
        return result;
    }
}