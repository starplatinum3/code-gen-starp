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

fun genElTableColumnRow(columnInfo: ColumnInfo): String {
    val commentShow = columnInfo.columnCommentShow
    val column_name = columnInfo.columN_NAME
//        val java字段类型 = columnInfo.获取java字段类型()
    val javaFieldName = columnInfo.javaFieldName
    // Name todo

    if (checkIfShouldTypeUrl(javaFieldName)) {
        return """
                <el-table-column
                    label="$commentShow"
                    sortable
                    align="center"
                    prop="$javaFieldName"
                >
                    <template v-slot="{ row }">
                        <el-image
                            :src="row.$javaFieldName"
                            fit="cover"
                            style="width: 50px; height: 50px"
                        ></el-image>
                    </template>
                </el-table-column>
            """.trimIndent()
    }
    val checkIfShouldTypeFormmaterYes = checkIfShouldTypeFormmater(javaFieldName)
    val formatter = if (checkIfShouldTypeFormmaterYes) {
        """
                :formatter="${javaFieldName}Formatter"
            """.trimIndent()
    } else {
        ""
    }

    if ("id" != column_name) {
        return """
                <el-table-column label="$commentShow" 
                    sortable
                    $formatter
                    align="center" prop="$javaFieldName" >
                </el-table-column>
                
            """.trimIndent()
    }
    return ""
}


//fun genByFunc(columnInfos: List<ColumnInfo>,function: Function<out String>): String{
//    val res = StringBuilder()
//    for (columnInfo in columnInfos) {
////        val row = genToTableButton(columnInfo)
//        val row =   function(columnInfo)
//        res.append(row)
//    }
//    return res.toString()
//}
fun genByFunc(columnInfos: List<ColumnInfo>, function: (ColumnInfo) -> String): String {
    val res = StringBuilder()
    for (columnInfo in columnInfos) {
        val row = function(columnInfo)
        res.append(row)
    }
    return res.toString()
}


fun genElTableToTableButton(columnInfos: List<ColumnInfo>): String{
    val res = StringBuilder()
    for (columnInfo in columnInfos) {
        val row = genToTableButton(columnInfo)
        res.append(row)
    }
    return res.toString()
}

