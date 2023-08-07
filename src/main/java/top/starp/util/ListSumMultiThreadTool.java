package top.starp.util;

import java.util.ArrayList;
import java.util.List;

public class ListSumMultiThreadTool {
    private static final int NUM_THREADS = 4; // 线程数

    public static Long sum(List<Integer> numbers) {
        long sum=0;
        for (Integer number : numbers) {
            sum+=number;
        }
        return  sum;
    }
//    public static Long sumList(List<Integer> numbers) {
////        numbers.su
////        numbers.stream().sum
//        ListUtil.sum(numbers);
//    }

    public static Long sumList(List<Integer> numbers) {
        Long sum = sumByThread(numbers);
        return  sum;
    }
//    sumList
    public static Long sumByThread(List<Integer> numbers) {
        int listSize = numbers.size();
        int segmentSize = (int) Math.ceil((double) listSize / NUM_THREADS);

        List<SumWorker> workers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < NUM_THREADS; i++) {
            int startIndex = i * segmentSize;
            int endIndex = Math.min(startIndex + segmentSize, listSize);
            List<Integer> segment = numbers.subList(startIndex, endIndex);

            SumWorker worker = new SumWorker(segment);
            workers.add(worker);

            Thread thread = new Thread(worker);
            threads.add(thread);
            thread.start();
        }

        long totalSum = 0L;

        try {
            for (Thread thread : threads) {
                thread.join();
            }

            for (SumWorker worker : workers) {
                totalSum += worker.getSum();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return totalSum;
    }

    static class SumWorker implements Runnable {
        private final List<Integer> segment;
        private long sum;

        public SumWorker(List<Integer> segment) {
            this.segment = segment;
        }

        public long getSum() {
            return sum;
        }

        @Override
        public void run() {
            long segmentSum = 0;
            for (int num : segment) {
                segmentSum += num;
            }
            sum = segmentSum;
        }
    }

//    d(){
//        long totalSum = sumList(numbers);
//
////        long endTime = System.currentTimeMillis();
//
//        System.out.println("总和：" + totalSum);
//
////        System.out.println("执行时间：" + (endTime - startTime) + "毫秒");
//    }

    /**
     * Time: 2023_07_24_14_35_49    size
     * Time: 2023_07_24_14_35_49    99999999
     * Time: 2023_07_24_14_35_49    sumTestSum1  ============
     * 总和：1565631590433110
     * 执行时间：189毫秒
     * Time: 2023_07_24_14_35_49    sumTestThread  ============
     * 总和：1565631590433110
     * 执行时间：3299毫秒
     * @param numbers
     */
    static  void sumTestThread( List<Integer> numbers){
        log.info("sumTestThread  ============");
        long startTime = System.currentTimeMillis();
//        int totalSum = sumList(numbers);
        long totalSum = sumList(numbers);

        long endTime = System.currentTimeMillis();

        System.out.println("总和：" + totalSum);

        System.out.println("执行时间：" + (endTime - startTime) + "毫秒");
    }


    static  void sumTestSum1( List<Integer> numbers){
        log.info("sumTestSum1  ============");

        long startTime = System.currentTimeMillis();
//        int totalSum = sumList(numbers);
//        long totalSum = sumList(numbers);
        long totalSum = ListUtil.sum(numbers);

        long endTime = System.currentTimeMillis();

        System.out.println("总和：" + totalSum);

        System.out.println("执行时间：" + (endTime - startTime) + "毫秒");
    }

    public static void main(String[] args) {
//        List<Integer> numbers = new ArrayList<>();
        // 假设有一些数据已经添加到numbers中，此处省略赋值过程
//        总和：0
//        执行时间：7毫秒

        List<Integer> numbers = ListUtil.generateRandomList(99999999, 0, 31313141);
        int size = numbers.size();


        log.info("size");
        log.info(size+"");

//        sumByThread(numbers);

        sumTestSum1(numbers);
        sumTestThread(numbers);


//        long startTime = System.currentTimeMillis();
////        int totalSum = sumList(numbers);
//        long totalSum = sumList(numbers);
//
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("总和：" + totalSum);
//
//        System.out.println("执行时间：" + (endTime - startTime) + "毫秒");

        /**
         * Time: 2023_07_24_14_20_31    size
         * Time: 2023_07_24_14_20_31    99999999
         * 总和：-1955971223
         * 执行时间：3677毫秒
         *
         * Time: 2023_07_24_14_23_23    size
         * Time: 2023_07_24_14_23_23    99999999
         * 总和：1565612165983100
         * 执行时间：4102毫秒
         */

    }
}
