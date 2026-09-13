/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import groovy.lang.GroovyRuntimeException;
import groovy.transform.Sealed;
import groovyjarjarasm.asm.AnnotationVisitor;
import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.FieldVisitor;
import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.RecordComponentVisitor;
import groovyjarjarasm.asm.Type;
import groovyjarjarasm.asm.TypePath;
import groovyjarjarasm.asm.TypeReference;
import groovyjarjarasm.asm.util.TraceMethodVisitor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.apache.groovy.io.StringBuilderWriter;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.AnnotatedNode;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.InterfaceHelperClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.ModuleNode;
import org.codehaus.groovy.ast.PackageNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.RecordComponentNode;
import org.codehaus.groovy.ast.expr.AnnotationConstantExpression;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.BitwiseNegationExpression;
import org.codehaus.groovy.ast.expr.BooleanExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ClosureListExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.DeclarationExpression;
import org.codehaus.groovy.ast.expr.EmptyExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.GStringExpression;
import org.codehaus.groovy.ast.expr.LambdaExpression;
import org.codehaus.groovy.ast.expr.ListExpression;
import org.codehaus.groovy.ast.expr.MapEntryExpression;
import org.codehaus.groovy.ast.expr.MapExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.MethodPointerExpression;
import org.codehaus.groovy.ast.expr.MethodReferenceExpression;
import org.codehaus.groovy.ast.expr.NotExpression;
import org.codehaus.groovy.ast.expr.PostfixExpression;
import org.codehaus.groovy.ast.expr.PrefixExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.RangeExpression;
import org.codehaus.groovy.ast.expr.SpreadExpression;
import org.codehaus.groovy.ast.expr.SpreadMapExpression;
import org.codehaus.groovy.ast.expr.StaticMethodCallExpression;
import org.codehaus.groovy.ast.expr.TernaryExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.UnaryMinusExpression;
import org.codehaus.groovy.ast.expr.UnaryPlusExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.AssertStatement;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.BreakStatement;
import org.codehaus.groovy.ast.stmt.CaseStatement;
import org.codehaus.groovy.ast.stmt.CatchStatement;
import org.codehaus.groovy.ast.stmt.ContinueStatement;
import org.codehaus.groovy.ast.stmt.DoWhileStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.stmt.IfStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.stmt.SwitchStatement;
import org.codehaus.groovy.ast.stmt.SynchronizedStatement;
import org.codehaus.groovy.ast.stmt.ThrowStatement;
import org.codehaus.groovy.ast.stmt.TryCatchStatement;
import org.codehaus.groovy.ast.stmt.WhileStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.ParameterUtils;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.classgen.BytecodeExpression;
import org.codehaus.groovy.classgen.BytecodeInstruction;
import org.codehaus.groovy.classgen.BytecodeSequence;
import org.codehaus.groovy.classgen.ClassGenerator;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.BytecodeVariable;
import org.codehaus.groovy.classgen.asm.MethodCaller;
import org.codehaus.groovy.classgen.asm.MethodCallerMultiAdapter;
import org.codehaus.groovy.classgen.asm.MopWriter;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.OptimizingStatementWriter;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.WriterControllerFactory;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.runtime.ScriptBytecodeAdapter;
import org.codehaus.groovy.syntax.RuntimeParserException;
import org.codehaus.groovy.transform.SealedASTTransformation;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;

