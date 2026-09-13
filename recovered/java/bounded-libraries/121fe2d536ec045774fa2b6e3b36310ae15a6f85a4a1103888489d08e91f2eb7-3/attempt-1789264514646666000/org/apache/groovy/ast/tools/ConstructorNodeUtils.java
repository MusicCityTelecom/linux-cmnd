/*
 * Decompiled with CFR 0.152.
 */
package org.apache.groovy.ast.tools;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.groovy.ast.tools.MethodCallUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.transform.ImmutableASTTransformation;

public class ConstructorNodeUtils {
    private static final ClassNode EXCEPTION = ClassHelper.make(IllegalArgumentException.class);
    private static final ClassNode IMMUTABLE_TYPE = ClassHelper.make(ImmutableASTTransformation.class);
    private static final ClassNode STRINGBUILDER_TYPE = ClassHelper.make(StringBuilder.class);
    private static final ClassNode INVOKER_TYPE = ClassHelper.make(InvokerHelper.class);

    private ConstructorNodeUtils() {
    }

    public static ConstructorCallExpression getFirstIfSpecialConstructorCall(Statement code) {
        if (code == null) {
            return null;
        }
        if (code instanceof BlockStatement) {
            BlockStatement block = (BlockStatement)code;
            List<Statement> statementList = block.getStatements();
            if (statementList.isEmpty()) {
                return null;
            }
            return ConstructorNodeUtils.getFirstIfSpecialConstructorCall(statementList.get(0));
        }
        if (!(code instanceof ExpressionStatement)) {
            return null;
        }
        Expression expression = ((ExpressionStatement)code).getExpression();
        if (!(expression instanceof ConstructorCallExpression)) {
            return null;
        }
        ConstructorCallExpression cce = (ConstructorCallExpression)expression;
        if (cce.isSpecialCall()) {
            return cce;
        }
        return null;
    }

    public static Statement checkPropNamesS(VariableExpression namedArgs, boolean pojo, List<PropertyNode> props) {
        if (!pojo) {
            return GeneralUtils.stmt(GeneralUtils.callX(IMMUTABLE_TYPE, "checkPropNames", (Expression)GeneralUtils.args(GeneralUtils.varX("this"), namedArgs)));
        }
        VariableExpression validNames = GeneralUtils.localVarX("validNames", ClassHelper.LIST_TYPE);
        Parameter name = GeneralUtils.param(ClassHelper.STRING_TYPE, "arg");
        MethodCallExpression names = GeneralUtils.callX(namedArgs, "keySet");
        names.setImplicitThis(false);
        MethodCallExpression isNameValid = GeneralUtils.callX((Expression)validNames, "contains", (Expression)GeneralUtils.varX(name));
        isNameValid.setImplicitThis(false);
        VariableExpression sb = GeneralUtils.localVarX("sb");
        Expression toString = pojo ? MethodCallUtils.toStringX(sb) : GeneralUtils.callX(INVOKER_TYPE, "toString", (Expression)sb);
        BlockStatement errorBlock = GeneralUtils.block(GeneralUtils.declS(sb, GeneralUtils.ctorX(STRINGBUILDER_TYPE)), MethodCallUtils.appendS(sb, GeneralUtils.constX("Unknown named argument: ")), MethodCallUtils.appendS(sb, GeneralUtils.varX(name)), GeneralUtils.throwS(GeneralUtils.ctorX(EXCEPTION, toString)));
        return GeneralUtils.block(GeneralUtils.declS(validNames, GeneralUtils.listX(props.stream().map(p -> GeneralUtils.constX(p.getName())).collect(Collectors.toList()))), GeneralUtils.forS(name, names, GeneralUtils.ifS((Expression)GeneralUtils.notX(isNameValid), errorBlock)));
    }
}

