/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.trait;

import java.lang.reflect.Modifier;
import java.util.Collection;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.trait.TraitASTTransformation;
import org.codehaus.groovy.transform.trait.Traits;

class TraitReceiverTransformer
extends ClassCodeExpressionTransformer {
    private final VariableExpression weaved;
    private final SourceUnit unit;
    private final ClassNode traitClass;
    private final ClassNode traitHelperClass;
    private final ClassNode fieldHelper;
    private final Collection<String> knownFields;
    private boolean inClosure;

    public TraitReceiverTransformer(VariableExpression thisObject, SourceUnit unit, ClassNode traitClass, ClassNode traitHelperClass, ClassNode fieldHelper, Collection<String> knownFields) {
        this.weaved = thisObject;
        this.unit = unit;
        this.traitClass = traitClass;
        this.traitHelperClass = traitHelperClass;
        this.fieldHelper = fieldHelper;
        this.knownFields = knownFields;
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return this.unit;
    }

    @Override
    public Expression transform(Expression exp) {
        ClassNode weavedType = this.weaved.getOriginType();
        if (exp instanceof BinaryExpression) {
            return this.transformBinaryExpression((BinaryExpression)exp, weavedType);
        }
        if (exp instanceof StaticMethodCallExpression) {
            StaticMethodCallExpression call = (StaticMethodCallExpression)exp;
            if (call.getOwnerType().equals(this.traitClass)) {
                MethodCallExpression mce = GeneralUtils.callX((Expression)GeneralUtils.varX(this.weaved), call.getMethod(), this.transform(call.getArguments()));
                mce.setSafe(false);
                mce.setSpreadSafe(false);
                mce.setImplicitThis(false);
                mce.setSourcePosition(exp);
                return mce;
            }
        } else if (exp instanceof MethodCallExpression) {
            MethodCallExpression mce = (MethodCallExpression)exp;
            String obj = mce.getObjectExpression().getText();
            if (mce.isImplicitThis() || "this".equals(obj)) {
                return this.transformMethodCallOnThis(mce);
            }
            if ("super".equals(obj)) {
                return this.transformSuperMethodCall(mce);
            }
        } else {
            if (exp instanceof FieldExpression) {
                FieldNode fn = ((FieldExpression)exp).getField();
                return this.transformFieldReference(exp, fn, fn.isStatic());
            }
            if (exp instanceof VariableExpression) {
                VariableExpression vexp = (VariableExpression)exp;
                Variable accessedVariable = vexp.getAccessedVariable();
                if (accessedVariable instanceof FieldNode || accessedVariable instanceof PropertyNode) {
                    if (this.knownFields.contains(vexp.getName())) {
                        boolean isStatic = Modifier.isStatic(accessedVariable.getModifiers());
                        return this.transformFieldReference(exp, accessedVariable instanceof FieldNode ? (FieldNode)accessedVariable : ((PropertyNode)accessedVariable).getField(), isStatic);
                    }
                    PropertyExpression propertyExpression = GeneralUtils.propX((Expression)GeneralUtils.varX(this.weaved), vexp.getName());
                    propertyExpression.getProperty().setSourcePosition(exp);
                    return propertyExpression;
                }
                if (accessedVariable instanceof DynamicVariable && !this.inClosure) {
                    PropertyExpression propertyExpression = GeneralUtils.propX((Expression)GeneralUtils.varX(this.weaved), vexp.getName());
                    propertyExpression.getProperty().setSourcePosition(exp);
                    return propertyExpression;
                }
                if (vexp.isThisExpression()) {
                    VariableExpression variableExpression = GeneralUtils.varX(this.weaved);
                    variableExpression.setSourcePosition(exp);
                    return variableExpression;
                }
                if (vexp.isSuperExpression()) {
                    this.throwSuperError(vexp);
                }
            } else if (exp instanceof PropertyExpression) {
                String propName;
                PropertyExpression pexp = (PropertyExpression)exp;
                String obj = pexp.getObjectExpression().getText();
                if ((pexp.isImplicitThis() || "this".equals(obj)) && this.knownFields.contains(propName = pexp.getPropertyAsString())) {
                    FieldNode fn = new FieldNode(propName, 0, ClassHelper.OBJECT_TYPE, weavedType, null);
                    return this.transformFieldReference(exp, fn, false);
                }
            } else if (exp instanceof ClosureExpression) {
                MethodCallExpression mce = GeneralUtils.callX(exp, "rehydrate", (Expression)GeneralUtils.args(GeneralUtils.varX(this.weaved), GeneralUtils.varX(this.weaved), GeneralUtils.varX(this.weaved)));
                mce.setImplicitThis(false);
                mce.setSourcePosition(exp);
                boolean oldInClosure = this.inClosure;
                this.inClosure = true;
                ((ClosureExpression)exp).getCode().visit(this);
                this.inClosure = oldInClosure;
                exp.putNodeMetaData(TraitASTTransformation.POST_TYPECHECKING_REPLACEMENT, mce);
                return exp;
            }
        }
        return super.transform(exp);
    }

    private Expression transformBinaryExpression(BinaryExpression exp, ClassNode weavedType) {
        Expression leftExpression = exp.getLeftExpression();
        Expression rightExpression = exp.getRightExpression();
        Token operation = exp.getOperation();
        if (operation.getType() == 100) {
            String leftFieldName = null;
            if (leftExpression instanceof VariableExpression && ((VariableExpression)leftExpression).getAccessedVariable() instanceof FieldNode) {
                leftFieldName = ((VariableExpression)leftExpression).getAccessedVariable().getName();
            } else if (leftExpression instanceof FieldExpression) {
                leftFieldName = ((FieldExpression)leftExpression).getFieldName();
            } else if (leftExpression instanceof PropertyExpression && (((PropertyExpression)leftExpression).isImplicitThis() || "this".equals(((PropertyExpression)leftExpression).getObjectExpression().getText()))) {
                leftFieldName = ((PropertyExpression)leftExpression).getPropertyAsString();
            }
            if (leftFieldName != null) {
                boolean isStatic;
                FieldNode staticField = TraitReceiverTransformer.tryGetFieldNode(weavedType, leftFieldName);
                if (this.fieldHelper == null || staticField == null && !this.fieldHelper.hasPossibleMethod(Traits.helperSetterName(new FieldNode(leftFieldName, 0, ClassHelper.OBJECT_TYPE, weavedType, null)), rightExpression)) {
                    return GeneralUtils.binX(GeneralUtils.propX((Expression)GeneralUtils.varX(this.weaved), leftFieldName), operation, this.transform(rightExpression));
                }
                FieldNode fn = weavedType.getDeclaredField(leftFieldName);
                if (fn == null) {
                    fn = new FieldNode(leftFieldName, 0, ClassHelper.OBJECT_TYPE, weavedType, null);
                }
                Expression receiver = this.createFieldHelperReceiver();
                boolean bl = isStatic = staticField != null && staticField.isStatic();
                if (fn.isStatic()) {
                    receiver = GeneralUtils.propX(receiver, "class");
                }
                String method = Traits.helperSetterName(fn);
                MethodCallExpression mce = GeneralUtils.callX(receiver, method, super.transform(rightExpression));
                mce.setImplicitThis(false);
                mce.setSourcePosition(exp);
                TraitReceiverTransformer.markDynamicCall(mce, staticField, isStatic);
                return mce;
            }
        }
        Expression leftTransform = this.transform(leftExpression);
        Expression rightTransform = this.transform(rightExpression);
        BinaryExpression ret = exp instanceof DeclarationExpression ? new DeclarationExpression(leftTransform, operation, rightTransform) : GeneralUtils.binX(leftTransform, operation, rightTransform);
        ret.setSourcePosition(exp);
        ret.copyNodeMetaData(exp);
        return ret;
    }

    private Expression transformFieldReference(Expression exp, FieldNode fn, boolean isStatic) {
        Expression receiver = this.createFieldHelperReceiver();
        if (isStatic) {
            receiver = this.asClass(receiver);
        }
        MethodCallExpression mce = GeneralUtils.callX(receiver, Traits.helperGetterName(fn));
        mce.setImplicitThis(false);
        mce.setSourcePosition(exp);
        TraitReceiverTransformer.markDynamicCall(mce, fn, isStatic);
        return mce;
    }

    private static void markDynamicCall(MethodCallExpression mce, FieldNode fn, boolean isStatic) {
        if (isStatic) {
            mce.putNodeMetaData(TraitASTTransformation.DO_DYNAMIC, fn.getOriginType());
        }
    }

    private static FieldNode tryGetFieldNode(ClassNode weavedType, String fieldName) {
        GenericsType[] genericsTypes;
        FieldNode fn = weavedType.getDeclaredField(fieldName);
        if (fn == null && ClassHelper.isClassType(weavedType) && (genericsTypes = weavedType.getGenericsTypes()) != null && genericsTypes.length == 1) {
            fn = genericsTypes[0].getType().getDeclaredField(fieldName);
        }
        return fn;
    }

    private void throwSuperError(ASTNode node) {
        this.unit.addError(new SyntaxException("Call to super is not allowed in a trait", node.getLineNumber(), node.getColumnNumber()));
    }

    private Expression transformSuperMethodCall(MethodCallExpression call) {
        String method = call.getMethodAsString();
        if (method == null) {
            this.throwSuperError(call);
        }
        Expression arguments = this.transform(call.getArguments());
        ArgumentListExpression superCallArgs = new ArgumentListExpression();
        if (arguments instanceof ArgumentListExpression) {
            ArgumentListExpression list = (ArgumentListExpression)arguments;
            for (Expression expression : list) {
                superCallArgs.addExpression(expression);
            }
        } else {
            superCallArgs.addExpression(arguments);
        }
        MethodCallExpression newCall = GeneralUtils.callX((Expression)this.weaved, Traits.getSuperTraitMethodName(this.traitClass, method), (Expression)superCallArgs);
        newCall.setSourcePosition(call);
        newCall.setSafe(call.isSafe());
        newCall.setSpreadSafe(call.isSpreadSafe());
        newCall.setImplicitThis(false);
        return newCall;
    }

    private Expression transformMethodCallOnThis(MethodCallExpression call) {
        Expression method = call.getMethod();
        Expression arguments = call.getArguments();
        Expression thisExpr = call.getObjectExpression();
        if (method instanceof ConstantExpression) {
            String methodName = call.getMethodAsString();
            for (MethodNode methodNode : this.traitClass.getMethods(methodName)) {
                MethodCallExpression newCall;
                if (!methodName.equals(methodNode.getName()) || !methodNode.isStatic() && !methodNode.isPrivate()) continue;
                if (!this.inClosure && methodNode.isStatic()) {
                    newCall = GeneralUtils.callX((Expression)GeneralUtils.varX(this.weaved), methodName, this.transform(arguments));
                    newCall.setImplicitThis(false);
                    newCall.setSafe(false);
                } else {
                    ArgumentListExpression newArgs = this.createArgumentList(methodNode.isStatic() ? this.asClass(GeneralUtils.varX("this")) : this.weaved, arguments);
                    newCall = GeneralUtils.callX(this.inClosure ? GeneralUtils.classX(this.traitHelperClass) : thisExpr, methodName, (Expression)newArgs);
                    newCall.setImplicitThis(true);
                    newCall.setSafe(call.isSafe());
                }
                newCall.setSpreadSafe(call.isSpreadSafe());
                newCall.setSourcePosition(call);
                return newCall;
            }
        }
        MethodCallExpression newCall = GeneralUtils.callX(this.inClosure ? thisExpr : this.weaved, method, this.transform(arguments));
        newCall.setImplicitThis(this.inClosure ? call.isImplicitThis() : false);
        newCall.setSafe(this.inClosure ? call.isSafe() : false);
        newCall.setSpreadSafe(call.isSpreadSafe());
        newCall.setSourcePosition(call);
        return newCall;
    }

    private ArgumentListExpression createArgumentList(Expression self, Expression arguments) {
        ArgumentListExpression newArgs = new ArgumentListExpression();
        newArgs.addExpression(self);
        if (arguments instanceof TupleExpression) {
            for (Expression argument : (TupleExpression)arguments) {
                newArgs.addExpression(this.transform(argument));
            }
        } else {
            newArgs.addExpression(this.transform(arguments));
        }
        return newArgs;
    }

    private Expression createFieldHelperReceiver() {
        return ClassHelper.isClassType(this.weaved.getOriginType()) ? this.weaved : GeneralUtils.castX(this.fieldHelper, this.weaved);
    }

    private Expression asClass(Expression e) {
        ClassNode rawClass = ClassHelper.CLASS_Type.getPlainNodeReference();
        return GeneralUtils.ternaryX(GeneralUtils.isInstanceOfX(e, rawClass), e, GeneralUtils.callX(e, "getClass"));
    }
}

