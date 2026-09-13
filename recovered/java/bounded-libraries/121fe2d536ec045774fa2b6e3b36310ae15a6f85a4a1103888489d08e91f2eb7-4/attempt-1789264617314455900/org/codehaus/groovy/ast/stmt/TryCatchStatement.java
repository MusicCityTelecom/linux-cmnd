/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.stmt;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;

public class TryCatchStatement
extends Statement {
    private static final String IS_RESOURCE = "_IS_RESOURCE";
    private Statement tryStatement;
    private final List<ExpressionStatement> resourceStatements = new ArrayList<ExpressionStatement>(4);
    private final List<CatchStatement> catchStatements = new ArrayList<CatchStatement>(4);
    private Statement finallyStatement;

    public TryCatchStatement(Statement tryStatement, Statement finallyStatement) {
        this.tryStatement = tryStatement;
        this.finallyStatement = finallyStatement;
    }

    @Override
    public void visit(GroovyCodeVisitor visitor) {
        visitor.visitTryCatchFinally(this);
    }

    public List<ExpressionStatement> getResourceStatements() {
        return this.resourceStatements;
    }

    public List<CatchStatement> getCatchStatements() {
        return this.catchStatements;
    }

    public Statement getFinallyStatement() {
        return this.finallyStatement;
    }

    public Statement getTryStatement() {
        return this.tryStatement;
    }

    public void addResource(ExpressionStatement resourceStatement) {
        Expression resourceExpression = resourceStatement.getExpression();
        if (!(resourceExpression instanceof DeclarationExpression) && !(resourceExpression instanceof VariableExpression)) {
            throw new GroovyBugError("resourceStatement should be a variable declaration statement or a variable");
        }
        resourceExpression.putNodeMetaData(IS_RESOURCE, Boolean.TRUE);
        this.resourceStatements.add(resourceStatement);
    }

    public static boolean isResource(Expression expression) {
        Boolean r = (Boolean)expression.getNodeMetaData(IS_RESOURCE);
        return null != r && r != false;
    }

    public void addCatch(CatchStatement catchStatement) {
        this.catchStatements.add(catchStatement);
    }

    public CatchStatement getCatchStatement(int idx) {
        if (idx >= 0 && idx < this.catchStatements.size()) {
            return this.catchStatements.get(idx);
        }
        return null;
    }

    public ExpressionStatement getResourceStatement(int idx) {
        if (idx >= 0 && idx < this.resourceStatements.size()) {
            return this.resourceStatements.get(idx);
        }
        return null;
    }

    public void setTryStatement(Statement tryStatement) {
        this.tryStatement = tryStatement;
    }

    public void setCatchStatement(int idx, CatchStatement catchStatement) {
        this.catchStatements.set(idx, catchStatement);
    }

    public void setFinallyStatement(Statement finallyStatement) {
        this.finallyStatement = finallyStatement;
    }
}

