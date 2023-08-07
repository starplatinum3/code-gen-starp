//package top.starp.util;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.bootdo.blog.domain.ContentDO;
//import com.bootdo.blog.service.ContentService;
//
///**
// * 文章内容
// *
// * @author chglee
// * @email 1992lcg@163.com
// * @date 2017-09-09 10:03:34
// */
//@Controller
//@RequestMapping("/blog/bContent")
//public class DetectedController {
//
//    Logger log  = LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    ContentService bContentService;
//
//
//    /**
//     * @return 需要配置thymeleaf模板引擎
//     */
//    @GetMapping()
//    String bContent() {
//        a();
//        return "blog/bContent/bContent";
//    }
//
//    /**
//     * @param cid
//     * @return 测试能否获得对象
//     */
//    @GetMapping("/edit/{cid}")
//    public ContentDO edit(@PathVariable("cid") Long cid) {
//        ContentDO bContentDO = bContentService.get(cid);
//        return bContentDO;
//    }
//
//    public void a() {
//
//    }
//}
////————————————————
////版权声明：本文为CSDN博主「shijiaolong0」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
////原文链接：https://blog.csdn.net/shijiaolong0/article/details/82083964