/**
 * har - HAR file reader, writer and viewer
 * Copyright (c) 2014, Sandeep Gupta
 * <p>
 * http://sangupta.com/projects/har
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sangupta.har;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.example.demo.util.JsonUtil;
import com.sangupta.har.model.*;
import org.apache.commons.io.FileUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.CheckUtils;
import com.sangupta.jerry.util.GsonUtils;
import top.starp.util.JsonUtil;
//import top.starp.util.JsonUtil;
//import top.starp.util.TestKt;

/**
 * Utility class for working with HAR files.
 *
 * @author sangupta
 *
 */
public class HarUtils {

    /**
     * Read the HAR file and create an {@link Har} model instance from the same.
     *
     * @param file
     *            the file to be read
     *
     * @return the {@link Har} instance
     *
     * @throws JsonSyntaxException
     *             if the JSON is not well formed
     *
     * @throws IOException
     *             if reading the file fails
     *
     * @throws IllegalArgumentException
     *             if the file does not exist, is a directory or is not a valid
     *             file
     */
    public static Har read(File file) throws JsonSyntaxException, IOException {
        CheckUtils.checkFileExists(file);

        return GsonUtils.getGson().fromJson(FileUtils.readFileToString(file), Har.class);
    }


    public static Har read(String harJson) throws JsonSyntaxException, IOException {
        if (AssertUtils.isEmpty(harJson)) {
            throw new IllegalArgumentException("HAR Json cannot be null/empty");
        }

        return GsonUtils.getGson().fromJson(harJson, Har.class);
    }

    public static Har read(Reader harReader) throws JsonSyntaxException, IOException {
        if (harReader == null) {
            throw new IllegalArgumentException("HAR reader cannot be null");
        }

        return GsonUtils.getGson().fromJson(harReader, Har.class);
    }

    public static Har read(JsonElement jsonElement) throws JsonSyntaxException, IOException {
        if (jsonElement == null) {
            throw new IllegalArgumentException("HAR JsonElement cannot be null");
        }

        return GsonUtils.getGson().fromJson(jsonElement, Har.class);
    }

    /**
     * Connect references between page and entries so that they can be obtained as needed.
     *
     * @param har
     */
    public static void connectReferences(Har har) {
        if (har == null) {
            throw new IllegalArgumentException("HAR object cannot be null");
        }

        if (har.log == null || AssertUtils.isEmpty(har.log.pages)) {
            // nothing to do
            return;
        }

        if (AssertUtils.isEmpty(har.log.entries)) {
            // no har entry - initialize empty list
            for (HarPage page : har.log.pages) {
                page.entries = new ArrayList<HarEntry>();
            }

            return;
        }

        for (HarPage page : har.log.pages) {
            String pageID = page.id;

            List<HarEntry> entries = new ArrayList<HarEntry>();

            for (HarEntry entry : har.log.entries) {
                if (pageID.equals(entry.pageref)) {
                    entries.add(entry);
                }
            }

            // sort these based on start date
            Collections.sort(entries);

            // set the parent reference
            page.entries = entries;
        }
    }

