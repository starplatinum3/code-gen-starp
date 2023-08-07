package top.starp.util

import com.alibaba.fastjson.JSONArray
import org.bson.Document

//class JsonUtil {
//}

fun  jsonArrayToDocuments(jsonArray:JSONArray): MutableList<Document> {
    val documents = mutableListOf<Document>()
//    jsonArray.leng
    for (i in 0 until jsonArray.size) {
        val jsonObject = jsonArray.getJSONObject(i)
        documents.add(Document.parse(jsonObject.toString()))
    }
    return documents
}
