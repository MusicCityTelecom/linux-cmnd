/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.classgen.asm.util;

import groovyjarjarasm.asm.Attribute;
import groovyjarjarasm.asm.Handle;
import groovyjarjarasm.asm.Label;
import groovyjarjarasm.asm.TypePath;
import groovyjarjarasm.asm.util.Printer;
import groovyjarjarasm.asm.util.Textifier;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.codehaus.groovy.control.CompilerConfiguration;

public class LoggableTextifier
extends Textifier {
    private final CompilerConfiguration compilerConfiguration;
    private final PrintWriter out;
    private int lineCount;

    public LoggableTextifier() {
        this(CompilerConfiguration.DEFAULT);
    }

    public LoggableTextifier(CompilerConfiguration compilerConfiguration) {
        super(589824);
        this.compilerConfiguration = compilerConfiguration;
        this.out = Optional.ofNullable(compilerConfiguration.getOutput()).orElseGet(() -> new PrintWriter(System.out, true));
    }

    @Override
    protected Textifier createTextifier() {
        return new LoggableTextifier(this.compilerConfiguration);
    }

    protected void log() {
        int textSize = this.text.size();
        ArrayList bcList = new ArrayList();
        for (int i = this.lineCount; i < textSize; ++i) {
            Object bc = this.text.get(i);
            if (bc instanceof List && ((List)bc).isEmpty()) continue;
            bcList.add(bc);
        }
        if (!bcList.isEmpty()) {
            this.out.print(this.getInvocationPositionInfo());
            for (Object bc : bcList) {
                this.out.print(bc);
            }
        }
        this.lineCount = textSize;
    }

    private String getInvocationPositionInfo() {
        int maxDepth = this.compilerConfiguration.getLogClassgenStackTraceMaxDepth();
        return maxDepth <= 0 ? "" : Arrays.stream(new Throwable().getStackTrace()).filter(stackTraceElement -> stackTraceElement.getClassName().contains(".groovy.") && !stackTraceElement.getClassName().endsWith(".LoggableTextifier")).map(stackTraceElement -> String.format("%30s// %s#%s:%s%n", "", stackTraceElement.getClassName(), stackTraceElement.getMethodName(), stackTraceElement.getLineNumber())).limit(maxDepth).collect(Collectors.joining());
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.log();
    }

    @Override
    public void visitSource(String file, String debug) {
        super.visitSource(file, debug);
        this.log();
    }

    @Override
    public Printer visitModule(String name, int access, String version) {
        Printer p = super.visitModule(name, access, version);
        this.log();
        return p;
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        super.visitOuterClass(owner, name, desc);
        this.log();
    }

    @Override
    public Textifier visitClassAnnotation(String desc, boolean visible) {
        Textifier t = super.visitClassAnnotation(desc, visible);
        this.log();
        return t;
    }

    @Override
    public Printer visitClassTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer t = super.visitClassTypeAnnotation(typeRef, typePath, desc, visible);
        this.log();
        return t;
    }

    @Override
    public void visitClassAttribute(Attribute attr) {
        super.visitClassAttribute(attr);
        this.log();
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
        this.log();
    }

    @Override
    public Textifier visitField(int access, String name, String desc, String signature, Object value) {
        Textifier t = super.visitField(access, name, desc, signature, value);
        this.log();
        return t;
    }

    @Override
    public Textifier visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Textifier t = super.visitMethod(access, name, desc, signature, exceptions);
        this.log();
        return t;
    }

    @Override
    public void visitClassEnd() {
        super.visitClassEnd();
        this.log();
    }

    @Override
    public void visitRequire(String require, int access, String version) {
        super.visitRequire(require, access, version);
        this.log();
    }

    @Override
    public void visitExport(String export, int access, String ... modules) {
        super.visitExport(export, access, modules);
        this.log();
    }

    @Override
    public void visitUse(String use) {
        super.visitUse(use);
        this.log();
    }

    @Override
    public void visitProvide(String provide, String ... providers) {
        super.visitProvide(provide, providers);
        this.log();
    }

    @Override
    public void visitModuleEnd() {
        super.visitModuleEnd();
        this.log();
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
        this.log();
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        super.visitEnum(name, desc, value);
        this.log();
    }

    @Override
    public Textifier visitAnnotation(String name, String desc) {
        Textifier t = super.visitAnnotation(name, desc);
        this.log();
        return t;
    }

    @Override
    public Textifier visitArray(String name) {
        Textifier t = super.visitArray(name);
        this.log();
        return t;
    }

    @Override
    public void visitAnnotationEnd() {
        super.visitAnnotationEnd();
        this.log();
    }

    @Override
    public Textifier visitFieldAnnotation(String desc, boolean visible) {
        Textifier t = super.visitFieldAnnotation(desc, visible);
        this.log();
        return t;
    }

    @Override
    public Printer visitFieldTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer t = super.visitFieldTypeAnnotation(typeRef, typePath, desc, visible);
        this.log();
        return t;
    }

    @Override
    public void visitFieldAttribute(Attribute attr) {
        super.visitFieldAttribute(attr);
        this.log();
    }

    @Override
    public void visitFieldEnd() {
        super.visitFieldEnd();
        this.log();
    }

    @Override
    public void visitParameter(String name, int access) {
        super.visitParameter(name, access);
        this.log();
    }

    @Override
    public Textifier visitAnnotationDefault() {
        Textifier t = super.visitAnnotationDefault();
        this.log();
        return t;
    }

    @Override
    public Textifier visitMethodAnnotation(String desc, boolean visible) {
        Textifier t = super.visitMethodAnnotation(desc, visible);
        this.log();
        return t;
    }

    @Override
    public Printer visitMethodTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer t = super.visitMethodTypeAnnotation(typeRef, typePath, desc, visible);
        this.log();
        return t;
    }

    @Override
    public Textifier visitParameterAnnotation(int parameter, String desc, boolean visible) {
        Textifier t = super.visitParameterAnnotation(parameter, desc, visible);
        this.log();
        return t;
    }

    @Override
    public void visitMethodAttribute(Attribute attr) {
        super.visitMethodAttribute(attr);
        this.log();
    }

    @Override
    public void visitCode() {
        super.visitCode();
        this.log();
    }

    @Override
    public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
        super.visitFrame(type, nLocal, local, nStack, stack);
        this.log();
    }

    @Override
    public void visitInsn(int opcode) {
        super.visitInsn(opcode);
        this.log();
    }

    @Override
    public void visitIntInsn(int opcode, int operand) {
        super.visitIntInsn(opcode, operand);
        this.log();
    }

    @Override
    public void visitVarInsn(int opcode, int varIndex) {
        super.visitVarInsn(opcode, varIndex);
        this.log();
    }

    @Override
    public void visitTypeInsn(int opcode, String type) {
        super.visitTypeInsn(opcode, type);
        this.log();
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        super.visitFieldInsn(opcode, owner, name, desc);
        this.log();
    }

    @Override
    @Deprecated
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        super.visitMethodInsn(opcode, owner, name, desc);
        this.log();
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
        super.visitMethodInsn(opcode, owner, name, desc, itf);
        this.log();
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object ... bsmArgs) {
        super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
        this.log();
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        super.visitJumpInsn(opcode, label);
        this.log();
    }

    @Override
    public void visitLabel(Label label) {
        super.visitLabel(label);
        this.log();
    }

    @Override
    public void visitLdcInsn(Object cst) {
        super.visitLdcInsn(cst);
        this.log();
    }

    @Override
    public void visitIincInsn(int varIndex, int increment) {
        super.visitIincInsn(varIndex, increment);
        this.log();
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label ... labels) {
        super.visitTableSwitchInsn(min, max, dflt, labels);
        this.log();
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        super.visitLookupSwitchInsn(dflt, keys, labels);
        this.log();
    }

    @Override
    public void visitMultiANewArrayInsn(String desc, int dims) {
        super.visitMultiANewArrayInsn(desc, dims);
        this.log();
    }

    @Override
    public Printer visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer t = super.visitInsnAnnotation(typeRef, typePath, desc, visible);
        this.log();
        return t;
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        super.visitTryCatchBlock(start, end, handler, type);
        this.log();
    }

    @Override
    public Printer visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Printer t = super.visitTryCatchAnnotation(typeRef, typePath, desc, visible);
        this.log();
        return t;
    }

    @Override
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
        super.visitLocalVariable(name, desc, signature, start, end, index);
        this.log();
    }

    @Override
    public Printer visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
        Printer t = super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
        this.log();
        return t;
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
        this.log();
    }

    @Override
    public void visitMaxs(int maxStack, int maxLocals) {
        super.visitMaxs(maxStack, maxLocals);
        this.log();
    }

    @Override
    public void visitMethodEnd() {
        super.visitMethodEnd();
        this.log();
    }

    @Override
    public Textifier visitAnnotation(String desc, boolean visible) {
        Textifier t = super.visitAnnotation(desc, visible);
        this.log();
        return t;
    }

    @Override
    public Textifier visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        Textifier t = super.visitTypeAnnotation(typeRef, typePath, desc, visible);
        this.log();
        return t;
    }

    @Override
    public void visitAttribute(Attribute attr) {
        super.visitAttribute(attr);
        this.log();
    }

    @Override
    public void visitNestHost(String nestHost) {
        super.visitNestHost(nestHost);
        this.log();
    }

    @Override
    public void visitNestMember(String nestMember) {
        super.visitNestMember(nestMember);
        this.log();
    }

    @Override
    public void visitMainClass(String mainClass) {
        super.visitMainClass(mainClass);
        this.log();
    }

    @Override
    public void visitPackage(String packaze) {
        super.visitPackage(packaze);
        this.log();
    }

    @Override
    public void visitOpen(String packaze, int access, String ... modules) {
        super.visitOpen(packaze, access, modules);
        this.log();
    }

    @Override
    public Textifier visitAnnotableParameterCount(int parameterCount, boolean visible) {
        Textifier t = super.visitAnnotableParameterCount(parameterCount, visible);
        this.log();
        return t;
    }

    @Override
    public Printer visitRecordComponent(String name, String descriptor, String signature) {
        Printer p = super.visitRecordComponent(name, descriptor, signature);
        this.log();
        return p;
    }

    @Override
    public Textifier visitRecordComponentAnnotation(String descriptor, boolean visible) {
        Textifier t = super.visitRecordComponentAnnotation(descriptor, visible);
        this.log();
        return t;
    }

    @Override
    public Printer visitRecordComponentTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        Printer p = super.visitRecordComponentTypeAnnotation(typeRef, typePath, descriptor, visible);
        this.log();
        return p;
    }

    @Override
    public void visitRecordComponentAttribute(Attribute attribute) {
        super.visitRecordComponentAttribute(attribute);
        this.log();
    }

    @Override
    public void visitRecordComponentEnd() {
        super.visitRecordComponentEnd();
        this.log();
    }

    @Override
    public void visitPermittedSubclass(String permittedSubclass) {
        super.visitPermittedSubclass(permittedSubclass);
        this.log();
    }
}

