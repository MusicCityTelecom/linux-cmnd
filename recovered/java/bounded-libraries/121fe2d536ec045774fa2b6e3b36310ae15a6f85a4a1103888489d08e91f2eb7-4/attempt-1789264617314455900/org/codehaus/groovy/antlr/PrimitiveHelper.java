/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.antlr;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;

public class PrimitiveHelper {
    private PrimitiveHelper() {
    }

    public static Expression getDefaultValueForPrimitive(ClassNode type) {
        if (ClassHelper.isPrimitiveInt(type)) {
            return new ConstantExpression(0, true);
        }
        if (ClassHelper.isPrimitiveLong(type)) {
            return new ConstantExpression(0L, true);
        }
        if (ClassHelper.isPrimitiveDouble(type)) {
            return new ConstantExpression(0.0, true);
        }
        if (ClassHelper.isPrimitiveBoolean(type)) {
            return new ConstantExpression(Boolean.FALSE, true);
        }
        if (ClassHelper.isPrimitiveByte(type)) {
            return new ConstantExpression((byte)0, true);
        }
        if (ClassHelper.isPrimitiveChar(type)) {
            return new ConstantExpression(Character.valueOf('\u0000'), true);
        }
        if (ClassHelper.isPrimitiveFloat(type)) {
            return new ConstantExpression(Float.valueOf(0.0f), true);
        }
        if (ClassHelper.isPrimitiveShort(type)) {
            return new ConstantExpression((short)0, true);
        }
        return null;
    }
}

