/*
 * Copyright 2011-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.demo.snippets;

//import example.springdata.jpa.showcase.core.Account;
//import example.springdata.jpa.showcase.core.Customer;

import org.springframework.data.jpa.domain.Specification;
//import zucc.kinect.entity.RedPacketRecord;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Collection of {@link Specification} implementations.
 *
 * @author Oliver Gierke
 */
public class CustomerSpecifications<T> {


    /**
     * byte	Byte
     * short	Short
     * int	Integer
     * long	Long
     * float	Float
     * double	Double
     * boolean	Boolean
     * char	Character
     *
     * @param canonicalName
     * @return
     */
    public static String primaryToPackaging(String canonicalName) {
        switch (canonicalName) {
            case "byte":
                return "java.lang.Byte";
            case "short":
                return "java.lang.Short";
            case "int":
                return "java.lang.Integer";
            case "long":
                return "java.lang.Long";
            case "float":
                return "java.lang.Float";
            case "double":
                return "java.lang.Double";
            case "boolean":
                return "java.lang.Boolean";
            case "char":
                return "java.lang.Character";
            default:
                return canonicalName;
        }
    }


    static Predicate[] removeNull(Predicate[] predicates) {
        int cnt = 0;
        for (Predicate predicate : predicates) {
            if (predicate != null)
                cnt++;
        }
        int j = 0;
        Predicate[] predicatesNoNull = new Predicate[cnt];
        for (Predicate predicate : predicates) {
            if (predicate != null)
                predicatesNoNull[j++] = predicate;
        }
        return predicatesNoNull;
    }

    /**
     * 从 from 到 to 时间的 记录
     *
     * @param from
     * @param to
     * @param dateStr 根据 变量的名字 java 是驼峰（不一定，如果java 写的是 下划线就是下划线，就是根据java），而不是根据数据库
     * @param <T>
     * @return
     */

    public static <T> Specification<T> timeBetween(LocalDate from, LocalDate to, String dateStr) {



//		只要再传入 一个 RedPacketRecord .class 就全部可以了
//		姑且认为 root 不是从数据库查  而是定义要查的对象
        return (Specification<T>) (root, query, cb) -> {
            // 这个query 应该没有连接数据库吧  其实是发起的 。 官方的例子 是从别的表里查一个 其实是做了join
//			这里如果自己的表 进行 query.from 的话，就会 cross join

//			java.lang.IllegalArgumentException: Unable to locate Attribute
//			with the the given name [send_date] on this ManagedType

            Path<Date> date = root.<Date>get(dateStr);
//			需要这个嘛  根据 变量的名字 java 是驼峰

//			根据entity里 定义的字段吗

            Predicate greaterThanOrEqualTo = cb.greaterThanOrEqualTo(date, java.sql.Date.valueOf(from));
            Predicate lessThanOrEqualTo = cb.lessThanOrEqualTo(date, java.sql.Date.valueOf(to));

            return cb.and(lessThanOrEqualTo, greaterThanOrEqualTo);
        };
    }


    public static <T> Specification<T> equal(Object object, List<String> excepts) {

        return specificationByObj(object, excepts, Pattern.EQUAL);

    }


    /**
     * jpa 如果是找一个实体的 like
     *
     * @param object
     * @param excepts
     * @param <T>
     * @return
     */
    public static <T> Specification<T> like(Object object, List<String> excepts) {

        return specificationByObj(object, excepts, Pattern.LIKE);

    }

    public static <T> Specification<T> like(Object object) {
        return like(object, null);
//        return specificationByObj(object,excepts,Pattern.LIKE);

    }


    enum Pattern {
        LIKE, EQUAL;
    }

