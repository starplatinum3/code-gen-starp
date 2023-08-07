package top.starp.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.JavadocBlockTag;
import com.github.javaparser.javadoc.description.JavadocDescriptionElement;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
public class MethodVisitor extends VoidVisitorAdapter<StringBuilder> {
    @Override
    public void visit(MethodDeclaration methodDeclaration, StringBuilder stringBuilder) {
//        stringBuilder.append(methodDeclaration.toString());
        setMethodDeclarationCamelCase(methodDeclaration);
        stringBuilder.append(methodDeclaration).append("\n\n\n");
        super.visit(methodDeclaration, stringBuilder);
    }

    @Override
    public void visit(VariableDeclarator variableDeclarator, StringBuilder stringBuilder) {
        String identifier = variableDeclarator.getName().getIdentifier();
//        log.info("identifier {}",identifier);
        String camelCaseIdentifier = StringUtils.underlineToCamelCase(identifier);
        variableDeclarator.getName().setIdentifier(camelCaseIdentifier);
//        需要把方法块里面的 函数调用名字 和参数都改成underlineToCamelCase
//        String updateSet = SqlUtil.to_update_set(data_map, id_col_name);
        super.visit(variableDeclarator, stringBuilder);
    }

    @Override
    public void visit(MethodCallExpr methodCall, StringBuilder arg) {
        super.visit(methodCall, arg);

        log.info("visit(MethodCallExpr me methodCall {}",methodCall);
        // 获取方法调用的名称
        String name = methodCall.getNameAsString();

        // 将方法调用的名称改成underlineToCamelCase形式
        String convertedName = StringUtils.underlineToCamelCase(name);
        methodCall.setName(convertedName);

        // 获取方法调用的参数列表
        NodeList<Expression> arguments = methodCall.getArguments();

        // 遍历参数列表，将每个参数改成underlineToCamelCase形式
        for (Expression argument : arguments) {
            if (argument.isStringLiteralExpr()) {
                // 如果参数是字符串字面量表达式，则将参数内容改成underlineToCamelCase形式
                String argumentString = argument.asStringLiteralExpr().getValue();
                String convertedArgumentString = StringUtils.underlineToCamelCase(argumentString);
                argument.asStringLiteralExpr().setValue(convertedArgumentString);
            } else if (argument.isMethodCallExpr()) {
                // 如果参数是方法调用表达式，则继续递归地访问方法调用表达式中的函数调用
                visit(argument.asMethodCallExpr(), arg);
            }
            // 其他类型的参数可以根据需求进行相应的处理
        }
    }


//    @Override
//    public void visit(JavadocComment javadocComment, StringBuilder arg) {
//        super.visit(javadocComment, arg);
//
////        javadocComment.get
//        // 获取所有JavadocBlockTag
//        List<JavadocBlockTag> blockTags = javadocComment.getBlockTags();
//
//        // 遍历所有@param标签
//        blockTags.stream()
//                .filter(tag -> tag.getTagName().equals("param"))
//                .forEach(tag -> {
//                    List<JavadocDescriptionElement> elements = tag.getContent().getElements();
//                    if (!elements.isEmpty()) {
//                        JavadocDescriptionElement element = elements.get(0);
//                        String originalParameterName = element.toText().trim();
//                        String convertedParameterName = StringUtils.underscoreToCamel(originalParameterName);
//                        element.setContent(convertedParameterName);
//
//                        // 更新@param标签的名称
//                        tag.setName(convertedParameterName);
//                    }
//                });
//    }
//


