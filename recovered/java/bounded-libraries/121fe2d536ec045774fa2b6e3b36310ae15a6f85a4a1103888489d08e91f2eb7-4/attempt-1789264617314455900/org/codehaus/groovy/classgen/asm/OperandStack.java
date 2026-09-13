/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.apache.groovy.ast.tools.ClassNodeUtils;
import org.codehaus.groovy.GroovyBugError;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Variable;
import org.codehaus.groovy.ast.expr.CastExpression;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.expr.Expression;
import org.codehaus.groovy.ast.tools.WideningCategories;
import org.codehaus.groovy.classgen.ClassGeneratorException;
import org.codehaus.groovy.classgen.asm.BytecodeHelper;
import org.codehaus.groovy.classgen.asm.BytecodeVariable;
import org.codehaus.groovy.classgen.asm.CompileStack;
import org.codehaus.groovy.classgen.asm.WriterController;

public class OperandStack {
    private final List<ClassNode> stack = new ArrayList<ClassNode>();
    private final WriterController controller;

    public OperandStack(WriterController controller) {
        this.controller = controller;
    }

    public int getStackLength() {
        return this.stack.size();
    }

    public void popDownTo(int elements) {
        int last = this.stack.size();
        MethodVisitor mv = this.controller.getMethodVisitor();
        while (last > elements) {
            ClassNode element;
            if (OperandStack.isTwoSlotType(element = this.popWithMessage(--last))) {
                mv.visitInsn(88);
                continue;
            }
            mv.visitInsn(87);
        }
    }

    private ClassNode popWithMessage(int last) {
        try {
            return this.stack.remove(last);
        }
        catch (IndexOutOfBoundsException e) {
            String method = this.controller.getMethodNode() != null ? this.controller.getMethodNode().getTypeDescriptor() : this.controller.getConstructorNode().getTypeDescriptor();
            throw new GroovyBugError("Error while popping argument from operand stack tracker in class " + this.controller.getClassName() + " method " + method + ".");
        }
    }

    private static boolean isTwoSlotType(ClassNode type) {
        return ClassHelper.isPrimitiveLong(type) || ClassHelper.isPrimitiveDouble(type);
    }

    public void castToBool(int mark, boolean emptyDefault) {
        int size = this.stack.size();
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (mark == size) {
            if (emptyDefault) {
                mv.visitIntInsn(16, 1);
            } else {
                mv.visitIntInsn(16, 0);
            }
            this.stack.add(null);
        } else if (mark == size - 1) {
            ClassNode last = this.stack.get(size - 1);
            if (ClassHelper.isPrimitiveBoolean(last)) {
                return;
            }
            if (ClassHelper.isPrimitiveType(last)) {
                BytecodeHelper.convertPrimitiveToBoolean(mv, last);
            } else {
                this.controller.getInvocationWriter().castNonPrimitiveToBool(last);
            }
        } else {
            throw new GroovyBugError("operand stack contains " + size + " elements, but we expected only " + mark);
        }
        this.stack.set(mark, ClassHelper.boolean_TYPE);
    }

    public void pop() {
        this.popDownTo(this.stack.size() - 1);
    }

    public Label jump(int ifIns) {
        Label label = new Label();
        this.jump(ifIns, label);
        return label;
    }

    public void jump(int ifIns, Label label) {
        this.controller.getMethodVisitor().visitJumpInsn(ifIns, label);
        this.remove(1);
    }

    public void dup() {
        ClassNode type = this.getTopOperand();
        this.stack.add(type);
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (OperandStack.isTwoSlotType(type)) {
            mv.visitInsn(92);
        } else {
            mv.visitInsn(89);
        }
    }

    public ClassNode box() {
        MethodVisitor mv = this.controller.getMethodVisitor();
        int size = this.stack.size();
        ClassNode type = this.stack.get(size - 1);
        if (ClassHelper.isPrimitiveType(type) && !ClassHelper.isPrimitiveVoid(type)) {
            ClassNode wrapper = ClassHelper.getWrapper(type);
            BytecodeHelper.doCastToWrappedType(mv, type, wrapper);
            type = wrapper;
        }
        this.stack.set(size - 1, type);
        return type;
    }

    public void remove(int amount) {
        int size = this.stack.size();
        int n = size - 1 - amount;
        for (int i = size - 1; i > n; --i) {
            this.popWithMessage(i);
        }
    }

    public void push(ClassNode type) {
        this.stack.add(type);
    }

