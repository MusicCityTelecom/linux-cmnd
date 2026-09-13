/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.sc;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.apache.groovy.ast.tools.ExpressionUtils;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GroovyCodeVisitor;
import org.codehaus.groovy.ast.InnerClassNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.decompiled.DecompiledClassNode;
import org.codehaus.groovy.ast.expr.ArgumentListExpression;
import org.codehaus.groovy.ast.expr.ArrayExpression;
import org.codehaus.groovy.ast.expr.AttributeExpression;
import org.codehaus.groovy.ast.expr.ClassExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.ConstructorCallExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.expr.ExpressionTransformer;
import org.codehaus.groovy.ast.expr.MethodCallExpression;
import org.codehaus.groovy.ast.expr.PropertyExpression;
import org.codehaus.groovy.ast.expr.TupleExpression;
import org.codehaus.groovy.ast.expr.VariableExpression;
import org.codehaus.groovy.ast.stmt.ForStatement;
import org.codehaus.groovy.ast.tools.GeneralUtils;
import org.codehaus.groovy.classgen.AsmClassGenerator;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.CallSiteWriter;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.ExpressionAsVariableSlot;
import org.codehaus.groovy.classgen.asm.InvocationWriter;
import org.codehaus.groovy.classgen.asm.MethodCallerMultiAdapter;
import org.codehaus.groovy.classgen.asm.OperandStack;
import org.codehaus.groovy.classgen.asm.TypeChooser;
import org.codehaus.groovy.classgen.asm.VariableSlotLoader;
import org.codehaus.groovy.classgen.asm.WriterController;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesCallSiteWriter;
import org.codehaus.groovy.classgen.asm.sc.StaticTypesWriterController;
import org.codehaus.groovy.runtime.InvokerHelper;
import org.codehaus.groovy.syntax.SyntaxException;
import org.codehaus.groovy.transform.sc.StaticCompilationMetadataKeys;
import org.codehaus.groovy.transform.sc.StaticCompilationVisitor;
import org.codehaus.groovy.transform.sc.TemporaryVariableExpression;
import org.codehaus.groovy.transform.stc.ExtensionMethodNode;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingSupport;
import org.codehaus.groovy.transform.stc.StaticTypeCheckingVisitor;
import org.codehaus.groovy.transform.stc.StaticTypesMarker;
import org.codehaus.groovy.transform.trait.Traits;

