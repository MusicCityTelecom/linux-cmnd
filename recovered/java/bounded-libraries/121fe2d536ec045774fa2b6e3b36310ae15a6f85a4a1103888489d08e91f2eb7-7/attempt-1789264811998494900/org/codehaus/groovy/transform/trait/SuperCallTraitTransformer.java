/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.trait;

import java.util.List;
import java.util.function.Function;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.tools.ClosureUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.Types;
import org.codehaus.groovy.transform.trait.Traits;

class SuperCallTraitTransformer
extends ClassCodeExpressionTransformer {
    static final String UNRESOLVED_HELPER_CLASS = "UNRESOLVED_HELPER_CLASS";
    private final SourceUnit unit;

    SuperCallTraitTransformer(SourceUnit unit) {
        this.unit = unit;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.unit;
    }

    @Override
    public Expression transform(Expression exp) {
        if (exp instanceof BinaryExpression) {
            return this.transformBinaryExpression((BinaryExpression)exp);
        }
        if (exp instanceof ClosureExpression) {
            return this.transformClosureExpression((ClosureExpression)exp);
        }
        if (exp instanceof PropertyExpression) {
            return this.transformPropertyExpression((PropertyExpression)exp);
        }
        if (exp instanceof MethodCallExpression) {
            return this.transformMethodCallExpression((MethodCallExpression)exp);
        }
        return super.transform(exp);
    }

    private Expression transformBinaryExpression(BinaryExpression exp) {
        PropertyExpression leftExpression;
        ClassNode traitType;
        BinaryExpression bin;
        Expression trn;
        if (Types.isAssignment(exp.getOperation().getType())) {
            exp.getLeftExpression().putNodeMetaData("assign.target", exp.getOperation());
        }
        if ((trn = super.transform(exp)) instanceof BinaryExpression && (bin = (BinaryExpression)trn).getOperation().getType() == 100 && bin.getLeftExpression() instanceof PropertyExpression && (traitType = this.getTraitSuperTarget((leftExpression = (PropertyExpression)bin.getLeftExpression()).getObjectExpression())) != null) {
            ClassNode helperType = this.getHelper(traitType);
            String setterName = GeneralUtils.getSetterName(leftExpression.getPropertyAsString());
            for (MethodNode method : helperType.getMethods(setterName)) {
                Parameter[] parameters = method.getParameters();
                if (parameters.length != 2 || !SuperCallTraitTransformer.isSelfType(parameters[0], traitType)) continue;
                Expression thisExpr = ClassHelper.isClassType(parameters[0].getType()) ? GeneralUtils.thisPropX(false, "class") : GeneralUtils.varX("this");
                MethodCallExpression setterCall = GeneralUtils.callX((Expression)GeneralUtils.classX(helperType), setterName, (Expression)GeneralUtils.args(thisExpr, bin.getRightExpression()));
                setterCall.getObjectExpression().setSourcePosition(leftExpression.getObjectExpression());
                setterCall.getMethod().setSourcePosition(leftExpression.getProperty());
                setterCall.setSpreadSafe(leftExpression.isSpreadSafe());
                setterCall.setImplicitThis(false);
                return setterCall;
            }
        }
        return trn;
    }

    private Expression transformClosureExpression(ClosureExpression exp) {
        for (Parameter prm : ClosureUtils.getParametersSafe(exp)) {
            Expression ini = this.transform(prm.getInitialExpression());
            prm.setInitialExpression(ini);
        }
        this.visitClassCodeContainer(exp.getCode());
        return super.transform(exp);
    }

    private Expression transformPropertyExpression(PropertyExpression exp) {
        ClassNode traitType;
        if (exp.getNodeMetaData("assign.target") == null && (traitType = this.getTraitSuperTarget(exp.getObjectExpression())) != null) {
            ClassNode helperType = this.getHelper(traitType);
            Function<MethodNode, MethodCallExpression> xform = methodNode -> {
                Expression thisExpr = ClassHelper.isClassType(methodNode.getParameters()[0].getType()) ? GeneralUtils.thisPropX(false, "class") : GeneralUtils.varX("this");
                MethodCallExpression methodCall = GeneralUtils.callX((Expression)GeneralUtils.classX(helperType), methodNode.getName(), (Expression)GeneralUtils.args(thisExpr));
                methodCall.getObjectExpression().setSourcePosition(traitType);
                methodCall.getMethod().setSourcePosition(exp.getProperty());
                methodCall.setSpreadSafe(exp.isSpreadSafe());
                methodCall.setMethodTarget((MethodNode)methodNode);
                methodCall.setImplicitThis(false);
                return methodCall;
            };
            String getterName = GeneralUtils.getGetterName(exp.getPropertyAsString());
            for (MethodNode method : helperType.getMethods(getterName)) {
                if (!method.isStatic() || method.isVoidMethod() || method.getParameters().length != 1 || !SuperCallTraitTransformer.isSelfType(method.getParameters()[0], traitType)) continue;
                return xform.apply(method);
            }
            String isserName = "is" + getterName.substring(3);
            for (MethodNode method : helperType.getMethods(isserName)) {
                if (!method.isStatic() || !ClassHelper.isPrimitiveBoolean(method.getReturnType()) || method.getParameters().length != 1 || !SuperCallTraitTransformer.isSelfType(method.getParameters()[0], traitType)) continue;
                return xform.apply(method);
            }
        }
        exp.removeNodeMetaData("assign.target");
        return super.transform(exp);
    }

    private Expression transformMethodCallExpression(MethodCallExpression exp) {
        ClassNode traitType = this.getTraitSuperTarget(exp.getObjectExpression());
        if (traitType != null) {
            ClassNode helperType = this.getHelper(traitType);
            List<MethodNode> targets = helperType.getMethods(exp.getMethodAsString());
            boolean isStatic = !targets.isEmpty() && targets.stream().map(MethodNode::getParameters).allMatch(params -> ((Parameter[])params).length > 0 && ClassHelper.isClassType(params[0].getType()));
            ArgumentListExpression newArgs = GeneralUtils.args(isStatic ? GeneralUtils.thisPropX(false, "class") : GeneralUtils.varX("this"));
            Expression arguments = exp.getArguments();
            if (arguments instanceof TupleExpression) {
                for (Expression expression : (TupleExpression)arguments) {
                    newArgs.addExpression(this.transform(expression));
                }
            } else {
                newArgs.addExpression(this.transform(arguments));
            }
            MethodCallExpression newCall = GeneralUtils.callX((Expression)GeneralUtils.classX(helperType), this.transform(exp.getMethod()), (Expression)newArgs);
            newCall.getObjectExpression().setSourcePosition(traitType);
            newCall.setGenericsTypes(exp.getGenericsTypes());
            newCall.setSpreadSafe(exp.isSpreadSafe());
            newCall.setImplicitThis(false);
            return newCall;
        }
        return super.transform(exp);
    }

    private ClassNode getHelper(ClassNode traitType) {
        if (!traitType.redirect().getInnerClasses().hasNext() && this.getSourceUnit().getAST().getClasses().contains(traitType.redirect())) {
            ClassNode helperType = new InnerClassNode(traitType, Traits.helperClassName(traitType), 5129, ClassHelper.OBJECT_TYPE, ClassNode.EMPTY_ARRAY, null).getPlainNodeReference();
            helperType.setRedirect(null);
            traitType.redirect().setNodeMetaData(UNRESOLVED_HELPER_CLASS, helperType);
            return helperType;
        }
        return Traits.findHelper(traitType);
    }

    private ClassNode getTraitSuperTarget(Expression exp) {
        ClassNode type;
        PropertyExpression pexp;
        Expression objExp;
        if (exp instanceof PropertyExpression && (objExp = (pexp = (PropertyExpression)exp).getObjectExpression()) instanceof ClassExpression && Traits.isTrait(type = objExp.getType()) && "super".equals(pexp.getPropertyAsString())) {
            return type;
        }
        return null;
    }

    private static boolean isSelfType(Parameter parameter, ClassNode traitType) {
        ClassNode paramType = parameter.getType();
        if (paramType.equals(traitType)) {
            return true;
        }
        return ClassHelper.isClassType(paramType) && paramType.getGenericsTypes() != null && paramType.getGenericsTypes()[0].getType().equals(traitType);
    }
}

