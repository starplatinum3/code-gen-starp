package com.example.demo.controller;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ExcelController {

  @GetMapping("/export")
  public void exportExcel(HttpServletResponse response) throws Exception {
    // 创建Excel文档
    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("Sheet1");

    // 创建表头
    XSSFRow header = sheet.createRow(0);
    header.createCell(0).setCellValue("姓名");
    header.createCell(1).setCellValue("年龄");
    header.createCell(2).setCellValue("性别");

//    mapList 转化为excel java

    // 填充数据
    List<User> users = getUserList();
    int rowIndex = 1;
    for (User user : users) {
      XSSFRow row = sheet.createRow(rowIndex++);
      row.createCell(0).setCellValue(user.getName());
      row.createCell(1).setCellValue(user.getAge());
      row.createCell(2).setCellValue(user.getGender());
    }

    // 设置响应头信息
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=users.xlsx");

    // 将Excel文档写入响应流中
    ServletOutputStream outputStream = response.getOutputStream();
    workbook.write(outputStream);
    outputStream.flush();
    outputStream.close();
  }

  // 模拟获取用户数据
  private List<User> getUserList() {
    List<User> users = new ArrayList<>();
    users.add(new User("张三", 25, "男"));
    users.add(new User("李四", 30, "女"));
    users.add(new User("王五", 28, "男"));
    return users;
  }

  // 用户实体类
  private static class User {
    private String name;
    private int age;
    private String gender;

    public User(String name, int age, String gender) {
      this.name = name;
      this.age = age;
      this.gender = gender;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getAge() {
      return age;
    }

    public void setAge(int age) {
      this.age = age;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }
  }
}

