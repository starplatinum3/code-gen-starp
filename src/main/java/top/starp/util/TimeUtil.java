package top.starp.util;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static  String   nowTimeStr(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedTime = currentTime.format(formatter);
//        System.out.println(formattedTime);
        return  formattedTime;
    }

    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    public static final String ymdhms = "yyyy-MM-dd HH:mm:ss";

    // 格式：年－月－日 小时：分钟
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";
    public static final String ymdhm = "yyyy-MM-dd HH:mm";
    public static final String ymdhmFile = "yyyy-MM-dd-HH-mm-ss";

    // 格式：年月日 小时分钟秒
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";
    public static final String ymdhms2 = "yyyyMMdd-HHmmss";
    //    public static final String ymCh = "yyyy年MM月dd-HHmmss";
    public static final String ymNoSplit = "yyyyMM";
    public static final String ymdCh = "yyyy年MM月dd日";
    public static final String yCh = "yyyy年";
    public static final String ymCh = "yyyy年MM月";
    public static final String ymChShort = "yyyy年M月";
    public static final String hmsCh = "HH时mm分ss秒";
    public static final String allCh = ymdCh + "-" + hmsCh;
    public static final String ymdDot = "yyyy.MM.dd";
    public static final String ymDot = "yyyy.MM";

    // 格式：年月日
    public static final String FORMAT_FOUR = "yyyyMMdd";
    public static final String ymd = "yyyyMMdd";
    public static final String ymdLeftSlash = "yyyy/MM/dd";

    // 格式：年－月－日
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    public static final String Y = "yyyy";
    public static final String YM = "yyyy-MM";
    public static final String YMChCaesura = "yyyy、MM";
    //    顿号
    public static final String YMChDot = "yyyy．MM";
    public static final String[] FORMAT_LIST = {ymdLeftSlash,LONG_DATE_FORMAT,
            ymd, ymdhms, ymdhms2, ymChShort,ymCh,YMChCaesura,
            ymdDot, ymDot,YMChDot, YM,yCh, ymNoSplit,};

    /**
     * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @return
     */
    public static String getNowTimeStr() {
        Calendar today = Calendar.getInstance();
//        return dateToString(today.getTime(), FORMAT_ONE);
        return dateToString(today.getTime(), ymdhms);
    }

    /**
     * 把日期转换为字符串
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // log.error(e);
        }
        return result;
    }
}
