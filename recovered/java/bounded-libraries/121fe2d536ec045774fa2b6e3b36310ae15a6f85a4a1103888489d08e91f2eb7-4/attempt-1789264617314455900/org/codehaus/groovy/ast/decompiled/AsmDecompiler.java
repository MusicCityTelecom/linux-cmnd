/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovy.lang.GroovyRuntimeException;
import groovyjarjarasm.asm.AnnotationVisitor;
import groovyjarjarasm.asm.ClassReader;
import groovyjarjarasm.asm.ClassVisitor;
import groovyjarjarasm.asm.FieldVisitor;
import groovyjarjarasm.asm.MethodVisitor;
import groovyjarjarasm.asm.RecordComponentVisitor;
import groovyjarjarasm.asm.Type;
import groovyjarjarasm.asm.TypePath;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.codehaus.groovy.ast.decompiled.AnnotationStub;
import org.codehaus.groovy.ast.decompiled.ClassStub;
import org.codehaus.groovy.ast.decompiled.EnumConstantWrapper;
import org.codehaus.groovy.ast.decompiled.FieldStub;
import org.codehaus.groovy.ast.decompiled.MethodStub;
import org.codehaus.groovy.ast.decompiled.RecordComponentStub;
import org.codehaus.groovy.ast.decompiled.TypeWrapper;
import org.codehaus.groovy.control.ResolveVisitor;
import org.codehaus.groovy.util.URLStreams;

public abstract class AsmDecompiler {
    private static final Map<URI, SoftReference<ClassStub>> stubCache = new ConcurrentHashMap<URI, SoftReference<ClassStub>>();

    public static ClassStub parseClass(URL url) throws IOException {
        ClassStub stub;
        URI uri;
        try {
            uri = url.toURI();
        }
        catch (URISyntaxException e) {
            throw new GroovyRuntimeException(e);
        }
        SoftReference<ClassStub> ref = stubCache.get(uri);
        ClassStub classStub = stub = ref != null ? ref.get() : null;
        if (stub == null) {
            DecompilingVisitor visitor = new DecompilingVisitor();
            try (BufferedInputStream stream = new BufferedInputStream(URLStreams.openUncachedStream(url));){
                new ClassReader(stream).accept(visitor, 4);
            }
            stub = visitor.result;
            stubCache.put(uri, new SoftReference<ClassStub>(stub));
        }
        return stub;
    }

    private static AnnotationReader readAnnotationMembers(final AnnotationStub stub) {
        return new AnnotationReader(){

            @Override
            void visitAttribute(String name, Object value) {
                stub.members.put(name, value);
            }
        };
    }

    static String fromInternalName(String name) {
        return name.replace('/', '.');
    }

    private static abstract class AnnotationReader
    extends AnnotationVisitor {
        public AnnotationReader() {
            super(589824);
        }

        abstract void visitAttribute(String var1, Object var2);

        @Override
        public void visit(String name, Object value) {
            this.visitAttribute(name, value instanceof Type ? new TypeWrapper(((Type)value).getDescriptor()) : value);
        }

        @Override
        public void visitEnum(String name, String desc, String value) {
            this.visitAttribute(name, new EnumConstantWrapper(desc, value));
        }

        @Override
        public AnnotationVisitor visitAnnotation(String name, String desc) {
            AnnotationStub stub = new AnnotationStub(desc);
            this.visitAttribute(name, stub);
            return AsmDecompiler.readAnnotationMembers(stub);
        }

        @Override
        public AnnotationVisitor visitArray(String name) {
            final ArrayList list = new ArrayList();
            this.visitAttribute(name, list);
            return new AnnotationReader(){

                @Override
                void visitAttribute(String name, Object value) {
                    list.add(value);
                }
            };
        }
    }

    private static class DecompilingVisitor
    extends ClassVisitor {
        private ClassStub result;

        public DecompilingVisitor() {
            super(589824);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaceNames) {
            this.result = new ClassStub(AsmDecompiler.fromInternalName(name), access, signature, superName, interfaceNames);
        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {
            if (AsmDecompiler.fromInternalName(name).equals(this.result.className)) {
                this.result.innerClassModifiers = access;
            }
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if ("<clinit>".equals(name)) {
                return null;
            }
            final MethodStub stub = new MethodStub(name, access, desc, signature, exceptions != null ? exceptions : ResolveVisitor.EMPTY_STRING_ARRAY);
            if (this.result.methods == null) {
                this.result.methods = new ArrayList<MethodStub>(1);
            }
            this.result.methods.add(stub);
            return new MethodVisitor(this.api){

                @Override
                public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                    return AsmDecompiler.readAnnotationMembers(stub.addAnnotation(desc));
                }

                @Override
                public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
                    if (stub.parameterAnnotations == null) {
                        stub.parameterAnnotations = new HashMap<Integer, List<AnnotationStub>>(1);
                    }
                    List list = stub.parameterAnnotations.computeIfAbsent(parameter, k -> new ArrayList());
                    AnnotationStub annotationStub = new AnnotationStub(desc);
                    list.add(annotationStub);
                    return AsmDecompiler.readAnnotationMembers(annotationStub);
                }

                @Override
                public AnnotationVisitor visitAnnotationDefault() {
                    return new AnnotationReader(){

                        @Override
                        void visitAttribute(String name, Object value) {
                            stub.annotationDefault = value;
                        }
                    };
                }

                @Override
                public void visitParameter(String name, int access) {
                    if (stub.parameterNames == null) {
                        stub.parameterNames = new ArrayList<String>();
                    }
                    stub.parameterNames.add(name);
                }
            };
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            return AsmDecompiler.readAnnotationMembers(this.result.addAnnotation(desc));
        }

        @Override
        public void visitPermittedSubclass(String permittedSubclass) {
            this.result.permittedSubclasses.add(permittedSubclass);
        }

        @Override
        public RecordComponentVisitor visitRecordComponent(String name, String descriptor, String signature) {
            final RecordComponentStub recordComponentStub = new RecordComponentStub(name, descriptor, signature);
            this.result.recordComponents.add(recordComponentStub);
            return new RecordComponentVisitor(this.api){

                @Override
                public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                    return AsmDecompiler.readAnnotationMembers(recordComponentStub.addAnnotation(descriptor));
                }

                @Override
                public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
                    return AsmDecompiler.readAnnotationMembers(recordComponentStub.addTypeAnnotation(descriptor));
                }
            };
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            final FieldStub stub = new FieldStub(name, access, desc, signature, value);
            if (this.result.fields == null) {
                this.result.fields = new ArrayList<FieldStub>(1);
            }
            this.result.fields.add(stub);
            return new FieldVisitor(this.api){

                @Override
                public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                    return AsmDecompiler.readAnnotationMembers(stub.addAnnotation(desc));
                }
            };
        }
    }
}

