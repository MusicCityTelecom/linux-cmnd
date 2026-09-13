/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.MetaClass;
import groovy.transform.CompileStatic;
import groovy.transform.Generated;
import groovy.transform.Internal;
import groovy.transform.NonSealed;
import groovy.transform.Sealed;
import groovy.transform.stc.POJO;
import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.Opcodes;
import java.beans.Transient;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.groovy.ast.tools.AnnotatedNodeUtils;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ConstructorNodeUtils;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.apache.groovy.ast.tools.MethodNodeUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CodeVisitorSupport;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.DynamicVariable;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.GroovyClassVisitor;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.VariableScope;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.BinaryExpression;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ClosureExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.FieldExpression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.BlockStatement;
import org.codehaus.groovy.ast.stmt.ExpressionStatement;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.stmt.Statement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.ast.tools.GenericsUtils;
import org.codehaus.groovy.ast.tools.PropertyNodeUtils;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.BytecodeInstruction;
import org.codehaus.groovy.classgen.BytecodeSequence;
import org.codehaus.groovy.classgen.FinalVariableAnalyzer;
import org.codehaus.groovy.classgen.ReturnAdder;
import org.codehaus.groovy.classgen.VerifierCodeVisitor;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.MopWriter;
import org.codehaus.groovy.classgen.asm.OptimizingStatementWriter;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.reflection.ClassInfo;
import org.codehaus.groovy.syntax.RuntimeParserException;
import org.codehaus.groovy.syntax.Token;
import org.codehaus.groovy.transform.trait.Traits;

