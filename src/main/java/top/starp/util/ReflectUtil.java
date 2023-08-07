package top.starp.util;

import com.example.demo.entity.Hum;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {

    public static void main(String[] args) {
        try {
//            Hum
            // 获取类名
//            String className = "YourClassName";
//            Hum
//            Hum
//            Hum
            String className = "com.example.demo.entity.Hum";
//            String className = "Hum";
//            Hum
            // 获取类的 Class 对象
            Class<?> clazz = Class.forName(className);

            // 获取类的所有字段
            Field[] fields = clazz.getDeclaredFields();

            // 遍历所有字段
            for (Field field : fields) {
                // 获取字段名和类型
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();

                // 生成 getter 方法名
                String getterMethodName = "get" + capitalize(fieldName);

                System.out.println("getterMethodName");
                System.out.println(getterMethodName);
//                // 判断 getter 方法是否已存在
//                if (!hasMethod(clazz, getterMethodName)) {
//                    // 生成 getter 方法
//                    Method getterMethod = clazz.getDeclaredMethod(getterMethodName);
//
//                    // 设置方法为公共方法
//                    getterMethod.setAccessible(true);
//
////                    Hum.class
//                    // 调用 getter 方法
////                    Object result = getterMethod.invoke(new YourClassName());
//                    Object result = getterMethod.invoke(new Hum());
//
//                    // 输出结果
//                    System.out.println(getterMethodName + ": " + result);
//                }

                // 生成 setter 方法名
                String setterMethodName = "set" + capitalize(fieldName);

                // 判断 setter 方法是否已存在
                if (!hasMethod(clazz, setterMethodName, fieldType)) {
                    // 生成 setter 方法
                    Method setterMethod = clazz.getDeclaredMethod(setterMethodName, fieldType);

                    // 设置方法为公共方法
                    setterMethod.setAccessible(true);

                    // 调用 setter 方法
//                    setterMethod.invoke(new YourClassName(), getDefaultValue(fieldType));
                    setterMethod.invoke(new Hum(), getDefaultValue(fieldType));

                    // 输出结果
                    System.out.println(setterMethodName + " called.");
                }
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 判断类中是否存在指定方法名的方法
    private static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            return method != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    // 将字符串首字母大写
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + (str.length() > 1 ? str.substring(1) : "");
    }

    // 获取字段类型的默认值
    private static Object getDefaultValue(Class<?> fieldType) {
        if (fieldType == int.class) {
            return 0;
        } else if (fieldType == double.class) {
            return 0.0;
        } else if (fieldType == boolean.class) {
            return false;
        } else {
            return null;
        }
    }
}
