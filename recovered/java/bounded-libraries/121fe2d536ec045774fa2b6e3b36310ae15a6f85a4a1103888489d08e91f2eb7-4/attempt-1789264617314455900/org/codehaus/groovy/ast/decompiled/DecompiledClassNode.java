/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.function.Supplier;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.MixinNode;
import org.codehaus.groovy.ast.RecordComponentNode;
import org.codehaus.groovy.ast.decompiled.AnnotationStub;
import org.codehaus.groovy.ast.decompiled.Annotations;
import org.codehaus.groovy.ast.decompiled.AsmReferenceResolver;
import org.codehaus.groovy.ast.decompiled.ClassSignatureParser;
import org.codehaus.groovy.ast.decompiled.ClassStub;
import org.codehaus.groovy.ast.decompiled.FieldStub;
import org.codehaus.groovy.ast.decompiled.LazyConstructorNode;
import org.codehaus.groovy.ast.decompiled.LazyFieldNode;
import org.codehaus.groovy.ast.decompiled.LazyMethodNode;
import org.codehaus.groovy.ast.decompiled.MemberSignatureParser;
import org.codehaus.groovy.ast.decompiled.MethodStub;
import org.codehaus.groovy.classgen.Verifier;
import org.codehaus.groovy.reflection.ReflectionUtils;

public class DecompiledClassNode
extends ClassNode {
    private final ClassStub classData;
    private final AsmReferenceResolver resolver;
    private volatile boolean supersInitialized;
    private volatile boolean membersInitialized;

    public DecompiledClassNode(ClassStub classData, AsmReferenceResolver resolver) {
        super(classData.className, DecompiledClassNode.getModifiers(classData), null, null, MixinNode.EMPTY_ARRAY);
        this.classData = classData;
        this.resolver = resolver;
        this.isPrimaryNode = false;
    }

    private static int getModifiers(ClassStub classData) {
        return classData.innerClassModifiers != -1 ? classData.innerClassModifiers : classData.accessModifiers;
    }

    public long getCompilationTimeStamp() {
        if (this.classData.fields != null) {
            for (FieldStub field : this.classData.fields) {
                Long timestamp;
                if (!Modifier.isStatic(field.accessModifiers) || (timestamp = Verifier.getTimestampFromFieldName(field.fieldName)) == null) continue;
                return timestamp;
            }
        }
        return Long.MAX_VALUE;
    }

    @Override
    public Class getTypeClass() {
        return this.resolver.resolveJvmClass(this.getName());
    }

    public boolean isParameterized() {
        return this.classData.signature != null && this.classData.signature.charAt(0) == '<';
    }

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    public boolean isSealed() {
        List annotations = this.classData.annotations;
        if (annotations != null) {
            for (AnnotationStub stub : annotations) {
                if (!stub.className.equals("groovy.transform.Sealed")) continue;
                return true;
            }
        }
        try {
            return ReflectionUtils.isSealed(this.getTypeClass());
        }
        catch (NoClassDefFoundError noClassDefFoundError) {
            return false;
        }
    }

    @Override
    public String setName(String name) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRedirect(ClassNode cn) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setUsingGenerics(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setGenericsPlaceHolder(boolean b) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<AnnotationNode> getAnnotations() {
        this.lazyInitSupers();
        return super.getAnnotations();
    }

    @Override
    public List<AnnotationNode> getAnnotations(ClassNode type) {
        this.lazyInitSupers();
        return super.getAnnotations(type);
    }

    @Override
    public GenericsType[] getGenericsTypes() {
        this.lazyInitSupers();
        return super.getGenericsTypes();
    }

    @Override
    public ClassNode[] getInterfaces() {
        this.lazyInitSupers();
        return super.getInterfaces();
    }

    @Override
    public List<RecordComponentNode> getRecordComponents() {
        this.lazyInitSupers();
        return super.getRecordComponents();
    }

    @Override
    public ClassNode[] getUnresolvedInterfaces(boolean useRedirect) {
        this.lazyInitSupers();
        return super.getUnresolvedInterfaces(useRedirect);
    }

    @Override
    public ClassNode getUnresolvedSuperClass(boolean useRedirect) {
        this.lazyInitSupers();
        return super.getUnresolvedSuperClass(useRedirect);
    }

    @Override
    public boolean isUsingGenerics() {
        this.lazyInitSupers();
        return super.isUsingGenerics();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lazyInitSupers() {
        if (this.supersInitialized) {
            return;
        }
        Object object = this.lazyInitLock;
        synchronized (object) {
            if (!this.supersInitialized) {
                ClassSignatureParser.configureClass(this, this.classData, this.resolver);
                Annotations.addAnnotations(this.classData, this, this.resolver);
                this.supersInitialized = true;
            }
        }
    }

    @Override
    public List<ConstructorNode> getDeclaredConstructors() {
        this.lazyInitMembers();
        return super.getDeclaredConstructors();
    }

    @Override
    public FieldNode getDeclaredField(String name) {
        this.lazyInitMembers();
        return super.getDeclaredField(name);
    }

    @Override
    public List<MethodNode> getDeclaredMethods(String name) {
        this.lazyInitMembers();
        return super.getDeclaredMethods(name);
    }

    @Override
    public List<FieldNode> getFields() {
        this.lazyInitMembers();
        return super.getFields();
    }

    @Override
    public List<MethodNode> getMethods() {
        this.lazyInitMembers();
        return super.getMethods();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void lazyInitMembers() {
        if (this.membersInitialized) {
            return;
        }
        Object object = this.lazyInitLock;
        synchronized (object) {
            if (!this.membersInitialized) {
                if (this.classData.methods != null) {
                    for (MethodStub method : this.classData.methods) {
                        if ("<init>".equals(method.methodName)) {
                            this.addConstructor(this.createConstructor(method));
                            continue;
                        }
                        this.addMethod(this.createMethodNode(method));
                    }
                }
                if (this.classData.fields != null) {
                    for (FieldStub field : this.classData.fields) {
                        this.addField(this.createFieldNode(field));
                    }
                }
                this.membersInitialized = true;
            }
        }
    }

    private FieldNode createFieldNode(FieldStub field) {
        Supplier<FieldNode> fieldNodeSupplier = () -> Annotations.addAnnotations(field, MemberSignatureParser.createFieldNode(field, this.resolver, this), this.resolver);
        if ((field.accessModifiers & 2) != 0) {
            return new LazyFieldNode(fieldNodeSupplier, field.fieldName);
        }
        return fieldNodeSupplier.get();
    }

    private MethodNode createMethodNode(MethodStub method) {
        Supplier<MethodNode> methodNodeSupplier = () -> Annotations.addAnnotations(method, MemberSignatureParser.createMethodNode(this.resolver, method), this.resolver);
        if ((method.accessModifiers & 2) != 0) {
            return new LazyMethodNode(methodNodeSupplier, method.methodName);
        }
        return methodNodeSupplier.get();
    }

    private ConstructorNode createConstructor(MethodStub method) {
        Supplier<ConstructorNode> constructorNodeSupplier = () -> (ConstructorNode)Annotations.addAnnotations(method, MemberSignatureParser.createMethodNode(this.resolver, method), this.resolver);
        if ((method.accessModifiers & 2) != 0) {
            return new LazyConstructorNode(constructorNodeSupplier);
        }
        return constructorNodeSupplier.get();
    }
}

