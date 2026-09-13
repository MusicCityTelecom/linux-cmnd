/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.classgen.asm.OptimizingStatementWriter;
import org.codehaus.groovy.classgen.asm.TypeChooser;

public class StatementMetaTypeChooser
implements TypeChooser {
    @Override
    public ClassNode resolveType(Expression exp, ClassNode current) {
        ClassNode type = null;
        if (exp instanceof ClassExpression) {
            type = exp.getType();
            ClassNode classType = ClassHelper.makeWithoutCaching("java.lang.Class");
            classType.setGenericsTypes(new GenericsType[]{new GenericsType(type)});
            classType.setRedirect(ClassHelper.CLASS_Type);
            return classType;
        }
        OptimizingStatementWriter.StatementMeta meta = (OptimizingStatementWriter.StatementMeta)exp.getNodeMetaData(OptimizingStatementWriter.StatementMeta.class);
        if (meta != null) {
            type = meta.type;
        }
        if (type != null) {
            return type;
        }
        if (exp instanceof VariableExpression) {
            VariableExpression vexp = (VariableExpression)exp;
            if (vexp.isClosureSharedVariable()) {
                return vexp.getType();
            }
            if (vexp.isSuperExpression()) {
                return current.getSuperClass();
            }
            Variable var = vexp.getAccessedVariable();
            if (var == null) {
                var = vexp;
            }
            type = var.getOriginType();
        } else {
            type = exp.getType();
        }
        return type;
    }
}

