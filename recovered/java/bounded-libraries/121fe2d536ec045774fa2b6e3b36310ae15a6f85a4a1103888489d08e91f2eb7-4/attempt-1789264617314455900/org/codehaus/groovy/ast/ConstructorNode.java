/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;

public class ConstructorNode
extends MethodNode {
    protected ConstructorNode() {
    }

    public ConstructorNode(int modifiers, Statement code) {
        this(modifiers, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, code);
    }

    public ConstructorNode(int modifiers, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        super("<init>", modifiers, ClassHelper.VOID_TYPE, parameters, exceptions, code);
    }

    public boolean firstStatementIsSpecialConstructorCall() {
        Statement code = this.getFirstStatement();
        if (!(code instanceof ExpressionStatement)) {
            return false;
        }
        Expression expression = ((ExpressionStatement)code).getExpression();
        if (!(expression instanceof ConstructorCallExpression)) {
            return false;
        }
        ConstructorCallExpression cce = (ConstructorCallExpression)expression;
        return cce.isSpecialCall();
    }
}

