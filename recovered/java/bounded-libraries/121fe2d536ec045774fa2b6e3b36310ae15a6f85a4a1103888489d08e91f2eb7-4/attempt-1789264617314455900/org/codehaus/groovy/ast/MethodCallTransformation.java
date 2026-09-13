/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import groovy.lang.MissingPropertyException;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.ASTTransformation;

public abstract class MethodCallTransformation
implements ASTTransformation {
    @Override
    public void visit(ASTNode[] nodes, SourceUnit sourceUnit) {
        GroovyCodeVisitor transformer = this.getTransformer(nodes, sourceUnit);
        if (nodes != null) {
            for (ASTNode aSTNode : nodes) {
                if (aSTNode instanceof AnnotationNode || aSTNode instanceof ClassNode) continue;
                aSTNode.visit(transformer);
            }
        }
        if (sourceUnit.getAST() != null) {
            sourceUnit.getAST().visit(transformer);
            if (sourceUnit.getAST().getStatementBlock() != null) {
                sourceUnit.getAST().getStatementBlock().visit(transformer);
            }
            if (sourceUnit.getAST().getClasses() != null) {
                for (ClassNode classNode : sourceUnit.getAST().getClasses()) {
                    if (classNode.getMethods() != null) {
                        for (MethodNode methodNode : classNode.getMethods()) {
                            if (methodNode == null || methodNode.getCode() == null) continue;
                            methodNode.getCode().visit(transformer);
                        }
                    }
                    try {
                        if (classNode.getDeclaredConstructors() != null) {
                            for (MethodNode methodNode : classNode.getDeclaredConstructors()) {
                                if (methodNode == null || methodNode.getCode() == null) continue;
                                methodNode.getCode().visit(transformer);
                            }
                        }
                    }
                    catch (MissingPropertyException missingPropertyException) {
                        // empty catch block
                    }
                    if (classNode.getFields() != null) {
                        for (FieldNode fieldNode : classNode.getFields()) {
                            if (fieldNode.getInitialValueExpression() == null) continue;
                            fieldNode.getInitialValueExpression().visit(transformer);
                        }
                    }
                    try {
                        for (Statement statement : classNode.getObjectInitializerStatements()) {
                            if (statement == null) continue;
                            statement.visit(transformer);
                        }
                    }
                    catch (MissingPropertyException missingPropertyException) {
                    }
                }
            }
            if (sourceUnit.getAST().getMethods() != null) {
                for (MethodNode node : sourceUnit.getAST().getMethods()) {
                    if (node == null) continue;
                    if (node.getParameters() != null) {
                        for (Parameter parameter : node.getParameters()) {
                            if (parameter == null || parameter.getInitialExpression() == null) continue;
                            parameter.getInitialExpression().visit(transformer);
                        }
                    }
                    if (node.getCode() == null) continue;
                    node.getCode().visit(transformer);
                }
            }
        }
    }

    protected abstract GroovyCodeVisitor getTransformer(ASTNode[] var1, SourceUnit var2);
}

