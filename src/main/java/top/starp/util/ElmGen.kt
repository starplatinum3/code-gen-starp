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
//        val java字段名 = columnInfo.java字段名
        val commentShow = columnInfo.columnCommentShow
        val column_name = columnInfo.columN_NAME
//        val java字段类型 = columnInfo.获取java字段类型()
        val javaFieldName = columnInfo.javaFieldName
        if ("id" != column_name) {
            val row =  """
                <el-table-column label="$commentShow" 
                    sortable
                    align="center" prop="$javaFieldName" >
                </el-table-column>
                
            """.trimIndent()
//            var row = "<el-table-column label=\"{commentShow}\" align=\"center\" prop=\"{javaFieldName}\" />"
//            row = row
//                    .replace("{commentShow}", commentShow)
//                    .replace("{javaFieldName}", javaFieldName)
            res.append(row)
        }
    }
    return res.toString()
}

//fun ddd(){
////    bed_type
//    """
//        <template>
//    <el-select v-model="selectedValue" @change="handleChange">
//        <el-option
//            v-for="(option, index) in options"
//            :key="index"
//            :label="option.text"
//            :value="option.value"
//        />
//    </el-select>
//</template>
//
//  <script>
//export default {
//    props: {
//        value: {
//            type: Number,
//            default: 0,
//        },
//    },
//    data() {
//        return {
//            options: [
//
//                {
//                    text: '大床间',
//                value: 0,
//            },
//            {
//                text: '单人间',
//                value: 1,
//            },
//            {
//                text: '双人间',
//                value: 2,
//            },
//
//
//
//            ],
//            selectedValue: this.value,
//        };
//    },
//    computed: {
//        selectedText() {
//            const option = this.options.find(
//                (opt) => opt.value === this.selectedValue
//            );
//            return option ? option.text : '';
//        },
//    },
//    methods: {
//        handleChange() {
//            this.$emit('input', this.selectedValue);
//        },
//    },
//};
//</script>
//
//    """.trimIndent()
//}

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

fun gen_form_item_rows_add(columnInfos: List<ColumnInfo>): String {


    val formItems = columnInfos.map { columnInfo ->
        val javaFieldName = columnInfo.javaFieldName
        val columnCommentShow = columnInfo.columnCommentShow
        """
        <el-form-item prop="number" label="$columnCommentShow">
                    <el-input
                        v-model="form.$javaFieldName"
                        placeholder="$columnCommentShow"
                        class="add-room__input"
                    >
                    </el-input>
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