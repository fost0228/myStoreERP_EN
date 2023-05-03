package com.southwind.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.MaterialInput;
import com.southwind.mapper.MaterialInputMapper;
import com.southwind.mapper.MaterialMapper;
import com.southwind.mapper.StorageMapper;
import com.southwind.mapper.SupplierMapper;
import com.southwind.service.MaterialInputService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.util.CommonUtils;
import com.southwind.util.ImportResult;
import com.southwind.util.MaterialInputExcelModel;
import com.southwind.entity.*;
import com.southwind.util.PageObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
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

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private StorageMapper storageMapper;


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
            //search supplier name
            QueryWrapper<Supplier> supplierQueryWrapper = new QueryWrapper<>();
            supplierQueryWrapper.eq("supplier_code", materialInputExcelModel.getSupplierCode());
            Supplier supplier = this.supplierMapper.selectOne(supplierQueryWrapper);
            if (supplier == null) {

                result.setCode(-1);
                result.setMsg("【Row " + row + " is wrong】" + "Supplier is not exist!");
                return result;
            }
            materialInput.setSupplierName(supplier.getSupplierName());
            materialInput.setSupplierId(supplier.getSupplierId());

            //search storage name
            QueryWrapper<Storage> storageQueryWrapper = new QueryWrapper<>();
            storageQueryWrapper.eq("storage_code", materialInputExcelModel.getStorageCode());
            Storage storage = this.storageMapper.selectOne(storageQueryWrapper);
            if (storage == null) {
                result.setCode(-1);
                result.setMsg("【Row " + row + " is wrong】" + "Storage is not exist!");
                return result;
            }
            materialInput.setStorageName(storage.getStorageName());
            materialInput.setStorageId(storage.getStorageId());
            //pretend a userName
            materialInput.setUserName("Jim");
//            this.materialInputMapper.insert(materialInput);


            //set Date
            materialInput.setOrderDate((CommonUtils.parseString(materialInputExcelModel.getOrderDate())));

            //check if the batch No. is repeated, save it if it is not repeated, cover it if so.
            QueryWrapper<MaterialInput> materialInputQueryWrapper = new QueryWrapper<>();
            materialInputQueryWrapper.eq("batch_no", materialInput.getBatchNo());
            MaterialInput materialInput1 = this.materialInputMapper.selectOne(materialInputQueryWrapper);

            if (materialInput1 != null) {
                Integer materialInputId = materialInput1.getMaterialInputId();
                //check order status, cover it if it is not verified
                if (materialInput1.getStatus().equals(0)) {
                    //cover
                    BeanUtils.copyProperties(materialInput, materialInput1);
                    materialInput1.setMaterialInputId(materialInputId);
                    int i = this.materialInputMapper.updateById(materialInput1);
                    if (i != 1) {
                        result.setCode(-1);
                        result.setMsg("Fail to update!");
                        return result;
                    }
                }


            } else {
                int insert = this.materialInputMapper.insert(materialInput);
                if (insert != 1) {
                    result.setCode(-1);
                    result.setMsg("Fail to save.");
                    return result;
                }
            }
        }
        result.setCode(0);
        result.setMsg("Success to save.");
        return result;
    }

    @Override
    public PageObject materialInputList(PageObject pageObject) {

        return null;
    }
}