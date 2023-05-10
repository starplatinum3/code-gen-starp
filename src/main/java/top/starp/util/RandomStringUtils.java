package top.starp.util;

import java.util.Random;

public class RandomStringUtils {
    public static String generateRandomString() {
     return   generateRandomString(6);
    }
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        String randomString = generateRandomString(10);
        System.out.println(randomString); // Output example: "xKdFhTg1bE"
    }
}
