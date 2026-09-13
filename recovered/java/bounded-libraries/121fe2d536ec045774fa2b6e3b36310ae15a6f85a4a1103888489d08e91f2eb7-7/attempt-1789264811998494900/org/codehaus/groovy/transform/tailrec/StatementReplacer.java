/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import groovy.lang.Closure;
import java.util.ArrayList;
import java.util.function.Consumer;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.WhileStatement;

public class StatementReplacer
extends CodeVisitorSupport {
    private Closure<Boolean> when;
    private Closure<Statement> replaceWith;
    private int closureLevel = 0;

    public StatementReplacer(Closure<Boolean> when, Closure<Statement> replaceWith) {
        this.when = when;
        this.replaceWith = replaceWith;
    }

    public void replaceIn(ASTNode root) {
        root.visit(this);
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        ++this.closureLevel;
        try {
            super.visitClosureExpression(expression);
        }
        finally {
            --this.closureLevel;
        }
    }

    @Override
    public void visitBlockStatement(BlockStatement block) {
        ArrayList<Statement> copyOfStatements = new ArrayList<Statement>(block.getStatements());
        int i = 0;
        int n = copyOfStatements.size();
        while (i < n) {
            int index = i++;
            Statement statement = (Statement)copyOfStatements.get(index);
            this.replaceIfNecessary(statement, node -> block.getStatements().set(index, (Statement)node));
        }
        super.visitBlockStatement(block);
    }

    @Override
    public void visitIfElse(IfStatement ifElse) {
        this.replaceIfNecessary(ifElse.getIfBlock(), ifElse::setIfBlock);
        this.replaceIfNecessary(ifElse.getElseBlock(), ifElse::setElseBlock);
        super.visitIfElse(ifElse);
    }

    @Override
    public void visitForLoop(ForStatement forLoop) {
        this.replaceIfNecessary(forLoop.getLoopBlock(), forLoop::setLoopBlock);
        super.visitForLoop(forLoop);
    }

    @Override
    public void visitWhileLoop(WhileStatement loop) {
        this.replaceIfNecessary(loop.getLoopBlock(), loop::setLoopBlock);
        super.visitWhileLoop(loop);
    }

    @Override
    public void visitDoWhileLoop(DoWhileStatement loop) {
        this.replaceIfNecessary(loop.getLoopBlock(), loop::setLoopBlock);
        super.visitDoWhileLoop(loop);
    }

    private void replaceIfNecessary(Statement nodeToCheck, Consumer<? super Statement> replacementCode) {
        if (this.conditionFulfilled(nodeToCheck)) {
            Statement replacement = this.replaceWith.call((Object)nodeToCheck);
            replacement.setSourcePosition(nodeToCheck);
            replacement.copyNodeMetaData(nodeToCheck);
            replacementCode.accept(replacement);
        }
    }

    private boolean conditionFulfilled(ASTNode nodeToCheck) {
        if (this.when.getMaximumNumberOfParameters() < 2) {
            return this.when.call((Object)nodeToCheck);
        }
        return this.when.call(nodeToCheck, this.isInClosure());
    }

    private boolean isInClosure() {
        return this.closureLevel > 0;
    }

    public Closure<Boolean> getWhen() {
        return this.when;
    }

    public void setWhen(Closure<Boolean> when) {
        this.when = when;
    }

    public Closure<Statement> getReplaceWith() {
        return this.replaceWith;
    }

    public void setReplaceWith(Closure<Statement> replaceWith) {
        this.replaceWith = replaceWith;
    }

    public int getClosureLevel() {
        return this.closureLevel;
    }

    public void setClosureLevel(int closureLevel) {
        this.closureLevel = closureLevel;
    }
}

