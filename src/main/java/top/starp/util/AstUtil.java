package top.starp.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
//import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
//import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
//import org.eclipse.jdt.core.dom.Javadoc;
//import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescription;
import com.github.javaparser.javadoc.description.JavadocDescriptionElement;
//import javassist.Modifier;
import com.github.javaparser.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import javax.lang.model.element.Modifier;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

public class AstUtil {

    public static CompilationUnit getCompilationUnit(String filename) {
//        String filename =   "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp\\BillingUsage.java";
//        CompilationUnit cu = null;
        try(
                FileInputStream in = new FileInputStream(filename);

                ) {
            JavaParser javaParser = new JavaParser();
            Optional<CompilationUnit> result = javaParser.parse(in).getResult();
            CompilationUnit cu = javaParser.parse(in).getResult().get();
//            CompilationUnit cu = javaParser.parse(in).getResult();
            return cu;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


//        try {
//            FileInputStream in = new FileInputStream(filename);
////            JavaParser javaParser = new JavaParser();
////            cu = JavaParser.parse(in);
//
//            JavaParser javaParser = new JavaParser();
//            cu = javaParser.parse(in).getResult().get();
//
//
////            javaParser.parse(in).getResult().ifPresent(compilationUnit -> {
//////                return compilationUnit.get;
////                cu =compilationUnit;
////            }).get();
//
//
////            cu = javaParser.parse(in);
////            cu = javaParser.parse(in);
//            in.close();
//            return cu;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;

    }
    public static Optional<CompilationUnit> getAst(String filename) {
//        String filename =   "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp\\BillingUsage.java";
//        CompilationUnit cu = null;
        try(
                FileInputStream in = new FileInputStream(filename);

                ) {
            JavaParser javaParser = new JavaParser();
            Optional<CompilationUnit> result = javaParser.parse(in).getResult();
//            CompilationUnit cu = javaParser.parse(in).getResult().get();
//            CompilationUnit cu = javaParser.parse(in).getResult();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void classFIled() {

//        String filename = "YourJavaFile.java";
        String filename = "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp\\BillingUsage.java";
        CompilationUnit cu = null;
        try {
            FileInputStream in = new FileInputStream(filename);
//            JavaParser javaParser = new JavaParser();
//            cu = JavaParser.parse(in);

            JavaParser javaParser = new JavaParser();
            cu = javaParser.parse(in).getResult().get();
//            cu = javaParser.parse(in);
//            cu = javaParser.parse(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//       cu.class
        // 获取所有的类成员变量
        assert cu != null;
        NodeList<TypeDeclaration<?>> types = cu.getTypes();
        for (TypeDeclaration<?> type : types) {
            List<FieldDeclaration> fields = type.getFields();
//            generateClassCode()

            String s = CodeGenKt.generateClassCode("", fields);


            for (FieldDeclaration field : fields) {
                // 获取成员变量的类型和名称
                String typeName = field.getElementType().toString();
                String fieldName = field.getVariable(0).getNameAsString();


                // 创建getter方法
                MethodDeclaration getter = new MethodDeclaration();
//                getter.setModifiers(Modifier.PUBLIC);
//                Modifier modifier = Modifier.publicModifier();
//                Modifier.publicModifier().getKeyword();
                Modifier.Keyword keywordPublicModifier = Modifier.publicModifier().getKeyword();
                getter.setModifiers(keywordPublicModifier);
//                Modifier.
                getter.setType(typeName);
                getter.setName("get" + Utils.capitalize(fieldName));
//                getter.setBody(new BlockStmt(new ReturnStmt(fieldName)));


//                'BlockStmt(com.github.javaparser.ast.NodeList<com.github.javaparser.ast.stmt.Statement>)' in 'com.github.javaparser.ast.stmt.BlockStmt' cannot be applied to '(com.github.javaparser.ast.stmt.ReturnStmt)'

                // 创建setter方法
                MethodDeclaration setter = new MethodDeclaration();
                setter.setModifiers(keywordPublicModifier);
//                setter.setModifiers(Modifier.PUBLIC);
                setter.setType(Void.TYPE);
//                Utils.capitalize()
                setter.setName("set" + Utils.capitalize(fieldName));
                setter.setParameters(new NodeList<>(new Parameter(new TypeParameter(typeName), fieldName)));
//                setter.setBody(new BlockStmt(new ExpressionStmt(
//                        new AssignExpr(
//                                new NameExpr(fieldName), new NameExpr(fieldName), AssignExpr.Operator.ASSIGN
//                        ))));


//                setter.setBody(new BlockStmt());

                // 将getter和setter方法添加到类中
                type.getMembers().addLast(getter);
                type.getMembers().addLast(setter);
            }
        }

    }


    //    d(List<JavadocBlockTag> blockTags){
//        // 遍历所有@param标签
//        blockTags.stream()
//                .filter(tag -> tag.getTagName().equals("param"))
//                .forEach(tag -> {
//                    List<JavadocDescriptionElement> elements = tag.getContent().getElements();
//                    if (!elements.isEmpty()) {
//                        JavadocDescriptionElement element = elements.get(0);
//                        String originalParameterName = element.toText().trim();
//                        String convertedParameterName = StringUtils.underlineToCamelCase(originalParameterName);
//                        element.setContent(convertedParameterName);
////                        element.
//
////                        tag.getContent().
//                        // 更新@param标签的名称
//                        tag.setName(convertedParameterName);
//                    }
//                });
//    }
    private static final Logger log = LoggerFactory.getLogger("AstUtil");

    /**
     * NameExpr nameExpr = (NameExpr) expression;
     *
     * @param body Statement body
     */
    public static void statementVariablesUnderlineToCamelCase(Statement body
            , MethodDeclaration methodDeclaration) {
        // 获取方法体
//        Statement body = methodDeclaration.getBody().orElse(null);
        if (body == null) {
            return;
        }

        // 遍历方法体中的所有语句
        body.findAll(Expression.class).forEach(expression -> {


            // 检查表达式是否为变量引用
            if (expression instanceof NameExpr) {
                NameExpr nameExpr = (NameExpr) expression;
                String originalName = nameExpr.getNameAsString();
//                        StringUtils.un
//                        StringUtils.underlineToCamelCase()
                String convertedName = StringUtils.underlineToCamelCase(originalName);
                nameExpr.setName(convertedName);


//                methodDeclaration.getJavadoc();
                // 更新变量的文档注释
//                methodDeclaration.getJavadoc().ifPresent(javadoc -> {
//                    String javadocText = javadoc.toText();
//           log.info("text  javadoc {}",javadocText);
//           log.info("originalName {}",originalName);
//
//                    List<JavadocBlockTag> blockTags = javadoc.getBlockTags();
//                    JavadocBlockTag javadocBlockTag = blockTags.get(0);
//                    javadocBlockTag.getName().ifPresent(name -> {
//                        JavadocDescription content = javadocBlockTag.getContent();
//                        List<JavadocDescriptionElement> elements = content.getElements();
//                        JavadocDescriptionElement javadocDescriptionElement = elements.get(0);
////                        javadocDescriptionElement.toText().
////                        javadocBlockTag.getContent().toText().
//
////                        name.
//                        log.info("name {}",name);
//                    });
////                    blockTags.
//                    JavadocDescription description = javadoc.getDescription();
//                    List<JavadocDescriptionElement> elements = description.getElements();
//
//                    JavadocDescriptionElement javadocDescriptionElement = elements.get(0);
//
////                    javadocDescriptionElement.
//
//
//
////                    javadoc.setContent(updatedComment);
//                    JavadocComment javadocComment = javadoc.toComment();
//
//                    String updatedComment = javadocComment.getContent()
//                            .replace(originalName, convertedName);
//                    log.info("javadocComment {}",javadocComment);
//                    javadocComment.setContent(updatedComment);
//                    log.info("javadocComment upda {}",javadocComment);
//
////                    javadoc.toComment()
//
//                });
//
//
//
//


//                methodDeclaration.doc
//                Javadoc javadoc = methodDeclaration.getJavadoc();
////                javadoc.getAST().newArrayType()
////                javadoc.get
//                String updatedComment =   javadoc.toString()
//                        .replace(originalName, convertedName);
////                String updatedComment = javadoc.toText()
////                        .replace(originalName, convertedName);
////                javadoc.setContent(updatedComment);
//                javadoc.setComment(updatedComment);


//                methodDeclaration.getJavadoc().ifPresent(javadoc -> {
//                    String updatedComment = javadoc.toText()
//                            .replace(originalName, convertedName);
//                    javadoc.setContent(updatedComment);
//                });

            } else if (expression instanceof MethodCallExpr) {
                // 检查语句是否为函数调用
                MethodCallExpr methodCallExpr = (MethodCallExpr) expression;
                String originalMethodName = methodCallExpr.getNameAsString();
                String convertedMethodName = StringUtils.underlineToCamelCase(originalMethodName);
                methodCallExpr.setName(convertedMethodName);

                // 遍历函数调用的参数列表
                NodeList<Expression> arguments = methodCallExpr.getArguments();
                for (Expression argument : arguments) {
                    if (argument instanceof NameExpr) {
                        // 检查参数是否为变量引用
                        NameExpr argumentNameExpr = (NameExpr) argument;
                        String originalArgumentName = argumentNameExpr.getNameAsString();
                        String convertedArgumentName = StringUtils.underlineToCamelCase(originalArgumentName);
                        argumentNameExpr.setName(convertedArgumentName);
                    }
                }
            }

//            else if (expression instanceof Deva)

        });
    }
//    Logger logger = LoggerFactory.getLogger("3131");

    public  static  void  genAndWriteClassCode( String fromJavaCodePath ,String outCodePathBase) throws IOException {
//        String filename = "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp\\BillingUsage.java";

//        CompilationUnit compilationUnit = getCompilationUnit(fromJavaCodePath);

        Optional<CompilationUnit> ast = AstUtil.getAst(fromJavaCodePath);
        if (!ast.isPresent()) {
            log.info("ast is null "+fromJavaCodePath);
            return;
        }
//        String code = CodeGenKt.genClassCode(compilationUnit);
//        log.info(" code {}",code);
//        String className = compilationUnit.getName();


//        String className =null;
//        assert compilationUnit != null;
//        Optional<String> primaryTypeName = compilationUnit.getPrimaryTypeName();
//        if (primaryTypeName.isPresent()) {
//            className = primaryTypeName.get();
//        }





//        String nowTimeStr = TimeUtil.nowTimeStr();
//        String outCodeDir="/file/codeGen";
//
//        String outCodePathBase="{outCodeDir}/codeGen_{nowTimeStr}"
//                .replace("{nowTimeStr}",nowTimeStr)
//                .replace("{outCodeDir}",outCodeDir)
//                ;



//        String outCodePathBase="/file/codeGen/codeGen_{nowTimeStr}"
//                .replace("{nowTimeStr}",nowTimeStr)
//                ;
//        assert compilationUnit != null;

        CompilationUnit compilationUnit = ast.get();
        NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();
        for (TypeDeclaration<?> type : types) {
            String className = type.getNameAsString();
            List<FieldDeclaration> fields = type.getFields();
//            generateClassCode()

//            String code = CodeGenKt.generateClassCode("", fields);
            String code = CodeGenKt.generateClassCode(className, fields);
//            log.info("code {}",code);

//            String outCodePath="/file/codeGen/codeGen_{nowTimeStr}/{className}"
//                    .replace("{nowTimeStr}",nowTimeStr)
//                    .replace("{className}",className)
//                    ;

            String outCodePath=outCodePathBase+"/{className}.java"
//                    .replace("{nowTimeStr}",nowTimeStr)
                    .replace("{className}",className)
                    ;
//            FileUtil.writeToFile(outCodePath,code);
            FileUtil.writeToFileMust(outCodePath,code);
        }
    }

   public static void  genDirClassCode(  String folderPath ) throws IOException {
//        String folderPath =   "D:\\proj\\doctorz\\gungnir-integration\\" +
//                "gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\req";
        String outGenCodeDir = FileUtil.makeCodeGenDir();
        List<File> files = FileUtil.listFiles(folderPath);
        log.info("outGenCodeDir {}",outGenCodeDir);
        for (File file : files) {
//            file.get
//            String originCode = FileUtil.readAll(file);
//            AstUtil.genAndWriteClassCode(originCode,outGenCodeDir);
            AstUtil.genAndWriteClassCode(file.getAbsolutePath(),outGenCodeDir);
        }
    }

    public static void main(String[] args) throws IOException {
//        String folderPath = "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp";

//        String folderPath =   "D:\\proj\\doctorz\\gungnir-integration\\" +
//                "gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\req";

//        String folderPath =  "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\dto";


//        String folderPath =  "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\util";
//        String folderPath =  "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\entity";

        String folderPath =     "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\entity";
        genDirClassCode(folderPath);




//
//        String outGenCodeDir = FileUtil.makeCodeGenDir();
//        List<File> files = FileUtil.listFiles(folderPath);
//        log.info("outGenCodeDir {}",outGenCodeDir);
//        for (File file : files) {
////            file.get
////            String originCode = FileUtil.readAll(file);
////            AstUtil.genAndWriteClassCode(originCode,outGenCodeDir);
//            AstUtil.genAndWriteClassCode(file.getAbsolutePath(),outGenCodeDir);
//        }
//



//        String  folderPath="";
//        File folder = new File(folderPath);
//
//        if (folder.exists() && folder.isDirectory()) {
//            File[] files = folder.listFiles();
//
//            if (files != null) {
//                for (File file : files) {
//                    if (file.isFile()) {
//                        // 处理文件
//                        System.out.println("文件：" + file.getName());
//                    } else if (file.isDirectory()) {
//                        // 处理文件夹
//                        System.out.println("文件夹：" + file.getName());
//                    }
//                }
//            } else {
//                System.out.println("文件夹为空");
//            }
//        } else {
//            System.out.println("文件夹路径不存在或不是一个文件夹");
//        }
    }
    public static void mainGenOneFile(String[] args) throws IOException {
//        classFIled();
        String filename = "D:\\proj\\doctorz\\gungnir-integration\\gungnir-integration-zucc-application\\src\\main\\java\\com\\gungnir\\integration\\api\\openai\\resp\\BillingUsage.java";

        CompilationUnit compilationUnit = getCompilationUnit(filename);
//        String code = CodeGenKt.genClassCode(compilationUnit);
//        log.info(" code {}",code);
//        String className = compilationUnit.getName();


//        String className =null;
//        assert compilationUnit != null;
//        Optional<String> primaryTypeName = compilationUnit.getPrimaryTypeName();
//        if (primaryTypeName.isPresent()) {
//            className = primaryTypeName.get();
//        }


//        String nowTimeStr = TimeUtil.nowTimeStr();
//        String outCodeDir="/file/codeGen";
//        String outCodePathBase="{outCodeDir}/codeGen_{nowTimeStr}"
//                .replace("{nowTimeStr}",nowTimeStr)
//                .replace("{outCodeDir}",outCodeDir)
//                ;

        String outCodePathBase = FileUtil.makeCodeGenDir();


        AstUtil.genAndWriteClassCode(filename,outCodePathBase);

//        String outCodePathBase="/file/codeGen/codeGen_{nowTimeStr}"
//                .replace("{nowTimeStr}",nowTimeStr)
//                ;
//        assert compilationUnit != null;
        NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();
        for (TypeDeclaration<?> type : types) {
            String className = type.getNameAsString();
            List<FieldDeclaration> fields = type.getFields();
//            generateClassCode()

//            String code = CodeGenKt.generateClassCode("", fields);
            String code = CodeGenKt.generateClassCode(className, fields);
//            log.info("code {}",code);

//            String outCodePath="/file/codeGen/codeGen_{nowTimeStr}/{className}"
//                    .replace("{nowTimeStr}",nowTimeStr)
//                    .replace("{className}",className)
//                    ;

            String outCodePath=outCodePathBase+"/{className}.java"
//                    .replace("{nowTimeStr}",nowTimeStr)
                    .replace("{className}",className)
                    ;
//            FileUtil.writeToFile(outCodePath,code);
            FileUtil.writeToFileMust(outCodePath,code);
        }
    }
}
