/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.control;

import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.SourceUnit;

public class AnnotationConstantsVisitor
extends ClassCodeVisitorSupport {
    private boolean annotationDef;
    private SourceUnit sourceUnit;

    @Override
    protected SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    public void visitClass(ClassNode classNode, SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit;
        this.annotationDef = classNode.isAnnotationDefinition();
        super.visitClass(classNode);
        this.annotationDef = false;
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        if (this.annotationDef) {
            Statement statement = node.getFirstStatement();
            if (statement instanceof ReturnStatement) {
                ReturnStatement rs = (ReturnStatement)statement;
                rs.setExpression(ExpressionUtils.transformInlineConstants(rs.getExpression(), node.getReturnType()));
            } else if (statement instanceof ExpressionStatement) {
                ExpressionStatement es = (ExpressionStatement)statement;
                es.setExpression(ExpressionUtils.transformInlineConstants(es.getExpression(), node.getReturnType()));
            }
        }
    }
}

