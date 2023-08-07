package top.starp.util;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.FileNotFoundException;
//import org.apache.commons.lang3.StringUtils;

public class VariableNameConverter {
    public static void convertVariablesToCamelCase(String code) {
        JavaParser javaParser = new JavaParser();
        CompilationUnit cu = javaParser.parse(code).getResult().get();
        // 解析代码
//        CompilationUnit cu = javaParser.parse(code);

        // 创建访问者对象
        VariableNameVisitor visitor = new VariableNameVisitor();

        // 访问所有方法声明
        visitor.visit(cu, null);

        // 获取转换后的代码
        String updatedCode = cu.toString();
        System.out.println(updatedCode);
    }

    public static void main(String[] args) throws FileNotFoundException {

       String  filePath= "D:\\proj\\springBoot\\code-gen-starp\\src\\main\\java\\top\\starp\\util\\SqlUtil.java";
        String code = FileUtil.readAll(filePath);
        convertVariablesToCamelCase(code);
    }
    private static class VariableNameVisitor extends VoidVisitorAdapter<Void> {
        @Override
        public void visit(MethodDeclaration methodDeclaration, Void arg) {
            super.visit(methodDeclaration, arg);

            // 获取方法体
            Statement body = methodDeclaration.getBody().orElse(null);
            if (body != null) {
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
                    }
                });
            }
        }
    }
}
