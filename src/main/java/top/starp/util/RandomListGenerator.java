package top.starp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomListGenerator {
    public static List<Integer> generateRandomList(int size, int minValue, int maxValue) {
//        ListUtil
        List<Integer> list = new ArrayList<>();

        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int randomNumber = random.nextInt(maxValue - minValue + 1) + minValue;
            list.add(randomNumber);
        }

        return list;
    }
}
