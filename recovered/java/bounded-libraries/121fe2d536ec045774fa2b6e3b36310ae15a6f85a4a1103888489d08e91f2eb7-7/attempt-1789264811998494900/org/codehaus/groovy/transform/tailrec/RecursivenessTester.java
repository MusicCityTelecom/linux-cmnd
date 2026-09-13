/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.tailrec;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

public class RecursivenessTester {
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    public boolean isRecursive(Map<String, ASTNode> params) {
        ASTNode method = params.get("method");
        assert (MethodNode.class.equals(method.getClass()));
        ASTNode call = params.get("call");
        Class<?> callClass = call.getClass();
        assert (MethodCallExpression.class.equals(callClass) || StaticMethodCallExpression.class.equals(callClass));
        if (callClass == MethodCallExpression.class) {
            return this.isRecursive((MethodNode)method, (MethodCallExpression)call);
        }
        return this.isRecursive((MethodNode)method, (StaticMethodCallExpression)call);
    }

    public boolean isRecursive(MethodNode method, MethodCallExpression call) {
        if (!this.isCallToThis(call)) {
            return false;
        }
        if (!(call.getMethod() instanceof ConstantExpression)) {
            return false;
        }
        if (!((ConstantExpression)call.getMethod()).getValue().equals(method.getName())) {
            return false;
        }
        return this.methodParamsMatchCallArgs(method, call);
    }

    public boolean isRecursive(MethodNode method, StaticMethodCallExpression call) {
        if (!method.isStatic()) {
            return false;
        }
        if (!method.getDeclaringClass().equals(call.getOwnerType())) {
            return false;
        }
        if (!call.getMethod().equals(method.getName())) {
            return false;
        }
        return this.methodParamsMatchCallArgs(method, call);
    }

    private boolean isCallToThis(MethodCallExpression call) {
        if (call.getObjectExpression() == null) {
            return call.isImplicitThis();
        }
        if (!(call.getObjectExpression() instanceof VariableExpression)) {
            return false;
        }
        return (Boolean)DefaultGroovyMethods.invokeMethod(call.getObjectExpression(), "isThisExpression", EMPTY_OBJECT_ARRAY);
    }

    private boolean methodParamsMatchCallArgs(MethodNode method, Expression call) {
        TupleExpression arguments = call instanceof MethodCallExpression ? (TupleExpression)((MethodCallExpression)call).getArguments() : (TupleExpression)((StaticMethodCallExpression)call).getArguments();
        if (method.getParameters().length != arguments.getExpressions().size()) {
            return false;
        }
        List classNodePairs = DefaultGroovyMethods.transpose(Arrays.asList(Arrays.stream(method.getParameters()).map(Parameter::getType).collect(Collectors.toList()), arguments.getExpressions().stream().map(Expression::getType).collect(Collectors.toList())));
        return classNodePairs.stream().allMatch(t -> this.areTypesCallCompatible((ClassNode)t.get(0), (ClassNode)t.get(1)));
    }

    private Boolean areTypesCallCompatible(ClassNode argType, ClassNode paramType) {
        ClassNode boxedParam;
        ClassNode boxedArg = ClassHelper.getWrapper(argType);
        return boxedArg.isDerivedFrom(boxedParam = ClassHelper.getWrapper(paramType)) || boxedParam.isDerivedFrom(boxedArg);
    }
}

