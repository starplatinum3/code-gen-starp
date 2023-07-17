/**
 * Copyright (c) 2016 21CN.COM . All rights reserved.<br>
 * <p>
 * Description: TimeNLP<br>
 * <p>
 * Modified log:<br>
 * ------------------------------------------------------<br>
 * Ver.		Date		Author			Description<br>
 * ------------------------------------------------------<br>
 * 1.0		2016年5月4日		kexm		created.<br>
 */
package com.time.util;

import java.net.URISyntaxException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.time.nlp.TimeUnit;
import org.junit.Test;

import com.time.nlp.TimeNormalizer;
import top.starp.util.ListUtil;
import top.starp.util.TimeUtil;
import top.starp.util.log;
import top.starp.util.u;

/**
 * <p>
 * 测试类
 * <p>
 * @author <a href="mailto:kexm@corp.21cn.com">kexm</a>
 * @version 1.0
 * @since 2016年5月4日
 *
 */
public class TimeAnalyseTest {

    public static Map<String, Object> nameToTimesMap = u.map(
            "暑假", u.list(
                    LocalDateTime.now().withMonth(7),
                    LocalDateTime.now().withMonth(9)
            ),
            "白天", u.list(
                    LocalDateTime.now().withHour(6),
                    LocalDateTime.now().withHour(6 + 12)
            ),
            "晚上", u.list(
                    LocalDateTime.now().withHour(6 + 12),
                    LocalDateTime.now().withHour(5)
            ),
            "半夜", u.list(
                    LocalDateTime.now().withHour(0),
                    LocalDateTime.now().withHour(5)
            ),
            "凌晨", u.list(
                    LocalDateTime.now().withHour(5),
                    LocalDateTime.now().withHour(6)
            )
            , "今年", thisYearRange()
    );


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

    /**
     * Time: 2023_07_14_15_42_49    暑假
     * Time: 2023_07_14_15_42_49   List: [2023-07-14T15:42:49.831, 2023-09-14T15:42:49.831]
     * List length: 2
     * @param args
     */
    public static void main(String[] args) {
        List<LocalDateTime> 暑假 = getByName("暑假");
        log.info("暑假");
        log.info(暑假);
    }

