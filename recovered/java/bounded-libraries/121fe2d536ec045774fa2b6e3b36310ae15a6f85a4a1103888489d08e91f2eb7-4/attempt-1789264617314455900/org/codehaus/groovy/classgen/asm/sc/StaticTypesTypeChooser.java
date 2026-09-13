/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.classgen.asm.StatementMetaTypeChooser;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class StaticTypesTypeChooser
extends StatementMetaTypeChooser {
    @Override
    public ClassNode resolveType(Expression exp, ClassNode current) {
        ASTNode target = StaticTypesTypeChooser.getTarget(exp);
        ClassNode inferredType = (ClassNode)target.getNodeMetaData((Object)StaticTypesMarker.DECLARATION_INFERRED_TYPE);
        if (inferredType == null) {
            inferredType = (ClassNode)target.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
        }
        if (inferredType != null && !ClassHelper.isPrimitiveVoid(inferredType)) {
            return inferredType;
        }
        if (target instanceof VariableExpression && ((VariableExpression)target).isThisExpression()) {
            return current;
        }
        return super.resolveType(exp, current);
    }

    private static ASTNode getTarget(Expression exp) {
        Variable var;
        ASTNode target = exp;
        while (target instanceof VariableExpression && (var = ((VariableExpression)target).getAccessedVariable()) instanceof ASTNode && var != target) {
            target = (ASTNode)((Object)var);
        }
        return target;
    }
}

