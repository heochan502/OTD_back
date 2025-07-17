package com.otd.onetoday_back.meal.util;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper

public interface ExcelMapper {
    public int insertExcel(List<FoodDBExcelDto> excelDto);
}
