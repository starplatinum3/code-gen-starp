package top.starp.util;//package top.starp.util;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
//
//public class ReflectionExample {
//    public static void main(String[] args) {
//        try {
//            // 获取类名
//            String className = "YourClassName";
//
//            // 获取类的 Class 对象
//            Class<?> clazz = Class.forName(className);
//
//            // 获取类的所有字段
//            Field[] fields = clazz.getDeclaredFields();
//
//            // 遍历所有字段
//            for (Field field : fields) {
//                // 获取字段名和类型
//                String fieldName = field.getName();
//                Class<?> fieldType = field.getType();
//
//                // 生成 getter 方法名
//                String getterMethodName = "get" + capitalize(fieldName);
//
//                // 判断 getter 方法是否已存在
//                if (!hasMethod(clazz, getterMethodName)) {
//
////                    Method.BU
////                    Method.Builder
//                    // 生成 getter 方法
//                    Method getterMethod = new MethodBuilder()
//                            .withModifiers(Modifier.PUBLIC)
//                            .withReturnType(fieldType)
//                            .withName(getterMethodName)
//                            .withBody("return this." + fieldName + ";")
//                            .build();
//
//                    // 添加 getter 方法到类中
//                    clazz.addMethod(getterMethod);
//                }
//
//                // 生成 setter 方法名
//                String setterMethodName = "set" + capitalize(fieldName);
//
//                // 判断 setter 方法是否已存在
//                if (!hasMethod(clazz, setterMethodName, fieldType)) {
//                    // 生成 setter 方法
//                    Method setterMethod = new MethodBuilder()
//                            .withModifiers(Modifier.PUBLIC)
//                            .withReturnType(void.class)
//                            .withName(setterMethodName)
//                            .withParameter(fieldType, fieldName)
//                            .withBody("this." + fieldName + " = " + fieldName + ";")
//                            .build();
//
//                    // 添加 setter 方法到类中
//                    clazz.addMethod(setterMethod);
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 判断类中是否存在指定方法名的方法
//    private static boolean hasMethod(Class<?> clazz, String methodName,  Class<?>... parameterTypes) {
//        try {
//            Method method = clazz.getMethod(methodName, parameterTypes);
//            return method != null;
//        } catch (NoSuchMethodException e) {
//            return false;
//        }
//    }
//
//    // 将字符串首字母大写
//    private static String capitalize(String str) {
//        if (str == null || str.isEmpty()) {
//            return str;
//        }
//        return Character.toUpperCase(str.charAt(0)) + (str.length() > 1 ? str.substring(1) : "");
//    }
//}


import com.example.demo.entity.Hum;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

//public class ReflectionExample {
//    public static void main(String[] args) {
//        try {
////            Hum
//            // 获取类名
////            String className = "YourClassName";
////            Hum
////            Hum
////            Hum
//            String className = "com.example.demo.entity.Hum";
////            String className = "Hum";
////            Hum
//            // 获取类的 Class 对象
//            Class<?> clazz = Class.forName(className);
//
//            // 获取类的所有字段
//            Field[] fields = clazz.getDeclaredFields();
//
//            // 遍历所有字段
//            for (Field field : fields) {
//                // 获取字段名和类型
//                String fieldName = field.getName();
//                Class<?> fieldType = field.getType();
//
//                // 生成 getter 方法名
//                String getterMethodName = "get" + capitalize(fieldName);
//
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
//
//                // 生成 setter 方法名
//                String setterMethodName = "set" + capitalize(fieldName);
//
//                // 判断 setter 方法是否已存在
//                if (!hasMethod(clazz, setterMethodName, fieldType)) {
//                    // 生成 setter 方法
//                    Method setterMethod = clazz.getDeclaredMethod(setterMethodName, fieldType);
//
//                    // 设置方法为公共方法
//                    setterMethod.setAccessible(true);
//
//                    // 调用 setter 方法
////                    setterMethod.invoke(new YourClassName(), getDefaultValue(fieldType));
//                    setterMethod.invoke(new Hum(), getDefaultValue(fieldType));
//
//                    // 输出结果
//                    System.out.println(setterMethodName + " called.");
//                }
//            }
//        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 判断类中是否存在指定方法名的方法
//    private static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
//        try {
//            Method method = clazz.getMethod(methodName, parameterTypes);
//            return method != null;
//        } catch (NoSuchMethodException e) {
//            return false;
//        }
//    }
//
//    // 将字符串首字母大写
//    private static String capitalize(String str) {
//        if (str == null || str.isEmpty()) {
//            return str;
//        }
//        return Character.toUpperCase(str.charAt(0)) + (str.length() > 1 ? str.substring(1) : "");
//    }
//
//    // 获取字段类型的默认值
//    private static Object getDefaultValue(Class<?> fieldType) {
//        if (fieldType == int.class) {
//            return 0;
//        } else if (fieldType == double.class) {
//            return 0.0;
//        } else if (fieldType == boolean.class) {
//            return false;
//        } else {
//            return null;
//        }
//    }
//}



import java.io.FileWriter;
        import java.io.IOException;
        import java.lang.reflect.Field;
        import java.lang.reflect.Modifier;

