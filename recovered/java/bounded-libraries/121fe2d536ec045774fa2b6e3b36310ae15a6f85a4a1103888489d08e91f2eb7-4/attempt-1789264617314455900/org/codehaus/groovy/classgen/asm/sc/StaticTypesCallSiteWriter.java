/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.apache.groovy.util.BeanUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ASTNode;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.PropertyNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.EmptyStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.BytecodeExpression;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.CallSiteWriter;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.MethodCallerMultiAdapter;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.TypeChooser;
import org.codehaus.groovy.classgen.asm.VariableSlotLoader;
import org.codehaus.groovy.classgen.asm.sc.StaticInvocationWriter;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesWriterController;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;

public class StaticTypesCallSiteWriter
extends CallSiteWriter {
    private static final ClassNode COLLECTION_TYPE = ClassHelper.make(Collection.class);
    private static final ClassNode INVOKERHELPER_TYPE = ClassHelper.make(InvokerHelper.class);
    private static final MethodNode COLLECTION_SIZE_METHOD = COLLECTION_TYPE.getMethod("size", Parameter.EMPTY_ARRAY);
    private static final MethodNode CLOSURE_GETTHISOBJECT_METHOD = ClassHelper.CLOSURE_TYPE.getMethod("getThisObject", Parameter.EMPTY_ARRAY);
    private static final MethodNode MAP_GET_METHOD = ClassHelper.MAP_TYPE.getMethod("get", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE, "key")});
    private static final MethodNode GROOVYOBJECT_GETPROPERTY_METHOD = ClassHelper.GROOVY_OBJECT_TYPE.getMethod("getProperty", new Parameter[]{new Parameter(ClassHelper.STRING_TYPE, "propertyName")});
    private static final MethodNode INVOKERHELPER_GETPROPERTY_METHOD = INVOKERHELPER_TYPE.getMethod("getProperty", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE, "object"), new Parameter(ClassHelper.STRING_TYPE, "propertyName")});
    private static final MethodNode INVOKERHELPER_GETPROPERTYSAFE_METHOD = INVOKERHELPER_TYPE.getMethod("getPropertySafe", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE, "object"), new Parameter(ClassHelper.STRING_TYPE, "propertyName")});
    private final StaticTypesWriterController controller;

    public StaticTypesCallSiteWriter(StaticTypesWriterController controller) {
        super(controller);
        this.controller = controller;
    }

    @Override
    public void generateCallSiteArray() {
        CallSiteWriter regularCallSiteWriter = this.controller.getRegularCallSiteWriter();
        if (regularCallSiteWriter.hasCallSiteUse()) {
            regularCallSiteWriter.generateCallSiteArray();
        }
    }

    @Override
    public void makeCallSite(Expression receiver, String message, Expression arguments, boolean safe, boolean implicitThis, boolean callCurrent, boolean callStatic) {
    }

    @Override
    public void makeGetPropertySite(Expression receiver, String propertyName, boolean safe, boolean implicitThis) {
        List<MethodNode> methodNodes;
        boolean isStaticProperty;
        Object dynamic = receiver.getNodeMetaData((Object)StaticCompilationMetadataKeys.RECEIVER_OF_DYNAMIC_PROPERTY);
        if (dynamic != null) {
            this.makeDynamicGetProperty(receiver, propertyName, safe);
            return;
        }
        boolean[] isClassReceiver = new boolean[1];
        ClassNode receiverType = this.getPropertyOwnerType(receiver, isClassReceiver);
        if (receiverType.isArray() && "length".equals(propertyName)) {
            receiver.visit(this.controller.getAcg());
            ClassNode arrayGetReturnType = this.controller.getTypeChooser().resolveType(receiver, this.controller.getClassNode());
            this.controller.getOperandStack().doGroovyCast(arrayGetReturnType);
            this.controller.getMethodVisitor().visitInsn(190);
            this.controller.getOperandStack().replace(ClassHelper.int_TYPE);
            return;
        }
        if (GeneralUtils.isOrImplements(receiverType, COLLECTION_TYPE) && ("size".equals(propertyName) || "length".equals(propertyName))) {
            MethodCallExpression expr = GeneralUtils.callX(receiver, "size");
            expr.setMethodTarget(COLLECTION_SIZE_METHOD);
            expr.setImplicitThis(implicitThis);
            expr.setSafe(safe);
            expr.visit(this.controller.getAcg());
            return;
        }
        boolean bl = isStaticProperty = receiver instanceof ClassExpression && (receiverType.isDerivedFrom(receiver.getType()) || receiverType.implementsInterface(receiver.getType()));
        if (!isStaticProperty && GeneralUtils.isOrImplements(receiverType, ClassHelper.MAP_TYPE)) {
            this.writeMapDotProperty(receiver, propertyName, safe);
            return;
        }
        if (this.makeGetPropertyWithGetter(receiver, receiverType, propertyName, safe, implicitThis)) {
            return;
        }
        if (this.makeGetField(receiver, receiverType, propertyName, safe, implicitThis)) {
            return;
        }
        if (receiver instanceof ClassExpression) {
            if (this.makeGetField(receiver, receiver.getType(), propertyName, safe, implicitThis)) {
                return;
            }
            if (this.makeGetPropertyWithGetter(receiver, receiver.getType(), propertyName, safe, implicitThis)) {
                return;
            }
            if (this.makeGetPrivateFieldWithBridgeMethod(receiver, receiver.getType(), propertyName, safe, implicitThis)) {
                return;
            }
        }
        if (isClassReceiver[0]) {
            if (this.makeGetPropertyWithGetter(receiver, ClassHelper.CLASS_Type, propertyName, safe, implicitThis)) {
                return;
            }
            if (this.makeGetField(receiver, ClassHelper.CLASS_Type, propertyName, safe, false)) {
                return;
            }
        }
        if (this.makeGetPrivateFieldWithBridgeMethod(receiver, receiverType, propertyName, safe, implicitThis)) {
            return;
        }
        String isserName = "is" + BeanUtils.capitalize(propertyName);
        String getterName = "get" + BeanUtils.capitalize(propertyName);
        if (receiverType.isInterface()) {
            MethodNode getterMethod = null;
            for (ClassNode anInterface : receiverType.getAllInterfaces()) {
                getterMethod = anInterface.getGetterMethod(isserName);
                if (getterMethod == null) {
                    getterMethod = anInterface.getGetterMethod(getterName);
                }
                if (getterMethod == null) continue;
                break;
            }
            if (getterMethod == null) {
                getterMethod = ClassHelper.OBJECT_TYPE.getGetterMethod(getterName);
            }
            if (getterMethod != null) {
                MethodCallExpression call = GeneralUtils.callX(receiver, getterName);
                call.setImplicitThis(false);
                call.setMethodTarget(getterMethod);
                call.setSafe(safe);
                call.setSourcePosition(receiver);
                call.visit(this.controller.getAcg());
                return;
            }
        }
        List<MethodNode> methods = StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(this.controller.getSourceUnit().getClassLoader(), receiverType, isserName, ClassNode.EMPTY_ARRAY);
        methods.removeIf(dgm -> !ClassHelper.isPrimitiveBoolean(dgm.getReturnType()));
        StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(this.controller.getSourceUnit().getClassLoader(), receiverType, getterName, ClassNode.EMPTY_ARRAY, methods);
        if (!methods.isEmpty() && (methodNodes = StaticTypeCheckingSupport.chooseBestMethod(receiverType, methods, ClassNode.EMPTY_ARRAY)).size() == 1) {
            MethodNode getter = methodNodes.get(0);
            MethodCallExpression call = GeneralUtils.callX(receiver, getter.getName());
            call.setImplicitThis(false);
            call.setMethodTarget(getter);
            call.setSafe(safe);
            call.setSourcePosition(receiver);
            call.visit(this.controller.getAcg());
            return;
        }
        if (!isStaticProperty && GeneralUtils.isOrImplements(receiverType, ClassHelper.LIST_TYPE)) {
            this.writeListDotProperty(receiver, propertyName, safe);
            return;
        }
        this.addPropertyAccessError(receiver, propertyName, receiverType);
        this.controller.getMethodVisitor().visitInsn(1);
        this.controller.getOperandStack().push(ClassHelper.OBJECT_TYPE);
    }

    private void makeDynamicGetProperty(Expression receiver, String propertyName, boolean safe) {
        MethodNode target = safe ? INVOKERHELPER_GETPROPERTYSAFE_METHOD : INVOKERHELPER_GETPROPERTY_METHOD;
        MethodCallExpression call = GeneralUtils.callX((Expression)GeneralUtils.classX(INVOKERHELPER_TYPE), target.getName(), (Expression)GeneralUtils.args(receiver, GeneralUtils.constX(propertyName)));
        call.setImplicitThis(false);
        call.setMethodTarget(target);
        call.visit(this.controller.getAcg());
    }

    private void writeMapDotProperty(Expression receiver, String propertyName, boolean safe) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        receiver.visit(this.controller.getAcg());
        Label skip = new Label();
        if (safe) {
            mv.visitInsn(89);
            mv.visitJumpInsn(198, skip);
        }
        mv.visitLdcInsn(propertyName);
        mv.visitMethodInsn(185, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
        if (safe) {
            mv.visitLabel(skip);
        }
        this.controller.getOperandStack().replace(ClassHelper.OBJECT_TYPE);
    }

    private void writeListDotProperty(Expression receiver, String propertyName, boolean safe) {
        ClassNode componentType = (ClassNode)receiver.getNodeMetaData((Object)StaticCompilationMetadataKeys.COMPONENT_TYPE);
        if (componentType == null) {
            componentType = ClassHelper.OBJECT_TYPE;
        }
        CompileStack compileStack = this.controller.getCompileStack();
        MethodVisitor mv = this.controller.getMethodVisitor();
        Label exit = new Label();
        if (safe) {
            receiver.visit(this.controller.getAcg());
            Label doGet = new Label();
            mv.visitJumpInsn(199, doGet);
            this.controller.getOperandStack().remove(1);
            mv.visitInsn(1);
            mv.visitJumpInsn(167, exit);
            mv.visitLabel(doGet);
        }
        VariableExpression tmpList = GeneralUtils.varX("tmpList", ClassHelper.make(ArrayList.class));
        int var = compileStack.defineTemporaryVariable(tmpList, false);
        VariableExpression iterator = GeneralUtils.varX("iterator", ClassHelper.Iterator_TYPE);
        int it = compileStack.defineTemporaryVariable(iterator, false);
        VariableExpression nextVar = GeneralUtils.varX("next", componentType);
        int next = compileStack.defineTemporaryVariable(nextVar, false);
        mv.visitTypeInsn(187, "java/util/ArrayList");
        mv.visitInsn(89);
        receiver.visit(this.controller.getAcg());
        mv.visitMethodInsn(185, "java/util/List", "size", "()I", true);
        this.controller.getOperandStack().remove(1);
        mv.visitMethodInsn(183, "java/util/ArrayList", "<init>", "(I)V", false);
        mv.visitVarInsn(58, var);
        Label l1 = new Label();
        mv.visitLabel(l1);
        receiver.visit(this.controller.getAcg());
        mv.visitMethodInsn(185, "java/util/List", "iterator", "()Ljava/util/Iterator;", true);
        this.controller.getOperandStack().remove(1);
        mv.visitVarInsn(58, it);
        Label l2 = new Label();
        mv.visitLabel(l2);
        mv.visitVarInsn(25, it);
        mv.visitMethodInsn(185, "java/util/Iterator", "hasNext", "()Z", true);
        Label l3 = new Label();
        mv.visitJumpInsn(153, l3);
        mv.visitVarInsn(25, it);
        mv.visitMethodInsn(185, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
        mv.visitTypeInsn(192, BytecodeHelper.getClassInternalName(componentType));
        mv.visitVarInsn(58, next);
        Label l4 = new Label();
        mv.visitLabel(l4);
        mv.visitVarInsn(25, var);
        PropertyExpression pexp = GeneralUtils.propX((Expression)GeneralUtils.bytecodeX(componentType, v -> v.visitVarInsn(25, next)), propertyName);
        pexp.visit(this.controller.getAcg());
        this.controller.getOperandStack().box();
        this.controller.getOperandStack().remove(1);
        mv.visitMethodInsn(185, "java/util/List", "add", "(Ljava/lang/Object;)Z", true);
        mv.visitInsn(87);
        Label l5 = new Label();
        mv.visitLabel(l5);
        mv.visitJumpInsn(167, l2);
        mv.visitLabel(l3);
        mv.visitVarInsn(25, var);
        if (safe) {
            mv.visitLabel(exit);
        }
        this.controller.getOperandStack().push(ClassHelper.make(ArrayList.class));
        this.controller.getCompileStack().removeVar(next);
        this.controller.getCompileStack().removeVar(it);
        this.controller.getCompileStack().removeVar(var);
    }

    private boolean makeGetPrivateFieldWithBridgeMethod(Expression receiver, ClassNode receiverType, String fieldName, boolean safe, boolean implicitThis) {
        ClassNode outerClass;
        FieldNode field = receiverType.getField(fieldName);
        if (field != null) {
            MethodNode methodNode;
            Map accessors;
            ClassNode classNode = this.controller.getClassNode();
            if (field.isPrivate() && !receiverType.equals(classNode) && (StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(receiverType, classNode) || StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(classNode, receiverType)) && (accessors = (Map)receiverType.redirect().getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_FIELDS_ACCESSORS)) != null && (methodNode = (MethodNode)accessors.get(fieldName)) != null) {
                MethodCallExpression call = GeneralUtils.callX(receiver, methodNode.getName(), (Expression)GeneralUtils.args(field.isStatic() ? GeneralUtils.nullX() : receiver));
                call.setImplicitThis(implicitThis);
                call.setMethodTarget(methodNode);
                call.setSafe(safe);
                call.visit(this.controller.getAcg());
                return true;
            }
        } else if (implicitThis && (outerClass = receiverType.getOuterClass()) != null && !receiverType.isStaticClass()) {
            Expression expr;
            ClassNode thisType = outerClass;
            if (this.controller.isInGeneratedFunction()) {
                while (ClassHelper.isGeneratedFunction(thisType)) {
                    thisType = thisType.getOuterClass();
                }
                MethodCallExpression call = GeneralUtils.callThisX("getThisObject");
                call.setImplicitThis(true);
                call.setMethodTarget(CLOSURE_GETTHISOBJECT_METHOD);
                call.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, thisType);
                expr = GeneralUtils.castX(thisType, call);
            } else {
                expr = GeneralUtils.propX((Expression)GeneralUtils.classX(outerClass), "this");
                expr.setImplicitThis(true);
            }
            expr.setSourcePosition(receiver);
            expr.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, thisType);
            return this.makeGetPrivateFieldWithBridgeMethod(expr, outerClass, fieldName, safe, true);
        }
        return false;
    }

    @Override
    public void makeGroovyObjectGetPropertySite(Expression receiver, String propertyName, boolean safe, boolean implicitThis) {
        boolean isScriptVariable;
        String implicitReceiver;
        MethodCallExpression currentCall;
        ClassNode receiverType = this.controller.getClassNode();
        if (!ExpressionUtils.isThisExpression(receiver) || this.controller.isInGeneratedFunction()) {
            receiverType = this.getPropertyOwnerType(receiver, new boolean[0]);
        }
        if (implicitThis && this.controller.getInvocationWriter() instanceof StaticInvocationWriter && (currentCall = ((StaticInvocationWriter)this.controller.getInvocationWriter()).getCurrentCall()) != null && (implicitReceiver = (String)currentCall.getNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER)) != null) {
            String[] pathElements = implicitReceiver.split("\\.");
            BytecodeExpression thisLoader = GeneralUtils.bytecodeX(ClassHelper.CLOSURE_TYPE, mv -> mv.visitVarInsn(25, 0));
            PropertyExpression pexp = GeneralUtils.propX(thisLoader, GeneralUtils.constX(pathElements[0]), safe);
            int n = pathElements.length;
            for (int i = 1; i < n; ++i) {
                pexp.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, ClassHelper.CLOSURE_TYPE);
                pexp = GeneralUtils.propX((Expression)pexp, pathElements[i]);
            }
            pexp.visit(this.controller.getAcg());
            return;
        }
        if (this.makeGetPropertyWithGetter(receiver, receiverType, propertyName, safe, implicitThis)) {
            return;
        }
        if (this.makeGetPrivateFieldWithBridgeMethod(receiver, receiverType, propertyName, safe, implicitThis)) {
            return;
        }
        if (this.makeGetField(receiver, receiverType, propertyName, safe, implicitThis)) {
            return;
        }
        boolean bl = isScriptVariable = receiverType.isScript() && receiver instanceof VariableExpression && ((VariableExpression)receiver).getAccessedVariable() == null;
        if (!isScriptVariable && this.controller.getClassNode().getOuterClass() == null) {
            this.addPropertyAccessError(receiver, propertyName, receiverType);
        }
        MethodCallExpression call = GeneralUtils.callX(receiver, "getProperty", (Expression)GeneralUtils.args(GeneralUtils.constX(propertyName)));
        call.setImplicitThis(implicitThis);
        call.setMethodTarget(GROOVYOBJECT_GETPROPERTY_METHOD);
        call.setSafe(safe);
        call.visit(this.controller.getAcg());
    }

    @Override
    public void makeCallSiteArrayInitializer() {
    }

    private boolean makeGetPropertyWithGetter(Expression receiver, ClassNode receiverType, String propertyName, boolean safe, boolean implicitThis) {
        String getterName = "is" + BeanUtils.capitalize(propertyName);
        MethodNode getterNode = receiverType.getGetterMethod(getterName);
        if (getterNode == null) {
            getterName = "get" + BeanUtils.capitalize(propertyName);
            getterNode = receiverType.getGetterMethod(getterName);
        }
        if (getterNode != null && receiver instanceof ClassExpression && !ClassHelper.isClassType(receiverType) && !getterNode.isStatic()) {
            return false;
        }
        PropertyNode propertyNode = receiverType.getProperty(propertyName);
        if (getterNode == null && propertyNode != null) {
            getterNode = new MethodNode(propertyNode.getGetterNameOrDefault(), 1 | (propertyNode.isStatic() ? 8 : 0), propertyNode.getOriginType(), Parameter.EMPTY_ARRAY, ClassNode.EMPTY_ARRAY, EmptyStatement.INSTANCE);
            getterNode.setDeclaringClass(receiverType);
        }
        if (getterNode != null) {
            MethodCallExpression call = GeneralUtils.callX(receiver, getterName);
            call.setImplicitThis(implicitThis);
            call.setMethodTarget(getterNode);
            call.setSafe(safe);
            call.setSourcePosition(receiver);
            call.visit(this.controller.getAcg());
            return true;
        }
        if (receiverType instanceof InnerClassNode && !receiverType.isStaticClass() && this.makeGetPropertyWithGetter(receiver, receiverType.getOuterClass(), propertyName, safe, implicitThis)) {
            return true;
        }
        for (ClassNode node : receiverType.getInterfaces()) {
            if (!this.makeGetPropertyWithGetter(receiver, node, propertyName, safe, implicitThis)) continue;
            return true;
        }
        ClassNode superClass = receiverType.getSuperClass();
        if (superClass != null) {
            return this.makeGetPropertyWithGetter(receiver, superClass, propertyName, safe, implicitThis);
        }
        return false;
    }

    boolean makeGetField(Expression receiver, ClassNode receiverType, String fieldName, boolean safe, boolean implicitThis) {
        FieldNode field = ClassNodeUtils.getField(receiverType, fieldName);
        if (field != null && AsmClassGenerator.isFieldDirectlyAccessible(field, this.controller.getClassNode())) {
            CompileStack compileStack = this.controller.getCompileStack();
            OperandStack operandStack = this.controller.getOperandStack();
            MethodVisitor mv = this.controller.getMethodVisitor();
            ClassNode resultType = field.getOriginType();
            if (field.isStatic()) {
                mv.visitFieldInsn(178, BytecodeHelper.getClassInternalName(receiverType), fieldName, BytecodeHelper.getTypeDescription(resultType));
                operandStack.push(resultType);
            } else {
                if (implicitThis) {
                    compileStack.pushImplicitThis(true);
                    receiver.visit(this.controller.getAcg());
                    compileStack.popImplicitThis();
                } else {
                    receiver.visit(this.controller.getAcg());
                }
                Label skip = new Label();
                if (safe) {
                    mv.visitInsn(89);
                    Label doGet = new Label();
                    mv.visitJumpInsn(199, doGet);
                    mv.visitInsn(87);
                    mv.visitInsn(1);
                    mv.visitJumpInsn(167, skip);
                    mv.visitLabel(doGet);
                }
                if (!operandStack.getTopOperand().isDerivedFrom(field.getOwner())) {
                    mv.visitTypeInsn(192, BytecodeHelper.getClassInternalName(field.getOwner()));
                }
                mv.visitFieldInsn(180, BytecodeHelper.getClassInternalName(field.getOwner()), fieldName, BytecodeHelper.getTypeDescription(resultType));
                if (safe) {
                    if (ClassHelper.isPrimitiveType(resultType)) {
                        operandStack.replace(resultType);
                        operandStack.box();
                        resultType = operandStack.getTopOperand();
                    }
                    mv.visitLabel(skip);
                }
            }
            operandStack.replace(resultType);
            return true;
        }
        return false;
    }

    @Override
    public void makeSiteEntry() {
    }

    @Override
    public void prepareCallSite(String message) {
    }

    @Override
    public void makeSingleArgumentCall(Expression receiver, String message, Expression arguments, boolean safe) {
        ClassNode aType;
        ClassNode classNode;
        TypeChooser typeChooser = this.controller.getTypeChooser();
        ClassNode rType = typeChooser.resolveType(receiver, classNode = this.controller.getClassNode());
        if (this.trySubscript(receiver, message, arguments, rType, aType = typeChooser.resolveType(arguments, classNode), safe)) {
            return;
        }
        rType = (ClassNode)receiver.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
        if (receiver instanceof VariableExpression && rType == null) {
            ASTNode node = (ASTNode)((Object)((VariableExpression)receiver).getAccessedVariable());
            rType = (ClassNode)node.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
        }
        if (rType != null && this.trySubscript(receiver, message, arguments, rType, aType, safe)) {
            return;
        }
        throw new GroovyBugError("At line " + receiver.getLineNumber() + " column " + receiver.getColumnNumber() + "\nOn receiver: " + receiver.getText() + " with message: " + message + " and arguments: " + arguments.getText() + "\nThis method should not have been called. Please try to create a simple example reproducing\nthis error and file a bug report at https://issues.apache.org/jira/browse/GROOVY");
    }

    private boolean trySubscript(Expression receiver, String message, Expression arguments, ClassNode rType, ClassNode aType, boolean safe) {
        if (ClassHelper.getWrapper(rType).isDerivedFrom(ClassHelper.Number_TYPE) && ClassHelper.getWrapper(aType).isDerivedFrom(ClassHelper.Number_TYPE)) {
            if ("plus".equals(message) || "minus".equals(message) || "multiply".equals(message) || "div".equals(message)) {
                this.writeNumberNumberCall(receiver, message, arguments);
                return true;
            }
            if ("power".equals(message)) {
                this.writePowerCall(receiver, arguments, rType, aType);
                return true;
            }
            if ("mod".equals(message) || "leftShift".equals(message) || "rightShift".equals(message) || "rightShiftUnsigned".equals(message) || "and".equals(message) || "or".equals(message) || "xor".equals(message)) {
                this.writeOperatorCall(receiver, arguments, message);
                return true;
            }
        } else {
            if (ClassHelper.isStringType(rType) && "plus".equals(message)) {
                this.writeStringPlusCall(receiver, message, arguments);
                return true;
            }
            if ("getAt".equals(message)) {
                if (rType.isArray() && ClassHelper.getWrapper(aType).isDerivedFrom(ClassHelper.Number_TYPE) && !safe) {
                    this.writeArrayGet(receiver, arguments, rType, aType);
                    return true;
                }
                MethodNode getAtNode = null;
                for (ClassNode current = rType; current != null && getAtNode == null; current = current.getSuperClass()) {
                    getAtNode = current.getDeclaredMethod("getAt", new Parameter[]{new Parameter(aType, "index")});
                    if (getAtNode == null) {
                        getAtNode = this.getCompatibleMethod(current, "getAt", aType);
                    }
                    if (getAtNode == null && ClassHelper.isPrimitiveType(aType)) {
                        getAtNode = current.getDeclaredMethod("getAt", new Parameter[]{new Parameter(ClassHelper.getWrapper(aType), "index")});
                        if (getAtNode != null) continue;
                        getAtNode = this.getCompatibleMethod(current, "getAt", ClassHelper.getWrapper(aType));
                        continue;
                    }
                    if (getAtNode != null || !aType.isDerivedFrom(ClassHelper.Number_TYPE) || (getAtNode = current.getDeclaredMethod("getAt", new Parameter[]{new Parameter(ClassHelper.getUnwrapper(aType), "index")})) != null) continue;
                    getAtNode = this.getCompatibleMethod(current, "getAt", ClassHelper.getUnwrapper(aType));
                }
                if (getAtNode != null) {
                    MethodCallExpression call = GeneralUtils.callX(receiver, "getAt", arguments);
                    call.setImplicitThis(false);
                    call.setMethodTarget(getAtNode);
                    call.setSafe(safe);
                    call.setSourcePosition(arguments);
                    call.visit(this.controller.getAcg());
                    return true;
                }
                ClassNode[] args = new ClassNode[]{aType};
                boolean acceptAnyMethod = ClassHelper.MAP_TYPE.equals(rType) || rType.implementsInterface(ClassHelper.MAP_TYPE) || ClassHelper.LIST_TYPE.equals(rType) || rType.implementsInterface(ClassHelper.LIST_TYPE);
                List<MethodNode> nodes = StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(this.controller.getSourceUnit().getClassLoader(), rType, message, args);
                if (nodes.isEmpty()) {
                    rType = rType.getPlainNodeReference();
                    nodes = StaticTypeCheckingSupport.findDGMMethodsByNameAndArguments(this.controller.getSourceUnit().getClassLoader(), rType, message, args);
                }
                if (nodes.size() == 1 || nodes.size() > 1 && acceptAnyMethod) {
                    MethodCallExpression call = GeneralUtils.callX(receiver, message, arguments);
                    call.setImplicitThis(false);
                    call.setMethodTarget(nodes.get(0));
                    call.setSafe(safe);
                    call.setSourcePosition(arguments);
                    call.visit(this.controller.getAcg());
                    return true;
                }
                if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(rType, ClassHelper.MAP_TYPE)) {
                    MethodCallExpression call = GeneralUtils.callX(receiver, "get", arguments);
                    call.setImplicitThis(false);
                    call.setMethodTarget(MAP_GET_METHOD);
                    call.setSafe(safe);
                    call.setSourcePosition(arguments);
                    call.visit(this.controller.getAcg());
                    return true;
                }
            }
        }
        return false;
    }

    private MethodNode getCompatibleMethod(ClassNode current, String getAt, ClassNode aType) {
        for (MethodNode methodNode : current.getDeclaredMethods("getAt")) {
            ClassNode paramType;
            if (methodNode.getParameters().length != 1 || !aType.isDerivedFrom(paramType = methodNode.getParameters()[0].getType()) && !aType.declaresInterface(paramType)) continue;
            return methodNode;
        }
        return null;
    }

    private void writeArrayGet(Expression receiver, Expression arguments, ClassNode rType, ClassNode aType) {
        OperandStack operandStack = this.controller.getOperandStack();
        int m1 = operandStack.getStackLength();
        receiver.visit(this.controller.getAcg());
        arguments.visit(this.controller.getAcg());
        operandStack.doGroovyCast(ClassHelper.int_TYPE);
        int m2 = operandStack.getStackLength();
        this.controller.getMethodVisitor().visitInsn(50);
        operandStack.replace(rType.getComponentType(), m2 - m1);
    }

    private void writeOperatorCall(Expression receiver, Expression arguments, String operator) {
        this.prepareSiteAndReceiver(receiver, operator, false, this.controller.getCompileStack().isLHS());
        this.controller.getOperandStack().doGroovyCast(ClassHelper.Number_TYPE);
        this.visitBoxedArgument(arguments);
        this.controller.getOperandStack().doGroovyCast(ClassHelper.Number_TYPE);
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/typehandling/NumberMath", operator, "(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;", false);
        this.controller.getOperandStack().replace(ClassHelper.Number_TYPE, 2);
    }

    private void writePowerCall(Expression receiver, Expression arguments, ClassNode rType, ClassNode aType) {
        OperandStack operandStack = this.controller.getOperandStack();
        int m1 = operandStack.getStackLength();
        this.prepareSiteAndReceiver(receiver, "power", false, this.controller.getCompileStack().isLHS());
        operandStack.doGroovyCast(ClassHelper.getWrapper(rType));
        this.visitBoxedArgument(arguments);
        operandStack.doGroovyCast(ClassHelper.getWrapper(aType));
        int m2 = operandStack.getStackLength();
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (ClassHelper.isBigDecimalType(rType) && ClassHelper.isWrapperInteger(ClassHelper.getWrapper(aType))) {
            mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/DefaultGroovyMethods", "power", "(Ljava/math/BigDecimal;Ljava/lang/Integer;)Ljava/lang/Number;", false);
        } else if (ClassHelper.isBigIntegerType(rType) && ClassHelper.isWrapperInteger(ClassHelper.getWrapper(aType))) {
            mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/DefaultGroovyMethods", "power", "(Ljava/math/BigInteger;Ljava/lang/Integer;)Ljava/lang/Number;", false);
        } else if (ClassHelper.isWrapperLong(ClassHelper.getWrapper(rType)) && ClassHelper.isWrapperInteger(ClassHelper.getWrapper(aType))) {
            mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/DefaultGroovyMethods", "power", "(Ljava/lang/Long;Ljava/lang/Integer;)Ljava/lang/Number;", false);
        } else if (ClassHelper.isWrapperInteger(ClassHelper.getWrapper(rType)) && ClassHelper.isWrapperInteger(ClassHelper.getWrapper(aType))) {
            mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/DefaultGroovyMethods", "power", "(Ljava/lang/Integer;Ljava/lang/Integer;)Ljava/lang/Number;", false);
        } else {
            mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/DefaultGroovyMethods", "power", "(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;", false);
        }
        operandStack.replace(ClassHelper.Number_TYPE, m2 - m1);
    }

    private void writeStringPlusCall(Expression receiver, String message, Expression arguments) {
        OperandStack operandStack = this.controller.getOperandStack();
        int m1 = operandStack.getStackLength();
        this.prepareSiteAndReceiver(receiver, message, false, this.controller.getCompileStack().isLHS());
        this.visitBoxedArgument(arguments);
        int m2 = operandStack.getStackLength();
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/DefaultGroovyMethods", "plus", "(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;", false);
        operandStack.replace(ClassHelper.STRING_TYPE, m2 - m1);
    }

    private void writeNumberNumberCall(Expression receiver, String message, Expression arguments) {
        OperandStack operandStack = this.controller.getOperandStack();
        int m1 = operandStack.getStackLength();
        this.prepareSiteAndReceiver(receiver, message, false, this.controller.getCompileStack().isLHS());
        operandStack.doGroovyCast(ClassHelper.Number_TYPE);
        this.visitBoxedArgument(arguments);
        operandStack.doGroovyCast(ClassHelper.Number_TYPE);
        int m2 = operandStack.getStackLength();
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitMethodInsn(184, "org/codehaus/groovy/runtime/dgmimpl/NumberNumber" + BeanUtils.capitalize(message), message, "(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;", false);
        operandStack.replace(ClassHelper.Number_TYPE, m2 - m1);
    }

    @Override
    public void fallbackAttributeOrPropertySite(PropertyExpression expression, Expression objectExpression, String name, MethodCallerMultiAdapter adapter) {
        CompileStack compileStack = this.controller.getCompileStack();
        OperandStack operandStack = this.controller.getOperandStack();
        if (name != null && compileStack.isLHS()) {
            boolean[] isClassReceiver = new boolean[1];
            ClassNode receiverType = this.getPropertyOwnerType(objectExpression, isClassReceiver);
            if ((adapter == AsmClassGenerator.setField || adapter == AsmClassGenerator.setGroovyObjectField) && this.setField(expression, objectExpression, receiverType, name)) {
                return;
            }
            if (ExpressionUtils.isThisExpression(objectExpression)) {
                MethodNode methodNode;
                Map mutators;
                ClassNode classNode = this.controller.getClassNode();
                FieldNode fieldNode = receiverType.getField(name);
                if (fieldNode != null && fieldNode.isPrivate() && !receiverType.equals(classNode) && StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(receiverType, classNode) && (mutators = (Map)receiverType.redirect().getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_FIELDS_MUTATORS)) != null && (methodNode = (MethodNode)mutators.get(name)) != null) {
                    ClassNode rhsType = operandStack.getTopOperand();
                    int i = compileStack.defineTemporaryVariable("$rhs", rhsType, true);
                    VariableSlotLoader rhsValue = new VariableSlotLoader(rhsType, i, operandStack);
                    MethodCallExpression call = GeneralUtils.callX(objectExpression, methodNode.getName(), (Expression)GeneralUtils.args(fieldNode.isStatic() ? GeneralUtils.nullX() : objectExpression, rhsValue));
                    call.setImplicitThis(expression.isImplicitThis());
                    call.setSpreadSafe(expression.isSpreadSafe());
                    call.setSafe(expression.isSafe());
                    call.setMethodTarget(methodNode);
                    call.visit(this.controller.getAcg());
                    operandStack.pop();
                    compileStack.removeVar(i);
                    return;
                }
            }
            if (GeneralUtils.isOrImplements(receiverType, ClassHelper.MAP_TYPE) && !isClassReceiver[0]) {
                MethodVisitor mv = this.controller.getMethodVisitor();
                ClassNode rhsType = operandStack.getTopOperand();
                int rhs = compileStack.defineTemporaryVariable("$rhs", rhsType, true);
                compileStack.pushLHS(false);
                objectExpression.visit(this.controller.getAcg());
                compileStack.popLHS();
                Label skip = new Label();
                if (expression.isSafe()) {
                    mv.visitInsn(89);
                    mv.visitJumpInsn(198, skip);
                }
                mv.visitLdcInsn(name);
                BytecodeHelper.load(mv, rhsType, rhs);
                if (ClassHelper.isPrimitiveType(rhsType)) {
                    BytecodeHelper.doCastToWrappedType(mv, rhsType, ClassHelper.getWrapper(rhsType));
                }
                mv.visitMethodInsn(185, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
                if (expression.isSafe()) {
                    mv.visitLabel(skip);
                }
                operandStack.pop();
                compileStack.removeVar(rhs);
                return;
            }
        }
        super.fallbackAttributeOrPropertySite(expression, objectExpression, name, adapter);
    }

    private ClassNode getPropertyOwnerType(Expression receiver, boolean ... isClassReceiver) {
        ClassNode receiverType;
        Variable variable;
        Object inferredType = receiver.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
        if (inferredType == null && receiver instanceof VariableExpression && (variable = ((VariableExpression)receiver).getAccessedVariable()) instanceof Expression) {
            inferredType = ((Expression)((Object)variable)).getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE);
        }
        if (inferredType instanceof ClassNode) {
            receiverType = (ClassNode)inferredType;
        } else {
            receiverType = (ClassNode)receiver.getNodeMetaData((Object)StaticCompilationMetadataKeys.PROPERTY_OWNER);
            if (receiverType == null) {
                receiverType = this.controller.getTypeChooser().resolveType(receiver, this.controller.getClassNode());
            }
        }
        if (StaticTypeCheckingSupport.isClassClassNodeWrappingConcreteType(receiverType)) {
            if (isClassReceiver.length > 0) {
                isClassReceiver[0] = true;
            }
            receiverType = receiverType.getGenericsTypes()[0].getType();
        }
        if (ClassHelper.isPrimitiveType(receiverType)) {
            receiverType = ClassHelper.getWrapper(receiverType);
        }
        return receiverType;
    }

    private boolean setField(PropertyExpression expression, Expression objectExpression, ClassNode receiverType, String name) {
        if (expression.isSafe()) {
            return false;
        }
        FieldNode fn = AsmClassGenerator.getDeclaredFieldOfCurrentClassOrAccessibleFieldOfSuper(this.controller.getClassNode(), receiverType, name, false);
        if (fn == null) {
            return false;
        }
        OperandStack stack = this.controller.getOperandStack();
        stack.doGroovyCast(fn.getType());
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (!fn.isStatic()) {
            this.controller.getCompileStack().pushLHS(false);
            objectExpression.visit(this.controller.getAcg());
            this.controller.getCompileStack().popLHS();
            if (!receiverType.equals(stack.getTopOperand())) {
                BytecodeHelper.doCast(mv, receiverType);
                stack.replace(receiverType);
            }
            stack.swap();
            mv.visitFieldInsn(181, BytecodeHelper.getClassInternalName(fn.getOwner()), name, BytecodeHelper.getTypeDescription(fn.getType()));
            stack.remove(1);
        } else {
            mv.visitFieldInsn(179, BytecodeHelper.getClassInternalName(receiverType), name, BytecodeHelper.getTypeDescription(fn.getType()));
        }
        return true;
    }

    private void addPropertyAccessError(Expression receiver, String propertyName, ClassNode receiverType) {
        String receiverName = (receiver instanceof ClassExpression ? receiver.getType() : receiverType).toString(false);
        String message = "Access to " + receiverName + "#" + propertyName + " is forbidden";
        this.controller.getSourceUnit().addError(new SyntaxException(message, receiver));
    }
}

