package top.starp.util;

import java.util.Random;

public class RoomMockData {
    private static final String[] TYPE_NAMES = {"单人间", "标准间", "豪华套房"};
    private static final String[] BED_TYPES = {"大床", "双床", "圆床"};
    private static final String[] LAYOUTS = {"1室1厅", "2室1厅", "3室2厅"};
    private static final String[] AMENITIES = {"WIFI", "电视", "空调", "冰箱", "洗衣机"};
    private static final int MIN_PRICE = 100;
    private static final int MAX_PRICE = 1000;
    private static final String[] PHOTO_URLS = {"http://example.com/1.jpg", "http://example.com/2.jpg", "http://example.com/3.jpg"};
    
    public static String getRandomTypeName() {
        return TYPE_NAMES[new Random().nextInt(TYPE_NAMES.length)];
    }
    
    public static String getRandomBedType() {
        return BED_TYPES[new Random().nextInt(BED_TYPES.length)];
    }
    
    public static String getRandomLayout() {
        return LAYOUTS[new Random().nextInt(LAYOUTS.length)];
    }
    
    public static String[] getRandomAmenities() {
        int numAmenities = new Random().nextInt(AMENITIES.length);
        String[] amenities = new String[numAmenities];
        for (int i = 0; i < numAmenities; i++) {
            amenities[i] = AMENITIES[new Random().nextInt(AMENITIES.length)];
        }

        return amenities;
    }
    
    public static int getRandomPrice() {
        return new Random().nextInt(MAX_PRICE - MIN_PRICE + 1) + MIN_PRICE;
    }
    
    public static String getRandomPhotoUrl() {
        return PHOTO_URLS[new Random().nextInt(PHOTO_URLS.length)];
    }
}