public class ReflectionExample {
    public static void main(String[] args) {
        try {
            // 获取类名
//            String className = "YourClassName";
            String className = "com.example.demo.entity.Hum";


            // 获取类的 Class 对象
            Class<?> clazz = Class.forName(className);

            // 生成 getter 和 setter 方法的代码
//            String code = generateGetterSetterCode(clazz);
            String code = CodeGenKt.genClassCode(clazz);

            // 将代码写入文件
            writeCodeToFile(code);
//            FileUtil.writeToFile();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 生成 getter 和 setter 方法的代码
    private static String generateGetterSetterCode(Class<?> clazz) {

        StringBuilder codeBuilder = new StringBuilder();

        // 获取类的所有字段
        Field[] fields = clazz.getDeclaredFields();

        // 遍历所有字段
        for (Field field : fields) {
            // 获取字段名和类型
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            // 生成 getter 方法名
            String getterMethodName = "get" + capitalize(fieldName);

//            // 判断 getter 方法是否已存在
//            if (!hasMethod(clazz, getterMethodName)) {
//                // 生成 getter 方法代码
//                String getterMethodCode = generateGetterMethodCode(fieldType, fieldName);
//
//                // 添加 getter 方法代码到字符串构建器
//                codeBuilder.append(getterMethodCode).append("\n");
//            }



            // 生成 getter 方法代码
//            String getterMethodCode = generateGetterMethodCode(fieldType, fieldName);

//            String getterMethodCode = CodeGenKt.genGetAndSet(fieldType, fieldName);
            String fieldTypeSimpleName = fieldType.getSimpleName();
//            String getAndSet = CodeGenKt.genGetAndSet(fieldType, fieldName);
            String getAndSet = CodeGenKt.genGetAndSet(fieldTypeSimpleName, fieldName);
//            String getAndSet = CodeGenKt.genClassCode(clazz);

            // 添加 getter 方法代码到字符串构建器
//            codeBuilder.append(getterMethodCode).append("\n");
            codeBuilder.append(getAndSet).append("\n");

//            // 生成 setter 方法名
//            String setterMethodName = "set" + capitalize(fieldName);
//
////            // 判断 setter 方法是否已存在
////            if (!hasMethod(clazz, setterMethodName, fieldType)) {
////                // 生成 setter 方法代码
////                String setterMethodCode = generateSetterMethodCode(fieldType, fieldName);
////
////                // 添加 setter 方法代码到字符串构建器
////                codeBuilder.append(setterMethodCode).append("\n");
////            }
//
//
//            // 生成 setter 方法代码
//            String setterMethodCode = generateSetterMethodCode(fieldType, fieldName);
//
//            // 添加 setter 方法代码到字符串构建器
//            codeBuilder.append(setterMethodCode).append("\n");
        }

        return codeBuilder.toString();
    }

    // 判断类中是否存在指定方法名的方法
    private static boolean hasMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            clazz.getMethod(methodName, parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    // 生成 getter 方法的代码
//    private static String generateGetterMethodCode(Class<?> fieldType, String fieldName) {
//        StringBuilder methodBuilder = new StringBuilder();
//
//        // 添加方法的修饰符和返回类型
//        methodBuilder.append("public ").append(fieldType.getName()).append(" ")
//                .append("get").append(capitalize(fieldName)).append("() {\n");
//
//        // 添加方法的具体实现
//        methodBuilder.append("    return this.").append(fieldName).append(";\n");
//
//        // 添加方法结束的大括号
//        methodBuilder.append("}");
//
//        return methodBuilder.toString();
//    }

    // 生成 setter 方法的代码
//    private static String generateSetterMethodCode(Class<?> fieldType, String fieldName) {
//        StringBuilder methodBuilder = new StringBuilder();
//
//        // 添加方法的修饰符和返回类型
//        methodBuilder.append("public void ")
//                .append("set").append(capitalize(fieldName))
//                .append("(").append(fieldType.getName()).append(" ").append(fieldName).append(") {\n");
//
//        // 添加方法的具体实现
//        methodBuilder.append("    this.").append(fieldName).append(" = ").append(fieldName).append(";\n");
//
//        // 添加方法结束的大括号
//        methodBuilder.append("}");
//
//        return methodBuilder.toString();
//    }

    // 生成 getter 方法的代码
    private static String generateGetterMethodCode(Class<?> fieldType, String fieldName) {
        StringBuilder methodBuilder = new StringBuilder();

        // 添加方法的修饰符和返回类型
        methodBuilder.append("public ").append(fieldType.getSimpleName()).append(" ")
                .append("get").append(capitalize(fieldName)).append("() {\n");

        // 添加方法的具体实现
        methodBuilder.append("    return this.").append(fieldName).append(";\n");

        // 添加方法结束的大括号
        methodBuilder.append("}");

        return methodBuilder.toString();
    }

    // 生成 setter 方法的代码
//    生成代码用kotlin 模板字符串
    private static String generateSetterMethodCode(Class<?> fieldType, String fieldName) {
        StringBuilder methodBuilder = new StringBuilder();


        // 添加方法的修饰符和返回类型
        methodBuilder.append("public void ")
                .append("set").append(capitalize(fieldName))
                .append("(").append(fieldType.getSimpleName()).append(" ").append(fieldName).append(") {\n");

        // 添加方法的具体实现
        methodBuilder.append("    this.").append(fieldName).append(" = ").append(fieldName).append(";\n");

        // 添加方法结束的大括号
        methodBuilder.append("}");

        return methodBuilder.toString();
    }


    // 将代码写入文件
    private static void writeCodeToFile(String code) {
        try (FileWriter writer = new FileWriter("GeneratedCode.java")) {
            writer.write(code);
            writer.flush();
            System.out.println("生成的代码已写入到文件 GeneratedCode.java");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 将字符串首字母大写
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + (str.length() > 1 ? str.substring(1) : "");
    }
}
