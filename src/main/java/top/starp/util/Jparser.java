package top.starp.util;

import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;

public class Jparser {
    public static void main(String[] args) {
        try {
            JavaParser javaParser = new JavaParser();
            String  filePath=   "D:\\proj\\springBoot\\code-gen-starp\\src\\main\\java\\top\\starp\\util\\SqlUtil.java";
//          String  filePath=  "D:\\proj\\springBoot\\code-gen-starp\\src\\main\\java\\top\\starp\\util\\TimeUtil.java";
            File file = new File(filePath);
//            File file = new File("D:\\Atools\\sts-bundle\\stsWorkspace\\bootdo2\\src\\main\\java\\com\\bootdo\\jparser\\DetectedController.java");
            //将一个java代码的文本解析为一棵CompilationUnit类型的树
//            CompilationUnit unit = JavaParser.parse(
//                    file
//            );


            
            ParseResult<CompilationUnit> parse = javaParser.parse(file);
//            parse.getCommentsCollection();
            Optional<CompilationUnit> result = parse.getResult();
            CompilationUnit unit = result.get();
//            unit
//            String  filePath=
//            ClassUnitVisitor
            ClassUnitVisitor vc = new ClassUnitVisitor();
            //在指定的树上进行搜索，然后根据遇到的节点的类型调用具体的回调
            //vc.visit(unit, null);
//            unit.accept(new VoidVisitor<Object>() {
//            });
            unit.accept(vc,null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
//————————————————
//版权声明：本文为CSDN博主「shijiaolong0」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//原文链接：https://blog.csdn.net/shijiaolong0/article/details/82083964