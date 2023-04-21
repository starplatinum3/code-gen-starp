package com.example.demo.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class Jpa {

//    @Override
//    public<T> Page<T> findRecordList(int couponDetailId, int pageNum, int pageSize, String startTime, String endTime) {
//        try {
////排序规则和分页
//            Sort sort = new Sort(new Sort.Order(Sort.Direction.DESC, "createTime"));
//            PageRequest pageRequest = new PageRequest(pageNum - 1, pageSize, sort);
//            Specification specification = new Specification() {
//                @Override
//                public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
//                    //增加筛选条件
//                    Predicate predicate = cb.conjunction();
//                    predicate.getExpressions().add(cb.equal(root.get("cardId"), couponDetailId));
////起始日期
//                    if (startTime != null && !startTime.trim().equals("")) {
//                        predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("createTime").as(String.class), startTime));
//                    }
////结束日期
//                    if (endTime != null && !endTime.trim().equals("")) {
//                        predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("createTime").as(String.class), endTime));
//                    }
//                    return predicate;
//                }
//            };
//            Page all = discountCouponRecordDao.findAll(specification, pageRequest);
//            return all;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
}
