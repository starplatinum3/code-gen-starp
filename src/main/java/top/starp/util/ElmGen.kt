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
//public
val haveRules = false

fun genElTableColumnRows(columnInfos :List<ColumnInfo>): String? {
    val res = StringBuilder()
    for (columnInfo in columnInfos) {
        val java字段名 = columnInfo.java字段名
        val commentShow = columnInfo.columnCommentShow
        val column_name = columnInfo.columN_NAME
        val java字段类型 = columnInfo.获取java字段类型()
        val javaFieldName = columnInfo.javaFieldName
        if ("id" != column_name) {
            var row =  """
                <el-table-column label="$commentShow" align="center" prop="$javaFieldName" >
                
                </el-table-column>
                
                <el-table-column
                  prop="字段名"
                  label="列标题"
                  width="宽度"
                  min-width="最小宽度"
                  fixed="left"
                  :render-header="自定义渲染函数"
                  sortable="custom"
                  :sort-method="自定义排序函数"
                  :sort-by="排序字段名"
                  :sort-orders="['asc', 'desc']"
                  :resizable="true"
                  :formatter="格式化函数"
                  :show-overflow-tooltip="true"
                  align="center"
                  header-align="center"
                  class-name="自定义 class 名称"
                  label-class-name="自定义 class 名称"
                  selectable="true"
                  reserve-selection="true"
                  :filters="筛选项数组"
                  filter-placement="bottom-start"
                  filter-multiple="true"
                  :filter-method="自定义筛选函数"
                  :filtered-value="筛选条件数组"
                  column-key="列唯一标识符"
                    >
             </el-table-column>
            """.trimIndent()
//            var row = "<el-table-column label=\"{commentShow}\" align=\"center\" prop=\"{javaFieldName}\" />"
            row = row
                    .replace("{commentShow}", commentShow)
                    .replace("{javaFieldName}", javaFieldName)
            res.append(row)
        }
    }
    return res.toString()
}

fun gen_form_item_rows(columnInfos: List<ColumnInfo>): String {

//    ElmG

    val formItems = columnInfos.map { columnInfo ->
        val javaFieldName = columnInfo.javaFieldName
        val columnCommentShow = columnInfo.columnCommentShow
//        label="$columnCommentShow"
//       val v= haveRules?"""
//       :rules="rules.$javaFieldName
//       """":""
//        val v = if (haveRules) {
//            """
//    :rules="rules.$javaFieldName"
//    """
//        } else {
//            ""
//        }
//        ElMGen

        """
       
        <el-form-item
            prop="$javaFieldName"
            ${
            if (haveRules) {
                """
    :rules="rules.$javaFieldName"
    """
            } else {
                ""
            }
        }
        
           
            class="check-in__item"
        >
         $columnCommentShow
         <el-input
          placeholder="请输入$columnCommentShow"
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

//@Throws(Exception::class)
//fun genElementTableMybatisPlus(tableInfo: TableInfo): String? {
//    var code = FileUtil.readResourceFileData("genCodeTemplate/elementUi/ElementTableMybatisPlus.vue")
//    //        String iViewFormInputs = genIViewFormInputs();
////    tableInfo.类名
//    tableInfo.className
//    val jsonDefaultNull: String = genJsonDefaultNull()
//    //        String iViewColumnsRows = genIViewColumnsRows();
//    val elmCols: String = genElmCols()
//    val elmFormItems: String = genElmFormItems()
//    val elmQueryInputs: String = genElmQueryInputs()
//    val elmQueryInputsSelectedRow: String = genElmQueryInputs("selectedRow")
//    code = code //                .replace("#formInputs#", iViewFormInputs)
//            .replace("#类名#", 类名)
//            .replace("#实体名#", 实体名) //                .replace("#实体名#", 实体名)
//            .replace("#elmFormItems#", elmFormItems)
//            .replace("#elmCols#", elmCols)
//            .replace("#jsonDefaultNull#", jsonDefaultNull)
//            .replace("#elmQueryInputs#", elmQueryInputs)
//            .replace("#elmFormItemsSelectedRow#", elmQueryInputsSelectedRow) //                .replace("#jsonDefaultNull#", jsonDefaultNull)
//    //                .replace("#iViewColumnsRows#", iViewColumnsRows)
//    val dictDataPath = Paths.get(pathFileString,
//            "elementUi", "tableMybatisPlus", 类名 + "Table.vue")
//    FileUtil.writeCode(dictDataPath, code)
//    return code
//}

fun d() {
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