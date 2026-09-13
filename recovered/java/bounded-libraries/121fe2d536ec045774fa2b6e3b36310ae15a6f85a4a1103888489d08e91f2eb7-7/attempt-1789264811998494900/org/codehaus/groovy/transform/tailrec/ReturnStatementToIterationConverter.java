/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.transform.tailrec.AstHelper;
import org.codehaus.groovy.transform.tailrec.UsedVariableTracker;
import org.codehaus.groovy.transform.tailrec.VariableAccessReplacer;

public class ReturnStatementToIterationConverter {
    private Statement recurStatement = AstHelper.recurStatement();

    public ReturnStatementToIterationConverter() {
    }

    public ReturnStatementToIterationConverter(Statement recurStatement) {
        this.recurStatement = recurStatement;
    }

    public Statement convert(ReturnStatement statement, Map<Integer, Map<String, Object>> positionMapping) {
        int i;
        Expression recursiveCall = statement.getExpression();
        if (!this.isAMethodCalls(recursiveCall)) {
            return statement;
        }
        LinkedHashMap<String, Map<String, Object>> tempMapping = new LinkedHashMap<String, Map<String, Object>>();
        LinkedHashMap<String, ExpressionStatement> tempDeclarations = new LinkedHashMap<String, ExpressionStatement>();
        ArrayList<ExpressionStatement> argAssignments = new ArrayList<ExpressionStatement>();
        BlockStatement result = new BlockStatement();
        result.copyStatementLabels(statement);
        List<Expression> arguments = this.getArguments(recursiveCall);
        int n = arguments.size();
        for (i = 0; i < n; ++i) {
            ExpressionStatement tempDeclaration = this.createTempDeclaration(i, positionMapping, tempMapping, tempDeclarations);
            result.addStatement(tempDeclaration);
        }
        n = arguments.size();
        for (i = 0; i < n; ++i) {
            ExpressionStatement argAssignment = this.createAssignmentToIterationVariable(arguments.get(i), i, positionMapping);
            argAssignments.add(argAssignment);
            result.addStatement(argAssignment);
        }
        Set<String> unusedTemps = this.replaceAllArgUsages(argAssignments, tempMapping);
        for (String temp : unusedTemps) {
            result.getStatements().remove(tempDeclarations.get(temp));
        }
        result.addStatement(this.recurStatement);
        return result;
    }

    private ExpressionStatement createAssignmentToIterationVariable(Expression expression, int index, Map<Integer, Map<String, Object>> positionMapping) {
        String argName = (String)positionMapping.get(index).get("name");
        ClassNode argAndTempType = (ClassNode)positionMapping.get(index).get("type");
        ExpressionStatement argAssignment = (ExpressionStatement)GeneralUtils.assignS(GeneralUtils.varX(argName, argAndTempType), expression);
        return argAssignment;
    }

    private ExpressionStatement createTempDeclaration(int index, Map<Integer, Map<String, Object>> positionMapping, Map<String, Map<String, Object>> tempMapping, Map<String, ExpressionStatement> tempDeclarations) {
        String argName = (String)positionMapping.get(index).get("name");
        ClassNode argAndTempType = (ClassNode)positionMapping.get(index).get("type");
        String tempName = "_" + argName + "_";
        ExpressionStatement tempDeclaration = AstHelper.createVariableAlias(tempName, argAndTempType, argName);
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>(2);
        map.put("name", tempName);
        map.put("type", argAndTempType);
        tempMapping.put(argName, map);
        tempDeclarations.put(tempName, tempDeclaration);
        return tempDeclaration;
    }

    private List<Expression> getArguments(Expression recursiveCall) {
        if (recursiveCall instanceof MethodCallExpression) {
            return ((TupleExpression)((MethodCallExpression)recursiveCall).getArguments()).getExpressions();
        }
        if (recursiveCall instanceof StaticMethodCallExpression) {
            return ((TupleExpression)((StaticMethodCallExpression)recursiveCall).getArguments()).getExpressions();
        }
        return null;
    }

    private boolean isAMethodCalls(Expression expression) {
        Class<?> clazz = expression.getClass();
        return MethodCallExpression.class == clazz || StaticMethodCallExpression.class == clazz;
    }

    private Set<String> replaceAllArgUsages(List<ExpressionStatement> iterationVariablesAssignmentNodes, Map<String, Map<String, Object>> tempMapping) {
        Set<String> unusedTempNames = tempMapping.values().stream().map(nameAndType -> (String)nameAndType.get("name")).collect(Collectors.toSet());
        UsedVariableTracker tracker = new UsedVariableTracker();
        for (ExpressionStatement statement : iterationVariablesAssignmentNodes) {
            this.replaceArgUsageByTempUsage((BinaryExpression)statement.getExpression(), tempMapping, tracker);
        }
        unusedTempNames = DefaultGroovyMethods.minus(unusedTempNames, tracker.getUsedVariableNames());
        return unusedTempNames;
    }

    private void replaceArgUsageByTempUsage(BinaryExpression binary, Map tempMapping, UsedVariableTracker tracker) {
        VariableAccessReplacer replacer = new VariableAccessReplacer(tempMapping, tracker);
        replacer.replaceIn(binary);
    }

    public Statement getRecurStatement() {
        return this.recurStatement;
    }

    public void setRecurStatement(Statement recurStatement) {
        this.recurStatement = recurStatement;
    }
}

