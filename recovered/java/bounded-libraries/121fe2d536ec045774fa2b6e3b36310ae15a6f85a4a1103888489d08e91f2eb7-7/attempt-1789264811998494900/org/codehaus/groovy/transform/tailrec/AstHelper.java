/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import java.util.Map;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.transform.tailrec.InWhileLoopWrapper;

public class AstHelper {
    private AstHelper() {
    }

    public static ExpressionStatement createVariableDefinition(String variableName, ClassNode variableType, Expression value) {
        return AstHelper.createVariableDefinition(variableName, variableType, value, false);
    }

    public static ExpressionStatement createVariableDefinition(String variableName, ClassNode variableType, Expression value, boolean variableShouldBeFinal) {
        VariableExpression newVariable = GeneralUtils.localVarX(variableName, variableType);
        if (variableShouldBeFinal) {
            newVariable.setModifiers(16);
        }
        return (ExpressionStatement)GeneralUtils.declS(newVariable, value);
    }

    public static ExpressionStatement createVariableAlias(String aliasName, ClassNode variableType, String variableName) {
        return AstHelper.createVariableDefinition(aliasName, variableType, GeneralUtils.varX(variableName, variableType));
    }

    public static VariableExpression createVariableReference(Map<String, ?> variableSpec) {
        return GeneralUtils.varX((String)variableSpec.get("name"), (ClassNode)variableSpec.get("type"));
    }

    public static Statement recurStatement() {
        return new ContinueStatement("_RECUR_HERE_");
    }

    public static Statement recurByThrowStatement() {
        return GeneralUtils.throwS(GeneralUtils.propX((Expression)GeneralUtils.classX(InWhileLoopWrapper.class), "LOOP_EXCEPTION"));
    }
}