public class StaticInvocationWriter
extends InvocationWriter {
    private static final ClassNode INVOKERHELPER_CLASSNODE = ClassHelper.make(InvokerHelper.class);
    private static final MethodNode INVOKERHELPER_INVOKEMETHOD = INVOKERHELPER_CLASSNODE.getMethod("invokeMethodSafe", new Parameter[]{new Parameter(ClassHelper.OBJECT_TYPE, "object"), new Parameter(ClassHelper.STRING_TYPE, "name"), new Parameter(ClassHelper.OBJECT_TYPE, "args")});
    private static final MethodNode INVOKERHELPER_INVOKESTATICMETHOD = INVOKERHELPER_CLASSNODE.getMethod("invokeStaticMethod", new Parameter[]{new Parameter(ClassHelper.CLASS_Type, "clazz"), new Parameter(ClassHelper.STRING_TYPE, "name"), new Parameter(ClassHelper.OBJECT_TYPE, "args")});
    private final AtomicInteger labelCounter = new AtomicInteger();
    private MethodCallExpression currentCall;

    public StaticInvocationWriter(WriterController wc) {
        super(wc);
    }

    @Override
    public void writeInvokeMethod(MethodCallExpression call) {
        MethodCallExpression old = this.currentCall;
        this.currentCall = call;
        super.writeInvokeMethod(call);
        this.currentCall = old;
    }

    @Override
    public void writeInvokeConstructor(ConstructorCallExpression call) {
        ConstructorNode cn;
        MethodNode mn = (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
        if (mn == null) {
            super.writeInvokeConstructor(call);
            return;
        }
        if (this.writeAICCall(call)) {
            return;
        }
        if (mn instanceof ConstructorNode) {
            cn = (ConstructorNode)mn;
        } else {
            cn = new ConstructorNode(mn.getModifiers(), mn.getParameters(), mn.getExceptions(), mn.getCode());
            cn.setDeclaringClass(mn.getDeclaringClass());
        }
        ArgumentListExpression args = StaticInvocationWriter.makeArgumentList(call.getArguments());
        if (cn.isPrivate()) {
            ClassNode classNode = this.controller.getClassNode();
            ClassNode declaringClass = cn.getDeclaringClass();
            if (declaringClass != classNode) {
                MethodNode bridge = null;
                if (call.getNodeMetaData((Object)StaticTypesMarker.PV_METHODS_ACCESS) != null) {
                    Map bridgeMethods = (Map)declaringClass.getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_BRIDGE_METHODS);
                    MethodNode methodNode = bridge = bridgeMethods != null ? (MethodNode)bridgeMethods.get(cn) : null;
                }
                if (bridge instanceof ConstructorNode) {
                    ArgumentListExpression newArgs = GeneralUtils.args(GeneralUtils.nullX());
                    for (Expression arg : args) {
                        newArgs.addExpression(arg);
                    }
                    cn = (ConstructorNode)bridge;
                    args = newArgs;
                } else {
                    this.controller.getSourceUnit().addError(new SyntaxException("Cannot call private constructor for " + declaringClass.toString(false) + " from class " + classNode.toString(false), call));
                }
            }
        }
        String ownerDescriptor = this.prepareConstructorCall(cn);
        int before = this.controller.getOperandStack().getStackLength();
        this.loadArguments(args.getExpressions(), cn.getParameters());
        this.finnishConstructorCall(cn, ownerDescriptor, this.controller.getOperandStack().getStackLength() - before);
    }

    @Override
    public void writeSpecialConstructorCall(ConstructorCallExpression call) {
        ConstructorNode cn;
        MethodNode mn = (MethodNode)call.getNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET);
        if (mn == null) {
            super.writeSpecialConstructorCall(call);
            return;
        }
        this.controller.getCompileStack().pushInSpecialConstructorCall();
        if (mn instanceof ConstructorNode) {
            cn = (ConstructorNode)mn;
        } else {
            cn = new ConstructorNode(mn.getModifiers(), mn.getParameters(), mn.getExceptions(), mn.getCode());
            cn.setDeclaringClass(mn.getDeclaringClass());
        }
        this.controller.getMethodVisitor().visitVarInsn(25, 0);
        String ownerDescriptor = BytecodeHelper.getClassInternalName(cn.getDeclaringClass());
        ArgumentListExpression args = StaticInvocationWriter.makeArgumentList(call.getArguments());
        int before = this.controller.getOperandStack().getStackLength();
        this.loadArguments(args.getExpressions(), cn.getParameters());
        this.finnishConstructorCall(cn, ownerDescriptor, this.controller.getOperandStack().getStackLength() - before);
        this.controller.getOperandStack().remove(1);
        this.controller.getCompileStack().pop();
    }

    @Deprecated
    protected boolean tryBridgeMethod(MethodNode target, Expression receiver, boolean implicitThis, TupleExpression args) {
        return this.tryBridgeMethod(target, receiver, implicitThis, args, null);
    }

    protected boolean tryBridgeMethod(MethodNode target, Expression receiver, boolean implicitThis, TupleExpression args, ClassNode thisClass) {
        Map bridges;
        MethodNode bridge;
        ClassNode lookupClassNode;
        if (target.isProtected()) {
            for (lookupClassNode = this.controller.getClassNode(); lookupClassNode != null && !lookupClassNode.isDerivedFrom(target.getDeclaringClass()); lookupClassNode = lookupClassNode.getOuterClass()) {
            }
            if (lookupClassNode == null) {
                return false;
            }
        } else {
            lookupClassNode = target.getDeclaringClass().redirect();
        }
        MethodNode methodNode = bridge = (bridges = (Map)lookupClassNode.getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_BRIDGE_METHODS)) == null ? null : (MethodNode)bridges.get(target);
        if (bridge != null) {
            Expression fixedReceiver = receiver;
            if (implicitThis) {
                if (!this.controller.isInGeneratedFunction()) {
                    if (!thisClass.isDerivedFrom(lookupClassNode)) {
                        fixedReceiver = GeneralUtils.propX((Expression)GeneralUtils.classX(lookupClassNode), "this");
                    }
                } else if (thisClass != null) {
                    ClassNode current = thisClass.getOuterClass();
                    fixedReceiver = GeneralUtils.varX("thisObject", current);
                    while (current.getOuterClass() != null && !lookupClassNode.equals(current)) {
                        FieldNode thisField = current.getField("this$0");
                        current = current.getOuterClass();
                        if (thisField == null) continue;
                        fixedReceiver = GeneralUtils.propX(fixedReceiver, "this$0");
                        fixedReceiver.setType(current);
                    }
                }
            }
            ArgumentListExpression newArgs = GeneralUtils.args(target.isStatic() ? GeneralUtils.nullX() : fixedReceiver);
            for (Expression expression : args.getExpressions()) {
                newArgs.addExpression(expression);
            }
            return this.writeDirectMethodCall(bridge, implicitThis, fixedReceiver, newArgs);
        }
        return false;
    }

    @Override
    protected boolean writeDirectMethodCall(MethodNode target, boolean implicitThis, Expression receiver, TupleExpression args) {
        if (target == null) {
            return false;
        }
        ClassNode classNode = this.controller.getClassNode();
        if (target instanceof ExtensionMethodNode) {
            ExtensionMethodNode emn = (ExtensionMethodNode)target;
            MethodVisitor mv = this.controller.getMethodVisitor();
            MethodNode node = emn.getExtensionMethodNode();
            Parameter[] parameters = node.getParameters();
            ClassNode returnType = node.getReturnType();
            ArrayList<Expression> argumentList = new ArrayList<Expression>();
            if (emn.isStaticExtension()) {
                argumentList.add(GeneralUtils.nullX());
            } else {
                Expression fixedReceiver = null;
                if (ExpressionUtils.isThisOrSuper(receiver) && classNode.getOuterClass() != null && this.controller.isInGeneratedFunction()) {
                    ClassNode current = classNode.getOuterClass();
                    fixedReceiver = GeneralUtils.varX("thisObject", current);
                    while (current.getOuterClass() != null && !classNode.equals(current)) {
                        FieldNode thisField = current.getField("this$0");
                        current = current.getOuterClass();
                        if (thisField == null) continue;
                        fixedReceiver = GeneralUtils.propX(fixedReceiver, "this$0");
                        fixedReceiver.setType(current);
                    }
                }
                argumentList.add(fixedReceiver != null ? fixedReceiver : receiver);
            }
            argumentList.addAll(args.getExpressions());
            this.loadArguments(argumentList, parameters);
            String owner = BytecodeHelper.getClassInternalName(node.getDeclaringClass());
            String desc = BytecodeHelper.getMethodDescriptor(returnType, parameters);
            mv.visitMethodInsn(184, owner, target.getName(), desc, false);
            this.controller.getOperandStack().remove(argumentList.size());
            if (ClassHelper.isPrimitiveVoid(returnType)) {
                returnType = ClassHelper.OBJECT_TYPE;
                mv.visitInsn(1);
            }
            this.controller.getOperandStack().push(returnType);
            return true;
        }
        if (target == StaticTypeCheckingVisitor.CLOSURE_CALL_VARGS) {
            ArrayExpression arr = new ArrayExpression(ClassHelper.OBJECT_TYPE, args.getExpressions());
            return super.writeDirectMethodCall(target, implicitThis, receiver, GeneralUtils.args(arr));
        }
        if (!target.isPublic() && this.controller.isInGeneratedFunction() && target.getDeclaringClass() != classNode) {
            if (!this.tryBridgeMethod(target, receiver, implicitThis, args, classNode)) {
                MethodNode methodNode = target.isStatic() ? INVOKERHELPER_INVOKESTATICMETHOD : INVOKERHELPER_INVOKEMETHOD;
                MethodCallExpression mce = GeneralUtils.callX((Expression)GeneralUtils.classX(INVOKERHELPER_CLASSNODE), methodNode.getName(), (Expression)GeneralUtils.args(target.isStatic() ? GeneralUtils.classX(target.getDeclaringClass()) : receiver, GeneralUtils.constX(target.getName()), new ArrayExpression(ClassHelper.OBJECT_TYPE, args.getExpressions())));
                mce.setMethodTarget(methodNode);
                mce.visit(this.controller.getAcg());
            }
            return true;
        }
        if (target.isPrivate() && this.tryPrivateMethod(target, implicitThis, receiver, args, classNode)) {
            return true;
        }
        Expression fixedReceiver = null;
        boolean fixedImplicitThis = implicitThis;
        if (target.isProtected()) {
            ClassNode node;
            ClassNode classNode2 = node = receiver == null ? ClassHelper.OBJECT_TYPE : this.controller.getTypeChooser().resolveType(receiver, this.controller.getClassNode());
            if (!implicitThis && !ExpressionUtils.isThisOrSuper(receiver) && !ClassNodeUtils.samePackageName(node, classNode) && StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(node, target.getDeclaringClass())) {
                this.controller.getSourceUnit().addError(new SyntaxException("Method " + target.getName() + " is protected in " + target.getDeclaringClass().toString(false), receiver != null ? receiver : args));
            } else if (!node.isDerivedFrom(target.getDeclaringClass()) && this.tryBridgeMethod(target, receiver, implicitThis, args, classNode)) {
                return true;
            }
        } else if (target.isPublic() && receiver != null && implicitThis && this.controller.isInGeneratedFunction() && !classNode.isDerivedFrom(target.getDeclaringClass()) && !classNode.implementsInterface(target.getDeclaringClass())) {
            ClassNode thisType = this.controller.getThisType();
            if (Traits.isTrait(thisType.getOuterClass())) {
                thisType = ClassHelper.dynamicType();
            }
            fixedReceiver = GeneralUtils.varX("thisObject", thisType);
            while (thisType.getOuterClass() != null && !target.getDeclaringClass().equals(thisType)) {
                FieldNode thisField = thisType.getField("this$0");
                thisType = thisType.getOuterClass();
                if (thisField == null) continue;
                fixedReceiver = GeneralUtils.propX(fixedReceiver, "this$0");
                fixedReceiver.setType(thisType);
                fixedImplicitThis = false;
            }
        }
        if (receiver != null && !ExpressionUtils.isSuperExpression(receiver)) {
            return super.writeDirectMethodCall(target, fixedImplicitThis, new CheckcastReceiverExpression(fixedReceiver != null ? fixedReceiver : receiver, target), args);
        }
        return super.writeDirectMethodCall(target, implicitThis, receiver, args);
    }

    private boolean tryPrivateMethod(MethodNode target, boolean implicitThis, Expression receiver, TupleExpression args, ClassNode classNode) {
        ClassNode declaringClass = target.getDeclaringClass();
        if ((StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(declaringClass, classNode) || StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(classNode, declaringClass)) && declaringClass.getNodeMetaData((Object)StaticCompilationMetadataKeys.PRIVATE_BRIDGE_METHODS) != null && !declaringClass.equals(classNode)) {
            if (this.tryBridgeMethod(target, receiver, implicitThis, args, classNode)) {
                return true;
            }
            this.checkAndAddCannotCallPrivateMethodError(target, receiver, classNode, declaringClass);
        }
        this.checkAndAddCannotCallPrivateMethodError(target, receiver, classNode, declaringClass);
        return false;
    }

    private void checkAndAddCannotCallPrivateMethodError(MethodNode target, Expression receiver, ClassNode classNode, ClassNode declaringClass) {
        if (declaringClass != classNode) {
            this.controller.getSourceUnit().addError(new SyntaxException("Cannot call private method " + (target.isStatic() ? "static " : "") + declaringClass.toString(false) + "#" + target.getName() + " from class " + classNode.toString(false), receiver));
        }
    }

    protected static boolean isPrivateBridgeMethodsCallAllowed(ClassNode receiver, ClassNode caller) {
        if (receiver == null) {
            return false;
        }
        if (receiver.redirect() == caller) {
            return true;
        }
        if (StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(receiver.getOuterClass(), caller)) {
            return true;
        }
        return caller.getOuterClass() != null && StaticInvocationWriter.isPrivateBridgeMethodsCallAllowed(receiver, caller.getOuterClass());
    }

    @Override
    protected void loadArguments(List<Expression> argumentList, Parameter[] parameters) {
        block12: {
            TypeChooser typeChooser;
            ClassNode classNode;
            int nPrms;
            int nArgs;
            block11: {
                nArgs = argumentList.size();
                nPrms = parameters.length;
                if (nPrms == 0) {
                    return;
                }
                classNode = this.controller.getClassNode();
                typeChooser = this.controller.getTypeChooser();
                ClassNode lastArgType = nArgs == 0 ? null : typeChooser.resolveType(argumentList.get(nArgs - 1), classNode);
                ClassNode lastPrmType = parameters[nPrms - 1].getOriginType();
                if (!lastPrmType.isArray() || nArgs <= nPrms && nArgs != nPrms - 1 && (nArgs != nPrms || lastArgType.isArray() || !StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(lastArgType, lastPrmType.getComponentType()) && (!ClassHelper.isGStringType(lastArgType) || !ClassHelper.isStringType(lastPrmType.getComponentType())))) break block11;
                OperandStack operandStack = this.controller.getOperandStack();
                int stackLength = operandStack.getStackLength() + nArgs;
                for (int i = 0; i < nPrms - 1; ++i) {
                    this.visitArgument(argumentList.get(i), parameters[i].getType());
                }
                ArrayList<Expression> lastArgs = new ArrayList<Expression>();
                for (int i = nPrms - 1; i < nArgs; ++i) {
                    lastArgs.add(argumentList.get(i));
                }
                ArrayExpression array = new ArrayExpression(lastPrmType.getComponentType(), lastArgs);
                array.visit(this.controller.getAcg());
                while (operandStack.getStackLength() < stackLength) {
                    operandStack.push(ClassHelper.OBJECT_TYPE);
                }
                if (nArgs != nPrms - 1) break block12;
                operandStack.remove(1);
                break block12;
            }
            if (nArgs == nPrms) {
                for (int i = 0; i < nArgs; ++i) {
                    this.visitArgument(argumentList.get(i), parameters[i].getType());
                }
            } else {
                int i;
                Expression[] arguments = new Expression[nPrms];
                int j = 0;
                for (i = 0; i < nPrms; ++i) {
                    Parameter p = parameters[i];
                    ClassNode pType = p.getType();
                    Expression a = j < nArgs ? argumentList.get(j) : null;
                    ClassNode aType = a == null ? null : typeChooser.resolveType(a, classNode);
                    Expression expression = StaticInvocationWriter.getInitialExpression(p);
                    if (expression != null && !StaticInvocationWriter.isCompatibleArgumentType(aType, pType)) {
                        arguments[i] = expression;
                        continue;
                    }
                    if (a != null) {
                        arguments[i] = a;
                        ++j;
                        continue;
                    }
                    this.controller.getSourceUnit().addFatalError("Binding failed for arguments [" + argumentList.stream().map(arg -> typeChooser.resolveType((Expression)arg, classNode).toString(false)).collect(Collectors.joining(", ")) + "] and parameters [" + Arrays.stream(parameters).map(prm -> prm.getType().toString(false)).collect(Collectors.joining(", ")) + "]", this.getCurrentCall());
                }
                for (i = 0; i < nArgs; ++i) {
                    this.visitArgument(arguments[i], parameters[i].getType());
                }
            }
        }
    }

    private static Expression getInitialExpression(Parameter parameter) {
        Expression initialExpression = (Expression)parameter.getNodeMetaData((Object)StaticTypesMarker.INITIAL_EXPRESSION);
        if (initialExpression == null && parameter.hasInitialExpression()) {
            initialExpression = parameter.getInitialExpression();
        }
        if (initialExpression == null && parameter.getNodeMetaData("INITIAL_EXPRESSION") != null) {
            initialExpression = (Expression)parameter.getNodeMetaData("INITIAL_EXPRESSION");
        }
        return initialExpression;
    }

    private static boolean isCompatibleArgumentType(ClassNode argumentType, ClassNode parameterType) {
        if (argumentType == null) {
            return false;
        }
        if (ClassHelper.getWrapper(argumentType).equals(ClassHelper.getWrapper(parameterType))) {
            return true;
        }
        if (parameterType.isInterface()) {
            return argumentType.implementsInterface(parameterType);
        }
        if (parameterType.isArray() && argumentType.isArray()) {
            return StaticInvocationWriter.isCompatibleArgumentType(argumentType.getComponentType(), parameterType.getComponentType());
        }
        return ClassHelper.getWrapper(argumentType).isDerivedFrom(ClassHelper.getWrapper(parameterType));
    }

    private void visitArgument(Expression argumentExpr, ClassNode parameterType) {
        argumentExpr.putNodeMetaData((Object)StaticTypesMarker.PARAMETER_TYPE, parameterType);
        argumentExpr.visit(this.controller.getAcg());
        if (!ExpressionUtils.isNullConstant(argumentExpr)) {
            this.controller.getOperandStack().doGroovyCast(parameterType);
        }
    }

    @Override
    public void makeCall(Expression origin, Expression receiver, Expression message, Expression arguments, MethodCallerMultiAdapter adapter, boolean safe, boolean spreadSafe, boolean implicitThis) {
        if (origin.getNodeMetaData((Object)StaticTypesMarker.DYNAMIC_RESOLUTION) != null) {
            StaticTypesWriterController staticController = (StaticTypesWriterController)this.controller;
            if (origin instanceof MethodCallExpression) {
                ((MethodCallExpression)origin).setMethodTarget(null);
            }
            InvocationWriter dynamicInvocationWriter = staticController.getRegularInvocationWriter();
            dynamicInvocationWriter.makeCall(origin, receiver, message, arguments, adapter, safe, spreadSafe, implicitThis);
            return;
        }
        if (implicitThis && this.tryImplicitReceiver(origin, message, arguments, adapter, safe, spreadSafe)) {
            return;
        }
        if (spreadSafe && origin instanceof MethodCallExpression) {
            Expression tmpReceiver = receiver;
            if (!(receiver instanceof VariableExpression) && !(receiver instanceof ConstantExpression)) {
                tmpReceiver = new TemporaryVariableExpression(receiver);
            }
            MethodVisitor mv = this.controller.getMethodVisitor();
            CompileStack compileStack = this.controller.getCompileStack();
            TypeChooser typeChooser = this.controller.getTypeChooser();
            OperandStack operandStack = this.controller.getOperandStack();
            ClassNode classNode = this.controller.getClassNode();
            int counter = this.labelCounter.incrementAndGet();
            ConstructorCallExpression cce = GeneralUtils.ctorX(StaticCompilationVisitor.ARRAYLIST_CLASSNODE);
            cce.setNodeMetaData((Object)StaticTypesMarker.DIRECT_METHOD_CALL_TARGET, StaticCompilationVisitor.ARRAYLIST_CONSTRUCTOR);
            TemporaryVariableExpression result = new TemporaryVariableExpression(cce);
            result.visit(this.controller.getAcg());
            operandStack.pop();
            tmpReceiver.visit(this.controller.getAcg());
            Label ifnull = compileStack.createLocalLabel("ifnull_" + counter);
            mv.visitJumpInsn(198, ifnull);
            operandStack.remove(1);
            Label nonull = compileStack.createLocalLabel("nonull_" + counter);
            mv.visitLabel(nonull);
            ClassNode componentType = StaticTypeCheckingVisitor.inferLoopElementType(typeChooser.resolveType(tmpReceiver, classNode));
            Parameter iterator = new Parameter(componentType, "for$it$" + counter);
            VariableExpression iteratorAsVar = GeneralUtils.varX(iterator);
            MethodCallExpression origMCE = (MethodCallExpression)origin;
            MethodCallExpression newMCE = GeneralUtils.callX((Expression)iteratorAsVar, origMCE.getMethodAsString(), origMCE.getArguments());
            newMCE.setImplicitThis(false);
            newMCE.setMethodTarget(origMCE.getMethodTarget());
            newMCE.setSafe(true);
            MethodCallExpression add = GeneralUtils.callX((Expression)result, "add", (Expression)newMCE);
            add.setImplicitThis(false);
            add.setMethodTarget(StaticCompilationVisitor.ARRAYLIST_ADD_METHOD);
            ForStatement stmt = new ForStatement(iterator, tmpReceiver, GeneralUtils.stmt(add));
            stmt.visit(this.controller.getAcg());
            mv.visitLabel(ifnull);
            result.visit(this.controller.getAcg());
            if (tmpReceiver instanceof TemporaryVariableExpression) {
                ((TemporaryVariableExpression)tmpReceiver).remove(this.controller);
            }
            result.remove(this.controller);
        } else if (safe && origin instanceof MethodCallExpression) {
            MethodVisitor mv = this.controller.getMethodVisitor();
            CompileStack compileStack = this.controller.getCompileStack();
            OperandStack operandStack = this.controller.getOperandStack();
            int counter = this.labelCounter.incrementAndGet();
            ExpressionAsVariableSlot slot = new ExpressionAsVariableSlot(this.controller, receiver);
            slot.visit(this.controller.getAcg());
            operandStack.box();
            Label ifnull = compileStack.createLocalLabel("ifnull_" + counter);
            mv.visitJumpInsn(198, ifnull);
            operandStack.remove(1);
            Label nonull = compileStack.createLocalLabel("nonull_" + counter);
            mv.visitLabel(nonull);
            MethodCallExpression origMCE = (MethodCallExpression)origin;
            MethodCallExpression newMCE = GeneralUtils.callX((Expression)new VariableSlotLoader(slot.getType(), slot.getIndex(), this.controller.getOperandStack()), origMCE.getMethodAsString(), origMCE.getArguments());
            MethodNode methodTarget = origMCE.getMethodTarget();
            newMCE.setImplicitThis(origMCE.isImplicitThis());
            newMCE.setMethodTarget(methodTarget);
            newMCE.setSafe(false);
            newMCE.setSourcePosition(origMCE);
            newMCE.getObjectExpression().setSourcePosition(origMCE.getObjectExpression());
            newMCE.visit(this.controller.getAcg());
            compileStack.removeVar(slot.getIndex());
            ClassNode returnType = operandStack.getTopOperand();
            if (ClassHelper.isPrimitiveType(returnType) && !ClassHelper.isPrimitiveVoid(returnType)) {
                operandStack.box();
            }
            Label endof = compileStack.createLocalLabel("endof_" + counter);
            mv.visitJumpInsn(167, endof);
            mv.visitLabel(ifnull);
            mv.visitInsn(1);
            mv.visitLabel(endof);
        } else {
            if (origin instanceof AttributeExpression && (adapter == AsmClassGenerator.getField || adapter == AsmClassGenerator.getGroovyObjectField)) {
                ClassNode receiverType;
                CallSiteWriter callSiteWriter = this.controller.getCallSiteWriter();
                String fieldName = ((AttributeExpression)origin).getPropertyAsString();
                if (fieldName != null && callSiteWriter instanceof StaticTypesCallSiteWriter && ((StaticTypesCallSiteWriter)callSiteWriter).makeGetField(receiver, receiverType = this.controller.getTypeChooser().resolveType(receiver, this.controller.getClassNode()), fieldName, safe, false)) {
                    return;
                }
            }
            super.makeCall(origin, receiver, message, arguments, adapter, safe, spreadSafe, implicitThis);
        }
    }

    private boolean tryImplicitReceiver(Expression origin, Expression message, Expression arguments, MethodCallerMultiAdapter adapter, boolean safe, boolean spreadSafe) {
        Object implicitReceiver = origin.getNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER);
        if (implicitReceiver == null && origin instanceof MethodCallExpression) {
            implicitReceiver = ((MethodCallExpression)origin).getObjectExpression().getNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER);
        }
        if (implicitReceiver != null) {
            String[] path = ((String)implicitReceiver).split("\\.");
            PropertyExpression pexp = GeneralUtils.propX((Expression)GeneralUtils.varX("this", ClassHelper.CLOSURE_TYPE), path[0]);
            pexp.setImplicitThis(true);
            int n = path.length;
            for (int i = 1; i < n; ++i) {
                pexp.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, ClassHelper.CLOSURE_TYPE);
                pexp = GeneralUtils.propX((Expression)pexp, path[i]);
            }
            pexp.putNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER, implicitReceiver);
            origin.removeNodeMetaData((Object)StaticTypesMarker.IMPLICIT_RECEIVER);
            if (origin instanceof PropertyExpression) {
                PropertyExpression rewritten = GeneralUtils.propX(pexp, ((PropertyExpression)origin).getProperty(), ((PropertyExpression)origin).isSafe());
                rewritten.setSpreadSafe(((PropertyExpression)origin).isSpreadSafe());
                rewritten.visit(this.controller.getAcg());
                rewritten.putNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE, origin.getNodeMetaData((Object)StaticTypesMarker.INFERRED_TYPE));
            } else {
                this.makeCall(origin, pexp, message, arguments, adapter, safe, spreadSafe, false);
            }
            return true;
        }
        return false;
    }

    public MethodCallExpression getCurrentCall() {
        return this.currentCall;
    }

    @Override
    protected boolean makeCachedCall(Expression origin, ClassExpression sender, Expression receiver, Expression message, Expression arguments, MethodCallerMultiAdapter adapter, boolean safe, boolean spreadSafe, boolean implicitThis, boolean containsSpreadExpression) {
        return false;
    }

    private class CheckcastReceiverExpression
    extends Expression {
        private final Expression receiver;
        private final MethodNode target;

        public CheckcastReceiverExpression(Expression receiver, MethodNode target) {
            this.receiver = receiver;
            this.target = target;
            this.setType(null);
        }

        @Override
        public Expression transformExpression(ExpressionTransformer transformer) {
            return this;
        }

        @Override
        public void visit(GroovyCodeVisitor visitor) {
            this.receiver.visit(visitor);
            if (visitor instanceof AsmClassGenerator) {
                ClassNode topOperand = StaticInvocationWriter.this.controller.getOperandStack().getTopOperand();
                ClassNode type = this.getType();
                if (ClassHelper.isGStringType(topOperand) && ClassHelper.isStringType(type)) {
                    StaticInvocationWriter.this.controller.getOperandStack().doGroovyCast(type);
                    return;
                }
                if (ClassHelper.isPrimitiveType(topOperand) && !ClassHelper.isPrimitiveType(type)) {
                    StaticInvocationWriter.this.controller.getOperandStack().box();
                } else if (!ClassHelper.isPrimitiveType(topOperand) && ClassHelper.isPrimitiveType(type)) {
                    StaticInvocationWriter.this.controller.getOperandStack().doGroovyCast(type);
                }
                if (StaticTypeCheckingSupport.implementsInterfaceOrIsSubclassOf(topOperand, type)) {
                    return;
                }
                StaticInvocationWriter.this.controller.getMethodVisitor().visitTypeInsn(192, type.isArray() ? BytecodeHelper.getTypeDescription(type) : BytecodeHelper.getClassInternalName(type.getName()));
                StaticInvocationWriter.this.controller.getOperandStack().replace(type);
            }
        }

        @Override
        public ClassNode getType() {
            ClassNode type = super.getType();
            if (type == null) {
                if (this.target instanceof ExtensionMethodNode) {
                    type = ((ExtensionMethodNode)this.target).getExtensionMethodNode().getDeclaringClass();
                } else {
                    type = StaticInvocationWriter.this.controller.getTypeChooser().resolveType(this.receiver, StaticInvocationWriter.this.controller.getClassNode());
                    if (ClassHelper.isPrimitiveType(type)) {
                        type = ClassHelper.getWrapper(type);
                    }
                    ClassNode declaringClass = this.target.getDeclaringClass();
                    if (type.getClass() != ClassNode.class && type.getClass() != InnerClassNode.class && type.getClass() != DecompiledClassNode.class) {
                        type = declaringClass;
                    }
                    if (ClassHelper.isObjectType(declaringClass)) {
                        type = ClassHelper.OBJECT_TYPE;
                    } else if (ClassHelper.isObjectType(type)) {
                        type = declaringClass;
                    }
                }
                this.setType(type);
            }
            return type;
        }
    }
}

