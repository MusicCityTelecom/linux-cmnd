/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.codehaus.groovy.ast.CodeVisitorSupport
 *  org.codehaus.groovy.ast.GroovyCodeVisitor
 *  org.codehaus.groovy.ast.expr.PropertyExpression
 *  org.codehaus.groovy.ast.stmt.ReturnStatement
 */
package groovy.sql;

import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;

public class SqlOrderByVisitor
extends CodeVisitorSupport {
    private final StringBuffer buffer = new StringBuffer();

    public String getOrderBy() {
        return this.buffer.toString();
    }

    public void visitReturnStatement(ReturnStatement statement) {
        statement.getExpression().visit((GroovyCodeVisitor)this);
    }

    public void visitPropertyExpression(PropertyExpression expression) {
        this.buffer.append(expression.getPropertyAsString());
    }
}

