package com.otd.onetoday_back.meal.util;



import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.otd.onetoday_back.meal.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class ExcelService {
    private final ExcelMapper inExcel;

    public String excelupload(MultipartFile file, Model model) {
        log.info("파일명: {}", file.getOriginalFilename() );
        log.info("파일 크기 {}", file.getSize());
        try{
            if (file.isEmpty()) {
                log.info("파일을 선택하여 주세요");
            return "/InputFoodDB";
            }
            //확장자 검증
            String fileName = file.getOriginalFilename();
            if(!fileName.endsWith(".xlsx") &&  !fileName.endsWith(".xls")) {
                model.addAttribute("message", "excel파일만 업로드 가능합니다");
                return "/InputFoodDB";
            }
            // 객체 생성
            List<FoodDBExcelDto> foodDBData = new ArrayList<FoodDBExcelDto>();

            //excel파일 처리
            //스프링 부트에서 Workbook은 Apache POI 라이브러리를 사용하여 엑셀 파일을 다루기 위한 인터페이스
            //또는 클래스를 의미합니다.
            //주로 엑셀 파일의 읽기, 쓰기, 조작 등의 작업을 수행할 때 사용됩니다
            Workbook workbook = WorkbookFactory.create(file.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            //헤더 행 읽기

            Row headerRow = sheet.getRow(0);
            for (Cell cell : headerRow) {
                log.debug("{}\\t ", cell.getStringCellValue());
            }
            DataFormatter formatter = new DataFormatter();
            // 테이터행 읽기
            log.info("파일총 행의 수 {}", sheet.getLastRowNum());
            for (int i =1; i<=sheet.getLastRowNum(); i++ ) {
                Row row = sheet.getRow(i);
                // 행 들을 리스트 객페로 구성
               foodDBData.add(FoodDBExcelDto.builder()
                                .foodName(row.getCell(0).getStringCellValue())
                                .foodCategory(row.getCell(1).getStringCellValue())
                                .calorie(Integer.parseInt(formatter.formatCellValue(row.getCell(3))))
                                .protein(Float.parseFloat( formatter.formatCellValue(row.getCell(4))))
                               .fat(Float.parseFloat( formatter.formatCellValue(row.getCell(5))))
                               .carbohydrate(Float.parseFloat( formatter.formatCellValue(row.getCell(6))))
                               .sugar(Float.parseFloat( formatter.formatCellValue(row.getCell(7))))
                               .natrium(Float.parseFloat( formatter.formatCellValue(row.getCell(8))))
                                .build());

            }
            log.info("==========================================");

            int batchsize=1000;
            int end=0;

            for (int i = 0; i < foodDBData.size(); i+=batchsize) {
                 end = Math.min(i+batchsize,foodDBData.size());
                 List<FoodDBExcelDto> batch = foodDBData.subList(i,end);
                inExcel.insertExcel(batch);
            }


            workbook.close();
            model.addAttribute("message", "파일이 성공적으로 업로드 되었습니다.");

        }
        catch (IOException e){
            model.addAttribute("message", "파일 처리중 오류가 발생 하였습니다..");
        }

        return "/InputFoodDB";
    }

}
