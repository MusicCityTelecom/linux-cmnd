/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.ToString;
import groovy.transform.stc.POJO;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.MethodCallUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class ToStringASTTransformation
extends AbstractASTTransformation {
    static final Class<?> MY_CLASS = ToString.class;
    static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode STRINGBUILDER_TYPE = ClassHelper.make(StringBuilder.class);
    private static final ClassNode INVOKER_TYPE = ClassHelper.make(InvokerHelper.class);
    private static final ClassNode POJO_TYPE = ClassHelper.make(POJO.class);
    private static final String TO_STRING = "toString";
    private static final String UNDER_TO_STRING = "_toString";

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            ClassNode cNode = (ClassNode)parent;
            if (!this.checkNotInterface(cNode, MY_TYPE_NAME)) {
                return;
            }
            boolean includeSuper = this.memberHasValue(anno, "includeSuper", true);
            boolean includeSuperProperties = this.memberHasValue(anno, "includeSuperProperties", true);
            boolean includeSuperFields = this.memberHasValue(anno, "includeSuperFields", true);
            boolean cacheToString = this.memberHasValue(anno, "cache", true);
            List<String> excludes = ToStringASTTransformation.getMemberStringList(anno, "excludes");
            List<String> includes = ToStringASTTransformation.getMemberStringList(anno, "includes");
            String leftDelim = ToStringASTTransformation.getMemberStringValue(anno, "leftDelimiter", "(");
            String rightDelim = ToStringASTTransformation.getMemberStringValue(anno, "rightDelimiter", ")");
            String nameValueSep = ToStringASTTransformation.getMemberStringValue(anno, "nameValueSeparator", ":");
            String fieldSep = ToStringASTTransformation.getMemberStringValue(anno, "fieldSeparator", ", ");
            if (includes != null && includes.contains("super")) {
                includeSuper = true;
            }
            if (includeSuper && cNode.getSuperClass().getName().equals("java.lang.Object")) {
                this.addError("Error during " + MY_TYPE_NAME + " processing: includeSuper=true but '" + cNode.getName() + "' has no super class.", anno);
            }
            boolean includeNames = this.memberHasValue(anno, "includeNames", true);
            boolean includeFields = this.memberHasValue(anno, "includeFields", true);
            boolean ignoreNulls = this.memberHasValue(anno, "ignoreNulls", true);
            boolean includePackage = !this.memberHasValue(anno, "includePackage", false);
            boolean allProperties = !this.memberHasValue(anno, "allProperties", false);
            boolean allNames = this.memberHasValue(anno, "allNames", true);
            Object pojoMember = this.getMemberValue(anno, "pojo");
            boolean pojo = pojoMember == null ? !cNode.getAnnotations(POJO_TYPE).isEmpty() : (Boolean)pojoMember;
            if (!this.checkIncludeExcludeUndefinedAware(anno, excludes, includes, MY_TYPE_NAME)) {
                return;
            }
            if (!this.checkPropertyList(cNode, includes != null ? DefaultGroovyMethods.minus(includes, (Object)"super") : null, "includes", anno, MY_TYPE_NAME, includeFields, includeSuperProperties, allProperties)) {
                return;
            }
            if (!this.checkPropertyList(cNode, excludes, "excludes", anno, MY_TYPE_NAME, includeFields, includeSuperProperties, allProperties)) {
                return;
            }
            String[] delims = new String[]{leftDelim, rightDelim, nameValueSep, fieldSep};
            ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, ignoreNulls, includePackage, cacheToString, includeSuperProperties, allProperties, allNames, includeSuperFields, pojo, delims);
        }
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames) {
        ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, false);
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls) {
        ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, ignoreNulls, true);
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls, boolean includePackage) {
        ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, ignoreNulls, includePackage, false);
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls, boolean includePackage, boolean cache) {
        ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, ignoreNulls, includePackage, cache, false);
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls, boolean includePackage, boolean cache, boolean includeSuperProperties) {
        ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, ignoreNulls, includePackage, cache, includeSuperProperties, true);
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls, boolean includePackage, boolean cache, boolean includeSuperProperties, boolean allProperties) {
        ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, ignoreNulls, includePackage, cache, includeSuperProperties, allProperties, false, false);
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls, boolean includePackage, boolean cache, boolean includeSuperProperties, boolean allProperties, boolean allNames, boolean includeSuperFields) {
        ToStringASTTransformation.createToString(cNode, includeSuper, includeFields, excludes, includes, includeNames, ignoreNulls, includePackage, cache, includeSuperProperties, allProperties, allNames, includeSuperFields, false, null);
    }

    public static void createToString(ClassNode cNode, boolean includeSuper, boolean includeFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls, boolean includePackage, boolean cache, boolean includeSuperProperties, boolean allProperties, boolean allNames, boolean includeSuperFields, boolean pojo, String[] delims) {
        Expression tempToString;
        boolean hasExistingToString;
        if (delims == null || delims.length != 4) {
            delims = new String[]{"(", ")", ":", ", "};
        }
        if (hasExistingToString = GeneralUtils.hasDeclaredMethod(cNode, TO_STRING, 0)) {
            if (GeneralUtils.hasDeclaredMethod(cNode, UNDER_TO_STRING, 0)) {
                return;
            }
            MethodNode toString = cNode.getDeclaredMethod(TO_STRING, Parameter.EMPTY_ARRAY);
            if (AnnotatedNodeUtils.isGenerated(toString)) {
                return;
            }
        }
        BlockStatement body = new BlockStatement();
        if (cache) {
            FieldNode cacheField = cNode.addField("$to$string", 4098, ClassHelper.STRING_TYPE, null);
            VariableExpression savedToString = GeneralUtils.varX(cacheField);
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.equalsNullX(savedToString), GeneralUtils.assignS(savedToString, ToStringASTTransformation.calculateToStringStatements(cNode, includeSuper, includeFields, includeSuperFields, excludes, includes, includeNames, ignoreNulls, includePackage, includeSuperProperties, allProperties, body, allNames, pojo, delims))));
            tempToString = savedToString;
        } else {
            tempToString = ToStringASTTransformation.calculateToStringStatements(cNode, includeSuper, includeFields, includeSuperFields, excludes, includes, includeNames, ignoreNulls, includePackage, includeSuperProperties, allProperties, body, allNames, pojo, delims);
        }
        body.addStatement(GeneralUtils.returnS(tempToString));
        ClassNodeUtils.addGeneratedMethod(cNode, hasExistingToString ? UNDER_TO_STRING : TO_STRING, hasExistingToString ? 2 : 1, ClassHelper.STRING_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private static Expression calculateToStringStatements(ClassNode cNode, boolean includeSuper, boolean includeFields, boolean includeSuperFields, List<String> excludes, List<String> includes, boolean includeNames, boolean ignoreNulls, boolean includePackage, boolean includeSuperProperties, boolean allProperties, BlockStatement body, boolean allNames, boolean pojo, String[] delims) {
        VariableExpression result = GeneralUtils.localVarX("_result");
        body.addStatement(GeneralUtils.declS(result, GeneralUtils.ctorX(STRINGBUILDER_TYPE)));
        ArrayList<ToStringElement> elements = new ArrayList<ToStringElement>();
        VariableExpression first = GeneralUtils.localVarX("$toStringFirst");
        body.addStatement(GeneralUtils.declS(first, GeneralUtils.constX(Boolean.TRUE)));
        String className = includePackage ? cNode.getName() : cNode.getNameWithoutPackage();
        body.addStatement(MethodCallUtils.appendS(result, GeneralUtils.constX(className + delims[0])));
        HashSet<String> names = new HashSet<String>();
        List<Object> superList = includeSuperProperties || includeSuperFields ? GeneralUtils.getAllProperties(names, cNode, cNode.getSuperClass(), includeSuperProperties, includeSuperFields, allProperties, false, true, true, true, allNames, false) : new ArrayList();
        List<PropertyNode> list = GeneralUtils.getAllProperties(names, cNode, cNode, true, includeFields, allProperties, false, false, true, false, allNames, false);
        list.addAll(superList);
        for (PropertyNode pNode : list) {
            String name = pNode.getName();
            if (ToStringASTTransformation.shouldSkipUndefinedAware(name, excludes, includes, allNames)) continue;
            FieldNode fNode = pNode.getField();
            if (!cNode.hasProperty(name) && fNode.getDeclaringClass() != null) {
                elements.add(new ToStringElement(GeneralUtils.varX(fNode), name, ToStringASTTransformation.canBeSelf(cNode, fNode.getType())));
                continue;
            }
            Expression getter = GeneralUtils.getterThisX(cNode, pNode);
            elements.add(new ToStringElement(getter, name, ToStringASTTransformation.canBeSelf(cNode, pNode.getType())));
        }
        if (includeSuper) {
            elements.add(new ToStringElement(GeneralUtils.callSuperX(TO_STRING), "super", false));
        }
        if (includes != null) {
            Comparator<ToStringElement> includeComparator = Comparator.comparingInt(tse -> includes.indexOf(tse.name));
            elements.sort(includeComparator);
        }
        for (ToStringElement el : elements) {
            ToStringASTTransformation.appendValue(body, result, first, el.value, el.name, includeNames, ignoreNulls, el.canBeSelf, pojo, delims);
        }
        body.addStatement(MethodCallUtils.appendS(result, GeneralUtils.constX(delims[1])));
        return MethodCallUtils.toStringX(result);
    }

    private static void appendValue(BlockStatement body, Expression result, VariableExpression first, Expression value, String name, boolean includeNames, boolean ignoreNulls, boolean canBeSelf, boolean pojo, String[] delims) {
        Expression toString;
        BlockStatement thenBlock = new BlockStatement();
        BlockStatement appendValue = ignoreNulls ? GeneralUtils.ifS((Expression)GeneralUtils.notNullX(value), thenBlock) : thenBlock;
        ToStringASTTransformation.appendCommaIfNotFirst(thenBlock, result, first, delims);
        ToStringASTTransformation.appendPrefix(thenBlock, result, name, includeNames, delims);
        Expression expression = toString = pojo ? MethodCallUtils.toStringX(value) : GeneralUtils.callX(INVOKER_TYPE, TO_STRING, value);
        if (canBeSelf) {
            thenBlock.addStatement(GeneralUtils.ifElseS(GeneralUtils.sameX(value, GeneralUtils.varX("this")), MethodCallUtils.appendS(result, GeneralUtils.constX("(this)")), MethodCallUtils.appendS(result, toString)));
        } else {
            thenBlock.addStatement(MethodCallUtils.appendS(result, toString));
        }
        body.addStatement(appendValue);
    }

    private static boolean canBeSelf(ClassNode cNode, ClassNode valueType) {
        return StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(valueType, cNode);
    }

    private static void appendCommaIfNotFirst(BlockStatement body, Expression result, VariableExpression first, String[] delims) {
        body.addStatement(GeneralUtils.ifElseS(first, GeneralUtils.assignS(first, ConstantExpression.FALSE), MethodCallUtils.appendS(result, GeneralUtils.constX(delims[3]))));
    }

    private static void appendPrefix(BlockStatement body, Expression result, String name, boolean includeNames, String[] delims) {
        if (includeNames) {
            body.addStatement(ToStringASTTransformation.toStringPropertyName(result, name, delims));
        }
    }

    private static Statement toStringPropertyName(Expression result, String fName, String[] delims) {
        BlockStatement body = new BlockStatement();
        body.addStatement(MethodCallUtils.appendS(result, GeneralUtils.constX(fName + delims[2])));
        return body;
    }

    private static class ToStringElement {
        Expression value;
        String name;
        boolean canBeSelf;

        ToStringElement(Expression value, String name, boolean canBeSelf) {
            this.value = value;
            this.name = name;
            this.canBeSelf = canBeSelf;
        }
    }
}

