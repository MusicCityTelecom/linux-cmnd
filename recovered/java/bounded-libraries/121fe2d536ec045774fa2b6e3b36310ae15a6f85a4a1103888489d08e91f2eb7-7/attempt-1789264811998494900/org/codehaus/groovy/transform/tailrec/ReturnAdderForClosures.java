/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.classgen.ReturnAdder;

public class ReturnAdderForClosures
extends CodeVisitorSupport {
    public synchronized void visitMethod(MethodNode method) {
        method.getCode().visit(this);
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        MethodNode node = new MethodNode("dummy", 0, ClassHelper.OBJECT_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, expression.getCode());
        new ReturnAdder().visitMethod(node);
        super.visitClosureExpression(expression);
    }
}

