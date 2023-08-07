package top.starp.util

import com.example.demo.common.MysqlDataType
import com.example.demo.util.codeGen.ColumnInfo
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.Modifier
import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.type.Type
import java.lang.reflect.Field


import com.squareup.kotlinpoet.*
//import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy


//fun  d( fields :List<FieldDeclaration>){
//    for (field in fields) {
////        field.ge
//        // 获取成员变量的类型和名称
//      val   typeName = field.elementType.toString();
//        val fieldName = field.getVariable(0).nameAsString;
//
//        """
//              /**
//             * 用户ID
//             */
//            @JsonProperty(value = "user_id")
//            private String userId;
//        """.trimIndent()
//    }
//}

//fun generateClassCode(className: String, fields: List<FieldDeclaration>): String {
////    serialVersionUID
//    val propertyDeclarations = fields.map { field ->
//        val typeName = field.elementType.toString()
//        val fieldName = field.getVariable(0).nameAsString
//
////        StringUtils.c
//        """
//        /**
//         * ${fieldName.capitalize()}
//         */
//        @JsonProperty(value = "${fieldName}")
//        private $typeName $fieldName;
//        """.trimIndent()
//    }
//
//    val getterSetterMethods = fields.map { field ->
//        val fieldName = field.getVariable(0).nameAsString
//        val capitalizedFieldName = fieldName.capitalize()
//        val typeName = field.elementType.toString()
//
//        """
//        /**
//         * 获取$fieldName
//         */
//        public $typeName get$capitalizedFieldName() {
//            return $fieldName;
//        }
//
//        /**
//         * 设置$fieldName
//         */
//        public void set$capitalizedFieldName($typeName $fieldName) {
//            this.$fieldName = $fieldName;
//        }
//        """.trimIndent()
//    }
//
//    val builderPattern = fields.joinToString(", ") { field ->
//        val typeName = field.elementType.toString()
//        val fieldName = field.getVariable(0).nameAsString
//
//        "$fieldName($fieldName)"
//    }
//
//    val constructorParameters = fields.joinToString(", ") { field ->
//        val typeName = field.elementType.toString()
//        val fieldName = field.getVariable(0).nameAsString
//
//        "$typeName $fieldName"
//    }
//
//    val constructorAssignments = fields.joinToString("\n") { field ->
//        val fieldName = field.getVariable(0).nameAsString
//
//        "this.$fieldName = $fieldName;"
//    }
//
//    return """
//
//        import com.fasterxml.jackson.annotation.JsonProperty;
//        import com.gungnir.integration.util.JsonUtil;
//
//        public class $className {
//            ${propertyDeclarations.joinToString("\n")}
//
//            public $className($constructorParameters) {
//                $constructorAssignments
//            }
//
//             public $className() {
//            }
//
//            ${getterSetterMethods.joinToString("\n")}
//
//
//
//            public static class Builder {
//                ${fields.map { field ->
//        val typeName = field.elementType.toString()
//        val fieldName = field.getVariable(0).nameAsString
//
//        "private $typeName $fieldName;"
//
//    }.joinToString("\n")}
//
//                ${fields.map { field ->
//        val typeName = field.elementType.toString()
//        val fieldName = field.getVariable(0).nameAsString
//        val capitalizedFieldName = fieldName.capitalize()
//
//        """
//                    public Builder ${fieldName}($typeName $fieldName) {
//                        this.$fieldName = $fieldName;
//                        return this;
//                    }
//                    """.trimIndent()
//
//    }.joinToString("\n")}
//
//                public $className build() {
//                    return new $className(${fields.map { field ->
//        field.getVariable(0).nameAsString
//    }.joinToString(", ")});
//                }
//            }
//
//
//            public static   Builder builder(){
//                return new Builder();
//            }
//
//            @Override
//            public String toString() {
//                try {
//                   return JsonUtil.toJsonString(this);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//
//
//
//
//
//
//
//
//        }
//    """.trimIndent()
//}

