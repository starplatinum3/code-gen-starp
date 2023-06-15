package com.example.demo.entity;

import lombok.Data;
//
//@Data
//public class DatabaseEntity {
//    private Long id;
//    private String name;
//    private int age;
//    // 更多字段...
//
//    // 构造函数、getter和setter等方法由Lombok自动生成
//}

public class DatabaseEntity {
    private String fieldName;
    private String fieldComment;
    private String fieldType;
    private int fieldLength;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldComment() {
        return fieldComment;
    }

    public void setFieldComment(String fieldComment) {
        this.fieldComment = fieldComment;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public int getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }
}

