package top.starp.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeUtil {

    public static String toDateStr(Date date, String format) {
//        Date date = new Date(); // 获取当前日期和时间

        // 创建SimpleDateFormat对象，指定日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        // 将Date对象格式化为时间字符串
        String dateString = dateFormat.format(date);
        return dateString;
    }

    public static String toDateStr(Date date) {
        return toDateStr(date, ymdhms);
//        Date date = new Date(); // 获取当前日期和时间

        // 创建SimpleDateFormat对象，指定日期格式
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        // 将Date对象格式化为时间字符串
//        String dateString = dateFormat.format(date);
//        return dateString;
    }

    public static LocalDate toLocalDate(Date date) {
//        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        System.out.println(localDate);
        return localDate;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
//        date 转化为localDate
        // 创建一个日期对象
//        Date date = new Date();

        // 将日期对象转换为本地日期时间
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDateTime;
//  时间是000 --00 可以去掉
    }

//    d(String  timeToken){
//        if ("暑假".equals(timeToken)) {
//            return
//        }
//    }

    public static List<LocalDateTime> thisSaturdayAndSunday() {
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();

        // 获取本周六的日期和时间
        LocalDateTime saturday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY))
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        // 获取本周日的日期和时间
//        LocalDateTime sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
//                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .withHour(23).withMinute(59).withSecond(59).withNano(59);

        // 打印本周六和本周日的日期和时间
//        System.out.println("本周六：" + saturday);
//        System.out.println("本周日：" + sunday);
        List<LocalDateTime> list = u.list(saturday, sunday);
        return list;
    }

//    today
public static List<LocalDateTime> today() {
    // 获取当前日期和时间
    LocalDateTime now = LocalDateTime.now();
    List<LocalDateTime> list = u.list(now.withHour(0).withMinute(0).withSecond(0)
            , now.withHour(23).withMinute(59).withSecond(59)
    );
    return list;
}

    public static List<LocalDateTime> todayToNow() {
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        List<LocalDateTime> list = u.list(now.withHour(0).withMinute(0).withSecond(0)
                , now
        );
        return list;
    }

    public static List<LocalDateTime> tomorrow() {
        LocalDateTime localDateTime = now();
//        LocalDateTime localDateTime = now().withHour(0).withMinute(0).withSecond(0);
//        localDateTime.se
        return  u.list(
                now().withHour(0).withMinute(0).withSecond(0).withDayOfMonth(localDateTime.getDayOfMonth()+1)
                , now().withHour(23).withMinute(59).withSecond(59).withDayOfMonth(localDateTime.getDayOfMonth()+1)
        );
    }


    // 后天
    public static List<LocalDateTime> afterTomorrow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterTomorrowStart = now.plusDays(2).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime afterTomorrowEnd = now.plusDays(2).withHour(23).withMinute(59).withSecond(59);
        return u.list(afterTomorrowStart, afterTomorrowEnd);
    }

    // 大后天
    public static List<LocalDateTime> afterAfterTomorrow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime afterAfterTomorrowStart = now.plusDays(3).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime afterAfterTomorrowEnd = now.plusDays(3).withHour(23).withMinute(59).withSecond(59);
        return u.list(afterAfterTomorrowStart, afterAfterTomorrowEnd);
    }

    // 昨天
    public static List<LocalDateTime> yesterday() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterdayStart = now.minusDays(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime yesterdayEnd = now.minusDays(1).withHour(23).withMinute(59).withSecond(59);
        return u.list(yesterdayStart, yesterdayEnd);
    }

    // 前天
    public static List<LocalDateTime> beforeYesterday() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime beforeYesterdayStart = now.minusDays(2).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime beforeYesterdayEnd = now.minusDays(2).withHour(23).withMinute(59).withSecond(59);
        return u.list(beforeYesterdayStart, beforeYesterdayEnd);
    }

    public static long strToTimestamp(String timeStr, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(timeStr);
        return date.getTime() / 1000;
    }

    // 前年
    public static List<LocalDateTime> beforeLastYear() {
        LocalDateTime now = LocalDateTime.now();
        long beforeLastYearMinus=2;
//        LocalDateTime beforeLastYearStart = now.minusYears(1).withHour(0).withMinute(0).withSecond(0);
//        LocalDateTime beforeLastYearEnd = now.minusYears(1).withHour(23).withMinute(59).withSecond(59);

        LocalDateTime beforeLastYearStart =
                now.minusYears(beforeLastYearMinus).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime beforeLastYearEnd =
                now.minusYears(beforeLastYearMinus).withHour(23).withMinute(59).withSecond(59);
        return u.list(beforeLastYearStart, beforeLastYearEnd);
    }

    // 上个月
    public static List<LocalDateTime> lastMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastMonthStart = now.minusMonths(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastMonthEnd = now.minusMonths(1).withHour(23).withMinute(59).withSecond(59);
        return u.list(lastMonthStart, lastMonthEnd);
    }
//    下个月
public static List<LocalDateTime> nextMonth() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime lastMonthStart = now.plusMonths(1).withHour(0).withMinute(0).withSecond(0);
    LocalDateTime lastMonthEnd = now.plusMonths(1).withHour(23).withMinute(59).withSecond(59);
    return u.list(lastMonthStart, lastMonthEnd);
}


    public static List<LocalDateTime> workdays() {
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();

        // 获取本周六的日期和时间
        LocalDateTime MONDAY = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
                .withHour(0).withMinute(0).withSecond(0).withNano(0);

        // 获取本周日的日期和时间
//        LocalDateTime sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
//                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime FRIDAY = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY))
//                .withHour(24)
                .withHour(23)
                .withMinute(59).withSecond(59).withNano(59);

        // 打印本周六和本周日的日期和时间
//        System.out.println("本周六：" + saturday);
//        System.out.println("本周日：" + sunday);
        List<LocalDateTime> list = u.list(MONDAY, FRIDAY);
        return list;
    }
