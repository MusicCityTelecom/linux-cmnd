/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm;

import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.Type;
import java.lang.reflect.Modifier;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.classgen.asm.util.TypeUtil;
import org.codehaus.groovy.reflection.ReflectionCache;
import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation;

public class BytecodeHelper {
    private static String DTT_CLASSNAME = BytecodeHelper.getClassInternalName(DefaultTypeTransformation.class);

    public static String getClassInternalName(ClassNode t) {
        if (t.isArray()) {
            return TypeUtil.getDescriptionByType(t);
        }
        return BytecodeHelper.getClassInternalName(t.getName());
    }

    public static String getClassInternalName(Class t) {
        return Type.getInternalName(t);
    }

    public static String getClassInternalName(String name) {
        return name.replace('.', '/');
    }

    public static String getMethodDescriptor(MethodNode methodNode) {
        return BytecodeHelper.getMethodDescriptor(methodNode.getReturnType(), methodNode.getParameters());
    }

    public static String getMethodDescriptor(ClassNode returnType, Parameter[] parameters) {
        ClassNode[] parameterTypes = new ClassNode[parameters.length];
        int n = parameters.length;
        for (int i = 0; i < n; ++i) {
            parameterTypes[i] = parameters[i].getType();
        }
        return BytecodeHelper.getMethodDescriptor(returnType, parameterTypes);
    }

