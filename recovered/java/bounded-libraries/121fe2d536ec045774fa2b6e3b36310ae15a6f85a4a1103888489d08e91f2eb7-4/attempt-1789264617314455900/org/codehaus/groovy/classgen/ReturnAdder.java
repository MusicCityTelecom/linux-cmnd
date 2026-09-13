/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.BytecodeSequence;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class ReturnAdder {
    private static final ReturnStatementListener DEFAULT_LISTENER = returnStatement -> {};
    private final ReturnStatementListener listener;
    private final boolean doAdd;

    public ReturnAdder() {
        this.listener = DEFAULT_LISTENER;
        this.doAdd = true;
    }

    public ReturnAdder(ReturnStatementListener listener) {
        this.listener = Objects.requireNonNull(listener);
        this.doAdd = false;
    }

    @Deprecated
    public static void addReturnIfNeeded(MethodNode node) {
        new ReturnAdder().visitMethod(node);
    }

    public void visitMethod(MethodNode node) {
        Statement code;
        if (!node.isVoidMethod() && (code = node.getCode()) != null) {
            code = this.addReturnsIfNeeded(code, node.getVariableScope());
            if (this.doAdd) {
                node.setCode(code);
            }
        }
    }

    private Statement addReturnsIfNeeded(Statement statement, VariableScope scope) {
        if (statement instanceof ReturnStatement || statement instanceof ThrowStatement || statement instanceof EmptyStatement || statement instanceof BytecodeSequence) {
            return statement;
        }
        if (statement == null) {
            ReturnStatement returnStatement2 = new ReturnStatement(GeneralUtils.nullX());
            this.listener.returnStatementAdded(returnStatement2);
            return returnStatement2;
        }
        if (statement instanceof ExpressionStatement) {
            Expression expression = ((ExpressionStatement)statement).getExpression();
            ReturnStatement returnStatement3 = new ReturnStatement(expression);
            returnStatement3.copyStatementLabels(statement);
            returnStatement3.setSourcePosition(statement.getLineNumber() < 0 ? expression : statement);
            this.listener.returnStatementAdded(returnStatement3);
            return returnStatement3;
        }
        if (statement instanceof SynchronizedStatement) {
            SynchronizedStatement syncStatement = (SynchronizedStatement)statement;
            Statement code = this.addReturnsIfNeeded(syncStatement.getCode(), scope);
            if (this.doAdd) {
                syncStatement.setCode(code);
            }
            return syncStatement;
        }
        if (statement instanceof IfStatement) {
            IfStatement ifElseStatement = (IfStatement)statement;
            Statement ifBlock = this.addReturnsIfNeeded(ifElseStatement.getIfBlock(), scope);
            Statement elseBlock = this.addReturnsIfNeeded(ifElseStatement.getElseBlock(), scope);
            if (this.doAdd) {
                ifElseStatement.setIfBlock(ifBlock);
                ifElseStatement.setElseBlock(elseBlock);
            }
            return ifElseStatement;
        }
        if (statement instanceof SwitchStatement) {
            SwitchStatement switchStatement = (SwitchStatement)statement;
            Statement defaultStatement = switchStatement.getDefaultStatement();
            List<CaseStatement> caseStatements = switchStatement.getCaseStatements();
            Iterator<CaseStatement> it = caseStatements.iterator();
            while (it.hasNext()) {
                CaseStatement caseStatement = it.next();
                Statement code = this.adjustSwitchCaseCode(caseStatement.getCode(), scope, defaultStatement == EmptyStatement.INSTANCE && !it.hasNext());
                if (!this.doAdd) continue;
                caseStatement.setCode(code);
            }
            defaultStatement = this.adjustSwitchCaseCode(defaultStatement, scope, true);
            if (this.doAdd) {
                switchStatement.setDefaultStatement(defaultStatement);
            }
            return switchStatement;
        }
        if (statement instanceof TryCatchStatement) {
            boolean hasFinally;
            TryCatchStatement tryCatchFinally = (TryCatchStatement)statement;
            boolean[] missesReturn = new boolean[1];
            new ReturnAdder(returnStatement -> {
                missesReturn[0] = true;
            }).addReturnsIfNeeded(tryCatchFinally.getFinallyStatement(), scope);
            boolean bl = hasFinally = !(tryCatchFinally.getFinallyStatement() instanceof EmptyStatement);
            if (hasFinally && !missesReturn[0]) {
                return tryCatchFinally;
            }
            Statement tryStatement = this.addReturnsIfNeeded(tryCatchFinally.getTryStatement(), scope);
            if (this.doAdd) {
                tryCatchFinally.setTryStatement(tryStatement);
            }
            for (CatchStatement catchStatement : tryCatchFinally.getCatchStatements()) {
                Statement code = this.addReturnsIfNeeded(catchStatement.getCode(), scope);
                if (!this.doAdd) continue;
                catchStatement.setCode(code);
            }
            return tryCatchFinally;
        }
        if (statement instanceof BlockStatement) {
            BlockStatement blockStatement = (BlockStatement)statement;
            if (blockStatement.isEmpty()) {
                ReturnStatement returnStatement4 = new ReturnStatement(GeneralUtils.nullX());
                returnStatement4.copyStatementLabels(blockStatement);
                returnStatement4.setSourcePosition(blockStatement);
                this.listener.returnStatementAdded(returnStatement4);
                return returnStatement4;
            }
            List<Statement> statements = blockStatement.getStatements();
            int lastIndex = statements.size() - 1;
            Statement last = this.addReturnsIfNeeded(statements.get(lastIndex), blockStatement.getVariableScope());
            if (this.doAdd) {
                statements.set(lastIndex, last);
            }
            return blockStatement;
        }
        ArrayList<Statement> statements = new ArrayList<Statement>(2);
        statements.add(statement);
        ReturnStatement returnStatement5 = new ReturnStatement(GeneralUtils.nullX());
        this.listener.returnStatementAdded(returnStatement5);
        statements.add(returnStatement5);
        BlockStatement blockStatement = new BlockStatement(statements, new VariableScope(scope));
        blockStatement.setSourcePosition(statement);
        return blockStatement;
    }

    private Statement adjustSwitchCaseCode(Statement statement, VariableScope scope, boolean lastCase) {
        if (!statement.isEmpty() && statement instanceof BlockStatement) {
            BlockStatement block = (BlockStatement)statement;
            int breakIndex = block.getStatements().size() - 1;
            if (block.getStatements().get(breakIndex) instanceof BreakStatement) {
                if (this.doAdd) {
                    Statement breakStatement = block.getStatements().remove(breakIndex);
                    if (breakIndex == 0) {
                        block.addStatement(EmptyStatement.INSTANCE);
                    }
                    this.addReturnsIfNeeded(block, scope);
                    Statement lastStatement = DefaultGroovyMethods.last(block.getStatements());
                    if (!(lastStatement instanceof ReturnStatement) && !(lastStatement instanceof ThrowStatement)) {
                        block.addStatement(breakStatement);
                    }
                } else {
                    this.addReturnsIfNeeded(new BlockStatement(block.getStatements().subList(0, breakIndex), null), scope);
                }
            } else if (lastCase) {
                return this.addReturnsIfNeeded(statement, scope);
            }
        }
        return statement;
    }

    @FunctionalInterface
    public static interface ReturnStatementListener {
        public void returnStatementAdded(ReturnStatement var1);
    }
}

