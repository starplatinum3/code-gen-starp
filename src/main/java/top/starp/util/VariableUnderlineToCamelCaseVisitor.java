package top.starp.util;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class VariableUnderlineToCamelCaseVisitor extends VoidVisitorAdapter<Void> {
//    UnderlineToCamelCase
    @Override
    public void visit(VariableDeclarator variableDeclarator, Void arg) {
        String identifier = variableDeclarator.getName().getIdentifier();
        String camelCaseIdentifier = StringUtils.underlineToCamelCase(identifier);
        variableDeclarator.getName().setIdentifier(camelCaseIdentifier);
        
        super.visit(variableDeclarator, arg);
    }
}
