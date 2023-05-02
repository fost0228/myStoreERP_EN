package com.southwind.controller;


import com.southwind.service.MaterialInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 *  前端控制器
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

    @PostMapping("/import")
    public String materialInputImport(@RequestParam("file") MultipartFile file) throws IOException {
        this.materialInputService.excelImport(file.getInputStream());
            return null;
    }
}

