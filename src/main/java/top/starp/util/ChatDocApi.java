package top.starp.util;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.Map;

public class ChatDocApi {
    /**
     * 把这个转化成 java hutool 请求的  代码 用python代码去转化
     * "/local_doc_qa/upload_file": {
     *             "post": {
     *                 "summary": "上传文件到知识库",
     *                 "operationId": "upload_file_local_doc_qa_upload_file_post",
     *                 "requestBody": {
     *                     "content": {
     *                         "multipart/form-data": {
     *                             "schema": {
     *                                 "$ref": "#/components/schemas/Body_upload_file_local_doc_qa_upload_file_post"
     *                             }
     *                         }
     *                     },
     *                     "required": true
     *                 },
     *                 "responses": {
     *                     "200": {
     *                         "description": "Successful Response",
     *                         "content": {
     *                             "application/json": {
     *                                 "schema": {
     *                                     "$ref": "#/components/schemas/BaseResponse"
     *                                 }
     *                             }
     *                         }
     *                     },
     *                     "422": {
     *                         "description": "Validation Error",
     *                         "content": {
     *                             "application/json": {
     *                                 "schema": {
     *                                     "$ref": "#/components/schemas/HTTPValidationError"
     *                                 }
     *                             }
     *                         }
     *                     }
     *                 }
     *             }
     *         },
     *
     * 写一个python代码 批量转化这个
     *
     * swagger 转化成 java 请求
     */
    Map<?, ?> upload_file( Map<String, Object> form ){

//        HttpReq.HttpReqBuilder.aHttpReq().withUrl("/local_doc_qa/upload_file")
//                .withForm(u.mapOf(u.p("file", FileUtil.file(filePath)))).build().postFrom
//        Map<String, File> stringFileMap = u.mapOf(u.p("file", FileUtil.file("filePath")));
//                        u.mapOf(u.p("file",  cn.hutool.core.io.FileUtil.file(filePath)))

//        Common.chatDocUri
        HttpReq httpReq = HttpReq.HttpReqBuilder.aHttpReq()
                .withUrl("/local_doc_qa/upload_file")
                .withForm(    form ).build();
        Map<?, ?> map = httpReq.postForm();
        return map;

    }
}