fun generateClassCode(className: String, fields: List<FieldDeclaration>): String {
    val filteredFields = fields.filter { field ->
        field.getVariable(0).nameAsString != "serialVersionUID"
    }

    val propertyDeclarations = filteredFields.map { field ->
        val typeName = field.elementType.toString()
        val fieldName = field.getVariable(0).nameAsString

        """
        /**
         * ${fieldName.capitalize()}
         */
        @JsonProperty(value = "${fieldName}")
        private $typeName $fieldName;
        """.trimIndent()
    }

    val getterSetterMethods = filteredFields.map { field ->
        val fieldName = field.getVariable(0).nameAsString
        val capitalizedFieldName = fieldName.capitalize()
        val typeName = field.elementType.toString()

        """
        /**
         * 获取$fieldName
         */
        public $typeName get$capitalizedFieldName() {
            return $fieldName;
        }

        /**
         * 设置$fieldName
         */
        public void set$capitalizedFieldName($typeName $fieldName) {
            this.$fieldName = $fieldName;
        }
        """.trimIndent()
    }

    val builderPattern = filteredFields.joinToString(", ") { field ->
        val fieldName = field.getVariable(0).nameAsString

        "$fieldName($fieldName)"
    }

    val constructorParameters = filteredFields.joinToString(", ") { field ->
        val typeName = field.elementType.toString()
        val fieldName = field.getVariable(0).nameAsString

        "$typeName $fieldName"
    }

    val constructorAssignments = filteredFields.joinToString("\n") { field ->
        val fieldName = field.getVariable(0).nameAsString

        "this.$fieldName = $fieldName;"
    }

    return """
        
        import com.fasterxml.jackson.annotation.JsonProperty;
        import com.gungnir.integration.util.JsonUtil;
        
        public class $className {
            ${propertyDeclarations.joinToString("\n")}
            
            public $className($constructorParameters) {
                $constructorAssignments
            }
            
            public $className() {
            }
            
            ${getterSetterMethods.joinToString("\n")}
            
            
            
            public static class Builder {
                ${filteredFields.map { field ->
        val typeName = field.elementType.toString()
        val fieldName = field.getVariable(0).nameAsString

        "private $typeName $fieldName;"

    }.joinToString("\n")}
                
                ${filteredFields.map { field ->
        val typeName = field.elementType.toString()
        val fieldName = field.getVariable(0).nameAsString
        val capitalizedFieldName = fieldName.capitalize()

        """
                    public Builder ${fieldName}($typeName $fieldName) {
                        this.$fieldName = $fieldName;
                        return this;
                    }
                    """.trimIndent()

    }.joinToString("\n")}
                
                public $className build() {
                    return new $className(${filteredFields.map { field ->
        field.getVariable(0).nameAsString
    }.joinToString(", ")});
                }
            }
            
            
            public static Builder builder(){
                return new Builder();
            }   
            
            @Override
            public String toString() {
                try {
                   return JsonUtil.toJsonString(this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            
            
            
            
            
        }
    """.trimIndent()
}


//
//val fields = listOf(
//    FieldDeclaration().apply {
//        elementType = "String"
//        addVariable(VariableDeclarator("userId"))
//    },
//    FieldDeclaration().apply {
//        elementType = "int"
//        addVariable(VariableDeclarator("age"))
//    }
//)
//
//val className = "User"
//
//val generatedCode = generateClassCode(className, fields)
//println(generatedCode)



//fun genClassCode(cu: CompilationUnit): String {
//    val className = cu.primaryTypeName.get()
//    val packageName = cu.packageDeclaration.get().nameAsString
//
//    val fileSpec = FileSpec.builder(packageName, className)
//        .addType(buildClassSpec(cu))
//        .build()
//
//    return fileSpec.toString()
//}

