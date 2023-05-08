package com.southwind.controller;


import com.alibaba.excel.EasyExcel;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.service.MaterialInputService;
import com.southwind.service.SupplierService;
import com.southwind.util.CustomCellWriteHandler;
import com.southwind.util.ImportResult;
import com.southwind.util.MaterialInputExportModel;
import com.southwind.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Forest
 * @since 2023-05-01
 */
@Controller
@RequestMapping("/materialInput")
public class MaterialInputController {

    @Autowired
    private MaterialInputService materialInputService;

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/import")
    public String materialInputImport(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        ImportResult result = this.materialInputService.excelImport(file.getInputStream());
        if (result.getCode().equals(0)) return "redirect:/materialInput/list";
        model.addAttribute("errorMsg", result.getMsg());
        return "materialInput";
    }


    @RequestMapping("/list")
    public String list(Model model, PageObject pageObject, MaterialInputSearchForm materialInputSearchForm) {
        PageObject resultPage = this.materialInputService.materialInputList(pageObject, materialInputSearchForm);
        model.addAttribute("page", resultPage);
        model.addAttribute("supplierList", this.supplierService.list());
        model.addAttribute("form", materialInputSearchForm);
        return "materialInputList";

    }

    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("采购数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //get data
            List<MaterialInputExportModel> list = this.materialInputService.getExportList();
            EasyExcel.write(response.getOutputStream(), MaterialInputExportModel.class)
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .sheet("采购数据")
                    .doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/verify")
    @ResponseBody
    public String verify(Integer status, String idArray) {
        boolean verify = this.materialInputService.verify(status, idArray);
        if (verify) return "success";
        return "fail";

    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(String idArray){
        boolean delete = this.materialInputService.delete(idArray);
        if(delete) return "success";
        return "fail";
    }
}

