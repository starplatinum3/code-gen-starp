package top.starp.util;

import java.util.Map;

public class Common {

    public static final String chatDocUri = "10.160.199.103:30412";

    public static void main(String[] args) {
//        Map<String, Object> data =u.
        Map<String, Object> stringMapMap = u.mapOf(
                u.p(
                        "1",
                        u.mapOf(
                                u.p("1", 3)
                        ))
        );

//        Map<String, Map<String, Integer>> stringMapMap1 = u.mapOf(
//                u.p(
//                        "1",
//                        u.mapOf(
//                                u.p("1", 3)
//                        ))
//        );

//        MongoUtil.

//        HttpUtil.postReturnMap(  Common.chatDocUri+"/local_doc_qa/bing_search_chat",)

        Map<String, Integer> dabble = (Map<String, Integer>) stringMapMap.get("1");

    }

    public static boolean doMock = false;
    public static final String conf_dir = "/home/app/private-conf";
    //            # D:\home\app\private-conf\mysqlBaiAn.json
    public static final String mongodb_conf_path_zucc = "{conf_dir}/mongodbZucc.json"
            .replace("{conf_dir}", conf_dir);
    public static final String mysql_conf_path_baian = "{conf_dir}/mysqlBaiAn.json"
            .replace("{conf_dir}", conf_dir);
    public static final String mysql_conf_path_teng_xun = "{conf_dir}/db_public.json"
            .replace("{conf_dir}", conf_dir);
    //    D:\home\app\private-conf\db_brain.json
    public static final String mysql_conf_path_db_brain_teng_xun = "{conf_dir}/db_brain.json"
            .replace("{conf_dir}", conf_dir);
//    D:\home\app\private-conf\db_public.json

}
