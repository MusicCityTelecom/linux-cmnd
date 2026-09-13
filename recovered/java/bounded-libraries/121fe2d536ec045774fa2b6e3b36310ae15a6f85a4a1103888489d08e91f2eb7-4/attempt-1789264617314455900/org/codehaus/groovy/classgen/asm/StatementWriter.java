/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.EmptyExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
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
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.BytecodeVariable;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.MethodCaller;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.WriterController;

public class StatementWriter {
    private static final MethodCaller iteratorHasNextMethod = MethodCaller.newInterface(Iterator.class, "hasNext");
    private static final MethodCaller iteratorNextMethod = MethodCaller.newInterface(Iterator.class, "next");
    protected final WriterController controller;

    public StatementWriter(WriterController controller) {
        this.controller = controller;
    }

    protected void writeStatementLabel(Statement statement) {
        Optional.ofNullable(statement.getStatementLabels()).ifPresent(labels -> labels.stream().map(this.controller.getCompileStack()::createLocalLabel).forEach(label -> this.controller.getMethodVisitor().visitLabel((Label)label)));
    }

    public void writeBlockStatement(BlockStatement block) {
        this.writeStatementLabel(block);
        int mark = this.controller.getOperandStack().getStackLength();
        CompileStack compileStack = this.controller.getCompileStack();
        compileStack.pushVariableScope(block.getVariableScope());
        for (Statement statement : block.getStatements()) {
            statement.visit(this.controller.getAcg());
        }
        compileStack.pop();
        this.controller.getOperandStack().popDownTo(mark);
    }

    public void writeForStatement(ForStatement statement) {
        if (statement.getVariable() == ForStatement.FOR_LOOP_DUMMY) {
            this.writeForLoopWithClosureList(statement);
        } else {
            this.writeForInLoop(statement);
        }
    }

    protected void writeForInLoop(ForStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitForLoop");
        this.writeStatementLabel(statement);
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        compileStack.pushLoop(statement.getVariableScope(), statement.getStatementLabels());
        MethodCallExpression iterator = new MethodCallExpression(statement.getCollectionExpression(), "iterator", (Expression)new ArgumentListExpression());
        iterator.visit(this.controller.getAcg());
        operandStack.doGroovyCast(ClassHelper.Iterator_TYPE);
        this.writeForInLoopControlAndBlock(statement);
        compileStack.pop();
    }

    protected void writeForInLoopControlAndBlock(ForStatement statement) {
        CompileStack compileStack = this.controller.getCompileStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        OperandStack operandStack = this.controller.getOperandStack();
        BytecodeVariable variable = compileStack.defineVariable(statement.getVariable(), false);
        int iterator = compileStack.defineTemporaryVariable("iterator", ClassHelper.Iterator_TYPE, true);
        Label breakLabel = compileStack.getBreakLabel();
        Label continueLabel = compileStack.getContinueLabel();
        mv.visitVarInsn(25, iterator);
        mv.visitJumpInsn(198, breakLabel);
        mv.visitLabel(continueLabel);
        mv.visitVarInsn(25, iterator);
        this.writeIteratorHasNext(mv);
        mv.visitJumpInsn(153, breakLabel);
        mv.visitVarInsn(25, iterator);
        this.writeIteratorNext(mv);
        operandStack.push(ClassHelper.OBJECT_TYPE);
        operandStack.storeVar(variable);
        statement.getLoopBlock().visit(this.controller.getAcg());
        mv.visitJumpInsn(167, continueLabel);
        mv.visitLabel(breakLabel);
        compileStack.removeVar(iterator);
    }

    protected void writeIteratorHasNext(MethodVisitor mv) {
        iteratorHasNextMethod.call(mv);
    }

    protected void writeIteratorNext(MethodVisitor mv) {
        iteratorNextMethod.call(mv);
    }

    protected void writeForLoopWithClosureList(ForStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitForLoop");
        this.writeStatementLabel(statement);
        MethodVisitor mv = this.controller.getMethodVisitor();
        this.controller.getCompileStack().pushLoop(statement.getVariableScope(), statement.getStatementLabels());
        ClosureListExpression clExpr = (ClosureListExpression)statement.getCollectionExpression();
        this.controller.getCompileStack().pushVariableScope(clExpr.getVariableScope());
        List<Expression> expressions = clExpr.getExpressions();
        int size = expressions.size();
        int condIndex = (size - 1) / 2;
        for (int i = 0; i < condIndex; ++i) {
            this.visitExpressionOfLoopStatement(expressions.get(i));
        }
        Label continueLabel = this.controller.getCompileStack().getContinueLabel();
        Label breakLabel = this.controller.getCompileStack().getBreakLabel();
        Label cond = new Label();
        mv.visitLabel(cond);
        int mark = this.controller.getOperandStack().getStackLength();
        Expression condExpr = expressions.get(condIndex);
        condExpr.visit(this.controller.getAcg());
        this.controller.getOperandStack().castToBool(mark, true);
        this.controller.getOperandStack().jump(153, breakLabel);
        statement.getLoopBlock().visit(this.controller.getAcg());
        mv.visitLabel(continueLabel);
        this.controller.getAcg().onLineNumber(statement, "increment condition");
        for (int i = condIndex + 1; i < size; ++i) {
            this.visitExpressionOfLoopStatement(expressions.get(i));
        }
        mv.visitJumpInsn(167, cond);
        mv.visitLabel(breakLabel);
        this.controller.getCompileStack().pop();
        this.controller.getCompileStack().pop();
    }

