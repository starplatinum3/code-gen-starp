package top.starp.util

import com.example.demo.common.MysqlDataType
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
//    javaFieldType.
    if (columnInfo.isNumberType
            ||columnInfo.isDateType) {
        return """
            
     @TableField(exist = false)
    private $javaFieldType ${javaFieldName}Max;
    
      @TableField(exist = false)
    private $javaFieldType ${javaFieldName}Min;
    
        $withSwaggerApiModelProperty
     @TableField("$column_name")
    private $javaFieldType $javaFieldName;
            """.trimIndent()
    }
    return """
        $withSwaggerApiModelProperty
     @TableField("$column_name")
    private $javaFieldType $javaFieldName;
            """.trimIndent()
}

fun  genReactColumnsItem(columnInfo :ColumnInfo): String {
    val javaFieldName=    columnInfo.javaFieldName
    val columnCommentShow=     columnInfo.columnCommentShow
    return       """
    {
      title: '$columnCommentShow',
      dataIndex: '$javaFieldName',
      width: 80,
      render: (_, record, index) => <>{(pagination.current - 1) * pagination.pageSize + index + 1}</>,
    },
        
    """.trimIndent()
}

fun genMybatisPlusSelectPageLikeRows(columnInfos:List<ColumnInfo>
                                     ,entityName:String

                                     ,className:String): String{
    val MybatisPlusSelectPageLikeRows = java.lang.StringBuilder()
    //        类名
    for (columnInfo in columnInfos) {
        val java字段名 = columnInfo.java字段名
        val java字段名开头大写 = columnInfo.java字段名开头大写
        val commentShow = columnInfo.columnCommentShow
        val column_type = columnInfo.columN_TYPE
      val javaFieldNameStartsWithUppercase=  columnInfo.javaFieldNameStartsWithUppercase
//        columnInfo.nam
        var compareType = "like"
        if (MysqlDataType.isNumberType(column_type)) {
            compareType = "eq"
        }

        var row = """.$compareType(
                            !StringUtils.isNullOrEmpty($entityName.get$javaFieldNameStartsWithUppercase()),
                           $className::get$javaFieldNameStartsWithUppercase,
                            $entityName.get$javaFieldNameStartsWithUppercase()
                    )
        """

        if (columnInfo.isNumberType
                ||columnInfo.isDateType) {
             row = """
                    .le(
                            !StringUtils.isNullOrEmpty($entityName.get${javaFieldNameStartsWithUppercase}Max()),
                           $className::get${javaFieldNameStartsWithUppercase}Max,
                            $entityName.get${javaFieldNameStartsWithUppercase}Max()
                     )
                      .ge(
                            !StringUtils.isNullOrEmpty($entityName.get${javaFieldNameStartsWithUppercase}Min()),
                           $className::get${javaFieldNameStartsWithUppercase}Min,
                            $entityName.get${javaFieldNameStartsWithUppercase}Min()
                     )
                  
                         """
        }
        //            columnInfo

//        row = row
//                .replace("#commentShow#", commentShow)
//                .replace("#java字段名#", java字段名)
//                .replace("#实体名#", 实体名)
//                .replace("#类名#", 类名)
//                .replace("#java字段名开头大写#", java字段名开头大写)
//                .replace("{compareType}", compareType)
        MybatisPlusSelectPageLikeRows.append(row)
    }
    return MybatisPlusSelectPageLikeRows.toString()
}

fun genMyBatisPlusEntityFields(columnInfos: List<ColumnInfo>, withSwagger: Boolean): String{
    val res = StringBuilder()
    for (columnInfo in columnInfos) {
        val genMyBatisPlusEntityField = genMyBatisPlusEntityField(columnInfo, withSwagger)
        res.append(genMyBatisPlusEntityField)
    }
    return res.toString()
}