    public static <T> Specification<T> specificationByObj(Object object, List<String> excepts, Pattern pattern) {


//		只要再传入 一个 RedPacketRecord .class 就全部可以了
//		姑且认为 root 不是从数据库查  而是定义要查的对象
        return (Specification<T>) (root, query, cb) -> {

            // 这个query 应该没有连接数据库吧  其实是发起的 。 官方的例子 是从别的表里查一个 其实是做了join
//			这里如果自己的表 进行 query.from 的话，就会 cross join

//			需要 类型 和 名字
//			但是 类型是 数据库类型  应该 一样吧

//			需要 type 和 字符串

            Field[] declaredFields = object.getClass().getDeclaredFields();

            Predicate[] predicates = new Predicate[declaredFields.length];

            int i = 0;
            for (Field field : declaredFields) {

                field.setAccessible(true);
//                Class<?> type = field.getType();
//                field.getClass()
//                if(type.equals(String.class)){
//
//                }

                String name = field.getName();

                if (name.equals("serialVersionUID")) {
                    continue;
                }
                if (excepts != null && excepts.contains(name)) {
                    continue;
                }


                try {
                    Object value = field.get(object);

                    if (!(value == null)) {
                        if (pattern == Pattern.EQUAL) {
                            Path<Object> objectPath = null;
//                Path<String> objectPath = null;
//                一定是个string 字符串比对嘛  但是日期呢
                            try {
                                objectPath = root.get(name);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            predicates[i++] = cb.equal(objectPath, value);

                        } else if (pattern == Pattern.LIKE) {

                            Class<?> type = field.getType();

//                            if (field.getClass().equals(String.class)) {
                            if (type.equals(String.class)) {

                                try {
                                    Path<String> objectPath = null;
                                    objectPath = root.get(name);
                                    //                            query.where(cb.like())
                                    predicates[i++] = cb.like(objectPath, "%" + value + "%");
//                            predicates[i++] = cb.like(objectPath);
                                    // TODO: 2021/8/10
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

////                            query.where(cb.like())
//                            predicates[i++] = cb.like(objectPath, "%"+value+"%");
////                            predicates[i++] = cb.like(objectPath);
//

                        }
                    }

//					不能有任何一个是 null

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }


            }

            predicates = removeNull(predicates);

//			需要这个嘛  根据 变量的名字 java 是驼峰

//			根据entity里 定义的字段吗

            return cb.and(predicates);
        };
    }


    public static <T> Specification<T> equal(Object object) {
        return equal(object, null);

    }


    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
//        Object redPacketRecord = new RedPacketRecord();
        Object redPacketRecord=new Object();
        Field[] declaredFields = redPacketRecord.getClass().getDeclaredFields();


        for (Field field : declaredFields) {
//			System.out.println("field");
//			System.out.println(field);
            System.out.println("accessible before");
            boolean accessible = field.isAccessible();
            System.out.println(accessible);
            field.setAccessible(true);
            System.out.println("获取到字段：" + field.getType().getCanonicalName()
                    + ",值：" + field.get(redPacketRecord));
            String modifier = Modifier.toString(field.getModifiers());
            System.out.println(modifier);
//			Object o = field.get(redPacketRecord);
//			System.out.println("o");
//			System.out.println(o);
            String name = field.getName();
//			System.out.println("name");
////			就要 name 是短的名字
//			System.out.println(name);
//

            Type genericType = field.getGenericType();
//			System.out.println("genericType");
//			System.out.println(genericType);
//
//			Class<?> aClass = Class.forName(String.valueOf(genericType).replace("class ",""));
//			System.out.println("aClass");
//			System.out.println(aClass);
            String genericString = field.toGenericString();
//			System.out.println("genericString");
//			System.out.println(genericString);


            Class<?> type = field.getType();
//			System.out.println("type");
//			System.out.println(type);

//			type
//			class java.lang.Integer

//			type
//			long

//			ListUtil.printListRet(Collections.singletonList(type));
//			ListUtil.printListRet(type);

            Class<?> declaringClass = field.getDeclaringClass();
//			System.out.println("declaringClass");
//			System.out.println(declaringClass);
//			declaringClass


            Class<? extends Field> aClass = field.getClass();

//			System.out.println("aClass");
//			System.out.println(aClass);
//			aClass
//			class java.lang.reflect.Field
            boolean accessibleAfter = field.isAccessible();
            System.out.println("accessibleAfter");
            System.out.println(accessibleAfter);
        }

//        String simpleName = RedPacketRecord.class.getSimpleName();
////		System.out.println("simpleName");
////		System.out.println(simpleName);
////
//
//        Class<?>[] classes = RedPacketRecord.class.getClasses();
//		System.out.println("classes");
//		ListUtil.printListRet(Arrays.asList(classes));
//		RedPacketRecord.class

//		Object redPacketRecord=new RedPacketRecord();
//		Field id = redPacketRecord.getClass().getField("redId");

//
//
//		System.out.println("id");
//		System.out.println(id);
//		Object o = redPacketRecord.getClass().getField("redId").get("id");
//		System.out.println("o");
//		System.out.println(o);

//		Object redPacketRecord2=new RedPacketRecord();
//		Field[] fields = redPacketRecord2.getClass().getDeclaredFields();
//		System.out.println("redPacketRecord2");
//		for (Field field : fields) {
//			System.out.println(field.getName());
//			System.out.println(field.isAccessible());
//		}
//
//		System.out.println("========");

//	  fields = redPacketRecord.getClass().getFields();
//	  空的

//		应该不是永久的  可以获得 那危险性应该不大
//		fields = redPacketRecord.getClass().getDeclaredFields();
//		System.out.println("redPacketRecord");
//		for (Field field : fields) {
//			System.out.println(field.getName());
//			System.out.println(field.isAccessible());
//		}
    }
}
