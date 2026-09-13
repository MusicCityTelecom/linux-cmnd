/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.decompiled.AnnotatedStub;
import org.codehaus.groovy.ast.decompiled.AnnotatedTypeStub;
import org.codehaus.groovy.ast.decompiled.AnnotationStub;
import org.codehaus.groovy.ast.decompiled.TypeAnnotationStub;

class RecordComponentStub
implements AnnotatedStub,
AnnotatedTypeStub {
    final String name;
    final String descriptor;
    final String signature;
    List<AnnotationStub> annotations;
    List<TypeAnnotationStub> typeAnnotations;

    public RecordComponentStub(String name, String descriptor, String signature) {
        this.name = name;
        this.descriptor = descriptor;
        this.signature = signature;
    }

    AnnotationStub addAnnotation(String desc) {
        AnnotationStub stub = new AnnotationStub(desc);
        if (this.annotations == null) {
            this.annotations = new ArrayList<AnnotationStub>(1);
        }
        this.annotations.add(stub);
        return stub;
    }

    @Override
    public List<AnnotationStub> getAnnotations() {
        return this.annotations;
    }

    public TypeAnnotationStub addTypeAnnotation(String desc) {
        TypeAnnotationStub stub = new TypeAnnotationStub(desc);
        if (this.typeAnnotations == null) {
            this.typeAnnotations = new ArrayList<TypeAnnotationStub>(1);
        }
        this.typeAnnotations.add(stub);
        return stub;
    }

    @Override
    public List<TypeAnnotationStub> getTypeAnnotations() {
        return this.typeAnnotations;
    }
}

