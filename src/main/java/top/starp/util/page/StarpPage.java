package top.starp.util.page;

//import cn.hutool.core.util.PageUtil;
//import com.gm.wj.util.MyPage;
//import cn.hutool.core.util.PageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
//import zucc.kinect.snippets.PageUtil;

import java.util.List;

/**
 *   "content": [
 *       {
 *         "id": 83,
 *         "companyName": "浙大城市学院",
 *         "companyAddress": "理四423",
 *         "companyPerson": "蔡建平",
 *         "companyPhone": "13616516188",
 *         "createTime": "2021-06-18T02:13:42.000+00:00",
 *         "deleteFlag": 0,
 *         "leftPoint": 0
 *       }
 *     ],
 *     "pageable": {
 *       "sort": {
 *         "sorted": false,
 *         "unsorted": true,
 *         "empty": true
 *       },
 *       "offset": 0,
 *       "pageSize": 1,
 *       "pageNumber": 0,
 *       "unpaged": false,
 *       "paged": true
 *     },
 *     "totalPages": 1,
 *     "totalElements": 1,
 *     "last": true,
 *     "number": 0,
 *     "size": 1,
 *     "sort": {
 *       "sorted": false,
 *       "unsorted": true,
 *       "empty": true
 *     },
 *     "numberOfElements": 1,
 *     "first": true,
 *     "empty": false
 *   },
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StarpPage<T> {
    List<T> content;
    Pageable pageable;
    int totalPages;
    int totalElements;
    boolean last;
    Sort sort;
    int number=0;
    int size=1;
    int numberOfElements=1;
    boolean first=true;
    boolean empty=false;


//
//    public MyPage(List content,int pageIndex,int pageSize) {
//        this.content = content;
//        pageable=new Pageable(pageIndex,pageSize);
//        totalPages=1;
//        totalElements=content.size();
//        last=true;
//        sort=new Sort();
//    }

    public StarpPage(List<T> wholeList,int pageIndex,int pageSize) {
        if(wholeList.size()==0){
            this.content=wholeList;
            totalPages=0;

        }else {
//            com.gm.wj.util.page.PageUtil.
//            PageUtil.listConvertToPage()
//            PageUtil.l
            this.content= PageUtil.listToPage(pageIndex,pageSize,wholeList);
            totalPages=1;

        }
//        this.content = content;
        pageable=new Pageable(pageIndex,pageSize);
//        totalElements=content.size();
//        需要是 整个list的长度
//        这个数字不对啊 应该是数据库里的全部的长度
//        没问题 传入的就是 所有数据
        totalElements=wholeList.size();
        last=true;
        sort=new Sort();
    }

    public  static<T>  StarpPage of(List<T> wholeList,int pageIndex,int pageSize,int totalElements) {
        StarpPage<T>myPage=new StarpPage<>(wholeList,pageIndex,pageSize);
        myPage.totalElements=totalElements;
        return  myPage;

    }

//    public MyPage(List<Map<String,Object>> wholeList, int pageIndex, int pageSize) {
//        if(wholeList.size()==0){
//            this.content=wholeList;
//            totalPages=0;
//
//        }else {
//            this.content= PageUtil.listToPage(pageIndex,pageSize,wholeList);
//            totalPages=1;
//
//        }
////        this.content = content;
//        pageable=new Pageable(pageIndex,pageSize);
////        totalElements=content.size();
////        需要是 整个list的长度
//        totalElements=wholeList.size();
//        last=true;
//        sort=new Sort();
//    }
}
