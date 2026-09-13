/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import org.apache.groovy.util.Maps;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.transform.tailrec.RecursivenessTester;

public class HasRecursiveCalls
extends CodeVisitorSupport {
    private MethodNode method;
    private boolean hasRecursiveCalls = false;

    @Override
    public void visitMethodCallExpression(MethodCallExpression call) {
        if (this.isRecursive(call)) {
            this.hasRecursiveCalls = true;
        } else {
            super.visitMethodCallExpression(call);
        }
    }

    @Override
    public void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
        if (this.isRecursive(call)) {
            this.hasRecursiveCalls = true;
        } else {
            super.visitStaticMethodCallExpression(call);
        }
    }

    public synchronized boolean test(MethodNode method) {
        this.hasRecursiveCalls = false;
        this.method = method;
        this.method.getCode().visit(this);
        return this.hasRecursiveCalls;
    }

    private boolean isRecursive(Expression call) {
        return new RecursivenessTester().isRecursive(Maps.of("method", this.method, "call", call));
    }
}