//    workdays

//    public  static Map<String, Object> nameToTimesMap = u.map(
//            "暑假", u.list(
//                    LocalDateTime.now().withMonth(7),
//                    LocalDateTime.now().withMonth(9)
//            ),
//            "白天", u.list(
//                    LocalDateTime.now().withHour(6),
//                    LocalDateTime.now().withHour(6 + 12)
//            ),
//            "晚上", u.list(
//                    LocalDateTime.now().withHour(6 + 12),
//                    LocalDateTime.now().withHour(5)
//            ),
//            "半夜", u.list(
//                    LocalDateTime.now().withHour(0),
//                    LocalDateTime.now().withHour(5)
//            ),
//            "凌晨", u.list(
//                    LocalDateTime.now().withHour(5),
//                    LocalDateTime.now().withHour(6)
//            )
//            ,"今年",thisYearRange()
//    );

    public static Map<String, List<LocalDateTime>> nameToTimesMap = u.mapOf(
            u.p(
                    "暑假", u.list(
                            LocalDateTime.now().withMonth(7).withDayOfMonth(1),
                            LocalDateTime.now().withMonth(9).withDayOfMonth(1)
                    )
            )
            , u.p(
                    "白天", u.list(
                            LocalDateTime.now().withHour(6).withMinute(0),
                            LocalDateTime.now().withHour(6 + 12).withMinute(0)
                    )
            )
            , u.p(
                    "晚上", u.list(
                            LocalDateTime.now().withHour(6 + 12).withMinute(0),
                            LocalDateTime.now().withHour(5).withMinute(0)
                    )
            )
            , u.p(
                    "半夜", u.list(
                            LocalDateTime.now().withHour(0).withMinute(0),
                            LocalDateTime.now().withHour(5).withMinute(0)
                    )
            )
            , u.p(
                    "凌晨", u.list(
                            LocalDateTime.now().withHour(5).withMinute(0),
                            LocalDateTime.now().withHour(6).withMinute(0)
                    )
            )
            , u.p("今年", thisYearRange())
            , u.p("周末", thisSaturdayAndSunday())
            , u.p("工作日", workdays())
            , u.p("今天", today())
            , u.p("明天", tomorrow())
            , u.p("后天", afterTomorrow())
            , u.p("大后天", afterAfterTomorrow())
            , u.p("昨天", yesterday())
//            , u.p("昨天", minusDays(1))
            , u.p("前天", beforeYesterday())
            , u.p("上个月", lastMonth())
            , u.p("前年", beforeLastYear())
            , u.p("下个月", nextMonth())
            , u.p("下周", nextWeek())
            , u.p("上周", lastWeek())
            , u.p("前面一周", lastWeek())
            , u.p("前一周", lastWeek())
            , u.p("上个季度", lastQuarter())
            , u.p("上个年份",lastYear() )
            , u.p("去年",lastYear() )
            , u.p("下个年份",nextYear() )
            , u.p("三天内",minusDays3() )
            , u.p("两天内",minusDays(2) )
            , u.p("两天前",minusDays(2) )
//            谁出学校 两天前 那一天出去的 不是 两天内
            , u.p("七天内",minusDays(7 ))
            , u.p("今天内",todayToNow() )
//            , u.p("一周内",workdays11())
//            , u.p("这周",minusWeek(1) )
            , u.p("这周",thisWeek())
            , u.p("两周内",weekBefore(1) )
            , u.p("上上周",weekBefore(2) )
            , u.p("上个礼拜",weekBefore(1) )
            , u.p("上礼拜",weekBefore(1) )

            , u.p("上周",weekBefore(1) )
//            , u.p("两周内",minusWeek(2) )
            , u.p("2周前",minusWeek(2) )
            , u.p("两周前",minusWeek(2) )
//            , u.p("前两周",weekBefore(1) )
            , u.p("前两周",weekBefore(2) )
//            , u.p("前两周",minusWeek(2) )

//            上周：lastWeek()
//            下周：nextWeek()
//            上个星期一：lastMonday()
//            下个星期一：nextMonday()
//            上个星期日：lastSunday()
//            下个星期日：nextSunday()
//            上个季度：lastQuarter()
//            下个季度：nextQuarter()
//            上个年份：lastYear()
//            下个年份：nextYear()


    );

   static   List<LocalDateTime> thisWeek(){
     return   weekBefore(0);
//       today()
//        // 获取当前日期和时间
//        LocalDateTime now = LocalDateTime.now();
//
//        // 获取本周六的日期和时间
//        LocalDateTime monday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
//                .withHour(0).withMinute(0).withSecond(0).withNano(0);
//        List<LocalDateTime> list = u.list(monday, now);
//        return list;
    }

    static   List<LocalDateTime> weekBefore(long weekDiff){
//       today()
        // 获取当前日期和时间
        LocalDateTime now = LocalDateTime.now();
        // 获取本周六的日期和时间
        LocalDateTime monday = now
//                .with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY))
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .minusWeeks(weekDiff)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        List<LocalDateTime> list = u.list(monday, now);
        return list;
    }

