package top.starp.util;

import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapListToExcelConverter {
    public static void convert(List<Map<String, Object>> mapList, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        int columnCount = 0;
        for (String key : mapList.get(0).keySet()) {
            Cell cell = headerRow.createCell(columnCount++);
            cell.setCellValue(key);
        }

        // 填充数据
        int rowCount = 1;
        for (Map<String, Object> map : mapList) {
            Row dataRow = sheet.createRow(rowCount++);
            columnCount = 0;
            for (Object value : map.values()) {
                Cell cell = dataRow.createCell(columnCount++);
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                }
                // 如果有其他数据类型，可以继续扩展判断和处理
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            System.out.println("Excel 文件已成功生成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        ExcelUtil.d
//        ExcelTool
        // 示例数据
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> map = u.map(
                    "姓名", "用户" + i,
                    "年龄", 20 + i,
                    "成绩", 80.0 + i
            );
//            Map<String, Object> map = Map.of(
//                    "姓名", "用户" + i,
//                    "年龄", 20 + i,
//                    "成绩", 80.0 + i
//            );
            mapList.add(map);
        }


        String filePath = "/file/path/to/output.xlsx";  // Excel 文件保存路径
//        FileUtil.wr();
//        java 一个文件的路径 父路径如果没有 就创建
        FileUtil.createParentDirectory(filePath);
        convert(mapList, filePath);
    }
}