    private void visitExpressionOfLoopStatement(Expression expression) {
        Consumer<Expression> visit = expr -> {
            if (expr instanceof EmptyExpression) {
                return;
            }
            int mark = this.controller.getOperandStack().getStackLength();
            expr.visit(this.controller.getAcg());
            this.controller.getOperandStack().popDownTo(mark);
        };
        if (expression instanceof ClosureListExpression) {
            ((ClosureListExpression)expression).getExpressions().forEach(visit);
        } else {
            visit.accept(expression);
        }
    }

    private void visitConditionOfLoopingStatement(BooleanExpression expression, Label breakLabel, MethodVisitor mv) {
        boolean boolHandled = false;
        if (expression.getExpression() instanceof ConstantExpression) {
            ConstantExpression constant = (ConstantExpression)expression.getExpression();
            if (constant.getValue() == Boolean.TRUE) {
                boolHandled = true;
            } else if (constant.getValue() == Boolean.FALSE) {
                boolHandled = true;
                mv.visitJumpInsn(167, breakLabel);
            }
        }
        if (!boolHandled) {
            expression.visit(this.controller.getAcg());
            this.controller.getOperandStack().jump(153, breakLabel);
        }
    }

    public void writeWhileLoop(WhileStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitWhileLoop");
        this.writeStatementLabel(statement);
        MethodVisitor mv = this.controller.getMethodVisitor();
        this.controller.getCompileStack().pushLoop(statement.getStatementLabels());
        Label continueLabel = this.controller.getCompileStack().getContinueLabel();
        Label breakLabel = this.controller.getCompileStack().getBreakLabel();
        mv.visitLabel(continueLabel);
        this.visitConditionOfLoopingStatement(statement.getBooleanExpression(), breakLabel, mv);
        statement.getLoopBlock().visit(this.controller.getAcg());
        mv.visitJumpInsn(167, continueLabel);
        mv.visitLabel(breakLabel);
        this.controller.getCompileStack().pop();
    }

    public void writeDoWhileLoop(DoWhileStatement statement) {
        this.writeStatementLabel(statement);
        this.controller.getCompileStack().pushLoop(statement.getStatementLabels());
        Label continueLabel = this.controller.getCompileStack().getContinueLabel();
        Label breakLabel = this.controller.getCompileStack().getBreakLabel();
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitLabel(continueLabel);
        statement.getLoopBlock().visit(this.controller.getAcg());
        this.visitConditionOfLoopingStatement(statement.getBooleanExpression(), breakLabel, mv);
        mv.visitJumpInsn(167, continueLabel);
        mv.visitLabel(breakLabel);
        this.controller.getCompileStack().pop();
    }

    public void writeIfElse(IfStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitIfElse");
        this.writeStatementLabel(statement);
        statement.getBooleanExpression().visit(this.controller.getAcg());
        Label l0 = this.controller.getOperandStack().jump(153);
        statement.getIfBlock().visit(this.controller.getAcg());
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (statement.getElseBlock().isEmpty()) {
            mv.visitLabel(l0);
        } else {
            Label l1 = new Label();
            mv.visitJumpInsn(167, l1);
            mv.visitLabel(l0);
            statement.getElseBlock().visit(this.controller.getAcg());
            mv.visitLabel(l1);
        }
    }