fun genElTableColumnRows(columnInfos: List<ColumnInfo>): String? {
    val res = StringBuilder()
    for (columnInfo in columnInfos) {
        val row = genElTableColumnRow(columnInfo)
////        val java字段名 = columnInfo.java字段名
//        val commentShow = columnInfo.columnCommentShow
//            val column_name = columnInfo.columN_NAME
////        val java字段类型 = columnInfo.获取java字段类型()
//            val javaFieldName = columnInfo.javaFieldName
//            // Name todo
//
//            {
//                """
//                <el-table-column
//                    label="房型照片的 URL"
//                    sortable
//                    align="center"
//                    prop="photoUrl"
//                >
//                    <template v-slot="{ row }">
//                        <el-image
//                            :src="row.photoUrl"
//                            fit="cover"
//                            style="width: 50px; height: 50px"
//                        ></el-image>
//                    </template>
//                </el-table-column>
//            """.trimIndent()
//            }
//            val checkIfShouldTypeFormmaterYes= checkIfShouldTypeFormmater(javaFieldName)
//            val formatter= if(checkIfShouldTypeFormmaterYes){
//                """
//                :formatter="${javaFieldName}Formatter"
//            """.trimIndent()
//            }else{
//                ""
//            }
//
//            if ("id" != column_name) {
//                val row =  """
//                <el-table-column label="$commentShow"
//                    sortable
//                    $formatter
//                    align="center" prop="$javaFieldName" >
//                </el-table-column>
//
//            """.trimIndent()
//            var row = "<el-table-column label=\"{commentShow}\" align=\"center\" prop=\"{javaFieldName}\" />"
//            row = row
//                    .replace("{commentShow}", commentShow)
//                    .replace("{javaFieldName}", javaFieldName)
        res.append(row)
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
fun checkIfShouldTypeFormmater(javaFieldName: String): Boolean {
    return checkIfShouldType(javaFieldName, listOf("level", "sex", "status", "type", "time", "date"));
}

fun checkIfShouldTypeTime(javaFieldName: String): Boolean {
    return checkIfShouldType(javaFieldName, listOf("date", "time"));
}

fun checkIfShouldUploadType(javaFieldName: String): Boolean {
//    val shouldUploadTypeList= listOf<String>("url","pic")
//    for ( type in shouldUploadTypeList){
//        val should=
//                StringUtils.containsIgnoreCase(javaFieldName, type)
//        if(should){
//            return true
//        }
//    }
//    return false

    val shouldUploadTypeList = listOf<String>("url", "pic")
    return checkIfShouldType(javaFieldName, shouldUploadTypeList)
}

fun checkIfShouldTypeUrl(javaFieldName: String): Boolean {
    val shouldUploadTypeList = listOf("url")
    return checkIfShouldType(javaFieldName, shouldUploadTypeList)
}

fun checkIfShouldType(javaFieldName: String, shouldUploadTypeList: List<String>): Boolean {
//    val shouldUploadTypeList= listOf<String>("url","pic")
    for (type in shouldUploadTypeList) {
        val should =
                StringUtils.containsIgnoreCase(javaFieldName, type)
        if (should) {
            return true
        }
    }
    return false
}

fun checkIfShouldTextAreaType(javaFieldName: String): Boolean {
//    val shouldUploadTypeList= listOf<String>("url","pic")
//   return checkIfShouldType(javaFieldName,shouldUploadTypeList)
    return checkIfShouldType(javaFieldName, listOf("intro", "extra"))
//    for ( type in shouldUploadTypeList){
//        val should=
//                StringUtils.containsIgnoreCase(javaFieldName, type)
//        if(should){
//            return true
//        }
//    }
//    return false
}

fun checkIfShouldTypePassword(javaFieldName: String): Boolean {
    return checkIfShouldType(javaFieldName, listOf("password", "pass"))
}

fun checkIfShouldTypeSelect(javaFieldName: String): Boolean {
    return checkIfShouldType(javaFieldName, listOf("status"))
}

fun gen_form_item_vue3_wrap_col_search(columnInfo: ColumnInfo): String {

    val form_item_vue3 = gen_form_item_vue3_search(columnInfo)
    if (columnInfo.isNumberType
            || columnInfo.isDateType) {
        return form_item_vue3
    }
    return """
        <el-col
                    :xs="{ span: 24 }"
                    :sm="{ span: 12 }"
                    :md="{ span: 12 }"
                    :lg="{ span: 8 }"
                    :xl="{ span: 8 }"
                >
                $form_item_vue3
                </el-col>
    """.trimIndent()
}

fun gen_form_item_vue3_wrap_col(columnInfo: ColumnInfo): String {
    val form_item_vue3 = gen_form_item_vue3(columnInfo)
    return """
        <el-col
                    :xs="{ span: 24 }"
                    :sm="{ span: 12 }"
                    :md="{ span: 12 }"
                    :lg="{ span: 8 }"
                    :xl="{ span: 8 }"
                >
                $form_item_vue3
                </el-col>
    """.trimIndent()
}


fun gen_form_item_vue3_search(columnInfo: ColumnInfo): String {
    val javaFieldName = columnInfo.javaFieldName
//        columnInfo.datA_TYPE
//        javaFieldName.contais("url")

    val columnCommentShow = columnInfo.columnCommentShow
//    图片 是不用搜索的 吧  但是 上传是要的
    val containsUrlIgnoreCase = checkIfShouldUploadType(javaFieldName)
//        val containsUrlIgnoreCase = StringUtils.containsIgnoreCase(javaFieldName, "url");
//    upload
    if (containsUrlIgnoreCase) {
        return """
            <el-form-item>
            $columnCommentShow
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
                            <img :src="form.$javaFieldName" style="width: 100%" />
                        </div>
                    </el-dialog>
                    <p class="hotel-intro__tip">只能上传一张图片</p>
                </el-form-item>
        """.trimIndent()
    }

    if (
            checkIfShouldTypeSelect(javaFieldName)
    ) {
        return """
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


    if (
            checkIfShouldTypeTime(javaFieldName)
    ) {
        return """
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

    if (
            checkIfShouldTextAreaType(javaFieldName)
    ) {
        return """
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

    if (
            checkIfShouldTypePassword(javaFieldName)
    ) {

//        label="密码"
        return """
                <el-form-item prop="$javaFieldName" >
                $columnCommentShow
                    <el-input
                        v-model="form.$javaFieldName"
                        style="width: 200px"
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

//    columnInfo.isDateType
//    if (columnInfo.isNumberType) {
//        return   """
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
//    }
//    if(
//            checkIfShouldTypePassword(javaFieldName)
//    )

    if (columnInfo.isNumberType) {
//        label="${columnCommentShow} 数字范围"
        return """
          <el-form-item >
             ${columnCommentShow}范围
        <el-input-number
            v-model="form.${javaFieldName}Min"
            :min="0"
            :max="form.${javaFieldName}Max"
            :step="1"
            controls-position="right"
            placeholder="${columnCommentShow}最小值"
              size="small"
          ></el-input-number>

 ~
          <el-input-number
          size="small"
            v-model="form.${javaFieldName}Max"
            :min="form.${javaFieldName}Min"
            :step="1"
            controls-position="right"
            placeholder="${columnCommentShow}最大值"
          ></el-input-number>

    </el-form-item>
    """.trimIndent()
    }

//    v-model
    val v_model = if (columnInfo.isNumberType) {
        """
    v-model.number
    """
    } else {
        "v-model"
    }

    val el_input_conf_type = if (columnInfo.isNumberType) {
        """
          v-number-range="{ min: 0, max: 20 }"
          type="number"
          
    """
    } else {
        """
    """
    }
    val rules = if (haveRules) {
        """
    :rules="rules.$javaFieldName"
    """
    } else {
        ""
    }
//    v-number-range="{ min: 0, max: 20 }"


    return """
       
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
            $v_model="form.$javaFieldName"
            $el_input_conf_type
        ></el-input>
                      
                    </el-form-item>
        """.trimIndent()

}

fun gen_form_item_vue3(columnInfo: ColumnInfo): String {
    val javaFieldName = columnInfo.javaFieldName
//        columnInfo.datA_TYPE
//        javaFieldName.contais("url")

    val columnCommentShow = columnInfo.columnCommentShow
//    图片 是不用搜索的 吧  但是 上传是要的
    val containsUrlIgnoreCase = checkIfShouldUploadType(javaFieldName)
//        val containsUrlIgnoreCase = StringUtils.containsIgnoreCase(javaFieldName, "url");
//    upload
    if (containsUrlIgnoreCase) {
        return """
            <el-form-item>
            $columnCommentShow
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
                            <img :src="form.$javaFieldName" style="width: 100%" />
                        </div>
                    </el-dialog>
                    <p class="hotel-intro__tip">只能上传一张图片</p>
                </el-form-item>
        """.trimIndent()
    }

    if (
            checkIfShouldTypeSelect(javaFieldName)
    ) {
        return """
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


    if (
            checkIfShouldTypeTime(javaFieldName)
    ) {
        return """
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

    if (
            checkIfShouldTextAreaType(javaFieldName)
    ) {
        return """
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

    if (
            checkIfShouldTypePassword(javaFieldName)
    ) {

//        label="密码"
        return """
                <el-form-item prop="$javaFieldName" >
                $columnCommentShow
                    <el-input
                        v-model="form.$javaFieldName"
                        style="width: 200px"
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

//    columnInfo.isDateType
//    if (columnInfo.isNumberType) {
//        return   """
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
//    }
//    if(
//            checkIfShouldTypePassword(javaFieldName)
//    )


//    v-model
    val v_model = if (columnInfo.isNumberType) {
        """
    v-model.number
    """
    } else {
        "v-model"
    }

    val el_input_conf_type = if (columnInfo.isNumberType) {
        """
          v-number-range="{ min: 0, max: 20 }"
          type="number"
          
    """
    } else {
        """
    """
    }
    val rules = if (haveRules) {
        """
    :rules="rules.$javaFieldName"
    """
    } else {
        ""
    }
//    v-number-range="{ min: 0, max: 20 }"

    return """
       
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
            $v_model="form.$javaFieldName"
            $el_input_conf_type
        ></el-input>
                      
                    </el-form-item>
        """.trimIndent()

}

fun gen_form_item_rows(columnInfos: List<ColumnInfo>): String {

    val formItems = columnInfos.map { columnInfo ->
//           gen_form_item_vue3(columnInfo)
        gen_form_item_vue3_wrap_col(columnInfo)


    }
    println("formItems")
    println(formItems)
    return formItems.joinToString("\n")
}

fun rangeSelectCodeGen(columnInfo:ColumnInfo,formItems: MutableList<String>
                       ,twoColSet:MutableSet<String>) {
    val row = gen_form_item_vue3_wrap_col_search(columnInfo)
//            twoColSet.
//            if(row.)
    if (
            twoColSet.size == 2
    ) {
        var twoColCode = ""
        for (s in twoColSet) {
            twoColCode += s + "\n"
        }
        val code= """
                 <el-row
      type="flex"
                justify="center"
                style="flex-wrap: wrap; flex-direction: row"
            >
            $twoColCode
            </el-row>
            """.trimIndent()
        formItems.add(code)

        twoColSet.clear()
    } else {
        twoColSet.add(row)
    }
}

fun  mapToWholeName(inputString:String): String? {
//   val map:HashMap<String,String> = mutableMapOf()
//    val map:HashMap<String,String> =
//   val map= mutableMapOf<String,String>()
//    map.put("u","User")
//    map.put("o","Order")
//
//    if ("u".equals(toWhere, ignoreCase = true)) {
//        toWhere="User"
//    }else if("o".equals(toWhere, ignoreCase = true)){
//
//    }

    val map = mapOf(
            "u" to "User",
            "o" to "Order",
            "r" to "Room",
    )

//    val inputString = "KEY1"

    val matchingValue = map.values.find { it.equals(inputString, ignoreCase = true) }

    return matchingValue
//    println(matchingValue)
}



//fun  genToTableFunc(columnInfo :ColumnInfo): String {
//    if ("id".equals(columnInfo.javaFieldName, ignoreCase = true)) {
//        return ""
//    }
////   val forignTableList= mutableListOf<String>()
//
//    if (StringUtils.containsIgnoreCase(columnInfo.javaFieldName,"id")) {
//
//        val replaceIgnoreCase = StringUtils.replaceIgnoreCase(
//                columnInfo.javaFieldName, "id", "")
////        room
////        roomId
//        val entity=replaceIgnoreCase
//        val toWhereShortWord=  StringUtils.upperCaseFirst(replaceIgnoreCase)
////        if ("u".equals(toWhere, ignoreCase = true)) {
////            toWhere="User"
////        }else if("o".equals(toWhere, ignoreCase = true)){
////
////        }
//        val toWhere=  mapToWholeName(toWhereShortWord)
////        if(toWhere.equals())
////        columnInfo.columnCommentShow
//        val javaFieldName=    columnInfo.javaFieldName
//        return       """
//         const user = reactive({});
//    """.trimIndent()
//    }
//    return  ""
//
//}


fun  genToTableFunc(columnInfo :ColumnInfo): String {
    if ("id".equals(columnInfo.javaFieldName, ignoreCase = true)) {
        return ""
    }

    if (StringUtils.containsIgnoreCase(columnInfo.javaFieldName,"id")) {

        val replaceIgnoreCase = StringUtils.replaceIgnoreCase(
                columnInfo.javaFieldName, "id", "")
//        room
//        roomId
        val entity=replaceIgnoreCase
        val toWhereShortWord=  StringUtils.upperCaseFirst(replaceIgnoreCase)
//        if ("u".equals(toWhere, ignoreCase = true)) {
//            toWhere="User"
//        }else if("o".equals(toWhere, ignoreCase = true)){
//
//        }
        val toWhere=  mapToWholeName(toWhereShortWord)
//        if(toWhere.equals())
//        columnInfo.columnCommentShow
    val javaFieldName=    columnInfo.javaFieldName
        return       """
        const to${toWhere} = (item) => {
            let ${columnInfo.javaFieldName} = item.${columnInfo.javaFieldName};
            router.push({
                name: 'Modify${toWhere}',
                query: { oid: item.oid, number ,$javaFieldName },
                params: { state: 1 },
            });
        };
        
    """.trimIndent()
    }
    return  ""


}


fun  genForeignTableEntity(columnInfo :ColumnInfo): String {
    val javaFieldName=    columnInfo.javaFieldName
    return       """
        const $javaFieldName = reactive({});
        
    """.trimIndent()
}

fun  genForeignTableUiSelectOne(columnInfo :ColumnInfo): String {
    val javaFieldName=    columnInfo.javaFieldName
//    uid
    return       """
        const $javaFieldName = reactive({});
        UiUtil.selectOne(k.$javaFieldName, form,$javaFieldName)
    """.trimIndent()
}



fun  genToTableFuncExport(columnInfo :ColumnInfo): String {
    if ("id".equals(columnInfo.javaFieldName, ignoreCase = true)) {
        return ""
    }

    if (StringUtils.containsIgnoreCase(columnInfo.javaFieldName,"id")) {

        val replaceIgnoreCase = StringUtils.replaceIgnoreCase(
                columnInfo.javaFieldName, "id", "")
//        room
//        roomId
        val entity=replaceIgnoreCase
//        val toWhere=  StringUtils.upperCaseFirst(replaceIgnoreCase)
        val toWhereShortWord=  StringUtils.upperCaseFirst(replaceIgnoreCase)
        val toWhere=  mapToWholeName(toWhereShortWord)
//        columnInfo.columnCommentShow
        val javaFieldName=    columnInfo.javaFieldName
        return       """
        to${toWhere} ,
        
    """.trimIndent()
    }
    return  ""
}

fun  genToTableButton(columnInfo :ColumnInfo): String {
//    StringUtils.
//    columnInfo.javaFieldName.
//    StringUtils.equ
//    "id".equalsIgnoreCase

    if ("id".equals(columnInfo.javaFieldName, ignoreCase = true)) {
        return ""
    }
    if (StringUtils.containsIgnoreCase(columnInfo.javaFieldName,"id")) {
//        equalsIgnoreCase
        val replaceIgnoreCase = StringUtils.replaceIgnoreCase(
                columnInfo.javaFieldName, "id", "")
//        roomId
      val toWhere=  StringUtils.upperCaseFirst(replaceIgnoreCase)
//        columnInfo.columnCommentShow
     return   """
            <el-button
                         size="mini"
                                @click="to${toWhere}(scope.row)"
                            >
                                查看${columnInfo.columnCommentShow}
                            </el-button>
        """.trimIndent()
    }
    return  ""
}

fun gen_form_item_rows_search(columnInfos: List<ColumnInfo>): String {

//    val formItems = columnInfos.map { columnInfo ->
////           gen_form_item_vue3(columnInfo)
//        gen_form_item_vue3_wrap_col_search(columnInfo)
//
//    }
//    val formItems= listOf();
    val formItems = mutableListOf<String>()

    var code: String = "";
    for (columnInfo in columnInfos) {
        val isNumberOrDate = columnInfo.isNumberType || columnInfo.isDateType
        if (!isNumberOrDate) {
            val row = gen_form_item_vue3_wrap_col_search(columnInfo)
//            columnInfo.javaFieldName.contains

//            code += row + "\n"
//            formItems
            formItems.add(row)
        }
    }

    val twoColSet = mutableSetOf<String>()

    //    val twoColSet= setOf<String>()
//    twoColSet.se

    val dateOrNumberList = columnInfos.filter { columnInfo ->
        columnInfo.isDateType || columnInfo.isNumberType }.toList();
    var idx=0

    for (columnInfo in dateOrNumberList) {
//        val isNumberOrDate=  columnInfo.isNumberType|| columnInfo.isDateType
//        if (columnInfo.isNumberType) {
//            rangeSelectCodeGen(columnInfo, formItems, twoColSet)
//            idx++
//        }
        rangeSelectCodeGen(columnInfo, formItems, twoColSet)
        idx++
    }
    if(idx%2==1){
//        多余一个
        val last = dateOrNumberList.last()
        val row = gen_form_item_vue3_wrap_col_search(last)
        formItems.add(row)
    }



//    for (columnInfo in columnInfos) {
////        val isNumberOrDate=  columnInfo.isNumberType|| columnInfo.isDateType
//        if (columnInfo.isNumberType) {
//            rangeSelectCodeGen(columnInfo,formItems,twoColSet)
//            idx++
////            val row = gen_form_item_vue3_wrap_col_search(columnInfo)
//////            twoColSet.
//////            if(row.)
////            if (
////                    twoColSet.size == 2
////            ) {
////                var twoColCode = ""
////                for (s in twoColSet) {
////                    twoColCode + s + "\n"
////                }
////                """
////                 <el-row
////      type="flex"
////                justify="center"
////                style="flex-wrap: wrap; flex-direction: row"
////            >
////            $twoColCode
////            </el-row>
////            """.trimIndent()
////
////                twoColSet.clear()
////            } else {
////                twoColSet.add(row)
////            }
//
//
////            formItems.add(row)
//        }
//    }
//
//    for (columnInfo in columnInfos) {
////        两个一组的
////        val isNumberOrDate=  columnInfo.isNumberType|| columnInfo.isDateType
//        if (columnInfo.isDateType) {
//            rangeSelectCodeGen(columnInfo,formItems,twoColSet)
////            val row = gen_form_item_vue3_wrap_col_search(columnInfo)
////            formItems.add(row)
//        }
//    }
    println("formItems")
    println(formItems)
    return formItems.joinToString("\n")
}

fun gen_form_item_rows_add(columnInfos: List<ColumnInfo>): String {


    val formItems = columnInfos.map { columnInfo ->
//        这里不能 return 不然直接return 这第一个了 是函数return了
        gen_form_item_vue3(columnInfo)
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
    println("formItems")
    println(formItems)
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