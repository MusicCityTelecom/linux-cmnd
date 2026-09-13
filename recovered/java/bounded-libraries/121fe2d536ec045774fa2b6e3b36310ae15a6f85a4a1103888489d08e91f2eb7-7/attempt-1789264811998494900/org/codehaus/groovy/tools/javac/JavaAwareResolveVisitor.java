/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.tools.javac;

import org.apache.groovy.ast.tools.ConstructorNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.ResolveVisitor;

public class JavaAwareResolveVisitor
extends ResolveVisitor {
    public JavaAwareResolveVisitor(CompilationUnit cu) {
        super(cu);
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        super.visitConstructor(node);
        Statement code = node.getCode();
        ConstructorCallExpression cce = ConstructorNodeUtils.getFirstIfSpecialConstructorCall(code);
        if (cce != null) {
            ((ASTNode)cce).visit(this);
        }
    }

    @Override
    protected void visitClassCodeContainer(Statement stmt) {
    }

    @Override
    public void addError(String error, ASTNode node) {
        if (error.startsWith("unable to resolve")) {
            this.getSourceUnit().getAST().putNodeMetaData("require.imports", Boolean.TRUE);
        }
    }
}

