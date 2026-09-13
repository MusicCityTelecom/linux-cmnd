/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.AutoFinal;
import java.util.Iterator;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class AutoFinalASTTransformation
extends AbstractASTTransformation {
    private static final Class<?> MY_CLASS = AutoFinal.class;
    private static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    private AnnotatedNode target;

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        this.process(nodes, this.createVisitor());
    }

    private void process(ASTNode[] nodes, ClassCodeVisitorSupport visitor) {
        this.target = (AnnotatedNode)nodes[1];
        AnnotationNode node = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(node.getClassNode())) {
            return;
        }
        if (this.memberHasValue(node, "enabled", Boolean.FALSE)) {
            return;
        }
        if (this.target instanceof ClassNode) {
            this.processClass((ClassNode)this.target, visitor);
        } else if (this.target instanceof FieldNode) {
            this.processField((FieldNode)this.target, visitor);
        } else if (this.target instanceof MethodNode) {
            this.processConstructorOrMethod((MethodNode)this.target, visitor);
        } else if (this.target instanceof DeclarationExpression) {
            this.processLocalVariable((DeclarationExpression)this.target, visitor);
        }
    }

    private void processClass(ClassNode node, ClassCodeVisitorSupport visitor) {
        if (!this.isEnabled(node)) {
            return;
        }
        if (node.isInterface()) {
            this.addError("Error processing interface '" + node.getName() + "'. @" + MY_TYPE.getNameWithoutPackage() + " only allowed for classes.", node);
            return;
        }
        for (ConstructorNode constructorNode : node.getDeclaredConstructors()) {
            if (!AutoFinalASTTransformation.hasNoExplicitAutoFinal(constructorNode)) continue;
            this.processConstructorOrMethod(constructorNode, visitor);
        }
        for (MethodNode methodNode : node.getAllDeclaredMethods()) {
            if (!AutoFinalASTTransformation.hasNoExplicitAutoFinal(methodNode)) continue;
            this.processConstructorOrMethod(methodNode, visitor);
        }
        Iterator<InnerClassNode> it = node.getInnerClasses();
        while (it.hasNext()) {
            ClassNode classNode = it.next();
            if (!AutoFinalASTTransformation.hasNoExplicitAutoFinal(classNode) || classNode.isInterface()) continue;
            this.processClass(classNode, visitor);
        }
        visitor.visitClass(node);
    }

    private void processField(FieldNode node, ClassCodeVisitorSupport visitor) {
        if (!this.isEnabled(node)) {
            return;
        }
        if (node.hasInitialExpression() && node.getInitialExpression() instanceof ClosureExpression) {
            visitor.visitField(node);
        }
    }

    private void processConstructorOrMethod(MethodNode node, ClassCodeVisitorSupport visitor) {
        if (!this.isEnabled(node)) {
            return;
        }
        if (node.isSynthetic()) {
            return;
        }
        for (Parameter p : node.getParameters()) {
            p.setModifiers(p.getModifiers() | 0x10);
        }
        visitor.visitMethod(node);
    }

    private void processLocalVariable(DeclarationExpression expr, ClassCodeVisitorSupport visitor) {
        if (!this.isEnabled(expr)) {
            return;
        }
        if (expr.getRightExpression() instanceof ClosureExpression) {
            visitor.visitDeclarationExpression(expr);
        }
    }

    private ClassCodeVisitorSupport createVisitor() {
        return new ClassCodeVisitorSupport(){

            @Override
            public void visitClosureExpression(ClosureExpression expression) {
                if (!expression.isSynthetic()) {
                    for (Parameter p : ClosureUtils.getParametersSafe(expression)) {
                        p.setModifiers(p.getModifiers() | 0x10);
                    }
                    super.visitClosureExpression(expression);
                }
            }

            @Override
            protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
                if (AutoFinalASTTransformation.this.target == node || AutoFinalASTTransformation.hasNoExplicitAutoFinal(node)) {
                    super.visitConstructorOrMethod(node, isConstructor);
                }
            }

            @Override
            public void visitField(FieldNode node) {
                if (AutoFinalASTTransformation.this.target == node || AutoFinalASTTransformation.hasNoExplicitAutoFinal(node)) {
                    super.visitField(node);
                }
            }

            @Override
            public void visitDeclarationExpression(DeclarationExpression expr) {
                if (AutoFinalASTTransformation.this.target == expr || AutoFinalASTTransformation.hasNoExplicitAutoFinal(expr)) {
                    super.visitDeclarationExpression(expr);
                }
            }

            @Override
            protected SourceUnit getSourceUnit() {
                return AutoFinalASTTransformation.this.sourceUnit;
            }
        };
    }

    private boolean isEnabled(AnnotatedNode node) {
        return node != null && node.getAnnotations(MY_TYPE).stream().noneMatch(anno -> this.memberHasValue((AnnotationNode)anno, "enabled", Boolean.FALSE));
    }

    private static boolean hasNoExplicitAutoFinal(AnnotatedNode node) {
        return node.getAnnotations(MY_TYPE).isEmpty();
    }
}

