package com.example.demo.util.codeGen;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableInfoTest {

    @Test
    void genIViewEdit() throws Exception {

        TableInfo tableInfo = new TableInfo();
//        tableInfo.se
//        String genFindFirstRows = tableInfo.genFindFirstRows();
//        System.out.println(genFindFirstRows);

        String s = tableInfo.genIViewEdit();
        System.out.println(s);
    }

    @Test
    void genCsv() throws Exception {

        TableInfo tableInfo = new TableInfo();
//        tableInfo.se
//        String genFindFirstRows = tableInfo.genFindFirstRows();
//        System.out.println(genFindFirstRows);

        String s = tableInfo.genCsv();
        System.out.println(s);
    }
}