   void  setMethodDeclarationCamelCase(MethodDeclaration methodDeclaration){
        log.info("********************** isit(MethodDeclaration methodDeclaration *************************************");
//        log.info("arg-----------"+arg);//null
//        log.info("n.toString()--------"+methodDeclaration.toString());//整个方法的内容
        String methodDeclarationString = methodDeclaration.toString();
        String nameAsString = methodDeclaration.getNameAsString();



        String pascalCaseFuncName = StringUtils.toPascalCase(nameAsString);
        String CamelCaseFuncName = StringUtils.underlineToCamelCase(nameAsString);
//        String methodCodeOfPascalCaseFuncName = methodDeclarationString.replace(nameAsString, pascalCaseFuncName);
//        String methodCodeOfCamelCaseFuncName = methodDeclarationString.replace(nameAsString, CamelCaseFuncName);

//       AstUtil.statementVariablesUnderlineToCamelCase();
        methodDeclaration.setName(CamelCaseFuncName);
       Optional<BlockStmt> body = methodDeclaration.getBody();
//       函数块里面的函数调用也要 UnderlineToCamelCase
       if (body.isPresent()) {



           BlockStmt blockStmt = body.get();
           AstUtil.statementVariablesUnderlineToCamelCase(blockStmt,methodDeclaration);
           NodeList<Statement> statements = blockStmt.getStatements();
           Statement statement = statements.get(0);
//           statement.aste
           /**
            * String dropTpl = "DROP TABLE IF EXISTS `{tableName}`;\n"; is not AssertStmt, it is ExpressionStmt
            */
//           AssertStmt assertStmt = statement.asAssertStmt();
//           statement.as
           log.info("blockStmt {}",blockStmt);
//           blockStmt.
//           assertStmt.
//           statement.getParsed()
//           VoidVisitorAdapter<Void> variableUnderlineToCamelCaseVisitor = new VoidVisitorAdapter<Void>() {
//               @Override
//               public void visit(VariableDeclarator variableDeclarator, Void arg) {
//                   String identifier = variableDeclarator.getName().getIdentifier();
//                   String camelCaseIdentifier = StringUtils.underlineToCamelCase(identifier);
//                   variableDeclarator.getName().setIdentifier(camelCaseIdentifier);
//
//                   super.visit(variableDeclarator, arg);
//               }
//           };
           VariableUnderlineToCamelCaseVisitor
                   variableUnderlineToCamelCaseVisitor=new VariableUnderlineToCamelCaseVisitor();

           variableUnderlineToCamelCaseVisitor.visit(blockStmt, null);
//           variableUnderlineToCamelCaseVisitor.visit(statement, null);
       }
//        log.info("methodCodeOfPascalCaseFuncName {}",methodCodeOfPascalCaseFuncName);
//        log.info("methodCodeOfCamelCaseFuncName {}",methodCodeOfCamelCaseFuncName);


//        log.info("n.getNameAsString()---------"+methodDeclaration.getNameAsString());//方法名
//        log.info("n.getParameters()---------"+methodDeclaration.getParameters());//所有参数
//        NodeList<Parameter> parameters1 = methodDeclaration.getParameters();
        NodeList<Parameter> parameters = methodDeclaration.getParameters();
        for (Parameter parameter : parameters) {
            SimpleName name = parameter.getName();
            String identifier = name.getIdentifier();
//            log.info("identifier {}",identifier);
//            log.info(identifier);
//            log.info(parameter.toString());

            parameter.setName(StringUtils.underlineToCamelCase(identifier));
        }

        log.info("methodDeclaration  underlineToCamelCase  {}",methodDeclaration);
        //获取方法的注解
        NodeList<AnnotationExpr> annotations = methodDeclaration.getAnnotations();
        //判断是否有注解
        if(annotations.size()>0) {
            for (AnnotationExpr annotation : annotations) {
                log.info(annotation.toString());
            }
        }
        //获取方法的注释
//        Optional<JavadocComment> javadocComment = methodDeclaration.getJavadocComment();
    }

    public static void main(String[] args) throws IOException {
        String  filePath= "D:\\proj\\springBoot\\code-gen-starp\\src\\main\\java\\top\\starp\\util\\SqlUtil.java";

        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);

        JavaParser javaParser = new JavaParser();
//        CompilationUnit cu = javaParser.parse(fis);
//        CompilationUnit cu = JavaParser.parse(fis);
        ParseResult<CompilationUnit> parse = javaParser.parse(fis);
//        parse.isSuccessful()
        CompilationUnit compilationUnit = parse.getResult().get();

        StringBuilder stringBuilder = new StringBuilder();
        VoidVisitor<StringBuilder> methodVisitor = new MethodVisitor();
        methodVisitor.visit(compilationUnit, stringBuilder);

        String code = stringBuilder.toString();
        System.out.println("code");
        System.out.println(code);
        String nowTimeStr = TimeUtil.nowTimeStr();
       String  filename= "/file/codeGenAst/CamelCaseSqlUtil_{nowTimeStr}.java"
               .replace("{nowTimeStr}",nowTimeStr);
        FileUtil.writeToFileMust(filename,code);
    }
}
