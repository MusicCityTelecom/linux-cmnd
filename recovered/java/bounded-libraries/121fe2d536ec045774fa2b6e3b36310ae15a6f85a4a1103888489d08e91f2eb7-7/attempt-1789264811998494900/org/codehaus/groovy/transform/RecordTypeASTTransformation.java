/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.transform;

import groovy.lang.GroovyClassLoader;
import groovy.transform.CompilationUnitAware;
import groovy.transform.NamedParam;
import groovy.transform.RecordBase;
import groovy.transform.RecordOptions;
import groovy.transform.RecordTypeMode;
import groovy.transform.options.PropertyHandler;
import groovyjarjarasm.asm.Handle;
import groovyjarjarasm.asm.Type;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.apache.groovy.lang.annotation.Incubating;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.RecordComponentNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilePhase;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.transform.AbstractASTTransformation;
import org.codehaus.groovy.transform.EqualsAndHashCodeASTTransformation;
import org.codehaus.groovy.transform.GroovyASTTransformation;
import org.codehaus.groovy.transform.ToStringASTTransformation;
import org.codehaus.groovy.transform.TupleConstructorASTTransformation;

@GroovyASTTransformation(phase=CompilePhase.SEMANTIC_ANALYSIS)
public class RecordTypeASTTransformation
extends AbstractASTTransformation
implements CompilationUnitAware {
    private CompilationUnit compilationUnit;
    private static final ClassNode ARRAYLIST_TYPE = ClassHelper.makeWithoutCaching(ArrayList.class, false);
    private static final String COMPONENTS = "components";
    private static final String COPY_WITH = "copyWith";
    private static final String GET_AT = "getAt";
    private static final ClassNode ILLEGAL_ARGUMENT = ClassHelper.makeWithoutCaching(IllegalArgumentException.class);
    private static final ClassNode LHMAP_TYPE = ClassHelper.makeWithoutCaching(LinkedHashMap.class, false);
    private static final String NAMED_ARGS = "namedArgs";
    private static final ClassNode NAMED_PARAM_TYPE = ClassHelper.makeWithoutCaching(NamedParam.class, false);
    private static final int PUBLIC_FINAL = 17;
    private static final String RECORD_CLASS_NAME = "java.lang.Record";
    private static final ClassNode RECORD_OPTIONS_TYPE = ClassHelper.make(RecordOptions.class);
    private static final String SIZE = "size";
    private static final String TO_LIST = "toList";
    private static final String TO_MAP = "toMap";
    private static final Class<? extends Annotation> MY_CLASS = RecordBase.class;
    public static final ClassNode MY_TYPE = ClassHelper.makeWithoutCaching(MY_CLASS, false);
    private static final String MY_TYPE_NAME = MY_TYPE.getNameWithoutPackage();

    @Override
    public String getAnnotationName() {
        return MY_TYPE_NAME;
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
            if (handler == null) {
                return;
            }
            if (!handler.validateAttributes(this, anno)) {
                return;
            }
            this.doProcessRecordType((ClassNode)parent, handler);
        }
    }

    private void doProcessRecordType(ClassNode cNode, PropertyHandler handler) {
        List<PropertyNode> pList;
        boolean isNative;
        List<AnnotationNode> annotations;
        if (cNode.getNodeMetaData("_RECORD_HEADER") != null) {
            cNode.putNodeMetaData("_SKIPPABLE_ANNOTATIONS", true);
        }
        AnnotationNode options = (annotations = cNode.getAnnotations(RECORD_OPTIONS_TYPE)).isEmpty() ? null : annotations.get(0);
        RecordTypeMode mode = RecordTypeASTTransformation.getMode(options, "mode");
        boolean isPostJDK16 = false;
        String message = "Expecting JDK16+ but unable to determine target bytecode";
        if (this.sourceUnit != null) {
            CompilerConfiguration config = this.sourceUnit.getConfiguration();
            String targetBytecode = config.getTargetBytecode();
            isPostJDK16 = CompilerConfiguration.isPostJDK16(targetBytecode);
            message = "Expecting JDK16+ but found " + targetBytecode;
        }
        boolean bl = isNative = isPostJDK16 && mode != RecordTypeMode.EMULATE;
        if (isNative) {
            String sName = cNode.getUnresolvedSuperClass().getName();
            if (!sName.equals("java.lang.Object") && !sName.equals(RECORD_CLASS_NAME)) {
                this.addError("Invalid superclass for native record found: " + sName, cNode);
            }
            cNode.setSuperClass(ClassHelper.makeWithoutCaching(RECORD_CLASS_NAME));
            cNode.setModifiers(cNode.getModifiers() | 0x10000);
            pList = GeneralUtils.getInstanceProperties(cNode);
            if (!pList.isEmpty()) {
                cNode.setRecordComponents(new ArrayList<RecordComponentNode>());
            }
            for (PropertyNode propertyNode : pList) {
                ClassNode pType = propertyNode.getOriginType();
                ClassNode type = pType.getPlainNodeReference();
                type.setGenericsPlaceHolder(pType.isGenericsPlaceHolder());
                type.setGenericsTypes(pType.getGenericsTypes());
                RecordComponentNode rec = new RecordComponentNode(cNode, propertyNode.getName(), type, propertyNode.getAnnotations());
                rec.putNodeMetaData("_SKIPPABLE_ANNOTATIONS", true);
                cNode.getRecordComponents().add(rec);
            }
        } else if (mode == RecordTypeMode.NATIVE) {
            this.addError(message + " when attempting to create a native record", cNode);
        }
        String cName = cNode.getName();
        if (!this.checkNotInterface(cNode, MY_TYPE_NAME)) {
            return;
        }
        RecordTypeASTTransformation.makeClassFinal(this, cNode);
        RecordTypeASTTransformation.makeInnerRecordStatic(cNode);
        pList = GeneralUtils.getInstanceProperties(cNode);
        for (PropertyNode propertyNode : pList) {
            RecordTypeASTTransformation.adjustPropertyForShallowImmutability(cNode, propertyNode, handler);
            propertyNode.setModifiers(propertyNode.getModifiers() | 0x10);
        }
        List<FieldNode> fList = cNode.getFields();
        for (FieldNode fNode : fList) {
            RecordTypeASTTransformation.ensureNotPublic(this, cName, fNode);
        }
        if (cNode.getDeclaredField("serialVersionUID") == null) {
            cNode.addField("serialVersionUID", 26, ClassHelper.long_TYPE, GeneralUtils.constX(0L));
        }
        if (!this.hasAnnotation(cNode, ToStringASTTransformation.MY_TYPE)) {
            if (isNative) {
                this.createRecordToString(cNode);
            } else {
                ToStringASTTransformation.createToString(cNode, false, false, null, null, true, false, false, false, false, false, false, false, true, new String[]{"[", "]", "=", ", "});
            }
        }
        if (!this.hasAnnotation(cNode, EqualsAndHashCodeASTTransformation.MY_TYPE)) {
            if (isNative) {
                this.createRecordEquals(cNode);
                this.createRecordHashCode(cNode);
            } else {
                EqualsAndHashCodeASTTransformation.createEquals(cNode, false, false, false, null, null, false, false, true);
                EqualsAndHashCodeASTTransformation.createHashCode(cNode, false, false, false, null, null, false, false, true);
            }
        }
        if (this.hasAnnotation(cNode, TupleConstructorASTTransformation.MY_TYPE)) {
            AnnotationNode annotationNode = cNode.getAnnotations(TupleConstructorASTTransformation.MY_TYPE).get(0);
            if (this.unsupportedTupleAttribute(annotationNode, "excludes")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "includes")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "includeProperties")) {
                return;
            }
            if (this.unsupportedTupleAttribute(annotationNode, "includeSuperFields")) {
                return;
            }
        }
        if (options != null && this.memberHasValue(options, COPY_WITH, Boolean.TRUE) && !GeneralUtils.hasDeclaredMethod(cNode, COPY_WITH, 1)) {
            this.createCopyWith(cNode, pList);
        }
        if (!(options != null && this.memberHasValue(options, GET_AT, Boolean.FALSE) || GeneralUtils.hasDeclaredMethod(cNode, GET_AT, 1))) {
            this.createGetAt(cNode, pList);
        }
        if (!(options != null && this.memberHasValue(options, TO_LIST, Boolean.FALSE) || GeneralUtils.hasDeclaredMethod(cNode, TO_LIST, 0))) {
            this.createToList(cNode, pList);
        }
        if (!(options != null && this.memberHasValue(options, TO_MAP, Boolean.FALSE) || GeneralUtils.hasDeclaredMethod(cNode, TO_MAP, 0))) {
            this.createToMap(cNode, pList);
        }
        if (options != null && this.memberHasValue(options, COMPONENTS, Boolean.TRUE) && !GeneralUtils.hasDeclaredMethod(cNode, COMPONENTS, 0)) {
            this.createComponents(cNode, pList);
        }
        if (!(options != null && this.memberHasValue(options, SIZE, Boolean.FALSE) || GeneralUtils.hasDeclaredMethod(cNode, SIZE, 0))) {
            ClassNodeUtils.addGeneratedMethod(cNode, SIZE, 17, ClassHelper.int_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, GeneralUtils.returnS(GeneralUtils.constX(pList.size())));
        }
    }

    @Incubating
    public static boolean recordNative(ClassNode node) {
        return node.getUnresolvedSuperClass() != null && RECORD_CLASS_NAME.equals(node.getUnresolvedSuperClass().getName());
    }

    private void createComponents(ClassNode cNode, List<PropertyNode> pList) {
        Statement body;
        ClassNode tupleClass;
        if (pList.size() > 16) {
            this.addError("Record has too many components for a components() method", cNode);
        }
        if ((tupleClass = this.getClass(cNode, "groovy.lang.Tuple" + pList.size())) == null) {
            return;
        }
        if (pList.isEmpty()) {
            body = GeneralUtils.returnS(GeneralUtils.propX((Expression)GeneralUtils.classX(tupleClass), "INSTANCE"));
        } else {
            ArrayList<GenericsType> gtypes = new ArrayList<GenericsType>();
            ArgumentListExpression args = new ArgumentListExpression();
            for (PropertyNode pNode : pList) {
                args.addExpression(GeneralUtils.callThisX(pNode.getName()));
                gtypes.add(new GenericsType(ClassHelper.getWrapper(pNode.getType())));
            }
            tupleClass.setGenericsTypes(gtypes.toArray(GenericsType.EMPTY_ARRAY));
            body = GeneralUtils.returnS(GeneralUtils.ctorX(tupleClass, args));
        }
        ClassNodeUtils.addGeneratedMethod(cNode, COMPONENTS, 17, tupleClass, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private ClassNode getClass(ClassNode cNode, String tupleName) {
        try {
            return ClassHelper.makeWithoutCaching(Class.forName(tupleName)).getPlainNodeReference();
        }
        catch (ClassNotFoundException cnfe) {
            this.addError("Unable to find Tuple class '" + tupleName + "'", cNode);
            return null;
        }
    }

    private void createToList(ClassNode cNode, List<PropertyNode> pList) {
        ArrayList<Expression> args = new ArrayList<Expression>();
        for (PropertyNode pNode : pList) {
            args.add(GeneralUtils.callThisX(pNode.getName()));
        }
        Statement body = GeneralUtils.returnS(GeneralUtils.ctorX(ARRAYLIST_TYPE.getPlainNodeReference(), GeneralUtils.listX(args)));
        ClassNodeUtils.addGeneratedMethod(cNode, TO_LIST, 17, ClassHelper.LIST_TYPE.getPlainNodeReference(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private void createToMap(ClassNode cNode, List<PropertyNode> pList) {
        ArrayList<MapEntryExpression> entries = new ArrayList<MapEntryExpression>();
        for (PropertyNode pNode : pList) {
            String name = pNode.getName();
            entries.add(GeneralUtils.mapEntryX(name, (Expression)GeneralUtils.callThisX(name)));
        }
        Statement body = GeneralUtils.returnS(GeneralUtils.ctorX(LHMAP_TYPE.getPlainNodeReference(), GeneralUtils.args(GeneralUtils.mapX(entries))));
        ClassNodeUtils.addGeneratedMethod(cNode, TO_MAP, 17, ClassHelper.MAP_TYPE.getPlainNodeReference(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private void createGetAt(ClassNode cNode, List<PropertyNode> pList) {
        VariableExpression i = GeneralUtils.varX("i");
        SwitchStatement body = GeneralUtils.switchS(i, GeneralUtils.throwS(GeneralUtils.ctorX(ILLEGAL_ARGUMENT, GeneralUtils.args(GeneralUtils.plusX(GeneralUtils.constX("No record component with index: "), i)))));
        for (int j = 0; j < pList.size(); ++j) {
            body.addCase(GeneralUtils.caseS(GeneralUtils.constX(j), GeneralUtils.returnS(GeneralUtils.callThisX(pList.get(j).getName()))));
        }
        ClassNodeUtils.addGeneratedMethod(cNode, GET_AT, 17, ClassHelper.OBJECT_TYPE.getPlainNodeReference(), GeneralUtils.params(GeneralUtils.param(ClassHelper.int_TYPE, "i")), ClassNode.EMPTY_ARRAY, body);
    }

    private void createCopyWith(ClassNode cNode, List<PropertyNode> pList) {
        ArgumentListExpression args = new ArgumentListExpression();
        Parameter mapParam = GeneralUtils.param(GenericsUtils.nonGeneric(ClassHelper.MAP_TYPE), NAMED_ARGS);
        VariableExpression mapArg = GeneralUtils.varX(mapParam);
        for (PropertyNode pNode : pList) {
            String name = pNode.getName();
            args.addExpression(GeneralUtils.ternaryX(GeneralUtils.callX((Expression)mapArg, "containsKey", (Expression)GeneralUtils.args(GeneralUtils.constX(name))), GeneralUtils.propX((Expression)mapArg, name), GeneralUtils.thisPropX(true, name)));
            ClassNode pType = pNode.getType();
            ClassNode type = pType.getPlainNodeReference();
            type.setGenericsPlaceHolder(pType.isGenericsPlaceHolder());
            type.setGenericsTypes(pType.getGenericsTypes());
            AnnotationNode namedParam = new AnnotationNode(NAMED_PARAM_TYPE);
            namedParam.addMember("value", GeneralUtils.constX(name));
            namedParam.addMember("type", GeneralUtils.classX(type));
            namedParam.addMember("required", GeneralUtils.constX(false, true));
            mapParam.addAnnotation(namedParam);
        }
        Statement body = GeneralUtils.returnS(GeneralUtils.ctorX(cNode.getPlainNodeReference(), args));
        ClassNodeUtils.addGeneratedMethod(cNode, COPY_WITH, 17, cNode.getPlainNodeReference(), GeneralUtils.params(mapParam), ClassNode.EMPTY_ARRAY, body);
    }

    private void createRecordToString(ClassNode cNode) {
        String desc = BytecodeHelper.getMethodDescriptor(ClassHelper.STRING_TYPE, new ClassNode[]{cNode});
        Statement body = GeneralUtils.stmt(GeneralUtils.bytecodeX(ClassHelper.STRING_TYPE, mv -> {
            mv.visitVarInsn(25, 0);
            mv.visitInvokeDynamicInsn("toString", desc, this.createBootstrapMethod(), this.createBootstrapMethodArguments(cNode));
            mv.visitInsn(176);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }));
        ClassNodeUtils.addGeneratedMethod(cNode, "toString", 17, ClassHelper.STRING_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private void createRecordEquals(ClassNode cNode) {
        String desc = BytecodeHelper.getMethodDescriptor(ClassHelper.boolean_TYPE, new ClassNode[]{cNode, ClassHelper.OBJECT_TYPE});
        Statement body = GeneralUtils.stmt(GeneralUtils.bytecodeX(ClassHelper.boolean_TYPE, mv -> {
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(25, 1);
            mv.visitInvokeDynamicInsn("equals", desc, this.createBootstrapMethod(), this.createBootstrapMethodArguments(cNode));
            mv.visitInsn(172);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }));
        ClassNodeUtils.addGeneratedMethod(cNode, "equals", 17, ClassHelper.boolean_TYPE, GeneralUtils.params(GeneralUtils.param(ClassHelper.OBJECT_TYPE, "other")), ClassNode.EMPTY_ARRAY, body);
    }

    private void createRecordHashCode(ClassNode cNode) {
        String desc = BytecodeHelper.getMethodDescriptor(ClassHelper.int_TYPE, new ClassNode[]{cNode});
        Statement body = GeneralUtils.stmt(GeneralUtils.bytecodeX(ClassHelper.int_TYPE, mv -> {
            mv.visitVarInsn(25, 0);
            mv.visitInvokeDynamicInsn("hashCode", desc, this.createBootstrapMethod(), this.createBootstrapMethodArguments(cNode));
            mv.visitInsn(172);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }));
        ClassNodeUtils.addGeneratedMethod(cNode, "hashCode", 17, ClassHelper.int_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, body);
    }

    private Object[] createBootstrapMethodArguments(ClassNode cNode) {
        String internalName = cNode.getName().replace('.', '/');
        String names = cNode.getRecordComponents().stream().map(RecordComponentNode::getName).collect(Collectors.joining(";"));
        LinkedList<Object> args = new LinkedList<Object>();
        args.add(Type.getType(BytecodeHelper.getTypeDescription(cNode)));
        args.add(names);
        cNode.getRecordComponents().stream().forEach(rcn -> args.add(this.createFieldHandle((RecordComponentNode)rcn, internalName)));
        return args.toArray();
    }

    private Object createFieldHandle(RecordComponentNode rcn, String cName) {
        return new Handle(1, cName, rcn.getName(), BytecodeHelper.getTypeDescription(rcn.getType()), false);
    }

    private Handle createBootstrapMethod() {
        return new Handle(6, "java/lang/runtime/ObjectMethods", "bootstrap", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/TypeDescriptor;Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/invoke/MethodHandle;)Ljava/lang/Object;", false);
    }

    private static RecordTypeMode getMode(AnnotationNode node, String name) {
        ClassExpression ce;
        PropertyExpression prop;
        Expression oe;
        Expression member;
        if (node != null && (member = node.getMember(name)) instanceof PropertyExpression && (oe = (prop = (PropertyExpression)member).getObjectExpression()) instanceof ClassExpression && (ce = (ClassExpression)oe).getType().getName().equals("groovy.transform.RecordTypeMode")) {
            return RecordTypeMode.valueOf(prop.getPropertyAsString());
        }
        return null;
    }

    private boolean unsupportedTupleAttribute(AnnotationNode anno, String memberName) {
        if (this.getMemberValue(anno, memberName) != null) {
            String tname = TupleConstructorASTTransformation.MY_TYPE_NAME;
            this.addError("Error during " + MY_TYPE_NAME + " processing: Annotation attribute '" + memberName + "' not supported for " + tname + " when used with " + MY_TYPE_NAME, anno);
            return true;
        }
        return false;
    }

    private static void makeInnerRecordStatic(ClassNode cNode) {
        if (cNode instanceof InnerClassNode) {
            cNode.setModifiers(cNode.getModifiers() | 8);
        }
    }

    private static void makeClassFinal(AbstractASTTransformation xform, ClassNode cNode) {
        int modifiers = cNode.getModifiers();
        if ((modifiers & 0x10) == 0) {
            if ((modifiers & 0x1400) == 5120) {
                xform.addError("Error during " + MY_TYPE_NAME + " processing: annotation found on inappropriate class " + cNode.getName(), cNode);
                return;
            }
            cNode.setModifiers(modifiers | 0x10);
        }
    }

    private static void ensureNotPublic(AbstractASTTransformation xform, String cNode, FieldNode fNode) {
        String fName = fNode.getName();
        if (!(!fNode.isPublic() || fName.contains("$") || fNode.isStatic() && fNode.isFinal())) {
            xform.addError("Public field '" + fName + "' not allowed for " + MY_TYPE_NAME + " class '" + cNode + "'.", fNode);
        }
    }

    private static void adjustPropertyForShallowImmutability(ClassNode cNode, PropertyNode pNode, PropertyHandler handler) {
        FieldNode fNode = pNode.getField();
        fNode.setModifiers(pNode.getModifiers() & 0xFFFFFFFE | 0x10 | 2);
        boolean isGetterDefined = cNode.getDeclaredMethods(pNode.getName()).stream().anyMatch(MethodNodeUtils::isGetterCandidate);
        if (!isGetterDefined) {
            pNode.setGetterName(pNode.getName());
            Statement getter = handler.createPropGetter(pNode);
            if (getter != null) {
                pNode.setGetterBlock(getter);
            }
        }
    }

    @Override
    public void setCompilationUnit(CompilationUnit unit) {
        this.compilationUnit = unit;
    }
}

