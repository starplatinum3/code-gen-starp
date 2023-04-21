package com.example.demo.util;

import static org.junit.jupiter.api.Assertions.*;

class StrUtilTest {
    public static void main(String[] args) {
        String s = StrUtil.frontDelStr("t_1_t_2", "t_");
        System.out.println(s);
//        1_t
//java 删除 前面 不要的字符串
    }

}
