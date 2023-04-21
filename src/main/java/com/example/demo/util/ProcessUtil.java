package com.example.demo.util;

import java.io.IOException;

public class ProcessUtil {


    public static void main(String[] args) throws IOException, InterruptedException {
//        ProcessUtil.shutdown("30");
        ProcessUtil.shutdown(30);
        Thread.sleep(1000);
        ProcessUtil.cancelShutdown();
    }
    public static void cancelShutdown() throws IOException {
        //取消自动关机
//        Cancel automatic shutdown
        Runtime.getRuntime().exec("shutdown -a");
        System.out.println("已取消自动关机");

    }

    public static void shutdown(int sec) throws IOException {

        Runtime.getRuntime().exec("shutdown -s -t " + sec);
        System.out.println("已启动自动关机系统，系统将在 " + sec + " 秒后关机");
//————————————————
//        版权声明：本文为CSDN博主「BublesLee」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/HJSN_RCTT/article/details/110957449
    }

    /**
     * 1、提示用户，欢迎使用某某 关机程序
     * 2、提示用户，输入倒计时关机的秒数
     * 3、根据用户输入的秒数，进行自动关机
     * 4、开始自动关机时，提示用户xxx秒后关机
     * */
    public static void shutdown(String sec) throws IOException {


//        System.out.println("欢迎使用自动关机程序");
//        @SuppressWarnings("resource")
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入倒计时关机的秒数（按回车结束输入）：");
//        String input = scanner.nextLine();
        //自动关机
        Runtime.getRuntime().exec("shutdown -s -t " + sec);
        System.out.println("已启动自动关机系统，系统将在 " + sec + " 秒后关机");
//————————————————
//        版权声明：本文为CSDN博主「BublesLee」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/HJSN_RCTT/article/details/110957449
    }
}
