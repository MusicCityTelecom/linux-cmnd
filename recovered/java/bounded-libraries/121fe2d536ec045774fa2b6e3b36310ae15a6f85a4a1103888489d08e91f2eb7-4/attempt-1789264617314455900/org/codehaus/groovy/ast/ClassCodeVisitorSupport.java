/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast;

import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GroovyClassVisitor;
import org.codehaus.groovy.ast.ImportNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.control.messages.SyntaxErrorMessage;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.ErrorCollecting;

public abstract class ClassCodeVisitorSupport
extends CodeVisitorSupport
implements ErrorCollecting,
GroovyClassVisitor {
    @Override
    public void visitClass(ClassNode node) {
        this.visitAnnotations(node);
        this.visitPackage(node.getPackage());
        this.visitImports(node.getModule());
        node.visitContents(this);
        this.visitObjectInitializerStatements(node);
    }

    public void visitAnnotations(AnnotatedNode node) {
        this.visitAnnotations(node.getAnnotations());
    }

    protected final void visitAnnotations(Iterable<AnnotationNode> nodes) {
        for (AnnotationNode node : nodes) {
            if (node.isBuiltIn()) continue;
            this.visitAnnotation(node);
        }
    }

    protected void visitAnnotation(AnnotationNode node) {
        for (Expression expr : node.getMembers().values()) {
            expr.visit(this);
        }
    }

    public void visitPackage(PackageNode node) {
        if (node != null) {
            this.visitAnnotations(node);
            node.visit(this);
        }
    }

    public void visitImports(ModuleNode node) {
        if (node != null) {
            for (ImportNode importNode : node.getImports()) {
                this.visitAnnotations(importNode);
                importNode.visit(this);
            }
            for (ImportNode importStarNode : node.getStarImports()) {
                this.visitAnnotations(importStarNode);
                importStarNode.visit(this);
            }
            for (ImportNode importStaticNode : node.getStaticImports().values()) {
                this.visitAnnotations(importStaticNode);
                importStaticNode.visit(this);
            }
            for (ImportNode importStaticStarNode : node.getStaticStarImports().values()) {
                this.visitAnnotations(importStaticStarNode);
                importStaticStarNode.visit(this);
            }
        }
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        this.visitConstructorOrMethod(node, true);
    }

    @Override
    public void visitMethod(MethodNode node) {
        this.visitConstructorOrMethod(node, false);
    }

    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        this.visitAnnotations(node);
        this.visitClassCodeContainer(node.getCode());
        for (Parameter param : node.getParameters()) {
            this.visitAnnotations(param);
        }
    }

    @Override
    public void visitField(FieldNode node) {
        this.visitAnnotations(node);
        Expression init = node.getInitialExpression();
        if (init != null) {
            init.visit(this);
        }
    }

    @Override
    public void visitProperty(PropertyNode node) {
        this.visitAnnotations(node);
        Statement statement = node.getGetterBlock();
        this.visitClassCodeContainer(statement);
        statement = node.getSetterBlock();
        this.visitClassCodeContainer(statement);
        Expression init = node.getInitialExpression();
        if (init != null) {
            init.visit(this);
        }
    }

    protected void visitClassCodeContainer(Statement code) {
        if (code != null) {
            code.visit(this);
        }
    }

    protected void visitObjectInitializerStatements(ClassNode node) {
        for (Statement statement : node.getObjectInitializerStatements()) {
            statement.visit(this);
        }
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        this.visitAnnotations(expression);
        super.visitDeclarationExpression(expression);
    }

    @Override
    public void visitAssertStatement(AssertStatement statement) {
        this.visitStatement(statement);
        super.visitAssertStatement(statement);
    }

    @Override
    public void visitBlockStatement(BlockStatement statement) {
        this.visitStatement(statement);
        super.visitBlockStatement(statement);
    }

    @Override
    public void visitBreakStatement(BreakStatement statement) {
        this.visitStatement(statement);
        super.visitBreakStatement(statement);
    }

    @Override
    public void visitCaseStatement(CaseStatement statement) {
        this.visitStatement(statement);
        super.visitCaseStatement(statement);
    }

    @Override
    public void visitCatchStatement(CatchStatement statement) {
        this.visitStatement(statement);
        super.visitCatchStatement(statement);
    }

    @Override
    public void visitContinueStatement(ContinueStatement statement) {
        this.visitStatement(statement);
        super.visitContinueStatement(statement);
    }

    @Override
    public void visitDoWhileLoop(DoWhileStatement statement) {
        this.visitStatement(statement);
        super.visitDoWhileLoop(statement);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        this.visitStatement(statement);
        super.visitExpressionStatement(statement);
    }

    @Override
    public void visitForLoop(ForStatement statement) {
        this.visitStatement(statement);
        super.visitForLoop(statement);
    }

    @Override
    public void visitIfElse(IfStatement statement) {
        this.visitStatement(statement);
        super.visitIfElse(statement);
    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {
        this.visitStatement(statement);
        super.visitReturnStatement(statement);
    }

    @Override
    public void visitSwitch(SwitchStatement statement) {
        this.visitStatement(statement);
        super.visitSwitch(statement);
    }

    @Override
    public void visitSynchronizedStatement(SynchronizedStatement statement) {
        this.visitStatement(statement);
        super.visitSynchronizedStatement(statement);
    }

    @Override
    public void visitThrowStatement(ThrowStatement statement) {
        this.visitStatement(statement);
        super.visitThrowStatement(statement);
    }

    @Override
    public void visitTryCatchFinally(TryCatchStatement statement) {
        this.visitStatement(statement);
        super.visitTryCatchFinally(statement);
    }

    @Override
    public void visitWhileLoop(WhileStatement statement) {
        this.visitStatement(statement);
        super.visitWhileLoop(statement);
    }

    protected void visitStatement(Statement statement) {
    }

    protected abstract SourceUnit getSourceUnit();

    @Override
    public void addError(String error, ASTNode node) {
        SourceUnit source = this.getSourceUnit();
        source.getErrorCollector().addErrorAndContinue(new SyntaxErrorMessage(new SyntaxException(error + '\n', node.getLineNumber(), node.getColumnNumber(), node.getLastLineNumber(), node.getLastColumnNumber()), source));
    }
}