    public void writeTryCatchFinally(TryCatchStatement statement) {
        this.writeStatementLabel(statement);
        MethodVisitor mv = this.controller.getMethodVisitor();
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        Statement tryStatement = statement.getTryStatement();
        Statement finallyStatement = statement.getFinallyStatement();
        CompileStack.BlockRecorder tryBlock = this.makeBlockRecorder(finallyStatement);
        StatementWriter.startRange(tryBlock, mv);
        tryStatement.visit(this.controller.getAcg());
        Label finallyStart = new Label();
        boolean fallthroughFinally = false;
        if (GeneralUtils.maybeFallsThrough(tryStatement)) {
            mv.visitJumpInsn(167, finallyStart);
            fallthroughFinally = true;
        }
        StatementWriter.closeRange(tryBlock, mv);
        compileStack.pop();
        CompileStack.BlockRecorder catches = this.makeBlockRecorder(finallyStatement);
        for (CatchStatement catchStatement : statement.getCatchStatements()) {
            Label catchBlock = StatementWriter.startRange(catches, mv);
            compileStack.pushState();
            compileStack.defineVariable(catchStatement.getVariable(), true);
            catchStatement.visit(this.controller.getAcg());
            mv.visitInsn(0);
            compileStack.pop();
            StatementWriter.closeRange(catches, mv);
            if (GeneralUtils.maybeFallsThrough(catchStatement.getCode())) {
                mv.visitJumpInsn(167, finallyStart);
                fallthroughFinally = true;
            }
            compileStack.writeExceptionTable(tryBlock, catchBlock, BytecodeHelper.getClassInternalName(catchStatement.getExceptionType()));
        }
        Label catchAll = new Label();
        Label afterCatchAll = new Label();
        compileStack.writeExceptionTable(tryBlock, catchAll, null);
        compileStack.writeExceptionTable(catches, catchAll, null);
        compileStack.pop();
        if (fallthroughFinally) {
            mv.visitLabel(finallyStart);
            finallyStatement.visit(this.controller.getAcg());
            mv.visitJumpInsn(167, afterCatchAll);
        }
        mv.visitLabel(catchAll);
        operandStack.push(ClassHelper.THROWABLE_TYPE);
        int anyThrowable = compileStack.defineTemporaryVariable("throwable", true);
        finallyStatement.visit(this.controller.getAcg());
        mv.visitVarInsn(25, anyThrowable);
        mv.visitInsn(191);
        if (fallthroughFinally) {
            mv.visitLabel(afterCatchAll);
        }
        compileStack.removeVar(anyThrowable);
    }

    private CompileStack.BlockRecorder makeBlockRecorder(Statement finallyStatement) {
        CompileStack.BlockRecorder recorder = new CompileStack.BlockRecorder();
        recorder.excludedStatement = () -> {
            this.controller.getCompileStack().pushBlockRecorderVisit(recorder);
            finallyStatement.visit(this.controller.getAcg());
            this.controller.getCompileStack().popBlockRecorderVisit(recorder);
        };
        this.controller.getCompileStack().pushBlockRecorder(recorder);
        return recorder;
    }

    private static Label startRange(CompileStack.BlockRecorder br, MethodVisitor mv) {
        Label label = new Label();
        mv.visitLabel(label);
        br.startRange(label);
        return label;
    }

    private static void closeRange(CompileStack.BlockRecorder br, MethodVisitor mv) {
        Label label = new Label();
        mv.visitLabel(label);
        br.closeRange(label);
    }

    public void writeSwitch(SwitchStatement statement) {
        int i;
        this.controller.getAcg().onLineNumber(statement, "visitSwitch");
        this.writeStatementLabel(statement);
        statement.getExpression().visit(this.controller.getAcg());
        Label breakLabel = this.controller.getCompileStack().pushSwitch();
        int switchVariableIndex = this.controller.getCompileStack().defineTemporaryVariable("switch", true);
        List<CaseStatement> caseStatements = statement.getCaseStatements();
        int caseCount = caseStatements.size();
        Label[] labels = new Label[caseCount + 1];
        for (i = 0; i < caseCount; ++i) {
            labels[i] = new Label();
        }
        i = 0;
        Iterator<CaseStatement> iter = caseStatements.iterator();
        while (iter.hasNext()) {
            this.writeCaseStatement(iter.next(), switchVariableIndex, labels[i], labels[i + 1]);
            ++i;
        }
        statement.getDefaultStatement().visit(this.controller.getAcg());
        this.controller.getMethodVisitor().visitLabel(breakLabel);
        this.controller.getCompileStack().removeVar(switchVariableIndex);
        this.controller.getCompileStack().pop();
    }

    private void writeCaseStatement(CaseStatement statement, int switchVariableIndex, Label thisLabel, Label nextLabel) {
        this.controller.getAcg().onLineNumber(statement, "visitCaseStatement");
        MethodVisitor mv = this.controller.getMethodVisitor();
        OperandStack operandStack = this.controller.getOperandStack();
        mv.visitVarInsn(25, switchVariableIndex);
        statement.getExpression().visit(this.controller.getAcg());
        operandStack.box();
        this.controller.getBinaryExpressionHelper().getIsCaseMethod().call(mv);
        operandStack.replace(ClassHelper.boolean_TYPE);
        Label l0 = this.controller.getOperandStack().jump(153);
        mv.visitLabel(thisLabel);
        statement.getCode().visit(this.controller.getAcg());
        if (nextLabel != null) {
            mv.visitJumpInsn(167, nextLabel);
        }
        mv.visitLabel(l0);
    }

