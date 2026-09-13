/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import groovyjarjarasm.asm.signature.SignatureVisitor;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.GenericsType;
import org.codehaus.groovy.ast.decompiled.AsmReferenceResolver;
import org.codehaus.groovy.ast.decompiled.TypeSignatureParser;
import org.codehaus.groovy.vmplugin.v8.Java8;

abstract class FormalParameterParser
extends SignatureVisitor {
    private String currentTypeParameter;
    private final List<ClassNode> parameterBounds = new ArrayList<ClassNode>();
    private final List<GenericsType> typeParameters = new ArrayList<GenericsType>();
    private final AsmReferenceResolver resolver;

    public FormalParameterParser(AsmReferenceResolver resolver) {
        super(589824);
        this.resolver = resolver;
    }

    protected void flushTypeParameter() {
        if (this.currentTypeParameter != null) {
            ClassNode ref = Java8.configureTypeVariableReference(this.currentTypeParameter);
            ClassNode[] theBoundTypes = this.parameterBounds.toArray(ClassNode.EMPTY_ARRAY);
            this.typeParameters.add(Java8.configureTypeVariableDefinition(ref, theBoundTypes));
            this.parameterBounds.clear();
            this.currentTypeParameter = null;
        }
    }

    public GenericsType[] getTypeParameters() {
        this.flushTypeParameter();
        return this.typeParameters.toArray(GenericsType.EMPTY_ARRAY);
    }

    @Override
    public void visitFormalTypeParameter(String name) {
        this.flushTypeParameter();
        this.currentTypeParameter = name;
    }

    @Override
    public SignatureVisitor visitClassBound() {
        return new TypeSignatureParser(this.resolver){

            @Override
            void finished(ClassNode result) {
                FormalParameterParser.this.parameterBounds.add(result);
            }
        };
    }

    @Override
    public SignatureVisitor visitInterfaceBound() {
        return this.visitClassBound();
    }
}

