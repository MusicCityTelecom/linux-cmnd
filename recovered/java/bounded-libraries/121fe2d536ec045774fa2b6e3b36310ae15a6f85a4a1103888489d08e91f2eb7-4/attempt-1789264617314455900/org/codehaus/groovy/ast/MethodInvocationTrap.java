/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.io.ReaderSource;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.syntax.SyntaxException;

public abstract class MethodInvocationTrap
extends CodeVisitorSupport {
    protected final ReaderSource source;
    protected final SourceUnit sourceUnit;

    public MethodInvocationTrap(ReaderSource source, SourceUnit sourceUnit) {
        if (source == null) {
            throw new IllegalArgumentException("Null: source");
        }
        if (sourceUnit == null) {
            throw new IllegalArgumentException("Null: sourceUnit");
        }
        this.source = source;
        this.sourceUnit = sourceUnit;
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression call) {
        boolean shouldContinueWalking = true;
        if (this.isBuildInvocation(call)) {
            shouldContinueWalking = this.handleTargetMethodCallExpression(call);
        }
        if (shouldContinueWalking) {
            call.getObjectExpression().visit(this);
            call.getMethod().visit(this);
            call.getArguments().visit(this);
        }
    }

    protected void addError(String msg, ASTNode expr) {
        this.sourceUnit.getErrorCollector().addErrorAndContinue(new SyntaxErrorMessage(new SyntaxException(msg + '\n', expr.getLineNumber(), expr.getColumnNumber(), expr.getLastLineNumber(), expr.getLastColumnNumber()), this.sourceUnit));
    }

    protected String convertClosureToSource(ClosureExpression expression) {
        try {
            return ClosureUtils.convertClosureToSource(this.source, expression);
        }
        catch (Exception e) {
            this.addError(e.getMessage(), expression);
            return null;
        }
    }

    protected abstract boolean handleTargetMethodCallExpression(MethodCallExpression var1);

    protected abstract boolean isBuildInvocation(MethodCallExpression var1);
}