    public void swap() {
        MethodVisitor mv = this.controller.getMethodVisitor();
        int size = this.stack.size();
        ClassNode b = this.stack.get(size - 1);
        ClassNode a = this.stack.get(size - 2);
        if (OperandStack.isTwoSlotType(a)) {
            if (OperandStack.isTwoSlotType(b)) {
                mv.visitInsn(94);
                mv.visitInsn(88);
            } else {
                mv.visitInsn(91);
                mv.visitInsn(87);
            }
        } else if (OperandStack.isTwoSlotType(b)) {
            mv.visitInsn(93);
            mv.visitInsn(88);
        } else {
            mv.visitInsn(95);
        }
        this.stack.set(size - 1, a);
        this.stack.set(size - 2, b);
    }

    public void replace(ClassNode type) {
        int size = this.ensureStackNotEmpty(this.stack);
        this.stack.set(size - 1, type);
    }

    private int ensureStackNotEmpty(List<ClassNode> stack) {
        int size = stack.size();
        try {
            if (size == 0) {
                throw new ArrayIndexOutOfBoundsException("size==0");
            }
        }
        catch (ArrayIndexOutOfBoundsException ai) {
            System.err.println("index problem in " + this.controller.getSourceUnit().getName());
            throw ai;
        }
        return size;
    }

    public void replace(ClassNode type, int n) {
        this.remove(n);
        this.push(type);
    }

    public void doGroovyCast(ClassNode targetType) {
        this.doConvertAndCast(targetType, false);
    }

    public void doGroovyCast(Variable v) {
        this.doConvertAndCast(v.getOriginType(), false);
    }

    public void doAsType(ClassNode targetType) {
        this.doConvertAndCast(targetType, true);
    }

    private void throwExceptionForNoStackElement(int size, ClassNode targetType, boolean coerce) {
        ConstructorNode constructorNode;
        if (size > 0) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Internal compiler error while compiling ").append(this.controller.getSourceUnit().getName()).append("\n");
        MethodNode methodNode = this.controller.getMethodNode();
        if (methodNode != null) {
            sb.append("Method: ");
            sb.append(methodNode);
            sb.append("\n");
        }
        if ((constructorNode = this.controller.getConstructorNode()) != null) {
            sb.append("Constructor: ");
            sb.append(methodNode);
            sb.append("\n");
        }
        sb.append("Line ").append(this.controller.getLineNumber()).append(",");
        sb.append(" expecting ").append(coerce ? "coercion" : "casting").append(" to ").append(targetType.toString(false));
        sb.append(" but operand stack is empty");
        throw new ArrayIndexOutOfBoundsException(sb.toString());
    }

    private void doConvertAndCast(ClassNode targetType, boolean coerce) {
        int size = this.stack.size();
        this.throwExceptionForNoStackElement(size, targetType, coerce);
        ClassNode top = this.stack.get(size - 1);
        targetType = targetType.redirect();
        if (top == targetType || ClassNodeUtils.isCompatibleWith(top, targetType)) {
            return;
        }
        if (coerce) {
            this.controller.getInvocationWriter().coerce(top, targetType);
            return;
        }
        boolean primTarget = ClassHelper.isPrimitiveType(targetType);
        boolean primTop = ClassHelper.isPrimitiveType(top);
        if (primTop && primTarget) {
            if (this.convertPrimitive(top, targetType)) {
                this.replace(targetType);
                return;
            }
            this.box();
        } else if (!primTarget) {
            this.controller.getInvocationWriter().castToNonPrimitiveIfNecessary(top, targetType);
        }
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (primTarget && !ClassHelper.isPrimitiveBoolean(targetType) && !primTop && ClassHelper.getWrapper(targetType).equals(top)) {
            BytecodeHelper.doCastToPrimitive(mv, top, targetType);
        } else {
            top = this.stack.get(size - 1);
            if (!WideningCategories.implementsInterfaceOrSubclassOf(top, targetType)) {
                BytecodeHelper.doCast(mv, targetType);
            }
        }
        this.replace(targetType);
    }

    private boolean convertFromInt(ClassNode target) {
        int convertCode;
        if (ClassHelper.isPrimitiveChar(target)) {
            convertCode = 146;
        } else if (ClassHelper.isPrimitiveByte(target)) {
            convertCode = 145;
        } else if (ClassHelper.isPrimitiveShort(target)) {
            convertCode = 147;
        } else if (ClassHelper.isPrimitiveLong(target)) {
            convertCode = 133;
        } else if (ClassHelper.isPrimitiveFloat(target)) {
            convertCode = 134;
        } else if (ClassHelper.isPrimitiveDouble(target)) {
            convertCode = 135;
        } else {
            return false;
        }
        this.controller.getMethodVisitor().visitInsn(convertCode);
        return true;
    }

