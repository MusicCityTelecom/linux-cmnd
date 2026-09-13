/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovyjarjarasm.asm.Type;
import groovyjarjarasm.asm.signature.SignatureVisitor;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.ClassHelper;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.decompiled.AsmDecompiler;
import org.codehaus.groovy.ast.decompiled.AsmReferenceResolver;
import org.codehaus.groovy.ast.decompiled.DecompiledClassNode;
import org.codehaus.groovy.vmplugin.v8.Java8;

abstract class TypeSignatureParser
extends SignatureVisitor {
    private final AsmReferenceResolver resolver;
    private String baseName;
    private final List<GenericsType> arguments = new ArrayList<GenericsType>();

    public TypeSignatureParser(AsmReferenceResolver resolver) {
        super(589824);
        this.resolver = resolver;
    }

    protected static ClassNode applyErasure(ClassNode genericType, ClassNode erasure) {
        if (genericType.isArray() && erasure.isArray() && genericType.getComponentType().isGenericsPlaceHolder()) {
            genericType.setRedirect(erasure);
            genericType.getComponentType().setRedirect(erasure.getComponentType());
        } else if (genericType.isGenericsPlaceHolder()) {
            genericType.setRedirect(erasure);
        }
        return genericType;
    }

    abstract void finished(ClassNode var1);

    @Override
    public void visitTypeVariable(String name) {
        this.finished(Java8.configureTypeVariableReference(name));
    }

    @Override
    public void visitBaseType(char descriptor) {
        this.finished(this.resolver.resolveType(Type.getType(String.valueOf(descriptor))));
    }

    @Override
    public SignatureVisitor visitArrayType() {
        final TypeSignatureParser outer = this;
        return new TypeSignatureParser(this.resolver){

            @Override
            void finished(ClassNode result) {
                outer.finished(result.makeArray());
            }
        };
    }

    @Override
    public void visitClassType(String name) {
        this.baseName = AsmDecompiler.fromInternalName(name);
    }

    @Override
    public void visitTypeArgument() {
        this.arguments.add(TypeSignatureParser.createWildcard(null, null));
    }

    @Override
    public SignatureVisitor visitTypeArgument(final char wildcard) {
        return new TypeSignatureParser(this.resolver){

            @Override
            void finished(ClassNode result) {
                ClassNode[] classNodeArray;
                if (wildcard == '=') {
                    TypeSignatureParser.this.arguments.add(new GenericsType(result));
                    return;
                }
                if (wildcard == '+') {
                    ClassNode[] classNodeArray2 = new ClassNode[1];
                    classNodeArray = classNodeArray2;
                    classNodeArray2[0] = result;
                } else {
                    classNodeArray = null;
                }
                ClassNode[] upper = classNodeArray;
                ClassNode lower = wildcard == '-' ? result : null;
                TypeSignatureParser.this.arguments.add(TypeSignatureParser.createWildcard(upper, lower));
            }
        };
    }

    @Override
    public void visitInnerClassType(String name) {
        this.baseName = this.baseName + "$" + name;
        this.arguments.clear();
    }

    @Override
    public void visitEnd() {
        ClassNode baseType = this.resolver.resolveClass(this.baseName);
        if (this.arguments.isEmpty() && TypeSignatureParser.isNotParameterized(baseType)) {
            this.finished(baseType);
        } else {
            ClassNode parameterizedType = baseType.getPlainNodeReference();
            if (!this.arguments.isEmpty()) {
                try {
                    int n = this.arguments.size();
                    for (int i = 0; i < n; ++i) {
                        ClassNode[] implicitBounds;
                        GenericsType argument = this.arguments.get(i);
                        if (!argument.isWildcard() || argument.getUpperBounds() != null || (implicitBounds = baseType.getGenericsTypes()[i].getUpperBounds()) == null || ClassHelper.isObjectType(implicitBounds[0])) continue;
                        argument.getType().setRedirect(implicitBounds[0]);
                    }
                }
                catch (StackOverflowError stackOverflowError) {
                    // empty catch block
                }
                parameterizedType.setGenericsTypes(this.arguments.toArray(GenericsType.EMPTY_ARRAY));
            }
            this.finished(parameterizedType);
        }
    }

    private static GenericsType createWildcard(ClassNode[] upper, ClassNode lower) {
        ClassNode base = ClassHelper.makeWithoutCaching("?");
        base.setRedirect(ClassHelper.OBJECT_TYPE);
        GenericsType t = new GenericsType(base, upper, lower);
        t.setWildcard(true);
        return t;
    }

    private static boolean isNotParameterized(ClassNode cn) {
        if (cn instanceof DecompiledClassNode) {
            return !((DecompiledClassNode)cn).isParameterized();
        }
        return cn.getGenericsTypes() == null;
    }
}

