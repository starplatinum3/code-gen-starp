package top.starp.util

import com.example.demo.common.MysqlDataType
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
        // Name todo
       val checkIfShouldTypeFormmaterYes= checkIfShouldTypeFormmater(javaFieldName)
       val formatter= if(checkIfShouldTypeFormmaterYes){
            """
                :formatter="${javaFieldName}Formatter"
            """.trimIndent()
        }else{
            ""
       }
        if ("id" != column_name) {
            val row =  """
                <el-table-column label="$commentShow" 
                    sortable
                    $formatter
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
//Level
fun  checkIfShouldTypeFormmater(javaFieldName:String): Boolean {
    return checkIfShouldType(javaFieldName,listOf("level","sex","status","type","time","date"));
}

fun  checkIfShouldTypeTime(javaFieldName:String): Boolean {
    return checkIfShouldType(javaFieldName,listOf("date","time"));
}

fun  checkIfShouldUploadType(javaFieldName:String): Boolean {
//    val shouldUploadTypeList= listOf<String>("url","pic")
//    for ( type in shouldUploadTypeList){
//        val should=
//                StringUtils.containsIgnoreCase(javaFieldName, type)
//        if(should){
//            return true
//        }
//    }
//    return false

    val shouldUploadTypeList= listOf<String>("url","pic")
    return checkIfShouldType(javaFieldName,shouldUploadTypeList)
}

fun  checkIfShouldType(javaFieldName:String,shouldUploadTypeList: List<String>): Boolean {
//    val shouldUploadTypeList= listOf<String>("url","pic")
    for ( type in shouldUploadTypeList){
        val should=
                StringUtils.containsIgnoreCase(javaFieldName, type)
        if(should){
            return true
        }
    }
    return false
}
fun  checkIfShouldTextAreaType(javaFieldName:String): Boolean {
//    val shouldUploadTypeList= listOf<String>("url","pic")
//   return checkIfShouldType(javaFieldName,shouldUploadTypeList)
    return checkIfShouldType(javaFieldName,listOf("intro","extra"))
//    for ( type in shouldUploadTypeList){
//        val should=
//                StringUtils.containsIgnoreCase(javaFieldName, type)
//        if(should){
//            return true
//        }
//    }
//    return false
}
fun  checkIfShouldTypePassword   (javaFieldName:String): Boolean {
    return checkIfShouldType(javaFieldName,listOf("password","pass"))
}

fun  checkIfShouldTypeSelect  (javaFieldName:String): Boolean {
    return checkIfShouldType(javaFieldName,listOf("status"))
}

fun gen_form_item_vue3(columnInfo :ColumnInfo): String {
    val javaFieldName = columnInfo.javaFieldName
//        columnInfo.datA_TYPE
//        javaFieldName.contais("url")

    val columnCommentShow = columnInfo.columnCommentShow
    val containsUrlIgnoreCase =   checkIfShouldUploadType(javaFieldName)
//        val containsUrlIgnoreCase = StringUtils.containsIgnoreCase(javaFieldName, "url");
    if(containsUrlIgnoreCase){
        return """
            <el-form-item>
                    <el-upload
                        ref="uploadElem"
                        class="l-flex"
                        action="http://localhost:9092/uploadIntroImg"
                        :http-request="uploadImg"
                        list-type="picture-card"
                        :file-list="upload.list"
                        :auto-upload="false"
                        :limit="1"
                        :on-preview="imgPreview"
                        :on-change="verifyFileType"
                    >
                        <i class="el-icon-plus"></i>
                    </el-upload>
                    <el-dialog v-model="dialogVisible">
                        <div style="text-align: center">
                            <img :src="upload.img" style="width: 100%" />
                        </div>
                    </el-dialog>
                    <p class="hotel-intro__tip">只能上传一张图片</p>
                </el-form-item>
        """.trimIndent()
    }

    if(
            checkIfShouldTypeSelect(javaFieldName)
    ){
        return   """
        <el-form-item label="$columnCommentShow">
                    <el-select v-model="form.${javaFieldName}" style="width: 35%">
                        <el-option
                            v-for="item in ${javaFieldName}Options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        ></el-option>
                    </el-select>
                    <span class="add-admin__detail">{{ detail }}</span>
                </el-form-item>
                
                 <el-select v-model="selectedValue" filterable 
                 :remote-method="handleSearch" :loading="loading" :remote="true">
      <el-option v-for="option in options" :key="option.value"
       :label="option.label" :value="option.value" />
    </el-select>
    """.trimIndent()
    }


    if(
            checkIfShouldTypeTime(javaFieldName)
    ){
        return  """
           <el-form-item
                label="$columnCommentShow"
                class="search-filter__item"
                v-if="date"
            >
                <el-date-picker
                    v-model="form.$javaFieldName"
                    type="daterange"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    :disabledDate="disabledDate"
                    size="small"
                >
                </el-date-picker>
            </el-form-item>
        """.trimIndent()
    }

    if(
            checkIfShouldTextAreaType(javaFieldName)
    ){
        return  """
            <el-form-item prop="$javaFieldName" label="$columnCommentShow">
                    <el-input
                        type="textarea"
                        v-model="form.$javaFieldName"
                        placeholder="输入其他内容"
                        maxlength="254"
                        rows="5"
                        show-word-limit
                    >
                        <template #prefix>
                            <i class="el-icon-lock form__icon"></i>
                        </template>
                    </el-input>
                </el-form-item>
        """.trimIndent()
    }

    if(
            checkIfShouldTypePassword(javaFieldName)
    )
    {

        return   """
                <el-form-item prop="$javaFieldName" label="密码">
                    <el-input
                        v-model="form.$javaFieldName"
                        placeholder="请输入密码(长度4-20的数字或字母或下划线)"
                        show-password
                    >
                        <template #prefix>
                            <i class="el-icon-lock form__icon"></i>
                        </template>
                    </el-input>
                </el-form-item>
            """.trimIndent()
    }



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

//        MysqlDataType.isNumberType()
//        if (MysqlDataType.isTextType(columnInfo.datA_TYPE)) {
//
//        }

    val  rules=if (haveRules) {
        """
    :rules="rules.$javaFieldName"
    """
    } else {
        ""
    }

    return   """
       
        <el-form-item
            prop="$javaFieldName"
            $rules
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
fun gen_form_item_rows(columnInfos: List<ColumnInfo>): String {

//    ElmG

//   val shouldUploadTypeList= listOf<String>("url","pic")
//    for ( type in shouldUploadTypeList){
//        val should=
//        StringUtils.containsIgnoreCase(javaFieldName, type)
//        if(should){
//            return true
//        }
//    }
    val formItems = columnInfos.map { columnInfo ->
     return   gen_form_item_vue3(columnInfo)
//
//        val javaFieldName = columnInfo.javaFieldName
////        columnInfo.datA_TYPE
////        javaFieldName.contais("url")
//
//        val columnCommentShow = columnInfo.columnCommentShow
//        val containsUrlIgnoreCase =   checkIfShouldUploadType(javaFieldName)
////        val containsUrlIgnoreCase = StringUtils.containsIgnoreCase(javaFieldName, "url");
//        if(containsUrlIgnoreCase){
//           return """
//            <el-form-item>
//                    <el-upload
//                        ref="uploadElem"
//                        class="l-flex"
//                        action="http://localhost:9092/uploadIntroImg"
//                        :http-request="uploadImg"
//                        list-type="picture-card"
//                        :file-list="upload.list"
//                        :auto-upload="false"
//                        :limit="1"
//                        :on-preview="imgPreview"
//                        :on-change="verifyFileType"
//                    >
//                        <i class="el-icon-plus"></i>
//                    </el-upload>
//                    <el-dialog v-model="dialogVisible">
//                        <div style="text-align: center">
//                            <img :src="upload.img" style="width: 100%" />
//                        </div>
//                    </el-dialog>
//                    <p class="hotel-intro__tip">只能上传一张图片</p>
//                </el-form-item>
//        """.trimIndent()
//        }
//
//        if(
//                checkIfShouldTypeTime(javaFieldName)
//        ){
//            return  """
//           <el-form-item
//                label="$columnCommentShow"
//                class="search-filter__item"
//                v-if="date"
//            >
//                <el-date-picker
//                    v-model="form.$javaFieldName"
//                    type="daterange"
//                    start-placeholder="开始日期"
//                    end-placeholder="结束日期"
//                    :disabledDate="disabledDate"
//                    size="small"
//                >
//                </el-date-picker>
//            </el-form-item>
//        """.trimIndent()
//        }
//
//        if(
//                checkIfShouldTextAreaType(javaFieldName)
//        ){
//          return  """
//            <el-form-item prop="$javaFieldName" label="$columnCommentShow">
//                    <el-input
//                        type="textarea"
//                        v-model="form.$javaFieldName"
//                        placeholder="输入其他内容"
//                        maxlength="254"
//                        rows="5"
//                        show-word-limit
//                    >
//                        <template #prefix>
//                            <i class="el-icon-lock form__icon"></i>
//                        </template>
//                    </el-input>
//                </el-form-item>
//        """.trimIndent()
//        }
//
//        if(
//                checkIfShouldTypePassword(javaFieldName)
//        )
//        {
//
//         return   """
//                <el-form-item prop="$javaFieldName" label="密码">
//                    <el-input
//                        v-model="form.$javaFieldName"
//                        placeholder="请输入密码(长度4-20的数字或字母或下划线)"
//                        show-password
//                    >
//                        <template #prefix>
//                            <i class="el-icon-lock form__icon"></i>
//                        </template>
//                    </el-input>
//                </el-form-item>
//            """.trimIndent()
//        }
//
//
//
////        label="$columnCommentShow"
////       val v= haveRules?"""
////       :rules="rules.$javaFieldName
////       """":""
////        val v = if (haveRules) {
////            """
////    :rules="rules.$javaFieldName"
////    """
////        } else {
////            ""
////        }
////        ElMGen
//
////        MysqlDataType.isNumberType()
////        if (MysqlDataType.isTextType(columnInfo.datA_TYPE)) {
////
////        }
//
//      val  rules=if (haveRules) {
//            """
//    :rules="rules.$javaFieldName"
//    """
//        } else {
//            ""
//        }
//
//     return   """
//
//        <el-form-item
//            prop="$javaFieldName"
//            $rules
//            class="check-in__item"
//        >
//         $columnCommentShow
//         <el-input
//          placeholder="请输入$columnCommentShow"
//          :maxlength="10"
//          size="small"
//          clearable
//          style="width: 200px"
//          v-model="form.$javaFieldName"
//        ></el-input>
//
//                    </el-form-item>
//        """.trimIndent()


    }
    return formItems.joinToString("\n")
}

fun gen_form_item_rows_add(columnInfos: List<ColumnInfo>): String {


    val formItems = columnInfos.map { columnInfo ->
       return gen_form_item_vue3(columnInfo)
//        val javaFieldName = columnInfo.javaFieldName
//        val columnCommentShow = columnInfo.columnCommentShow
//        """
//        <el-form-item prop="$javaFieldName" label="$columnCommentShow">
//                    <el-input
//                        v-model="form.$javaFieldName"
//                        placeholder="$columnCommentShow"
//                        class="add-room__input"
//                    >
//                    </el-input>
//                </el-form-item>
//    """.trimIndent()

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