//    public static List<LocalDateTime> nextMonth() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime nextMonthStart = now.plusMonths(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
//        LocalDateTime nextMonthEnd = now.plusMonths(2).withDayOfMonth(1).minusDays(1).withHour(23).withMinute(59).withSecond(59);
//        return list(nextMonthStart, nextMonthEnd);
//    }

    public static List<LocalDateTime> nextWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextWeekStart = now.plusWeeks(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime nextWeekEnd = now.plusWeeks(1).plusDays(6).withHour(23).withMinute(59).withSecond(59);
//        return list(nextWeekStart, nextWeekEnd);
        return u.list(nextWeekStart, nextWeekEnd);
    }

    public static List<LocalDateTime> lastWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastWeekStart = now.minusWeeks(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastWeekEnd = now.minusWeeks(1).plusDays(6).withHour(23).withMinute(59).withSecond(59);
        return u.list(lastWeekStart, lastWeekEnd);
    }

    public static List<LocalDateTime> previousWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime previousWeekStart = now.minusWeeks(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime previousWeekEnd = now.minusWeeks(1).plusDays(6).withHour(23).withMinute(59).withSecond(59);
        return u.list(previousWeekStart, previousWeekEnd);
    }

    public static List<LocalDateTime> lastQuarter() {
        LocalDateTime now = LocalDateTime.now();
        Month currentMonth = now.getMonth();
        int quarterStartMonthValue = ((currentMonth.getValue() - 1) / 3) * 3 + 1;
        LocalDateTime lastQuarterStart = now.withMonth(quarterStartMonthValue).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastQuarterEnd = lastQuarterStart.plusMonths(3).minusDays(1).withHour(23).withMinute(59).withSecond(59);
        return u.list(lastQuarterStart, lastQuarterEnd);
    }

    public static List<LocalDateTime> lastYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lastYearStart = now.minusYears(1).withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime lastYearEnd = lastYearStart.withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59);
        return list(lastYearStart, lastYearEnd);
    }

    public static List<LocalDateTime> nextYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextYearStart = now.plusYears(1).withMonth(1).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime nextYearEnd = nextYearStart.withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59);
        return list(nextYearStart, nextYearEnd);
    }

    public static List<LocalDateTime> minusDays3() {
       return minusDays(3);
//        LocalDateTime now = LocalDateTime.now();
//        return list( now.minusDays(3), now);
    }

    public static List<LocalDateTime> minusDays(long days ) {
        LocalDateTime now = LocalDateTime.now();
        return u.list( now.minusDays(days), now);
    }

    public static List<LocalDateTime> minusDaysStart0(long days ) {
        LocalDateTime now = LocalDateTime.now();
        return u.list(
                now.minusDays(days).withHour(0).withMinute(0).withSecond(0)
                , now);
    }
 public static String    toBetweenAndSql( List<LocalDateTime> localDateTimes){

       if(localDateTimes.size()==1){
              LocalDateTime localDateTime = localDateTimes.get(0);
              return "  = '{fromTime}' "
                     .replace("{fromTime}",    toMysqlCanDateStr(localDateTime))
                      ;

       }
     if (localDateTimes.size()<2) {
         log.info("localDateTimes");
         log.info(localDateTimes);
         Map<String, Object> map = u.mapOf(
                 u.p("localDateTimes.size()", localDateTimes.size())
                 , u.p("localDateTimes", localDateTimes)
         );
         log.error(map);
         return null;
//         throw new RuntimeException("localDateTimes.size()<2");
     }
       String tpl= "  BETWEEN '{fromTime}' AND '{toTime}' ";
      return tpl
                .replace("{fromTime}",    toMysqlCanDateStr(localDateTimes.get(0)))
                .replace("{toTime}",    toMysqlCanDateStr(localDateTimes.get(1)));
    }

    public static List<LocalDateTime> minusWeek(long weeks ) {
       return minusWeek(weeks,true);
    }
    public static List<LocalDateTime> minusWeek(long weeks,boolean start0 ) {

//        LocalDateTime now = LocalDateTime.now();
////        minusDays.
////        long weeks
////        now.minusWeeks(days)
////        DateTimeParams
//        return u.list(   now.minusWeeks(weeks), now);
        if(start0){
            return    minusWeekStart0(weeks);
        }
        return   minusWeekNotStart0(weeks);

    }

    public static List<LocalDateTime> minusWeekNotStart0(long weeks ) {
        LocalDateTime now = LocalDateTime.now();
//        now.adjustInto()
        return u.list(   now.minusWeeks(weeks), now);
    }

    public static List<LocalDateTime> minusWeekStart0(long weeks ) {
        LocalDateTime now = LocalDateTime.now();
        return u.list(   now.minusWeeks(weeks)
                .withHour(0).withMinute(0).withSecond(0), now);
    }
    public static List<LocalDateTime> list(LocalDateTime start, LocalDateTime end) {
        return u.list(start,end);
//        List<LocalDateTime> dates = new ArrayList<>();
//        LocalDateTime current = start;
//        while (!current.isAfter(end)) {
//            dates.add(current);
//            current = current.plusSeconds(1);
//        }
//        return dates;
    }
    public static List<LocalDateTime> thisYearRange() {

//	Map<String, List<LocalDateTime>> stringListMap = u.mapOf(
//			u.p(
//					"暑假", u.list(
//							LocalDateTime.now().withMonth(7),
//							LocalDateTime.now().withMonth(9)
//					)
//			)
//			,
//			u.p(
//					"暑假", u.list(
//							LocalDateTime.now().withMonth(7),
//							LocalDateTime.now().withMonth(9)
//					)
//			)
//
//	);
        List<LocalDateTime> list = u.list(
                now().withMonth(1).withDayOfYear(1),
                now().withMonth(12).withDayOfYear(365)
        );
        return list;
    }

    public static LocalDateTime now() {
        LocalDateTime now = LocalDateTime.now();
        return now;
    }


    public static List<LocalDateTime> getBySentence(String Sentence) {
        Set<String> strings = nameToTimesMap.keySet();
//        for (String string : strings) {
//
//        }

//        if (ListUtil.haveLike(strings,Sentence)) {
//
//        }
        String tag = ListUtil.getTag(strings, Sentence);
        if(tag==null){
            return null;
        }
        if (tag != null && tag.equals("年") && Sentence.contains("月")) {
            return null;
        }
//        Sentence.
//        StringUtils.in
        int idx = Sentence.indexOf(tag);

//        StringUtils.sub
//        String next = StringUtils.subString(Sentence, idx + 1, 1);
//        String next = StringUtils.subString(Sentence, idx + 1+ tag.length(), 1);
       int beginIdx= idx + tag.length();
        int length = Sentence.length();
        if(beginIdx>=length){
           Map<String, String> stringStringMap = u.mapOf(
                   u.p("Sentence", Sentence),
                   u.p("tag", tag)
                   ,  u.p(k.msg, "beginIdx>=Sentence.length()")
                   ,  u.p("beginIdx",""+beginIdx)
                   ,  u.p("Sentence.length",""+length)
           );
//           stringStringMap.toString()
//           throw new RuntimeException("beginIdx>=Sentence.length()");

//           log.error(stringStringMap);
           return null;
//           throw new RuntimeException(   StringUtils.getMapStr(stringStringMap));
       }
        String next = StringUtils.subString(Sentence, beginIdx, 1);
       if(next==null){
           return null;
       }
//        String next = Sentence.substring(idx + 1, 1);
        boolean nextIsBlank = u.list("的", " ").contains(next);
        if(nextIsBlank){
            List<LocalDateTime> byName = getByName(tag);
            return byName;
        }
//        if (tag != null &&!nextIsBlank) {
//            return null;
//        }
        return null;
//        List<LocalDateTime> byName = getByName(tag);
//        return byName;
//        ListUtil.haveLike(strings,Sentence)

    }

    public static List<LocalDateTime> getByName(String timeToken) {

//        u.map(
//                "暑假", u.list(
//                        LocalDateTime.now().withMonth(7),
//                        LocalDateTime.now().withMonth(9)
//                ),
//                "白天", u.list(
//                        LocalDateTime.now().withHour(6),
//                        LocalDateTime.now().withHour(6 + 12)
//                ),
//                "晚上", u.list(
//                        LocalDateTime.now().withHour(6 + 12),
//                        LocalDateTime.now().withHour(5)
//                ),
//                "半夜", u.list(
//                        LocalDateTime.now().withHour(0),
//                        LocalDateTime.now().withHour(5)
//                ),
//                "凌晨", u.list(
//                        LocalDateTime.now().withHour(5),
//                        LocalDateTime.now().withHour(6)
//                )
//                ,"今年",thisYearRange()
//        );


//		Map<Object, Object> map = u.map(
//				"暑假", u.list(
//						LocalDateTime.now().withMonth(7)
//						, LocalDateTime.now().withMonth(9)
//				)
//				, "白天", u.list(
//						LocalDateTime.now().withHour(6)
//						, LocalDateTime.now().withHour(6 + 12)
//				)
//		);


//		nameToTimesMap
        if (nameToTimesMap.containsKey(timeToken)) {
            return (List<LocalDateTime>) nameToTimesMap.get(timeToken);
        }
        return u.list();


//
////		白天的数
//        if ("暑假".equals(timeToken)) {
////			创建一个localDateTime  7月的
////			LocalDateTime.of(2023, Month.JULY, 1, 0, 0, 0)
////					,LocalDateTime.of(2023, Month.JULY, 1, 0, 0, 0)
//
//			List<LocalDateTime> list = u.list(
//					LocalDateTime.now().withMonth(7)
//					, LocalDateTime.now().withMonth(9)
//			);
//			return list;
//		}
//		if ("白天".equals(timeToken)) {
//			List<LocalDateTime> list = u.list(
//					LocalDateTime.now().withHour(6)
//					, LocalDateTime.now().withHour(6+12)
//			);
//			return list;
//		}
//		if ("晚上".equals(timeToken)) {
//			List<LocalDateTime> list = u.list(
//					LocalDateTime.now().withHour(6+12)
//					, LocalDateTime.now().withHour(5)
//			);
//			return list;
//		}
//		if ("半夜".equals(timeToken)) {
//			List<LocalDateTime> list = u.list(
//					LocalDateTime.now().withHour(0)
//					, LocalDateTime.now().withHour(5)
//			);
//			return list;
//		}
//		if ("凌晨".equals(timeToken)) {
//			List<LocalDateTime> list = u.list(
//					LocalDateTime.now().withHour(5)
//					, LocalDateTime.now().withHour(6)
//			);
//			return list;
//		}
//		return  u.list();


    }

    //    = "我要2022/10/2到2023/11/3的数据"
    public static List<Date> parseDate(String input) throws ParseException {

// 创建一个列表来存储解析出的日期
        List<Date> dates = new ArrayList<>();
// 创建一个日期格式化对象，指定日期的格式为yyyy/MM/dd
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
// 创建一个正则表达式对象，匹配yyyy/MM/dd格式的日期
        Pattern p = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}");
