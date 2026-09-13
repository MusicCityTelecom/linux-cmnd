/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.Undefined;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.tools.BeanUtils;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.StringGroovyMethods;
import org.codehaus.groovy.transform.ASTTransformation;
import org.codehaus.groovy.transform.ErrorCollecting;

public abstract class AbstractASTTransformation
implements ASTTransformation,
ErrorCollecting {
    public static final ClassNode RETENTION_CLASSNODE = ClassHelper.makeWithoutCaching(Retention.class);
    protected SourceUnit sourceUnit;

    protected List<AnnotationNode> copyAnnotatedNodeAnnotations(AnnotatedNode annotatedNode, String myTypeName) {
        return this.copyAnnotatedNodeAnnotations(annotatedNode, myTypeName, true);
    }

    protected List<AnnotationNode> copyAnnotatedNodeAnnotations(AnnotatedNode annotatedNode, String myTypeName, boolean includeGenerated) {
        ArrayList<AnnotationNode> copiedAnnotations = new ArrayList<AnnotationNode>();
        ArrayList<AnnotationNode> notCopied = new ArrayList<AnnotationNode>();
        GeneralUtils.copyAnnotatedNodeAnnotations(annotatedNode, copiedAnnotations, notCopied, includeGenerated);
        for (AnnotationNode annotation : notCopied) {
            this.addError(myTypeName + " does not support keeping Closure annotation members.", annotation);
        }
        return copiedAnnotations;
    }

    public String getAnnotationName() {
        return null;
    }

    protected void init(ASTNode[] nodes, SourceUnit sourceUnit) {
        if (nodes == null || nodes.length != 2 || !(nodes[0] instanceof AnnotationNode) || !(nodes[1] instanceof AnnotatedNode)) {
            throw new GroovyBugError("Internal error: expecting [AnnotationNode, AnnotatedNode] but got: " + (nodes == null ? null : Arrays.asList(nodes)));
        }
        this.sourceUnit = sourceUnit;
    }

    public boolean memberHasValue(AnnotationNode node, String name, Object value) {
        Expression member = node.getMember(name);
        return member instanceof ConstantExpression && ((ConstantExpression)member).getValue().equals(value);
    }

    public Object getMemberValue(AnnotationNode node, String name) {
        Expression member = node.getMember(name);
        if (member instanceof ConstantExpression) {
            return ((ConstantExpression)member).getValue();
        }
        return null;
    }

    public static String getMemberStringValue(AnnotationNode node, String name, String defaultValue) {
        Expression member = node.getMember(name);
        if (member instanceof ConstantExpression) {
            Object result = ((ConstantExpression)member).getValue();
            if (result instanceof String && Undefined.isUndefined((String)result)) {
                result = null;
            }
            if (result != null) {
                return result.toString();
            }
        }
        return defaultValue;
    }

    public static String getMemberStringValue(AnnotationNode node, String name) {
        return AbstractASTTransformation.getMemberStringValue(node, name, null);
    }

    public int getMemberIntValue(AnnotationNode node, String name) {
        Object value = this.getMemberValue(node, name);
        if (value instanceof Integer) {
            return (Integer)value;
        }
        return 0;
    }

    public ClassNode getMemberClassValue(AnnotationNode node, String name) {
        return this.getMemberClassValue(node, name, null);
    }

    public ClassNode getMemberClassValue(AnnotationNode node, String name, ClassNode defaultValue) {
        Expression member = node.getMember(name);
        if (member != null) {
            if (member instanceof ClassExpression) {
                if (!Undefined.isUndefined(member.getType())) {
                    return member.getType();
                }
            } else {
                if (member instanceof VariableExpression) {
                    this.addError("Expecting to find a class value for '" + name + "' but found variable: " + member.getText() + ". Missing import?", node);
                    return null;
                }
                if (member instanceof ConstantExpression) {
                    this.addError("Expecting to find a class value for '" + name + "' but found constant: " + member.getText() + "!", node);
                    return null;
                }
            }
        }
        return defaultValue;
    }

    public static List<String> getMemberStringList(AnnotationNode anno, String name) {
        Expression expr = anno.getMember(name);
        if (expr == null) {
            return null;
        }
        if (expr instanceof ListExpression) {
            ListExpression listExpression = (ListExpression)expr;
            if (AbstractASTTransformation.isUndefinedMarkerList(listExpression)) {
                return null;
            }
            return AbstractASTTransformation.getValueStringList(listExpression);
        }
        return AbstractASTTransformation.tokenize(AbstractASTTransformation.getMemberStringValue(anno, name));
    }

    private static boolean isUndefinedMarkerList(ListExpression listExpression) {
        Object value;
        if (listExpression.getExpressions().size() != 1) {
            return false;
        }
        Expression itemExpr = listExpression.getExpression(0);
        if (itemExpr == null) {
            return false;
        }
        return itemExpr instanceof ConstantExpression ? (value = ((ConstantExpression)itemExpr).getValue()) instanceof String && Undefined.isUndefined((String)value) : itemExpr instanceof ClassExpression && Undefined.isUndefined(itemExpr.getType());
    }

    private static List<String> getValueStringList(ListExpression listExpression) {
        ArrayList<String> list = new ArrayList<String>();
        for (Expression itemExpr : listExpression.getExpressions()) {
            Object value;
            if (!(itemExpr instanceof ConstantExpression) || (value = ((ConstantExpression)itemExpr).getValue()) == null) continue;
            list.add(value.toString());
        }
        return list;
    }

    public List<ClassNode> getMemberClassList(AnnotationNode anno, String name) {
        List<ClassNode> list = new ArrayList<ClassNode>();
        Expression expr = anno.getMember(name);
        if (expr == null) {
            return null;
        }
        if (expr instanceof ListExpression) {
            ListExpression listExpression = (ListExpression)expr;
            if (AbstractASTTransformation.isUndefinedMarkerList(listExpression)) {
                return null;
            }
            list = this.getTypeList(anno, name, listExpression);
        } else if (expr instanceof ClassExpression) {
            ClassNode cn = expr.getType();
            if (Undefined.isUndefined(cn)) {
                return null;
            }
            if (cn != null) {
                list.add(cn);
            }
        } else if (expr instanceof VariableExpression) {
            this.addError("Expecting to find a class value for '" + name + "' but found variable: " + expr.getText() + ". Missing import or unknown class?", anno);
        } else if (expr instanceof ConstantExpression) {
            this.addError("Expecting to find a class value for '" + name + "' but found constant: " + expr.getText() + "!", anno);
        }
        return list;
    }

    private List<ClassNode> getTypeList(AnnotationNode anno, String name, ListExpression listExpression) {
        ArrayList<ClassNode> list = new ArrayList<ClassNode>();
        for (Expression itemExpr : listExpression.getExpressions()) {
            if (itemExpr instanceof ClassExpression) {
                ClassNode cn = itemExpr.getType();
                if (cn == null) continue;
                list.add(cn);
                continue;
            }
            if (itemExpr instanceof VariableExpression) {
                this.addError("Expecting a list of class values for '" + name + "' but found variable: " + itemExpr.getText() + ". Missing import or unknown class?", anno);
                continue;
            }
            if (!(itemExpr instanceof ConstantExpression)) continue;
            this.addError("Expecting a list of class values for '" + name + "' but found constant: " + itemExpr.getText() + "!", anno);
        }
        return list;
    }

    @Override
    public void addError(String msg, ASTNode node) {
        this.sourceUnit.getErrorCollector().addErrorAndContinue(msg + '\n', node, this.sourceUnit);
    }

    protected boolean checkNotInterface(ClassNode cNode, String annotationName) {
        if (cNode.isInterface()) {
            this.addError("Error processing interface '" + cNode.getName() + "'. " + annotationName + " not allowed for interfaces.", cNode);
            return false;
        }
        return true;
    }

    public boolean hasAnnotation(ClassNode node, ClassNode annotation) {
        return AnnotatedNodeUtils.hasAnnotation(node, annotation);
    }

    public static List<String> tokenize(String rawExcludes) {
        return rawExcludes == null ? new ArrayList() : StringGroovyMethods.tokenize((CharSequence)rawExcludes, ", ");
    }

    public static boolean deemedInternalName(String name) {
        return name.contains("$");
    }

    public static boolean shouldSkipUndefinedAware(String name, List<String> excludes, List<String> includes) {
        return AbstractASTTransformation.shouldSkipUndefinedAware(name, excludes, includes, false);
    }

    public static boolean shouldSkipUndefinedAware(String name, List<String> excludes, List<String> includes, boolean allNames) {
        return excludes != null && excludes.contains(name) || !allNames && AbstractASTTransformation.deemedInternalName(name) || includes != null && !includes.contains(name);
    }

    public static boolean shouldSkip(String name, List<String> excludes, List<String> includes) {
        return AbstractASTTransformation.shouldSkip(name, excludes, includes, false);
    }

    public static boolean shouldSkip(String name, List<String> excludes, List<String> includes, boolean allNames) {
        return excludes != null && excludes.contains(name) || !allNames && AbstractASTTransformation.deemedInternalName(name) || includes != null && !includes.isEmpty() && !includes.contains(name);
    }

    public static boolean shouldSkipOnDescriptorUndefinedAware(boolean checkReturn, Map genericsSpec, MethodNode mNode, List<ClassNode> excludeTypes, List<ClassNode> includeTypes) {
        String md;
        MethodNode correctedMethodNode;
        ClassNode next;
        Map<String, ClassNode> updatedGenericsSpec;
        LinkedList<ClassNode> remaining;
        String descriptor = mNode.getTypeDescriptor();
        String descriptorNoReturn = MethodNodeUtils.methodDescriptorWithoutReturnType(mNode);
        if (excludeTypes != null) {
            for (ClassNode cn : excludeTypes) {
                remaining = new LinkedList<ClassNode>();
                remaining.add(cn);
                updatedGenericsSpec = new HashMap<String, ClassNode>(genericsSpec);
                while (!remaining.isEmpty()) {
                    next = (ClassNode)remaining.remove(0);
                    if (ClassHelper.isObjectType(next)) continue;
                    updatedGenericsSpec = GenericsUtils.createGenericsSpec(next, updatedGenericsSpec);
                    for (MethodNode mn : next.getMethods()) {
                        correctedMethodNode = GenericsUtils.correctToGenericsSpec(updatedGenericsSpec, mn);
                        if (!(checkReturn ? (md = correctedMethodNode.getTypeDescriptor()).equals(descriptor) : (md = MethodNodeUtils.methodDescriptorWithoutReturnType(correctedMethodNode)).equals(descriptorNoReturn))) continue;
                        return true;
                    }
                    remaining.addAll(Arrays.asList(next.getInterfaces()));
                }
            }
        }
        if (includeTypes == null) {
            return false;
        }
        for (ClassNode cn : includeTypes) {
            remaining = new LinkedList();
            remaining.add(cn);
            updatedGenericsSpec = new HashMap(genericsSpec);
            while (!remaining.isEmpty()) {
                next = (ClassNode)remaining.remove(0);
                if (ClassHelper.isObjectType(next)) continue;
                updatedGenericsSpec = GenericsUtils.createGenericsSpec(next, updatedGenericsSpec);
                for (MethodNode mn : next.getMethods()) {
                    correctedMethodNode = GenericsUtils.correctToGenericsSpec(updatedGenericsSpec, mn);
                    if (!(checkReturn ? (md = correctedMethodNode.getTypeDescriptor()).equals(descriptor) : (md = MethodNodeUtils.methodDescriptorWithoutReturnType(correctedMethodNode)).equals(descriptorNoReturn))) continue;
                    return false;
                }
                remaining.addAll(Arrays.asList(next.getInterfaces()));
            }
        }
        return true;
    }

    protected boolean checkIncludeExcludeUndefinedAware(AnnotationNode node, List<String> excludes, List<String> includes, String typeName) {
        if (includes != null && excludes != null && !excludes.isEmpty()) {
            this.addError("Error during " + typeName + " processing: Only one of 'includes' and 'excludes' should be supplied not both.", node);
            return false;
        }
        return true;
    }

    protected void checkIncludeExcludeUndefinedAware(AnnotationNode node, List<String> excludes, List<String> includes, List<ClassNode> excludeTypes, List<ClassNode> includeTypes, String typeName) {
        int found = 0;
        if (includes != null) {
            ++found;
        }
        if (excludes != null && !excludes.isEmpty()) {
            ++found;
        }
        if (includeTypes != null) {
            ++found;
        }
        if (excludeTypes != null && !excludeTypes.isEmpty()) {
            ++found;
        }
        if (found > 1) {
            this.addError("Error during " + typeName + " processing: Only one of 'includes', 'excludes', 'includeTypes' and 'excludeTypes' should be supplied.", node);
        }
    }

    public boolean checkPropertyList(ClassNode cNode, List<String> propertyNameList, String listName, AnnotationNode anno, String typeName, boolean includeFields) {
        return this.checkPropertyList(cNode, propertyNameList, listName, anno, typeName, includeFields, false, false);
    }

    public boolean checkPropertyList(ClassNode cNode, List<String> propertyNameList, String listName, AnnotationNode anno, String typeName, boolean includeFields, boolean includeSuperProperties, boolean allProperties) {
        return this.checkPropertyList(cNode, propertyNameList, listName, anno, typeName, includeFields, includeSuperProperties, allProperties, false, false);
    }

    public boolean checkPropertyList(ClassNode cNode, List<String> propertyNameList, String listName, AnnotationNode anno, String typeName, boolean includeFields, boolean includeSuperProperties, boolean allProperties, boolean includeSuperFields, boolean includeStatic) {
        if (propertyNameList == null || propertyNameList.isEmpty()) {
            return true;
        }
        ArrayList<String> pNames = new ArrayList<String>();
        for (PropertyNode pNode : BeanUtils.getAllProperties(cNode, includeSuperProperties, includeStatic, allProperties)) {
            pNames.add(pNode.getField().getName());
        }
        boolean result = true;
        if (includeFields || includeSuperFields) {
            ArrayList<String> fNames = new ArrayList<String>();
            if (includeFields) {
                fNames.addAll(GeneralUtils.getInstanceNonPropertyFieldNames(cNode));
            }
            if (includeSuperFields) {
                List<FieldNode> superNonPropertyFields = GeneralUtils.getSuperNonPropertyFields(cNode.getSuperClass());
                Iterator iterator = superNonPropertyFields.iterator();
                while (iterator.hasNext()) {
                    FieldNode fn = (FieldNode)iterator.next();
                    fNames.add(fn.getName());
                }
            }
            for (String pName : propertyNameList) {
                if (pNames.contains(pName) || fNames.contains(pName)) continue;
                this.addError("Error during " + typeName + " processing: '" + listName + "' property or field '" + pName + "' does not exist.", anno);
                result = false;
            }
        } else {
            for (String pName : propertyNameList) {
                if (pNames.contains(pName)) continue;
                this.addError("Error during " + typeName + " processing: '" + listName + "' property '" + pName + "' does not exist.", anno);
                result = false;
            }
        }
        return result;
    }
}

