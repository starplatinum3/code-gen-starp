package top.starp.util;
import   cn.hutool.http.HttpRequest;
import  cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpResponse;

public class RequTes {
    void  d(){
        String chatDocUri="10.160.199.103:30412";
//        "http://10.160.199.103:30412/"

        String url = "http://{chatDocUri}/local_doc_qa/upload_file"
                .replace("{chatDocUri}",chatDocUri)
                ;
        String filePath = "/path/to/file.pdf";

//        cn.hutool.http.HttpRequest
//        cn.hutool.http.HttpRequest
//        cn.hutool.core.io.FileUtil
        HttpRequest request = HttpRequest.post(url)
                .form("file",FileUtil.file(filePath))
                .timeout(20000);

        try(
                HttpResponse execute = request.execute();
                ){
//            request.execute();
            String response =execute.body();
            System.out.println(response);
        }

    }

}
