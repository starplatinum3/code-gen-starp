//package top.starp.util;
//
//import com.huaban.analysis.jieba.JiebaSegmenter;
//import com.huaban.analysis.jieba.SegToken;
//
//public class MovieNameExtractor {
//    public static String getMovieName(String text, String userdictPath) {
//        String movieName = "";
//        JiebaSegmenter segmenter = new JiebaSegmenter();
//        segmenter.loadUserDict(userdictPath);
//        System.out.println("text");
//        System.out.println(text);
//        Iterable<SegToken> tokens = segmenter.process(text, JiebaSegmenter.SegMode.SEARCH);
//        for (SegToken token : tokens) {
//            if (token.word.flag.equals("lqy")) {
//                movieName = token.word.word;
//            }
//        }
//        return movieName;
//    }
//
//    public static void main(String[] args) {
//        String userdictPath = "D:\\i-brain\\job\\job_title\\jobTitle_selfDefiningTxt.txt";
//        String text = "用户输入内容";
//        String movieName = getMovieName(text, userdictPath);
//        System.out.println(movieName);
//    }
//}
