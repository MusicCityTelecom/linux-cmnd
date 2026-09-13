/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import org.codehaus.groovy.ast.expr.VariableExpression;

public interface VariableReplacedListener {
    public static final VariableReplacedListener NULL = (oldVar, newVar) -> {};

    public void variableReplaced(VariableExpression var1, VariableExpression var2);
}

