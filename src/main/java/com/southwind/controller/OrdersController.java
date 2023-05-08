package com.southwind.controller;


import com.southwind.service.OrdersService;
import com.southwind.util.PageObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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

    @RequestMapping("/list")
    public String list(PageObject pageObject,Model model){
        model.addAttribute("page", this.ordersService.ordersList(pageObject));
        return "ordersList";
    }
}

