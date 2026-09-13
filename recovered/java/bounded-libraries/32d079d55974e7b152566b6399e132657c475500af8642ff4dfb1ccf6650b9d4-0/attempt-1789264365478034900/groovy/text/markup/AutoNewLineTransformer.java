/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.codehaus.groovy.ast.ASTNode
 *  org.codehaus.groovy.ast.ClassCodeVisitorSupport
 *  org.codehaus.groovy.ast.GroovyCodeVisitor
 *  org.codehaus.groovy.ast.VariableScope
 *  org.codehaus.groovy.ast.expr.ArgumentListExpression
 *  org.codehaus.groovy.ast.expr.ClosureExpression
 *  org.codehaus.groovy.ast.expr.Expression
 *  org.codehaus.groovy.ast.expr.MethodCallExpression
 *  org.codehaus.groovy.ast.expr.TupleExpression
 *  org.codehaus.groovy.ast.expr.VariableExpression
 *  org.codehaus.groovy.ast.stmt.BlockStatement
 *  org.codehaus.groovy.ast.stmt.ExpressionStatement
 *  org.codehaus.groovy.ast.stmt.Statement
 *  org.codehaus.groovy.control.SourceUnit
 */
package groovy.text.markup;

import java.util.Collections;
import java.util.List;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.SourceUnit;

class AutoNewLineTransformer
extends ClassCodeVisitorSupport {
    private final SourceUnit unit;
    private boolean inBuilderMethod;

    public AutoNewLineTransformer(SourceUnit unit) {
        this.unit = unit;
    }

    protected SourceUnit getSourceUnit() {
        return this.unit;
    }

    public void visitMethodCallExpression(MethodCallExpression call) {
        boolean old = this.inBuilderMethod;
        this.inBuilderMethod = false;
        if (call.isImplicitThis() && call.getArguments() instanceof TupleExpression) {
            Expression lastArg;
            List expressions = ((TupleExpression)call.getArguments()).getExpressions();
            if (!expressions.isEmpty() && (lastArg = (Expression)expressions.get(expressions.size() - 1)) instanceof ClosureExpression) {
                call.getObjectExpression().visit((GroovyCodeVisitor)this);
                call.getMethod().visit((GroovyCodeVisitor)this);
                for (Expression expression : expressions) {
                    this.inBuilderMethod = expression == lastArg;
                    expression.visit((GroovyCodeVisitor)this);
                }
            }
        } else {
            super.visitMethodCallExpression(call);
        }
        this.inBuilderMethod = old;
    }

    public void visitClosureExpression(ClosureExpression expression) {
        super.visitClosureExpression(expression);
        if (this.inBuilderMethod) {
            Statement oldCode = expression.getCode();
            BlockStatement block = oldCode instanceof BlockStatement ? (BlockStatement)oldCode : new BlockStatement(Collections.singletonList(oldCode), new VariableScope());
            List statements = block.getStatements();
            if (!statements.isEmpty()) {
                Statement first = (Statement)statements.get(0);
                Statement last = (Statement)statements.get(statements.size() - 1);
                if (expression.getLineNumber() < first.getLineNumber()) {
                    statements.add(0, this.createNewLine((ASTNode)expression));
                }
                if (expression.getLastLineNumber() > last.getLastLineNumber()) {
                    statements.add(this.createNewLine((ASTNode)expression));
                }
            }
            expression.setCode((Statement)block);
        }
    }

    private Statement createNewLine(ASTNode node) {
        MethodCallExpression mce = new MethodCallExpression((Expression)new VariableExpression("this"), "newLine", (Expression)ArgumentListExpression.EMPTY_ARGUMENTS);
        mce.setImplicitThis(true);
        mce.setSourcePosition(node);
        ExpressionStatement stmt = new ExpressionStatement((Expression)mce);
        stmt.setSourcePosition(node);
        return stmt;
    }
}