    public static void main(String[] args) throws IOException {
//		File file = new File("D:\\download\\we.51job.com.har");
        File file = new File("D:\\download\\www.nowcoder.com工作4.har");
        Har read = HarUtils.read(file);
//		System.out.println(read.log.pages.get(0));
//		List<Object> collect = read.log.entries.stream().map(o -> o.connection).collect(Collectors.toList());
//		List<HarRequest> collect = read.log.entries.stream().map(o -> o.request).collect(Collectors.toList());
//		List<String> collect = read.log.entries.stream().map(o -> o.request.url).collect(Collectors.toList());
        List<HarResponse> responses = read.log.entries.stream().map(o -> o.response).collect(Collectors.toList());
//		List<HarRequest> collect = read.log.entries.stream().map(o -> o.request).collect(Collectors.toList());
//		List<List<HarQueryParm>> collect = read.log.entries.stream()
//				.map(o -> o.request.queryString).collect(Collectors.toList());
//		List<List<HarQueryParm>> collect = read.log.entries.stream()
//				.map(o -> o.request.queryString).collect(Collectors.toList());
//		for (String s : collect) {
////			System.out.println("====");
////			System.out.println(s);
//			if (s.startsWith("https://vapi.51job.com/user.php?query=pc_personcenter&version=400&clientid")) {
//				System.out.println("====");
//				System.out.println(s);
//			}
//
//		}

        int index=0;

        for (HarEntry entry : read.log.entries) {
            if (!entry.request.url.contains("recommend")) {
                continue;
            }
//            System.out.println(entry.request.url);
//            recommend
            if(index==0){
              String jsonStr=  entry.response.content.text;
//                System.out.println(entry.response.content.text);
//                JSONObject jsonObject = JsonUtil.stringToJson(jsonStr);
                parseJson(jsonStr);

            }

            index++;
        }

//		for (HarRequest harRequest : collect) {
//			if (harRequest.url.startsWith("https://vapi.51job.com/user.php?query=pc_personcenter&version=400&clientid")) {
//				System.out.println(harRequest.postData);
//			}
//		}

//        for (HarResponse respons : responses) {
//            String responsText = respons.content.text;
//            if (responsText.contains("C++")) {
//                System.out.println(responsText);
//            }
//        }


//		for (HarRequest harRequest : responses) {
//			if (harRequest.url.startsWith("https://vapi.51job.com/user.php?query=pc_personcenter&version=400&clientid")) {
//				System.out.println(harRequest.postData);
//			}
//		}
//		System.out.println(collect);
//		System.out.println(read.log.entries.get(0).request);
    }

   static void printJsonObjInfo(JSONObject jsonObject){
        // 获取 JSONObject 对象中所有的 key
        Set<String> keySet = jsonObject.keySet();

        // 遍历 keySet，输出每个 key 和对应的值的长度
        for (String key : keySet) {
            Object value = jsonObject.get(key);
            int valueLength = value.toString().length();
            System.out.println(key + ":   valLen =  " + valueLength);
        }
//       TestKt.parseJson();
    }

//    [current, total, tabName, size, records, totalPage]
//    current: 1
//    total: 1
//    tabName: 2
//    size: 1
//    records: 40181
//    totalPage: 1

//    static <T> T  d(T momentData){
//        if(momentData==null){
//
//        }else{
//            return momentData;
////            String content = momentData.getString("content");
//        }
//
//    }

    static void parseJson(String jsonStr){
        JSONObject jsonObject = JsonUtil.stringToJson(jsonStr);
       JSONObject data = jsonObject.getJSONObject("data");
//       JSONObject 的所有key 获取 java fastJson
//       JsonUtil.k
//       JSONObject 的所有key 和他的内容的长度 获取 java fastJson
       Set<String> keySet = data.keySet();
       System.out.println("keySet");
       System.out.println(keySet);
//       [current, total, tabName, size, records, totalPage]
       JSONArray records = data.getJSONArray("records");
//       System.out.println("records");
//       System.out.println(records);
       printJsonObjInfo(data);
       int idx=0;
//        records.
        int showIndex=7;
        List<String> contentList=new ArrayList<>();
       for (Object record : records) {
//           r
           JSONObject recordJson =(JSONObject)record;
//           momentData
           JSONObject recommendData = recordJson.getJSONObject("recommendData");
           JSONObject momentData = recordJson.getJSONObject("momentData");
//           content
//           recommendData
           JSONObject extraInfo = recommendData.getJSONObject("extraInfo");
//           String entityId = extraInfo.getString("entityId");
           String entityId = JsonUtil.getString(extraInfo, "entityId");
//           JSONObject content = JsonUtil.getJSONObject(momentData, "content");
           String content = JsonUtil.getString(momentData, "content");
//           contentList.add()
//           if(momentData==null){
//
//           }else{
//               String content = momentData.getString("content");
//           }

//           java 怎么防止是null  最优雅
//           给代码例子
           System.out.println("=========");
           System.out.println(content);


//           if(idx<=showIndex){
//               System.out.println("=========");
//               System.out.println(content);
//               System.out.println(recommendData);
////               System.out.println("entityId");
////               System.out.println(entityId);
////               System.out.println(record);
//           }
//

           idx++;
       }
   }
}
