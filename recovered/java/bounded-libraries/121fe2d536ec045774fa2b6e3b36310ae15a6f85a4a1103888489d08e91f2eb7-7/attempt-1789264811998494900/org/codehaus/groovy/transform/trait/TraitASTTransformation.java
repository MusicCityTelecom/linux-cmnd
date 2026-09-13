/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform.trait;

import groovy.transform.CompilationUnitAware;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassCodeExpressionTransformer;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.VariableScopeVisitor;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.ASTTransformationVisitor;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.trait.NAryOperationRewriter;
import org.codehaus.groovy.transform.trait.SuperCallTraitTransformer;
import org.codehaus.groovy.transform.trait.TraitReceiverTransformer;
import org.codehaus.groovy.transform.trait.Traits;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class TraitASTTransformation
extends AbstractASTTransformation
implements CompilationUnitAware {
    public static final String DO_DYNAMIC = TraitReceiverTransformer.class + ".doDynamic";
    public static final String POST_TYPECHECKING_REPLACEMENT = TraitReceiverTransformer.class + ".replacement";
    private CompilationUnit compilationUnit;

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        this.compilationUnit = unit;
    }

    @Override
    public void visit(ASTNode[] nodes, SourceUnit source) {
        AnnotatedNode node = (AnnotatedNode)nodes[1];
        AnnotationNode anno = (AnnotationNode)nodes[0];
        if (!anno.getClassNode().equals(Traits.TRAIT_CLASSNODE)) {
            return;
        }
        this.init(nodes, source);
        if (node instanceof ClassNode) {
            ClassNode cNode = (ClassNode)node;
            if (!this.checkNotInterface(cNode, Traits.TRAIT_TYPE_NAME)) {
                return;
            }
            this.checkNoConstructor(cNode);
            this.checkExtendsClause(cNode);
            TraitASTTransformation.replaceExtendsByImplements(cNode);
            TraitASTTransformation.generateMethodsWithDefaultArgs(cNode);
            this.resolveHelperClassIfNecessary(this.createHelperClass(cNode));
        }
    }

    private void checkNoConstructor(ClassNode cNode) {
        if (!cNode.getDeclaredConstructors().isEmpty()) {
            this.addError("Error processing trait '" + cNode.getName() + "'.  Constructors are not allowed.", cNode);
        }
    }

    private void checkExtendsClause(ClassNode cNode) {
        ClassNode superClass = cNode.getSuperClass();
        if (superClass.isInterface() && !Traits.isTrait(superClass)) {
            this.addError("Trait cannot extend an interface. Use 'implements' instead", cNode);
        }
    }

    private static void replaceExtendsByImplements(ClassNode cNode) {
        ClassNode superClass = cNode.getUnresolvedSuperClass();
        if (Traits.isTrait(superClass)) {
            cNode.setSuperClass(ClassHelper.OBJECT_TYPE);
            cNode.setUnresolvedSuperClass(ClassHelper.OBJECT_TYPE);
            cNode.addInterface(superClass);
        }
    }

    private static void generateMethodsWithDefaultArgs(ClassNode cNode) {
        new Verifier(){

            @Override
            public void addDefaultParameterMethods(ClassNode cn) {
                this.setClassNode(cn);
                super.addDefaultParameterMethods(cn);
            }
        }.addDefaultParameterMethods(cNode);
    }

    private ClassNode createHelperClass(ClassNode cNode) {
        InnerClassNode helper = new InnerClassNode(cNode, Traits.helperClassName(cNode), 5129, ClassHelper.OBJECT_TYPE, ClassNode.EMPTY_ARRAY, null);
        helper.setStaticClass(true);
        cNode.setModifiers(1537);
        this.checkInnerClasses(cNode);
        MethodNode initializer = TraitASTTransformation.createInitMethod(false, cNode, helper);
        MethodNode staticInitializer = TraitASTTransformation.createInitMethod(true, cNode, helper);
        TraitASTTransformation.generatePropertyMethods(cNode);
        ArrayList<Object> fields = new ArrayList<FieldNode>();
        HashSet<String> fieldNames = new HashSet<String>();
        boolean hasStatic = false;
        for (FieldNode field : cNode.getFields()) {
            if ("metaClass".equals(field.getName()) || field.isSynthetic() && field.getName().indexOf(36) >= 0) continue;
            fields.add(field);
            fieldNames.add(field.getName());
            if (!field.isStatic()) continue;
            hasStatic = true;
        }
        InnerClassNode fieldHelper = null;
        InnerClassNode staticFieldHelper = null;
        if (!fields.isEmpty()) {
            fieldHelper = new InnerClassNode(cNode, Traits.fieldHelperClassName(cNode), 5641, ClassHelper.OBJECT_TYPE);
            fieldHelper.setStaticClass(true);
            if (hasStatic) {
                staticFieldHelper = new InnerClassNode(cNode, Traits.staticFieldHelperClassName(cNode), 5641, ClassHelper.OBJECT_TYPE);
                staticFieldHelper.setStaticClass(true);
            }
        }
        for (FieldNode fieldNode : fields) {
            this.processField(fieldNode, initializer, staticInitializer, fieldHelper, helper, staticFieldHelper, cNode, fieldNames);
        }
        ArrayList<MethodNode> methods = new ArrayList<MethodNode>(cNode.getMethods());
        ArrayList<MethodNode> arrayList = new ArrayList<MethodNode>();
        List<Statement> staticInitStatements = null;
        for (MethodNode methodNode : methods) {
            boolean declared = methodNode.getDeclaringClass() == cNode;
            if (!declared) continue;
            if (!methodNode.isSynthetic() && (methodNode.isProtected() || !methodNode.isPrivate() && !methodNode.isPublic())) {
                this.sourceUnit.addError(new SyntaxException("Cannot have protected/package-private method in a trait (" + cNode.getName() + "#" + methodNode.getTypeDescriptor() + ")", methodNode.getLineNumber(), methodNode.getColumnNumber()));
                return null;
            }
            if (!methodNode.isAbstract()) {
                MethodNode newMethod = this.processMethod(cNode, helper, methodNode, fieldHelper, fieldNames);
                if (methodNode.isStaticConstructor()) {
                    staticInitStatements = MethodNodeUtils.getCodeAsBlock(newMethod).getStatements();
                } else {
                    helper.addMethod(newMethod);
                }
            }
            if (!methodNode.isPrivate() && !methodNode.isStatic()) continue;
            arrayList.add(methodNode);
        }
        for (MethodNode methodNode : arrayList) {
            cNode.removeMethod(methodNode);
        }
        if (staticInitStatements != null) {
            BlockStatement toBlock = TraitASTTransformation.getBlockStatement(staticInitializer, staticInitializer.getCode());
            for (Statement next : staticInitStatements) {
                toBlock.addStatement(next);
            }
        }
        List<Statement> initStatements = cNode.getObjectInitializerStatements();
        Statement statement = initializer.getCode();
        BlockStatement toBlock = TraitASTTransformation.getBlockStatement(initializer, statement);
        for (Statement statement2 : initStatements) {
            Parameter selfParam = TraitASTTransformation.createSelfParameter(cNode, false);
            toBlock.addStatement(this.processBody(GeneralUtils.varX(selfParam), statement2, cNode, helper, fieldHelper, fieldNames));
        }
        initStatements.clear();
        cNode.getProperties().clear();
        TraitASTTransformation.copyClassAnnotations(cNode, helper);
        AnnotatedNodeUtils.markAsGenerated(cNode, helper);
        fields = new ArrayList<FieldNode>(cNode.getFields());
        for (FieldNode fieldNode : fields) {
            cNode.removeField(fieldNode.getName());
        }
        this.registerASTTransformations(helper);
        this.sourceUnit.getAST().addClass(helper);
        if (fieldHelper != null) {
            this.sourceUnit.getAST().addClass(fieldHelper);
            if (staticFieldHelper != null) {
                this.sourceUnit.getAST().addClass(staticFieldHelper);
            }
        }
        this.resolveScope(helper);
        if (fieldHelper != null) {
            this.resolveScope(fieldHelper);
            if (staticFieldHelper != null) {
                this.resolveScope(staticFieldHelper);
            }
        }
        return helper;
    }

    private void resolveHelperClassIfNecessary(ClassNode helperClass) {
        for (ClassNode cn : this.sourceUnit.getAST().getClasses()) {
            ClassNode unresolvedHelperClass = (ClassNode)cn.getNodeMetaData("UNRESOLVED_HELPER_CLASS");
            if (unresolvedHelperClass == null || !unresolvedHelperClass.getName().equals(helperClass.getName())) continue;
            unresolvedHelperClass.setRedirect(helperClass);
        }
    }

    private void resolveScope(ClassNode cNode) {
        new VariableScopeVisitor(this.sourceUnit).visitClass(cNode);
    }

    private static BlockStatement getBlockStatement(MethodNode targetMethod, Statement code) {
        BlockStatement blockStmt;
        if (code instanceof BlockStatement) {
            blockStmt = (BlockStatement)code;
        } else {
            blockStmt = GeneralUtils.block(code);
            targetMethod.setCode(blockStmt);
        }
        return blockStmt;
    }

    private static MethodNode createInitMethod(boolean isStatic, ClassNode cNode, ClassNode helper) {
        MethodNode initializer = new MethodNode(isStatic ? "$static$init$" : "$init$", 4105, ClassHelper.VOID_TYPE, new Parameter[]{TraitASTTransformation.createSelfParameter(cNode, isStatic)}, ClassNode.EMPTY_ARRAY, new BlockStatement());
        helper.addMethod(initializer);
        return initializer;
    }

    private void registerASTTransformations(ClassNode helper) {
        ASTTransformationVisitor.addNewPhaseOperation(this.compilationUnit, this.sourceUnit, helper);
        this.compilationUnit.addPhaseOperation((source, context, classNode) -> {
            if (classNode == helper) {
                PostTypeCheckingExpressionReplacer visitor = new PostTypeCheckingExpressionReplacer(source);
                visitor.visitClass(helper);
            }
        }, CompilePhase.INSTRUCTION_SELECTION.getPhaseNumber());
    }

    private static void copyClassAnnotations(ClassNode cNode, ClassNode helper) {
        for (AnnotationNode annotation : cNode.getAnnotations()) {
            ClassNode annotationType = annotation.getClassNode();
            if (annotationType.equals(Traits.TRAIT_CLASSNODE) || annotationType.equals(ClassHelper.SEALED_TYPE)) continue;
            helper.addAnnotation(annotation);
        }
    }

    private void checkInnerClasses(ClassNode cNode) {
        Iterator<InnerClassNode> it = cNode.getInnerClasses();
        while (it.hasNext()) {
            InnerClassNode origin = it.next();
            if ((origin.getModifiers() & 8) != 0) continue;
            this.sourceUnit.addError(new SyntaxException("Cannot have non-static inner class inside a trait (" + origin.getName() + ")", origin.getLineNumber(), origin.getColumnNumber()));
        }
    }

    private static void generatePropertyMethods(ClassNode cNode) {
        for (PropertyNode node : cNode.getProperties()) {
            TraitASTTransformation.processProperty(cNode, node);
        }
    }

    private static void processProperty(ClassNode cNode, PropertyNode node) {
        Statement setterBlock;
        String name = node.getName();
        FieldNode field = node.getField();
        int propNodeModifiers = node.getModifiers() & 0x1F;
        String getterName = node.getGetterNameOrDefault();
        String setterName = node.getSetterNameOrDefault();
        Statement getterBlock = node.getGetterBlock();
        if (getterBlock == null) {
            MethodNode getter = cNode.getGetterMethod(getterName);
            if (getter == null && ClassHelper.isPrimitiveBoolean(node.getType())) {
                getter = cNode.getGetterMethod("is" + BeanUtils.capitalize(name));
            }
            if (!node.isPrivate() && TraitASTTransformation.methodNeedsReplacement(cNode, getter)) {
                getterBlock = GeneralUtils.stmt(GeneralUtils.fieldX(field));
            }
        }
        if ((setterBlock = node.getSetterBlock()) == null) {
            MethodNode setter = cNode.getSetterMethod(setterName, false);
            if (!node.isPrivate() && (propNodeModifiers & 0x10) == 0 && TraitASTTransformation.methodNeedsReplacement(cNode, setter)) {
                setterBlock = GeneralUtils.assignS(GeneralUtils.fieldX(field), GeneralUtils.varX(name));
            }
        }
        if (getterBlock != null) {
            MethodNode secondGetter;
            MethodNode getter = new MethodNode(getterName, propNodeModifiers, node.getType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, getterBlock);
            getter.setSynthetic(true);
            ClassNodeUtils.addGeneratedMethod(cNode, getter);
            if (node.getGetterName() == null && getterName.startsWith("get") && (ClassHelper.isPrimitiveBoolean(node.getType()) || ClassHelper.isWrapperBoolean(node.getType())) && TraitASTTransformation.methodNeedsReplacement(cNode, secondGetter = new MethodNode("is" + BeanUtils.capitalize(name), propNodeModifiers, node.getType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, getterBlock))) {
                secondGetter.setSynthetic(true);
                ClassNodeUtils.addGeneratedMethod(cNode, secondGetter);
            }
        }
        if (setterBlock != null) {
            VariableExpression var = (VariableExpression)((BinaryExpression)((ExpressionStatement)setterBlock).getExpression()).getRightExpression();
            Parameter setterParameter = new Parameter(node.getType(), name);
            var.setAccessedVariable(setterParameter);
            MethodNode setter = new MethodNode(setterName, propNodeModifiers, ClassHelper.VOID_TYPE, GeneralUtils.params(setterParameter), ClassNode.EMPTY_ARRAY, setterBlock);
            setter.setSynthetic(true);
            ClassNodeUtils.addGeneratedMethod(cNode, setter);
        }
    }

    private static boolean methodNeedsReplacement(ClassNode cNode, MethodNode mNode) {
        if (mNode == null) {
            return true;
        }
        if (mNode.getDeclaringClass() == cNode) {
            return false;
        }
        return (mNode.getModifiers() & 0x10) == 0;
    }

    private void processField(FieldNode field, MethodNode initializer, MethodNode staticInitializer, ClassNode fieldHelper, ClassNode helper, ClassNode staticFieldHelper, ClassNode trait, Set<String> knownFields) {
        ClassNode target;
        if (field.isProtected()) {
            this.sourceUnit.addError(new SyntaxException("Cannot have protected field in a trait (" + trait.getName() + "#" + field.getName() + ")", field.getLineNumber(), field.getColumnNumber()));
            return;
        }
        Expression initialExpression = field.getInitialExpression();
        MethodNode selectedMethod = field.isStatic() ? staticInitializer : initializer;
        ClassNode classNode = target = field.isStatic() && staticFieldHelper != null ? staticFieldHelper : fieldHelper;
        if (initialExpression != null) {
            VariableExpression thisObject = GeneralUtils.varX(selectedMethod.getParameters()[0]);
            ExpressionStatement initCode = new ExpressionStatement(initialExpression);
            this.processBody(thisObject, initCode, trait, helper, fieldHelper, knownFields);
            if (field.isFinal()) {
                String baseName = field.isStatic() ? "$static$init$" : "$init$";
                MethodNode fieldInitializer = new MethodNode(baseName + Traits.remappedFieldName(trait, field.getName()), 4105, field.getOriginType(), new Parameter[]{TraitASTTransformation.createSelfParameter(trait, field.isStatic())}, ClassNode.EMPTY_ARRAY, GeneralUtils.returnS(initCode.getExpression()));
                helper.addMethod(fieldInitializer);
            } else {
                MethodCallExpression mce;
                BlockStatement code = (BlockStatement)selectedMethod.getCode();
                if (field.isStatic()) {
                    if (staticFieldHelper != null) {
                        target = staticFieldHelper;
                    }
                    mce = GeneralUtils.callX((Expression)GeneralUtils.classX(InvokerHelper.class), "invokeStaticMethod", (Expression)GeneralUtils.args(thisObject, GeneralUtils.constX(Traits.helperSetterName(field)), initCode.getExpression()));
                } else {
                    mce = GeneralUtils.callX((Expression)GeneralUtils.castX(TraitASTTransformation.createReceiverType(field.isStatic(), fieldHelper), thisObject), Traits.helperSetterName(field), (Expression)GeneralUtils.castX(field.getOriginType(), initCode.getExpression()));
                }
                mce.setImplicitThis(false);
                mce.setSourcePosition(initialExpression);
                code.addStatement(GeneralUtils.stmt(mce));
            }
        }
        ClassNodeUtils.addGeneratedMethod(target, Traits.helperSetterName(field), 1025, field.getOriginType(), new Parameter[]{new Parameter(field.getOriginType(), "val")}, ClassNode.EMPTY_ARRAY, null);
        ClassNodeUtils.addGeneratedMethod(target, Traits.helperGetterName(field), 1025, field.getOriginType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, null);
        int mods = field.getModifiers() & 0x9B;
        String dummyFieldName = String.format("$0x%04x", mods) + Traits.remappedFieldName(field.getOwner(), field.getName());
        FieldNode dummyField = new FieldNode(dummyFieldName, 4121, field.getOriginType().getPlainNodeReference(), fieldHelper, null);
        dummyField.setSynthetic(true);
        ArrayList<AnnotationNode> copy = new ArrayList<AnnotationNode>();
        ArrayList<AnnotationNode> skip = new ArrayList<AnnotationNode>();
        GeneralUtils.copyAnnotatedNodeAnnotations(field, copy, skip);
        dummyField.addAnnotations(copy);
        fieldHelper.addField(dummyField);
        dummyFieldName = (field.isStatic() ? "$static" : "$ins") + (field.isPublic() ? "$0" : "$1") + Traits.remappedFieldName(field.getOwner(), field.getName());
        dummyField = new FieldNode(dummyFieldName, 4121, field.getOriginType().getPlainNodeReference(), fieldHelper, null);
        dummyField.setSynthetic(true);
        dummyField.addAnnotations(copy);
        fieldHelper.addField(dummyField);
    }

    private MethodNode processMethod(ClassNode traitClass, ClassNode traitHelperClass, MethodNode methodNode, ClassNode fieldHelper, Collection<String> knownFields) {
        Parameter[] initialParams = methodNode.getParameters();
        Parameter[] newParams = new Parameter[initialParams.length + 1];
        newParams[0] = TraitASTTransformation.createSelfParameter(traitClass, methodNode.isStatic());
        System.arraycopy(initialParams, 0, newParams, 1, initialParams.length);
        int mod = methodNode.isPrivate() ? 2 : 1 | (methodNode.isFinal() ? 16 : 0);
        MethodNode mNode = new MethodNode(methodNode.getName(), mod | 8, methodNode.getReturnType(), newParams, methodNode.getExceptions(), this.processBody(GeneralUtils.varX(newParams[0]), methodNode.getCode(), traitClass, traitHelperClass, fieldHelper, knownFields));
        mNode.setSourcePosition(methodNode);
        mNode.addAnnotations(TraitASTTransformation.filterAnnotations(methodNode.getAnnotations()));
        mNode.setGenericsTypes(methodNode.getGenericsTypes());
        if (methodNode.isAbstract()) {
            mNode.setModifiers(1025);
        } else {
            methodNode.addAnnotation(new AnnotationNode(Traits.IMPLEMENTED_CLASSNODE));
        }
        methodNode.setCode(null);
        if (!methodNode.isPrivate() && !methodNode.isStatic()) {
            methodNode.setModifiers(1025);
        }
        return mNode;
    }

    private static List<AnnotationNode> filterAnnotations(List<AnnotationNode> annotations) {
        ArrayList<AnnotationNode> result = new ArrayList<AnnotationNode>(annotations.size());
        for (AnnotationNode annotation : annotations) {
            if (annotation.getClassNode().equals(ClassHelper.OVERRIDE_TYPE)) continue;
            result.add(annotation);
        }
        return result;
    }

    private static Parameter createSelfParameter(ClassNode traitClass, boolean isStatic) {
        ClassNode rawType = traitClass.getPlainNodeReference();
        ClassNode type = TraitASTTransformation.createReceiverType(isStatic, rawType);
        return new Parameter(type, isStatic ? "$static$self" : "$self");
    }

    private static ClassNode createReceiverType(boolean isStatic, ClassNode rawType) {
        ClassNode type = isStatic ? GenericsUtils.makeClassSafe0(ClassHelper.CLASS_Type, new GenericsType(rawType)) : rawType;
        return type;
    }

    private Statement processBody(VariableExpression thisObject, Statement code, ClassNode trait, ClassNode traitHelper, ClassNode fieldHelper, Collection<String> knownFields) {
        if (code != null) {
            code.visit(new NAryOperationRewriter(this.sourceUnit, knownFields));
            code.visit(new SuperCallTraitTransformer(this.sourceUnit));
            code.visit(new TraitReceiverTransformer(thisObject, this.sourceUnit, trait, traitHelper, fieldHelper, knownFields));
        }
        return code;
    }

    private static class PostTypeCheckingExpressionReplacer
    extends ClassCodeExpressionTransformer {
        private final SourceUnit sourceUnit;

        PostTypeCheckingExpressionReplacer(SourceUnit sourceUnit) {
            this.sourceUnit = sourceUnit;
        }

        @Override
        protected SourceUnit getSourceUnit() {
            return this.sourceUnit;
        }

        @Override
        public Expression transform(Expression exp) {
            Expression replacement;
            if (exp != null && (replacement = (Expression)exp.getNodeMetaData(POST_TYPECHECKING_REPLACEMENT)) != null) {
                return replacement;
            }
            return super.transform(exp);
        }
    }
}

