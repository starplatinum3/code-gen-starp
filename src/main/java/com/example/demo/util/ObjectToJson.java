//TODO 这样要注意，entity是文件名称，要换成你自己实体类上的文件名，如：com.greatmap.internet.estate.entity
package com.example.demo.util;

import com.alibaba.fastjson.JSONObject;
//import com.gm.wj.dto.NoticeDto;
//import com.gm.wj.dto.TimeSlotDto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lxw
 * @date 2020/11/30 18:08
 */
public class ObjectToJson {

    public static String Object2JsonAllFiled(Object object){
        return JSONObject.toJSONString(getAllFiledName(object));
    }

    public static void main(String[] args) {
//        NoticeDto noticeDto = new NoticeDto();
//        List<TimeSlotDto> timeSlotDtos=new ArrayList<>();
////        noticeDto.setTimeSlotDtos(new ArrayList<>());
//        timeSlotDtos.add(new TimeSlotDto());
////        需要set 进去 不然没有他的类型
//        noticeDto.setTimeSlotDtos(timeSlotDtos);
//        Object allFiledName = getAllFiledName(noticeDto);
//        System.out.println(allFiledName);
//
//        String s = Object2JsonAllFiled(noticeDto);
//        System.out.println(s);
    }

    /**
     * @param model
     * @return
     */
    public static Object getAllFiledName(Object model) {
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (int j = 0; j < field.length; j++) {  // 遍历所有属性
                String name = field[j].getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = field[j].getGenericType().toString(); // 获取属性的类型
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = model.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(model); // 调用getter方法获取属性值
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, String.class);
                        m.invoke(model, "");
                    }
                }
                if (type.equals("class java.lang.Integer")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Integer value = (Integer) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Integer.class);
                        m.invoke(model, 0);
                    }
                }
                if (type.equals("class java.lang.Boolean")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Boolean value = (Boolean) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Boolean.class);
                        m.invoke(model, false);
                    }
                }
                if (type.equals("class java.util.Date")) {
                    Method m = model.getClass().getMethod("get" + name);
                    Date value = (Date) m.invoke(model);
                    if (value == null) {
                        m = model.getClass().getMethod("set" + name, Date.class);
                        //格式化日期
                        m.invoke(model,new Date() );
                    }
                }
                if (type.contains("List")) {
                    System.out.println("list");
                    System.out.println(name);
//                    他需要有值吗
                    Method m = model.getClass().getMethod("get" + name);
                    List list = (List) m.invoke(model);
                    if (null == list) {
                        m = model.getClass().getMethod("set" + name, List.class);
                        m.invoke(model, new ArrayList<>());
                        System.out.println("方法");
                        System.out.println(m);
                    }else {
                        System.out.println("如果是集合中只有一部分有值，其他属于都要显示");
                       //如果是集合中只有一部分有值，其他属于都要显示
                        for(int i=0;i<list.size();i++){
                            getAllFiledName(list.get(i));
                        }
                    }
                }
                //如果是对象，就要递归遍历对象,这里对接智能用关键字来看,对象数组不接受
                //TODO 这样要注意，entity是文件名称，要换成你自己实体类上的文件名，
                // 如：com.greatmap.internet.estate.entity
//                Re com.gm.wj.entity
               String ref="com.gm.wj.entity";
//               String  ref="com.greatmap.internet.estate.entity";
                if (type.contains(ref)
                        &&!type.contains("List")) {
                    System.out.println(name);
                    //这里是递归查找，如果对象相互引用，就会导致死循环
                    Method m = model.getClass().getMethod("get" + name);
                    Object value =  m.invoke(model);
                    if(null==value){
                        try {
                          Object obj=  Class.forName(type).newInstance();
                            getAllFiledName(obj);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }else {
                        getAllFiledName(value);
                    }
                }

                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static String getSystemYYYYMMDD(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return  df.format(new Date());
    }


}
//————————————————
//版权声明：本文为CSDN博主「liuxianwen1990」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/liuxianwen1990/article/details/110436818