    private boolean convertFromLong(ClassNode target) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (ClassHelper.isPrimitiveInt(target)) {
            mv.visitInsn(136);
            return true;
        }
        if (ClassHelper.isPrimitiveChar(target) || ClassHelper.isPrimitiveByte(target) || ClassHelper.isPrimitiveShort(target)) {
            mv.visitInsn(136);
            return this.convertFromInt(target);
        }
        if (ClassHelper.isPrimitiveDouble(target)) {
            mv.visitInsn(138);
            return true;
        }
        if (ClassHelper.isPrimitiveFloat(target)) {
            mv.visitInsn(137);
            return true;
        }
        return false;
    }

    private boolean convertFromDouble(ClassNode target) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (ClassHelper.isPrimitiveInt(target)) {
            mv.visitInsn(142);
            return true;
        }
        if (ClassHelper.isPrimitiveChar(target) || ClassHelper.isPrimitiveByte(target) || ClassHelper.isPrimitiveShort(target)) {
            mv.visitInsn(142);
            return this.convertFromInt(target);
        }
        if (ClassHelper.isPrimitiveLong(target)) {
            mv.visitInsn(143);
            return true;
        }
        if (ClassHelper.isPrimitiveFloat(target)) {
            mv.visitInsn(144);
            return true;
        }
        return false;
    }

    private boolean convertFromFloat(ClassNode target) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        if (ClassHelper.isPrimitiveInt(target)) {
            mv.visitInsn(139);
            return true;
        }
        if (ClassHelper.isPrimitiveChar(target) || ClassHelper.isPrimitiveByte(target) || ClassHelper.isPrimitiveShort(target)) {
            mv.visitInsn(139);
            return this.convertFromInt(target);
        }
        if (ClassHelper.isPrimitiveLong(target)) {
            mv.visitInsn(140);
            return true;
        }
        if (ClassHelper.isPrimitiveDouble(target)) {
            mv.visitInsn(141);
            return true;
        }
        return false;
    }

    private boolean convertPrimitive(ClassNode top, ClassNode target) {
        if (top == target) {
            return true;
        }
        if (ClassHelper.isPrimitiveInt(top)) {
            return this.convertFromInt(target);
        }
        if (ClassHelper.isPrimitiveChar(top) || ClassHelper.isPrimitiveByte(top) || ClassHelper.isPrimitiveShort(top)) {
            return ClassHelper.isPrimitiveInt(target) || this.convertFromInt(target);
        }
        if (ClassHelper.isPrimitiveFloat(top)) {
            return this.convertFromFloat(target);
        }
        if (ClassHelper.isPrimitiveDouble(top)) {
            return this.convertFromDouble(target);
        }
        if (ClassHelper.isPrimitiveLong(top)) {
            return this.convertFromLong(target);
        }
        return false;
    }

    public void pushConstant(ConstantExpression expression) {
        boolean asPrimitive;
        ClassNode type;
        MethodVisitor mv = this.controller.getMethodVisitor();
        Object value = expression.getValue();
        ClassNode origType = expression.getType().redirect();
        boolean boxing = !origType.equals(type = ClassHelper.getUnwrapper(origType));
        boolean bl = asPrimitive = boxing || ClassHelper.isPrimitiveType(type);
        if (value == null) {
            mv.visitInsn(1);
        } else if (boxing && value instanceof Boolean) {
            Boolean bool = (Boolean)value;
            String text = bool != false ? "TRUE" : "FALSE";
            mv.visitFieldInsn(178, "java/lang/Boolean", text, "Ljava/lang/Boolean;");
            boxing = false;
            type = origType;
        } else if (asPrimitive) {
            OperandStack.pushPrimitiveConstant(mv, value, type);
        } else if (value instanceof BigDecimal) {
            OperandStack.newInstance(mv, value);
        } else if (value instanceof BigInteger) {
            OperandStack.newInstance(mv, value);
        } else if (value instanceof String) {
            mv.visitLdcInsn(value);
        } else {
            throw new ClassGeneratorException("Cannot generate bytecode for constant: " + value + " of type: " + type.getName());
        }
        this.push(type);
        if (boxing) {
            this.box();
        }
    }

    private static void newInstance(MethodVisitor mv, Object value) {
        String className = BytecodeHelper.getClassInternalName(value.getClass().getName());
        mv.visitTypeInsn(187, className);
        mv.visitInsn(89);
        mv.visitLdcInsn(value.toString());
        mv.visitMethodInsn(183, className, "<init>", "(Ljava/lang/String;)V", false);
    }

    private static void pushPrimitiveConstant(MethodVisitor mv, Object value, ClassNode type) {
        boolean isInt = ClassHelper.isPrimitiveInt(type);
        boolean isShort = ClassHelper.isPrimitiveShort(type);
        boolean isByte = ClassHelper.isPrimitiveByte(type);
        boolean isChar = ClassHelper.isPrimitiveChar(type);
        if (isInt || isShort || isByte || isChar) {
            int val = isInt ? (Integer)value : (isShort ? (int)((Short)value).shortValue() : (isChar ? (int)((Character)value).charValue() : (int)((Byte)value).byteValue()));
            BytecodeHelper.pushConstant(mv, val);
        } else if (ClassHelper.isPrimitiveLong(type)) {
            if ((Long)value == 0L) {
                mv.visitInsn(9);
            } else if ((Long)value == 1L) {
                mv.visitInsn(10);
            } else {
                mv.visitLdcInsn(value);
            }
        } else if (ClassHelper.isPrimitiveFloat(type)) {
            if (value.equals(Float.valueOf(0.0f))) {
                mv.visitInsn(11);
            } else if (((Float)value).floatValue() == 1.0f) {
                mv.visitInsn(12);
            } else if (((Float)value).floatValue() == 2.0f) {
                mv.visitInsn(13);
            } else {
                mv.visitLdcInsn(value);
            }
        } else if (ClassHelper.isPrimitiveDouble(type)) {
            if (value.equals(0.0)) {
                mv.visitInsn(14);
            } else if ((Double)value == 1.0) {
                mv.visitInsn(15);
            } else {
                mv.visitLdcInsn(value);
            }
        } else if (ClassHelper.isPrimitiveBoolean(type)) {
            boolean b = (Boolean)value;
            if (b) {
                mv.visitInsn(4);
            } else {
                mv.visitInsn(3);
            }
        } else {
            mv.visitLdcInsn(value);
        }
    }

    public void pushDynamicName(Expression name) {
        ConstantExpression ce;
        Object value;
        if (name instanceof ConstantExpression && (value = (ce = (ConstantExpression)name).getValue()) instanceof String) {
            this.pushConstant(ce);
            return;
        }
        new CastExpression(ClassHelper.STRING_TYPE, name).visit(this.controller.getAcg());
    }

    public void loadOrStoreVariable(BytecodeVariable variable, boolean useReferenceDirectly) {
        CompileStack compileStack = this.controller.getCompileStack();
        if (compileStack.isLHS()) {
            this.storeVar(variable);
        } else {
            MethodVisitor mv = this.controller.getMethodVisitor();
            int idx = variable.getIndex();
            ClassNode type = variable.getType();
            if (variable.isHolder()) {
                mv.visitVarInsn(25, idx);
                if (!useReferenceDirectly) {
                    mv.visitMethodInsn(182, "groovy/lang/Reference", "get", "()Ljava/lang/Object;", false);
                    BytecodeHelper.doCast(mv, type);
                    this.push(type);
                } else {
                    this.push(ClassHelper.REFERENCE_TYPE);
                }
            } else {
                this.load(type, idx);
            }
        }
    }

    public void storeVar(BytecodeVariable variable) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        int idx = variable.getIndex();
        ClassNode type = variable.getType();
        if (variable.isHolder()) {
            this.doGroovyCast(type);
            this.box();
            mv.visitVarInsn(25, idx);
            mv.visitTypeInsn(192, "groovy/lang/Reference");
            mv.visitInsn(95);
            mv.visitMethodInsn(182, "groovy/lang/Reference", "set", "(Ljava/lang/Object;)V", false);
        } else {
            this.doGroovyCast(type);
            BytecodeHelper.store(mv, type, idx);
        }
        this.remove(1);
    }

    public void load(ClassNode type, int idx) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        BytecodeHelper.load(mv, type, idx);
        this.push(type);
    }

    public void pushBool(boolean inclusive) {
        MethodVisitor mv = this.controller.getMethodVisitor();
        mv.visitLdcInsn(inclusive);
        this.push(ClassHelper.boolean_TYPE);
    }

    public String toString() {
        return "OperandStack(size=" + this.stack.size() + ":" + this.stack.toString() + ")";
    }

    public ClassNode getTopOperand() {
        int size = this.ensureStackNotEmpty(this.stack);
        return this.stack.get(size - 1);
    }
}

