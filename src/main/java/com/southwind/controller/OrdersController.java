package com.southwind.controller;


import com.southwind.entity.Material;
import com.southwind.entity.Storage;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.form.OrdersSearchForm;
import com.southwind.service.MaterialService;
import com.southwind.service.OrdersService;
import com.southwind.service.StorageService;
import com.southwind.service.SupplierService;
import com.southwind.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Forest
 * @since 2023-05-06
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {


    @Autowired
    private OrdersService ordersService;
    @Autowired
    private SupplierService supplierService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private MaterialService materialService;


    @RequestMapping("/list")
    public String list(PageObject pageObject, Model model, OrdersSearchForm form){
        model.addAttribute("page", this.ordersService.ordersList(pageObject, form));
        model.addAttribute("supplierList", this.supplierService.list());
        model.addAttribute("form", form);
        return "ordersList";
    }

    @PostMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(String orderNoArr){
        boolean batchDelete = this.ordersService.batchDelete(orderNoArr);
        if(batchDelete) return "success";
        return "fail";
    }

    @PostMapping("/batchVerify")
    @ResponseBody
    public String batchVerify(String orderNoArr){
        boolean b = this.ordersService.batchVerify(orderNoArr);
        if(b) return "success";
        return "fail";
    }

    @PostMapping("/batchInvalid")
    @ResponseBody
    public String batchInvalid(String orderNoArr){
        boolean b = this.ordersService.batchInvalid(orderNoArr);
        if(b) return "success";
        return "fail";
    }

    @GetMapping("/init")
    public String init(Model model){
        model.addAttribute("supplierList", this.supplierService.list());
        return "orderAdd";
    }

    @GetMapping("/storageList")
    @ResponseBody
    public List<Storage> storageList(){
        return this.storageService.list();
    }

    @GetMapping("/materialList")
    @ResponseBody
    public List<Material> materialList(){
        return this.materialService.list();
    }

}