//private fun buildClassSpec(cu: CompilationUnit): TypeSpec {
//    val typeName = cu.primaryTypeName.get()
//    val classBuilder = TypeSpec.classBuilder(typeName)
//
//    // 构建字段声明
//    val types = cu.types
//    if (types.isNotEmpty()) {
//        val typeDeclaration = types[0]
//        val fields = typeDeclaration.fields
//        for (field in fields) {
//           val elementType: Type? = field.elementType
//            val fieldType = field.elementType.toString()
//            val fieldName = field.variables[0].nameAsString
////            PropertySpec.builder(fieldName, fieldType).build()
////            TypeName.
////            classBuilder.addProperty(PropertySpec.builder(fieldName, fieldType).build())
////            classBuilder.addProperty(PropertySpec.builder(fieldName,  field.elementType).build())
//
//            if (elementType != null) {
//                // 处理非空情况
//                val fieldType = elementType.toString()
//                val fieldName = field.variables[0].nameAsString
////                classBuilder.addProperty(PropertySpec.builder(fieldName, elementType,Modif).build())
//
//                classBuilder.addProperty(PropertySpec.builder(fieldName, elementType!!)
//                    .addModifiers(Modifier.PUBLIC)  // 添加修饰符
//                    .build())
//
//            }
//
////            classBuilder.addProperty(PropertySpec.builder(fieldName,  elementType).build())
//        }
//    }
//
//    // 构建构造函数
//    classBuilder.primaryConstructor(FunSpec.constructorBuilder().build())
//
//    // 构建其他方法、内部类等...
//
//    return classBuilder.build()
//}

fun  genGetAndSet( fieldType:Class<*>,fieldName:String): String {
   return genGetAndSet( fieldType.simpleName,fieldName)
//  val  capitalizedFieldName= capitalize(fieldName)
//  val  FieldName= capitalize(fieldName)

//
//    val  FieldName=    StringUtils.captureName(fieldName)
//    val  typeName=  fieldType.simpleName;
//
//   val code= """
//        public ${typeName} get${FieldName}() {
//            return this.${fieldName};
//        }
//        public void set${FieldName}(${typeName} ${fieldName}) {
//            this.${fieldName} = ${fieldName};
//        }
//
//    """.trimIndent()
//    return code;
}


//fun genToStringAndBuilderCode(clazz: Class<*>): String {
fun genClassCode(clazz: Class<*>): String {
    val className = clazz.simpleName
//    val fields = clazz.declaredFields
    val fields = clazz.declaredFields.filter { it.name != "serialVersionUID" }.toTypedArray()
//    val fieldsArray = fields.toTypedArray()
//    val fields = clazz.declaredFields.filter { it.name != "serialVersionUID" }
    val toStringCode = genToStringCode(fields)
    val builderCode = genBuilderCode(className, fields)

    val code = """
        |public class $className {
        |    ${genFieldsCode(clazz)} // 生成字段声明代码
        |
        |    public $className(${genParametersCode(fields)}) {
        |        ${genAssignmentsCode(fields)} // 生成参数赋值语句
        |        // 编写其他构造器逻辑
        |    }
        |
        |    ${genGettersCode(clazz)} // 生成 getter 方法代码
        |
        |    ${genSettersCode(clazz)} // 生成 setter 方法代码
        |
        |    ${genToStringMethod(toStringCode)} // 生成 toString() 方法代码
        |
        |    ${genBuilderClass(builderCode)} // 生成 Builder 类代码
        |
        |    // 编写其他类逻辑
        |}
    """.trimMargin()

    return code
}

fun genParametersCode(fields: Array<Field>): String {
    return fields.joinToString(", ") { "${it.type.simpleName} ${it.name}" } // 生成构造器参数列表字符串
}

fun genAssignmentsCode(fields: Array<Field>): String {
    return fields.joinToString("\n") { "this.${it.name} = ${it.name};" } // 生成参数赋值语句
}

