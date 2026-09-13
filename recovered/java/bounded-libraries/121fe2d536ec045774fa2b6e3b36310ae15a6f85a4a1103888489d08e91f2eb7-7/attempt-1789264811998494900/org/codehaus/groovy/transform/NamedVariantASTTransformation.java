/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.NamedDelegate;
import groovy.transform.NamedParam;
import groovy.transform.NamedVariant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.VisibilityUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.ErrorCollecting;
import org.codehaus.groovy.transform.GroovyASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class NamedVariantASTTransformation
extends AbstractASTTransformation {
    private static final ClassNode NAMED_VARIANT_TYPE = ClassHelper.make(NamedVariant.class);
    private static final String NAMED_VARIANT = "@" + NAMED_VARIANT_TYPE.getNameWithoutPackage();
    private static final ClassNode NAMED_PARAM_TYPE = ClassHelper.makeWithoutCaching(NamedParam.class, false);
    private static final ClassNode NAMED_DELEGATE_TYPE = ClassHelper.makeWithoutCaching(NamedDelegate.class, false);
    private static final ClassNode ILLEGAL_ARGUMENT = ClassHelper.makeWithoutCaching(IllegalArgumentException.class);

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        MethodNode mNode = (MethodNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!NAMED_VARIANT_TYPE.equals(anno.getClassNode())) {
            return;
        }
        Parameter[] fromParams = mNode.getParameters();
        if (fromParams.length == 0) {
            this.addError("Error during " + NAMED_VARIANT + " processing. No-args method not supported.", mNode);
            return;
        }
        boolean autoDelegate = this.memberHasValue(anno, "autoDelegate", true);
        boolean coerce = this.memberHasValue(anno, "coerce", true);
        Parameter mapParam = GeneralUtils.param(GenericsUtils.nonGeneric(ClassHelper.MAP_TYPE), "namedArgs");
        ArrayList<Parameter> genParams = new ArrayList<Parameter>();
        genParams.add(mapParam);
        ClassNode cNode = mNode.getDeclaringClass();
        BlockStatement inner = new BlockStatement();
        ArgumentListExpression args = new ArgumentListExpression();
        ArrayList<String> propNames = new ArrayList<String>();
        boolean annoFound = false;
        for (Parameter fromParam : fromParams) {
            if (!AnnotatedNodeUtils.hasAnnotation(fromParam, NAMED_PARAM_TYPE) && !AnnotatedNodeUtils.hasAnnotation(fromParam, NAMED_DELEGATE_TYPE)) continue;
            annoFound = true;
            break;
        }
        if (!annoFound && autoDelegate) {
            this.processDelegateParam(mNode, mapParam, args, propNames, fromParams[0], coerce);
        } else {
            HashMap<Parameter, Expression> seen = new HashMap<Parameter, Expression>();
            for (Parameter fromParam : fromParams) {
                if (!annoFound) {
                    if (NamedVariantASTTransformation.processImplicitNamedParam(this, mNode, mapParam, inner, args, propNames, fromParam, coerce, seen)) continue;
                    return;
                }
                if (AnnotatedNodeUtils.hasAnnotation(fromParam, NAMED_PARAM_TYPE)) {
                    if (this.processExplicitNamedParam(mNode, mapParam, inner, args, propNames, fromParam, coerce, seen)) continue;
                    return;
                }
                if (AnnotatedNodeUtils.hasAnnotation(fromParam, NAMED_DELEGATE_TYPE)) {
                    if (this.processDelegateParam(mNode, mapParam, args, propNames, fromParam, coerce)) continue;
                    return;
                }
                VariableExpression arg = GeneralUtils.varX(fromParam);
                VariableExpression argOrDefault = fromParam.hasInitialExpression() ? GeneralUtils.elvisX(arg, fromParam.getDefaultValue()) : arg;
                args.addExpression(NamedVariantASTTransformation.asType(argOrDefault, fromParam.getType(), coerce));
                if (NamedVariantASTTransformation.hasDuplicates(this, mNode, propNames, fromParam.getName())) {
                    return;
                }
                genParams.add(fromParam);
            }
        }
        NamedVariantASTTransformation.createMapVariant(this, mNode, anno, mapParam, genParams, cNode, inner, args, propNames);
    }

    static boolean processImplicitNamedParam(ErrorCollecting xform, MethodNode mNode, Parameter mapParam, BlockStatement inner, ArgumentListExpression args, List<String> propNames, Parameter fromParam, boolean coerce) {
        return NamedVariantASTTransformation.processImplicitNamedParam(xform, mNode, mapParam, inner, args, propNames, fromParam, coerce, null);
    }

    static boolean processImplicitNamedParam(ErrorCollecting xform, MethodNode mNode, Parameter mapParam, BlockStatement inner, ArgumentListExpression args, List<String> propNames, Parameter fromParam, boolean coerce, Map<Parameter, Expression> seen) {
        boolean required;
        String name = fromParam.getName();
        ClassNode type = fromParam.getType();
        boolean bl = required = !fromParam.hasInitialExpression();
        if (NamedVariantASTTransformation.hasDuplicates(xform, mNode, propNames, name)) {
            return false;
        }
        AnnotationNode namedParam = new AnnotationNode(NAMED_PARAM_TYPE);
        namedParam.addMember("value", GeneralUtils.constX(name));
        namedParam.addMember("type", GeneralUtils.classX(type));
        namedParam.addMember("required", GeneralUtils.constX(required, true));
        mapParam.addAnnotation(namedParam);
        if (required) {
            inner.addStatement(new AssertStatement(GeneralUtils.boolX(NamedVariantASTTransformation.containsKey(mapParam, name)), GeneralUtils.plusX(GeneralUtils.constX("Missing required named argument '" + name + "'. Keys found: "), GeneralUtils.callX(GeneralUtils.varX(mapParam), "keySet"))));
        }
        Expression defValue = NamedVariantASTTransformation.earlierParamIfSeen(seen, fromParam.getInitialExpression());
        Expression initExpr = NamedVariantASTTransformation.namedParamValue(mapParam, name, type, coerce, defValue);
        if (seen != null) {
            seen.put(fromParam, initExpr);
        }
        args.addExpression(initExpr);
        return true;
    }

    private static Expression earlierParamIfSeen(Map<Parameter, Expression> seen, Expression defValue) {
        if (seen == null) {
            return defValue;
        }
        if (defValue instanceof CastExpression) {
            defValue = ((CastExpression)defValue).getExpression();
        }
        return defValue instanceof VariableExpression ? seen.getOrDefault(((VariableExpression)defValue).getAccessedVariable(), defValue) : defValue;
    }

    private boolean processExplicitNamedParam(MethodNode mNode, Parameter mapParam, BlockStatement inner, ArgumentListExpression args, List<String> propNames, Parameter fromParam, boolean coerce, Map<Parameter, Expression> seen) {
        boolean required;
        AnnotationNode namedParam = fromParam.getAnnotations(NAMED_PARAM_TYPE).get(0);
        String name = NamedVariantASTTransformation.getMemberStringValue(namedParam, "value");
        if (name == null) {
            name = fromParam.getName();
            namedParam.addMember("value", GeneralUtils.constX(name));
        }
        if (NamedVariantASTTransformation.hasDuplicates(this, mNode, propNames, name)) {
            return false;
        }
        ClassNode type = this.getMemberClassValue(namedParam, "type");
        if (type == null) {
            type = fromParam.getType();
            namedParam.addMember("type", GeneralUtils.classX(type));
        }
        if (required = this.memberHasValue(namedParam, "required", true)) {
            if (fromParam.hasInitialExpression()) {
                this.addError("Error during " + NAMED_VARIANT + " processing. A required parameter can't have an initial value.", fromParam);
                return false;
            }
            inner.addStatement(new AssertStatement(GeneralUtils.boolX(NamedVariantASTTransformation.containsKey(mapParam, name)), GeneralUtils.plusX(GeneralUtils.constX("Missing required named argument '" + name + "'. Keys found: "), GeneralUtils.callX(GeneralUtils.varX(mapParam), "keySet"))));
        }
        Expression defValue = NamedVariantASTTransformation.earlierParamIfSeen(seen, fromParam.getInitialExpression());
        Expression initExpr = NamedVariantASTTransformation.namedParamValue(mapParam, name, type, coerce, defValue);
        seen.put(fromParam, initExpr);
        args.addExpression(initExpr);
        mapParam.addAnnotation(namedParam);
        fromParam.getAnnotations().remove(namedParam);
        return true;
    }

    private boolean processDelegateParam(MethodNode mNode, Parameter mapParam, ArgumentListExpression args, List<String> propNames, Parameter fromParam, boolean coerce) {
        if (ClassNodeUtils.isInnerClass(fromParam.getType()) && mNode.isStatic()) {
            this.addError("Error during " + NAMED_VARIANT + " processing. Delegate type '" + fromParam.getType().getNameWithoutPackage() + "' is an inner class which is not supported.", mNode);
            return false;
        }
        HashSet<String> names = new HashSet<String>();
        List<PropertyNode> props = GeneralUtils.getAllProperties(names, fromParam.getType(), true, false, false, true, false, true);
        for (String name2 : names) {
            if (!NamedVariantASTTransformation.hasDuplicates(this, mNode, propNames, name2)) continue;
            return false;
        }
        for (PropertyNode prop : props) {
            AnnotationNode namedParam = new AnnotationNode(NAMED_PARAM_TYPE);
            namedParam.addMember("value", GeneralUtils.constX(prop.getName()));
            namedParam.addMember("type", GeneralUtils.classX(prop.getType()));
            mapParam.addAnnotation(namedParam);
        }
        Expression[] subMapArgs = (Expression[])names.stream().map(name -> GeneralUtils.constX(name)).toArray(Expression[]::new);
        MethodCallExpression delegateMap = GeneralUtils.callX((Expression)GeneralUtils.varX(mapParam), "subMap", (Expression)GeneralUtils.args(subMapArgs));
        args.addExpression(GeneralUtils.castX(fromParam.getType(), delegateMap));
        return true;
    }

    private static boolean hasDuplicates(ErrorCollecting xform, MethodNode mNode, List<String> propNames, String next) {
        if (propNames.contains(next)) {
            xform.addError("Error during " + NAMED_VARIANT + " processing. Duplicate property '" + next + "' found.", mNode);
            return true;
        }
        propNames.add(next);
        return false;
    }

    static void createMapVariant(ErrorCollecting xform, MethodNode mNode, AnnotationNode anno, Parameter mapParam, List<Parameter> genParams, ClassNode cNode, BlockStatement inner, ArgumentListExpression args, List<String> propNames) {
        Parameter namedArgKey = GeneralUtils.param(ClassHelper.STRING_TYPE, "namedArgKey");
        if (!(mNode instanceof ConstructorNode)) {
            inner.getStatements().add(0, GeneralUtils.ifS((Expression)GeneralUtils.isNullX(GeneralUtils.varX(mapParam)), GeneralUtils.throwS(GeneralUtils.ctorX(ILLEGAL_ARGUMENT, GeneralUtils.args(GeneralUtils.constX("Named parameter map cannot be null"))))));
        }
        inner.addStatement(new ForStatement(namedArgKey, GeneralUtils.callX(GeneralUtils.varX(mapParam), "keySet"), new AssertStatement(GeneralUtils.boolX(GeneralUtils.callX((Expression)GeneralUtils.list2args(propNames), "contains", (Expression)GeneralUtils.varX(namedArgKey))), GeneralUtils.plusX(GeneralUtils.constX("Unrecognized namedArgKey: "), GeneralUtils.varX(namedArgKey)))));
        Parameter[] genParamsArray = genParams.toArray(Parameter.EMPTY_ARRAY);
        if (cNode.hasMethod(mNode.getName(), genParamsArray)) {
            xform.addError("Error during " + NAMED_VARIANT + " processing. Class " + cNode.getNameWithoutPackage() + " already has a named-arg " + (mNode instanceof ConstructorNode ? "constructor" : "method") + " of type " + genParams, mNode);
            return;
        }
        BlockStatement body = new BlockStatement();
        int modifiers = VisibilityUtils.getVisibility(anno, mNode, mNode.getClass(), mNode.getModifiers());
        if (mNode instanceof ConstructorNode) {
            body.addStatement(GeneralUtils.stmt(GeneralUtils.ctorX(ClassNode.THIS, args)));
            body.addStatement(inner);
            ClassNodeUtils.addGeneratedConstructor(cNode, modifiers, genParamsArray, mNode.getExceptions(), body);
        } else {
            body.addStatement(inner);
            body.addStatement(GeneralUtils.stmt(GeneralUtils.callThisX(mNode.getName(), args)));
            ClassNodeUtils.addGeneratedMethod(cNode, mNode.getName(), modifiers, mNode.getReturnType(), genParamsArray, mNode.getExceptions(), body);
        }
    }

    private static Expression namedParamValue(Parameter mapParam, String name, ClassNode type, boolean coerce, Expression defaultValue) {
        Expression value = GeneralUtils.propX((Expression)GeneralUtils.varX(mapParam), name);
        if (defaultValue == null && ClassHelper.isPrimitiveType(type)) {
            defaultValue = GeneralUtils.defaultValueX(type);
        }
        if (defaultValue != null) {
            value = GeneralUtils.ternaryX(NamedVariantASTTransformation.containsKey(mapParam, name), value, defaultValue);
        }
        return NamedVariantASTTransformation.asType(value, type, coerce);
    }

    private static Expression containsKey(Parameter mapParam, String name) {
        MethodCallExpression call = GeneralUtils.callX((Expression)GeneralUtils.varX(mapParam), "containsKey", (Expression)GeneralUtils.constX(name));
        call.setImplicitThis(false);
        call.setMethodTarget(ClassHelper.MAP_TYPE.getMethods("containsKey").get(0));
        return call;
    }

    private static Expression asType(Expression value, ClassNode type, boolean coerce) {
        return coerce ? GeneralUtils.asX(type, value) : value;
    }
}

