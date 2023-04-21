package com.example.demo.snippets;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

/**
 * 比较通用的 根据传进来的 object 里面有的参数 不是null的话 就要去数据库了找和他一样的
 * 比如说 student name="吉良吉影" , age=33 就会找到 name="吉良吉影" , age=33 的人
 * 如果 student , age=33  就会找到 age=33 的人，这样的话 不就不用写 findByAge 这种东西了吗
 * 不过前端传参数需要 每次都传一个 student， 是不是比较浪费资源 反正我也不管了
 * 虽然说 findByAge 其实也只要声明一下就好了
 */
public class BaseRepository {
    public static <T> Page<T> list(T object, Integer pageNumber,
                                   Integer pageSize,
                                   String fromDate, String toDate,

                                   String dateStr, JpaSpecificationExecutor<T> repository) {

        Specification<T> equal = CustomerSpecifications.equal(object);
        LocalDate from = LocalDate.parse(fromDate);
        LocalDate to = LocalDate.parse(toDate);
        Specification<T> timeBetween = CustomerSpecifications.timeBetween(from, to, dateStr);

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<T> all = repository.findAll(equal.and(timeBetween), pageable);
        return all;
    }

}
