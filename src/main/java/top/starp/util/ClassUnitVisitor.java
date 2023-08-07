package top.starp.util;

import java.util.Optional;

import com.github.javaparser.ast.expr.SimpleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassUnitVisitor extends VoidVisitorAdapter<Object> {
    Logger log  = LoggerFactory.getLogger(ClassUnitVisitor.class);
    //alt+shift m 成员方法
    
    /**
     * 包名
     */
     @Override
     public void visit(PackageDeclaration n, Object arg) {
         System.out.println("package---------"+n.getName());
         super.visit(n, arg);
     }
     
     /**
      * 支持包
      */
     @Override
    public void visit(ImportDeclaration n, Object arg) {
         log.info("ImportDeclaration-------------"+n.toString());
        super.visit(n, arg);
    }
     
    /**
     * 类
     */
    @Override
    public void visit(ClassOrInterfaceDeclaration classOrInterfaceDeclaration, Object arg) {
        log.info("arg-----------"+arg);//null
        log.info("n.toString()--------"+classOrInterfaceDeclaration.toString());//整个类的内容,不包括package和import
        log.info("n.getNameAsString()---------"+classOrInterfaceDeclaration.getNameAsString());//类名
        //获取类的注解
        NodeList<AnnotationExpr> annotations = classOrInterfaceDeclaration.getAnnotations();
        //判断是否有注解
        if(annotations.size()>0) {
            for (AnnotationExpr annotation : annotations) {
                log.info(annotation.toString());
            }
        }
        //获取类的注释
        Optional<JavadocComment> javadocComment = classOrInterfaceDeclaration.getJavadocComment();
        log.info(javadocComment.toString());
        super.visit(classOrInterfaceDeclaration, arg);
    }
    
    
    /**
     * 成员方法
     */
    @Override
    public void visit(MethodDeclaration methodDeclaration, Object arg) {
        log.info("********************** isit(MethodDeclaration methodDeclaration *************************************");
//        log.info("arg-----------"+arg);//null
//        log.info("n.toString()--------"+methodDeclaration.toString());//整个方法的内容
        String methodDeclarationString = methodDeclaration.toString();
        String nameAsString = methodDeclaration.getNameAsString();



        String pascalCaseFuncName = StringUtils.toPascalCase(nameAsString);
        String CamelCaseFuncName = StringUtils.underlineToCamelCase(nameAsString);
//        String methodCodeOfPascalCaseFuncName = methodDeclarationString.replace(nameAsString, pascalCaseFuncName);
//        String methodCodeOfCamelCaseFuncName = methodDeclarationString.replace(nameAsString, CamelCaseFuncName);

        methodDeclaration.setName(CamelCaseFuncName);
//        log.info("methodCodeOfPascalCaseFuncName {}",methodCodeOfPascalCaseFuncName);
//        log.info("methodCodeOfCamelCaseFuncName {}",methodCodeOfCamelCaseFuncName);


        log.info("n.getNameAsString()---------"+methodDeclaration.getNameAsString());//方法名
        log.info("n.getParameters()---------"+methodDeclaration.getParameters());//所有参数
//        NodeList<Parameter> parameters1 = methodDeclaration.getParameters();
        NodeList<Parameter> parameters = methodDeclaration.getParameters();
        for (Parameter parameter : parameters) {
            SimpleName name = parameter.getName();
            String identifier = name.getIdentifier();
            log.info("identifier {}",identifier);
//            log.info(identifier);
            log.info(parameter.toString());

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
        Optional<JavadocComment> javadocComment = methodDeclaration.getJavadocComment();
//        log.info(javadocComment.toString());
        //获取方法里面的方法
        /*CompilationUnit cu = JavaParser.parse(n.toString());
        cu.accept(new methodUnit(), null);*/
        super.visit(methodDeclaration, arg);
    }
    
    /**
     * 变量
     */
    @Override
    public void visit(VariableDeclarator n, Object arg) {
        log.info("VariableDeclarator--------"+n.toString());
        log.info("VariableDeclarator--------");
        //bContentService  成员变量
        //bContentDO = bContentService.get(cid)  方法内部的变量
        super.visit(n, arg);
    }
    
    /**
     * 成员变量的注解
     */
    @Override
    public void visit(FieldDeclaration n, Object arg) {
        log.info("FieldDeclaration###############"+n.toString());//@Autowired
        System.out.println("所在位置的行号,int类型"+n.getRange().get().begin.line);
        super.visit(n, arg);
    }
    
}

 
//————————————————
//版权声明：本文为CSDN博主「shijiaolong0」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/shijiaolong0/article/details/82083964