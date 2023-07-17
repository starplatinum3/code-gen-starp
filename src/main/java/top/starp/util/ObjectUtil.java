package top.starp.util;

import java.math.BigDecimal;

public class ObjectUtil {
  public static int  toInt( BigDecimal bigDecimal){
        int i = bigDecimal.intValue();
        return i;
    }
    public static boolean equals(BigDecimal expectedValue, BigDecimal actualValue) {
//        BigDecimal expectedValue =   BigDecimal.valueOf(number);
//        BigDecimal actualValue =  CNToNumber.chineseNumToArabicNum(chineseNumber);
        return expectedValue.compareTo(actualValue) == 0;
    }

    /**
     * Time: 2023_07_13_10_07_59      boolean equals = ObjectUtil.equals(
     * BigDecimal.ONE
     * , BigDecimal.valueOf(233)
     * );equals : false
     *
     * @param args
     */
    public static void main(String[] args) {
        boolean equals = ObjectUtil.equals(
                BigDecimal.ONE
                , BigDecimal.valueOf(233)
        );
        log.info("  boolean equals = ObjectUtil.equals(\n" +
                "                BigDecimal.ONE\n" +
                "                , BigDecimal.valueOf(233)\n" +
                "        );" +
                "" +
                "equals : " + equals);
    }
}