fun genToStringCode(fields: Array<Field>): String {
    return """
        try {
            return JsonUtil.toJsonString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    """.trimIndent()
//    return fields.joinToString(", ") { "${it.name}=" + "\${${it.name}}" } // 生成toString中的字段拼接
}

fun genBuilderCode(className: String, fields: Array<Field>): String {
    val builderFields = fields.joinToString("\n") { "private ${it.type.simpleName} ${it.name};" }
//    val builderSetters = fields.joinToString("\n") { "public Builder set${StringUtils.captureName(it.name)}(${it.type.simpleName} ${it.name}) { this.${it.name} = ${it.name}; return this; }" }
    val builderSetters = fields.joinToString("\n") { "public Builder ${it.name}(${it.type.simpleName} ${it.name}) { this.${it.name} = ${it.name}; return this; }" }
    val builderBuild = fields.joinToString(",\n") { "${it.name}" }
    val buildMethod = fields.joinToString("\n") { "this.${it.name} = builder.${it.name};" }

    return """
        |public static class Builder {
        |    $builderFields // 生成 Builder 类的字段声明代码
        |
        |    public Builder() {}
        |
        |    $builderSetters // 生成 Builder 类的 setter 方法代码
        |
        |    public $className build() {
        |        $className instance = new $className();
        |        $buildMethod // 生成构建实例时的赋值语句
        |        return instance;
        |    }
        |}
    """.trimMargin()
}

fun genToStringMethod(toStringCode: String): String {
//    |    return "$toStringCode"; // 生成 toString() 方法代码
    return """
        |@Override
        |public String toString() {
        |  $toStringCode
        |}
    """.trimMargin()
}

fun genBuilderClass(builderCode: String): String {
//    newBuilder
//    builder
    return """
        |public static Builder builder() {
        |    return new Builder();
        |}
        |
        |$builderCode // 生成 Builder 类代码
    """.trimMargin()
}

//fun genConstructorAndLogicCode(className: String, parameterNames: List<String>): String {
//    val parameterList = parameterNames.joinToString(", ") { "$it: Type" } // 将参数名称列表转换为参数列表字符串
//    val assignments = parameterNames.joinToString("\n") { "this.$it = $it;" } // 生成参数赋值语句
//
//    val code = """
//        |public class $className {
//        |    ${genFieldsCode(parameterNames)} // 生成字段声明代码
//        |
//        |    public $className($parameterList) {
//        |        $assignments  // 生成参数赋值语句
//        |        // 编写其他构造器逻辑
//        |    }
//        |
//        |    ${genGettersCode(parameterNames)} // 生成 getter 方法代码
//        |
//        |    ${genSettersCode(parameterNames)} // 生成 setter 方法代码
//        |
//        |    // 编写其他类逻辑
//        |}
//    """.trimMargin()
//
//    return code
//}
//


fun genFieldsCode(clazz: Class<*>): String {
//    val fields = clazz.declaredFields
//    val fields = clazz.declaredFields 排除 serialVersionUID
    val fields = clazz.declaredFields.filter { it.name != "serialVersionUID" }

//    serialVersionUID
//    fields.
    return fields.joinToString("\n") { "private ${it.type.simpleName} ${it.name};" } // 生成字段声明代码
}

fun genGettersCode(clazz: Class<*>): String {
//    val fields = clazz.declaredFields
    val fields = clazz.declaredFields.filter { it.name != "serialVersionUID" }
    return fields.joinToString("\n\n") { "public ${it.type.simpleName} get${StringUtils.captureName(it.name)}() { return this.${it.name}; }" } // 生成 getter 方法代码
}

fun genSettersCode(clazz: Class<*>): String {
//    val fields = clazz.declaredFields
    val fields = clazz.declaredFields.filter { it.name != "serialVersionUID" }
    return fields.joinToString("\n\n") { "public void set${StringUtils.captureName(it.name)}(${it.type.simpleName} ${it.name}) { this.${it.name} = ${it.name}; }" } // 生成 setter 方法代码
}

