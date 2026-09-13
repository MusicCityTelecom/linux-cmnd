/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyClassLoader;
import groovy.lang.MetaClass;
import groovy.lang.MissingPropertyException;
import groovy.transform.CompilationUnitAware;
import groovy.transform.ImmutableBase;
import groovy.transform.options.PropertyHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ImmutablePropertyUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.TupleConstructorASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class ImmutableASTTransformation
extends AbstractASTTransformation
implements CompilationUnitAware {
    private static final ClassNode HMAP_TYPE = ClassHelper.makeWithoutCaching(HashMap.class, false);
    private static final Class<? extends Annotation> MY_CLASS = ImmutableBase.class;
    public static final ClassNode MY_TYPE = ClassHelper.makeWithoutCaching(MY_CLASS, false);
    private static final String MY_TYPE_NAME = MY_TYPE.getNameWithoutPackage();
    public static final String IMMUTABLE_BREADCRUMB = "_IMMUTABLE_BREADCRUMB";
    private CompilationUnit compilationUnit;

    @Override
    public String getAnnotationName() {
        return MY_TYPE_NAME;
    }

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        this.compilationUnit = unit;
    }

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        this.init(nodes, source);
        AnnotatedNode parent = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!MY_TYPE.equals(anno.getClassNode())) {
            return;
        }
        if (parent instanceof ClassNode) {
            GroovyClassLoader classLoader = this.compilationUnit != null ? this.compilationUnit.getTransformLoader() : source.getClassLoader();
            PropertyHandler handler = PropertyHandler.createPropertyHandler(this, classLoader, (ClassNode)parent);
            if (handler == null || !handler.validateAttributes(this, anno)) {
                return;
            }
            this.doMakeImmutable((ClassNode)parent, anno, handler);
        }
    }

    private void doMakeImmutable(ClassNode cNode, AnnotationNode node, PropertyHandler handler) {
        ArrayList<PropertyNode> newProperties = new ArrayList<PropertyNode>();
        String cName = cNode.getName();
        if (!this.checkNotInterface(cNode, MY_TYPE_NAME)) {
            return;
        }
        List<PropertyNode> pList = GeneralUtils.getInstanceProperties(cNode);
        for (PropertyNode propertyNode : pList) {
            ImmutableASTTransformation.adjustPropertyForImmutability(propertyNode, newProperties, handler);
        }
        for (PropertyNode propertyNode : newProperties) {
            cNode.getProperties().remove(propertyNode);
            ImmutableASTTransformation.addProperty(cNode, propertyNode);
        }
        List<FieldNode> fList = cNode.getFields();
        for (FieldNode fNode : fList) {
            ImmutableASTTransformation.ensureNotPublic(this, cName, fNode);
        }
        if (this.hasAnnotation(cNode, TupleConstructorASTTransformation.MY_TYPE)) {
            AnnotationNode annotationNode = cNode.getAnnotations(TupleConstructorASTTransformation.MY_TYPE).get(0);
            if (this.unsupportedTupleAttribute(annotationNode, "excludes")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "includes")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "includeFields")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "includeProperties")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "includeSuperFields")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "callSuper")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "force")) {
                return;
            }
        }
        if (ClassNodeUtils.hasExplicitConstructor(this, cNode)) {
            return;
        }
        if (!pList.isEmpty() && this.memberHasValue(node, "copyWith", Boolean.TRUE) && !GeneralUtils.hasDeclaredMethod(cNode, "copyWith", 1)) {
            ImmutableASTTransformation.createCopyWith(cNode, pList);
        }
    }

    private boolean unsupportedTupleAttribute(AnnotationNode anno, String memberName) {
        if (this.getMemberValue(anno, memberName) != null) {
            String tname = TupleConstructorASTTransformation.MY_TYPE_NAME;
            this.addError("Error during " + MY_TYPE_NAME + " processing: Annotation attribute '" + memberName + "' not supported for " + tname + " when used with " + MY_TYPE_NAME, anno);
            return true;
        }
        return false;
    }

    static boolean isSpecialNamedArgCase(List<PropertyNode> list, boolean checkSize) {
        if (checkSize && list.size() != 1) {
            return false;
        }
        if (list.size() == 0) {
            return false;
        }
        ClassNode firstParamType = list.get(0).getField().getType();
        if (firstParamType.equals(ClassHelper.MAP_TYPE)) {
            return true;
        }
        for (ClassNode candidate = HMAP_TYPE; candidate != null; candidate = candidate.getSuperClass()) {
            if (!candidate.equals(firstParamType)) continue;
            return true;
        }
        return false;
    }

    private static void ensureNotPublic(AbstractASTTransformation xform, String cNode, FieldNode fNode) {
        String fName = fNode.getName();
        if (!(!fNode.isPublic() || fName.contains("$") || fNode.isStatic() && fNode.isFinal())) {
            xform.addError("Public field '" + fName + "' not allowed for " + MY_TYPE_NAME + " class '" + cNode + "'.", fNode);
        }
    }

    private static void addProperty(ClassNode cNode, PropertyNode pNode) {
        FieldNode fn = pNode.getField();
        cNode.getFields().remove(fn);
        cNode.addProperty(pNode.getName(), pNode.getModifiers() | 0x10, pNode.getType(), pNode.getInitialExpression(), pNode.getGetterBlock(), pNode.getSetterBlock());
        FieldNode newfn = cNode.getField(fn.getName());
        cNode.getFields().remove(newfn);
        cNode.addField(fn);
    }

    static boolean makeImmutable(ClassNode cNode) {
        List<AnnotationNode> annotations = cNode.getAnnotations(ImmutablePropertyUtils.IMMUTABLE_OPTIONS_TYPE);
        AnnotationNode annoImmutable = annotations.isEmpty() ? null : annotations.get(0);
        return annoImmutable != null;
    }

    private static void adjustPropertyForImmutability(PropertyNode pNode, List<PropertyNode> newNodes, PropertyHandler handler) {
        FieldNode fNode = pNode.getField();
        fNode.setModifiers(pNode.getModifiers() & 0xFFFFFFFE | 0x10 | 2);
        fNode.setNodeMetaData(IMMUTABLE_BREADCRUMB, Boolean.TRUE);
        pNode.setSetterBlock(null);
        Statement getter = handler.createPropGetter(pNode);
        if (getter != null) {
            pNode.setGetterBlock(getter);
        }
        newNodes.add(pNode);
    }

    private static Statement createCheckForProperty(PropertyNode pNode) {
        return GeneralUtils.block(new VariableScope(), GeneralUtils.ifElseS(GeneralUtils.callX((Expression)GeneralUtils.varX("map", HMAP_TYPE), "containsKey", (Expression)GeneralUtils.args(GeneralUtils.constX(pNode.getName()))), GeneralUtils.block(new VariableScope(), GeneralUtils.declS(GeneralUtils.localVarX("newValue", ClassHelper.OBJECT_TYPE), GeneralUtils.callX((Expression)GeneralUtils.varX("map", HMAP_TYPE), "get", (Expression)GeneralUtils.args(GeneralUtils.constX(pNode.getName())))), GeneralUtils.declS(GeneralUtils.localVarX("oldValue", ClassHelper.OBJECT_TYPE), GeneralUtils.callThisX(pNode.getGetterNameOrDefault())), GeneralUtils.ifS((Expression)GeneralUtils.neX(GeneralUtils.varX("newValue", ClassHelper.OBJECT_TYPE), GeneralUtils.varX("oldValue", ClassHelper.OBJECT_TYPE)), GeneralUtils.block(new VariableScope(), GeneralUtils.assignS(GeneralUtils.varX("oldValue", ClassHelper.OBJECT_TYPE), GeneralUtils.varX("newValue", ClassHelper.OBJECT_TYPE)), GeneralUtils.assignS(GeneralUtils.varX("dirty", ClassHelper.boolean_TYPE), ConstantExpression.TRUE))), GeneralUtils.stmt(GeneralUtils.callX((Expression)GeneralUtils.varX("construct", HMAP_TYPE), "put", (Expression)GeneralUtils.args(GeneralUtils.constX(pNode.getName()), GeneralUtils.varX("oldValue", ClassHelper.OBJECT_TYPE))))), GeneralUtils.block(new VariableScope(), GeneralUtils.stmt(GeneralUtils.callX((Expression)GeneralUtils.varX("construct", HMAP_TYPE), "put", (Expression)GeneralUtils.args(GeneralUtils.constX(pNode.getName()), GeneralUtils.callThisX(pNode.getGetterNameOrDefault())))))));
    }

    private static void createCopyWith(ClassNode cNode, List<PropertyNode> pList) {
        BlockStatement body = new BlockStatement();
        body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.orX(GeneralUtils.equalsNullX(GeneralUtils.varX("map", ClassHelper.MAP_TYPE)), GeneralUtils.eqX(GeneralUtils.callX(GeneralUtils.varX("map", HMAP_TYPE), "size"), GeneralUtils.constX(0))), GeneralUtils.returnS(GeneralUtils.varX("this", cNode))));
        body.addStatement(GeneralUtils.declS(GeneralUtils.localVarX("dirty", ClassHelper.boolean_TYPE), ConstantExpression.PRIM_FALSE));
        body.addStatement(GeneralUtils.declS(GeneralUtils.localVarX("construct", HMAP_TYPE), GeneralUtils.ctorX(HMAP_TYPE)));
        for (PropertyNode pNode : pList) {
            body.addStatement(ImmutableASTTransformation.createCheckForProperty(pNode));
        }
        body.addStatement(GeneralUtils.returnS(GeneralUtils.ternaryX(GeneralUtils.isTrueX(GeneralUtils.varX("dirty", ClassHelper.boolean_TYPE)), GeneralUtils.ctorX(cNode, GeneralUtils.args(GeneralUtils.varX("construct", HMAP_TYPE))), GeneralUtils.varX("this", cNode))));
        ClassNode clonedNode = cNode.getPlainNodeReference();
        ClassNodeUtils.addGeneratedMethod(cNode, "copyWith", 17, clonedNode, GeneralUtils.params(new Parameter(new ClassNode(Map.class), "map")), null, body);
    }

    public static Object checkImmutable(String className, String fieldName, Object field) {
        if (field == null || field instanceof Enum || ImmutablePropertyUtils.isBuiltinImmutable(field.getClass().getName())) {
            return field;
        }
        if (field instanceof Collection) {
            return DefaultGroovyMethods.asImmutable((Collection)field);
        }
        if (ImmutableASTTransformation.getAnnotationByName(field, "groovy.transform.Immutable") != null) {
            return field;
        }
        String typeName = field.getClass().getName();
        throw new RuntimeException(ImmutablePropertyUtils.createErrorMessage(className, fieldName, typeName, "constructing"));
    }

    private static Annotation getAnnotationByName(Object field, String name) {
        for (Annotation an : field.getClass().getAnnotations()) {
            if (!an.getClass().getName().equals(name)) continue;
            return an;
        }
        return null;
    }

    public static Object checkImmutable(Class<?> clazz, String fieldName, Object field) {
        if (field == null || field instanceof Enum || ImmutablePropertyUtils.builtinOrMarkedImmutableClass(field.getClass())) {
            return field;
        }
        boolean isImmutable = false;
        for (Annotation an : field.getClass().getAnnotations()) {
            if (!an.getClass().getName().startsWith("groovy.transform.Immutable")) continue;
            isImmutable = true;
            break;
        }
        if (isImmutable) {
            return field;
        }
        if (field instanceof Collection) {
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                Class<?> fieldType = declaredField.getType();
                if (Collection.class.isAssignableFrom(fieldType)) {
                    return DefaultGroovyMethods.asImmutable((Collection)field);
                }
                if (ImmutablePropertyUtils.builtinOrMarkedImmutableClass(fieldType)) {
                    return field;
                }
            }
            catch (NoSuchFieldException noSuchFieldException) {
                // empty catch block
            }
        }
        String typeName = field.getClass().getName();
        throw new RuntimeException(ImmutablePropertyUtils.createErrorMessage(clazz.getName(), fieldName, typeName, "constructing"));
    }

    public static Object checkImmutable(Class<?> clazz, String fieldName, Object field, List<String> knownImmutableFieldNames, List<Class> knownImmutableClasses) {
        if (field == null || field instanceof Enum || ImmutablePropertyUtils.builtinOrMarkedImmutableClass(field.getClass()) || knownImmutableFieldNames.contains(fieldName) || knownImmutableClasses.contains(field.getClass())) {
            return field;
        }
        boolean isImmutable = false;
        for (Annotation an : field.getClass().getAnnotations()) {
            if (!an.getClass().getName().startsWith("groovy.transform.Immutable")) continue;
            isImmutable = true;
            break;
        }
        if (isImmutable) {
            return field;
        }
        if (field instanceof Collection) {
            try {
                Field declaredField = clazz.getDeclaredField(fieldName);
                Class<?> fieldType = declaredField.getType();
                if (Collection.class.isAssignableFrom(fieldType)) {
                    return DefaultGroovyMethods.asImmutable((Collection)field);
                }
                if (ImmutablePropertyUtils.builtinOrMarkedImmutableClass(fieldType) || knownImmutableClasses.contains(fieldType)) {
                    return field;
                }
            }
            catch (NoSuchFieldException noSuchFieldException) {
                // empty catch block
            }
        }
        String typeName = field.getClass().getName();
        throw new RuntimeException(ImmutablePropertyUtils.createErrorMessage(clazz.getName(), fieldName, typeName, "constructing"));
    }

    public static void checkPropNames(Object instance, Map<String, Object> args) {
        MetaClass metaClass = InvokerHelper.getMetaClass(instance);
        for (String name : args.keySet()) {
            if (metaClass.hasProperty(instance, name) != null) continue;
            throw new MissingPropertyException(name, instance.getClass());
        }
    }
}

