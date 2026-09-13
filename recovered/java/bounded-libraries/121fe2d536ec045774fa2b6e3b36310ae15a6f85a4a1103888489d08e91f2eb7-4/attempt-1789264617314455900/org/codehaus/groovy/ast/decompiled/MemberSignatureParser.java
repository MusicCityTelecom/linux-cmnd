/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovyjarjarasm.asm.Type;
import groovyjarjarasm.asm.signature.SignatureReader;
import groovyjarjarasm.asm.signature.SignatureVisitor;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.ast.AnnotationNode;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.ConstructorNode;
import org.codehaus.groovy.ast.FieldNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.decompiled.AnnotationStub;
import org.codehaus.groovy.ast.decompiled.Annotations;
import org.codehaus.groovy.ast.decompiled.AsmDecompiler;
import org.codehaus.groovy.ast.decompiled.AsmReferenceResolver;
import org.codehaus.groovy.ast.decompiled.DecompiledClassNode;
import org.codehaus.groovy.ast.decompiled.FieldStub;
import org.codehaus.groovy.ast.decompiled.FormalParameterParser;
import org.codehaus.groovy.ast.decompiled.MethodStub;
import org.codehaus.groovy.ast.decompiled.TypeSignatureParser;
import org.codehaus.groovy.ast.decompiled.TypeWrapper;
import org.codehaus.groovy.ast.expr.ConstantExpression;
import org.codehaus.groovy.ast.stmt.ReturnStatement;
import org.codehaus.groovy.ast.tools.GenericsUtils;

class MemberSignatureParser {
    MemberSignatureParser() {
    }

    static FieldNode createFieldNode(FieldStub field, AsmReferenceResolver resolver, DecompiledClassNode owner) {
        final ClassNode[] type = MemberSignatureParser.resolve(resolver, Type.getType(field.desc));
        if (field.signature != null) {
            new SignatureReader(field.signature).accept(new TypeSignatureParser(resolver){

                @Override
                void finished(ClassNode result) {
                    type[0] = 1.applyErasure(result, type[0]);
                }
            });
        } else {
            type[0] = GenericsUtils.nonGeneric(type[0]);
        }
        return new FieldNode(field.fieldName, field.accessModifiers, type[0], owner, field.value != null ? new ConstantExpression(field.value) : null);
    }

    static MethodNode createMethodNode(final AsmReferenceResolver resolver, MethodStub method) {
        MethodNode result;
        GenericsType[] typeParameters = null;
        final ClassNode[] returnType = MemberSignatureParser.resolve(resolver, Type.getReturnType(method.desc));
        final ClassNode[] parameterTypes = MemberSignatureParser.resolve(resolver, Type.getArgumentTypes(method.desc));
        final ClassNode[] exceptionTypes = MemberSignatureParser.resolve(resolver, method.exceptions);
        if (method.signature != null) {
            FormalParameterParser parser = new FormalParameterParser(resolver){
                private int exceptionIndex;
                private int parameterIndex;

                @Override
                public SignatureVisitor visitReturnType() {
                    return new TypeSignatureParser(resolver){

                        @Override
                        void finished(ClassNode result) {
                            returnType[0] = 1.applyErasure(result, returnType[0]);
                        }
                    };
                }

                @Override
                public SignatureVisitor visitParameterType() {
                    return new TypeSignatureParser(resolver){

                        @Override
                        void finished(ClassNode result) {
                            parameterTypes[(this).parameterIndex] = 2.applyErasure(result, parameterTypes[parameterIndex]);
                            parameterIndex += 1;
                        }
                    };
                }

                @Override
                public SignatureVisitor visitExceptionType() {
                    return new TypeSignatureParser(resolver){

                        @Override
                        void finished(ClassNode result) {
                            exceptionTypes[(this).exceptionIndex] = 3.applyErasure(result, exceptionTypes[exceptionIndex]);
                            exceptionIndex += 1;
                        }
                    };
                }
            };
            new SignatureReader(method.signature).accept(parser);
            typeParameters = parser.getTypeParameters();
        } else {
            returnType[0] = GenericsUtils.nonGeneric(returnType[0]);
            int n = parameterTypes.length;
            for (int i = 0; i < n; ++i) {
                parameterTypes[i] = GenericsUtils.nonGeneric(parameterTypes[i]);
            }
        }
        int nParameters = parameterTypes.length;
        Parameter[] parameters = new Parameter[nParameters];
        List<String> parameterNames = method.parameterNames;
        for (int i = 0; i < nParameters; ++i) {
            String decompiledName;
            String parameterName = "param" + i;
            if (parameterNames != null && i < parameterNames.size() && (decompiledName = parameterNames.get(i)) != null) {
                parameterName = decompiledName;
            }
            parameters[i] = new Parameter(parameterTypes[i], parameterName);
        }
        if (method.parameterAnnotations != null) {
            for (Map.Entry<Integer, List<AnnotationStub>> entry : method.parameterAnnotations.entrySet()) {
                for (AnnotationStub stub : entry.getValue()) {
                    AnnotationNode annotationNode = Annotations.createAnnotationNode(stub, resolver);
                    if (annotationNode == null) continue;
                    parameters[entry.getKey()].addAnnotation(annotationNode);
                }
            }
        }
        if ("<init>".equals(method.methodName)) {
            result = new ConstructorNode(method.accessModifiers, parameters, exceptionTypes, null);
        } else {
            result = new MethodNode(method.methodName, method.accessModifiers, returnType[0], parameters, exceptionTypes, null);
            Object annDefault = method.annotationDefault;
            if (annDefault != null) {
                if (annDefault instanceof TypeWrapper) {
                    annDefault = resolver.resolveType(Type.getType(((TypeWrapper)annDefault).desc));
                }
                result.setCode(new ReturnStatement(new ConstantExpression(annDefault)));
                result.setAnnotationDefault(true);
            } else {
                result.setCode(new ReturnStatement(new ConstantExpression(null)));
            }
        }
        if (typeParameters != null && typeParameters.length > 0) {
            result.setGenericsTypes(typeParameters);
        }
        return result;
    }

    private static ClassNode[] resolve(AsmReferenceResolver resolver, String[] names) {
        int n = names.length;
        ClassNode[] nodes = new ClassNode[n];
        for (int i = 0; i < n; ++i) {
            nodes[i] = resolver.resolveClass(AsmDecompiler.fromInternalName(names[i]));
        }
        return nodes;
    }

    private static ClassNode[] resolve(AsmReferenceResolver resolver, Type ... types) {
        int n = types.length;
        ClassNode[] nodes = new ClassNode[n];
        for (int i = 0; i < n; ++i) {
            nodes[i] = resolver.resolveType(types[i]);
        }
        return nodes;
    }
}

