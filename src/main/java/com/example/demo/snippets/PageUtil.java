package com.example.demo.snippets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
//import zucc.kinect.entity.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PageUtil {

    public static String page(int pageIndex, int pageSize) {
//        String pageSqlPrefix = " limit " + (pageIndex * pageSize) + " , " + pageSize + " ";
        String pageSqlSuffix = " limit " + (pageIndex * pageSize) + " , " + pageSize + " ";
        return pageSqlSuffix;
    }


    /**
     * 需要配合 page limit 先在 jdbc 查出来那个分页，然后外面包住
     *
     * @param list
     * @param pageIndex
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> Page listToPage(List<T> list, int pageIndex, int pageSize) {
//        String start = redPacketRecordPageTime.getStart();
//        String end = redPacketRecordPageTime.getEnd();
//        int pageIndex = redPacketRecordPageTime.getPageIndex();
//        int pageSize = redPacketRecordPageTime.getPageSize();
//        List<ShowRedRecordUserEquip> showRedRecordUserEquipList =
//                redPacketRecordService.findRedRecordUserEquipPage(start, end, pageIndex, pageSize);
//      todo  但是这个不是从数据库里查到的 确实是那个页数的
//        是把所有的 变成一个page，还是只是套了壳子
//        我感觉是 用所有的 ，然后在这里做分页
        log.info("list");
        log.info(String.valueOf(list));
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        int totalElements = list.size();
        Page<T> page =
                new PageImpl<>(list, pageable, totalElements);


//        log.info("showRedRecordUserEquipPage page");
//        log.info(String.valueOf(showRedRecordUserEquipPage));
//        log.info(String.valueOf(showRedRecordUserEquipPage.getContent()));
//        return redRecordUserEquipPage;
        return page;
    }

//    public static List listToPage(int currentPage, int rows, List list) {
//
//        currentPage = (currentPage - 1) * rows;   //每页的起始索引
////        currentPage = (currentPage - 1) * rows;   //每页的起始索引
////        ind*siz , siz
//        Integer sum = list.size(); //记录总数
//        if (currentPage + rows > sum)
//            return list.subList(currentPage, sum);
//        else
//            return list.subList(currentPage, currentPage + rows);
//    }

    /**
     * 这一页包含的内容
     * @param pageIndex
     * @param pageSize
     * @param list
     * @param <T>
     * @return
     */
    public static <T> List<T> listToPage(int pageIndex, int pageSize, List<T> list) {

        List<T> pageContent = new ArrayList<>();
        for (int i = pageIndex * pageSize; i<list.size()&&i < pageSize+pageIndex * pageSize; i++) {
            T elem = list.get(i);
            pageContent.add(elem);

//            try{
//                T elem = list.get(i);
//                pageContent.add(elem);
//            }catch (IndexOutOfBoundsException e){
//                break;
//            }

        }
        return pageContent;
//        currentPage = (currentPage - 1) * rows;   //每页的起始索引
////        currentPage = (currentPage - 1) * rows;   //每页的起始索引
////        ind*siz , siz
//        Integer sum = list.size(); //记录总数
//        if (currentPage + rows > sum)
//            return list.subList(currentPage, sum);
//        else
//            return list.subList(currentPage, currentPage + rows);
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(i);
        }

//        list1
//                [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
//        List list1 = listToPage(1, 10, list);
        List list2 = listToPage(0, 2, list);
        List list3 = listToPage(1, 2, list);
        List list4 = listToPage(1, 3, list);
//        System.out.println("list1");
//        System.out.println(list1);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list4);
//        System.out.println(list2);
//        list.add(1);

//        listToPage()
    }

    //    JpaRepository<TblRecordEntity, Integer>,
//    public static WithPage  page(WithPage withPage, JpaSpecificationExecutor<WithPage> repository) {
//    有问题了 这里不应该是  JpaRepository<WithPage, Object>  应该是不包含他的
//    public static Page<WithPage> page(WithPage withPage, JpaRepository<WithPage, Object> repository) {
//
////        &pageNumber=1
//        //创建匹配器，需要查询条件请修改此处代码
//        ExampleMatcher matcher = ExampleMatcher.matchingAll();
//
//        //创建实例
//        Example<WithPage> example = Example.of(withPage, matcher);
////        log.info("example");
////        log.info(example.toString());
//        //分页构造
////            Pageable pageable = PageRequest.of(pageNumber,pageSize);
////        Pageable pageable = PageRequest.of(redPacketRecordWithPage.getPageNumber(),
////                redPacketRecordWithPage.getPageSize());
//        Pageable pageable = PageRequest.of(withPage.getPageIndex(),
//                withPage.getPageSize());
////        log.info(pageable);
////        log.info(pageable);
//        Page<WithPage> all = repository.findAll(example, pageable);
////        log.info("all page");
//        return all;
////        log.info(String.valueOf(all.getContent()));
////        return ReturnT.success(all);
////        return redPacketRecordRepository.findAll(example, pageable);
//    }

}
