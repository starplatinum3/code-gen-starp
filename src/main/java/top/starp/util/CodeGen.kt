package top.starp.util

import com.example.demo.util.codeGen.ColumnInfo

fun genMyBatisPlusEntityField(columnInfo: ColumnInfo, withSwagger: Boolean): String {
    val java字段名 = columnInfo.java字段名
    val commentShow = columnInfo.columnCommentShow
    val column_name = columnInfo.columN_NAME
    val java字段类型 = columnInfo.获取java字段类型()
    val javaFieldType = columnInfo.javaFieldType
    val javaFieldName = columnInfo.javaFieldName
    val withSwaggerApiModelProperty = if (withSwagger) {
        """ @ApiModelProperty("$javaFieldName") """
    } else {
        ""
    }
    return """
        $withSwaggerApiModelProperty
     @TableField("$column_name")
    private $javaFieldType $javaFieldName;
            """.trimIndent()
}

fun genMyBatisPlusEntityFields(columnInfos: List<ColumnInfo>, withSwagger: Boolean): String{
    val res = StringBuilder()
    for (columnInfo in columnInfos) {
        val genMyBatisPlusEntityField = genMyBatisPlusEntityField(columnInfo, withSwagger)
        res.append(genMyBatisPlusEntityField)
    }
    return res.toString()
}