// 创建一个字符串，存储输入的文本
//        String input = "我要2022/10/2到2023/11/3的数据";
// 创建一个匹配器，用正则表达式在输入文本中查找日期
        Matcher m = p.matcher(input);
// 循环遍历匹配到的日期
        while (m.find()) {
            // 获取匹配到的日期字符串
            String dateStr = m.group();
            // 将日期字符串转换为日期对象
            Date date = sdf.parse(dateStr);
            // 将日期对象添加到列表中
            dates.add(date);
        }
// 打印列表中的日期
//        System.out.println(dates);
        return dates;

    }


    /**
     * String text = "我要2022-10-2到2023-11-3的数据";
     * List<LocalDate> dates = extractDates(text);
     * fPattern("yyyy-MM-d");
     *
     * @param text
     * @return
     */
    public static List<LocalDate> extractDates(String text) {
//        test
        List<LocalDate> dates = new ArrayList<>();
        String regex = "\\d{4}-\\d{1,2}-\\d{1,2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // 查找匹配的日期字符串
        while (matcher.find()) {
            String dateString = matcher.group();

            // 使用日期解析器解析日期字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
            LocalDate date = LocalDate.parse(dateString, formatter);

            dates.add(date);
        }

        return dates;
    }

    public static List<LocalDateTime> extractDateTimes(String text) {
        List<LocalDateTime> dates = new ArrayList<>();
        String regex = "\\d{4}-\\d{1,2}-\\d{1,2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        // 查找匹配的日期字符串
        while (matcher.find()) {
            String dateString = matcher.group();

            // 使用日期解析器解析日期字符串
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime date = LocalDateTime.parse(dateString, formatter);

            dates.add(date);
        }

        return dates;
    }


    public static void adjustedDatePrevOldTime(List<LocalDate> dates) {
        log.info("dates from");
        log.info(dates);
//        Collections.reverse(dates);
        for (int i = dates.size() - 1; i > 0; i--) {
            LocalDate currentDate = dates.get(i);
//            LocalDate nextDate = dates.get(i + 1);
            LocalDate prevDate = dates.get(i - 1);
            if (!currentDate.isAfter(prevDate)) {
                int year = currentDate.getYear() - 1;
//                int year = prevDate.getYear() - 1;
//                LocalDate adjustedDate = LocalDate.of(year, currentDate.getMonth(), currentDate.getDayOfMonth());
                LocalDate adjustedDate = LocalDate.of(year, prevDate.getMonth(), prevDate.getDayOfMonth());
                dates.set(i - 1, adjustedDate);
            }
        }
//        Collections.reverse(dates);
        log.info("dates to");
        log.info(dates);
    }

    public static void adjustedDateTimePrevOldTime(List<LocalDateTime> dates) {
//        log.info("dates from");
//        log.info(dates);

        for (int i = dates.size() - 1; i > 0; i--) {
            LocalDateTime currentDate = dates.get(i);
            LocalDateTime prevDate = dates.get(i - 1);

            if (!currentDate.isAfter(prevDate)) {
                int year = currentDate.getYear() - 1;
                LocalDateTime adjustedDate = LocalDateTime.of(year, prevDate.getMonth(), prevDate.getDayOfMonth(),
                        prevDate.getHour(), prevDate.getMinute(), prevDate.getSecond());
                dates.set(i - 1, adjustedDate);
            }
        }

//        log.info("dates to");
//        log.info(dates);
    }


    public static void adjustedDateTime(List<LocalDateTime> dates, DateTimeParams dateTimeParams) {
//        log.info("dates from");
//        log.info(dates);

        for (int i = dates.size() - 1; i > 0; i--) {
            LocalDateTime currentDate = dates.get(i);
            LocalDateTime prevDate = dates.get(i - 1);
            Month month = prevDate.getMonth();
//            month.minus()
            if (!currentDate.isAfter(prevDate)) {
                int year = currentDate.getYear() - 1;
                LocalDateTime adjustedDate = LocalDateTime.of(
                        currentDate.getYear() - dateTimeParams.getYear()
                        , month.minus(dateTimeParams.getMonth())
//                        prevDate.getMonth()-dateTimeParams.getMonth()
                        , prevDate.getDayOfMonth() - dateTimeParams.getDay()
                        , prevDate.getHour() - dateTimeParams.getHour()
                        , prevDate.getMinute() - dateTimeParams.getMinute()
                        , prevDate.getSecond() - dateTimeParams.getSecond()
                );
                dates.set(i - 1, adjustedDate);
            }
        }

//        log.info("dates to");
//        log.info(dates);
    }

    public static void main要十二号(String[] args) {
        String input = "我要十二号到二号的数据";
        List<LocalDate> dates = extractDatesDay(input);
//        List<LocalDate> dates = extractDatesDayRetDateTimes(input);
        for (LocalDate date : dates) {
            System.out.println(date);
        }
    }

    public static void main要十二号RetDateTimes(String[] args) {
        String input = "我要十二号到二号的数据";
//        List<LocalDate> dates = extractDatesDay(input);
        List<LocalDateTime> dates = extractDatesDayRetDateTimes(input);
        log.info("input  " + input);
        log.info("dates");
        log.info(dates);
//        LocalDateTime from to 转为 sql mysql
//        for (LocalDate date : dates) {
//            System.out.println(date);
//        }
    }

    public static void mainToMysql(String[] args) {
        LocalDateTime localDateTime = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = localDateTime.format(formatter);

        // 将formattedDateTime插入到MySQL数据库中
        // INSERT INTO table_name (datetime_column) VALUES ('formattedDateTime');

        System.out.println("Formatted DateTime: " + formattedDateTime);
    }

    public static List<LocalDateTime> getDateTimes(String input) {

//        String input = "我要十二号到二号的数据";
//        extractDatesDay()
//        log.info("input  "+input);
//        List<LocalDate> localDates = extractDates(input);
        List<LocalDateTime> localDateTimes1 = extractDateTimes(input);
        if (!localDateTimes1.isEmpty()) {
            return localDateTimes1;
        }
//        log.info("localDateTimes1");
//        log.info(localDateTimes1);
        List<LocalDateTime> localDateTimes = extractDatesDayRetDateTimes(input);
//        log.info("localDateTimes");
//        log.info(localDateTimes);
        if (!localDateTimes.isEmpty()) {
            return localDateTimes;
        }
//       List<LocalDate> localDates = extractDatesMonthZh(input);
        List<LocalDateTime> localDateTimes2 = extractDateTimesMonthZh(input);
        if (!localDateTimes2.isEmpty()) {
            return localDateTimes2;
        }
//       List<LocalTime> localTimes = extractTimes(input);
//       if (!localTimes.isEmpty()) {
//           return localTimes;
//       }
//       extractTimes
//       parseChineseMonth()
//       y
        return new ArrayList<>();
    }


    private static List<LocalTime> extractTimes(String input) {
        List<LocalTime> times = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+)点");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String hour = matcher.group(1);
            int hourNumber = Integer.parseInt(hour);

            if (hourNumber >= 0 && hourNumber <= 23) {
                LocalTime time = LocalTime.of(hourNumber, 0);
                times.add(time);
            }
        }

        return times;
    }

    /**
     * input  我要十二号到二号的数据
     * localDates
     * Disconnected from the target VM, address: 'localhost:55518', transport: 'socket'
     * Time: 2023_07_13_11_07_34   List(len=0):   []
     * localDateTimes
     * Time: 2023_07_13_11_07_35   List(len=2):   [2023-07-12T11:07:35.008, 2023-07-02T11:07:35.013]
     * Formatted DateTime: 2023-07-13 11:07:35
     *
     * @param args
     */
    public static void main(String[] args) {


//
//        String input = "我要十二号到二号的数据";
//        String input = "我要两点到十二点的数据";
        String input = "我要2点到12点的数据";
//        String input = "我要4月到7月的数据";

        List<LocalDateTime> dateTimes = getDateTimes(input);
//        extractDatesDay()
        log.info("input  " + input);

        log.info("dateTimes");
        log.info(dateTimes);


//
//        List<LocalDate> localDates = extractDates(input);
//        log.info("localDates");
//        log.info(localDates);
//        List<LocalDateTime> localDateTimes = extractDatesDayRetDateTimes(input);
//        log.info("localDateTimes");
//        log.info(localDateTimes);
////        main要十二号(args);
////        main要十二号RetDateTimes(args);
//        mainToMysql(args);


    }

    static String toMysqlCanDateStr(LocalDateTime fromDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fromDateString = fromDate.format(formatter);
//        String toDateString = toDate.format(formatter);

        return fromDateString;
    }

    static LocalDateTime getDateTimeWithDay(String dateStr) {
        String dateStrJustNumber = dateStr.replace("号", "");
        BigDecimal bigDecimal = CNToNumber.chineseNumToArabicNum(dateStrJustNumber);
//        LocalDate 传入日期 年月 都是
//        LocalDateTime 传入日期,  其他 年月,时间 都是now
//        LocalDate now = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
//        bigDecimal.toBigInteger();
//        bigDecimal.to
//        bigDecimal 转化为int
//        ObjectUtil.toInt(bigDecimal);
//        bigDecimal.intValue()
        LocalDateTime localDateTime = now.withDayOfMonth(
//                Integer.valueOf(bigDecimal)
                ObjectUtil.toInt(bigDecimal)
        );
        return localDateTime;
//        now.at()
//        LocalDate date = LocalDate.parse(dateStr, formatter);
    }

    @Deprecated
    private static List<LocalDate> extractDatesDay(String input) {
        List<LocalDate> dates = new ArrayList<>();

        Pattern pattern = Pattern.compile("[一二三四五六七八九十零百千万亿]+号");
        Matcher matcher = pattern.matcher(input);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月d号");

        while (matcher.find()) {
            String dateStr = matcher.group();
            LocalDateTime dateTimeWithDay = getDateTimeWithDay(dateStr);

            dateStr = CNToNumber.chineseNumToArabicNum(dateStr).toString() + "号";
//            dateStr = convertChineseNumberToArabic(dateStr);
            LocalDate date = LocalDate.parse(dateStr, formatter);
            dates.add(date);
        }

        return dates;
    }

    /**
     * String input = "我要十二号到二号的数据";
     * List<LocalDateTime> dates = extractDatesDayRetDateTimes(input);
     * log.info("input  "+input);
     * log.info("dates");
     * log.info(dates);
     * Time: 2023_07_13_10_39_11    dates
     * Time: 2023_07_13_10_39_11   List(len=2):   [2023-07-12T10:39:11.035, 2023-07-02T10:39:11.036]
     *
     * @param input
     * @return
     */
    private static List<LocalDateTime> extractDatesDayRetDateTimes(String input) {
        List<LocalDateTime> dates = new ArrayList<>();

        Pattern pattern = Pattern.compile("[一二三四五六七八九十零百千万亿]+号");
        Matcher matcher = pattern.matcher(input);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月d号");

        while (matcher.find()) {
            String dateStr = matcher.group();
            LocalDateTime dateTimeWithDay = getDateTimeWithDay(dateStr);

//            dateStr =   CNToNumber.chineseNumToArabicNum(dateStr).toString() +"号" ;
//            dateStr = convertChineseNumberToArabic(dateStr);
//            LocalDate date = LocalDate.parse(dateStr, formatter);
            dates.add(dateTimeWithDay);
        }

        return dates;
    }

    public static void main_convertChineseNumberToArabic_test(String[] args) {
        String zhNumber = "十二";
        String albaNumber = convertChineseNumberToArabic("十二");
        u.map(k.albaNumber, albaNumber
                , k.zhNumber, zhNumber);
//        StringUtils.getMapStr()
//        log.info(albaNumber);
        log.info(u.map(k.albaNumber, albaNumber
                , k.zhNumber, zhNumber));
//        Time: 2023_07_13_10_15_07    {zhNumber=十二, albaNumber=102}
    }


    /**
     * String zhNumber="十二";
     * String albaNumber = convertChineseNumberToArabic("十二");
     * u.map(k.albaNumber,albaNumber
     * ,k.zhNumber,zhNumber);
     * log.info(   u.map(k.albaNumber,albaNumber
     * ,k.zhNumber,zhNumber));
     * //        Time: 2023_07_13_10_15_07    {zhNumber=十二, albaNumber=102}
     *
     * @param chineseNumber
     * @return
     */
    private static String convertChineseNumberToArabic(String chineseNumber) {
        chineseNumber = chineseNumber.replace("一", "1")
                .replace("二", "2")
                .replace("三", "3")
                .replace("四", "4")
                .replace("五", "5")
                .replace("六", "6")
                .replace("七", "7")
                .replace("八", "8")
                .replace("九", "9")
                .replace("十", "10")
                .replace("零", "0")
                .replace("百", "00")
                .replace("千", "000")
                .replace("万", "0000")
                .replace("亿", "00000000");
        return chineseNumber;
    }

    public static void mainMonthGet(String[] args) {
        String input = "我要十二月到二月的数据";

//        String input = "我要12月到2月的数据";
//        List<LocalDate> dates = extractDates(input);
//        List<LocalDate> dates = extractDatesMonth(input);
        List<LocalDate> dates = extractDatesMonthZh(input);
        /**
         *  List<LocalDate> dates  列表 时间是  [2023-12-01, 2023-02-01]  现在用最后一个作为基准 前面的日期要比后面的小
         *  2023-02-01 前面的 2023-12-01 要改为 2022-12-01 , 每个的月份不变,如果前面的日期不满足小的话 要年份-1
         * Time: 2023_07_12_17_53_47    dates
         * Time: 2023_07_12_17_53_47   List(len=2):   [2023-12-01, 2023-02-01]
         */

        log.info("dates");
        log.info(dates);
//        for (LocalDate date : dates) {
//            System.out.println(date);
//        }
    }

    /**
     * String input = "我要十二月到二月的数据";
     * List<LocalDate> dates= extractDatesMonthZh(input);
     * Time: 2023_07_12_19_44_57    input  我要十二月到二月的数据
     * Time: 2023_07_12_19_44_57    dates
     * Time: 2023_07_12_19_44_57   List(len=2):   [2023-12-01, 2023-02-01]
     *
     * @param input
     * @return
     */
    private static List<LocalDate> extractDatesMonthZh(String input) {
        List<LocalDate> dates = new ArrayList<>();
        log.info("input  " + input);
//        log.info(input);

        Pattern pattern = Pattern.compile("([一二三四五六七八九十]+)月");
        Matcher matcher = pattern.matcher(input);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月");

        while (matcher.find()) {
            String month = matcher.group(1);

            if (month != null) {
                int monthNumber = parseChineseMonth(month);

                // 可以选择使用当前年份来构建 LocalDate 对象，也可以根据实际需求自行调整
                LocalDate date = LocalDate.of(LocalDate.now().getYear(), monthNumber, 1);

                dates.add(date);
            }
        }

        return dates;
    }


    private static List<LocalDateTime> extractDateTimesMonthZh(String input) {
        List<LocalDateTime> dates = new ArrayList<>();
        log.info("input  " + input);

        Pattern pattern = Pattern.compile("([一二三四五六七八九十]+)月");
        Matcher matcher = pattern.matcher(input);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月");

        while (matcher.find()) {
            String month = matcher.group(1);

            if (month != null) {
                int monthNumber = parseChineseMonth(month);

                // 可以选择使用当前年份来构建 LocalDate 对象，也可以根据实际需求自行调整
                LocalDate date = LocalDate.of(LocalDate.now().getYear(), monthNumber, 1);
                LocalDateTime dateTime = date.atStartOfDay();

                dates.add(dateTime);
            }
        }

        return dates;
    }

    /**
     * switch (month) {
     * case "一":
     * return 1;
     *
     * @param month
     * @return
     */
    private static int parseChineseMonth(String month) {
        switch (month) {
            case "一":
                return 1;
            case "二":
                return 2;
            case "三":
                return 3;
            case "四":
                return 4;
            case "五":
                return 5;
            case "六":
                return 6;
            case "七":
                return 7;
            case "八":
                return 8;
            case "九":
                return 9;
            case "十":
                return 10;
            case "十一":
                return 11;
            case "十二":
                return 12;
            default:
                throw new IllegalArgumentException("Invalid month: " + month);
        }
    }

    /**
     * String input = "我要12月到2月的数据";
     * //        List<LocalDate> dates = extractDates(input);
     * List<LocalDate> dates = extractDatesMonth(input);
     * <p>
     * Time: 2023_07_12_17_53_47    dates
     * * Time: 2023_07_12_17_53_47   List(len=2):   [2023-12-01, 2023-02-01]
     *
     * @param input
     * @return
     */
    private static List<LocalDate> extractDatesMonth(String input) {
        List<LocalDate> dates = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d+)*月");
        Matcher matcher = pattern.matcher(input);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月");

        while (matcher.find()) {
            String month = matcher.group(1);

            if (month != null) {
                int monthNumber = Integer.parseInt(month);

                // 可以选择使用当前年份来构建 LocalDate 对象，也可以根据实际需求自行调整
                LocalDate date = LocalDate.of(LocalDate.now().getYear(), monthNumber, 1);

                dates.add(date);
            }
        }

        return dates;
    }

    public static void main月(String[] args) {
        String input = "我要10月到11月的数据";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M月");
        List<LocalDate> dates = new ArrayList<>();
        String[] parts = input.split("到");
        for (String part : parts) {
            String month = part.replaceAll("[^0-9]", "");
            LocalDate date = LocalDate.parse(month, formatter);
            dates.add(date);
        }
        System.out.println(dates);
    }

    public static void main2(String[] args) {
        try {
//            String input = "我要2022/10/2到2023/11/3的数据";
//            [Sun Oct 02 00:00:00 CST 2022, Fri Nov 03 00:00:00 CST 2023]

//            我要10月到12月的数据 提取出其中的10月 这些日期 返回 list java代码
            List<Date> dates = parseDate("我要2022/10/2到2023/11/3的数据");
            log.info("dates");
            log.info(dates);
            /**
             * Time: 2023_07_12_17_03_46    dates
             * Time: 2023_07_12_17_03_46   List(len=2):   [Sun Oct 02 00:00:00 CST 2022, Fri Nov 03 00:00:00 CST 2023]
             */

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void mainGang(String[] args) {


        String text = "我要2022-10-2到2023-11-3的数据";
        List<LocalDate> dates = extractDates(text);

        // 输出解析到的日期
        for (LocalDate date : dates) {
//            System.out.println(date);
            log.info("date " + date);
//            date.get(TemporalField)
//            LocalDate 获取 时间
            int dayOfMonth = date.getDayOfMonth();
            DayOfWeek dayOfWeek = date.getDayOfWeek();
//            dayOfWeek.getDisplayName()
            log.info("dayOfMonth  " + dayOfMonth);
            log.info("dayOfWeek  " + dayOfWeek);
        }

//        LocalDateTime

//        2022-10-02
//        2023-11-03
    }


    public static String ymdHengGang = "yyyy-MM-dd";

    public static List<Date> parseDates(String sentence) {
        Pattern pattern = Pattern.compile("(\\d{4})年(\\d{1,2})月(\\d{1,2})日");
        Matcher matcher = pattern.matcher(sentence);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        List<Date> dates = new ArrayList<>();
        while (matcher.find()) {
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);

            String dateStr = year + "-" + month + "-" + day;

            try {
                Date date = dateFormat.parse(dateStr);
                dates.add(date);
//                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return dates;
    }

    public static Boolean isAfter(String dateString, String compareDate) {
//        String dateString = "20030908";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
//String compareDate= "20190101";
        try {
            Date date = dateFormat.parse(dateString);
            Date comparisonDate = dateFormat.parse(compareDate);
            boolean after = date.after(comparisonDate);
            return after;
//            if (date.after(comparisonDate)) {
//                System.out.println("日期在2019年之后");
//            } else {
//                System.out.println("日期在2019年之前或等于2019年");
//            }
        } catch (ParseException e) {
            System.out.println("日期解析错误：" + e.getMessage());
            return null;
        }
    }

    public static Date intStrToDate(String currentEntryTime) {
        return new Date(
                Integer.parseInt(currentEntryTime));
    }

    public static long getTimeDifferenceOfIntStr(String startTime, String endTime) {
        long timeDifference = TimeUtil.getTimeDifference(
                intStrToDate(startTime)
                , intStrToDate(endTime)
        );
        return timeDifference;

    }

    public static long getTimeDifference(Date dateEarly, Date dateLater) {
        // 创建两个 Date 对象
//        Date date1 = new Date(); // 第一个 Date 对象
//        Date date2 = new Date(); // 第二个 Date 对象

        // 创建两个 Calendar 对象，并将其时间设置为要比较的 Date 对象的时间
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dateEarly);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(dateLater);

        // 获取两个 Calendar 对象的小时数
        int hour1 = cal1.get(Calendar.HOUR_OF_DAY);
        int hour2 = cal2.get(Calendar.HOUR_OF_DAY);

        // 计算小时差
        int hourDifference = hour2 - hour1;

        return hourDifference;
//        System.out.println("Hour difference: " + hourDifference);
    }

    public static long getTimeDifference(String startTime, String endTime) {
        // Assuming startTime and endTime are in the format "yyyy-MM-dd HH:mm:ss"
        try {
            java.time.LocalDateTime startDateTime = java.time.LocalDateTime.parse(startTime);
            java.time.LocalDateTime endDateTime = java.time.LocalDateTime.parse(endTime);

            return java.time.Duration.between(startDateTime, endDateTime).toHours();
        } catch (java.time.format.DateTimeParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

//    public static void main(String[] args) {
//        LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
//        String formattedTime = currentTime.format(formatter);
//        System.out.println(formattedTime);
//    }

//    public static  String   nowTimeStr(){
//        LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
//        String formattedTime = currentTime.format(formatter);
////        System.out.println(formattedTime);
//        return  formattedTime;
//    }

    public static String nowTimeStr() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedTime = currentTime.format(formatter);
//        System.out.println(formattedTime);
        return formattedTime;
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
    public static final String ymdHeng = "yyyy-MM-dd";
    public static final String ymdLeftSlash = "yyyy/MM/dd";

    // 格式：年－月－日
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    public static final String Y = "yyyy";
    public static final String YM = "yyyy-MM";
    public static final String YMChCaesura = "yyyy、MM";
    //    顿号
    public static final String YMChDot = "yyyy．MM";
    public static final String[] FORMAT_LIST = {ymdLeftSlash, LONG_DATE_FORMAT,
            ymd, ymdhms, ymdhms2, ymChShort, ymCh, YMChCaesura,
            ymdDot, ymDot, YMChDot, YM, yCh, ymNoSplit,};

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