public class Verifier
implements GroovyClassVisitor,
Opcodes {
    public static final String SWAP_INIT = "__$swapInit";
    public static final String STATIC_METACLASS_BOOL = "__$stMC";
    public static final String INITIAL_EXPRESSION = "INITIAL_EXPRESSION";
    public static final String DEFAULT_PARAMETER_GENERATED = "DEFAULT_PARAMETER_GENERATED";
    private static final ClassNode GENERATED_ANNOTATION = ClassHelper.make(Generated.class);
    private static final ClassNode INTERNAL_ANNOTATION = ClassHelper.make(Internal.class);
    private static final ClassNode TRANSIENT_ANNOTATION = ClassHelper.make(Transient.class);
    public static final String __TIMESTAMP = "__timeStamp";
    public static final String __TIMESTAMP__ = "__timeStamp__239_neverHappen";
    private static final Class<Sealed> SEALED_TYPE = Sealed.class;
    private static final Class<NonSealed> NON_SEALED_TYPE = NonSealed.class;
    private static final Parameter[] SET_METACLASS_PARAMS = new Parameter[]{new Parameter(ClassHelper.METACLASS_TYPE, "mc")};
    private ClassNode classNode;
    private MethodNode methodNode;
    private static final String[] INVALID_COMPONENTS = new String[]{"clone", "finalize", "getClass", "hashCode", "notify", "notifyAll", "toString", "wait"};

    public ClassNode getClassNode() {
        return this.classNode;
    }

    protected void setClassNode(ClassNode classNode) {
        this.classNode = classNode;
    }

    public MethodNode getMethodNode() {
        return this.methodNode;
    }

    private static FieldNode getMetaClassField(ClassNode node) {
        FieldNode metaClassField = node.getDeclaredField("metaClass");
        if (metaClassField != null) {
            ClassNode mcFieldType = metaClassField.getType();
            if (!mcFieldType.equals(ClassHelper.METACLASS_TYPE)) {
                throw new RuntimeParserException("The class " + node.getName() + " cannot declare field 'metaClass' of type " + mcFieldType.getName() + " as it needs to be of the type " + ClassHelper.METACLASS_TYPE.getName() + " for internal groovy purposes", metaClassField);
            }
            return metaClassField;
        }
        ClassNode current = node;
        while (!ClassHelper.isObjectType(current) && (current = current.getSuperClass()) != null) {
            metaClassField = current.getDeclaredField("metaClass");
            if (metaClassField == null || Modifier.isPrivate(metaClassField.getModifiers())) continue;
            return metaClassField;
        }
        return null;
    }

    private static FieldNode setMetaClassFieldIfNotExists(ClassNode node, FieldNode metaClassField) {
        if (metaClassField == null) {
            String classInternalName = BytecodeHelper.getClassInternalName(node);
            metaClassField = node.addField("metaClass", 4226, ClassHelper.METACLASS_TYPE, GeneralUtils.bytecodeX(ClassHelper.METACLASS_TYPE, mv -> {
                mv.visitVarInsn(25, 0);
                mv.visitMethodInsn(182, classInternalName, "$getStaticMetaClass", "()Lgroovy/lang/MetaClass;", false);
            }));
            metaClassField.setSynthetic(true);
        }
        return metaClassField;
    }

    @Override
    public void visitClass(ClassNode node) {
        boolean skipGroovify;
        this.classNode = node;
        if (node.isRecord()) {
            Verifier.detectInvalidRecordComponentNames(node);
            if (node.isAbstract()) {
                throw new RuntimeParserException("Record '" + node.getNameWithoutPackage() + "' must not be abstract", node);
            }
        }
        Verifier.checkForDuplicateInterfaces(node);
        if (node.isInterface() || Traits.isTrait(node)) {
            this.addInitialization(node, new ConstructorNode(0, null));
            node.visitContents(this);
            if (node.getNodeMetaData(OptimizingStatementWriter.ClassNodeSkip.class) == null) {
                node.setNodeMetaData(OptimizingStatementWriter.ClassNodeSkip.class, Boolean.TRUE);
            }
            return;
        }
        this.addDefaultParameterMethods(node);
        this.addDefaultParameterConstructors(node);
        boolean bl = skipGroovify = AnnotatedNodeUtils.hasAnnotation(node, ClassHelper.make(POJO.class)) && AnnotatedNodeUtils.hasAnnotation(node, ClassHelper.make(CompileStatic.class));
        if (!skipGroovify) {
            String classInternalName = BytecodeHelper.getClassInternalName(node);
            this.addStaticMetaClassField(node, classInternalName);
            Verifier.addFastPathHelperFieldsAndHelperMethod(node, classInternalName);
            if (!node.isDerivedFrom(ClassHelper.GROOVY_OBJECT_SUPPORT_TYPE)) {
                this.addGroovyObjectInterfaceAndMethods(node, classInternalName);
            }
            Verifier.addGetLookupMethod(node);
        }
        this.addDefaultConstructor(node);
        this.addInitialization(node);
        Verifier.checkReturnInObjectInitializer(node.getObjectInitializerStatements());
        node.getObjectInitializerStatements().clear();
        node.visitContents(this);
        Verifier.checkForDuplicateMethods(node);
        this.addCovariantMethods(node);
        Verifier.detectNonSealedClasses(node);
        this.checkFinalVariables(node);
    }

    private static void detectInvalidRecordComponentNames(ClassNode node) {
        for (FieldNode fn : node.getFields()) {
            if (Arrays.binarySearch(INVALID_COMPONENTS, fn.getName()) < 0) continue;
            throw new RuntimeParserException("Illegal record component name '" + fn.getName() + "'", fn);
        }
    }

    private static void detectNonSealedClasses(ClassNode node) {
        if (Modifier.isFinal(node.getModifiers())) {
            return;
        }
        if (Boolean.TRUE.equals(node.getNodeMetaData(SEALED_TYPE))) {
            return;
        }
        if (Boolean.TRUE.equals(node.getNodeMetaData(NON_SEALED_TYPE))) {
            return;
        }
        boolean found = false;
        for (ClassNode sn = node.getSuperClass(); sn != null && !sn.equals(ClassHelper.OBJECT_TYPE); sn = sn.getSuperClass()) {
            if (!sn.isSealed()) continue;
            found = true;
            break;
        }
        if (found) {
            node.putNodeMetaData(NON_SEALED_TYPE, Boolean.TRUE);
        }
    }

    private void checkFinalVariables(ClassNode node) {
        FinalVariableAnalyzer visitor = new FinalVariableAnalyzer(null, this.getFinalVariablesCallback());
        visitor.visitClass(node);
    }

    protected FinalVariableAnalyzer.VariableNotFinalCallback getFinalVariablesCallback() {
        return new FinalVariableAnalyzer.VariableNotFinalCallback(){

            @Override
            public void variableNotFinal(Variable var, Expression bexp) {
                if (var instanceof VariableExpression) {
                    var = ((VariableExpression)var).getAccessedVariable();
                }
                if (var instanceof VariableExpression && Modifier.isFinal(var.getModifiers())) {
                    throw new RuntimeParserException("The variable [" + var.getName() + "] is declared final but is reassigned", bexp);
                }
                if (var instanceof Parameter && Modifier.isFinal(var.getModifiers())) {
                    throw new RuntimeParserException("The parameter [" + var.getName() + "] is declared final but is reassigned", bexp);
                }
            }

            @Override
            public void variableNotAlwaysInitialized(VariableExpression var) {
                if (Modifier.isFinal(var.getAccessedVariable().getModifiers())) {
                    throw new RuntimeParserException("The variable [" + var.getName() + "] may be uninitialized", var);
                }
            }
        };
    }

    private static Set<ClassNode> getAllInterfaces(ClassNode cn) {
        return GeneralUtils.getInterfacesAndSuperInterfaces(cn);
    }

    private static void checkForDuplicateInterfaces(ClassNode cn) {
        ClassNode[] interfaces = cn.getInterfaces();
        int nInterfaces = interfaces.length;
        if (nInterfaces == 0) {
            return;
        }
        if (nInterfaces > 1) {
            ArrayList<String> interfaceNames = new ArrayList<String>(nInterfaces);
            for (ClassNode in : interfaces) {
                interfaceNames.add(in.getName());
            }
            if (interfaceNames.size() != new HashSet(interfaceNames).size()) {
                throw new RuntimeParserException("Duplicate interfaces in implements list: " + interfaceNames, cn);
            }
        }
        ArrayList<Set<ClassNode>> allInterfaces = new ArrayList<Set<ClassNode>>(nInterfaces + 1);
        for (ClassNode in : interfaces) {
            allInterfaces.add(Verifier.getAllInterfaces(in));
        }
        allInterfaces.add(Verifier.getAllInterfaces(cn.getUnresolvedSuperClass()));
        if (nInterfaces == 1 && ((Set)allInterfaces.get(1)).isEmpty()) {
            return;
        }
        for (int i = 0; i < nInterfaces; ++i) {
            for (ClassNode in : (Set)allInterfaces.get(i)) {
                if (in.redirect().getGenericsTypes() == null) continue;
                block4: for (int j = i + 1; j < nInterfaces + 1; ++j) {
                    Set set = (Set)allInterfaces.get(j);
                    if (!set.contains(in)) continue;
                    for (ClassNode t : set) {
                        String two;
                        if (!t.equals(in)) continue;
                        String one = in.toString(false);
                        if (one.equals(two = t.toString(false))) continue block4;
                        throw new RuntimeParserException("The interface " + in.getNameWithoutPackage() + " cannot be implemented more than once with different arguments: " + one + " and " + two, cn);
                    }
                }
            }
        }
    }

    private static void checkForDuplicateMethods(ClassNode cn) {
        HashSet<String> descriptors = new HashSet<String>();
        for (MethodNode mn : cn.getMethods()) {
            if (mn.isSynthetic()) continue;
            String mySig = MethodNodeUtils.methodDescriptorWithoutReturnType(mn);
            if (descriptors.contains(mySig)) {
                if (mn.isScriptBody() || mySig.equals(Verifier.scriptBodySignatureWithoutReturnType(cn))) {
                    throw new RuntimeParserException("The method " + mn.getText() + " is a duplicate of the one declared for this script's body code", Verifier.sourceOf(mn));
                }
                throw new RuntimeParserException("The method " + mn.getText() + " duplicates another method of the same signature", Verifier.sourceOf(mn));
            }
            descriptors.add(mySig);
        }
    }

    private static String scriptBodySignatureWithoutReturnType(ClassNode cn) {
        for (MethodNode mn : cn.getMethods()) {
            if (!mn.isScriptBody()) continue;
            return MethodNodeUtils.methodDescriptorWithoutReturnType(mn);
        }
        return null;
    }

    private static FieldNode checkFieldDoesNotExist(ClassNode node, String fieldName) {
        FieldNode ret = node.getDeclaredField(fieldName);
        if (ret != null) {
            if (Modifier.isPublic(ret.getModifiers()) && ClassHelper.isPrimitiveBoolean(ret.getType().redirect())) {
                return ret;
            }
            throw new RuntimeParserException("The class " + node.getName() + " cannot declare field '" + fieldName + "' as this field is needed for internal groovy purposes", ret);
        }
        return null;
    }

    private static void addFastPathHelperFieldsAndHelperMethod(ClassNode node, String classInternalName) {
        if (Boolean.TRUE.equals(node.getNodeMetaData(OptimizingStatementWriter.ClassNodeSkip.class))) {
            return;
        }
        FieldNode stMCB = Verifier.checkFieldDoesNotExist(node, STATIC_METACLASS_BOOL);
        if (stMCB == null) {
            stMCB = node.addField(STATIC_METACLASS_BOOL, 4233, ClassHelper.boolean_TYPE, null);
            stMCB.setSynthetic(true);
        }
    }

    protected void addDefaultConstructor(ClassNode node) {
        if (!node.getDeclaredConstructors().isEmpty()) {
            return;
        }
        ConstructorNode constructor = new ConstructorNode(1, new BlockStatement());
        constructor.setHasNoRealSourcePosition(true);
        AnnotatedNodeUtils.markAsGenerated(node, constructor);
        node.addConstructor(constructor);
    }

    private static void addGetLookupMethod(ClassNode node) {
        int modifiers = 1;
        if (Modifier.isStatic(node.getModifiers()) || node.getOuterClass() == null) {
            modifiers |= 8;
        }
        node.addSyntheticMethod("$getLookup", modifiers, ClassHelper.make(MethodHandles.Lookup.class), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, new BytecodeSequence(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                mv.visitMethodInsn(184, "java/lang/invoke/MethodHandles", "lookup", "()Ljava/lang/invoke/MethodHandles$Lookup;", false);
                mv.visitInsn(176);
            }
        }));
    }

    private void addStaticMetaClassField(final ClassNode node, final String classInternalName) {
        String _staticClassInfoFieldName = "$staticClassInfo";
        while (node.getDeclaredField(_staticClassInfoFieldName) != null) {
            _staticClassInfoFieldName = _staticClassInfoFieldName + "$";
        }
        final String staticMetaClassFieldName = _staticClassInfoFieldName;
        FieldNode staticMetaClassField = node.addField(staticMetaClassFieldName, 4106, ClassHelper.make(ClassInfo.class, false), null);
        staticMetaClassField.setSynthetic(true);
        node.addSyntheticMethod("$getStaticMetaClass", 4, ClassHelper.make(MetaClass.class), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, new BytecodeSequence(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                mv.visitVarInsn(25, 0);
                mv.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                if (BytecodeHelper.isClassLiteralPossible(node) || BytecodeHelper.isSameCompilationUnit(Verifier.this.classNode, node)) {
                    BytecodeHelper.visitClassLiteral(mv, node);
                } else {
                    mv.visitMethodInsn(184, classInternalName, "$get$$class$" + classInternalName.replace('/', '$'), "()Ljava/lang/Class;", false);
                }
                Label l1 = new Label();
                mv.visitJumpInsn(165, l1);
                mv.visitVarInsn(25, 0);
                mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/ScriptBytecodeAdapter", "initMetaClass", "(Ljava/lang/Object;)Lgroovy/lang/MetaClass;", false);
                mv.visitInsn(176);
                mv.visitLabel(l1);
                mv.visitFieldInsn(178, classInternalName, staticMetaClassFieldName, "Lorg/codehaus/groovy/reflection/ClassInfo;");
                mv.visitVarInsn(58, 1);
                mv.visitVarInsn(25, 1);
                Label l0 = new Label();
                mv.visitJumpInsn(199, l0);
                mv.visitVarInsn(25, 0);
                mv.visitMethodInsn(182, "java/lang/Object", "getClass", "()Ljava/lang/Class;", false);
                mv.visitMethodInsn(184, "org/codehaus/groovy/reflection/ClassInfo", "getClassInfo", "(Ljava/lang/Class;)Lorg/codehaus/groovy/reflection/ClassInfo;", false);
                mv.visitInsn(89);
                mv.visitVarInsn(58, 1);
                mv.visitFieldInsn(179, classInternalName, staticMetaClassFieldName, "Lorg/codehaus/groovy/reflection/ClassInfo;");
                mv.visitLabel(l0);
                mv.visitVarInsn(25, 1);
                mv.visitMethodInsn(182, "org/codehaus/groovy/reflection/ClassInfo", "getMetaClass", "()Lgroovy/lang/MetaClass;", false);
                mv.visitInsn(176);
            }
        }));
    }

    protected void addGroovyObjectInterfaceAndMethods(ClassNode node, final String classInternalName) {
        MethodNode methodNode;
        boolean shouldAnnotate;
        if (!node.isDerivedFromGroovyObject()) {
            node.addInterface(ClassHelper.GROOVY_OBJECT_TYPE);
        }
        FieldNode metaClassField = Verifier.getMetaClassField(node);
        boolean bl = shouldAnnotate = this.classNode.getModule().getContext() != null;
        if (!node.hasMethod("getMetaClass", Parameter.EMPTY_ARRAY)) {
            metaClassField = Verifier.setMetaClassFieldIfNotExists(node, metaClassField);
            BytecodeSequence getMetaClassCode = new BytecodeSequence(new BytecodeInstruction(){

                @Override
                public void visit(MethodVisitor mv) {
                    Label nullLabel = new Label();
                    mv.visitVarInsn(25, 0);
                    mv.visitFieldInsn(180, classInternalName, "metaClass", "Lgroovy/lang/MetaClass;");
                    mv.visitInsn(89);
                    mv.visitJumpInsn(198, nullLabel);
                    mv.visitInsn(176);
                    mv.visitLabel(nullLabel);
                    mv.visitInsn(87);
                    mv.visitVarInsn(25, 0);
                    mv.visitInsn(89);
                    mv.visitMethodInsn(182, classInternalName, "$getStaticMetaClass", "()Lgroovy/lang/MetaClass;", false);
                    mv.visitFieldInsn(181, classInternalName, "metaClass", "Lgroovy/lang/MetaClass;");
                    mv.visitVarInsn(25, 0);
                    mv.visitFieldInsn(180, classInternalName, "metaClass", "Lgroovy/lang/MetaClass;");
                    mv.visitInsn(176);
                }
            });
            methodNode = this.addMethod(node, !shouldAnnotate, "getMetaClass", 1, ClassHelper.METACLASS_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, getMetaClassCode);
            if (shouldAnnotate) {
                methodNode.addAnnotation(GENERATED_ANNOTATION);
                methodNode.addAnnotation(INTERNAL_ANNOTATION);
                methodNode.addAnnotation(TRANSIENT_ANNOTATION);
            }
        }
        if (!node.hasMethod("setMetaClass", SET_METACLASS_PARAMS)) {
            Statement setMetaClassCode = Modifier.isFinal((metaClassField = Verifier.setMetaClassFieldIfNotExists(node, metaClassField)).getModifiers()) ? GeneralUtils.stmt(GeneralUtils.ctorX(ClassHelper.make(IllegalArgumentException.class), GeneralUtils.constX("cannot set read-only meta class"))) : new BytecodeSequence(new BytecodeInstruction(){

                @Override
                public void visit(MethodVisitor mv) {
                    mv.visitVarInsn(25, 0);
                    mv.visitVarInsn(25, 1);
                    mv.visitFieldInsn(181, classInternalName, "metaClass", "Lgroovy/lang/MetaClass;");
                    mv.visitInsn(177);
                }
            });
            methodNode = this.addMethod(node, !shouldAnnotate, "setMetaClass", 1, ClassHelper.VOID_TYPE, SET_METACLASS_PARAMS, ClassNode.EMPTY_ARRAY, setMetaClassCode);
            if (shouldAnnotate) {
                methodNode.addAnnotation(GENERATED_ANNOTATION);
                methodNode.addAnnotation(INTERNAL_ANNOTATION);
            }
        }
    }

    protected MethodNode addMethod(ClassNode node, boolean shouldBeSynthetic, String name, int modifiers, ClassNode returnType, Parameter[] parameters, ClassNode[] exceptions, Statement code) {
        if (shouldBeSynthetic) {
            return node.addSyntheticMethod(name, modifiers, returnType, parameters, exceptions, code);
        }
        return node.addMethod(name, modifiers & 0xFFFFEFFF, returnType, parameters, exceptions, code);
    }

    @Deprecated
    protected void addTimeStamp(ClassNode node) {
    }

    private static void checkReturnInObjectInitializer(List<Statement> init) {
        CodeVisitorSupport visitor = new CodeVisitorSupport(){

            @Override
            public void visitClosureExpression(ClosureExpression expression) {
            }

            @Override
            public void visitReturnStatement(ReturnStatement statement) {
                throw new RuntimeParserException("'return' is not allowed in object initializer", statement);
            }
        };
        for (Statement stmt : init) {
            stmt.visit(visitor);
        }
    }

    @Override
    public void visitConstructor(final ConstructorNode node) {
        Statement stmt = node.getCode();
        if (stmt != null) {
            stmt.visit(new VerifierCodeVisitor(this.getClassNode()));
            stmt.visit(new CodeVisitorSupport(){
                private boolean inClosure;
                private boolean inSpecialConstructorCall;

                @Override
                public void visitClosureExpression(ClosureExpression ce) {
                    boolean oldInClosure = this.inClosure;
                    this.inClosure = true;
                    super.visitClosureExpression(ce);
                    this.inClosure = oldInClosure;
                }

                @Override
                public void visitConstructorCallExpression(ConstructorCallExpression cce) {
                    boolean oldIsSpecialConstructorCall = this.inSpecialConstructorCall;
                    this.inSpecialConstructorCall |= cce.isSpecialCall();
                    super.visitConstructorCallExpression(cce);
                    this.inSpecialConstructorCall = oldIsSpecialConstructorCall;
                }

                @Override
                public void visitMethodCallExpression(MethodCallExpression mce) {
                    if (this.inSpecialConstructorCall && this.isThisObjectExpression(mce)) {
                        MethodNode methodTarget = mce.getMethodTarget();
                        if (methodTarget == null || !methodTarget.isStatic() && !Verifier.this.classNode.getOuterClasses().contains(methodTarget.getDeclaringClass())) {
                            if (!mce.isImplicitThis()) {
                                throw this.newVariableError(mce.getObjectExpression().getText(), mce.getObjectExpression());
                            }
                            throw this.newVariableError(mce.getMethodAsString(), mce.getMethod());
                        }
                        mce.getMethod().visit(this);
                        mce.getArguments().visit(this);
                    } else {
                        super.visitMethodCallExpression(mce);
                    }
                }

                @Override
                public void visitVariableExpression(VariableExpression ve) {
                    if (this.inSpecialConstructorCall && (ve.isThisExpression() || ve.isSuperExpression() || this.isNonStaticMemberAccess(ve))) {
                        throw this.newVariableError(ve.getName(), ve.getLineNumber() > 0 ? ve : node);
                    }
                }

                private boolean isNonStaticMemberAccess(VariableExpression ve) {
                    Variable variable = ve.getAccessedVariable();
                    return !this.inClosure && variable != null && !Modifier.isStatic(variable.getModifiers()) && !(variable instanceof DynamicVariable) && !(variable instanceof Parameter);
                }

                private boolean isThisObjectExpression(MethodCallExpression mce) {
                    if (mce.isImplicitThis()) {
                        return true;
                    }
                    if (mce.getObjectExpression() instanceof VariableExpression) {
                        VariableExpression var = (VariableExpression)mce.getObjectExpression();
                        return var.isThisExpression() || var.isSuperExpression();
                    }
                    return false;
                }

                private GroovyRuntimeException newVariableError(String name, ASTNode node2) {
                    RuntimeParserException rpe = new RuntimeParserException("Cannot reference '" + name + "' before supertype constructor has been called. Possible causes:\nYou attempted to access an instance field, method, or property.\nYou attempted to construct a non-static inner class.", node2);
                    rpe.setModule(Verifier.this.getClassNode().getModule());
                    return rpe;
                }
            });
        }
    }

    @Override
    public void visitMethod(MethodNode node) {
        if (MopWriter.isMopMethod(node.getName())) {
            throw new RuntimeParserException("Found unexpected MOP methods in the class node for " + this.classNode.getName() + "(" + node.getName() + ")", this.classNode);
        }
        Verifier.adjustTypesIfStaticMainMethod(node);
        this.methodNode = node;
        this.addReturnIfNeeded(node);
        Statement stmt = node.getCode();
        if (stmt != null) {
            stmt.visit(new VerifierCodeVisitor(this.getClassNode()));
        }
    }

    private static void adjustTypesIfStaticMainMethod(MethodNode node) {
        Parameter param;
        Parameter[] params;
        if (node.getName().equals("main") && node.isStatic() && (params = node.getParameters()).length == 1 && ((param = params[0]).getType() == null || ClassHelper.isObjectType(param.getType()))) {
            param.setType(ClassHelper.STRING_TYPE.makeArray());
            ClassNode returnType = node.getReturnType();
            if (ClassHelper.isObjectType(returnType)) {
                node.setReturnType(ClassHelper.VOID_TYPE);
            }
        }
    }

    protected void addReturnIfNeeded(MethodNode node) {
        ReturnAdder adder = new ReturnAdder();
        adder.visitMethod(node);
    }

    @Override
    public void visitField(FieldNode node) {
    }

    private boolean methodNeedsReplacement(MethodNode m) {
        if (m == null) {
            return true;
        }
        if (m.getDeclaringClass() == this.getClassNode()) {
            return false;
        }
        return !Modifier.isFinal(m.getModifiers());
    }

    @Override
    public void visitProperty(PropertyNode node) {
        Statement setterBlock;
        String name = node.getName();
        FieldNode field = node.getField();
        String getterName = node.getGetterName();
        if (getterName == null) {
            getterName = "get" + Verifier.capitalize(name);
        }
        String setterName = node.getSetterNameOrDefault();
        int accessorModifiers = PropertyNodeUtils.adjustPropertyModifiersForMethod(node);
        Statement getterBlock = node.getGetterBlock();
        if (getterBlock == null) {
            MethodNode getter = this.classNode.getGetterMethod(getterName, !node.isStatic());
            if (getter == null && ClassHelper.isPrimitiveBoolean(node.getType())) {
                getter = this.classNode.getGetterMethod("is" + Verifier.capitalize(name));
            }
            if (!node.isPrivate() && this.methodNeedsReplacement(getter)) {
                getterBlock = this.createGetterBlock(node, field);
            }
        }
        if ((setterBlock = node.getSetterBlock()) == null) {
            MethodNode setter = this.classNode.getSetterMethod(setterName, false);
            if ((accessorModifiers & 0x12) == 0 && this.methodNeedsReplacement(setter)) {
                setterBlock = this.createSetterBlock(node, field);
            }
        }
        int getterModifiers = accessorModifiers;
        if (node.isStatic()) {
            getterModifiers &= 0xFFFFFFEF;
        }
        if (getterBlock != null) {
            String altGetterName;
            MethodNode altGetter;
            this.visitGetter(node, field, getterBlock, getterModifiers, getterName);
            if (node.getGetterName() == null && getterName.startsWith("get") && ClassHelper.isPrimitiveBoolean(node.getType()) && this.methodNeedsReplacement(altGetter = this.classNode.getGetterMethod(altGetterName = "is" + Verifier.capitalize(name), !node.isStatic()))) {
                this.visitGetter(node, field, getterBlock, getterModifiers, altGetterName);
            }
        }
        if (setterBlock != null) {
            Parameter[] setterParameterTypes = new Parameter[]{new Parameter(node.getType(), "value")};
            MethodNode setter = new MethodNode(setterName, accessorModifiers, ClassHelper.VOID_TYPE, setterParameterTypes, ClassNode.EMPTY_ARRAY, setterBlock);
            setter.setSynthetic(true);
            this.addPropertyMethod(setter);
            if (!field.isSynthetic()) {
                Verifier.copyAnnotations(node, setter);
            }
            this.visitMethod(setter);
        }
    }

    private void visitGetter(PropertyNode node, FieldNode field, Statement getterBlock, int getterModifiers, String getterName) {
        MethodNode getter = new MethodNode(getterName, getterModifiers, node.getType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, getterBlock);
        getter.setSynthetic(true);
        this.addPropertyMethod(getter);
        if (this.classNode.getNodeMetaData("_RECORD_HEADER") != null || !field.isSynthetic()) {
            Verifier.copyAnnotations(node, getter);
        }
        this.visitMethod(getter);
    }

    private static void copyAnnotations(PropertyNode node, MethodNode accessor) {
        accessor.addAnnotations(node.getAnnotations());
        accessor.putNodeMetaData("_SKIPPABLE_ANNOTATIONS", true);
    }

    protected void addPropertyMethod(MethodNode method) {
        this.classNode.addMethod(method);
        AnnotatedNodeUtils.markAsGenerated(this.classNode, method);
        String methodName = method.getName();
        Parameter[] parameters = method.getParameters();
        ClassNode methodReturnType = method.getReturnType();
        for (MethodNode node : this.classNode.getAbstractMethods()) {
            ClassNode nodeReturnType;
            if (!node.getName().equals(methodName) || !node.getDeclaringClass().equals(this.classNode) || node.getParameters().length != parameters.length) continue;
            if (parameters.length == 1) {
                ClassNode abstractMethodParameterType = node.getParameters()[0].getType();
                ClassNode methodParameterType = parameters[0].getType();
                if (!methodParameterType.isDerivedFrom(abstractMethodParameterType) && !methodParameterType.implementsInterface(abstractMethodParameterType)) continue;
            }
            if (!methodReturnType.isDerivedFrom(nodeReturnType = node.getReturnType()) && !methodReturnType.implementsInterface(nodeReturnType)) continue;
            node.setModifiers(node.getModifiers() ^ 0x400);
            node.setCode(method.getCode());
        }
    }

    protected void addDefaultParameterMethods(ClassNode type) {
        ArrayList<MethodNode> methods = new ArrayList<MethodNode>(type.getMethods());
        this.addDefaultParameters(methods, (ArgumentListExpression arguments, Parameter[] params, MethodNode method) -> {
            final BlockStatement block = new BlockStatement();
            final MethodNode newMethod = new MethodNode(method.getName(), method.getModifiers() & 0xFFFFFBFF, method.getReturnType(), params, method.getExceptions(), block);
            MethodNode oldMethod = type.getDeclaredMethod(method.getName(), params);
            if (oldMethod != null) {
                throw new RuntimeParserException("The method with default parameters \"" + method.getTypeDescriptor() + "\" defines a method \"" + newMethod.getTypeDescriptor() + "\" that is already defined.", Verifier.sourceOf(method));
            }
            newMethod.addAnnotations(method.getAnnotations());
            newMethod.setGenericsTypes(method.getGenericsTypes());
            CodeVisitorSupport visitor = new CodeVisitorSupport(){
                private boolean inClosure;

                @Override
                public void visitClosureExpression(ClosureExpression e) {
                    boolean prev = this.inClosure;
                    this.inClosure = true;
                    super.visitClosureExpression(e);
                    this.inClosure = prev;
                }

                @Override
                public void visitVariableExpression(VariableExpression e) {
                    Parameter p;
                    if (e.getAccessedVariable() instanceof Parameter && (p = (Parameter)e.getAccessedVariable()).hasInitialExpression() && !Arrays.asList(params).contains(p)) {
                        VariableScope blockScope = block.getVariableScope();
                        VariableExpression localVariable = (VariableExpression)blockScope.getDeclaredVariable(p.getName());
                        if (localVariable == null) {
                            localVariable = GeneralUtils.localVarX(p.getName(), p.getType());
                            localVariable.setModifiers(p.getModifiers());
                            blockScope.putDeclaredVariable(localVariable);
                            localVariable.setInStaticContext(blockScope.isInStaticContext());
                            block.addStatement(GeneralUtils.declS(localVariable, p.getInitialExpression()));
                        }
                        if (!localVariable.isClosureSharedVariable()) {
                            localVariable.setClosureSharedVariable(this.inClosure);
                        }
                    }
                }
            };
            visitor.visitArgumentlistExpression(arguments);
            ListIterator<Expression> it = arguments.getExpressions().listIterator();
            block0: while (it.hasNext()) {
                Expression argument = it.next();
                if (argument instanceof CastExpression) {
                    argument = ((CastExpression)argument).getExpression();
                }
                for (Parameter p : method.getParameters()) {
                    if (!p.hasInitialExpression() || p.getInitialExpression() != argument) continue;
                    if (block.getVariableScope().getDeclaredVariable(p.getName()) == null) continue block0;
                    it.set(GeneralUtils.varX(p.getName()));
                    continue block0;
                }
            }
            MethodCallExpression call = GeneralUtils.callThisX(method.getName(), arguments);
            call.setMethodTarget(method);
            call.setImplicitThis(true);
            if (method.isVoidMethod()) {
                block.addStatement(new ExpressionStatement(call));
            } else {
                block.addStatement(new ReturnStatement(call));
            }
            visitor = new CodeVisitorSupport(){

                @Override
                public void visitConstructorCallExpression(ConstructorCallExpression call) {
                    if (call.isUsingAnonymousInnerClass()) {
                        call.getType().setEnclosingMethod(newMethod);
                    }
                    super.visitConstructorCallExpression(call);
                }
            };
            visitor.visitBlockStatement(block);
            this.addPropertyMethod(newMethod);
            newMethod.putNodeMetaData(DEFAULT_PARAMETER_GENERATED, Boolean.TRUE);
        });
    }

    protected void addDefaultParameterConstructors(final ClassNode type) {
        ArrayList<ConstructorNode> constructors = new ArrayList<ConstructorNode>(type.getDeclaredConstructors());
        this.addDefaultParameters(constructors, (ArgumentListExpression arguments, Parameter[] params, MethodNode method) -> {
            final List<Parameter> paramList = Arrays.asList(params);
            ListIterator<Expression> it = arguments.getExpressions().listIterator();
            while (it.hasNext()) {
                Parameter p;
                VariableExpression v;
                Expression argument = it.next();
                if (argument instanceof CastExpression) {
                    argument = ((CastExpression)argument).getExpression();
                }
                if (!(argument instanceof VariableExpression) || !((v = (VariableExpression)argument).getAccessedVariable() instanceof Parameter) || !(p = (Parameter)v.getAccessedVariable()).hasInitialExpression() || paramList.contains(p) || !(p.getInitialExpression() instanceof ConstantExpression)) continue;
                it.set(GeneralUtils.castX(method.getParameters()[it.nextIndex() - 1].getType(), p.getInitialExpression()));
            }
            CodeVisitorSupport visitor = new CodeVisitorSupport(){

                @Override
                public void visitVariableExpression(VariableExpression e) {
                    Parameter p;
                    if (e.getAccessedVariable() instanceof Parameter && (p = (Parameter)e.getAccessedVariable()).hasInitialExpression() && !paramList.contains(p)) {
                        String error = String.format("The generated constructor \"%s(%s)\" references parameter '%s' which has been replaced by a default value expression.", type.getNameWithoutPackage(), Arrays.stream(params).map(Parameter::getType).map(ClassNodeUtils::formatTypeName).collect(Collectors.joining(",")), p.getName());
                        throw new RuntimeParserException(error, Verifier.sourceOf(method));
                    }
                }
            };
            visitor.visitArgumentlistExpression(arguments);
            ExpressionStatement code = new ExpressionStatement(new ConstructorCallExpression(ClassNode.THIS, arguments));
            this.addConstructor(params, (ConstructorNode)method, code, type);
        });
    }

    protected void addConstructor(Parameter[] newParams, ConstructorNode ctor, Statement code, ClassNode type) {
        final ConstructorNode newConstructor = type.addConstructor(ctor.getModifiers(), newParams, ctor.getExceptions(), code);
        newConstructor.putNodeMetaData(DEFAULT_PARAMETER_GENERATED, Boolean.TRUE);
        AnnotatedNodeUtils.markAsGenerated(type, newConstructor);
        code.visit(new CodeVisitorSupport(){

            @Override
            public void visitConstructorCallExpression(ConstructorCallExpression call) {
                if (call.isUsingAnonymousInnerClass()) {
                    call.getType().setEnclosingMethod(newConstructor);
                }
                super.visitConstructorCallExpression(call);
            }
        });
    }

    protected void addDefaultParameters(List<? extends MethodNode> methods, DefaultArgsAction action) {
        for (MethodNode methodNode : methods) {
            if (!methodNode.hasDefaultValue()) continue;
            this.addDefaultParameters(action, methodNode);
        }
    }

    protected void addDefaultParameters(DefaultArgsAction action, MethodNode method) {
        Parameter[] parameters = method.getParameters();
        long n = Arrays.stream(parameters).filter(Parameter::hasInitialExpression).count();
        int i = 1;
        while ((long)i <= n) {
            Parameter[] newParams = new Parameter[parameters.length - i];
            ArgumentListExpression arguments = new ArgumentListExpression();
            int index = 0;
            int j = 1;
            for (Parameter parameter : parameters) {
                Expression e;
                if (parameter == null) {
                    throw new GroovyBugError("Parameter should not be null for method " + this.methodNode.getName());
                }
                if ((long)j > n - (long)i && parameter.hasInitialExpression()) {
                    e = parameter.getInitialExpression();
                } else {
                    newParams[index++] = parameter;
                    e = GeneralUtils.varX(parameter);
                }
                arguments.addExpression(GeneralUtils.castX(parameter.getType(), e));
                if (!parameter.hasInitialExpression()) continue;
                ++j;
            }
            action.call(arguments, newParams, method);
            ++i;
        }
        for (Parameter parameter : parameters) {
            if (!parameter.hasInitialExpression()) continue;
            parameter.putNodeMetaData(INITIAL_EXPRESSION, parameter.getInitialExpression());
            parameter.setInitialExpression(null);
        }
    }

    protected void addClosureCode(InnerClassNode node) {
    }

    protected void addInitialization(final ClassNode node) {
        boolean addSwapInit = Verifier.moveOptimizedConstantsInitialization(node);
        for (ConstructorNode cn : node.getDeclaredConstructors()) {
            this.addInitialization(node, cn);
        }
        if (addSwapInit) {
            BytecodeSequence seq = new BytecodeSequence(new BytecodeInstruction(){

                @Override
                public void visit(MethodVisitor mv) {
                    mv.visitMethodInsn(184, BytecodeHelper.getClassInternalName(node), Verifier.SWAP_INIT, "()V", false);
                }
            });
            ArrayList<Statement> swapCall = new ArrayList<Statement>(1);
            swapCall.add(seq);
            node.addStaticInitializerStatements(swapCall, true);
        }
    }

    protected void addInitialization(ClassNode node, ConstructorNode constructorNode) {
        BlockStatement block;
        List<Statement> blockStatements;
        Statement firstStatement = constructorNode.getFirstStatement();
        if (firstStatement instanceof BytecodeSequence) {
            return;
        }
        ConstructorCallExpression specialCtorCall = ConstructorNodeUtils.getFirstIfSpecialConstructorCall(firstStatement);
        if (specialCtorCall != null && specialCtorCall.isThisCall()) {
            return;
        }
        boolean isEnum = node.isEnum();
        ArrayList<Statement> statements = new ArrayList<Statement>();
        ArrayList<Statement> staticStatements = new ArrayList<Statement>();
        ArrayList<Statement> initStmtsAfterEnumValuesInit = new ArrayList<Statement>();
        if (!Traits.isTrait(node)) {
            Set<Object> explicitStaticPropsInEnum = !isEnum ? Collections.emptySet() : Verifier.getExplicitStaticProperties(node);
            for (FieldNode fn : node.getFields()) {
                this.addFieldInitialization(statements, staticStatements, fn, isEnum, initStmtsAfterEnumValuesInit, explicitStaticPropsInEnum);
            }
        }
        if (!(blockStatements = (block = MethodNodeUtils.getCodeAsBlock(constructorNode)).getStatements()).isEmpty()) {
            Statement initThis$0;
            if (specialCtorCall != null) {
                blockStatements.remove(0);
                statements.add(0, firstStatement);
                if (node instanceof InnerClassNode && ((InnerClassNode)node).isAnonymous()) {
                    Verifier.extractVariableReferenceInitializers(statements).forEach(s -> statements.add(0, (Statement)s));
                }
            }
            if (node instanceof InnerClassNode && (initThis$0 = Verifier.getImplicitThis$0Stmt(blockStatements)) != null) {
                statements.add(0, initThis$0);
            }
            statements.addAll(node.getObjectInitializerStatements());
            statements.addAll(blockStatements);
        } else {
            statements.addAll(node.getObjectInitializerStatements());
        }
        BlockStatement newBlock = new BlockStatement(statements, block.getVariableScope());
        newBlock.setSourcePosition(block);
        constructorNode.setCode(newBlock);
        if (!staticStatements.isEmpty()) {
            if (isEnum) {
                staticStatements.removeAll(initStmtsAfterEnumValuesInit);
                node.addStaticInitializerStatements(staticStatements, true);
                if (!initStmtsAfterEnumValuesInit.isEmpty()) {
                    node.positionStmtsAfterEnumInitStmts(initStmtsAfterEnumValuesInit);
                }
            } else {
                node.addStaticInitializerStatements(staticStatements, true);
            }
        }
    }

    private static Set<String> getExplicitStaticProperties(ClassNode cn) {
        HashSet<String> staticProperties = new HashSet<String>();
        for (PropertyNode pn : cn.getProperties()) {
            if (pn.isSynthetic() || !pn.getField().isStatic()) continue;
            staticProperties.add(pn.getField().getName());
        }
        for (FieldNode fn : cn.getFields()) {
            if (fn.isSynthetic() || !fn.isStatic() || fn.getType() == cn) continue;
            staticProperties.add(fn.getName());
        }
        return staticProperties;
    }

    private static Statement getImplicitThis$0Stmt(List<Statement> statements) {
        for (Statement stmt : statements) {
            if (stmt instanceof BlockStatement) {
                List<Statement> stmts = ((BlockStatement)stmt).getStatements();
                for (Statement bstmt : stmts) {
                    if (!(bstmt instanceof ExpressionStatement) || !Verifier.extractImplicitThis$0StmtFromExpression(stmts, bstmt)) continue;
                    return bstmt;
                }
                continue;
            }
            if (!(stmt instanceof ExpressionStatement) || !Verifier.extractImplicitThis$0StmtFromExpression(statements, stmt)) continue;
            return stmt;
        }
        return null;
    }

    private static boolean extractImplicitThis$0StmtFromExpression(List<Statement> stmts, Statement exprStmt) {
        Expression lExpr;
        Expression expr = ((ExpressionStatement)exprStmt).getExpression();
        if (expr instanceof BinaryExpression && (lExpr = ((BinaryExpression)expr).getLeftExpression()) instanceof FieldExpression && "this$0".equals(((FieldExpression)lExpr).getFieldName())) {
            stmts.remove(exprStmt);
            return true;
        }
        return false;
    }

    private static List<Statement> extractVariableReferenceInitializers(List<Statement> statements) {
        ArrayList<Statement> localVariableReferences = new ArrayList<Statement>();
        ListIterator<Statement> it = statements.listIterator(1);
        while (it.hasNext()) {
            BinaryExpression expr;
            Statement stmt = it.next();
            if (!(stmt instanceof ExpressionStatement) || !(((ExpressionStatement)stmt).getExpression() instanceof BinaryExpression) || (expr = (BinaryExpression)((ExpressionStatement)stmt).getExpression()).getOperation().getType() != 100 || !(expr.getLeftExpression() instanceof FieldExpression) || !expr.getLeftExpression().getType().equals(ClassHelper.REFERENCE_TYPE) || (((FieldExpression)expr.getLeftExpression()).getField().getModifiers() & 0x1000) == 0) continue;
            localVariableReferences.add(stmt);
            it.remove();
        }
        return localVariableReferences;
    }

    protected void addFieldInitialization(List list, List staticList, FieldNode fieldNode, boolean isEnumClassNode, List initStmtsAfterEnumValuesInit, Set explicitStaticPropsInEnum) {
        Expression expression = fieldNode.getInitialExpression();
        if (expression != null) {
            FieldExpression fe = GeneralUtils.fieldX(fieldNode);
            if (fieldNode.getType().equals(ClassHelper.REFERENCE_TYPE) && (fieldNode.getModifiers() & 0x1000) != 0) {
                fe.setUseReferenceDirectly(true);
            }
            Statement statement = GeneralUtils.stmt(GeneralUtils.binX(fe, Token.newSymbol(100, fieldNode.getLineNumber(), fieldNode.getColumnNumber()), expression));
            if (fieldNode.isStatic()) {
                Expression initialValueExpression = fieldNode.getInitialValueExpression();
                Expression transformed = ExpressionUtils.transformInlineConstants(initialValueExpression, fieldNode.getType());
                if (transformed instanceof ConstantExpression) {
                    ConstantExpression cexp = (ConstantExpression)transformed;
                    cexp = Verifier.transformToPrimitiveConstantIfPossible(cexp);
                    if (fieldNode.isFinal() && ClassHelper.isStaticConstantInitializerType(cexp.getType()) && cexp.getType().equals(fieldNode.getType())) {
                        fieldNode.setInitialValueExpression(transformed);
                        return;
                    }
                    staticList.add(0, statement);
                } else {
                    staticList.add(statement);
                }
                fieldNode.setInitialValueExpression(null);
                if (isEnumClassNode && explicitStaticPropsInEnum.contains(fieldNode.getName())) {
                    initStmtsAfterEnumValuesInit.add(statement);
                }
            } else {
                list.add(statement);
            }
        }
    }

    public static String capitalize(String name) {
        return BeanUtils.capitalize(name);
    }

    protected Statement createGetterBlock(PropertyNode propertyNode, final FieldNode field) {
        return new BytecodeSequence(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                if (field.isStatic()) {
                    mv.visitFieldInsn(178, BytecodeHelper.getClassInternalName(Verifier.this.classNode), field.getName(), BytecodeHelper.getTypeDescription(field.getType()));
                } else {
                    mv.visitVarInsn(25, 0);
                    mv.visitFieldInsn(180, BytecodeHelper.getClassInternalName(Verifier.this.classNode), field.getName(), BytecodeHelper.getTypeDescription(field.getType()));
                }
                BytecodeHelper.doReturn(mv, field.getType());
            }
        });
    }

    protected Statement createSetterBlock(PropertyNode propertyNode, final FieldNode field) {
        return new BytecodeSequence(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                if (field.isStatic()) {
                    BytecodeHelper.load(mv, field.getType(), 0);
                    mv.visitFieldInsn(179, BytecodeHelper.getClassInternalName(Verifier.this.classNode), field.getName(), BytecodeHelper.getTypeDescription(field.getType()));
                } else {
                    mv.visitVarInsn(25, 0);
                    BytecodeHelper.load(mv, field.getType(), 1);
                    mv.visitFieldInsn(181, BytecodeHelper.getClassInternalName(Verifier.this.classNode), field.getName(), BytecodeHelper.getTypeDescription(field.getType()));
                }
                mv.visitInsn(177);
            }
        });
    }

    public void visitGenericType(GenericsType genericsType) {
    }

    public static Long getTimestampFromFieldName(String fieldName) {
        if (fieldName.startsWith(__TIMESTAMP__)) {
            try {
                return Long.decode(fieldName.substring(__TIMESTAMP__.length()));
            }
            catch (NumberFormatException e) {
                return Long.MAX_VALUE;
            }
        }
        return null;
    }

    public static long getTimestamp(Class<?> clazz) {
        if (clazz.getClassLoader() instanceof GroovyClassLoader.InnerLoader) {
            GroovyClassLoader.InnerLoader innerLoader = (GroovyClassLoader.InnerLoader)clazz.getClassLoader();
            return innerLoader.getTimeStamp();
        }
        for (Field field : clazz.getFields()) {
            Long timestamp;
            if (!Modifier.isStatic(field.getModifiers()) || (timestamp = Verifier.getTimestampFromFieldName(field.getName())) == null) continue;
            return timestamp;
        }
        return Long.MAX_VALUE;
    }

    protected void addCovariantMethods(ClassNode classNode) {
        HashMap<String, MethodNode> methodsToAdd = new HashMap<String, MethodNode>();
        HashMap<String, ClassNode> genericsSpec = new HashMap<String, ClassNode>();
        Map<String, MethodNode> abstractMethods = ClassNodeUtils.getDeclaredMethodsFromInterfaces(classNode);
        HashMap<String, MethodNode> allInterfaceMethods = new HashMap<String, MethodNode>(abstractMethods);
        ClassNodeUtils.addDeclaredMethodsFromAllInterfaces(classNode, allInterfaceMethods);
        ArrayList<MethodNode> declaredMethods = new ArrayList<MethodNode>(classNode.getMethods());
        Iterator methodsIterator = declaredMethods.iterator();
        while (methodsIterator.hasNext()) {
            MethodNode methodNode;
            MethodNode m = (MethodNode)methodsIterator.next();
            abstractMethods.remove(m.getTypeDescriptor());
            if (m.isStatic() || !m.isPublic() && !m.isProtected()) {
                methodsIterator.remove();
            }
            if ((methodNode = (MethodNode)allInterfaceMethods.get(m.getTypeDescriptor())) == null || (m.getModifiers() & 0x1000) != 0 || m.isPublic() || m.isStaticConstructor()) continue;
            throw new RuntimeParserException("The method " + m.getName() + " should be public as it implements the corresponding method from interface " + methodNode.getDeclaringClass(), Verifier.sourceOf(m));
        }
        this.addCovariantMethods(classNode, declaredMethods, abstractMethods, methodsToAdd, genericsSpec);
        HashMap<String, MethodNode> declaredMethodsMap = new HashMap<String, MethodNode>();
        if (!methodsToAdd.isEmpty()) {
            for (MethodNode methodNode : declaredMethods) {
                declaredMethodsMap.put(methodNode.getTypeDescriptor(), methodNode);
            }
        }
        for (Map.Entry entry : methodsToAdd.entrySet()) {
            MethodNode mn = (MethodNode)declaredMethodsMap.get(entry.getKey());
            if (mn != null && mn.getDeclaringClass().equals(classNode)) continue;
            this.addPropertyMethod((MethodNode)entry.getValue());
        }
    }

    private void addCovariantMethods(ClassNode classNode, List<MethodNode> declaredMethods, Map<String, MethodNode> abstractMethods, Map<String, MethodNode> methodsToAdd, Map<String, ClassNode> oldGenericsSpec) {
        ClassNode sn = classNode.getUnresolvedSuperClass(false);
        if (sn != null) {
            Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(sn, oldGenericsSpec);
            List<MethodNode> classMethods = sn.getMethods();
            this.storeMissingCovariantMethods(declaredMethods, methodsToAdd, genericsSpec, classMethods);
            if (!abstractMethods.isEmpty()) {
                for (MethodNode method : classMethods) {
                    if (method.isStatic()) continue;
                    this.storeMissingCovariantMethods(abstractMethods.values(), method, methodsToAdd, Collections.emptyMap(), true);
                }
            }
            this.addCovariantMethods(sn.redirect(), declaredMethods, abstractMethods, methodsToAdd, genericsSpec);
        }
        for (ClassNode anInterface : classNode.getInterfaces()) {
            List<MethodNode> interfacesMethods = anInterface.getMethods();
            Map<String, ClassNode> genericsSpec = GenericsUtils.createGenericsSpec(anInterface, oldGenericsSpec);
            this.storeMissingCovariantMethods(declaredMethods, methodsToAdd, genericsSpec, interfacesMethods);
            this.addCovariantMethods(anInterface, declaredMethods, abstractMethods, methodsToAdd, genericsSpec);
        }
    }

    private void storeMissingCovariantMethods(List<MethodNode> declaredMethods, Map<String, MethodNode> methodsToAdd, Map<String, ClassNode> genericsSpec, List<MethodNode> methodNodeList) {
        for (MethodNode method : declaredMethods) {
            if (method.isStatic()) continue;
            this.storeMissingCovariantMethods(methodNodeList, method, methodsToAdd, genericsSpec, false);
        }
    }

    private MethodNode getCovariantImplementation(final MethodNode oldMethod, final MethodNode overridingMethod, Map<String, ClassNode> genericsSpec, boolean ignoreError) {
        boolean equalParameters;
        if (!oldMethod.getName().equals(overridingMethod.getName())) {
            return null;
        }
        if ((overridingMethod.getModifiers() & 0x40) != 0) {
            return null;
        }
        if ((oldMethod.getModifiers() & 0x40) != 0) {
            return null;
        }
        if (oldMethod.isPrivate()) {
            return null;
        }
        if (oldMethod.getGenericsTypes() != null) {
            genericsSpec = GenericsUtils.addMethodGenerics(oldMethod, genericsSpec);
        }
        if (!(equalParameters = Verifier.equalParametersNormal(overridingMethod, oldMethod)) && !Verifier.equalParametersWithGenerics(overridingMethod, oldMethod, genericsSpec)) {
            return null;
        }
        final ClassNode nmr = overridingMethod.getReturnType();
        ClassNode omr = oldMethod.getReturnType();
        boolean equalReturnType = nmr.equals(omr);
        ClassNode omrCorrected = GenericsUtils.correctToGenericsSpec(genericsSpec, omr);
        if (!Verifier.isAssignable(nmr, omrCorrected)) {
            if (ignoreError) {
                return null;
            }
            throw new RuntimeParserException("The return type of " + overridingMethod.getTypeDescriptor() + " in " + overridingMethod.getDeclaringClass().getName() + " is incompatible with " + omrCorrected.getName() + " in " + oldMethod.getDeclaringClass().getName(), Verifier.sourceOf(overridingMethod));
        }
        if (equalReturnType && equalParameters) {
            return null;
        }
        if (oldMethod.isFinal()) {
            throw new RuntimeParserException("Cannot override final method " + oldMethod.getTypeDescriptor() + " in " + oldMethod.getDeclaringClass().getName(), Verifier.sourceOf(overridingMethod));
        }
        if (oldMethod.isStatic() != overridingMethod.isStatic()) {
            throw new RuntimeParserException("Cannot override method " + oldMethod.getTypeDescriptor() + " in " + oldMethod.getDeclaringClass().getName() + " with disparate static modifier", Verifier.sourceOf(overridingMethod));
        }
        if (!equalReturnType) {
            boolean oldM = ClassHelper.isPrimitiveType(omr);
            boolean newM = ClassHelper.isPrimitiveType(nmr);
            if (oldM || newM) {
                String message = oldM && newM ? " with old and new method having different primitive return types" : (newM ? " with new method having a primitive return type and old method not" : " with old method having a primitive return type and new method not");
                throw new RuntimeParserException("Cannot override method " + oldMethod.getTypeDescriptor() + " in " + oldMethod.getDeclaringClass().getName() + message, Verifier.sourceOf(overridingMethod));
            }
        }
        return new MethodNode(oldMethod.getName(), overridingMethod.getModifiers() | 0x1000 | 0x40, Verifier.cleanType(omr), Verifier.cleanParameters(oldMethod.getParameters()), oldMethod.getExceptions(), new BytecodeSequence(new BytecodeInstruction(){

            @Override
            public void visit(MethodVisitor mv) {
                mv.visitVarInsn(25, 0);
                Parameter[] para = oldMethod.getParameters();
                Parameter[] goal = overridingMethod.getParameters();
                int doubleSlotOffset = 0;
                int n = para.length;
                for (int i = 0; i < n; ++i) {
                    ClassNode type = para[i].getType();
                    BytecodeHelper.load(mv, type, i + 1 + doubleSlotOffset);
                    if (ClassHelper.isPrimitiveDouble(type.redirect()) || ClassHelper.isPrimitiveLong(type.redirect())) {
                        ++doubleSlotOffset;
                    }
                    if (type.equals(goal[i].getType())) continue;
                    BytecodeHelper.doCast(mv, goal[i].getType());
                }
                mv.visitMethodInsn(182, BytecodeHelper.getClassInternalName(Verifier.this.classNode), overridingMethod.getName(), BytecodeHelper.getMethodDescriptor(nmr, overridingMethod.getParameters()), false);
                BytecodeHelper.doReturn(mv, oldMethod.getReturnType());
            }
        }));
    }

    private static boolean isAssignable(ClassNode node, ClassNode testNode) {
        if (node.isArray() && testNode.isArray()) {
            return Verifier.isArrayAssignable(node.getComponentType(), testNode.getComponentType());
        }
        if (testNode.isInterface() && (node.equals(testNode) || node.implementsInterface(testNode))) {
            return true;
        }
        return node.isDerivedFrom(testNode);
    }

    private static boolean isArrayAssignable(ClassNode node, ClassNode testNode) {
        if (node.isArray() && testNode.isArray()) {
            return Verifier.isArrayAssignable(node.getComponentType(), testNode.getComponentType());
        }
        return Verifier.isAssignable(node, testNode);
    }

    private static Parameter[] cleanParameters(Parameter[] parameters) {
        return (Parameter[])Arrays.stream(parameters).map(p -> GeneralUtils.param(Verifier.cleanType(p.getType()), p.getName())).toArray(Parameter[]::new);
    }

    private static ClassNode cleanType(ClassNode type) {
        if (type.isArray()) {
            return Verifier.cleanType(type.getComponentType()).makeArray();
        }
        return type.getPlainNodeReference();
    }

    private void storeMissingCovariantMethods(Iterable<MethodNode> methods, MethodNode method, Map<String, MethodNode> methodsToAdd, Map<String, ClassNode> genericsSpec, boolean ignoreError) {
        for (MethodNode toOverride : methods) {
            MethodNode bridgeMethod = this.getCovariantImplementation(toOverride, method, genericsSpec, ignoreError);
            if (bridgeMethod == null) continue;
            methodsToAdd.put(bridgeMethod.getTypeDescriptor(), bridgeMethod);
            return;
        }
    }

    private static boolean equalParametersNormal(MethodNode m1, MethodNode m2) {
        Parameter[] p2;
        Parameter[] p1 = m1.getParameters();
        if (p1.length != (p2 = m2.getParameters()).length) {
            return false;
        }
        int n = p2.length;
        for (int i = 0; i < n; ++i) {
            ClassNode type = p2[i].getType();
            ClassNode parameterType = p1[i].getType();
            if (parameterType.equals(type)) continue;
            return false;
        }
        return true;
    }

    private static boolean equalParametersWithGenerics(MethodNode m1, MethodNode m2, Map<String, ClassNode> genericsSpec) {
        Parameter[] p2;
        Parameter[] p1 = m1.getParameters();
        if (p1.length != (p2 = m2.getParameters()).length) {
            return false;
        }
        int n = p2.length;
        for (int i = 0; i < n; ++i) {
            ClassNode type = p2[i].getType();
            ClassNode genericsType = GenericsUtils.correctToGenericsSpec(genericsSpec, type);
            ClassNode parameterType = p1[i].getType();
            if (parameterType.equals(genericsType)) continue;
            return false;
        }
        return true;
    }

    private static boolean moveOptimizedConstantsInitialization(ClassNode node) {
        if (node.isInterface() && !Traits.isTrait(node)) {
            return false;
        }
        String name = SWAP_INIT;
        int mods = 4105;
        BlockStatement methodCode = GeneralUtils.block(new SwapInitStatement());
        boolean swapInitRequired = false;
        for (FieldNode fn : node.getFields()) {
            if (!fn.isStatic() || !fn.isSynthetic() || !fn.getName().startsWith("$const$") || fn.getInitialExpression() == null) continue;
            FieldExpression fe = GeneralUtils.fieldX(fn);
            if (fn.getType().equals(ClassHelper.REFERENCE_TYPE)) {
                fe.setUseReferenceDirectly(true);
            }
            ConstantExpression init = (ConstantExpression)fn.getInitialExpression();
            init = GeneralUtils.constX(init.getValue(), true);
            Statement statement = GeneralUtils.stmt(GeneralUtils.binX(fe, Token.newSymbol(100, fn.getLineNumber(), fn.getColumnNumber()), init));
            fn.setInitialValueExpression(null);
            methodCode.addStatement(statement);
            swapInitRequired = true;
        }
        if (swapInitRequired) {
            node.addSyntheticMethod(name, mods, ClassHelper.VOID_TYPE, Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, methodCode);
        }
        return swapInitRequired;
    }

    private static ASTNode sourceOf(MethodNode methodNode) {
        if (methodNode.getLineNumber() < 1) {
            PropertyNode propertyNode;
            String propertyName;
            ClassNode declaringClass = methodNode.getDeclaringClass();
            if (methodNode.isSynthetic() && (propertyName = MethodNodeUtils.getPropertyName(methodNode)) != null && (propertyNode = declaringClass.getProperty(propertyName)) != null && propertyNode.getLineNumber() > 0) {
                return propertyNode;
            }
            return declaringClass;
        }
        return methodNode;
    }

    public static ConstantExpression transformToPrimitiveConstantIfPossible(ConstantExpression constantExpression) {
        ConstantExpression result;
        Object value = constantExpression.getValue();
        if (value == null) {
            return constantExpression;
        }
        ClassNode type = constantExpression.getType();
        if (ClassHelper.isPrimitiveType(type)) {
            return constantExpression;
        }
        if (value instanceof String && ((String)value).length() == 1) {
            result = GeneralUtils.constX(Character.valueOf(((String)value).charAt(0)));
            result.setType(ClassHelper.char_TYPE);
        } else {
            type = ClassHelper.getUnwrapper(type);
            result = GeneralUtils.constX(value, true);
            result.setType(type);
        }
        return result;
    }

    @FunctionalInterface
    public static interface DefaultArgsAction {
        public void call(ArgumentListExpression var1, Parameter[] var2, MethodNode var3);
    }

    private static class SwapInitStatement
    extends BytecodeSequence {
        private WriterController controller;

        SwapInitStatement() {
            super(new SwapInitInstruction());
            ((SwapInitInstruction)this.getInstructions().get(0)).statement = this;
        }

        @Override
        public void visit(GroovyCodeVisitor visitor) {
            if (visitor instanceof AsmClassGenerator) {
                this.controller = ((AsmClassGenerator)visitor).getController();
            }
            super.visit(visitor);
        }

        private static class SwapInitInstruction
        extends BytecodeInstruction {
            private SwapInitStatement statement;

            private SwapInitInstruction() {
            }

            @Override
            public void visit(MethodVisitor mv) {
                this.statement.controller.getCallSiteWriter().makeCallSiteArrayInitializer();
            }
        }
    }
}

