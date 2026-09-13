/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.ast.tools;

import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;

public class MethodCallUtils {
    private MethodCallUtils() {
    }

    public static Statement appendS(Expression result, Expression expr) {
        MethodCallExpression append = GeneralUtils.callX(result, "append", expr);
        append.setImplicitThis(false);
        return GeneralUtils.stmt(append);
    }

    public static Expression toStringX(Expression object) {
        MethodCallExpression toString = GeneralUtils.callX(object, "toString");
        toString.setImplicitThis(false);
        return toString;
    }
}

