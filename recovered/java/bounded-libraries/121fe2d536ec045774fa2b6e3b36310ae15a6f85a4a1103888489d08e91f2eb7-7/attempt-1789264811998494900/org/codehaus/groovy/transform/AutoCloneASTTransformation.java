/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.transform.AutoClone;
import groovy.transform.AutoCloneStyle;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.sc.StaticCompilationVisitor;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

@GroovyASTTransformation(phase=CompilePhase.CANONICALIZATION)
public class AutoCloneASTTransformation
extends AbstractASTTransformation {
    static final Class MY_CLASS = AutoClone.class;
    static final ClassNode MY_TYPE = ClassHelper.make(MY_CLASS);
    static final String MY_TYPE_NAME = "@" + MY_TYPE.getNameWithoutPackage();
    private static final ClassNode CLONEABLE_TYPE = ClassHelper.make(Cloneable.class);
    private static final ClassNode BAOS_TYPE = ClassHelper.make(ByteArrayOutputStream.class);
    private static final ClassNode BAIS_TYPE = ClassHelper.make(ByteArrayInputStream.class);
    private static final ClassNode OOS_TYPE = ClassHelper.make(ObjectOutputStream.class);
    private static final ClassNode OIS_TYPE = ClassHelper.make(ObjectInputStream.class);
    private static final ClassNode INVOKER_TYPE = ClassHelper.make(InvokerHelper.class);

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
            cNode.addInterface(CLONEABLE_TYPE);
            boolean includeFields = this.memberHasValue(anno, "includeFields", true);
            AutoCloneStyle style = AutoCloneASTTransformation.getStyle(anno, "style");
            List<String> excludes = AutoCloneASTTransformation.getMemberStringList(anno, "excludes");
            if (!this.checkPropertyList(cNode, excludes, "excludes", anno, MY_TYPE_NAME, includeFields)) {
                return;
            }
            List<FieldNode> list = GeneralUtils.getInstancePropertyFields(cNode);
            if (includeFields) {
                list.addAll(GeneralUtils.getInstanceNonPropertyFields(cNode));
            }
            if (style == null) {
                style = AutoCloneStyle.CLONE;
            }
            switch (style) {
                case COPY_CONSTRUCTOR: {
                    AutoCloneASTTransformation.createCloneCopyConstructor(cNode, list, excludes);
                    break;
                }
                case SERIALIZATION: {
                    this.createCloneSerialization(cNode);
                    break;
                }
                case SIMPLE: {
                    AutoCloneASTTransformation.createSimpleClone(cNode, list, excludes);
                    break;
                }
                default: {
                    AutoCloneASTTransformation.createClone(cNode, list, excludes);
                }
            }
        }
    }

    private void createCloneSerialization(ClassNode cNode) {
        BlockStatement body = new BlockStatement();
        VariableExpression baos = GeneralUtils.localVarX("baos");
        body.addStatement(GeneralUtils.declS(baos, GeneralUtils.ctorX(BAOS_TYPE)));
        MethodCallExpression writeObject = GeneralUtils.callX((Expression)GeneralUtils.castX(OOS_TYPE, GeneralUtils.varX("it")), "writeObject", (Expression)GeneralUtils.varX("this"));
        writeObject.setImplicitThis(false);
        ClosureExpression writeClos = GeneralUtils.closureX(GeneralUtils.block(GeneralUtils.stmt(writeObject)));
        writeClos.setVariableScope(new VariableScope());
        body.addStatement(GeneralUtils.stmt(GeneralUtils.callX((Expression)baos, "withObjectOutputStream", (Expression)GeneralUtils.args(writeClos))));
        VariableExpression bais = GeneralUtils.localVarX("bais");
        body.addStatement(GeneralUtils.declS(bais, GeneralUtils.ctorX(BAIS_TYPE, GeneralUtils.args(GeneralUtils.callX(baos, "toByteArray")))));
        MethodCallExpression readObject = GeneralUtils.callX(GeneralUtils.castX(OIS_TYPE, GeneralUtils.varX("it")), "readObject");
        readObject.setImplicitThis(false);
        ClosureExpression readClos = GeneralUtils.closureX(GeneralUtils.block(GeneralUtils.stmt(GeneralUtils.castX(GenericsUtils.nonGeneric(cNode), readObject))));
        readClos.setVariableScope(new VariableScope());
        MethodCallExpression classLoader = GeneralUtils.callX(GeneralUtils.callThisX("getClass"), "getClassLoader");
        body.addStatement(GeneralUtils.returnS(GeneralUtils.callX((Expression)bais, "withObjectInputStream", (Expression)GeneralUtils.args(classLoader, readClos))));
        new VariableScopeVisitor(this.sourceUnit, true).visitClass(cNode);
        ClassNode[] exceptions = new ClassNode[]{ClassHelper.make(CloneNotSupportedException.class)};
        ClassNodeUtils.addGeneratedMethod(cNode, "clone", 1, GenericsUtils.nonGeneric(cNode), Parameter.EMPTY_ARRAY, exceptions, body);
    }

    private static void createCloneCopyConstructor(ClassNode cNode, List<FieldNode> list, List<String> excludes) {
        if (cNode.getDeclaredConstructors().isEmpty()) {
            BlockStatement noArgBody = new BlockStatement();
            noArgBody.addStatement(EmptyStatement.INSTANCE);
            ClassNodeUtils.addGeneratedConstructor(cNode, 1, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, noArgBody);
        }
        boolean hasThisCons = false;
        for (ConstructorNode consNode : cNode.getDeclaredConstructors()) {
            Parameter[] parameters = consNode.getParameters();
            if (parameters.length != 1 || !parameters[0].getType().equals(cNode)) continue;
            hasThisCons = true;
        }
        if (!hasThisCons) {
            boolean hasParent;
            BlockStatement initBody = new BlockStatement();
            Parameter initParam = GeneralUtils.param(GenericsUtils.nonGeneric(cNode), "other");
            VariableExpression other = GeneralUtils.varX(initParam);
            boolean bl = hasParent = !ClassHelper.isObjectType(cNode.getSuperClass());
            if (hasParent) {
                initBody.addStatement(GeneralUtils.stmt(GeneralUtils.ctorX(ClassNode.SUPER, other)));
            }
            for (FieldNode fieldNode : list) {
                String name = fieldNode.getName();
                if (excludes != null && excludes.contains(name)) continue;
                ClassNode fieldType = fieldNode.getType();
                PropertyExpression direct = GeneralUtils.propX((Expression)other, name);
                PropertyExpression to = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), name);
                Statement assignDirect = GeneralUtils.assignS(to, direct);
                Statement assignCloned = GeneralUtils.assignS(to, GeneralUtils.castX(fieldType, AutoCloneASTTransformation.callCloneDirectX(direct)));
                Statement assignClonedDynamic = GeneralUtils.assignS(to, GeneralUtils.castX(fieldType, AutoCloneASTTransformation.callCloneDynamicX(direct)));
                if (AutoCloneASTTransformation.isCloneableType(fieldType)) {
                    initBody.addStatement(assignCloned);
                    continue;
                }
                if (!AutoCloneASTTransformation.possiblyCloneable(fieldType)) {
                    initBody.addStatement(assignDirect);
                    continue;
                }
                initBody.addStatement(GeneralUtils.ifElseS(GeneralUtils.isInstanceOfX(direct, CLONEABLE_TYPE), assignClonedDynamic, assignDirect));
            }
            ClassNodeUtils.addGeneratedConstructor(cNode, 4, GeneralUtils.params(initParam), ClassNode.EMPTY_ARRAY, initBody);
        }
        ClassNode[] exceptions = new ClassNode[]{ClassHelper.make(CloneNotSupportedException.class)};
        ClassNodeUtils.addGeneratedMethod(cNode, "clone", 1, GenericsUtils.nonGeneric(cNode), Parameter.EMPTY_ARRAY, exceptions, GeneralUtils.block(GeneralUtils.stmt(GeneralUtils.ctorX(cNode, GeneralUtils.args(GeneralUtils.varX("this"))))));
    }

    private static boolean isCloneableType(ClassNode fieldType) {
        return GeneralUtils.isOrImplements(fieldType, CLONEABLE_TYPE) || !fieldType.getAnnotations(MY_TYPE).isEmpty();
    }

    private static boolean possiblyCloneable(ClassNode type) {
        return !ClassHelper.isPrimitiveType(type) && (AutoCloneASTTransformation.isCloneableType(type) || (type.getModifiers() & 0x10) == 0);
    }

    private static Expression callCloneDynamicX(Expression target) {
        return GeneralUtils.callX(INVOKER_TYPE, "invokeMethod", (Expression)GeneralUtils.args(target, GeneralUtils.constX("clone"), GeneralUtils.nullX()));
    }

    private static Expression callCloneDirectX(Expression direct) {
        return GeneralUtils.ternaryX(GeneralUtils.equalsNullX(direct), GeneralUtils.nullX(), GeneralUtils.callX(direct, "clone"));
    }

    private static void createSimpleClone(ClassNode cNode, List<FieldNode> fieldNodes, List<String> excludes) {
        if (cNode.getDeclaredConstructors().isEmpty()) {
            ClassNodeUtils.addGeneratedConstructor(cNode, 1, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, GeneralUtils.block(EmptyStatement.INSTANCE));
        }
        AutoCloneASTTransformation.addSimpleCloneHelperMethod(cNode, fieldNodes, excludes);
        VariableExpression result = GeneralUtils.localVarX("_result");
        ClassNode[] exceptions = new ClassNode[]{ClassHelper.make(CloneNotSupportedException.class)};
        ConstructorCallExpression init = GeneralUtils.ctorX(cNode);
        if (AnnotatedNodeUtils.hasAnnotation(cNode, StaticCompilationVisitor.COMPILESTATIC_CLASSNODE) && cNode.getDeclaredConstructor(Parameter.EMPTY_ARRAY) == null) {
            ConstructorNode cons = new ConstructorNode(1, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE);
            cons.setDeclaringClass(cNode);
            init.putNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, cons);
        }
        ClassNodeUtils.addGeneratedMethod(cNode, "clone", 1, GenericsUtils.nonGeneric(cNode), Parameter.EMPTY_ARRAY, exceptions, GeneralUtils.block(GeneralUtils.declS(result, init), GeneralUtils.stmt(GeneralUtils.callThisX("cloneOrCopyMembers", GeneralUtils.args(result))), GeneralUtils.returnS(result)));
    }

    private static void addSimpleCloneHelperMethod(ClassNode cNode, List<FieldNode> fieldNodes, List<String> excludes) {
        Parameter methodParam = new Parameter(GenericsUtils.nonGeneric(cNode), "other");
        VariableExpression other = GeneralUtils.varX(methodParam);
        boolean hasParent = !ClassHelper.isObjectType(cNode.getSuperClass());
        BlockStatement methodBody = new BlockStatement();
        if (hasParent) {
            methodBody.addStatement(GeneralUtils.stmt(GeneralUtils.callSuperX("cloneOrCopyMembers", GeneralUtils.args(other))));
        }
        for (FieldNode fieldNode : fieldNodes) {
            String name = fieldNode.getName();
            if (excludes != null && excludes.contains(name)) continue;
            ClassNode fieldType = fieldNode.getType();
            PropertyExpression direct = GeneralUtils.propX((Expression)GeneralUtils.varX("this"), name);
            PropertyExpression to = GeneralUtils.propX((Expression)other, name);
            Statement assignDirect = GeneralUtils.assignS(to, direct);
            Statement assignCloned = GeneralUtils.assignS(to, GeneralUtils.castX(fieldType, AutoCloneASTTransformation.callCloneDirectX(direct)));
            Statement assignClonedDynamic = GeneralUtils.assignS(to, GeneralUtils.castX(fieldType, AutoCloneASTTransformation.callCloneDynamicX(direct)));
            if (AutoCloneASTTransformation.isCloneableType(fieldType)) {
                methodBody.addStatement(assignCloned);
                continue;
            }
            if (!AutoCloneASTTransformation.possiblyCloneable(fieldType)) {
                methodBody.addStatement(assignDirect);
                continue;
            }
            methodBody.addStatement(GeneralUtils.ifElseS(GeneralUtils.isInstanceOfX(direct, CLONEABLE_TYPE), assignClonedDynamic, assignDirect));
        }
        ClassNode[] exceptions = new ClassNode[]{ClassHelper.make(CloneNotSupportedException.class)};
        ClassNodeUtils.addGeneratedMethod(cNode, "cloneOrCopyMembers", 4, ClassHelper.VOID_TYPE, GeneralUtils.params(methodParam), exceptions, methodBody);
    }

    private static void createClone(ClassNode cNode, List<FieldNode> fieldNodes, List<String> excludes) {
        BlockStatement body = new BlockStatement();
        VariableExpression result = GeneralUtils.localVarX("_result");
        body.addStatement(GeneralUtils.declS(result, GeneralUtils.castX(cNode, GeneralUtils.callSuperX("clone"))));
        for (FieldNode fieldNode : fieldNodes) {
            if (excludes != null && excludes.contains(fieldNode.getName())) continue;
            ClassNode fieldType = fieldNode.getType();
            VariableExpression fieldExpr = GeneralUtils.varX(fieldNode);
            PropertyExpression to = GeneralUtils.propX((Expression)result, fieldNode.getName());
            Statement doClone = GeneralUtils.assignS(to, GeneralUtils.castX(fieldType, AutoCloneASTTransformation.callCloneDirectX(fieldExpr)));
            Statement doCloneDynamic = GeneralUtils.assignS(to, GeneralUtils.castX(fieldType, AutoCloneASTTransformation.callCloneDynamicX(fieldExpr)));
            if (AutoCloneASTTransformation.isCloneableType(fieldType)) {
                body.addStatement(doClone);
                continue;
            }
            if (!AutoCloneASTTransformation.possiblyCloneable(fieldType)) continue;
            body.addStatement(GeneralUtils.ifS((Expression)GeneralUtils.isInstanceOfX(fieldExpr, CLONEABLE_TYPE), doCloneDynamic));
        }
        body.addStatement(GeneralUtils.returnS(result));
        ClassNode[] exceptions = new ClassNode[]{ClassHelper.make(CloneNotSupportedException.class)};
        ClassNodeUtils.addGeneratedMethod(cNode, "clone", 1, GenericsUtils.nonGeneric(cNode), Parameter.EMPTY_ARRAY, exceptions, body);
    }

    private static AutoCloneStyle getStyle(AnnotationNode node, String name) {
        ClassExpression ce;
        PropertyExpression prop;
        Expression oe;
        Expression member = node.getMember(name);
        if (member instanceof PropertyExpression && (oe = (prop = (PropertyExpression)member).getObjectExpression()) instanceof ClassExpression && (ce = (ClassExpression)oe).getType().getName().equals("groovy.transform.AutoCloneStyle")) {
            return AutoCloneStyle.valueOf(prop.getPropertyAsString());
        }
        return null;
    }
}

