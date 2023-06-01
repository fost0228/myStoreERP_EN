package com.southwind.service;

import com.southwind.entity.MaterialInput;
import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.util.ImportResult;
import com.southwind.util.MaterialInputExportModel;
import com.southwind.util.PageObject;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Forest
 * @since 2023-05-01
 */
public interface MaterialInputService extends IService<MaterialInput> {
    public ImportResult excelImport(InputStream inputStream);
    public PageObject materialInputList(PageObject pageObject,MaterialInputSearchForm materialInputSearchForm);
    public List<MaterialInputExportModel> getExportList();

    public boolean verify(Integer status, String idArray);
    public boolean delete(String idArray);
}
