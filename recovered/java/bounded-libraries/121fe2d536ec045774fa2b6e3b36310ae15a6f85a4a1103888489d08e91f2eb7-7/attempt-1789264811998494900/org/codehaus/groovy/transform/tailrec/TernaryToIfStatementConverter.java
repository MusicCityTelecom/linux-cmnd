/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;

public class TernaryToIfStatementConverter {
    public Statement convert(ReturnStatement statementWithInnerTernaryExpression) {
        if (!(statementWithInnerTernaryExpression.getExpression() instanceof TernaryExpression)) {
            return statementWithInnerTernaryExpression;
        }
        TernaryExpression ternary = (TernaryExpression)statementWithInnerTernaryExpression.getExpression();
        return GeneralUtils.ifElseS(ternary.getBooleanExpression(), GeneralUtils.returnS(ternary.getTrueExpression()), GeneralUtils.returnS(ternary.getFalseExpression()));
    }
}

