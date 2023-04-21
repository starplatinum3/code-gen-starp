package com.example.demo.util;

import java.util.Random;

public class NameCreator {
    public static String createName() {
        String line = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦" +
                "章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞仁袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅" +
                "皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜" +
                "阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟徐邱骆高夏蔡田樊胡";
        Random random = new Random();
        StringBuilder name = new StringBuilder(line.charAt(random.nextInt(line.length())) + "");
        for (int i = 1 + random.nextInt(2); i > 0; i--) {
            name.append(line.charAt(random.nextInt(line.length())));
        }
        return name.toString();
    }

    public static void main(String[] args) {
        String name = createName();
        System.out.println(name);
//        明章
//        吕康
//        窦蓝康
//                杨闵,乐华董,水纪,萧范,秦伍,汤雷,邵华明,卞马邱,水薛,禹褚,
        for (int i = 0; i <10 ; i++) {
            final String name1 = NameCreator.createName();
            System.out.print(name1);
            System.out.print(",");
        }
    }
}
//}
//————————————————
//版权声明：本文为CSDN博主「Wan_Nian」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/Wan_Nian/article/details/126946461
