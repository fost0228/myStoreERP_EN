package com.southwind.controller;

import com.southwind.entity.OrderDetail;
import com.southwind.form.OrdersAddForm;
import com.southwind.form.OrdersSearchForm;
import com.southwind.service.OrderDetailService;
import com.southwind.service.OrdersService;
import com.southwind.service.SupplierService;
import com.southwind.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    @Autowired
    private OrderDetailService orderDetailService;

    @RequestMapping("/list")
    public String list(PageObject pageObject, Model model, OrdersSearchForm form){
        model.addAttribute("page", this.ordersService.saleList(pageObject, form));
        model.addAttribute("ordersForm", form);
        model.addAttribute("supplierList", this.supplierService.list());
        return "saleList";
    }

    @GetMapping("/init")
    public String init(Model model){
        model.addAttribute("supplierList", this.supplierService.list());
        return "saleAdd";
    }
    @GetMapping("/returnInit")
    public String returnInit(Model model){
        model.addAttribute("supplierList", this.supplierService.list());
        return "saleAdd";
    }

    @PostMapping("/save")
    @ResponseBody
    public String save(OrdersAddForm ordersAddForm){
        boolean save = this.ordersService.saleSave(ordersAddForm);
        if(save) return "success";
        return "fail";
    }

    @PostMapping("/return")
    @ResponseBody
    public String ordersReturn(OrdersAddForm ordersAddForm){
        boolean ordersReturn = this.ordersService.ordersReturn(ordersAddForm);
        if(ordersReturn) return "success";
        return "fail";
    }

    @RequestMapping("/returnList")
    public String returnList(PageObject pageObject, Model model, OrdersSearchForm form){
        model.addAttribute("page", this.ordersService.saleList(pageObject, form));
        model.addAttribute("ordersForm", form);
        model.addAttribute("supplierList", this.supplierService.list());
        return "saleReturnList";
    }

    @GetMapping("/saleReturnOrdersDetailList")
    @ResponseBody
    public List<OrderDetail> saleReturnOrdersDetailList(){
        return this.orderDetailService.orderDetailList();
    }
}
