package top.starp.util;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

//public class LogUtil {
//    void info(List<T>list){
//
//    }
//}

public class LogUtil {

    public static void info(boolean logInfo) {
//        System.out.println("Time: " +  TimeUtil.nowTimeStr());
        System.out.println("Time: "+new Date());
        String logInfoZh=  logInfo?"是":"否";
//        System.out.println(logInfo+" :"+logInfoZh);
        String logData = "{logInfo} ({logInfoZh})"
                .replace("{logInfo}", logInfo + "")
                .replace("{logInfoZh}", logInfoZh);
        System.out.println( logData);

//        System.out.println(logInfoZh);
    }
    public static void info(List list) {
        if(list==null){
            System.out.println("list is null");
            return;
        }
        int maxLength = 10; // Maximum length of the list to display
//        TimeUtil.get
        String logData ="Time: " +  TimeUtil.nowTimeStr();
//                System.out.println("Time: " +  TimeUtil.nowTimeStr());
//        System.out.println("Method: info");

        System.out.print(logData+"   ");
        if (list.size() > maxLength) {
//            logData+="  List (truncated to " + maxLength + " elements): " + list.subList(0, maxLength)+"\n";
//            logData+= "List length: " + list.size();
            System.out.println("List (truncated to " + maxLength + " elements): " + list.subList(0, maxLength));
            System.out.println("List length: " + list.size());
        } else {
            System.out.println("List: " + list);
            System.out.println("List length: " + list.size());
        }
    }
    public static void info(Map map) {
        log.info(map);
    }
    public static void info(String str) {
        print(str, System.out);
//        System.out.println();


////        int maxLength = 10; //  Maximum length of the list to display
//        int maxLength = 50; //  Maximum length of the list to display
////        TimeUtil.get
//
//        System.out.println("Time: " +  TimeUtil.nowTimeStr());
////        System.out.println("Method: info");
//        if (str.length() > maxLength) {
//            System.out.println(str.substring(0,maxLength));
//        } else {
//            System.out.println(str);
//        }
    }

//    void d(){
////        public static final PrintStream err;
//    }

    public static void print(String str,PrintStream printStream) {
        print(str,printStream,100);
//        int maxLength = 10; //  Maximum length of the list to display
//        int maxLength = 50; //  Maximum length of the list to display
//        TimeUtil.get

//        printStream.print("Time: " +  TimeUtil.nowTimeStr()+"    ");
////        System.out.println("Method: info");
//        if (str.length() > maxLength) {
//            printStream.println(str.substring(0,maxLength));
//        } else {
//            printStream.println(str);
//        }
    }
    public static void print(String str,PrintStream printStream,int maxLength) {

//        int maxLength = 10; //  Maximum length of the list to display
//        int maxLength = 50; //  Maximum length of the list to display
//        TimeUtil.get

        printStream.print("Time: " +  TimeUtil.nowTimeStr()+"    ");
//        System.out.println("Method: info");
        if (str.length() > maxLength) {
            printStream.println(str.substring(0,maxLength));
        } else {
            printStream.println(str);
        }
    }

    public static void printMap(Map<?, ?> map, int maxLength,PrintStream printStream) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            String key = entry.getKey().toString();
//            if(entry==null){
//                throw new FrontMonitorException("entyry null "+entry);
//            }
            Object entryValue = entry.getValue();
            String value;
            if(entryValue==null){
                value ="null";
//                throw new FrontMonitorException("entryValue null  entry  "+entry);
            }else{
                value =entryValue.toString();
            }


            if (value.length() > maxLength) {
                value = value.substring(0, maxLength) + "...";
            }
            printStream.println(key + ": " + value);
//            System.out.println(key + ": " + value);
        }
    }
    public static void error(Map map) {
//        StringUtils.printMap(str);
        printMap(map,100,System.err);
//        print(str, System.err);
    }
    public static void error(String str) {
//        System.out.println();
        print(str,System.err);

////        int maxLength = 10; //  Maximum length of the list to display
//        int maxLength = 50; //  Maximum length of the list to display
////        TimeUtil.get
//
//        System.err.println("Time: " +  TimeUtil.nowTimeStr());
////        System.out.println("Method: info");
//        if (str.length() > maxLength) {
//            System.err.println(str.substring(0,maxLength));
//        } else {
//            System.err.println(str);
//        }
    }
    public static String getCurrentTime() {
        // Implement your logic to get the current time as a string
        return "";
    }
}
