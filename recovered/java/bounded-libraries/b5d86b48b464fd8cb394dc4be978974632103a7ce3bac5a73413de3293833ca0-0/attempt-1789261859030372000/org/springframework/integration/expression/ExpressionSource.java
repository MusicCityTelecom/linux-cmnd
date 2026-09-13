/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.expression.Expression
 *  org.springframework.lang.Nullable
 */
package org.springframework.integration.expression;

import java.util.Locale;
import org.springframework.expression.Expression;
import org.springframework.lang.Nullable;

@FunctionalInterface
public interface ExpressionSource {
    @Nullable
    public Expression getExpression(String var1, Locale var2);
}

