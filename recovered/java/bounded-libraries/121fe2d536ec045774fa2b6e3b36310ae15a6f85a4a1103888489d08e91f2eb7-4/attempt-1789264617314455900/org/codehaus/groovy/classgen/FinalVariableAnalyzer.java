/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.EmptyExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;

public class FinalVariableAnalyzer
extends ClassCodeVisitorSupport {
    private final SourceUnit sourceUnit;
    private final VariableNotFinalCallback callback;
    private Set<Variable> declaredFinalVariables = null;
    private boolean inAssignmentRHS = false;
    private boolean inArgumentList = false;
    private final Deque<Map<Variable, VariableState>> assignmentTracker = new LinkedList<Map<Variable, VariableState>>();

    public FinalVariableAnalyzer(SourceUnit sourceUnit) {
        this(sourceUnit, null);
    }

    public FinalVariableAnalyzer(SourceUnit sourceUnit, VariableNotFinalCallback callback) {
        this.callback = callback;
        this.sourceUnit = sourceUnit;
        this.assignmentTracker.add(new StateMap());
    }

    private Map<Variable, VariableState> pushState() {
        StateMap state = new StateMap();
        state.putAll(this.getState());
        this.assignmentTracker.add(state);
        return state;
    }

    private static Variable getTarget(Variable v) {
        if (v instanceof VariableExpression) {
            Variable t = ((VariableExpression)v).getAccessedVariable();
            if (t == v) {
                return t;
            }
            return FinalVariableAnalyzer.getTarget(t);
        }
        return v;
    }

    private Map<Variable, VariableState> popState() {
        return this.assignmentTracker.removeLast();
    }

    private Map<Variable, VariableState> getState() {
        return this.assignmentTracker.getLast();
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.sourceUnit;
    }

    public boolean isEffectivelyFinal(Variable v) {
        VariableState state = this.getState().get(v);
        return v instanceof Parameter && state == null || state != null && state.isFinal();
    }

    @Override
    public void visitBlockStatement(BlockStatement block) {
        Set<Variable> old = this.declaredFinalVariables;
        this.declaredFinalVariables = new HashSet<Variable>();
        super.visitBlockStatement(block);
        this.declaredFinalVariables = old;
    }

    @Override
    public void visitArgumentlistExpression(ArgumentListExpression ale) {
        boolean old = this.inArgumentList;
        this.inArgumentList = true;
        super.visitArgumentlistExpression(ale);
        this.inArgumentList = old;
    }

    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        boolean assignment = StaticTypeCheckingSupport.isAssignment(expression.getOperation().getType());
        boolean isDeclaration = expression instanceof DeclarationExpression;
        Expression leftExpression = expression.getLeftExpression();
        Expression rightExpression = expression.getRightExpression();
        if (isDeclaration) {
            this.recordFinalVars(leftExpression);
        }
        this.inAssignmentRHS = assignment;
        rightExpression.visit(this);
        this.inAssignmentRHS = false;
        leftExpression.visit(this);
        if (assignment) {
            this.recordAssignments(expression, isDeclaration, leftExpression, rightExpression);
        }
    }

    private void recordAssignments(BinaryExpression expression, boolean isDeclaration, Expression leftExpression, Expression rightExpression) {
        if (leftExpression instanceof Variable) {
            boolean uninitialized = isDeclaration && rightExpression instanceof EmptyExpression;
            this.recordAssignment((Variable)((Object)leftExpression), isDeclaration, uninitialized, false, expression);
        } else if (leftExpression instanceof TupleExpression) {
            TupleExpression te = (TupleExpression)leftExpression;
            for (Expression next : te.getExpressions()) {
                if (!(next instanceof Variable)) continue;
                this.recordAssignment((Variable)((Object)next), isDeclaration, false, false, next);
            }
        }
    }

    private void recordFinalVars(Expression leftExpression) {
        if (leftExpression instanceof VariableExpression) {
            VariableExpression var = (VariableExpression)leftExpression;
            if (Modifier.isFinal(var.getModifiers())) {
                this.declaredFinalVariables.add(var);
            }
        } else if (leftExpression instanceof TupleExpression) {
            TupleExpression te = (TupleExpression)leftExpression;
            for (Expression next : te.getExpressions()) {
                if (!(next instanceof Variable)) continue;
                this.declaredFinalVariables.add((Variable)((Object)next));
            }
        }
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        boolean old = this.inAssignmentRHS;
        this.inAssignmentRHS = false;
        StateMap origState = new StateMap();
        origState.putAll(this.getState());
        super.visitClosureExpression(expression);
        this.cleanLocalVars(origState, this.getState());
        this.inAssignmentRHS = old;
    }

    private void cleanLocalVars(Map<Variable, VariableState> origState, Map<Variable, VariableState> state) {
        Iterator<Map.Entry<Variable, VariableState>> iter = state.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Variable, VariableState> next = iter.next();
            Variable key = next.getKey();
            if (!(key instanceof VariableExpression) || ((VariableExpression)key).getAccessedVariable() != key || origState.containsKey(key)) continue;
            iter.remove();
        }
    }

    @Override
    public void visitPrefixExpression(PrefixExpression expression) {
        this.inAssignmentRHS = expression.getExpression() instanceof VariableExpression;
        super.visitPrefixExpression(expression);
        this.inAssignmentRHS = false;
        this.checkPrePostfixOperation(expression.getExpression(), expression);
    }

    @Override
    public void visitPostfixExpression(PostfixExpression expression) {
        this.inAssignmentRHS = expression.getExpression() instanceof VariableExpression;
        super.visitPostfixExpression(expression);
        this.inAssignmentRHS = false;
        this.checkPrePostfixOperation(expression.getExpression(), expression);
    }

    private void checkPrePostfixOperation(Expression variable, Expression originalExpression) {
        if (variable instanceof Variable) {
            Variable accessed;
            this.recordAssignment((Variable)((Object)variable), false, false, true, originalExpression);
            if (variable instanceof VariableExpression && (accessed = ((VariableExpression)variable).getAccessedVariable()) != variable) {
                this.recordAssignment(accessed, false, false, true, originalExpression);
            }
        }
    }

    @Override
    public void visitVariableExpression(VariableExpression expression) {
        super.visitVariableExpression(expression);
        Map<Variable, VariableState> state = this.getState();
        Variable key = expression.getAccessedVariable();
        if (key == null) {
            this.fixVar(expression);
            key = expression.getAccessedVariable();
        }
        if (key != null && !key.isClosureSharedVariable() && this.callback != null) {
            VariableState variableState = state.get(key);
            if ((this.inAssignmentRHS || this.inArgumentList) && (variableState == VariableState.is_uninitialized || variableState == VariableState.is_ambiguous)) {
                this.callback.variableNotAlwaysInitialized(expression);
            }
        }
    }

    @Override
    public void visitIfElse(IfStatement ifElse) {
        this.visitStatement(ifElse);
        ifElse.getBooleanExpression().visit(this);
        Map<Variable, VariableState> ifState = this.pushState();
        ifElse.getIfBlock().visit(this);
        this.popState();
        Map<Variable, VariableState> elseState = this.pushState();
        ifElse.getElseBlock().visit(this);
        this.popState();
        Map<Variable, VariableState> curState = this.getState();
        HashSet<Variable> allVars = new HashSet<Variable>();
        allVars.addAll(curState.keySet());
        allVars.addAll(ifState.keySet());
        allVars.addAll(elseState.keySet());
        boolean ifReturning = this.returningBlock(ifElse.getIfBlock());
        boolean elseReturning = this.returningBlock(ifElse.getElseBlock());
        for (Variable var : allVars) {
            VariableState elseValue;
            VariableState beforeValue = curState.get(var);
            if (beforeValue == null) continue;
            VariableState ifValue = ifState.get(var);
            if (ifValue == (elseValue = elseState.get(var)) || elseReturning && !ifReturning) {
                curState.put(var, ifValue);
                continue;
            }
            if (ifReturning && !elseReturning) {
                curState.put(var, elseValue);
                continue;
            }
            if (ifReturning) {
                curState.put(var, beforeValue);
                continue;
            }
            curState.put(var, beforeValue == VariableState.is_uninitialized ? VariableState.is_ambiguous : VariableState.is_var);
        }
    }

    @Override
    public void visitSwitch(SwitchStatement switchS) {
        this.visitStatement(switchS);
        switchS.getExpression().visit(this);
        ArrayList<CaseStatement> branches = new ArrayList<CaseStatement>(switchS.getCaseStatements());
        if (!(switchS.getDefaultStatement() instanceof EmptyStatement)) {
            branches.add((CaseStatement)switchS.getDefaultStatement());
        }
        ArrayList<Map<Variable, VariableState>> afterStates = new ArrayList<Map<Variable, VariableState>>();
        int lastIndex = branches.size() - 1;
        for (int i = 0; i <= lastIndex; ++i) {
            this.pushState();
            boolean done = false;
            boolean returning = false;
            int n = i;
            while (!done) {
                Statement branch;
                Statement block = branch = (Statement)branches.get(n);
                if (branch instanceof CaseStatement) {
                    CaseStatement caseS = (CaseStatement)branch;
                    block = caseS.getCode();
                    caseS.getExpression().visit(this);
                }
                block.visit(this);
                boolean bl = done = n == lastIndex || !this.fallsThrough(block);
                if (done) {
                    returning = this.returningBlock(block);
                }
                ++n;
            }
            if (!returning) {
                afterStates.add(this.getState());
            }
            this.popState();
        }
        if (afterStates.isEmpty()) {
            return;
        }
        Map<Variable, VariableState> beforeState = this.getState();
        HashSet<Variable> allVars = new HashSet<Variable>(beforeState.keySet());
        for (Map map : afterStates) {
            allVars.addAll(map.keySet());
        }
        for (Variable variable : allVars) {
            VariableState merged;
            VariableState beforeValue = beforeState.get(variable);
            if (beforeValue == null || (merged = (VariableState)((Object)((Map)afterStates.get(0)).get(variable))) == null) continue;
            if (afterStates.stream().allMatch(state -> merged.equals(state.get(var)))) {
                beforeState.put(variable, merged);
                continue;
            }
            VariableState different = beforeValue == VariableState.is_uninitialized ? VariableState.is_ambiguous : VariableState.is_var;
            beforeState.put(variable, different);
        }
    }

    @Override
    public void visitTryCatchFinally(TryCatchStatement statement) {
        this.visitStatement(statement);
        HashMap<Variable, VariableState> beforeTryState = new HashMap<Variable, VariableState>(this.getState());
        this.pushState();
        Statement tryStatement = statement.getTryStatement();
        tryStatement.visit(this);
        HashMap<Variable, VariableState> afterTryState = new HashMap<Variable, VariableState>(this.getState());
        Statement finallyStatement = statement.getFinallyStatement();
        ArrayList<Map<Variable, VariableState>> afterStates = new ArrayList<Map<Variable, VariableState>>();
        finallyStatement.visit(this);
        if (!this.returningBlock(tryStatement)) {
            afterStates.add(new HashMap<Variable, VariableState>(this.getState()));
        }
        this.popState();
        if (statement.getCatchStatements().isEmpty()) {
            finallyStatement.visit(this);
            if (!this.returningBlock(tryStatement)) {
                afterStates.add(new HashMap<Variable, VariableState>(this.getState()));
            }
        }
        for (CatchStatement catchStatement : statement.getCatchStatements()) {
            this.visitCatchFinally(beforeTryState, afterStates, catchStatement, finallyStatement);
            this.visitCatchFinally(afterTryState, afterStates, catchStatement, finallyStatement);
        }
        if (afterStates.isEmpty()) {
            return;
        }
        Map corrected = (Map)afterStates.remove(0);
        for (Map map : afterStates) {
            for (Map.Entry entry : corrected.entrySet()) {
                Variable var = (Variable)entry.getKey();
                VariableState currentCorrectedState = (VariableState)((Object)entry.getValue());
                VariableState candidateCorrectedState = (VariableState)((Object)map.get(var));
                if (currentCorrectedState == VariableState.is_ambiguous || currentCorrectedState == candidateCorrectedState) continue;
                if (currentCorrectedState == VariableState.is_uninitialized || candidateCorrectedState == VariableState.is_uninitialized) {
                    corrected.put(var, VariableState.is_ambiguous);
                    continue;
                }
                corrected.put(var, VariableState.is_var);
            }
        }
        this.getState().putAll(corrected);
    }

    private void visitCatchFinally(Map<Variable, VariableState> initialVarState, List<Map<Variable, VariableState>> afterTryCatchStates, CatchStatement catchStatement, Statement finallyStatement) {
        this.pushState();
        this.getState().putAll(initialVarState);
        Statement code = catchStatement.getCode();
        catchStatement.visit(this);
        finallyStatement.visit(this);
        if (code == null || !this.returningBlock(code)) {
            afterTryCatchStates.add(new HashMap<Variable, VariableState>(this.getState()));
        }
        this.popState();
    }

    private boolean returningBlock(Statement block) {
        if (block instanceof ReturnStatement || block instanceof ThrowStatement) {
            return true;
        }
        if (!(block instanceof BlockStatement)) {
            return false;
        }
        BlockStatement bs = (BlockStatement)block;
        if (bs.getStatements().size() == 0) {
            return false;
        }
        Statement last = DefaultGroovyMethods.last(bs.getStatements());
        return last instanceof ReturnStatement || last instanceof ThrowStatement;
    }

    private boolean fallsThrough(Statement statement) {
        if (statement instanceof EmptyStatement) {
            return true;
        }
        if (statement instanceof ReturnStatement) {
            return false;
        }
        BlockStatement block = (BlockStatement)statement;
        if (block.getStatements().size() == 0) {
            return true;
        }
        Statement last = DefaultGroovyMethods.last(block.getStatements());
        boolean completesAbruptly = last instanceof ReturnStatement || last instanceof BreakStatement || last instanceof ThrowStatement || last instanceof ContinueStatement;
        return !completesAbruptly;
    }

    private void recordAssignment(Variable var, boolean isDeclaration, boolean uninitialized, boolean forceVariable, Expression expression) {
        VariableState variableState;
        if (var == null) {
            return;
        }
        if (FinalVariableAnalyzer.getTarget(var) == null) {
            this.fixVar(var);
            if (FinalVariableAnalyzer.getTarget(var) == null) {
                return;
            }
        }
        if (!isDeclaration && var.isClosureSharedVariable()) {
            this.getState().put(var, VariableState.is_var);
        }
        if ((variableState = this.getState().get(var)) == null) {
            VariableState variableState2 = variableState = uninitialized ? VariableState.is_uninitialized : VariableState.is_final;
            if (FinalVariableAnalyzer.getTarget(var) instanceof Parameter) {
                variableState = VariableState.is_var;
            }
        } else {
            variableState = variableState.getNext();
        }
        if (forceVariable) {
            variableState = VariableState.is_var;
        }
        this.getState().put(var, variableState);
        if ((variableState == VariableState.is_var || variableState == VariableState.is_ambiguous) && this.callback != null) {
            this.callback.variableNotFinal(var, expression);
        }
    }

    private void fixVar(Variable var) {
        if (FinalVariableAnalyzer.getTarget(var) == null && var instanceof VariableExpression && this.getState() != null && var.getName() != null) {
            for (Variable v : this.getState().keySet()) {
                if (!var.getName().equals(v.getName())) continue;
                ((VariableExpression)var).setAccessedVariable(v);
                break;
            }
        }
    }

    public static interface VariableNotFinalCallback {
        public void variableNotFinal(Variable var1, Expression var2);

        public void variableNotAlwaysInitialized(VariableExpression var1);
    }

    private static class StateMap
    extends HashMap<Variable, VariableState> {
        private static final long serialVersionUID = -5881634573411342092L;

        private StateMap() {
        }

        @Override
        public VariableState get(Object key) {
            return (VariableState)((Object)super.get(FinalVariableAnalyzer.getTarget((Variable)key)));
        }

        @Override
        public VariableState put(Variable key, VariableState value) {
            return super.put(FinalVariableAnalyzer.getTarget(key), value);
        }
    }

    private static enum VariableState {
        is_uninitialized(false),
        is_final(true),
        is_var(false),
        is_ambiguous(false);

        private final boolean isFinal;

        private VariableState(boolean isFinal) {
            this.isFinal = isFinal;
        }

        public VariableState getNext() {
            switch (this) {
                case is_uninitialized: {
                    return is_final;
                }
            }
            return is_var;
        }

        public boolean isFinal() {
            return this.isFinal;
        }
    }
}

