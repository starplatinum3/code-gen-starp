package top.starp.util

import com.alibaba.fastjson.JSONArray
import com.example.demo.util.JsonUtil
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.InsertManyOptions
import com.sangupta.har.HarUtils
import com.sangupta.har.model.HarEntry
import org.bson.Document
import java.io.File
import java.util.stream.Collectors

fun  getNowcoderHarList(filepath:String): MutableList<MutableList<Document>> {
//    val file = File("D:\\download\\www.nowcoder.com工作4.har")
    val file = File(filepath)
    val read = HarUtils.read(file)
//    Har
//    read.log.entries.
//    val responses = read.log.entries.stream().map { o: HarEntry -> o.response }.collect(Collectors.toList())
    var index = 0
//    JsonObject.

//    val list=List<>
//    new list kotlin 往里面 add

//    var list: MutableList<Document>  = ArrayList<Document>()
//    var list: List< List<Document> > = ArrayList<List<Document>>()
//    var list: List< MutableList<Document> > = ArrayList<MutableList<Document>>()
//    list.ad
    var list: MutableList<MutableList<Document>> = ArrayList()



//    springboot kotlin mongo 查询 col_a 的 recommendData.entityId  和 col_b 的 entityId  字段一样的 join起来
    for (entry in read.log.entries) {
        if (!entry.request.url.contains("recommend")) {
            continue
        }
        if (index == 0) {
            val jsonStr = entry.response.content.text
//            parseJson(jsonStr)
            val parseJsonGetRecords = parseJsonGetRecords(jsonStr)
            if (parseJsonGetRecords != null) {
                val jsonArrayToDocuments = jsonArrayToDocuments(parseJsonGetRecords)
//                collection.insertMany(jsonArrayToDocuments, InsertManyOptions().ordered(false))
//                list.add
                list.add(jsonArrayToDocuments)
//                list.ad
            }
//            JsonObjec
//            collection.in
//            collection.insertMany(documents, InsertManyOptions().ordered(false))

        }
        index++
    }

    return list

}



//fun traverseFolderGetHarList(folder: File
//                   ,collection: MongoCollection<Document>) {
//  val list :MutableList<Document>= MutableList()
//    if (folder.isDirectory) {
//        val files = folder.listFiles()
//        if (files != null) {
//            for (file in files) {
////                file.name.startsWith("")
//                val isHar= file.name.endsWith(".har")
//                        &&  file.name.contains("nowcoder.com")
////                nowcoder.com
////                file.name.contains("nowcoder.com")
//                if(!isHar){
//                    continue
//                }
////                nowcoder.com工作回暖了
//                println(file.absolutePath)
////                insertFile(file,collection)
//                val nowcoderHarList = getNowcoderHarList(file.absolutePath)
//                list.add(nowcoderHarList)
////                if (file.isDirectory) {
////                    println(file.absolutePath)
//////                    insertFile(file,collection)
////                }
//            }
//        }
//    }
//}

fun parseJsonGetRecords(jsonStr: String): JSONArray? {
    val jsonObject = JsonUtil.stringToJson(jsonStr)
    val data = jsonObject.getJSONObject("data")
    val keySet = data.keys
//    val keySet = data.keySet()
    println("keySet")
    println(keySet)
//    [current, total, tabName, size, records, totalPage]
    val records = data.getJSONArray("records")
    return records
}