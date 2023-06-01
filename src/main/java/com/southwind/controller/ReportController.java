package com.southwind.controller;

import com.southwind.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jiangH
 * @create 31-05-2023 8:19 PM
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/list")
    public String list(Model model){
        model.addAttribute("list", this.ordersService.reportList());
        return "reportList";
    }
}
