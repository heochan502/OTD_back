package com.otd.onetoday_back.meal.util;


import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/foodexcel")
public class ExcelController {
    private final ExcelService excelService;

    @GetMapping("/upload")
    public String upload() {
        return "/InputFoodDB";
    }
    @PostMapping ("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
      return " " ;
    }
}