    public static String getMethodDescriptor(ClassNode returnType, ClassNode[] parameterTypes) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append('(');
        for (ClassNode parameterType : parameterTypes) {
            buffer.append(BytecodeHelper.getTypeDescription(parameterType));
        }
        buffer.append(')');
        buffer.append(BytecodeHelper.getTypeDescription(returnType));
        return buffer.toString();
    }

    public static String getMethodDescriptor(Class returnType, Class[] paramTypes) {
        StringBuilder buffer = new StringBuilder(100);
        buffer.append('(');
        for (Class paramType : paramTypes) {
            buffer.append(BytecodeHelper.getTypeDescription(paramType));
        }
        buffer.append(')');
        buffer.append(BytecodeHelper.getTypeDescription(returnType));
        return buffer.toString();
    }

    public static String getClassLoadingTypeDescription(ClassNode c) {
        String desc = TypeUtil.getDescriptionByType(c);
        if (!c.isArray() && desc.startsWith("L") && desc.endsWith(";")) {
            desc = desc.substring(1, desc.length() - 1);
        }
        return desc.replace('/', '.');
    }

    public static String getTypeDescription(Class c) {
        return Type.getDescriptor(c);
    }

    public static String getTypeDescription(ClassNode c) {
        return BytecodeHelper.getTypeDescription(c, true);
    }

    private static String getTypeDescription(ClassNode c, boolean end) {
        ClassNode d = c;
        if (ClassHelper.isPrimitiveType(d.redirect())) {
            d = d.redirect();
        }
        String desc = TypeUtil.getDescriptionByType(d);
        if (!end && desc.endsWith(";")) {
            desc = desc.substring(0, desc.length() - 1);
        }
        return desc;
    }

    public static String[] getClassInternalNames(ClassNode[] names) {
        int size = names.length;
        String[] answer = new String[size];
        for (int i = 0; i < size; ++i) {
            answer[i] = BytecodeHelper.getClassInternalName(names[i]);
        }
        return answer;
    }

    public static void pushConstant(MethodVisitor mv, int value) {
        switch (value) {
            case 0: {
                mv.visitInsn(3);
                break;
            }
            case 1: {
                mv.visitInsn(4);
                break;
            }
            case 2: {
                mv.visitInsn(5);
                break;
            }
            case 3: {
                mv.visitInsn(6);
                break;
            }
            case 4: {
                mv.visitInsn(7);
                break;
            }
            case 5: {
                mv.visitInsn(8);
                break;
            }
            default: {
                if (value >= -128 && value <= 127) {
                    mv.visitIntInsn(16, value);
                    break;
                }
                if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                    mv.visitIntInsn(17, value);
                    break;
                }
                mv.visitLdcInsn(value);
            }
        }
    }

    public static void negateBoolean(MethodVisitor mv) {
        Label endLabel = new Label();
        Label falseLabel = new Label();
        mv.visitJumpInsn(154, falseLabel);
        mv.visitInsn(4);
        mv.visitJumpInsn(167, endLabel);
        mv.visitLabel(falseLabel);
        mv.visitInsn(3);
        mv.visitLabel(endLabel);
    }

    public static String formatNameForClassLoading(String name) {
        if (name == null) {
            return "java.lang.Object;";
        }
        if (TypeUtil.isPrimitiveType(name)) {
            return name;
        }
        if (name.startsWith("[")) {
            return name.replace('/', '.');
        }
        if (name.startsWith("L")) {
            if ((name = name.substring(1)).endsWith(";")) {
                name = name.substring(0, name.length() - 1);
            }
            return name.replace('/', '.');
        }
        String prefix = "";
        if (name.endsWith("[]")) {
            prefix = "[";
            name = name.substring(0, name.length() - 2);
            return prefix + TypeUtil.getDescriptionByName(name);
        }
        return name.replace('/', '.');
    }

    private static boolean hasGenerics(Parameter[] param) {
        if (param.length == 0) {
            return false;
        }
        for (Parameter parameter : param) {
            ClassNode type = parameter.getType();
            if (!BytecodeHelper.hasGenerics(type)) continue;
            return true;
        }
        return false;
    }

    private static boolean hasGenerics(ClassNode type) {
        return type.isArray() ? BytecodeHelper.hasGenerics(type.getComponentType()) : type.getGenericsTypes() != null;
    }

    public static String getGenericsMethodSignature(MethodNode node) {
        GenericsType[] generics = node.getGenericsTypes();
        Parameter[] param = node.getParameters();
        ClassNode returnType = node.getReturnType();
        if (generics == null && !BytecodeHelper.hasGenerics(param) && !BytecodeHelper.hasGenerics(returnType)) {
            return null;
        }
        StringBuilder ret = new StringBuilder(100);
        BytecodeHelper.getGenericsTypeSpec(ret, generics);
        GenericsType[] paramTypes = new GenericsType[param.length];
        for (int i = 0; i < param.length; ++i) {
            ClassNode pType = param[i].getType();
            paramTypes[i] = pType.getGenericsTypes() == null || !pType.isGenericsPlaceHolder() ? new GenericsType(pType) : pType.getGenericsTypes()[0];
        }
        BytecodeHelper.addSubTypes(ret, paramTypes, "(", ")");
        BytecodeHelper.addSubTypes(ret, new GenericsType[]{new GenericsType(returnType)}, "", "");
        return ret.toString();
    }

    private static boolean usesGenericsInClassSignature(ClassNode node) {
        if (!node.isUsingGenerics()) {
            return false;
        }
        if (BytecodeHelper.hasGenerics(node)) {
            return true;
        }
        ClassNode sclass = node.getUnresolvedSuperClass(false);
        if (sclass.isUsingGenerics()) {
            return true;
        }
        ClassNode[] interfaces = node.getInterfaces();
        if (interfaces != null) {
            for (ClassNode anInterface : interfaces) {
                if (!anInterface.isUsingGenerics()) continue;
                return true;
            }
        }
        return false;
    }

    public static String getTypeGenericsSignature(ClassNode node) {
        if (!BytecodeHelper.usesGenericsInTypeSignature(node)) {
            return null;
        }
        StringBuilder ret = new StringBuilder(100);
        ret.append(BytecodeHelper.getTypeDescription(node.getPlainNodeReference(), false));
        BytecodeHelper.addSubTypes(ret, node.getGenericsTypes(), "<", ">");
        ret.append(";");
        return ret.toString();
    }

    private static boolean usesGenericsInTypeSignature(ClassNode node) {
        if (!node.isUsingGenerics()) {
            return false;
        }
        return BytecodeHelper.hasGenerics(node);
    }

    public static String getGenericsSignature(ClassNode node) {
        ClassNode[] interfaces;
        if (!BytecodeHelper.usesGenericsInClassSignature(node)) {
            return null;
        }
        GenericsType[] genericsTypes = node.getGenericsTypes();
        StringBuilder ret = new StringBuilder(100);
        BytecodeHelper.getGenericsTypeSpec(ret, genericsTypes);
        GenericsType extendsPart = new GenericsType(node.getUnresolvedSuperClass(false));
        BytecodeHelper.writeGenericsBounds(ret, extendsPart, true);
        for (ClassNode anInterface : interfaces = node.getInterfaces()) {
            GenericsType interfacePart = new GenericsType(anInterface);
            BytecodeHelper.writeGenericsBounds(ret, interfacePart, false);
        }
        return ret.toString();
    }

    private static void getGenericsTypeSpec(StringBuilder ret, GenericsType[] genericsTypes) {
        if (genericsTypes == null) {
            return;
        }
        ret.append('<');
        for (GenericsType genericsType : genericsTypes) {
            String name = genericsType.getName();
            ret.append(name);
            ret.append(':');
            BytecodeHelper.writeGenericsBounds(ret, genericsType, true);
        }
        ret.append('>');
    }

    public static String getGenericsBounds(ClassNode type) {
        GenericsType[] genericsTypes = type.getGenericsTypes();
        if (genericsTypes == null) {
            return null;
        }
        StringBuilder ret = new StringBuilder(100);
        if (type.isGenericsPlaceHolder()) {
            BytecodeHelper.addSubTypes(ret, type.getGenericsTypes(), "", "");
        } else {
            GenericsType gt = new GenericsType(type);
            BytecodeHelper.writeGenericsBounds(ret, gt, false);
        }
        return ret.toString();
    }

    private static void writeGenericsBoundType(StringBuilder ret, ClassNode printType, boolean writeInterfaceMarker) {
        if (writeInterfaceMarker && printType.isInterface()) {
            ret.append(":");
        }
        if (printType.isGenericsPlaceHolder() && printType.getGenericsTypes() != null) {
            ret.append("T");
            ret.append(printType.getGenericsTypes()[0].getName());
            ret.append(";");
        } else {
            ret.append(BytecodeHelper.getTypeDescription(printType, false));
            BytecodeHelper.addSubTypes(ret, printType.getGenericsTypes(), "<", ">");
            if (!ClassHelper.isPrimitiveType(printType)) {
                ret.append(";");
            }
        }
    }

    private static void writeGenericsBounds(StringBuilder ret, GenericsType type, boolean writeInterfaceMarker) {
        if (type.getUpperBounds() != null) {
            ClassNode[] bounds;
            for (ClassNode bound : bounds = type.getUpperBounds()) {
                BytecodeHelper.writeGenericsBoundType(ret, bound, writeInterfaceMarker);
            }
        } else if (type.getLowerBound() != null) {
            BytecodeHelper.writeGenericsBoundType(ret, type.getLowerBound(), writeInterfaceMarker);
        } else {
            BytecodeHelper.writeGenericsBoundType(ret, type.getType(), writeInterfaceMarker);
        }
    }

    private static void addSubTypes(StringBuilder ret, GenericsType[] types, String start, String end) {
        if (types == null) {
            return;
        }
        ret.append(start);
        for (GenericsType type : types) {
            if (type.getType().isArray()) {
                ret.append("[");
                BytecodeHelper.addSubTypes(ret, new GenericsType[]{new GenericsType(type.getType().getComponentType())}, "", "");
                continue;
            }
            if (type.isPlaceholder()) {
                ret.append('T');
                String name = type.getName();
                ret.append(name);
                ret.append(';');
                continue;
            }
            if (type.isWildcard()) {
                if (type.getUpperBounds() != null) {
                    ret.append('+');
                    BytecodeHelper.writeGenericsBounds(ret, type, false);
                    continue;
                }
                if (type.getLowerBound() != null) {
                    ret.append('-');
                    BytecodeHelper.writeGenericsBounds(ret, type, false);
                    continue;
                }
                ret.append('*');
                continue;
            }
            BytecodeHelper.writeGenericsBounds(ret, type, false);
        }
        ret.append(end);
    }

    public static void doCast(MethodVisitor mv, ClassNode type) {
        if (ClassHelper.isObjectType(type)) {
            return;
        }
        if (ClassHelper.isPrimitiveType(type) && !ClassHelper.isPrimitiveVoid(type)) {
            BytecodeHelper.unbox(mv, type);
        } else {
            mv.visitTypeInsn(192, type.isArray() ? BytecodeHelper.getTypeDescription(type) : BytecodeHelper.getClassInternalName(type.getName()));
        }
    }

    public static void doCastToPrimitive(MethodVisitor mv, ClassNode sourceType, ClassNode targetType) {
        mv.visitMethodInsn(182, BytecodeHelper.getClassInternalName(sourceType), targetType.getName() + "Value", "()" + BytecodeHelper.getTypeDescription(targetType), false);
    }

    public static void doCastToWrappedType(MethodVisitor mv, ClassNode sourceType, ClassNode targetType) {
        mv.visitMethodInsn(184, BytecodeHelper.getClassInternalName(targetType), "valueOf", "(" + BytecodeHelper.getTypeDescription(sourceType) + ")" + BytecodeHelper.getTypeDescription(targetType), false);
    }

    public static void doCast(MethodVisitor mv, Class type) {
        if (type == Object.class) {
            return;
        }
        if (type.isPrimitive() && type != Void.TYPE) {
            BytecodeHelper.unbox(mv, type);
        } else {
            mv.visitTypeInsn(192, type.isArray() ? BytecodeHelper.getTypeDescription(type) : BytecodeHelper.getClassInternalName(type.getName()));
        }
    }

    @Deprecated
    public static boolean box(MethodVisitor mv, ClassNode type) {
        if (ClassHelper.isPrimitiveType(type) && !ClassHelper.isPrimitiveVoid(type)) {
            BytecodeHelper.box(mv, BytecodeHelper.getTypeDescription(type));
            return true;
        }
        return false;
    }

    @Deprecated
    public static boolean box(MethodVisitor mv, Class type) {
        if (ReflectionCache.getCachedClass((Class)type).isPrimitive && type != Void.TYPE) {
            BytecodeHelper.box(mv, BytecodeHelper.getTypeDescription(type));
            return true;
        }
        return false;
    }

    private static void box(MethodVisitor mv, String typeDescription) {
        mv.visitMethodInsn(184, DTT_CLASSNAME, "box", "(" + typeDescription + ")Ljava/lang/Object;", false);
    }

    public static void unbox(MethodVisitor mv, ClassNode type) {
        if (ClassHelper.isPrimitiveType(type) && !ClassHelper.isPrimitiveVoid(type)) {
            BytecodeHelper.unbox(mv, type.getName(), BytecodeHelper.getTypeDescription(type));
        }
    }

    public static void unbox(MethodVisitor mv, Class type) {
        if (type.isPrimitive() && type != Void.TYPE) {
            BytecodeHelper.unbox(mv, type.getName(), BytecodeHelper.getTypeDescription(type));
        }
    }

    private static void unbox(MethodVisitor mv, String typeName, String typeDescription) {
        mv.visitMethodInsn(184, DTT_CLASSNAME, typeName + "Unbox", "(Ljava/lang/Object;)" + typeDescription, false);
    }

    public static void visitClassLiteral(MethodVisitor mv, ClassNode classNode) {
        if (ClassHelper.isPrimitiveType(classNode)) {
            mv.visitFieldInsn(178, BytecodeHelper.getClassInternalName(ClassHelper.getWrapper(classNode)), "TYPE", "Ljava/lang/Class;");
        } else {
            mv.visitLdcInsn(Type.getType(BytecodeHelper.getTypeDescription(classNode)));
        }
    }

    public static boolean isClassLiteralPossible(ClassNode classNode) {
        return Modifier.isPublic(classNode.getModifiers());
    }

    public static boolean isSameCompilationUnit(ClassNode a, ClassNode b) {
        CompileUnit cu1 = a.getCompileUnit();
        CompileUnit cu2 = b.getCompileUnit();
        return cu1 != null && cu1 == cu2;
    }

    public static int hashCode(String str) {
        char[] chars = str.toCharArray();
        int h = 0;
        for (char aChar : chars) {
            h = 31 * h + aChar;
        }
        return h;
    }

    public static void convertPrimitiveToBoolean(MethodVisitor mv, ClassNode type) {
        if (ClassHelper.isPrimitiveBoolean(type)) {
            return;
        }
        if (ClassHelper.isPrimitiveDouble(type)) {
            BytecodeHelper.convertDoubleToBoolean(mv);
            return;
        }
        if (ClassHelper.isPrimitiveFloat(type)) {
            BytecodeHelper.convertFloatToBoolean(mv);
            return;
        }
        Label trueLabel = new Label();
        Label falseLabel = new Label();
        if (ClassHelper.isPrimitiveLong(type)) {
            mv.visitInsn(9);
            mv.visitInsn(148);
        }
        mv.visitJumpInsn(153, falseLabel);
        mv.visitInsn(4);
        mv.visitJumpInsn(167, trueLabel);
        mv.visitLabel(falseLabel);
        mv.visitInsn(3);
        mv.visitLabel(trueLabel);
    }

    private static void convertDoubleToBoolean(MethodVisitor mv) {
        Label trueLabel = new Label();
        Label falseLabel = new Label();
        Label falseLabelWithPop = new Label();
        mv.visitInsn(92);
        mv.visitInsn(14);
        mv.visitInsn(151);
        mv.visitJumpInsn(153, falseLabelWithPop);
        mv.visitMethodInsn(184, "java/lang/Double", "isNaN", "(D)Z", false);
        mv.visitJumpInsn(154, falseLabel);
        mv.visitInsn(4);
        mv.visitJumpInsn(167, trueLabel);
        mv.visitLabel(falseLabelWithPop);
        mv.visitInsn(88);
        mv.visitLabel(falseLabel);
        mv.visitInsn(3);
        mv.visitLabel(trueLabel);
    }

    private static void convertFloatToBoolean(MethodVisitor mv) {
        Label trueLabel = new Label();
        Label falseLabel = new Label();
        Label falseLabelWithPop = new Label();
        mv.visitInsn(89);
        mv.visitInsn(11);
        mv.visitInsn(149);
        mv.visitJumpInsn(153, falseLabelWithPop);
        mv.visitMethodInsn(184, "java/lang/Float", "isNaN", "(F)Z", false);
        mv.visitJumpInsn(154, falseLabel);
        mv.visitInsn(4);
        mv.visitJumpInsn(167, trueLabel);
        mv.visitLabel(falseLabelWithPop);
        mv.visitInsn(87);
        mv.visitLabel(falseLabel);
        mv.visitInsn(3);
        mv.visitLabel(trueLabel);
    }

    public static void doReturn(MethodVisitor mv, ClassNode type) {
        new ReturnVarHandler(mv, type).handle();
    }

    public static void load(MethodVisitor mv, ClassNode type, int idx) {
        new LoadVarHandler(mv, type, idx).handle();
    }

    public static void store(MethodVisitor mv, ClassNode type, int idx) {
        new StoreVarHandler(mv, type, idx).handle();
    }

    private static class ReturnVarHandler
    extends PrimitiveTypeHandler {
        private MethodVisitor mv;

        public ReturnVarHandler(MethodVisitor mv, ClassNode type) {
            super(type);
            this.mv = mv;
        }

        @Override
        protected void handleDoubleType() {
            this.mv.visitInsn(175);
        }

        @Override
        protected void handleFloatType() {
            this.mv.visitInsn(174);
        }

        @Override
        protected void handleLongType() {
            this.mv.visitInsn(173);
        }

        @Override
        protected void handleIntType() {
            this.mv.visitInsn(172);
        }

        @Override
        protected void handleVoidType() {
            this.mv.visitInsn(177);
        }

        @Override
        protected void handleRefType() {
            this.mv.visitInsn(176);
        }
    }

    private static class LoadVarHandler
    extends PrimitiveTypeHandler {
        private MethodVisitor mv;
        private int idx;

        public LoadVarHandler(MethodVisitor mv, ClassNode type, int idx) {
            super(type);
            this.mv = mv;
            this.idx = idx;
        }

        @Override
        protected void handleDoubleType() {
            this.mv.visitVarInsn(24, this.idx);
        }

        @Override
        protected void handleFloatType() {
            this.mv.visitVarInsn(23, this.idx);
        }

        @Override
        protected void handleLongType() {
            this.mv.visitVarInsn(22, this.idx);
        }

        @Override
        protected void handleIntType() {
            this.mv.visitVarInsn(21, this.idx);
        }

        @Override
        protected void handleVoidType() {
        }

        @Override
        protected void handleRefType() {
            this.mv.visitVarInsn(25, this.idx);
        }
    }

    private static class StoreVarHandler
    extends PrimitiveTypeHandler {
        private MethodVisitor mv;
        private int idx;

        public StoreVarHandler(MethodVisitor mv, ClassNode type, int idx) {
            super(type);
            this.mv = mv;
            this.idx = idx;
        }

        @Override
        protected void handleDoubleType() {
            this.mv.visitVarInsn(57, this.idx);
        }

        @Override
        protected void handleFloatType() {
            this.mv.visitVarInsn(56, this.idx);
        }

        @Override
        protected void handleLongType() {
            this.mv.visitVarInsn(55, this.idx);
        }

        @Override
        protected void handleIntType() {
            this.mv.visitVarInsn(54, this.idx);
        }

        @Override
        protected void handleVoidType() {
        }

        @Override
        protected void handleRefType() {
            this.mv.visitVarInsn(58, this.idx);
        }
    }

    private static abstract class PrimitiveTypeHandler {
        private ClassNode type;

        public PrimitiveTypeHandler(ClassNode type) {
            this.type = type;
        }

        public void handle() {
            if (ClassHelper.isPrimitiveDouble(this.type)) {
                this.handleDoubleType();
            } else if (ClassHelper.isPrimitiveFloat(this.type)) {
                this.handleFloatType();
            } else if (ClassHelper.isPrimitiveLong(this.type)) {
                this.handleLongType();
            } else if (ClassHelper.isPrimitiveBoolean(this.type) || ClassHelper.isPrimitiveChar(this.type) || ClassHelper.isPrimitiveByte(this.type) || ClassHelper.isPrimitiveInt(this.type) || ClassHelper.isPrimitiveShort(this.type)) {
                this.handleIntType();
            } else if (ClassHelper.isPrimitiveVoid(this.type)) {
                this.handleVoidType();
            } else {
                this.handleRefType();
            }
        }

        protected abstract void handleDoubleType();

        protected abstract void handleFloatType();

        protected abstract void handleLongType();

        protected abstract void handleIntType();

        protected abstract void handleVoidType();

        protected abstract void handleRefType();
    }
}

