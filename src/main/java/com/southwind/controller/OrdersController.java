package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.Material;
import com.southwind.entity.Orders;
import com.southwind.entity.Storage;
import com.southwind.form.MaterialInputSearchForm;
import com.southwind.form.OrdersAddForm;
import com.southwind.form.OrdersSearchForm;
import com.southwind.service.*;
import com.southwind.util.CommonUtils;
import com.southwind.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 前端控制器
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
    @Autowired
    private OrderDetailService orderDetailService;


    @RequestMapping("/list")
    public String list(PageObject pageObject, Model model, OrdersSearchForm form) {
        model.addAttribute("page", this.ordersService.ordersList(pageObject, form));
        model.addAttribute("supplierList", this.supplierService.list());
        model.addAttribute("form", form);
        return "ordersList";
    }

    @PostMapping("/batchDelete")
    @ResponseBody
    public String batchDelete(String orderNoArr) {
        boolean batchDelete = this.ordersService.batchDelete(orderNoArr);
        if (batchDelete) return "success";
        return "fail";
    }

    @PostMapping("/batchVerify")
    @ResponseBody
    public String batchVerify(String orderNoArr) {
        boolean b = this.ordersService.batchVerify(orderNoArr);
        if (b) return "success";
        return "fail";
    }

    @PostMapping("/batchInvalid")
    @ResponseBody
    public String batchInvalid(String orderNoArr) {
        boolean b = this.ordersService.batchInvalid(orderNoArr);
        if (b) return "success";
        return "fail";
    }

    @GetMapping("/init")
    public String init(Model model) {
        model.addAttribute("supplierList", this.supplierService.list());
        return "orderAdd";
    }

    @GetMapping("/storageList")
    @ResponseBody
    public List<Storage> storageList() {
        return this.storageService.list();
    }

    @GetMapping("/materialList")
    @ResponseBody
    public List<Material> materialList() {
        return this.materialService.list();
    }


    @PostMapping("/checkBatchNo")
    @ResponseBody
    public String checkBatchNo(String batchNoStr) {
        boolean checkBatchNo = this.orderDetailService.checkBatchNo(batchNoStr);
        if(checkBatchNo) return "success";
        return "fail";
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(OrdersAddForm ordersAddForm){
        boolean save = this.ordersService.save(ordersAddForm);
        if(save) return "success";
        return "fail";
    }

    @GetMapping("/edit")
    public String edit(String orderNo, Model model){
        QueryWrapper<Orders> ordersQueryWrapper = new QueryWrapper<>();
        ordersQueryWrapper.eq("order_no", orderNo);
        Orders orders = this.ordersService.getOne(ordersQueryWrapper);
        model.addAttribute("orders", orders);
        model.addAttribute("supplierList", this.supplierService.list());
        return "ordersEdit";
    }

    @PostMapping("/update")
    @ResponseBody
    public String update(OrdersAddForm ordersAddForm){
        boolean save = this.ordersService.update(ordersAddForm);
        if(save) return "success";
        return "fail";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String delete(String orderNo) {
        boolean batchDelete = this.ordersService.delete(orderNo);
        if (batchDelete) return "success";
        return "fail";
    }

    @PostMapping("/verify")
    @ResponseBody
    public String verify(String orderNo) {
        boolean b = this.ordersService.verify(orderNo);
        if (b) return "success";
        return "fail";
    }

    @PostMapping("/invalid")
    @ResponseBody
    public String invalid(String orderNo) {
        boolean b = this.ordersService.invalid(orderNo);
        if (b) return "success";
        return "fail";
    }


    @RequestMapping("/returnList")
    public String returnList(PageObject pageObject, Model model){

        return "ordersReturnList";
    }
}