    public void writeBreak(BreakStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitBreakStatement");
        this.writeStatementLabel(statement);
        String name = statement.getLabel();
        Label breakLabel = this.controller.getCompileStack().getNamedBreakLabel(name);
        this.controller.getCompileStack().applyFinallyBlocks(breakLabel, true);
        this.controller.getMethodVisitor().visitJumpInsn(167, breakLabel);
    }

    public void writeContinue(ContinueStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitContinueStatement");
        this.writeStatementLabel(statement);
        String name = statement.getLabel();
        Label continueLabel = this.controller.getCompileStack().getContinueLabel();
        if (name != null) {
            continueLabel = this.controller.getCompileStack().getNamedContinueLabel(name);
        }
        this.controller.getCompileStack().applyFinallyBlocks(continueLabel, false);
        this.controller.getMethodVisitor().visitJumpInsn(167, continueLabel);
    }

    public void writeSynchronized(SynchronizedStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitSynchronizedStatement");
        this.writeStatementLabel(statement);
        MethodVisitor mv = this.controller.getMethodVisitor();
        CompileStack compileStack = this.controller.getCompileStack();
        statement.getExpression().visit(this.controller.getAcg());
        this.controller.getOperandStack().box();
        int index = compileStack.defineTemporaryVariable("synchronized", ClassHelper.OBJECT_TYPE, true);
        Label synchronizedStart = new Label();
        Label synchronizedEnd = new Label();
        Label catchAll = new Label();
        mv.visitVarInsn(25, index);
        mv.visitInsn(194);
        mv.visitLabel(synchronizedStart);
        mv.visitInsn(0);
        Runnable finallyPart = () -> {
            mv.visitVarInsn(25, index);
            mv.visitInsn(195);
        };
        CompileStack.BlockRecorder fb = new CompileStack.BlockRecorder(finallyPart);
        fb.startRange(synchronizedStart);
        compileStack.pushBlockRecorder(fb);
        statement.getCode().visit(this.controller.getAcg());
        fb.closeRange(catchAll);
        compileStack.writeExceptionTable(fb, catchAll, null);
        compileStack.pop();
        finallyPart.run();
        mv.visitJumpInsn(167, synchronizedEnd);
        mv.visitLabel(catchAll);
        finallyPart.run();
        mv.visitInsn(191);
        mv.visitLabel(synchronizedEnd);
        compileStack.removeVar(index);
    }

    public void writeAssert(AssertStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitAssertStatement");
        this.writeStatementLabel(statement);
        this.controller.getAssertionWriter().writeAssertStatement(statement);
    }

    public void writeThrow(ThrowStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitThrowStatement");
        this.writeStatementLabel(statement);
        MethodVisitor mv = this.controller.getMethodVisitor();
        statement.getExpression().visit(this.controller.getAcg());
        mv.visitTypeInsn(192, "java/lang/Throwable");
        mv.visitInsn(191);
        this.controller.getOperandStack().remove(1);
    }

    public void writeReturn(ReturnStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitReturnStatement");
        this.writeStatementLabel(statement);
        MethodVisitor mv = this.controller.getMethodVisitor();
        OperandStack operandStack = this.controller.getOperandStack();
        ClassNode returnType = this.controller.getReturnType();
        if (ClassHelper.isPrimitiveVoid(returnType)) {
            if (!statement.isReturningNullOrVoid()) {
                this.controller.getAcg().throwException("Cannot use return statement with an expression on a method that returns void");
            }
            this.controller.getCompileStack().applyBlockRecorder();
            mv.visitInsn(177);
            return;
        }
        Expression expression = statement.getExpression();
        expression.visit(this.controller.getAcg());
        operandStack.doGroovyCast(returnType);
        if (this.controller.getCompileStack().hasBlockRecorder()) {
            ClassNode type = operandStack.getTopOperand();
            int returnValueIdx = this.controller.getCompileStack().defineTemporaryVariable("returnValue", returnType, true);
            this.controller.getCompileStack().applyBlockRecorder();
            operandStack.load(type, returnValueIdx);
            this.controller.getCompileStack().removeVar(returnValueIdx);
        }
        BytecodeHelper.doReturn(mv, returnType);
        operandStack.remove(1);
    }

    public void writeExpressionStatement(ExpressionStatement statement) {
        this.controller.getAcg().onLineNumber(statement, "visitExpressionStatement: " + statement.getExpression().getClass().getName());
        this.writeStatementLabel(statement);
        int mark = this.controller.getOperandStack().getStackLength();
        Expression expression = statement.getExpression();
        expression.visit(this.controller.getAcg());
        this.controller.getOperandStack().popDownTo(mark);
    }
}

