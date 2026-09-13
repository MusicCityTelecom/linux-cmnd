/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import java.util.ArrayList;
import java.util.List;
import org.apache.groovy.util.Maps;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.transform.tailrec.RecursivenessTester;

public class CollectRecursiveCalls
extends CodeVisitorSupport {
    private final List<Expression> recursiveCalls = new ArrayList<Expression>();
    private MethodNode method;

    @Override
    public void visitMethodCallExpression(MethodCallExpression call) {
        if (this.isRecursive(call)) {
            this.recursiveCalls.add(call);
        }
        super.visitMethodCallExpression(call);
    }

    @Override
    public void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
        if (this.isRecursive(call)) {
            this.recursiveCalls.add(call);
        }
        super.visitStaticMethodCallExpression(call);
    }

    public synchronized List<Expression> collect(MethodNode method) {
        this.recursiveCalls.clear();
        this.method = method;
        this.method.getCode().visit(this);
        return this.recursiveCalls;
    }

    private boolean isRecursive(Expression call) {
        return new RecursivenessTester().isRecursive(Maps.of("method", this.method, "call", call));
    }
}

