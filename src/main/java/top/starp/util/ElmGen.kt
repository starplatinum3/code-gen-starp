package top.starp.util

import com.example.demo.util.codeGen.ColumnInfo

//fun gen_form_item(columnInfos: List<ColumnInfo>) {
////    一个列表 把form_item 这些放进去，最后 join成一个字符串
//    val formItems = columnInfos.forEach { columnInfo ->
//        val javaFieldName = columnInfo.javaFieldName;
//        val columnCommentShow = columnInfo.columnCommentShow;
////        val form_item = """
////         <el-form-item
////                        prop="$javaFieldName"
////                        :rules="rules.$javaFieldName"
////                        label="$columnCommentShow"
////                        class="check-in__item"
////                    >
////    """
//////      return
////        form_item
//
//        """
//         <el-form-item
//                        prop="$javaFieldName"
//                        :rules="rules.$javaFieldName"
//                        label="$columnCommentShow"
//                        class="check-in__item"
//                    >
//    """
//
////        println(form_item)
////               .trimIndent()
//
//    }
//
//}

fun gen_form_item(columnInfos: List<ColumnInfo>): String {
    val formItems = columnInfos.map { columnInfo ->
        val javaFieldName = columnInfo.javaFieldName
        val columnCommentShow = columnInfo.columnCommentShow
        """
       
        <el-form-item
            prop="$javaFieldName"
            :rules="rules.$javaFieldName"
            label="$columnCommentShow"
            class="check-in__item"
        >
         {commentShow}  
         <el-input
          placeholder="请输入{commentShow}"
          :maxlength="10"
          size="small"
          clearable
          style="width: 200px"
          v-model="form.$javaFieldName"
        ></el-input>
                      
                    </el-form-item>
        """.trimIndent()
    }
    return formItems.joinToString("\n")
}

fun  d(){
    """
         {commentShow}  <el-input
          placeholder="请输入{commentShow}"
          :maxlength="10"
          size="small"
          clearable
          style="width: 200px"
          v-model="query.{javaFieldName}"
        ></el-input>
    """.trimIndent()
}