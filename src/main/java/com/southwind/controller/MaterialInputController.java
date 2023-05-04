package com.southwind.controller;


import com.southwind.form.MaterialInputSearchForm;
import com.southwind.service.MaterialInputService;
import com.southwind.service.SupplierService;
import com.southwind.util.ImportResult;
import com.southwind.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
        PageObject resultPage = this.materialInputService.materialInputList(pageObject);
        model.addAttribute("page", resultPage);
        model.addAttribute("supplierList", this.supplierService.list());
        return "materialInputList";

    }
}