public class AsmClassGenerator
extends ClassGenerator {
    public static final MethodCallerMultiAdapter setField = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "setField", false, false);
    public static final MethodCallerMultiAdapter getField = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "getField", false, false);
    private static final MethodCallerMultiAdapter setFieldOnSuper = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "setFieldOnSuper", false, false);
    private static final MethodCallerMultiAdapter getFieldOnSuper = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "getFieldOnSuper", false, false);
    public static final MethodCallerMultiAdapter setGroovyObjectField = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "setGroovyObjectField", false, false);
    public static final MethodCallerMultiAdapter getGroovyObjectField = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "getGroovyObjectField", false, false);
    public static final MethodCallerMultiAdapter setProperty = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "setProperty", false, false);
    private static final MethodCallerMultiAdapter getProperty = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "getProperty", false, false);
    private static final MethodCallerMultiAdapter setPropertyOnSuper = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "setPropertyOnSuper", false, false);
    private static final MethodCallerMultiAdapter getPropertyOnSuper = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "getPropertyOnSuper", false, false);
    private static final MethodCallerMultiAdapter setGroovyObjectProperty = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "setGroovyObjectProperty", false, false);
    private static final MethodCallerMultiAdapter getGroovyObjectProperty = MethodCallerMultiAdapter.newStatic(ScriptBytecodeAdapter.class, "getGroovyObjectProperty", false, false);
    private static final MethodCaller spreadMap = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "spreadMap");
    private static final MethodCaller despreadList = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "despreadList");
    private static final MethodCaller createMapMethod = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "createMap");
    private static final MethodCaller createListMethod = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "createList");
    private static final MethodCaller createRangeMethod = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "createRange", 4);
    private static final MethodCaller createPojoWrapperMethod = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "createPojoWrapper");
    private static final MethodCaller createGroovyObjectWrapperMethod = MethodCaller.newStatic(ScriptBytecodeAdapter.class, "createGroovyObjectWrapper");
    private final Map<String, ClassNode> referencedClasses = new HashMap<String, ClassNode>();
    private boolean passingParams;
    public static final boolean CREATE_DEBUG_INFO = true;
    public static final boolean CREATE_LINE_NUMBER_INFO = true;
    public static final boolean ASM_DEBUG = false;
    public static final String MINIMUM_BYTECODE_VERSION = "_MINIMUM_BYTECODE_VERSION";
    private WriterController controller;
    private ASTNode currentASTNode;
    private final SourceUnit source;
    private final GeneratorContext context;
    private ClassVisitor classVisitor;
    private final String sourceFile;

    public AsmClassGenerator(SourceUnit source, GeneratorContext context, ClassVisitor classVisitor, String sourceFile) {
        this.source = source;
        this.context = context;
        this.classVisitor = classVisitor;
        this.sourceFile = sourceFile;
    }

    @Override
    public SourceUnit getSourceUnit() {
        return this.source;
    }

    public WriterController getController() {
        return this.controller;
    }

    @Override
    public void visitClass(ClassNode classNode) {
        this.referencedClasses.clear();
        WriterControllerFactory factory = (WriterControllerFactory)classNode.getNodeMetaData(WriterControllerFactory.class);
        this.controller = new WriterController();
        if (factory != null) {
            this.controller = factory.makeController(this.controller);
        }
        this.controller.init(this, this.context, this.classVisitor, classNode);
        if (this.controller.shouldOptimizeForInt() || factory != null) {
            OptimizingStatementWriter.setNodeMeta(this.controller.getTypeChooser(), classNode);
        }
        this.classVisitor = this.controller.getClassVisitor();
        try {
            int minVersion;
            int bytecodeVersion = this.controller.getBytecodeVersion();
            Object min = classNode.getNodeMetaData(MINIMUM_BYTECODE_VERSION);
            if (min instanceof Integer && (bytecodeVersion ^ 0xFFFF0000) < (minVersion = ((Integer)min).intValue())) {
                bytecodeVersion = minVersion;
            }
            this.classVisitor.visit(bytecodeVersion, AsmClassGenerator.adjustedClassModifiersForClassWriting(classNode), this.controller.getInternalClassName(), BytecodeHelper.getGenericsSignature(classNode), this.controller.getInternalBaseClassName(), BytecodeHelper.getClassInternalNames(classNode.getInterfaces()));
            this.classVisitor.visitSource(this.sourceFile, null);
            if (classNode instanceof InnerClassNode) {
                this.makeInnerClassEntry(classNode);
                MethodNode enclosingMethod = classNode.getEnclosingMethod();
                if (enclosingMethod != null) {
                    this.classVisitor.visitOuterClass(BytecodeHelper.getClassInternalName(classNode.getOuterClass().getName()), enclosingMethod.getName(), BytecodeHelper.getMethodDescriptor(enclosingMethod));
                }
            }
            if (classNode.getName().endsWith("package-info")) {
                PackageNode packageNode = classNode.getPackage();
                if (packageNode != null) {
                    this.visitAnnotations(classNode, packageNode, this.classVisitor);
                }
            } else {
                this.visitAnnotations(classNode, this.classVisitor);
                this.visitTypeParameters(classNode, (Object)this.classVisitor);
                this.visitType(classNode.getUnresolvedSuperClass(), this.classVisitor, TypeReference.newSuperTypeReference(-1), "", true);
                ClassNode[] interfaces = classNode.getInterfaces();
                for (int i = 0; i < interfaces.length; ++i) {
                    this.visitType(interfaces[i], this.classVisitor, TypeReference.newSuperTypeReference(i), "", true);
                }
                if (classNode.isInterface()) {
                    String outerClassName = classNode.getName();
                    String name = outerClassName + "$" + this.context.getNextInnerClassIdx();
                    this.controller.setInterfaceClassLoadingClass(new InterfaceHelperClassNode(Optional.ofNullable(classNode.getOuterClass()).orElse(classNode), name, 4136, ClassHelper.OBJECT_TYPE, this.controller.getCallSiteWriter().getCallSites()));
                    super.visitClass(classNode);
                    this.createInterfaceSyntheticStaticFields();
                } else {
                    super.visitClass(classNode);
                    MopWriter.Factory mopWriterFactory = (MopWriter.Factory)classNode.getNodeMetaData(MopWriter.Factory.class);
                    if (mopWriterFactory == null) {
                        mopWriterFactory = MopWriter.FACTORY;
                    }
                    MopWriter mopWriter = mopWriterFactory.create(this.controller);
                    mopWriter.createMopMethods();
                    this.controller.getCallSiteWriter().generateCallSiteArray();
                    this.createSyntheticStaticFields();
                }
            }
            Iterator<ClassNode> it = classNode.getInnerClasses();
            while (it.hasNext()) {
                this.makeInnerClassEntry(it.next());
            }
            if (SealedASTTransformation.sealedNative(classNode)) {
                for (ClassNode sub : classNode.getPermittedSubclasses()) {
                    this.classVisitor.visitPermittedSubclass(BytecodeHelper.getClassInternalName(sub));
                }
            }
            if (classNode.isRecord()) {
                this.visitRecordComponents(classNode);
            }
            this.classVisitor.visitEnd();
        }
        catch (GroovyRuntimeException e) {
            e.setModule(classNode.getModule());
            throw e;
        }
        catch (NegativeArraySizeException | NullPointerException e) {
            String m = e.getClass().getSimpleName() + " while processing " + this.sourceFile;
            GroovyRuntimeException gre = new GroovyRuntimeException(m, e);
            gre.setModule(classNode.getModule());
            throw gre;
        }
    }

    private void visitRecordComponents(ClassNode classNode) {
        for (RecordComponentNode recordComponent : classNode.getRecordComponents()) {
            ClassNode type = recordComponent.getType();
            RecordComponentVisitor visitor = this.classVisitor.visitRecordComponent(recordComponent.getName(), BytecodeHelper.getTypeDescription(type), BytecodeHelper.getTypeGenericsSignature(type));
            this.visitAnnotations(recordComponent, visitor);
            TypeReference typeRef = new TypeReference(0x13000000);
            this.visitTypeAnnotations(type, visitor, typeRef, "", true);
            visitor.visitEnd();
        }
    }

    private void maybeInnerClassEntry(ClassNode classNode) {
        if (classNode.getOuterClass() != null) {
            this.makeInnerClassEntry(classNode);
        }
    }

    private void makeInnerClassEntry(ClassNode innerClass) {
        ClassNode outerClass = innerClass.getOuterClass();
        this.maybeInnerClassEntry(outerClass);
        String innerClassName = innerClass.getName();
        String innerClassInternalName = BytecodeHelper.getClassInternalName(innerClassName);
        int index = innerClassName.lastIndexOf(36);
        if (index >= 0) {
            innerClassName = innerClassName.substring(index + 1);
        }
        String outerClassInternalName = BytecodeHelper.getClassInternalName(outerClass.getName());
        if (innerClass.getEnclosingMethod() != null) {
            outerClassInternalName = null;
            if (innerClass instanceof InnerClassNode && ((InnerClassNode)innerClass).isAnonymous()) {
                innerClassName = null;
            }
        }
        int modifiers = AsmClassGenerator.adjustedClassModifiersForInnerClassTable(innerClass);
        this.classVisitor.visitInnerClass(innerClassInternalName, outerClassInternalName, innerClassName, modifiers);
    }

    private static int adjustedClassModifiersForInnerClassTable(ClassNode classNode) {
        return AsmClassGenerator.fixInterfaceModifiers(classNode, classNode.getModifiers()) & 0xFFFFFFDF;
    }

    private static int fixInterfaceModifiers(ClassNode classNode, int modifiers) {
        if (classNode.isInterface()) {
            modifiers &= 0xFFFFBFFF;
            modifiers &= 0xFFFFFFEF;
        }
        return modifiers;
    }

    private static int fixInnerClassModifiers(ClassNode classNode, int modifiers) {
        if (classNode.getOuterClass() != null) {
            if ((modifiers & 2) != 0) {
                modifiers &= 0xFFFFFFFD;
            }
            if ((modifiers & 4) != 0) {
                modifiers = modifiers & 0xFFFFFFFB | 1;
            }
        }
        return modifiers;
    }

    private static int adjustedClassModifiersForClassWriting(ClassNode classNode) {
        int modifiers = classNode.getModifiers();
        boolean needsSuper = !classNode.isInterface();
        modifiers = needsSuper ? modifiers | 0x20 : modifiers & 0xFFFFFFDF;
        modifiers &= 0xFFFFFFF7;
        modifiers = AsmClassGenerator.fixInnerClassModifiers(classNode, modifiers);
        modifiers = AsmClassGenerator.fixInterfaceModifiers(classNode, modifiers);
        return modifiers;
    }

    @Override
    protected void visitConstructorOrMethod(MethodNode node, boolean isConstructor) {
        int i;
        Parameter[] parameters = node.getParameters();
        MethodVisitor mv = this.classVisitor.visitMethod(node.getModifiers() | (ParameterUtils.isVargs(parameters) ? 128 : 0), node.getName(), BytecodeHelper.getMethodDescriptor(node.getReturnType(), parameters), BytecodeHelper.getGenericsMethodSignature(node), AsmClassGenerator.buildExceptions(node.getExceptions()));
        this.controller.setMethodVisitor(mv);
        this.controller.resetLineNumber();
        this.visitAnnotations(node, mv);
        this.visitTypeParameters(node, (Object)mv);
        if (!(node instanceof ConstructorNode)) {
            this.visitType(node.getReturnType(), mv, TypeReference.newTypeReference(20), "", true);
        }
        if (Optional.ofNullable(this.controller.getClassNode().getCompileUnit()).orElseGet(this.context::getCompileUnit).getConfig().getParameters()) {
            for (Parameter parameter : parameters) {
                mv.visitParameter(parameter.getName(), parameter.getModifiers());
            }
        }
        int n = parameters.length;
        for (i = 0; i < n; ++i) {
            this.visitParameterAnnotations(parameters[i], i, mv);
            ClassNode paramType = parameters[i].getType();
            if (paramType.isGenericsPlaceHolder()) {
                this.visitTypeAnnotations(paramType, mv, TypeReference.newFormalParameterReference(i), "", true);
                continue;
            }
            this.visitType(parameters[i].getType(), mv, TypeReference.newFormalParameterReference(i), "", true);
        }
        if (node.getExceptions() != null) {
            n = node.getExceptions().length;
            for (i = 0; i < n; ++i) {
                this.visitType(node.getExceptions()[i], mv, TypeReference.newExceptionReference(i), "", true);
            }
        }
        if (this.controller.getClassNode().isAnnotationDefinition() && !node.isStaticConstructor()) {
            this.visitAnnotationDefault(node, mv);
        } else if (!node.isAbstract()) {
            BytecodeInstruction instruction;
            mv.visitCode();
            Statement code = node.getCode();
            if (code instanceof BytecodeSequence && (instruction = ((BytecodeSequence)code).getBytecodeInstruction()) != null) {
                instruction.visit(mv);
            } else {
                this.visitStdMethod(node, isConstructor, parameters, code);
            }
            try {
                mv.visitMaxs(0, 0);
            }
            catch (Exception e) {
                StringBuilderWriter writer = null;
                if (mv instanceof TraceMethodVisitor) {
                    writer = new StringBuilderWriter();
                    PrintWriter p = new PrintWriter(writer);
                    ((TraceMethodVisitor)mv).p.print(p);
                    p.flush();
                }
                StringBuilder message = new StringBuilder(64);
                message.append("ASM reporting processing error for ");
                message.append(this.controller.getClassNode().toString(false)).append('#').append(node.getName());
                message.append(" with signature ").append(node.getTypeDescriptor());
                message.append(" in ").append(this.sourceFile).append(':').append(node.getLineNumber());
                if (writer != null) {
                    message.append("\nLast known generated bytecode in last generated method or constructor:\n");
                    message.append(writer);
                }
                throw new GroovyRuntimeException(message.toString(), e);
            }
        }
        mv.visitEnd();
    }

    private void visitStdMethod(MethodNode node, boolean isConstructor, Parameter[] parameters, Statement code) {
        this.controller.getCompileStack().init(node.getVariableScope(), parameters);
        this.controller.getCallSiteWriter().makeSiteEntry();
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (isConstructor && (code == null || !((ConstructorNode)node).firstStatementIsSpecialConstructorCall())) {
            boolean hasCallToSuper = false;
            if (code != null && this.controller.getClassNode().getOuterClass() != null && code instanceof BlockStatement) {
                hasCallToSuper = ((BlockStatement)code).getStatements().stream().map(statement -> statement instanceof ExpressionStatement ? ((ExpressionStatement)statement).getExpression() : null).anyMatch(expression -> expression instanceof ConstructorCallExpression && ((ConstructorCallExpression)expression).isSuperCall());
            }
            if (!hasCallToSuper) {
                if (code != null) {
                    this.controller.visitLineNumber(code.getLineNumber());
                }
                mv.visitVarInsn(25, 0);
                mv.visitMethodInsn(183, this.controller.getInternalBaseClassName(), "<init>", "()V", false);
            }
        }
        if (code != null) {
            code.visit(this);
        }
        if (code == null || GeneralUtils.maybeFallsThrough(code)) {
            if (code != null) {
                this.controller.visitLineNumber(code.getLastLineNumber());
            }
            if (node.isVoidMethod()) {
                mv.visitInsn(177);
            } else {
                ClassNode type = node.getReturnType();
                if (ClassHelper.isPrimitiveType(type)) {
                    mv.visitLdcInsn(0);
                    OperandStack operandStack = this.controller.getOperandStack();
                    operandStack.push(ClassHelper.int_TYPE);
                    operandStack.doGroovyCast(type);
                    BytecodeHelper.doReturn(mv, type);
                    operandStack.remove(1);
                } else {
                    mv.visitInsn(1);
                    BytecodeHelper.doReturn(mv, type);
                }
            }
        }
        this.controller.getCompileStack().clear();
    }

    private void visitAnnotationDefaultExpression(AnnotationVisitor av, ClassNode type, Expression exp) {
        if (exp instanceof ClosureExpression) {
            ClassNode closureClass = this.controller.getClosureWriter().getOrAddClosureClass((ClosureExpression)exp, 1);
            Type t = Type.getType(BytecodeHelper.getTypeDescription(closureClass));
            av.visit(null, t);
        } else if (type.isArray()) {
            AnnotationVisitor avl = av.visitArray(null);
            ClassNode componentType = type.getComponentType();
            if (exp instanceof ListExpression) {
                ListExpression list = (ListExpression)exp;
                for (Expression lExp : list.getExpressions()) {
                    this.visitAnnotationDefaultExpression(avl, componentType, lExp);
                }
            } else {
                this.visitAnnotationDefaultExpression(avl, componentType, exp);
            }
        } else if (ClassHelper.isPrimitiveType(type) || ClassHelper.isStringType(type)) {
            ConstantExpression constExp = (ConstantExpression)exp;
            av.visit(null, constExp.getValue());
        } else if (ClassHelper.isClassType(type)) {
            ClassNode clazz = exp.getType();
            Type t = Type.getType(BytecodeHelper.getTypeDescription(clazz));
            av.visit(null, t);
        } else if (type.isDerivedFrom(ClassHelper.Enum_Type)) {
            PropertyExpression pExp = (PropertyExpression)exp;
            ClassExpression cExp = (ClassExpression)pExp.getObjectExpression();
            String desc = BytecodeHelper.getTypeDescription(cExp.getType());
            String name = pExp.getPropertyAsString();
            av.visitEnum(null, desc, name);
        } else if (type.implementsInterface(ClassHelper.Annotation_TYPE)) {
            AnnotationConstantExpression avExp = (AnnotationConstantExpression)exp;
            AnnotationNode value = (AnnotationNode)avExp.getValue();
            AnnotationVisitor avc = av.visitAnnotation(null, BytecodeHelper.getTypeDescription(avExp.getType()));
            this.visitAnnotationAttributes(value, avc);
        } else {
            throw new GroovyBugError("unexpected annotation type " + type.getName());
        }
        av.visitEnd();
    }

    private void visitAnnotationDefault(MethodNode node, MethodVisitor mv) {
        if (!node.hasAnnotationDefault()) {
            return;
        }
        Expression exp = ((ReturnStatement)node.getCode()).getExpression();
        AnnotationVisitor av = mv.visitAnnotationDefault();
        this.visitAnnotationDefaultExpression(av, node.getReturnType(), exp);
    }

    @Override
    public void visitConstructor(ConstructorNode node) {
        this.controller.setConstructorNode(node);
        super.visitConstructor(node);
    }

    @Override
    public void visitMethod(MethodNode node) {
        this.controller.setMethodNode(node);
        super.visitMethod(node);
    }

    @Override
    public void visitField(FieldNode fieldNode) {
        Integer value;
        ConstantExpression cexp;
        this.onLineNumber(fieldNode, "visitField: " + fieldNode.getName());
        ClassNode t = fieldNode.getType();
        String signature = BytecodeHelper.getGenericsBounds(t);
        Expression initialValueExpression = fieldNode.getInitialValueExpression();
        ConstantExpression constantExpression = cexp = initialValueExpression instanceof ConstantExpression ? (ConstantExpression)initialValueExpression : null;
        if (cexp != null) {
            cexp = Verifier.transformToPrimitiveConstantIfPossible(cexp);
        }
        Integer n = value = cexp != null && ClassHelper.isStaticConstantInitializerType(cexp.getType()) && cexp.getType().equals(t) && fieldNode.isStatic() && fieldNode.isFinal() ? cexp.getValue() : null;
        if (value != null) {
            if (ClassHelper.isPrimitiveByte(t) || ClassHelper.isPrimitiveShort(t)) {
                value = ((Number)value).intValue();
            } else if (ClassHelper.isPrimitiveChar(t)) {
                value = ((Character)((Object)value)).charValue();
            }
        }
        FieldVisitor fv = this.classVisitor.visitField(fieldNode.getModifiers(), fieldNode.getName(), BytecodeHelper.getTypeDescription(t), signature, value);
        this.visitAnnotations(fieldNode, fv);
        this.visitType(fieldNode.getType(), fv, TypeReference.newTypeReference(19), "", true);
        fv.visitEnd();
    }

    @Override
    public void visitProperty(PropertyNode statement) {
        this.onLineNumber(statement, "visitProperty:" + statement.getField().getName());
        this.controller.setMethodNode(null);
    }

    @Override
    protected void visitStatement(Statement statement) {
        throw new GroovyBugError("visitStatement should not be visited here.");
    }

    @Override
    public void visitBlockStatement(BlockStatement statement) {
        this.controller.getStatementWriter().writeBlockStatement(statement);
    }

    @Override
    public void visitForLoop(ForStatement statement) {
        this.controller.getStatementWriter().writeForStatement(statement);
    }

    @Override
    public void visitWhileLoop(WhileStatement statement) {
        this.controller.getStatementWriter().writeWhileLoop(statement);
    }

    @Override
    public void visitDoWhileLoop(DoWhileStatement statement) {
        this.controller.getStatementWriter().writeDoWhileLoop(statement);
    }

    @Override
    public void visitIfElse(IfStatement statement) {
        this.controller.getStatementWriter().writeIfElse(statement);
    }

    @Override
    public void visitAssertStatement(AssertStatement statement) {
        this.controller.getStatementWriter().writeAssert(statement);
    }

    @Override
    public void visitTryCatchFinally(TryCatchStatement statement) {
        this.controller.getStatementWriter().writeTryCatchFinally(statement);
    }

    @Override
    public void visitCatchStatement(CatchStatement statement) {
        this.maybeInnerClassEntry(statement.getExceptionType());
        statement.getCode().visit(this);
    }

    @Override
    public void visitSwitch(SwitchStatement statement) {
        this.controller.getStatementWriter().writeSwitch(statement);
    }

    @Override
    public void visitCaseStatement(CaseStatement statement) {
    }

    @Override
    public void visitBreakStatement(BreakStatement statement) {
        this.controller.getStatementWriter().writeBreak(statement);
    }

    @Override
    public void visitContinueStatement(ContinueStatement statement) {
        this.controller.getStatementWriter().writeContinue(statement);
    }

    @Override
    public void visitSynchronizedStatement(SynchronizedStatement statement) {
        this.controller.getStatementWriter().writeSynchronized(statement);
    }

    @Override
    public void visitThrowStatement(ThrowStatement statement) {
        this.controller.getStatementWriter().writeThrow(statement);
    }

    @Override
    public void visitReturnStatement(ReturnStatement statement) {
        this.controller.getStatementWriter().writeReturn(statement);
    }

    @Override
    public void visitExpressionStatement(ExpressionStatement statement) {
        this.controller.getStatementWriter().writeExpressionStatement(statement);
    }

    @Override
    public void visitTernaryExpression(TernaryExpression expression) {
        this.onLineNumber(expression, "visitTernaryExpression");
        this.controller.getBinaryExpressionHelper().evaluateTernary(expression);
        this.doPostVisit(expression);
    }

    @Override
    public void visitDeclarationExpression(DeclarationExpression expression) {
        this.onLineNumber(expression, "visitDeclarationExpression: " + expression.getText());
        this.controller.getBinaryExpressionHelper().evaluateEqual(expression, true);
    }

    @Override
    public void visitBinaryExpression(BinaryExpression expression) {
        this.onLineNumber(expression, "visitBinaryExpression: " + expression.getOperation().getText());
        this.controller.getBinaryExpressionHelper().eval(expression);
        this.controller.getAssertionWriter().record(expression.getOperation());
        this.doPostVisit(expression);
    }

    @Override
    public void visitPostfixExpression(PostfixExpression expression) {
        this.controller.getBinaryExpressionHelper().evaluatePostfixMethod(expression);
        this.controller.getAssertionWriter().record(expression);
    }

    @Override
    public void visitPrefixExpression(PrefixExpression expression) {
        this.controller.getBinaryExpressionHelper().evaluatePrefixMethod(expression);
        this.controller.getAssertionWriter().record(expression);
    }

    @Override
    public void visitClosureExpression(ClosureExpression expression) {
        this.controller.getClosureWriter().writeClosure(expression);
    }

    @Override
    public void visitLambdaExpression(LambdaExpression expression) {
        this.controller.getLambdaWriter().writeLambda(expression);
    }

    protected void loadThisOrOwner() {
        ClassNode classNode = this.controller.getClassNode();
        if (classNode.getOuterClass() == null) {
            this.loadThis(VariableExpression.THIS_EXPRESSION);
        } else {
            GeneralUtils.fieldX(classNode.getDeclaredField("owner")).visit(this);
        }
    }

    @Override
    public void visitConstantExpression(ConstantExpression expression) {
        String constantName = expression.getConstantName();
        OperandStack operandStack = this.controller.getOperandStack();
        if (this.controller.isStaticConstructor() || constantName == null) {
            operandStack.pushConstant(expression);
        } else {
            this.controller.getMethodVisitor().visitFieldInsn(178, this.controller.getInternalClassName(), constantName, BytecodeHelper.getTypeDescription(expression.getType()));
            operandStack.push(expression.getType());
        }
    }

    @Override
    public void visitSpreadExpression(SpreadExpression expression) {
        throw new GroovyBugError("SpreadExpression should not be visited here");
    }

    @Override
    public void visitSpreadMapExpression(SpreadMapExpression expression) {
        GeneralUtils.callX(ClassHelper.make(Collections.class), "emptyMap").visit(this);
        spreadMap.call(this.controller.getMethodVisitor());
        this.controller.getOperandStack().replace(ClassHelper.OBJECT_TYPE);
    }

    @Override
    public void visitMethodPointerExpression(MethodPointerExpression expression) {
        this.controller.getMethodPointerExpressionWriter().writeMethodPointerExpression(expression);
    }

    @Override
    public void visitMethodReferenceExpression(MethodReferenceExpression expression) {
        this.controller.getMethodReferenceExpressionWriter().writeMethodReferenceExpression(expression);
    }

    @Override
    public void visitUnaryMinusExpression(UnaryMinusExpression expression) {
        this.controller.getUnaryExpressionHelper().writeUnaryMinus(expression);
    }

    @Override
    public void visitUnaryPlusExpression(UnaryPlusExpression expression) {
        this.controller.getUnaryExpressionHelper().writeUnaryPlus(expression);
    }

    @Override
    public void visitBitwiseNegationExpression(BitwiseNegationExpression expression) {
        this.controller.getUnaryExpressionHelper().writeBitwiseNegate(expression);
    }

    @Override
    public void visitCastExpression(CastExpression castExpression) {
        Expression subExpression = castExpression.getExpression();
        subExpression.visit(this);
        ClassNode type = castExpression.getType();
        if (ClassHelper.isObjectType(type)) {
            return;
        }
        this.maybeInnerClassEntry(type);
        OperandStack operandStack = this.controller.getOperandStack();
        if (castExpression.isCoerce()) {
            operandStack.doAsType(type);
        } else if (ExpressionUtils.isNullConstant(subExpression) && !ClassHelper.isPrimitiveType(type)) {
            operandStack.replace(type);
        } else {
            ClassNode subExprType = this.controller.getTypeChooser().resolveType(subExpression, this.controller.getClassNode());
            if (castExpression.isStrict() || !ClassHelper.isPrimitiveType(type) && WideningCategories.implementsInterfaceOrSubclassOf(subExprType, type)) {
                BytecodeHelper.doCast(this.controller.getMethodVisitor(), type);
                operandStack.replace(type);
            } else {
                operandStack.doGroovyCast(type);
            }
        }
    }

    @Override
    public void visitNotExpression(NotExpression expression) {
        this.controller.getUnaryExpressionHelper().writeNotExpression(expression);
    }

    @Override
    public void visitBooleanExpression(BooleanExpression expression) {
        OperandStack operandStack = this.controller.getOperandStack();
        int mark = operandStack.getStackLength();
        expression.getExpression().visit(this);
        operandStack.castToBool(mark, true);
    }

    @Override
    public void visitMethodCallExpression(MethodCallExpression call) {
        this.onLineNumber(call, "visitMethodCallExpression: \"" + call.getMethod() + "\":");
        this.controller.getInvocationWriter().writeInvokeMethod(call);
        this.controller.getAssertionWriter().record(call.getMethod());
    }

    @Override
    public void visitStaticMethodCallExpression(StaticMethodCallExpression call) {
        this.onLineNumber(call, "visitStaticMethodCallExpression: \"" + call.getMethod() + "\":");
        this.controller.getInvocationWriter().writeInvokeStaticMethod(call);
        this.controller.getAssertionWriter().record(call);
        this.maybeInnerClassEntry(call.getOwnerType());
    }

    @Override
    public void visitConstructorCallExpression(ConstructorCallExpression call) {
        this.onLineNumber(call, "visitConstructorCallExpression: \"" + call.getType().getName() + "\":");
        if (call.isSpecialCall()) {
            this.controller.getInvocationWriter().writeSpecialConstructorCall(call);
            return;
        }
        this.controller.getInvocationWriter().writeInvokeConstructor(call);
        this.controller.getAssertionWriter().record(call);
        this.maybeInnerClassEntry(call.getType());
    }

    private static String makeFieldClassName(ClassNode type) {
        String internalName = BytecodeHelper.getClassInternalName(type);
        StringBuilder ret = new StringBuilder(internalName.length());
        int n = internalName.length();
        for (int i = 0; i < n; ++i) {
            char c = internalName.charAt(i);
            if (c == '/') {
                ret.append('$');
                continue;
            }
            if (c == ';') continue;
            ret.append(c);
        }
        return ret.toString();
    }

    private static String getStaticFieldName(ClassNode type) {
        ClassNode componentType = type;
        StringBuilder prefix = new StringBuilder();
        while (componentType.isArray()) {
            prefix.append("$");
            componentType = componentType.getComponentType();
        }
        if (prefix.length() != 0) {
            prefix.insert(0, "array");
        }
        String name = prefix + "$class$" + AsmClassGenerator.makeFieldClassName(componentType);
        return name;
    }

    @Deprecated
    public static boolean isValidFieldNodeForByteCodeAccess(FieldNode field, ClassNode accessingClass) {
        return AsmClassGenerator.isFieldDirectlyAccessible(field, accessingClass);
    }

    public static boolean isFieldDirectlyAccessible(FieldNode field, ClassNode clazz) {
        if (field == null) {
            return false;
        }
        if (field.isPublic()) {
            return true;
        }
        ClassNode declaringClass = field.getDeclaringClass();
        if (clazz.equals(declaringClass)) {
            return true;
        }
        if (field.isPrivate()) {
            return false;
        }
        if (field.isProtected() && clazz.isDerivedFrom(declaringClass)) {
            return true;
        }
        return Objects.equals(clazz.getPackageName(), declaringClass.getPackageName());
    }

    public static FieldNode getDeclaredFieldOfCurrentClassOrAccessibleFieldOfSuper(ClassNode accessingNode, ClassNode current, String fieldName, boolean skipCurrent) {
        return ClassNodeUtils.getField(current, fieldName, fieldNode -> (!skipCurrent || !current.equals(fieldNode.getDeclaringClass())) && AsmClassGenerator.isFieldDirectlyAccessible(fieldNode, accessingNode));
    }

    private void visitAttributeOrProperty(PropertyExpression pexp, MethodCallerMultiAdapter adapter) {
        ClassNode classNode = this.controller.getClassNode();
        String propertyName = pexp.getPropertyAsString();
        Expression objectExpression = pexp.getObjectExpression();
        if (objectExpression instanceof ClassExpression && "this".equals(propertyName)) {
            ClassNode type = objectExpression.getType();
            if (this.controller.getCompileStack().isInSpecialConstructorCall() && type.equals(classNode.getOuterClass())) {
                ConstructorNode ctor = this.controller.getConstructorNode();
                Expression receiver = !classNode.isStaticClass() ? new VariableExpression(ctor.getParameters()[0]) : new ClassExpression(type);
                receiver.setSourcePosition(pexp);
                receiver.visit(this);
                return;
            }
            MethodVisitor mv = this.controller.getMethodVisitor();
            mv.visitVarInsn(25, 0);
            ClassNode iterType = classNode;
            while (!iterType.equals(type)) {
                String ownerName = BytecodeHelper.getClassInternalName(iterType);
                if (iterType.getOuterClass() == null) break;
                FieldNode thisField = iterType.getField("this$0");
                iterType = iterType.getOuterClass();
                if (thisField == null) {
                    while (ClassHelper.isGeneratedFunction(iterType)) {
                        iterType = iterType.getOuterClass();
                    }
                    mv.visitMethodInsn(182, BytecodeHelper.getClassInternalName(ClassHelper.CLOSURE_TYPE), "getThisObject", "()Ljava/lang/Object;", false);
                    mv.visitTypeInsn(192, BytecodeHelper.getClassInternalName(iterType));
                    continue;
                }
                ClassNode thisFieldType = thisField.getType();
                if (ClassHelper.CLOSURE_TYPE.equals(thisFieldType)) {
                    mv.visitFieldInsn(180, ownerName, "this$0", BytecodeHelper.getTypeDescription(ClassHelper.CLOSURE_TYPE));
                    mv.visitMethodInsn(182, BytecodeHelper.getClassInternalName(ClassHelper.CLOSURE_TYPE), "getThisObject", "()Ljava/lang/Object;", false);
                    mv.visitTypeInsn(192, BytecodeHelper.getClassInternalName(iterType));
                    continue;
                }
                String typeName = BytecodeHelper.getTypeDescription(iterType);
                mv.visitFieldInsn(180, ownerName, "this$0", typeName);
            }
            this.controller.getOperandStack().push(type);
            return;
        }
        if (propertyName != null) {
            if (adapter == getProperty && !pexp.isSpreadSafe()) {
                this.controller.getCallSiteWriter().makeGetPropertySite(objectExpression, propertyName, pexp.isSafe(), pexp.isImplicitThis());
            } else if (adapter == getGroovyObjectProperty && !pexp.isSpreadSafe()) {
                this.controller.getCallSiteWriter().makeGroovyObjectGetPropertySite(objectExpression, propertyName, pexp.isSafe(), pexp.isImplicitThis());
            } else {
                this.controller.getCallSiteWriter().fallbackAttributeOrPropertySite(pexp, objectExpression, propertyName, adapter);
            }
        } else {
            this.controller.getCallSiteWriter().fallbackAttributeOrPropertySite(pexp, objectExpression, null, adapter);
        }
    }

    private boolean checkStaticOuterField(PropertyExpression pexp, String propertyName) {
        for (ClassNode outer : this.controller.getClassNode().getOuterClasses()) {
            FieldNode field = outer.getDeclaredField(propertyName);
            if (field != null) {
                if (!field.isStatic()) break;
                ClassExpression outerClass = GeneralUtils.classX(outer);
                outerClass.setNodeMetaData((Object)StaticCompilationMetadataKeys.PROPERTY_OWNER, outer);
                outerClass.setSourcePosition(pexp.getObjectExpression());
                Expression outerField = GeneralUtils.attrX(outerClass, pexp.getProperty());
                outerField.setSourcePosition(pexp);
                outerField.visit(this);
                return true;
            }
            field = outer.getField(propertyName);
            if (field == null || field.isPrivate() || !field.isPublic() && !field.isProtected() && !Objects.equals(field.getDeclaringClass().getPackageName(), outer.getPackageName())) continue;
            if (!field.isStatic()) break;
            ClassExpression upperClass = GeneralUtils.classX(field.getDeclaringClass());
            upperClass.setNodeMetaData((Object)StaticCompilationMetadataKeys.PROPERTY_OWNER, field.getDeclaringClass());
            upperClass.setSourcePosition(pexp.getObjectExpression());
            PropertyExpression upperField = GeneralUtils.propX((Expression)upperClass, pexp.getProperty());
            upperField.setSourcePosition(pexp);
            ((ASTNode)upperField).visit(this);
            return true;
        }
        return false;
    }

    private boolean isGroovyObject(Expression objectExpression) {
        if (ExpressionUtils.isThisOrSuper(objectExpression)) {
            return true;
        }
        if (objectExpression instanceof ClassExpression) {
            return false;
        }
        ClassNode objectExpressionType = this.controller.getTypeChooser().resolveType(objectExpression, this.controller.getClassNode());
        if (ClassHelper.isObjectType(objectExpressionType)) {
            objectExpressionType = objectExpression.getType();
        }
        return objectExpressionType.isDerivedFromGroovyObject();
    }

    @Override
    public void visitPropertyExpression(PropertyExpression expression) {
        String name;
        Expression objectExpression = expression.getObjectExpression();
        OperandStack operandStack = this.controller.getOperandStack();
        int mark = operandStack.getStackLength() - 1;
        boolean visited = false;
        if (ExpressionUtils.isThisOrSuper(objectExpression) && (name = expression.getPropertyAsString()) != null) {
            FieldNode fieldNode = null;
            ClassNode classNode = this.controller.getClassNode();
            if (ExpressionUtils.isThisExpression(objectExpression)) {
                if (this.controller.isInGeneratedFunction()) {
                    if (expression.isImplicitThis()) {
                        fieldNode = classNode.getDeclaredField(name);
                    }
                } else {
                    fieldNode = classNode.getDeclaredField(name);
                    if (fieldNode != null && !expression.isImplicitThis() && (fieldNode.getModifiers() & 0x1000) != 0 && fieldNode.getType().equals(ClassHelper.REFERENCE_TYPE)) {
                        fieldNode = null;
                    }
                    if (fieldNode == null && !AsmClassGenerator.isFieldDirectlyAccessible(ClassNodeUtils.getField(classNode, name), classNode) && this.checkStaticOuterField(expression, name)) {
                        return;
                    }
                }
            } else {
                fieldNode = classNode.getSuperClass().getDeclaredField(name);
                if (fieldNode != null && fieldNode.isPrivate()) {
                    fieldNode = null;
                }
            }
            if (fieldNode != null) {
                GeneralUtils.fieldX(fieldNode).visit(this);
                visited = true;
            }
        }
        if (!visited) {
            boolean useMetaObjectProtocol;
            boolean bl = useMetaObjectProtocol = this.isGroovyObject(objectExpression) && (!ExpressionUtils.isThisOrSuper(objectExpression) || !this.controller.isStaticContext() || this.controller.isInGeneratedFunction());
            MethodCallerMultiAdapter adapter = this.controller.getCompileStack().isLHS() ? (ExpressionUtils.isSuperExpression(objectExpression) ? setPropertyOnSuper : (useMetaObjectProtocol ? setGroovyObjectProperty : setProperty)) : (ExpressionUtils.isSuperExpression(objectExpression) ? getPropertyOnSuper : (useMetaObjectProtocol ? getGroovyObjectProperty : getProperty));
            this.visitAttributeOrProperty(expression, adapter);
        }
        if (this.controller.getCompileStack().isLHS()) {
            operandStack.remove(operandStack.getStackLength() - mark);
        } else {
            this.controller.getAssertionWriter().record(expression.getProperty());
        }
    }

    @Override
    public void visitAttributeExpression(AttributeExpression expression) {
        ClassNode classNode;
        FieldNode fieldNode;
        String name;
        Expression objectExpression = expression.getObjectExpression();
        OperandStack operandStack = this.controller.getOperandStack();
        int mark = operandStack.getStackLength() - 1;
        boolean visited = false;
        if (ExpressionUtils.isThisOrSuper(objectExpression) && (name = expression.getPropertyAsString()) != null && (fieldNode = AsmClassGenerator.getDeclaredFieldOfCurrentClassOrAccessibleFieldOfSuper(classNode = this.controller.getClassNode(), classNode, name, ExpressionUtils.isSuperExpression(objectExpression))) != null) {
            GeneralUtils.fieldX(fieldNode).visit(this);
            visited = true;
        }
        if (!visited) {
            MethodCallerMultiAdapter adapter = this.controller.getCompileStack().isLHS() ? (ExpressionUtils.isSuperExpression(objectExpression) ? setFieldOnSuper : (this.isGroovyObject(objectExpression) ? setGroovyObjectField : setField)) : (ExpressionUtils.isSuperExpression(objectExpression) ? getFieldOnSuper : (this.isGroovyObject(objectExpression) ? getGroovyObjectField : getField));
            this.visitAttributeOrProperty(expression, adapter);
        }
        if (this.controller.getCompileStack().isLHS()) {
            operandStack.remove(operandStack.getStackLength() - mark);
        } else {
            this.controller.getAssertionWriter().record(expression.getProperty());
        }
    }

    @Override
    public void visitFieldExpression(FieldExpression expression) {
        if (expression.getField().isStatic()) {
            if (this.controller.getCompileStack().isLHS()) {
                this.storeStaticField(expression);
            } else {
                this.loadStaticField(expression);
            }
        } else if (this.controller.getCompileStack().isLHS()) {
            this.storeThisInstanceField(expression);
        } else {
            this.loadInstanceField(expression);
        }
    }

    public void loadStaticField(FieldExpression expression) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        FieldNode field = expression.getField();
        ClassNode type = field.getType();
        OperandStack operandStack = this.controller.getOperandStack();
        if (field.isHolder() && !this.controller.isInGeneratedFunctionConstructor()) {
            mv.visitFieldInsn(178, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
            mv.visitMethodInsn(182, "groovy/lang/Reference", "get", "()Ljava/lang/Object;", false);
            operandStack.push(ClassHelper.OBJECT_TYPE);
        } else {
            mv.visitFieldInsn(178, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
            operandStack.push(type);
        }
    }

    public void loadInstanceField(FieldExpression expression) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        FieldNode field = expression.getField();
        ClassNode type = field.getType();
        mv.visitVarInsn(25, 0);
        mv.visitFieldInsn(180, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
        OperandStack operandStack = this.controller.getOperandStack();
        if (field.isHolder() && !this.controller.isInGeneratedFunctionConstructor()) {
            mv.visitMethodInsn(182, "groovy/lang/Reference", "get", "()Ljava/lang/Object;", false);
            operandStack.push(ClassHelper.OBJECT_TYPE);
        } else {
            operandStack.push(type);
        }
    }

    private void storeThisInstanceField(FieldExpression expression) {
        OperandStack operandStack = this.controller.getOperandStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        FieldNode field = expression.getField();
        ClassNode type = field.getType();
        if (field.isHolder() && expression.isUseReferenceDirectly()) {
            mv.visitVarInsn(25, 0);
            operandStack.push(this.controller.getClassNode());
            operandStack.swap();
            mv.visitFieldInsn(181, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
        } else if (field.isHolder()) {
            operandStack.doGroovyCast(field.getOriginType());
            operandStack.box();
            mv.visitVarInsn(25, 0);
            mv.visitFieldInsn(180, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
            mv.visitInsn(95);
            mv.visitMethodInsn(182, "groovy/lang/Reference", "set", "(Ljava/lang/Object;)V", false);
        } else {
            operandStack.doGroovyCast(field.getOriginType());
            mv.visitVarInsn(25, 0);
            operandStack.push(this.controller.getClassNode());
            operandStack.swap();
            mv.visitFieldInsn(181, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
        }
    }

    private void storeStaticField(FieldExpression expression) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        FieldNode field = expression.getField();
        ClassNode type = field.getType();
        OperandStack operandStack = this.controller.getOperandStack();
        operandStack.doGroovyCast(field);
        if (field.isHolder() && !this.controller.isInGeneratedFunctionConstructor()) {
            operandStack.box();
            mv.visitFieldInsn(178, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
            mv.visitInsn(95);
            mv.visitMethodInsn(182, "groovy/lang/Reference", "set", "(Ljava/lang/Object;)V", false);
        } else {
            mv.visitFieldInsn(179, this.getFieldOwnerName(field), field.getName(), BytecodeHelper.getTypeDescription(type));
        }
        operandStack.remove(1);
    }

    private String getFieldOwnerName(FieldNode field) {
        if (field.getOwner().equals(this.controller.getClassNode())) {
            return this.controller.getInternalClassName();
        }
        return BytecodeHelper.getClassInternalName(field.getOwner());
    }

    @Override
    public void visitVariableExpression(VariableExpression expression) {
        String variableName = expression.getName();
        if (expression.isThisExpression()) {
            if (this.controller.isStaticMethod() || this.controller.getCompileStack().isInSpecialConstructorCall() || !this.controller.getCompileStack().isImplicitThis() && this.controller.isStaticContext()) {
                GeneralUtils.classX(this.controller.getThisType()).visit(this);
            } else {
                this.loadThis(expression);
            }
            return;
        }
        if (expression.isSuperExpression()) {
            if (this.controller.isStaticMethod()) {
                ClassNode superType = this.controller.getClassNode().getSuperClass();
                GeneralUtils.classX(superType).visit(this);
            } else {
                this.loadThis(expression);
            }
            return;
        }
        BytecodeVariable variable = this.controller.getCompileStack().getVariable(variableName, false);
        if (variable != null) {
            this.controller.getOperandStack().loadOrStoreVariable(variable, expression.isUseReferenceDirectly());
        } else if (this.passingParams && this.controller.isInScriptBody()) {
            MethodVisitor mv = this.controller.getMethodVisitor();
            mv.visitTypeInsn(187, "org/codehaus/groovy/runtime/ScriptReference");
            mv.visitInsn(89);
            this.loadThisOrOwner();
            mv.visitLdcInsn(variableName);
            mv.visitMethodInsn(183, "org/codehaus/groovy/runtime/ScriptReference", "<init>", "(Lgroovy/lang/Script;Ljava/lang/String;)V", false);
        } else {
            PropertyExpression pexp = GeneralUtils.thisPropX(true, variableName);
            pexp.getObjectExpression().setSourcePosition(expression);
            pexp.getProperty().setSourcePosition(expression);
            pexp.copyNodeMetaData(expression);
            pexp.visit(this);
        }
        if (!this.controller.getCompileStack().isLHS()) {
            this.controller.getAssertionWriter().record(expression);
        }
    }

    protected void createInterfaceSyntheticStaticFields() {
        InterfaceHelperClassNode icl = this.controller.getInterfaceClassLoadingClass();
        if (this.referencedClasses.isEmpty()) {
            Iterator<InnerClassNode> it = ((ClassNode)icl).getOuterClass().getInnerClasses();
            while (it.hasNext()) {
                InnerClassNode inner = it.next();
                if (inner != icl) continue;
                it.remove();
                return;
            }
            return;
        }
        this.addInnerClass(icl);
        for (Map.Entry<String, ClassNode> entry : this.referencedClasses.entrySet()) {
            String staticFieldName = entry.getKey();
            ClassNode cn = entry.getValue();
            icl.addField(staticFieldName, 4104, ClassHelper.CLASS_Type.getPlainNodeReference(), new ClassExpression(cn));
        }
    }

    protected void createSyntheticStaticFields() {
        MethodVisitor mv;
        if (this.referencedClasses.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ClassNode> entry : this.referencedClasses.entrySet()) {
            String staticFieldName = entry.getKey();
            ClassNode cn = entry.getValue();
            FieldNode fn = this.controller.getClassNode().getDeclaredField(staticFieldName);
            if (fn != null) {
                boolean modifiers;
                boolean type = ClassHelper.isClassType(fn.getType());
                boolean bl = modifiers = fn.getModifiers() == 4104;
                if (!type || !modifiers) {
                    String text = "";
                    if (!type) {
                        text = " with wrong type: " + fn.getType() + " (java.lang.Class needed)";
                    }
                    if (!modifiers) {
                        text = " with wrong modifiers: " + fn.getModifiers() + " (" + 4104 + " needed)";
                    }
                    this.throwException("tried to set a static synthetic field " + staticFieldName + " in " + this.controller.getClassNode().getName() + " for class resolving, but found already a node of that name " + text);
                }
            } else {
                this.classVisitor.visitField(4106, staticFieldName, "Ljava/lang/Class;", null, null);
            }
            mv = this.classVisitor.visitMethod(4106, "$get$" + staticFieldName, "()Ljava/lang/Class;", null, null);
            mv.visitCode();
            mv.visitFieldInsn(178, this.controller.getInternalClassName(), staticFieldName, "Ljava/lang/Class;");
            mv.visitInsn(89);
            Label l0 = new Label();
            mv.visitJumpInsn(199, l0);
            mv.visitInsn(87);
            mv.visitLdcInsn(BytecodeHelper.getClassLoadingTypeDescription(cn));
            mv.visitMethodInsn(184, this.controller.getInternalClassName(), "class$", "(Ljava/lang/String;)Ljava/lang/Class;", false);
            mv.visitInsn(89);
            mv.visitFieldInsn(179, this.controller.getInternalClassName(), staticFieldName, "Ljava/lang/Class;");
            mv.visitLabel(l0);
            mv.visitInsn(176);
            mv.visitMaxs(0, 0);
            mv.visitEnd();
        }
        mv = this.classVisitor.visitMethod(4104, "class$", "(Ljava/lang/String;)Ljava/lang/Class;", null, null);
        Label l0 = new Label();
        mv.visitLabel(l0);
        mv.visitVarInsn(25, 0);
        mv.visitMethodInsn(184, "java/lang/Class", "forName", "(Ljava/lang/String;)Ljava/lang/Class;", false);
        Label l1 = new Label();
        mv.visitLabel(l1);
        mv.visitInsn(176);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitVarInsn(58, 1);
        mv.visitTypeInsn(187, "java/lang/NoClassDefFoundError");
        mv.visitInsn(89);
        mv.visitVarInsn(25, 1);
        mv.visitMethodInsn(182, "java/lang/ClassNotFoundException", "getMessage", "()Ljava/lang/String;", false);
        mv.visitMethodInsn(183, "java/lang/NoClassDefFoundError", "<init>", "(Ljava/lang/String;)V", false);
        mv.visitInsn(191);
        mv.visitTryCatchBlock(l0, l2, l2, "java/lang/ClassNotFoundException");
        mv.visitMaxs(3, 2);
    }

    @Override
    public void visitClassExpression(ClassExpression expression) {
        ClassNode type = expression.getType();
        MethodVisitor mv = this.controller.getMethodVisitor();
        OperandStack operandStack = this.controller.getOperandStack();
        if (BytecodeHelper.isClassLiteralPossible(type) || BytecodeHelper.isSameCompilationUnit(this.controller.getClassNode(), type)) {
            if (this.controller.getClassNode().isInterface()) {
                InterfaceHelperClassNode interfaceClassLoadingClass = this.controller.getInterfaceClassLoadingClass();
                if (BytecodeHelper.isClassLiteralPossible(interfaceClassLoadingClass)) {
                    BytecodeHelper.visitClassLiteral(mv, interfaceClassLoadingClass);
                    operandStack.push(ClassHelper.CLASS_Type);
                    this.maybeInnerClassEntry(type);
                    return;
                }
            } else {
                BytecodeHelper.visitClassLiteral(mv, type);
                operandStack.push(ClassHelper.CLASS_Type);
                this.maybeInnerClassEntry(type);
                return;
            }
        }
        String staticFieldName = AsmClassGenerator.getStaticFieldName(type);
        this.referencedClasses.put(staticFieldName, type);
        String internalClassName = this.controller.getInternalClassName();
        if (this.controller.getClassNode().isInterface()) {
            internalClassName = BytecodeHelper.getClassInternalName(this.controller.getInterfaceClassLoadingClass());
            mv.visitFieldInsn(178, internalClassName, staticFieldName, "Ljava/lang/Class;");
        } else {
            mv.visitMethodInsn(184, internalClassName, "$get$" + staticFieldName, "()Ljava/lang/Class;", false);
        }
        operandStack.push(ClassHelper.CLASS_Type);
    }

    @Override
    public void visitRangeExpression(RangeExpression expression) {
        OperandStack operandStack = this.controller.getOperandStack();
        expression.getFrom().visit(this);
        operandStack.box();
        expression.getTo().visit(this);
        operandStack.box();
        operandStack.pushBool(expression.isExclusiveLeft());
        operandStack.pushBool(expression.isExclusiveRight());
        createRangeMethod.call(this.controller.getMethodVisitor());
        operandStack.replace(ClassHelper.RANGE_TYPE, 4);
    }

    @Override
    public void visitMapEntryExpression(MapEntryExpression expression) {
        throw new GroovyBugError("MapEntryExpression should not be visited here");
    }

    @Override
    public void visitMapExpression(MapExpression expression) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        List<MapEntryExpression> entries = expression.getMapEntryExpressions();
        int size = entries.size();
        BytecodeHelper.pushConstant(mv, size * 2);
        mv.visitTypeInsn(189, "java/lang/Object");
        int i = 0;
        OperandStack operandStack = this.controller.getOperandStack();
        for (MapEntryExpression entry : entries) {
            mv.visitInsn(89);
            BytecodeHelper.pushConstant(mv, i++);
            entry.getKeyExpression().visit(this);
            operandStack.box();
            mv.visitInsn(83);
            mv.visitInsn(89);
            BytecodeHelper.pushConstant(mv, i++);
            entry.getValueExpression().visit(this);
            operandStack.box();
            mv.visitInsn(83);
            operandStack.remove(2);
        }
        createMapMethod.call(mv);
        operandStack.push(ClassHelper.MAP_TYPE);
    }

    @Override
    public void visitArgumentlistExpression(ArgumentListExpression ale) {
        if (AsmClassGenerator.containsSpreadExpression(ale)) {
            this.despreadList(ale.getExpressions(), true);
        } else {
            this.visitTupleExpression(ale, true);
        }
    }

    @Override
    public void visitTupleExpression(TupleExpression expression) {
        this.visitTupleExpression(expression, false);
    }

    void visitTupleExpression(TupleExpression expression, boolean useWrapper) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        int size = expression.getExpressions().size();
        BytecodeHelper.pushConstant(mv, size);
        mv.visitTypeInsn(189, "java/lang/Object");
        OperandStack operandStack = this.controller.getOperandStack();
        for (int i = 0; i < size; ++i) {
            mv.visitInsn(89);
            BytecodeHelper.pushConstant(mv, i);
            Expression argument = expression.getExpression(i);
            argument.visit(this);
            operandStack.box();
            if (useWrapper && argument instanceof CastExpression) {
                this.loadWrapper(argument);
            }
            mv.visitInsn(83);
            operandStack.remove(1);
        }
    }

    @Override
    public void visitArrayExpression(ArrayExpression expression) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        int size = 0;
        int dimensions = 0;
        OperandStack operandStack = this.controller.getOperandStack();
        if (expression.hasInitializer()) {
            size = expression.getExpressions().size();
            BytecodeHelper.pushConstant(mv, size);
        } else {
            for (Expression element : expression.getSizeExpression()) {
                if (element == ConstantExpression.EMPTY_EXPRESSION) break;
                ++dimensions;
                element.visit(this);
                operandStack.doGroovyCast(ClassHelper.int_TYPE);
            }
            operandStack.remove(dimensions);
        }
        ClassNode arrayType = expression.getType();
        ClassNode elementType = arrayType.getComponentType();
        int storeIns = 83;
        if (!elementType.isArray() || expression.hasInitializer()) {
            if (ClassHelper.isPrimitiveType(elementType)) {
                int primType = 0;
                if (ClassHelper.isPrimitiveBoolean(elementType)) {
                    primType = 4;
                    storeIns = 84;
                } else if (ClassHelper.isPrimitiveChar(elementType)) {
                    primType = 5;
                    storeIns = 85;
                } else if (ClassHelper.isPrimitiveFloat(elementType)) {
                    primType = 6;
                    storeIns = 81;
                } else if (ClassHelper.isPrimitiveDouble(elementType)) {
                    primType = 7;
                    storeIns = 82;
                } else if (ClassHelper.isPrimitiveByte(elementType)) {
                    primType = 8;
                    storeIns = 84;
                } else if (ClassHelper.isPrimitiveShort(elementType)) {
                    primType = 9;
                    storeIns = 86;
                } else if (ClassHelper.isPrimitiveInt(elementType)) {
                    primType = 10;
                    storeIns = 79;
                } else if (ClassHelper.isPrimitiveLong(elementType)) {
                    primType = 11;
                    storeIns = 80;
                }
                mv.visitIntInsn(188, primType);
            } else {
                mv.visitTypeInsn(189, BytecodeHelper.getClassInternalName(elementType));
                this.maybeInnerClassEntry(elementType);
            }
        } else {
            mv.visitMultiANewArrayInsn(BytecodeHelper.getTypeDescription(arrayType), dimensions);
        }
        for (int i = 0; i < size; ++i) {
            mv.visitInsn(89);
            BytecodeHelper.pushConstant(mv, i);
            Expression elementExpression = expression.getExpression(i);
            if (elementExpression == null) {
                ConstantExpression.NULL.visit(this);
            } else {
                elementExpression.visit(this);
                operandStack.doGroovyCast(elementType);
            }
            mv.visitInsn(storeIns);
            operandStack.remove(1);
        }
        operandStack.push(arrayType);
    }

    @Override
    public void visitClosureListExpression(ClosureListExpression expression) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        this.controller.getCompileStack().pushVariableScope(expression.getVariableScope());
        List<Expression> expressions = expression.getExpressions();
        final int size = expressions.size();
        for (int i = 0; i < size; ++i) {
            Expression expr = expressions.get(i);
            if (!(expr instanceof DeclarationExpression)) continue;
            DeclarationExpression de = (DeclarationExpression)expr;
            BinaryExpression be = new BinaryExpression(de.getLeftExpression(), de.getOperation(), de.getRightExpression());
            expressions.set(i, be);
            de.setRightExpression(ConstantExpression.NULL);
            this.visitDeclarationExpression(de);
        }
        LinkedList<Object> instructions = new LinkedList<Object>();
        instructions.add(ConstantExpression.NULL);
        final Label dflt = new Label();
        final Label tableEnd = new Label();
        final Label[] labels = new Label[size];
        instructions.add(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                mv.visitVarInsn(21, 1);
                mv.visitTableSwitchInsn(0, size - 1, dflt, labels);
            }
        });
        for (int i = 0; i < size; ++i) {
            final Label label = new Label();
            Expression expr = expressions.get(i);
            labels[i] = label;
            instructions.add(new BytecodeInstruction(){

                @Override
                public void visit(MethodVisitor mv) {
                    mv.visitLabel(label);
                    mv.visitInsn(87);
                }
            });
            instructions.add(expr);
            instructions.add(new BytecodeInstruction(){

                @Override
                public void visit(MethodVisitor mv) {
                    mv.visitJumpInsn(167, tableEnd);
                }
            });
        }
        instructions.add(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                mv.visitLabel(dflt);
            }
        });
        ConstantExpression text = new ConstantExpression("invalid index for closure");
        ConstructorCallExpression cce = new ConstructorCallExpression(ClassHelper.make(IllegalArgumentException.class), text);
        ThrowStatement ts = new ThrowStatement(cce);
        instructions.add(ts);
        instructions.add(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                mv.visitLabel(tableEnd);
                mv.visitInsn(176);
            }
        });
        BlockStatement bs = new BlockStatement();
        bs.addStatement(new BytecodeSequence(instructions));
        Parameter closureIndex = new Parameter(ClassHelper.int_TYPE, "__closureIndex");
        ClosureExpression ce = new ClosureExpression(new Parameter[]{closureIndex}, bs);
        ce.setVariableScope(expression.getVariableScope());
        this.visitClosureExpression(ce);
        BytecodeHelper.pushConstant(mv, size);
        mv.visitTypeInsn(189, "java/lang/Object");
        int listArrayVar = this.controller.getCompileStack().defineTemporaryVariable("_listOfClosures", true);
        for (int i = 0; i < size; ++i) {
            mv.visitTypeInsn(187, "org/codehaus/groovy/runtime/CurriedClosure");
            mv.visitInsn(92);
            mv.visitInsn(95);
            mv.visitInsn(4);
            mv.visitTypeInsn(189, "java/lang/Object");
            mv.visitInsn(89);
            mv.visitInsn(3);
            mv.visitLdcInsn(i);
            mv.visitMethodInsn(184, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
            mv.visitInsn(83);
            mv.visitMethodInsn(183, "org/codehaus/groovy/runtime/CurriedClosure", "<init>", "(Lgroovy/lang/Closure;[Ljava/lang/Object;)V", false);
            mv.visitVarInsn(25, listArrayVar);
            mv.visitInsn(95);
            BytecodeHelper.pushConstant(mv, i);
            mv.visitInsn(95);
            mv.visitInsn(83);
        }
        mv.visitInsn(87);
        mv.visitVarInsn(25, listArrayVar);
        createListMethod.call(mv);
        this.controller.getCompileStack().removeVar(listArrayVar);
        this.controller.getOperandStack().pop();
    }

    @Override
    public void visitBytecodeExpression(BytecodeExpression expression) {
        expression.visit(this.controller.getMethodVisitor());
        this.controller.getOperandStack().push(expression.getType());
    }

    @Override
    public void visitBytecodeSequence(BytecodeSequence bytecodeSequence) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        List<?> sequence = bytecodeSequence.getInstructions();
        OperandStack operandStack = this.controller.getOperandStack();
        int mark = operandStack.getStackLength();
        for (Object element : sequence) {
            if (element instanceof EmptyExpression) {
                mv.visitInsn(1);
                continue;
            }
            if (element instanceof Expression) {
                ((Expression)element).visit(this);
                continue;
            }
            if (element instanceof Statement) {
                ((Statement)element).visit(this);
                mv.visitInsn(1);
                continue;
            }
            ((BytecodeInstruction)element).visit(mv);
        }
        operandStack.remove(mark - operandStack.getStackLength());
    }

    @Override
    public void visitListExpression(ListExpression expression) {
        this.onLineNumber(expression, "ListExpression");
        int size = expression.getExpressions().size();
        boolean containsSpreadExpression = AsmClassGenerator.containsSpreadExpression(expression);
        boolean containsOnlyConstants = !containsSpreadExpression && AsmClassGenerator.containsOnlyConstants(expression);
        OperandStack operandStack = this.controller.getOperandStack();
        if (!containsSpreadExpression) {
            MethodVisitor mv = this.controller.getMethodVisitor();
            BytecodeHelper.pushConstant(mv, size);
            mv.visitTypeInsn(189, "java/lang/Object");
            int maxInit = 1000;
            if (size < maxInit || !containsOnlyConstants) {
                for (int i = 0; i < size; ++i) {
                    mv.visitInsn(89);
                    BytecodeHelper.pushConstant(mv, i);
                    expression.getExpression(i).visit(this);
                    operandStack.box();
                    mv.visitInsn(83);
                }
                operandStack.remove(size);
            } else {
                List<Expression> expressions = expression.getExpressions();
                ArrayList<String> methods = new ArrayList<String>();
                MethodVisitor oldMv = mv;
                int index = 0;
                while (index < size) {
                    String methodName = "$createListEntry_" + this.controller.getNextHelperMethodIndex();
                    methods.add(methodName);
                    mv = this.controller.getClassVisitor().visitMethod(4106, methodName, "([Ljava/lang/Object;)V", null, null);
                    this.controller.setMethodVisitor(mv);
                    mv.visitCode();
                    int methodBlockSize = Math.min(size - index, maxInit);
                    int methodBlockEnd = index + methodBlockSize;
                    while (index < methodBlockEnd) {
                        mv.visitVarInsn(25, 0);
                        mv.visitLdcInsn(index);
                        expressions.get(index).visit(this);
                        operandStack.box();
                        mv.visitInsn(83);
                        ++index;
                    }
                    operandStack.remove(methodBlockSize);
                    mv.visitInsn(177);
                    mv.visitMaxs(0, 0);
                    mv.visitEnd();
                }
                mv = oldMv;
                this.controller.setMethodVisitor(mv);
                for (String methodName : methods) {
                    mv.visitInsn(89);
                    mv.visitMethodInsn(184, this.controller.getInternalClassName(), methodName, "([Ljava/lang/Object;)V", false);
                }
            }
        } else {
            this.despreadList(expression.getExpressions(), false);
        }
        createListMethod.call(this.controller.getMethodVisitor());
        operandStack.push(ClassHelper.LIST_TYPE);
    }

    @Override
    public void visitGStringExpression(GStringExpression expression) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitTypeInsn(187, "org/codehaus/groovy/runtime/GStringImpl");
        mv.visitInsn(89);
        int size = expression.getValues().size();
        BytecodeHelper.pushConstant(mv, size);
        mv.visitTypeInsn(189, "java/lang/Object");
        OperandStack operandStack = this.controller.getOperandStack();
        for (int i = 0; i < size; ++i) {
            mv.visitInsn(89);
            BytecodeHelper.pushConstant(mv, i);
            expression.getValue(i).visit(this);
            operandStack.box();
            mv.visitInsn(83);
        }
        operandStack.remove(size);
        List<ConstantExpression> strings = expression.getStrings();
        size = strings.size();
        BytecodeHelper.pushConstant(mv, size);
        mv.visitTypeInsn(189, "java/lang/String");
        for (int i = 0; i < size; ++i) {
            mv.visitInsn(89);
            BytecodeHelper.pushConstant(mv, i);
            operandStack.pushConstant(strings.get(i));
            operandStack.box();
            mv.visitInsn(83);
        }
        operandStack.remove(size);
        mv.visitMethodInsn(183, "org/codehaus/groovy/runtime/GStringImpl", "<init>", "([Ljava/lang/Object;[Ljava/lang/String;)V", false);
        operandStack.push(ClassHelper.GSTRING_TYPE);
    }

    @Override
    public void visitAnnotations(AnnotatedNode node) {
    }

    private void visitAnnotations(AnnotatedNode targetNode, Object visitor) {
        this.visitAnnotations(targetNode, targetNode, visitor);
    }

    private void visitAnnotations(AnnotatedNode targetNode, AnnotatedNode sourceNode, Object visitor) {
        for (AnnotationNode an : sourceNode.getAnnotations()) {
            if (an.isBuiltIn() || an.hasSourceRetention() || an.getClassNode().getName().equals(Sealed.class.getName()) && SealedASTTransformation.sealedNative(sourceNode) && SealedASTTransformation.sealedSkipAnnotation(sourceNode)) continue;
            this.maybeInnerClassEntry(an.getClassNode());
            AnnotationVisitor av = this.getAnnotationVisitor(targetNode, an, visitor);
            this.visitAnnotationAttributes(an, av);
            av.visitEnd();
        }
    }

    private void visitTypeAnnotations(ClassNode sourceNode, Object visitor, TypeReference typeRef, String typePathStr, boolean typeUse) {
        for (AnnotationNode an : sourceNode.getTypeAnnotations()) {
            TypePath typePath;
            if (an.isBuiltIn() || an.hasSourceRetention() || typeUse && !an.isTargetAllowed(512)) continue;
            AnnotationVisitor av = null;
            try {
                typePath = TypePath.fromString(typePathStr);
            }
            catch (IllegalArgumentException ex) {
                throw new GroovyBugError("Illegal type path for " + sourceNode.getText() + ", typeRef = " + typeRef + ", typePath = " + typePathStr);
            }
            int typeRefInt = typeRef.getValue();
            String annotationDescriptor = BytecodeHelper.getTypeDescription(an.getClassNode());
            if (visitor instanceof ClassVisitor) {
                av = ((ClassVisitor)visitor).visitTypeAnnotation(typeRefInt, typePath, annotationDescriptor, an.hasRuntimeRetention());
            } else if (visitor instanceof MethodVisitor) {
                av = ((MethodVisitor)visitor).visitTypeAnnotation(typeRefInt, typePath, annotationDescriptor, an.hasRuntimeRetention());
            } else if (visitor instanceof FieldVisitor) {
                av = ((FieldVisitor)visitor).visitTypeAnnotation(typeRefInt, typePath, annotationDescriptor, an.hasRuntimeRetention());
            } else if (visitor instanceof RecordComponentVisitor) {
                av = ((RecordComponentVisitor)visitor).visitTypeAnnotation(typeRefInt, typePath, annotationDescriptor, an.hasRuntimeRetention());
            } else {
                this.throwException("Cannot create an AnnotationVisitor. Please report Groovy bug");
            }
            this.visitAnnotationAttributes(an, av);
            av.visitEnd();
        }
    }

    private void visitGenericsTypeAnnotations(ClassNode classNode, Object visitor, TypeReference typeRef, String typePath, boolean typeUse) {
        if (!classNode.isUsingGenerics() || classNode.getGenericsTypes() == null) {
            return;
        }
        this.visitGenericsTypeAnnotations(classNode.getGenericsTypes(), visitor, typePath, typeRef, typeUse);
    }

    private void visitTypeParameters(MethodNode methodNode, Object visitor) {
        if (methodNode.getGenericsTypes() == null) {
            return;
        }
        this.visitGenericsTypeParameterAnnotations(methodNode.getGenericsTypes(), visitor, "", 1, 18);
    }

    private void visitTypeParameters(ClassNode classNode, Object visitor) {
        if (classNode.getGenericsTypes() == null) {
            return;
        }
        this.visitGenericsTypeParameterAnnotations(classNode.getGenericsTypes(), visitor, "", 0, 17);
    }

    private void visitGenericsTypeParameterAnnotations(GenericsType[] genericsTypes, Object visitor, String typePath, int sort, int boundSort) {
        for (int paramIdx = 0; paramIdx < genericsTypes.length; ++paramIdx) {
            GenericsType gt = genericsTypes[paramIdx];
            this.visitType(gt.getType(), visitor, TypeReference.newTypeParameterReference(sort, paramIdx), typePath, false);
            if (gt.getLowerBound() != null) {
                this.visitType(gt.getLowerBound(), visitor, TypeReference.newTypeParameterBoundReference(boundSort, paramIdx, 0), typePath, false);
            }
            if (gt.getUpperBounds() == null) continue;
            ClassNode[] upperBounds = gt.getUpperBounds();
            for (int boundIdx = 0; boundIdx < upperBounds.length; ++boundIdx) {
                this.visitType(upperBounds[boundIdx], visitor, TypeReference.newTypeParameterBoundReference(boundSort, paramIdx, boundIdx), typePath, false);
            }
        }
    }

    private void visitGenericsTypeAnnotations(GenericsType[] genericsTypes, Object visitor, String typePath, TypeReference typeRef, boolean typeUse) {
        for (int paramIdx = 0; paramIdx < genericsTypes.length; ++paramIdx) {
            GenericsType gt = genericsTypes[paramIdx];
            String prefix = typePath + paramIdx + ";";
            this.visitType(gt.getType(), visitor, typeRef, prefix, typeUse);
            if (gt.isPlaceholder()) continue;
            if (gt.getLowerBound() != null) {
                this.visitType(gt.getLowerBound(), visitor, typeRef, gt.isWildcard() ? prefix + "*" : prefix + "0;", typeUse);
            }
            if (gt.getUpperBounds() == null) continue;
            ClassNode[] upperBounds = gt.getUpperBounds();
            for (int boundIdx = 0; boundIdx < upperBounds.length; ++boundIdx) {
                this.visitType(upperBounds[boundIdx], visitor, typeRef, gt.isWildcard() ? prefix + "*" : prefix + boundIdx + ";", typeUse);
            }
        }
    }

    private void visitType(ClassNode classNode, Object visitor, TypeReference typeRef, String typePath, boolean typeUse) {
        this.maybeInnerClassEntry(classNode);
        this.visitTypeAnnotations(classNode, visitor, typeRef, typePath, typeUse);
        this.visitGenericsTypeAnnotations(classNode, visitor, typeRef, typePath, typeUse);
    }

    private void visitParameterAnnotations(Parameter parameter, int paramNumber, MethodVisitor mv) {
        for (AnnotationNode an : parameter.getAnnotations()) {
            if (an.isBuiltIn() || an.hasSourceRetention()) continue;
            String annotationDescriptor = BytecodeHelper.getTypeDescription(an.getClassNode());
            AnnotationVisitor av = mv.visitParameterAnnotation(paramNumber, annotationDescriptor, an.hasRuntimeRetention());
            this.visitAnnotationAttributes(an, av);
            av.visitEnd();
        }
    }

    private AnnotationVisitor getAnnotationVisitor(AnnotatedNode targetNode, AnnotationNode an, Object visitor) {
        String annotationDescriptor = BytecodeHelper.getTypeDescription(an.getClassNode());
        if (targetNode instanceof MethodNode) {
            return ((MethodVisitor)visitor).visitAnnotation(annotationDescriptor, an.hasRuntimeRetention());
        }
        if (targetNode instanceof FieldNode) {
            return ((FieldVisitor)visitor).visitAnnotation(annotationDescriptor, an.hasRuntimeRetention());
        }
        if (targetNode instanceof ClassNode) {
            return ((ClassVisitor)visitor).visitAnnotation(annotationDescriptor, an.hasRuntimeRetention());
        }
        if (visitor instanceof RecordComponentVisitor) {
            return ((RecordComponentVisitor)visitor).visitAnnotation(annotationDescriptor, true);
        }
        this.throwException("Cannot create an AnnotationVisitor. Please report Groovy bug");
        return null;
    }

    private void visitAnnotationAttributes(AnnotationNode an, AnnotationVisitor av) {
        HashMap<String, Object> constantAttrs = new HashMap<String, Object>();
        HashMap<String, PropertyExpression> enumAttrs = new HashMap<String, PropertyExpression>();
        HashMap<String, Object> atAttrs = new HashMap<String, Object>();
        HashMap<String, ListExpression> arrayAttrs = new HashMap<String, ListExpression>();
        for (Map.Entry<String, Expression> entry : an.getMembers().entrySet()) {
            String name = entry.getKey();
            Expression expr = entry.getValue();
            if (expr instanceof AnnotationConstantExpression) {
                atAttrs.put(name, ((AnnotationConstantExpression)expr).getValue());
                continue;
            }
            if (expr instanceof ConstantExpression) {
                constantAttrs.put(name, ((ConstantExpression)expr).getValue());
                continue;
            }
            if (expr instanceof ClassExpression) {
                constantAttrs.put(name, Type.getType(BytecodeHelper.getTypeDescription(expr.getType())));
                continue;
            }
            if (expr instanceof PropertyExpression) {
                enumAttrs.put(name, (PropertyExpression)expr);
                continue;
            }
            if (expr instanceof ListExpression) {
                arrayAttrs.put(name, (ListExpression)expr);
                continue;
            }
            if (!(expr instanceof ClosureExpression)) continue;
            ClassNode closureClass = this.controller.getClosureWriter().getOrAddClosureClass((ClosureExpression)expr, 1);
            constantAttrs.put(name, Type.getType(BytecodeHelper.getTypeDescription(closureClass)));
        }
        for (Map.Entry<String, Expression> entry : constantAttrs.entrySet()) {
            av.visit(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Expression> entry : enumAttrs.entrySet()) {
            PropertyExpression propExp = (PropertyExpression)entry.getValue();
            av.visitEnum(entry.getKey(), BytecodeHelper.getTypeDescription(propExp.getObjectExpression().getType()), String.valueOf(((ConstantExpression)propExp.getProperty()).getValue()));
        }
        for (Map.Entry<String, Expression> entry : atAttrs.entrySet()) {
            AnnotationNode atNode = (AnnotationNode)((Object)entry.getValue());
            AnnotationVisitor av2 = av.visitAnnotation(entry.getKey(), BytecodeHelper.getTypeDescription(atNode.getClassNode()));
            this.visitAnnotationAttributes(atNode, av2);
            av2.visitEnd();
        }
        this.visitArrayAttributes(an, arrayAttrs, av);
    }

    private void visitArrayAttributes(AnnotationNode an, Map<String, ListExpression> arrayAttr, AnnotationVisitor av) {
        if (arrayAttr.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ListExpression> entry : arrayAttr.entrySet()) {
            AnnotationVisitor av2 = av.visitArray(entry.getKey());
            List<Expression> values = entry.getValue().getExpressions();
            if (!values.isEmpty()) {
                int arrayElementType = AsmClassGenerator.determineCommonArrayType(values);
                for (Expression exprChild : values) {
                    this.visitAnnotationArrayElement(exprChild, arrayElementType, av2);
                }
            }
            av2.visitEnd();
        }
    }

    private void visitAnnotationArrayElement(Expression expr, int arrayElementType, AnnotationVisitor av) {
        switch (arrayElementType) {
            case 1: {
                AnnotationNode atAttr = (AnnotationNode)((AnnotationConstantExpression)expr).getValue();
                AnnotationVisitor av2 = av.visitAnnotation(null, BytecodeHelper.getTypeDescription(atAttr.getClassNode()));
                this.visitAnnotationAttributes(atAttr, av2);
                av2.visitEnd();
                break;
            }
            case 2: {
                av.visit(null, ((ConstantExpression)expr).getValue());
                break;
            }
            case 3: {
                ClassNode type = expr.getType();
                if (expr instanceof ClosureExpression) {
                    type = this.controller.getClosureWriter().getOrAddClosureClass((ClosureExpression)expr, 1);
                }
                av.visit(null, Type.getType(BytecodeHelper.getTypeDescription(type)));
                break;
            }
            case 4: {
                PropertyExpression propExpr = (PropertyExpression)expr;
                av.visitEnum(null, BytecodeHelper.getTypeDescription(propExpr.getObjectExpression().getType()), String.valueOf(((ConstantExpression)propExpr.getProperty()).getValue()));
            }
        }
    }

    public boolean addInnerClass(ClassNode innerClass) {
        ModuleNode mn = this.controller.getClassNode().getModule();
        innerClass.setModule(mn);
        mn.getUnit().addGeneratedInnerClass((InnerClassNode)innerClass);
        return this.innerClasses.add(innerClass);
    }

    public static int argumentSize(Expression arguments) {
        if (arguments instanceof TupleExpression) {
            TupleExpression tupleExpression = (TupleExpression)arguments;
            int size = tupleExpression.getExpressions().size();
            return size;
        }
        return 1;
    }

    private static String[] buildExceptions(ClassNode[] exceptions) {
        if (exceptions == null) {
            return null;
        }
        return (String[])Arrays.stream(exceptions).map(BytecodeHelper::getClassInternalName).toArray(String[]::new);
    }

    private static boolean containsOnlyConstants(ListExpression list) {
        for (Expression exp : list.getExpressions()) {
            if (exp instanceof ConstantExpression) continue;
            return false;
        }
        return true;
    }

    public static boolean containsSpreadExpression(Expression arguments) {
        List<Expression> args;
        if (arguments instanceof TupleExpression) {
            TupleExpression tupleExpression = (TupleExpression)arguments;
            args = tupleExpression.getExpressions();
        } else if (arguments instanceof ListExpression) {
            ListExpression le = (ListExpression)arguments;
            args = le.getExpressions();
        } else {
            return arguments instanceof SpreadExpression;
        }
        for (Expression arg : args) {
            if (!(arg instanceof SpreadExpression)) continue;
            return true;
        }
        return false;
    }

    public void despreadList(List<Expression> expressions, boolean wrap) {
        ArrayList<Expression> spreadIndexes = new ArrayList<Expression>();
        ArrayList<Expression> spreadExpressions = new ArrayList<Expression>();
        ArrayList<Expression> normalArguments = new ArrayList<Expression>();
        int n = expressions.size();
        for (int i = 0; i < n; ++i) {
            Expression expr = expressions.get(i);
            if (!(expr instanceof SpreadExpression)) {
                normalArguments.add(expr);
                continue;
            }
            spreadIndexes.add(new ConstantExpression(i - spreadExpressions.size(), true));
            spreadExpressions.add(((SpreadExpression)expr).getExpression());
        }
        this.visitTupleExpression(new ArgumentListExpression(normalArguments), wrap);
        new TupleExpression(spreadExpressions).visit(this);
        new ArrayExpression(ClassHelper.int_TYPE, spreadIndexes, null).visit(this);
        this.controller.getOperandStack().remove(1);
        despreadList.call(this.controller.getMethodVisitor());
    }

    private static int determineCommonArrayType(List<Expression> values) {
        Expression expr = values.get(0);
        int arrayElementType = -1;
        if (expr instanceof AnnotationConstantExpression) {
            arrayElementType = 1;
        } else if (expr instanceof ConstantExpression) {
            arrayElementType = 2;
        } else if (expr instanceof ClassExpression || expr instanceof ClosureExpression) {
            arrayElementType = 3;
        } else if (expr instanceof PropertyExpression) {
            arrayElementType = 4;
        }
        return arrayElementType;
    }

    private void doPostVisit(ASTNode node) {
        Consumer callback = (Consumer)node.getNodeMetaData("classgen.callback");
        if (callback != null) {
            callback.accept(this.controller);
        }
    }

    @Deprecated
    public static boolean isThisExpression(Expression expression) {
        return ExpressionUtils.isThisExpression(expression);
    }

    @Deprecated
    public static boolean isSuperExpression(Expression expression) {
        return ExpressionUtils.isSuperExpression(expression);
    }

    @Deprecated
    public static boolean isNullConstant(Expression expression) {
        return ExpressionUtils.isNullConstant(expression);
    }

    private void loadThis(VariableExpression thisOrSuper) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitVarInsn(25, 0);
        OperandStack operandStack = this.controller.getOperandStack();
        if (this.controller.isInGeneratedFunction() && !this.controller.getCompileStack().isImplicitThis()) {
            mv.visitMethodInsn(182, "groovy/lang/Closure", "getThisObject", "()Ljava/lang/Object;", false);
            ClassNode expectedType = this.controller.getTypeChooser().resolveType(thisOrSuper, this.controller.getOutermostClass());
            if (!ClassHelper.isObjectType(expectedType) && !ClassHelper.isPrimitiveType(expectedType)) {
                BytecodeHelper.doCast(mv, expectedType);
                operandStack.push(expectedType);
            } else {
                operandStack.push(ClassHelper.OBJECT_TYPE);
            }
        } else {
            operandStack.push(this.controller.getClassNode());
        }
    }

    public void loadWrapper(Expression argument) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        ClassNode goalClass = argument.getType();
        this.visitClassExpression(new ClassExpression(goalClass));
        if (goalClass.isDerivedFromGroovyObject()) {
            createGroovyObjectWrapperMethod.call(mv);
        } else {
            createPojoWrapperMethod.call(mv);
        }
        this.controller.getOperandStack().remove(1);
    }

    public void onLineNumber(ASTNode statement, String message) {
        if (statement == null || statement instanceof BlockStatement) {
            return;
        }
        this.currentASTNode = statement;
        int line = statement.getLineNumber();
        if (line == this.controller.getLineNumber()) {
            return;
        }
        this.controller.visitLineNumber(line);
    }

    public void throwException(String message) {
        throw new RuntimeParserException(message, this.currentASTNode);
    }
}

