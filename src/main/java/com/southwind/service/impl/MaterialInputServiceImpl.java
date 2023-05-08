package com.southwind.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.southwind.entity.MaterialInput;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.mapper.*;
import com.southwind.service.MaterialInputService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.util.*;
import com.southwind.entity.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;


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
    public PageObject materialInputList(PageObject pageObject, MaterialInputSearchForm materialInputSearchForm) {
        Page<MaterialInput> page = new Page<>(pageObject.getCurrent(), pageObject.getSize());
        QueryWrapper<MaterialInput> queryWrapper = new QueryWrapper<>();
        boolean supplierIdFlag = materialInputSearchForm.getSupplierId() != null;
        boolean statusFlag = materialInputSearchForm.getStatus() != null;
        queryWrapper.eq(supplierIdFlag, "supplier_id", materialInputSearchForm.getSupplierId())
                .like(StringUtils.isNotBlank(materialInputSearchForm.getMaterialName()), "material_name", materialInputSearchForm.getMaterialName())
                .like(StringUtils.isNotBlank(materialInputSearchForm.getBatchNo()), "batch_no", materialInputSearchForm.getBatchNo())
                .eq(statusFlag, "status", materialInputSearchForm.getStatus())
                .between(StringUtils.isNotBlank(materialInputSearchForm.getOrderDate1())
                                && StringUtils.isNotBlank(materialInputSearchForm.getOrderDate2()),
                        "order_date", materialInputSearchForm.getOrderDate1(), materialInputSearchForm.getOrderDate2());
        Page<MaterialInput> resultPage = this.materialInputMapper.selectPage(page, queryWrapper);
        PageObject result = new PageObject();
        result.setCurrent(resultPage.getCurrent());
        result.setSize(resultPage.getSize());
        result.setTotal(resultPage.getTotal());
        result.setData(resultPage.getRecords());
        return result;
    }

    @Override
    public List<MaterialInputExportModel> getExportList() {
        List<MaterialInput> materialInputs = this.materialInputMapper.selectList(null);
        ArrayList<MaterialInputExportModel> list = new ArrayList<>();
        for (MaterialInput materialInput : materialInputs) {
            MaterialInputExportModel model = new MaterialInputExportModel();
            BeanUtils.copyProperties(materialInput, model);
            //date
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String format = materialInput.getOrderDate().format(dateTimeFormatter);
            model.setOrderDate(format);
            //status
            String status = "";
            switch (materialInput.getStatus()) {
                case 0:
                    status = "Not validate";
                    break;
                case 1:
                    status = "validated";
                    break;
                case 2:
                    status = "In storage";
                    break;
            }
            model.setStatus(status);
            list.add(model);

        }
        return list;
    }

    @Override
    public boolean verify(Integer status, String idArray) {
        boolean flag = false;
        String[] ids = idArray.split(",");
        switch (status) {
            case 1:
                //validate

                for (String id : ids) {
                    MaterialInput materialInput = this.materialInputMapper.selectById(id);
                    materialInput.setStatus(status);
                    int updateById = this.materialInputMapper.updateById(materialInput);
                    if (updateById != 1) return false;
                }
                flag = true;
                break;
            //into storage
            case 2:

                for (String id : ids) {
                    MaterialInput materialInput = this.materialInputMapper.selectById(id);
                    materialInput.setStatus(status);
                    //sync data into orders & order_detail table
                    Orders orders = new Orders();
                    Integer count = this.ordersMapper.selectCount(null);
                    orders.setOrderNo(CommonUtils.createOrderNo(count, 1));
                    orders.setInvalid(1);
                    orders.setVerifyPerson("Layia");
                    orders.setVerifyDate(LocalDateTime.now());
                    orders.setEmployeeName(materialInput.getUserName());
                    orders.setOrderType(1);
                    int insert = this.ordersMapper.insert(orders);
                    if (insert != 1) return false;
                    //sync data into table orderDetail
                    OrderDetail orderDetail = new OrderDetail();
                    BeanUtils.copyProperties(materialInput, orderDetail);
                    orderDetail.setOrderNo(orders.getOrderNo());
                    orderDetail.setOrderFlag("Normal");
                    int insert1 = this.orderDetailMapper.insert(orderDetail);
                    if(insert1 !=1) return false;
                    materialInput.setStatus(status);

                    //generate order no.

                    materialInput.setOrderNo(orders.getOrderNo());
                    int updateById = this.materialInputMapper.updateById(materialInput);
                    if (updateById != 1) return false;
                }
                flag = true;
                break;

        }
        return flag;
    }
}