    public static List<LocalDateTime> getByName(String timeToken) {
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

    static List<LocalDateTime> toLocalDateTimes(TimeUnit[] times) {
        List<LocalDateTime> collect = Arrays.stream(times).map(
                timeUnit -> TimeUtil.toLocalDateTime(timeUnit.getTime())).collect(Collectors.toList());
        return collect;

//		for (TimeUnit time : times) {
//			LocalDateTime localDateTime = TimeUtil.toLocalDateTime(time.getTime());
//		}
    }

    void testOne(String ask, TimeNormalizer normalizer) {

        log.info("ask  " + ask);

//        TimeUtil.getByName(ask)
        /**
         * 今年 关键词 但是 后面有月啊
         * Time: 2023_07_14_17_13_59    ask  我需要今年7月14号 9点30分到8月32号10点20分的数据
         * Time: 2023_07_14_17_13_59   List: [2023-01-01T17:13:59.275, 2023-12-31T17:13:59.275]
         */
//        TimeUtil.adjustedDateTimePrevOldTime();
        List<LocalDateTime> bySentence = TimeUtil.getBySentence(ask);
        if (bySentence!=null&&!bySentence.isEmpty()) {
            log.info(bySentence);
            return;
        }

//		"Hi，all.下周一下午三点开会"
//		normalizer.parse("Hi，all.下周一下午三点开会");// 抽取时间
        normalizer.parse(ask);// 抽取时间
        TimeUnit[] times = normalizer.getTimeUnit();
//		TimeUtil.to
//		date 转化为localDatetime java

//		TimeUtil.adjustedDateTimePrevOldTime();
        List<LocalDateTime> localDateTimes = toLocalDateTimes(times);
        TimeUtil.adjustedDateTimePrevOldTime(localDateTimes);

//        for (LocalDateTime localDateTime : localDateTimes) {
//            log.info(localDateTimes);
//        }


        for (TimeUnit time : times) {
//            time.timePoint
//            Boolean isAllDayTime = time.getIsAllDayTime();
            String infoStr = DateUtil.formatDateDefault(time.getTime()) + "  -  getIsAllDayTime : "
                    + time.getIsAllDayTime();
            log.info(infoStr);
//			DateUtil.formatDateDefault(times[0].getTime()) + "  -  getIsAllDayTime : " + times[0].getIsAllDayTime()
        }

        log.info(localDateTimes);



//		log.info(DateUtil.formatDateDefault(times[0].getTime()) + "  -  getIsAllDayTime : " + times[0].getIsAllDayTime());
//
//
//		System.out.println("Hi，all.下周一下午三点开会");
//		System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "  -  getIsAllDayTime : " + unit[0].getIsAllDayTime());
//
    }

    @Test
    public void testTimeNLP() throws URISyntaxException {
        URL url = TimeNormalizer.class.getResource("/TimeExp.m");
//        System.out.println("url.toURI().toString()");
//        System.out.println(url.toURI().toString());
        TimeNormalizer normalizer = new TimeNormalizer(url.toURI().toString());
        normalizer.setPreferFuture(true);

        /**
         * Hi，all.下周一下午三点开会
         * 2023-07-17 15:00:00-false
         */
//		normalizer.parse("Hi，all.下周一下午三点开会");// 抽取时间
//        TimeUnit[] unit = normalizer.getTimeUnit();
//        System.out.println("Hi，all.下周一下午三点开会");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "  -  getIsAllDayTime : " + unit[0].getIsAllDayTime());
////

        /**
         * Time: 2023_07_14_14_46_42    ask  我需要6点到8点的数据
         * Time: 2023_07_14_14_46_42    2023-07-15 06:00:00  -  getIsAllDayTime : false
         *
         * Time: 2023_07_14_14_48_37    ask  我需要6点到8点的数据
         * Time: 2023_07_14_14_48_37    2023-07-15 06:00:00  -  getIsAllDayTime : false
         * Time: 2023_07_14_14_48_37    2023-07-15 08:00:00  -  getIsAllDayTime : false
         *
         *
         * Time: 2023_07_14_14_51_02    ask  我需要暑假的数据  ---- 问题
         * Time: 2023_07_14_14_51_02    ask  我需要7月到9月的数据
         * Time: 2023_07_14_14_51_02    2023-07-01 00:00:00  -  getIsAllDayTime : true
         * Time: 2023_07_14_14_51_02    2023-09-01 00:00:00  -  getIsAllDayTime : true
         * Time: 2023_07_14_14_51_02    ask  我需要4点到9点的数据
         * Time: 2023_07_14_14_51_02    2023-07-15 04:00:00  -  getIsAllDayTime : false
         * Time: 2023_07_14_14_51_02    2023-07-15 09:00:00  -  getIsAllDayTime : false
         * Time: 2023_07_14_14_51_02    ask  我需要4点到下午9点的数据
         * Time: 2023_07_14_14_51_02    2023-07-15 04:00:00  -  getIsAllDayTime : false
         * Time: 2023_07_14_14_51_02    2023-07-15 21:00:00  -  getIsAllDayTime : false
         * Time: 2023_07_14_14_51_02    ask  我需要白天的数据  问题
         *
         */
//		testOne("我需要6点到8点的数据",normalizer);
//		testOne("我需要暑假的数据",normalizer);
//		testOne("我需要7月到9月的数据",normalizer);
//		testOne("我需要4点到9点的数据",normalizer);
//		testOne("我需要4点到下午9点的数据",normalizer);
//		testOne("我需要白天的数据",normalizer);
//		testOne("我需要今年的数据",normalizer);
//		testOne("我需要今年7月14号 9点30分到8月32号10点20分的数据",normalizer);
////

        testOne(  "我需要晚上6点到凌晨2点的数据",normalizer);

        /**
         * Time: 2023_07_17_11_05_21    ask  凌晨2点
         * Time: 2023_07_17_11_05_21    2023-07-18 02:00:00  -  getIsAllDayTime : false
         */
        testOne(  "凌晨2点",normalizer);
//		ArrayList<String> strings = Lists.newArrayList("");
//		Lists.newList
        List<String> list = ListUtil.createList(
//                "我需要6点到8点的数据",
                "我需要暑假的数据",
//                "我需要7月到9月的数据",
//                "我需要4点到9点的数据",
//                "我需要4点到下午9点的数据",
                "我需要白天的数据",
                "我需要今年的数据",
//                "我需要今年7月14号 9点30分到8月31号10点20分的数据",
//                "我需要早上6点到晚上8点的数据",
//                "我需要暑假期间的数据",
//                "我需要7月份到9月份的数据",
//                "我需要下午4点到晚上9点的数据",
//                "我需要下午4点到晚上9点的数据",
//                "我需要白天的数据",
//                "我需要今年的数据",
//                "我需要今年7月14日9点30分到8月31日10点20分的数据",
//                "我需要从早上7点到下午3点的数据",
                "我需要周末的数据",
//                "我需要10月份到12月份的数据",
                "我需要晚上6点到凌晨2点的数据",
                "我需要星期二到星期五的数据",
                "我需要工作日的数据",
//                "我需要今年1月1日到今年12月31日的数据",
//                "我需要2023年5月15日9点到2024年1月1日12点的数据",
//                "我需要上午10点到下午2点的数据",
                "我需要今天的数据",
                "我需要明天的数据",
                "我需要下个月的数据",
                "我需要下周的数据",
                "我需要周一上午9点到周五下午5点的数据"
        );

        for (String s : list) {
            testOne(s, normalizer);
        }

//		文档：Time 2023_07_14_15_04_50    ask  我?..
//		链接：http://note.youdao.com/noteshare?id=fce327b70bb1a4acf9bbebc5eb9edd85&sub=CA20D55FB903420FA75F4DA2CF3434E5


        /**
         * Time: 2023_07_14_14_59_44    ask  我需要今年的数据
         * Time: 2023_07_14_14_59_44    2023-01-01 00:00:00  -  getIsAllDayTime : true
         * Time: 2023_07_14_14_59_44    ask  我需要今年7月14号 9点30分到8月32号10点20分的数据
         * Time: 2023_07_14_14_59_44    2023-07-14 09:30:00  -  getIsAllDayTime : false
         * Time: 2023_07_14_14_59_44    2023-09-01 10:20:00  -  getIsAllDayTime : false
         */
        testOne("我需要今年的数据", normalizer);
        testOne("我需要今年7月14号 9点30分到8月32号10点20分的数据", normalizer);

//        normalizer.parse("早上六点起床");// 注意此处识别到6天在今天已经过去，自动识别为明早六点（未来倾向，可通过开关关闭：new TimeNormalizer(classPath+"/TimeExp.m", false)）
//        unit = normalizer.getTimeUnit();
//        System.out.println("早上六点起床");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("周一开会");// 如果本周已经是周二，识别为下周周一。同理处理各级时间。（未来倾向）
//        unit = normalizer.getTimeUnit();
//        System.out.println("周一开会");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("下下周一开会");//对于上/下的识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("下下周一开会");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("6:30 起床");// 严格时间格式的识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("6:30 起床");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("6-3 春游");// 严格时间格式的识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("6-3 春游");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("6月3春游");// 残缺时间的识别 （打字输入时可便捷用户）
//        unit = normalizer.getTimeUnit();
//        System.out.println("6月3春游");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("明天早上跑步");// 模糊时间范围识别（可在RangeTimeEnum中修改
//        unit = normalizer.getTimeUnit();
//        System.out.println("明天早上跑步");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//
//        normalizer.parse("本周日到下周日出差");// 多时间识别
//        unit = normalizer.getTimeUnit();
//        System.out.println("本周日到下周日出差");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());
//
//        normalizer.parse("周四下午三点到五点开会");// 多时间识别，注意第二个时间点用了第一个时间的上文
//        unit = normalizer.getTimeUnit();
//        System.out.println("周四下午三点到五点开会");
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
//        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());
//
//        //新闻随机抽取长句识别（2016年6月7日新闻,均以当日0点为基准时间计算）
//        //例1
//        normalizer.parse("7月 10日晚上7 点左右，六安市公安局裕安分局平桥派出所接到辖区居民戴某报警称，到同学家玩耍的女儿迟迟未归，手机也打不通了。很快，派出所又接到与戴某同住一小区的王女士报警：下午5点左右，12岁的儿子和同学在家中吃过晚饭后，"
//        		+ "带着3 岁的弟弟一起出了门，之后便没了消息，手机也关机了。短时间内，接到两起孩子失联的报警，值班民警张晖和队友立即前往小区。", "2016-07-19-00-00-00");
//        unit = normalizer.getTimeUnit();
//        System.out.println("7月 10日晚上7 点左右，六安市公安局裕安分局平桥派出所接到辖区居民戴某报警称，到同学家玩耍的女儿迟迟未归，手机也打不通了。很快，派出所又接到与戴某同住一小区的王女士报警：下午5点左右，12岁的儿子和同学在家中吃过晚饭后，"
//        		+ "带着3 岁的弟弟一起出了门，之后便没了消息，手机也关机了。短时间内，接到两起孩子失联的报警，值班民警张晖和队友立即前往小区。");
//        for(int i = 0; i < unit.length; i++){
//            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
//        }
//
//        //例2
//        normalizer.parse("《辽宁日报》今日报道，7月18日辽宁召开省委常委扩大会，会议从下午两点半开到六点半，主要议题为：落实中央巡视整改要求。", "2016-07-19-00-00-00");
//        unit = normalizer.getTimeUnit();
//        System.out.println("《辽宁日报》今日报道，7月18日辽宁召开省委常委扩大会，会议从下午两点半开到六点半，主要议题为：落实中央巡视整改要求。");
//        for(int i = 0; i < unit.length; i++){
//            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
//        }

        //例3
//        normalizer.parse("周五下午7点到8点", "2017-07-19-00-00-00");
//        unit = normalizer.getTimeUnit();
//        System.out.println("周五下午7点到8点");
//        for(int i = 0; i < unit.length; i++){
//            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
//        }

    }


    /**
     * 修改TimeExp.m文件的内容
     */
    @Test
    public void editTimeExp() {
        String path = TimeNormalizer.class.getResource("").getPath();
        String classPath = path.substring(0, path.indexOf("/com/time"));
        System.out.println(classPath + "/TimeExp.m");
        /**写TimeExp*/
        Pattern p = Pattern.compile("((前|昨|今|明|后)(天|日)?(早|晚)(晨|上|间)?)|(\\d+个?[年月日天][以之]?[前后])|(\\d+个?半?(小时|钟头|h|H))|(半个?(小时|钟头))|(\\d+(分钟|min))|([13]刻钟)|((上|这|本|下)+(周|星期)([一二三四五六七天日]|[1-7])?)|((周|星期)([一二三四五六七天日]|[1-7]))|((早|晚)?([0-2]?[0-9](点|时)半)(am|AM|pm|PM)?)|((早|晚)?(\\d+[:：]\\d+([:：]\\d+)*)\\s*(am|AM|pm|PM)?)|((早|晚)?([0-2]?[0-9](点|时)[13一三]刻)(am|AM|pm|PM)?)|((早|晚)?(\\d+[时点](\\d+)?分?(\\d+秒?)?)\\s*(am|AM|pm|PM)?)|(大+(前|后)天)|(([零一二三四五六七八九十百千万]+|\\d+)世)|([0-9]?[0-9]?[0-9]{2}\\.((10)|(11)|(12)|([1-9]))\\.((?<!\\\\d))([0-3][0-9]|[1-9]))|(现在)|(届时)|(这个月)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)日)|(晚些时候)|(今年)|(长期)|(以前)|(过去)|(时期)|(时代)|(当时)|(近来)|(([零一二三四五六七八九十百千万]+|\\d+)夜)|(当前)|(日(数|多|多少|好几|几|差不多|近|前|后|上|左右))|((\\d+)点)|(今年([零一二三四五六七八九十百千万]+|\\d+))|(\\d+[:：]\\d+(分|))|((\\d+):(\\d+))|(\\d+/\\d+/\\d+)|(未来)|((充满美丽、希望、挑战的)?未来)|(最近)|(早上)|(早(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(日前)|(新世纪)|(小时)|(([0-3][0-9]|[1-9])(日|号))|(明天)|(\\d+)月|(([0-3][0-9]|[1-9])[日号])|((数|多|多少|好几|几|差不多|近|前|后|上|左右)周)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)([零一二三四五六七八九十百千万]+|\\d+)年)|(每[年月日天小时分秒钟]+)|((\\d+分)+(\\d+秒)?)|([一二三四五六七八九十]+来?[岁年])|([新?|\\d*]世纪末?)|((\\d+)时)|(世纪)|(([零一二三四五六七八九十百千万]+|\\d+)岁)|(今年)|([星期周]+[一二三四五六七])|(星期([零一二三四五六七八九十百千万]+|\\d+))|(([零一二三四五六七八九十百千万]+|\\d+)年)|([本后昨当新后明今去前那这][一二三四五六七八九十]?[年月日天])|(早|早晨|早上|上午|中午|午后|下午|晚上|晚间|夜里|夜|凌晨|深夜)|(回归前后)|((\\d+点)+(\\d+分)?(\\d+秒)?左右?)|((\\d+)年代)|(本月(\\d+))|(第(\\d+)天)|((\\d+)岁)|((\\d+)年(\\d+)月)|([去今明]?[年月](底|末))|(([零一二三四五六七八九十百千万]+|\\d+)世纪)|(昨天(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(年度)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)星期)|(年底)|([下个本]+赛季)|(\\d+)月(\\d+)日|(\\d+)月(\\d+)|(今年(\\d+)月(\\d+)日)|((\\d+)月(\\d+)日(数|多|多少|好几|几|差不多|近|前|后|上|左右)午(\\d+)时)|(今年晚些时候)|(两个星期)|(过去(数|多|多少|好几|几|差不多|近|前|后|上|左右)周)|(本赛季)|(半个(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(稍晚)|((\\d+)号晚(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(今(数|多|多少|好几|几|差不多|近|前|后|上|左右)(\\d+)年)|(这个时候)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)个小时)|(最(数|多|多少|好几|几|差不多|近|前|后|上|左右)(数|多|多少|好几|几|差不多|近|前|后|上|左右)年)|(凌晨)|((\\d+)年(\\d+)月(\\d+)日)|((\\d+)个月)|(今天早(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(第[一二三四五六七八九十\\d+]+季)|(当地时间)|(今(数|多|多少|好几|几|差不多|近|前|后|上|左右)([零一二三四五六七八九十百千万]+|\\d+)年)|(早晨)|(一段时间)|([本上]周[一二三四五六七])|(凌晨(\\d+)点)|(去年(\\d+)月(\\d+)日)|(年关)|(如今)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)小时)|(当晚)|((\\d+)日晚(\\d+)时)|(([零一二三四五六七八九十百千万]+|\\d+)(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(每年(\\d+)月(\\d+)日)|((\\d+)月)|(农历)|(两个小时)|(本周([零一二三四五六七八九十百千万]+|\\d+))|(长久)|(清晨)|((\\d+)号晚)|(春节)|(星期日)|(圣诞)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)段)|(现年)|(当日)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)分钟)|(\\d+(天|日|周|月|年)(后|前))|((文艺复兴|巴洛克|前苏联|前一|暴力和专制|成年时期|古罗马|我们所处的敏感)+时期)|((\\d+)[年月天])|(清早)|(两年)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(昨天(数|多|多少|好几|几|差不多|近|前|后|上|左右)午(\\d+)时)|(([零一二三四五六七八九十百千万]+|\\d+)(数|多|多少|好几|几|差不多|近|前|后|上|左右)年)|(今(数|多|多少|好几|几|差不多|近|前|后|上|左右)(\\d+))|(圣诞节)|(学期)|(\\d+来?分钟)|(过去(数|多|多少|好几|几|差不多|近|前|后|上|左右)年)|(星期天)|(夜间)|((\\d+)日凌晨)|(([零一二三四五六七八九十百千万]+|\\d+)月底)|(当天)|((\\d+)日)|(((10)|(11)|(12)|([1-9]))月)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)(数|多|多少|好几|几|差不多|近|前|后|上|左右)年)|(今年(\\d+)月份)|(晚(数|多|多少|好几|几|差不多|近|前|后|上|左右)(\\d+)时)|(连[年月日夜])|((\\d+)年(\\d+)月(\\d+)日(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|((一|二|两|三|四|五|六|七|八|九|十|百|千|万|几|多|上|\\d+)+个?(天|日|周|月|年)(后|前|半))|((胜利的)日子)|(青春期)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)年)|(早(数|多|多少|好几|几|差不多|近|前|后|上|左右)([零一二三四五六七八九十百千万]+|\\d+)点(数|多|多少|好几|几|差不多|近|前|后|上|左右))|([0-9]{4}年)|(周末)|(([零一二三四五六七八九十百千万]+|\\d+)个(数|多|多少|好几|几|差不多|近|前|后|上|左右)小时)|(([(小学)|初中?|高中?|大学?|研][一二三四五六七八九十]?(\\d+)?)?[上下]半?学期)|(([零一二三四五六七八九十百千万]+|\\d+)时期)|(午间)|(次年)|(这时候)|(农历新年)|([春夏秋冬](天|季))|((\\d+)天)|(元宵节)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)分)|((\\d+)月(\\d+)日(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(晚(数|多|多少|好几|几|差不多|近|前|后|上|左右)(\\d+)时(\\d+)分)|(傍晚)|(周([零一二三四五六七八九十百千万]+|\\d+))|((数|多|多少|好几|几|差不多|近|前|后|上|左右)午(\\d+)时(\\d+)分)|(同日)|((\\d+)年(\\d+)月底)|((\\d+)分钟)|((\\d+)世纪)|(冬季)|(国庆)|(年代)|(([零一二三四五六七八九十百千万]+|\\d+)年半)|(今年年底)|(新年)|(本周)|(当地时间星期([零一二三四五六七八九十百千万]+|\\d+))|(([零一二三四五六七八九十百千万]+|\\d+)(数|多|多少|好几|几|差不多|近|前|后|上|左右)岁)|(半小时)|(每周)|((重要|最后)?时刻)|(([零一二三四五六七八九十百千万]+|\\d+)期间)|(周日)|(晚(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(今后)|(([零一二三四五六七八九十百千万]+|\\d+)段时间)|(明年)|([12][09][0-9]{2}(年度?))|(([零一二三四五六七八九十百千万]+|\\d+)生)|(今天凌晨)|(过去(\\d+)年)|(元月)|((\\d+)月(\\d+)日凌晨)|([前去今明后新]+年)|(\\d+)月(\\d+)(日?)|(夏天)|((\\d+)日凌晨(\\d+)时许)|((\\d+)月(\\d+)日)|((\\d+)点半)|(去年底)|(最后一[天刻])|(最(数|多|多少|好几|几|差不多|近|前|后|上|左右)(数|多|多少|好几|几|差不多|近|前|后|上|左右)个月)|(圣诞节?)|(下?个?(星期|周)(一|二|三|四|五|六|七|天))|((\\d+)(数|多|多少|好几|几|差不多|近|前|后|上|左右)年)|(当天(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(每年的(\\d+)月(\\d+)日)|((\\d+)日晚(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(星期([零一二三四五六七八九十百千万]+|\\d+)晚)|(深夜)|(现如今)|([上中下]+午)|(昨晚)|(近年)|(今天清晨)|(中旬)|(星期([零一二三四五六七八九十百千万]+|\\d+)早)|(([零一二三四五六七八九十百千万]+|\\d+)战期间)|(星期)|(昨天晚(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(较早时)|(个(数|多|多少|好几|几|差不多|近|前|后|上|左右)小时)|((民主高中|我们所处的|复仇主义和其它危害人类的灾难性疾病盛行的|快速承包电影主权的|恢复自我美德|人类审美力基础设施|饱受暴力、野蛮、流血、仇恨、嫉妒的|童年|艰苦的童年)+时代)|(元旦)|(([零一二三四五六七八九十百千万]+|\\d+)个礼拜)|(昨日)|([年月]初)|((\\d+)年的(\\d+)月)|(每年)|(([零一二三四五六七八九十百千万]+|\\d+)月份)|(今年(\\d+)月(\\d+)号)|(今年([零一二三四五六七八九十百千万]+|\\d+)月)|((\\d+)月底)|(未来(\\d+)年)|(第([零一二三四五六七八九十百千万]+|\\d+)季)|(\\d?多年)|(([零一二三四五六七八九十百千万]+|\\d+)个星期)|((\\d+)年([零一二三四五六七八九十百千万]+|\\d+)月)|([下上中]午)|(早(数|多|多少|好几|几|差不多|近|前|后|上|左右)(\\d+)点)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)月)|(([零一二三四五六七八九十百千万]+|\\d+)个(数|多|多少|好几|几|差不多|近|前|后|上|左右)月)|(同([零一二三四五六七八九十百千万]+|\\d+)天)|((\\d+)号凌晨)|(夜里)|(两个(数|多|多少|好几|几|差不多|近|前|后|上|左右)小时)|(昨天)|(罗马时代)|(目(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(([零一二三四五六七八九十百千万]+|\\d+)月)|((\\d+)年(\\d+)月(\\d+)号)|(((10)|(11)|(12)|([1-9]))月份?)|([12][0-9]世纪)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)([零一二三四五六七八九十百千万]+|\\d+)天)|(工作日)|(稍后)|((\\d+)号(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(未来([零一二三四五六七八九十百千万]+|\\d+)年)|(([零一二三四五六七八九十百千万]+|\\d+)日(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(最(数|多|多少|好几|几|差不多|近|前|后|上|左右)([零一二三四五六七八九十百千万]+|\\d+)刻)|(很久)|((\\d+)(数|多|多少|好几|几|差不多|近|前|后|上|左右)岁)|(去年(\\d+)月(\\d+)号)|(两个月)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)午(\\d+)时)|(古代)|(两天)|(\\d+个?(小时|星期))|((\\d+)年半)|(较早)|(([零一二三四五六七八九十百千万]+|\\d+)个小时)|(星期([零一二三四五六七八九十百千万]+|\\d+)(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(时刻)|((\\d+天)+(\\d+点)?(\\d+分)?(\\d+秒)?)|((\\d+)日([零一二三四五六七八九十百千万]+|\\d+)时)|(([零一二三四五六七八九十百千万]+|\\d+)早)|(([零一二三四五六七八九十百千万]+|\\d+)日)|(去年(\\d+)月)|(过去([零一二三四五六七八九十百千万]+|\\d+)年)|((\\d+)个星期)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)(数|多|多少|好几|几|差不多|近|前|后|上|左右)天)|(执政期间)|([当前昨今明后春夏秋冬]+天)|(去年(\\d+)月份)|(今(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(两星期)|(([零一二三四五六七八九十百千万]+|\\d+)年代)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)天)|(昔日)|(两个半月)|([印尼|北京|美国]?当地时间)|(连日)|(本月(\\d+)日)|(第([零一二三四五六七八九十百千万]+|\\d+)天)|((\\d+)点(\\d+)分)|([长近多]年)|((\\d+)日(数|多|多少|好几|几|差不多|近|前|后|上|左右)午(\\d+)时)|(那时)|(冷战时代)|(([零一二三四五六七八九十百千万]+|\\d+)天)|(这个星期)|(去年)|(昨天傍晚)|(近期)|(星期([零一二三四五六七八九十百千万]+|\\d+)早些时候)|((\\d+)([零一二三四五六七八九十百千万]+|\\d+)年)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)两个月)|((\\d+)个小时)|(([零一二三四五六七八九十百千万]+|\\d+)个月)|(当年)|(本月)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)([零一二三四五六七八九十百千万]+|\\d+)个月)|((\\d+)点(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(目前)|(去年([零一二三四五六七八九十百千万]+|\\d+)月)|((\\d+)时(\\d+)分)|(每月)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)段时间)|((\\d+)日晚)|(早(数|多|多少|好几|几|差不多|近|前|后|上|左右)(\\d+)点(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(下旬)|((\\d+)月份)|(逐年)|(稍(数|多|多少|好几|几|差不多|近|前|后|上|左右))|((\\d+)年)|(月底)|(这个月)|((\\d+)年(\\d+)个月)|(\\d+大寿)|(周([零一二三四五六七八九十百千万]+|\\d+)早(数|多|多少|好几|几|差不多|近|前|后|上|左右))|(半年)|(今日)|(末日)|(昨天深夜)|(今年(\\d+)月)|((\\d+)月(\\d+)号)|((\\d+)日夜)|((早些|某个|晚间|本星期早些|前些)+时候)|(同年)|((北京|那个|更长的|最终冲突的)时间)|(每个月)|(一早)|((\\d+)来?[岁年])|((数|多|多少|好几|几|差不多|近|前|后|上|左右)个月)|([鼠牛虎兔龙蛇马羊猴鸡狗猪]年)|(季度)|(早些时候)|(今天)|(每天)|(年半)|(下(个)?月)|(午后)|((\\d+)日(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|((数|多|多少|好几|几|差不多|近|前|后|上|左右)个星期)|(今天(数|多|多少|好几|几|差不多|近|前|后|上|左右)午)|(同[一二三四五六七八九十][年|月|天])|(T\\d+:\\d+:\\d+)|(\\d+/\\d+/\\d+:\\d+:\\d+.\\d+)|(\\?\\?\\?\\?-\\?\\?-\\?\\?T\\d+:\\d+:\\d+)|(\\d+-\\d+-\\d+T\\d+:\\d+:\\d+)|(\\d+/\\d+/\\d+ \\d+:\\d+:\\d+.\\d+)|(\\d+-\\d+-\\d+|[0-9]{8})|(((\\d+)年)?((10)|(11)|(12)|([1-9]))月(\\d+))|((\\d[\\.\\-])?((10)|(11)|(12)|([1-9]))[\\.\\-](\\d+))");
        try {
            TimeNormalizer.writeModel(p, classPath + "/TimeExp.m");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRegex() {
        String[] rs = new String[]{
                "同[一二三四五六七八九十][年|月|天]"
        };
        for (String r : rs) {
            Pattern p = Pattern.compile(r);
            String s = "二周年";
            Matcher m = p.matcher(s);
//			System.out.println(m.find());
            if (m.find()) {
                System.out.println(r);
            }
        }

    }
}
