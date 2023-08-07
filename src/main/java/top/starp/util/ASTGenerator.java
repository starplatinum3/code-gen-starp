//package top.starp.util;
//
////import antlr.ASTVisitor;
//import org.eclipse.jdt.core.dom.*;
//
//public class ASTGenerator {
//    public static void main(String[] args) {
//        // 要解析的 Java 代码
//        String code = "public class HelloWorld {\n" +
//                "    public static void main(String[] args) {\n" +
//                "        System.out.println(\"Hello, World!\");\n" +
//                "    }\n" +
//                "}";
//
//        // 创建 ASTParser 实例
////        ASTParser parser = ASTParser.newParser(AST.JLS14);
//        ASTParser parser = ASTParser.newParser(AST.JLS16);
//
//        // 设置 Java 代码源
//        parser.setSource(code.toCharArray());
//
//        // 解析 Java 代码并生成抽象语法树
//        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
//
//        // 处理抽象语法树，这里可以根据自己的需要进行操作
//        // 例如打印抽象语法树
//        cu.accept(new ASTPrinter());
//    }
//
//    // 自定义 ASTVisitor 子类，用于遍历和处理抽象语法树
//    static class ASTPrinter extends ASTVisitor {
//        @Override
//        public boolean visit(CompilationUnit node) {
//            System.out.println("Package: " + node.getPackage().getName());
//            return super.visit(node);
//        }
//
//        @Override
//        public boolean visit(MethodDeclaration node) {
//            System.out.println("Method: " + node.getName().getIdentifier());
//            return super.visit(node);
//        }
//
////        @Override
////        public void visit(antlr.collections.AST ast) {
////            String text = ast.getText();
////            log.info(text);
//////            System.out.println("Method: " + node.getName().getIdentifier());
//////            super.
//////            return super.visit(node);
////        }
//
//        // TODO: 可以继续实现其他访问方法来处理不同类型的节点
//    }
//}
