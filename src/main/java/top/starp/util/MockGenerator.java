//package top.starp.util;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//public class MockGenerator {
//
//    private static final Map<String, MockValueGenerator> GENERATOR_MAP = new HashMap<>();
//
//    static {
//        GENERATOR_MAP.put("questionId", MockValueGenerator::generateMockInteger);
//        // GENERATOR_MAP.put("subject", MockValueGenerator::generateMockString);
//        GENERATOR_MAP.put("subject", MockValueGenerator::generateMockSubject);
//        GENERATOR_MAP.put("question", MockValueGenerator::generateMockString);
//        GENERATOR_MAP.put("answer", MockValueGenerator::generateMockString);
//        GENERATOR_MAP.put("analysis", MockValueGenerator::generateMockString);
//        // GENERATOR_MAP.put("score", MockValueGenerator::generateMockInteger);
//        GENERATOR_MAP.put("score", MockValueGenerator::generateMockScore);
//        // GENERATOR_MAP.put("level", MockValueGenerator::generateMockString);
//        GENERATOR_MAP.put("level", MockValueGenerator::generateMockLevel);
//        GENERATOR_MAP.put("section", MockValueGenerator::generateMockString);
//        // add more mappings here
//    }
//
//    public static Object genMock(String colName) {
//        MockValueGenerator generator = GENERATOR_MAP.get(colName);
//        if (generator != null) {
//            return generator.generate();
//        }
//        // handle unknown column name
//        return null;
//    }
//
//    private static class MockValueGenerator {
//
//        private static final Random RANDOM = new Random();
//
//        static Integer generateMockInteger() {
//            return RANDOM.nextInt(100);
//        }
//
//        static String generateMockString() {
//            return "mock" + RANDOM.nextInt(100);
//        }
//
//        // add more mock value generators here
//
//        static Object generateMockObject() {
//            return new Object(); // default mock value generator
//        }
//
//        static Object generate() {
//            return generateMockObject();
//        }
//
//        // 对于subject字段，您可以定义一个可选的学科列表，然后从列表中随机选择一个值作为mock值。例如：
//
//        // arduino
//        // Copy code
//        private static final String[] SUBJECT_LIST = { "数学", "语文", "英语", "物理", "化学", "生物" };
//
//        static String generateMockSubject() {
//            return SUBJECT_LIST[RANDOM.nextInt(SUBJECT_LIST.length)];
//        }
//        // 对于score字段，您可以定义一个最小值和最大值，然后在这个范围内生成一个随机的分数。例如：
//
//        // arduino
//        // Copy code
//        private static final int MIN_SCORE = 0;
//        private static final int MAX_SCORE = 100;
//
//        static Integer generateMockScore() {
//            return MIN_SCORE + RANDOM.nextInt(MAX_SCORE - MIN_SCORE + 1);
//        }
//        // 对于level字段，您可以定义一个可选的级别列表，然后从列表中随机选择一个值作为mock值。例如：
//
//        // arduino
//        // Copy code
//        private static final String[] LEVEL_LIST = { "小学一年级", "小学二年级", "小学三年级", "小学四年级", "小学五年级", "小学六年级", "初中一年级",
//                "初中二年级", "初中三年级", "高中一年级", "高中二年级", "高中三年级", "本科", "研究生" };
//
//        static String generateMockLevel() {
//            return LEVEL_LIST[RANDOM.nextInt(LEVEL_LIST.length)];
//        }
//        // 通过这种方式，您可以根据字段的业务含义为每个字段生成有意义的mock值。
//    }
//}
