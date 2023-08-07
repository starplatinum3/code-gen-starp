package top.starp.util;

import java.util.ArrayList;
import java.util.List;

public class ListSum {
    private static final int NUM_THREADS = 4; // 线程数

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>(); // 列表数据
        // 假设有一些数据已经添加到numbers中

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

        int totalSum = 0;

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

        System.out.println("总和：" + totalSum);
    }

    static class SumWorker implements Runnable {
        private final List<Integer> segment;
        private int sum;

        public SumWorker(List<Integer> segment) {
            this.segment = segment;
        }

        public int getSum() {
            return sum;
        }

        @Override
        public void run() {
            int segmentSum = 0;
            for (int num : segment) {
                segmentSum += num;
            }
            sum = segmentSum;
        }
    }
}
