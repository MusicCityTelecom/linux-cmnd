/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.EqualsAndHashCode;
import groovy.transform.stc.POJO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.NullCheckASTTransformation;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.util.HashCodeHelper;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class EqualsAndHashCodeASTTransformation
extends AbstractASTTransformation {
    static final Class MY_CLASS = EqualsAndHashCode.class;
    static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode HASHUTIL_TYPE = ClassHelper.make(HashCodeHelper.class);
    private static final ClassNode POJO_TYPE = ClassHelper.make(POJO.class);
    private static final ClassNode OBJECTS_TYPE = ClassHelper.make(Objects.class);
    private static final ClassNode OBJECT_TYPE = GenericsUtils.makeClassSafe(Object.class);
    private static final String HASH_CODE = "hashCode";
    private static final String UNDER_HASH_CODE = "_hashCode";
    private static final String UPDATE_HASH = "updateHash";
    private static final String EQUALS = "equals";
    private static final String UNDER_EQUALS = "_equals";
    private static final String CAN_EQUAL = "canEqual";
    private static final String UNDER_CAN_EQUAL = "_canEqual";

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            boolean useCanEqual;
            ClassNode cNode = (ClassNode)parent;
            if (!this.checkNotInterface(cNode, MY_TYPE_NAME)) {
                return;
            }
            boolean callSuper = this.memberHasValue(anno, "callSuper", true);
            boolean cacheHashCode = this.memberHasValue(anno, "cache", true);
            Object pojoMember = this.getMemberValue(anno, "pojo");
            boolean pojo = pojoMember == null ? !cNode.getAnnotations(POJO_TYPE).isEmpty() : (Boolean)pojoMember;
            boolean bl = useCanEqual = !this.memberHasValue(anno, "useCanEqual", false);
            if (callSuper && cNode.getSuperClass().getName().equals("java.lang.Object")) {
                this.addError("Error during " + MY_TYPE_NAME + " processing: callSuper=true but '" + cNode.getName() + "' has no super class.", anno);
            }
            boolean includeFields = this.memberHasValue(anno, "includeFields", true);
            List<String> excludes = EqualsAndHashCodeASTTransformation.getMemberStringList(anno, "excludes");
            List<String> includes = EqualsAndHashCodeASTTransformation.getMemberStringList(anno, "includes");
            boolean allNames = this.memberHasValue(anno, "allNames", true);
            boolean allProperties = this.memberHasValue(anno, "allProperties", true);
            if (!this.checkIncludeExcludeUndefinedAware(anno, excludes, includes, MY_TYPE_NAME)) {
                return;
            }
            if (!this.checkPropertyList(cNode, includes, "includes", anno, MY_TYPE_NAME, includeFields)) {
                return;
            }
            if (!this.checkPropertyList(cNode, excludes, "excludes", anno, MY_TYPE_NAME, includeFields)) {
                return;
            }
            EqualsAndHashCodeASTTransformation.createHashCode(cNode, cacheHashCode, includeFields, callSuper, excludes, includes, allNames, allProperties, pojo);
            EqualsAndHashCodeASTTransformation.createEquals(cNode, includeFields, callSuper, useCanEqual, excludes, includes, allNames, allProperties, pojo);
        }
    }

    public static void createHashCode(ClassNode cNode, boolean cacheResult, boolean includeFields, boolean callSuper, List<String> excludes, List<String> includes) {
        EqualsAndHashCodeASTTransformation.createHashCode(cNode, cacheResult, includeFields, callSuper, excludes, includes, false);
    }

    public static void createHashCode(ClassNode cNode, boolean cacheResult, boolean includeFields, boolean callSuper, List<String> excludes, List<String> includes, boolean allNames) {
        EqualsAndHashCodeASTTransformation.createHashCode(cNode, cacheResult, includeFields, callSuper, excludes, includes, allNames, false);
    }

    public static void createHashCode(ClassNode cNode, boolean cacheResult, boolean includeFields, boolean callSuper, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties) {
        EqualsAndHashCodeASTTransformation.createHashCode(cNode, cacheResult, includeFields, callSuper, excludes, includes, allNames, allProperties, false);
    }

    public static void createHashCode(ClassNode cNode, boolean cacheResult, boolean includeFields, boolean callSuper, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties, boolean pojo) {
        boolean hasExistingHashCode = GeneralUtils.hasDeclaredMethod(cNode, HASH_CODE, 0);
        if (hasExistingHashCode) {
            if (GeneralUtils.hasDeclaredMethod(cNode, UNDER_HASH_CODE, 0)) {
                return;
            }
            MethodNode hashCode = cNode.getDeclaredMethod(HASH_CODE, Parameter.EMPTY_ARRAY);
            if (AnnotatedNodeUtils.isGenerated(hashCode)) {
                return;
            }
        }
        BlockStatement body = new BlockStatement();
        if (cacheResult) {
            FieldNode hashField = cNode.addField("$hash$code", 4098, ClassHelper.int_TYPE, null);
            VariableExpression hash = GeneralUtils.varX(hashField);
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.isZeroX(hash), EqualsAndHashCodeASTTransformation.calculateHashStatements(cNode, hash, includeFields, callSuper, excludes, includes, allNames, allProperties, pojo)));
            body.addStatement(GeneralUtils.returnS(hash));
        } else {
            body.addStatement(EqualsAndHashCodeASTTransformation.calculateHashStatements(cNode, null, includeFields, callSuper, excludes, includes, allNames, allProperties, pojo));
        }
        ClassNodeUtils.addGeneratedMethod(cNode, hasExistingHashCode ? UNDER_HASH_CODE : HASH_CODE, hasExistingHashCode ? 2 : 1, ClassHelper.int_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private static Statement calculateHashStatements(ClassNode cNode, Expression hash, boolean includeFields, boolean callSuper, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties, boolean pojo) {
        if (pojo) {
            return EqualsAndHashCodeASTTransformation.calculateHashStatementsPOJO(cNode, hash, includeFields, callSuper, excludes, includes, allNames, allProperties);
        }
        return EqualsAndHashCodeASTTransformation.calculateHashStatementsDefault(cNode, hash, includeFields, callSuper, excludes, includes, allNames, allProperties);
    }

    private static Statement calculateHashStatementsDefault(ClassNode cNode, Expression hash, boolean includeFields, boolean callSuper, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties) {
        StaticMethodCallExpression current;
        HashSet<String> names = new HashSet<String>();
        List<PropertyNode> pList = GeneralUtils.getAllProperties(names, cNode, true, false, allProperties, false, false, false);
        ArrayList<FieldNode> fList = new ArrayList<FieldNode>();
        if (includeFields) {
            fList.addAll(GeneralUtils.getInstanceNonPropertyFields(cNode));
        }
        BlockStatement body = new BlockStatement();
        VariableExpression result = GeneralUtils.localVarX("_result");
        body.addStatement(GeneralUtils.declS(result, GeneralUtils.callX(HASHUTIL_TYPE, "initHash")));
        for (PropertyNode pNode : pList) {
            if (EqualsAndHashCodeASTTransformation.shouldSkipUndefinedAware(pNode.getName(), excludes, includes, allNames)) continue;
            Expression getter = GeneralUtils.getterThisX(cNode, pNode);
            current = GeneralUtils.callX(HASHUTIL_TYPE, UPDATE_HASH, (Expression)GeneralUtils.args(result, getter));
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notIdenticalX(getter, GeneralUtils.varX("this")), GeneralUtils.assignS(result, current)));
        }
        for (FieldNode fNode : fList) {
            if (EqualsAndHashCodeASTTransformation.shouldSkipUndefinedAware(fNode.getName(), excludes, includes, allNames)) continue;
            VariableExpression fieldExpr = GeneralUtils.varX(fNode);
            current = GeneralUtils.callX(HASHUTIL_TYPE, UPDATE_HASH, (Expression)GeneralUtils.args(result, fieldExpr));
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notIdenticalX(fieldExpr, GeneralUtils.varX("this")), GeneralUtils.assignS(result, current)));
        }
        if (callSuper) {
            StaticMethodCallExpression current2 = GeneralUtils.callX(HASHUTIL_TYPE, UPDATE_HASH, (Expression)GeneralUtils.args(result, GeneralUtils.callSuperX(HASH_CODE)));
            body.addStatement(GeneralUtils.assignS(result, current2));
        }
        if (hash != null) {
            body.addStatement(GeneralUtils.assignS(hash, result));
        } else {
            body.addStatement(GeneralUtils.returnS(result));
        }
        return body;
    }

    private static Statement calculateHashStatementsPOJO(ClassNode cNode, Expression hash, boolean includeFields, boolean callSuper, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties) {
        HashSet<String> names = new HashSet<String>();
        List<PropertyNode> pList = GeneralUtils.getAllProperties(names, cNode, true, false, allProperties, false, false, false);
        ArrayList<FieldNode> fList = new ArrayList<FieldNode>();
        if (includeFields) {
            fList.addAll(GeneralUtils.getInstanceNonPropertyFields(cNode));
        }
        BlockStatement body = new BlockStatement();
        ArgumentListExpression args = new ArgumentListExpression();
        for (PropertyNode pNode : pList) {
            if (EqualsAndHashCodeASTTransformation.shouldSkipUndefinedAware(pNode.getName(), excludes, includes, allNames)) continue;
            args.addExpression(GeneralUtils.getterThisX(cNode, pNode));
        }
        for (FieldNode fNode : fList) {
            if (EqualsAndHashCodeASTTransformation.shouldSkipUndefinedAware(fNode.getName(), excludes, includes, allNames)) continue;
            args.addExpression(GeneralUtils.varX(fNode));
        }
        if (callSuper) {
            args.addExpression(GeneralUtils.varX("super"));
        }
        StaticMethodCallExpression calcHash = GeneralUtils.callX(OBJECTS_TYPE, "hash", (Expression)args);
        if (hash != null) {
            body.addStatement(GeneralUtils.assignS(hash, calcHash));
        } else {
            body.addStatement(GeneralUtils.returnS(calcHash));
        }
        return body;
    }

    private static void createCanEqual(ClassNode cNode) {
        boolean hasExistingCanEqual = GeneralUtils.hasDeclaredMethod(cNode, CAN_EQUAL, 1);
        if (hasExistingCanEqual && GeneralUtils.hasDeclaredMethod(cNode, UNDER_CAN_EQUAL, 1)) {
            return;
        }
        BlockStatement body = new BlockStatement();
        VariableExpression other = GeneralUtils.varX("other");
        body.addStatement(GeneralUtils.returnS(GeneralUtils.isInstanceOfX(other, GenericsUtils.nonGeneric(cNode))));
        MethodNode canEqual = ClassNodeUtils.addGeneratedMethod(cNode, hasExistingCanEqual ? UNDER_CAN_EQUAL : CAN_EQUAL, hasExistingCanEqual ? 2 : 1, ClassHelper.boolean_TYPE, GeneralUtils.params(GeneralUtils.param(OBJECT_TYPE, other.getName())), ClassNode.EMPTY_ARRAY, body);
        NullCheckASTTransformation.markAsProcessed(canEqual);
    }

    public static void createEquals(ClassNode cNode, boolean includeFields, boolean callSuper, boolean useCanEqual, List<String> excludes, List<String> includes) {
        EqualsAndHashCodeASTTransformation.createEquals(cNode, includeFields, callSuper, useCanEqual, excludes, includes, false);
    }

    public static void createEquals(ClassNode cNode, boolean includeFields, boolean callSuper, boolean useCanEqual, List<String> excludes, List<String> includes, boolean allNames) {
        EqualsAndHashCodeASTTransformation.createEquals(cNode, includeFields, callSuper, useCanEqual, excludes, includes, allNames, false);
    }

    public static void createEquals(ClassNode cNode, boolean includeFields, boolean callSuper, boolean useCanEqual, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties) {
        EqualsAndHashCodeASTTransformation.createEquals(cNode, includeFields, callSuper, useCanEqual, excludes, includes, allNames, allProperties, false);
    }

    public static void createEquals(ClassNode cNode, boolean includeFields, boolean callSuper, boolean useCanEqual, List<String> excludes, List<String> includes, boolean allNames, boolean allProperties, boolean pojo) {
        boolean hasExistingEquals;
        if (useCanEqual) {
            EqualsAndHashCodeASTTransformation.createCanEqual(cNode);
        }
        if (hasExistingEquals = GeneralUtils.hasDeclaredMethod(cNode, EQUALS, 1)) {
            if (GeneralUtils.hasDeclaredMethod(cNode, UNDER_EQUALS, 1)) {
                return;
            }
            MethodNode equals = GeneralUtils.findDeclaredMethod(cNode, EQUALS, 1);
            if (AnnotatedNodeUtils.isGenerated(equals)) {
                return;
            }
        }
        if (hasExistingEquals && GeneralUtils.hasDeclaredMethod(cNode, UNDER_EQUALS, 1)) {
            return;
        }
        BlockStatement body = new BlockStatement();
        VariableExpression other = GeneralUtils.varX("other");
        body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.equalsNullX(other), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true))));
        body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.sameX(GeneralUtils.varX("this"), other), GeneralUtils.returnS(GeneralUtils.constX(Boolean.TRUE, true))));
        if (useCanEqual) {
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(GeneralUtils.isInstanceOfX(other, GenericsUtils.nonGeneric(cNode))), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true))));
        } else {
            BinaryExpression classesEqual = pojo ? GeneralUtils.callX((Expression)GeneralUtils.callThisX("getClass"), EQUALS, (Expression)GeneralUtils.callX(other, "getClass")) : GeneralUtils.hasClassX(other, GenericsUtils.nonGeneric(cNode));
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(classesEqual), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true))));
        }
        VariableExpression otherTyped = GeneralUtils.localVarX("otherTyped", GenericsUtils.nonGeneric(cNode));
        ClassNode originType = otherTyped.getOriginType();
        CastExpression castExpression = new CastExpression(GenericsUtils.nonGeneric(cNode), other);
        castExpression.setStrict(true);
        body.addStatement(GeneralUtils.declS(otherTyped, castExpression));
        if (useCanEqual) {
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(GeneralUtils.callX((Expression)otherTyped, CAN_EQUAL, (Expression)GeneralUtils.varX("this"))), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true))));
        }
        HashSet<String> names = new HashSet<String>();
        List<PropertyNode> pList = GeneralUtils.getAllProperties(names, cNode, true, includeFields, allProperties, false, false, false);
        for (PropertyNode propertyNode : pList) {
            BinaryExpression propsEqual;
            if (EqualsAndHashCodeASTTransformation.shouldSkipUndefinedAware(propertyNode.getName(), excludes, includes, allNames)) continue;
            boolean canBeSelf = StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(propertyNode.getOriginType(), cNode);
            Expression expression = propsEqual = pojo ? GeneralUtils.callX(OBJECTS_TYPE, EQUALS, (Expression)GeneralUtils.args(GeneralUtils.getterThisX(originType, propertyNode), GeneralUtils.getterX(originType, otherTyped, propertyNode))) : GeneralUtils.hasEqualPropertyX(originType, propertyNode, otherTyped);
            if (!canBeSelf) {
                body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(propsEqual), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true))));
                continue;
            }
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(GeneralUtils.hasSamePropertyX(propertyNode, otherTyped)), GeneralUtils.ifElseS(EqualsAndHashCodeASTTransformation.differentSelfRecursivePropertyX(propertyNode, otherTyped), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true)), GeneralUtils.ifS((Expression)GeneralUtils.notX(EqualsAndHashCodeASTTransformation.bothSelfRecursivePropertyX(propertyNode, otherTyped)), GeneralUtils.ifS((Expression)GeneralUtils.notX(propsEqual), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true)))))));
        }
        ArrayList<FieldNode> fList = new ArrayList<FieldNode>();
        if (includeFields) {
            fList.addAll(GeneralUtils.getInstanceNonPropertyFields(cNode));
        }
        for (FieldNode fNode : fList) {
            if (EqualsAndHashCodeASTTransformation.shouldSkipUndefinedAware(fNode.getName(), excludes, includes, allNames)) continue;
            BinaryExpression fieldsEqual = pojo ? GeneralUtils.callX(OBJECTS_TYPE, EQUALS, (Expression)GeneralUtils.args(GeneralUtils.varX(fNode), GeneralUtils.propX((Expression)otherTyped, fNode.getName()))) : GeneralUtils.hasEqualFieldX(fNode, otherTyped);
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(GeneralUtils.hasSameFieldX(fNode, otherTyped)), GeneralUtils.ifElseS(EqualsAndHashCodeASTTransformation.differentSelfRecursiveFieldX(fNode, otherTyped), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true)), GeneralUtils.ifS((Expression)GeneralUtils.notX(EqualsAndHashCodeASTTransformation.bothSelfRecursiveFieldX(fNode, otherTyped)), GeneralUtils.ifS((Expression)GeneralUtils.notX(fieldsEqual), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true)))))));
        }
        if (callSuper) {
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.notX(GeneralUtils.isTrueX(GeneralUtils.callSuperX(EQUALS, other))), GeneralUtils.returnS(GeneralUtils.constX(Boolean.FALSE, true))));
        }
        body.addStatement(GeneralUtils.returnS(GeneralUtils.constX(Boolean.TRUE, true)));
        MethodNode methodNode = ClassNodeUtils.addGeneratedMethod(cNode, hasExistingEquals ? UNDER_EQUALS : EQUALS, hasExistingEquals ? 2 : 1, ClassHelper.boolean_TYPE, GeneralUtils.params(GeneralUtils.param(OBJECT_TYPE, other.getName())), ClassNode.EMPTY_ARRAY, body);
        NullCheckASTTransformation.markAsProcessed(methodNode);
    }

    private static BinaryExpression differentSelfRecursivePropertyX(PropertyNode pNode, Expression other) {
        String getterName = pNode.getGetterNameOrDefault();
        MethodCallExpression selfGetter = GeneralUtils.callThisX(getterName);
        MethodCallExpression otherGetter = GeneralUtils.callX(other, getterName);
        return GeneralUtils.orX(GeneralUtils.andX(GeneralUtils.sameX(selfGetter, GeneralUtils.varX("this")), GeneralUtils.notX(GeneralUtils.sameX(otherGetter, other))), GeneralUtils.andX(GeneralUtils.notX(GeneralUtils.sameX(selfGetter, GeneralUtils.varX("this"))), GeneralUtils.sameX(otherGetter, other)));
    }

    private static BinaryExpression bothSelfRecursivePropertyX(PropertyNode pNode, Expression other) {
        String getterName = pNode.getGetterNameOrDefault();
        MethodCallExpression selfGetter = GeneralUtils.callThisX(getterName);
        MethodCallExpression otherGetter = GeneralUtils.callX(other, getterName);
        return GeneralUtils.andX(GeneralUtils.sameX(selfGetter, GeneralUtils.varX("this")), GeneralUtils.sameX(otherGetter, other));
    }

    private static BinaryExpression differentSelfRecursiveFieldX(FieldNode fNode, Expression other) {
        VariableExpression fieldExpr = GeneralUtils.varX(fNode);
        PropertyExpression otherExpr = GeneralUtils.propX(other, fNode.getName());
        return GeneralUtils.orX(GeneralUtils.andX(GeneralUtils.sameX(fieldExpr, GeneralUtils.varX("this")), GeneralUtils.notX(GeneralUtils.sameX(otherExpr, other))), GeneralUtils.andX(GeneralUtils.notX(GeneralUtils.sameX(fieldExpr, GeneralUtils.varX("this"))), GeneralUtils.sameX(otherExpr, other)));
    }

    private static BinaryExpression bothSelfRecursiveFieldX(FieldNode fNode, Expression other) {
        VariableExpression fieldExpr = GeneralUtils.varX(fNode);
        PropertyExpression otherExpr = GeneralUtils.propX(other, fNode.getName());
        return GeneralUtils.andX(GeneralUtils.sameX(fieldExpr, GeneralUtils.varX("this")), GeneralUtils.sameX(otherExpr, other));
    }
}