fun genFieldsCode(fieldNames: List<String>): String {
    return fieldNames.joinToString("\n") { "private Type $it;" } // 生成字段声明代码
}

fun genGettersCode(fieldNames: List<String>): String {
    return fieldNames.joinToString("\n\n") { "public Type get${StringUtils.captureName(it)}() { return this.$it; }" } // 生成 getter 方法代码
}

fun genSettersCode(fieldNames: List<String>): String {
    return fieldNames.joinToString("\n\n") { "public void set${StringUtils.captureName(it)}(Type $it) { this.$it = $it; }" } // 生成 setter 方法代码
}


fun  genGetAndSet( typeName:String,fieldName:String): String {
//    genGetAndSet( Class.forName(typeName),fieldName)
//  val  capitalizedFieldName= capitalize(fieldName)
//    val  FieldName= capitalize(fieldName)
    val  FieldName=    StringUtils.captureName(fieldName)
//    fieldName.capitalize()
//    String.ca
//    val  typeName=  fieldType.simpleName;
    val code= """
        public ${typeName} get${FieldName}() {
            return this.${fieldName};
        }
        public void set${FieldName}(${typeName} ${fieldName}) {
            this.${fieldName} = ${fieldName};
        }

    """.trimIndent()
    return code;
}

//fun capitalize(fieldName: String) {
//
//}

fun changeCodeGen(  tableName:String="columnInfo_pair_env"): String {

      if (!tableName.contains(k._pair_)) {
            return "";
        }
//    val parent="columnInfo"
//    val son="env"


   val parts= tableName.split( k._pair_)
    val parent=  parts[0]
    val son=  parts[1]
//    changeCodeGen(parent,son)

   return """
        
        ${changeCodeGen(parent,son)}
        
         ${changeCodeGen(son,parent)}
         
    """.trimIndent()
//    val Parent=StringUtils.toPascalCase(parent)
//    val Son=StringUtils.toPascalCase(son)
//  return  """
//  @PostMapping("/change")
//        @ApiOperation(value = "change", notes = "change")
//        public Object change(@RequestBody List<${Parent}Pair${Son}> list){
//                List<Integer> ${son}Ids = list.stream().map({Parent}Pair${Son}::get${Son}Id).collect(Collectors.toList());
//                ${parent}Pair${Son}Repository.deleteAllBy${Son}IdIn(${son}Ids);
//                List<${Parent}Pair${Son}> ${parent}Pair${Son}s = ${parent}Pair${Son}Repository.saveAll(list);
//                return ReturnT.success(${parent}Pair${Son}s);
//        }
//    """.trimIndent()
}


fun changeCodeGen(     parent:String,
                     son:String): String {

    val Parent=StringUtils.toPascalCase(parent)
    val Son=StringUtils.toPascalCase(son)
    return  """
  @PostMapping("/change")
        @ApiOperation(value = "change", notes = "change")
        public Object change(@RequestBody List<${Parent}Pair${Son}> list){
                List<Integer> ${son}Ids = list.stream().map({Parent}Pair${Son}::get${Son}Id).collect(Collectors.toList());  
                ${parent}Pair${Son}Repository.deleteAllBy${Son}IdIn(${son}Ids);
                List<${Parent}Pair${Son}> ${parent}Pair${Son}s = ${parent}Pair${Son}Repository.saveAll(list);
                return ReturnT.success(${parent}Pair${Son}s);
        }
    """.trimIndent()
}


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
        var compareType =  k.like
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
                  .eq(
                            !StringUtils.isNullOrEmpty($entityName.get${javaFieldNameStartsWithUppercase}()),
                           $className::get${javaFieldNameStartsWithUppercase},
                            $entityName.get${javaFieldNameStartsWithUppercase}()
                     )
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