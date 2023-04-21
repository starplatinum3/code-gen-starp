package com.example.demo.snippets;//package zucc.kinect.snippets;
//
//import org.springframework.data.domain.*;
//import org.springframework.web.bind.annotation.RequestBody;
//import zucc.kinect.entity.RedPacketRecord;
//import zucc.kinect.entity.ReturnT;
//import zucc.kinect.entity.ShowComRedPack;
//
//public class PageUtilBean<T> {
//
////    T ? extends
////    泛型 传参 含有某些 int的
//    public Object pageCompanys(<? extends  T> t) {
//
////        &pageNumber=1
//        //创建匹配器，需要查询条件请修改此处代码
//        ExampleMatcher matcher = ExampleMatcher.matchingAll();
//
//        //创建实例
//        Example<T> example = Example.of(t, matcher);
////        log.info("example");
////        log.info(example.toString());
//        //分页构造
////            Pageable pageable = PageRequest.of(pageNumber,pageSize);
////        Pageable pageable = PageRequest.of(redPacketRecordWithPage.getPageNumber(),
////                redPacketRecordWithPage.getPageSize());
//        Pageable pageable = PageRequest.of(t.getPageIndex(),
//                t.getPageSize());
////        log.info(pageable);
////        log.info(pageable);
//        Page<RedPacketRecord> all = redPacketRecordRepository.findAll(example, pageable);
//        log.info("all page");
//        log.info(String.valueOf(all.getContent()));
//        return ReturnT.success(all);
////        return redPacketRecordRepository.findAll(example, pageable);
//    }
////
////    public Object pageCompanys(@RequestBody ShowComRedPack showComRedPack) {
////
//////        &pageNumber=1
////        //创建匹配器，需要查询条件请修改此处代码
////        ExampleMatcher matcher = ExampleMatcher.matchingAll();
////
////        //创建实例
////        Example<ShowComRedPack> example = Example.of(showComRedPack, matcher);
//////        log.info("example");
//////        log.info(example.toString());
////        //分页构造
//////            Pageable pageable = PageRequest.of(pageNumber,pageSize);
//////        Pageable pageable = PageRequest.of(redPacketRecordWithPage.getPageNumber(),
//////                redPacketRecordWithPage.getPageSize());
////        Pageable pageable = PageRequest.of(redPacketRecordWithPage.getPageIndex(),
////                redPacketRecordWithPage.getPageSize());
//////        log.info(pageable);
//////        log.info(pageable);
////        Page<RedPacketRecord> all = redPacketRecordRepository.findAll(example, pageable);
////        log.info("all page");
////        log.info(String.valueOf(all.getContent()));
////        return ReturnT.success(all);
//////        return redPacketRecordRepository.findAll(example, pageable);
////    }
//}
