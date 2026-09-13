/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovyjarjarasm.asm.Type;
import groovyjarjarasm.asm.signature.SignatureReader;
import groovyjarjarasm.asm.signature.SignatureVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.RecordComponentNode;
import org.codehaus.groovy.ast.decompiled.Annotations;
import org.codehaus.groovy.ast.decompiled.AsmDecompiler;
import org.codehaus.groovy.ast.decompiled.AsmReferenceResolver;
import org.codehaus.groovy.ast.decompiled.ClassStub;
import org.codehaus.groovy.ast.decompiled.FormalParameterParser;
import org.codehaus.groovy.ast.decompiled.TypeSignatureParser;

class ClassSignatureParser {
    ClassSignatureParser() {
    }

    static void configureClass(ClassNode classNode, ClassStub stub, AsmReferenceResolver resolver) {
        if (stub.signature != null) {
            ClassSignatureParser.parseClassSignature(classNode, stub.signature, resolver);
            return;
        }
        if (stub.superName != null) {
            ClassNode sc = resolver.resolveClass(AsmDecompiler.fromInternalName(stub.superName));
            classNode.setSuperClass(sc);
        }
        int nInterfaces = stub.interfaceNames.length;
        ClassNode[] interfaces = new ClassNode[nInterfaces];
        for (int i = 0; i < nInterfaces; ++i) {
            String name = stub.interfaceNames[i];
            interfaces[i] = resolver.resolveClass(AsmDecompiler.fromInternalName(name));
        }
        classNode.setInterfaces(interfaces);
        if (!stub.permittedSubclasses.isEmpty()) {
            List<ClassNode> permitted = classNode.getPermittedSubclasses();
            for (String name : stub.permittedSubclasses) {
                ClassNode ps = resolver.resolveClass(AsmDecompiler.fromInternalName(name));
                permitted.add(ps);
            }
        }
        if (!stub.recordComponents.isEmpty()) {
            classNode.setRecordComponents(stub.recordComponents.stream().map(rc -> {
                final ClassNode[] type = new ClassNode[]{resolver.resolveType(Type.getType(rc.descriptor))};
                if (rc.signature != null) {
                    new SignatureReader(rc.signature).accept(new TypeSignatureParser(resolver){

                        @Override
                        void finished(ClassNode result) {
                            type[0] = 1.applyErasure(result, type[0]);
                        }
                    });
                }
                ClassNode rcType = type[0];
                Annotations.addTypeAnnotations(rc, rcType, resolver);
                RecordComponentNode recordComponent = new RecordComponentNode(classNode, rc.name, rcType);
                Annotations.addAnnotations(rc, recordComponent, resolver);
                return recordComponent;
            }).collect(Collectors.toList()));
        }
    }

    private static void parseClassSignature(final ClassNode classNode, String signature, final AsmReferenceResolver resolver) {
        final ArrayList interfaces = new ArrayList();
        FormalParameterParser parser = new FormalParameterParser(resolver){

            @Override
            public SignatureVisitor visitSuperclass() {
                this.flushTypeParameter();
                return new TypeSignatureParser(resolver){

                    @Override
                    void finished(ClassNode superClass) {
                        classNode.setSuperClass(superClass);
                    }
                };
            }

            @Override
            public SignatureVisitor visitInterface() {
                this.flushTypeParameter();
                return new TypeSignatureParser(resolver){

                    @Override
                    void finished(ClassNode superInterface) {
                        interfaces.add(superInterface);
                    }
                };
            }
        };
        new SignatureReader(signature).accept(parser);
        classNode.setInterfaces(interfaces.isEmpty() ? ClassNode.EMPTY_ARRAY : interfaces.toArray(ClassNode.EMPTY_ARRAY));
        GenericsType[] typeParameters = parser.getTypeParameters();
        if (typeParameters.length > 0) {
            classNode.setGenericsTypes(typeParameters);
        }
    }
}

