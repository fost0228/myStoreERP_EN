package com.southwind.controller;

import com.alibaba.excel.EasyExcel;
import com.southwind.form.ReportForm;
import com.southwind.service.OrdersService;
import com.southwind.service.StorageService;
import com.southwind.util.CustomCellWriteHandler;
import com.southwind.util.MaterialInputExportModel;
import com.southwind.util.PageObject;
import com.southwind.vo.ReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author jiangH
 * @create 01-06-2023 9:51 PM
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private StorageService storageService;

    @RequestMapping("/list")
    public String list(PageObject pageObject, ReportForm form, Model model){
        model.addAttribute("list", this.ordersService.reportList(pageObject, form));
        model.addAttribute("reportForm", form);
        model.addAttribute("storageList", this.storageService.list());
        return "reportList";
    }

    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            String fileName = URLEncoder.encode("仓库明细报表", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //get data
            List<ReportVO> reportVOList = this.ordersService.reportList();
            EasyExcel.write(response.getOutputStream(), ReportVO.class)
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .sheet("仓库明细")
                    .doWrite(reportVOList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
