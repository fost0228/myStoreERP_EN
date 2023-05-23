package com.southwind.controller;

import com.southwind.form.OrdersSearchForm;
import com.southwind.service.OrdersService;
import com.southwind.service.SupplierService;
import com.southwind.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jiangH
 * @create 18-05-2023 11:51 PM
 */
@Controller
@RequestMapping("sale")
public class SaleController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private SupplierService supplierService;

    @RequestMapping("/list")
    public String list(PageObject pageObject, Model model, OrdersSearchForm form){
        model.addAttribute("page", this.ordersService.saleList(pageObject, form));
        model.addAttribute("ordersForm", form);
        model.addAttribute("supplierList", this.supplierService.list());
        return "saleList";
    }
}
