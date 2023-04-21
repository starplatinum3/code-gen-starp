package com.example.demo.util;

public class ipv4{

public static boolean isIPv4(String address) {
    if (address == null) {
        return false;
    }
    String[] parts = address.split("\\.");
    if (parts.length != 4) {
        return false;
    }
    for (String part : parts) {
        try {
            int value = Integer.parseInt(part);
            if (value < 0 || value > 255) {
                return false;
            }
            if (part.length() > 1 && part.charAt(0) == '0') {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
    return true;
}

public static void main(String[] args) {
//    String address = "192.168.1.1";
//    String address = "192.168.1.256";
//    false
    String address = "192.168.1.255";
//    true
boolean isIPv4 = isIPv4(address);
System.out.println(isIPv4); // true
    // System.out.println(isIPv4("));
}

}