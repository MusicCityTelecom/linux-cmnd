/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.groovy.ast.decompiled;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.groovy.ast.decompiled.AnnotatedStub;
import org.codehaus.groovy.ast.decompiled.AnnotationStub;

class MemberStub
implements AnnotatedStub {
    List<AnnotationStub> annotations = null;

    MemberStub() {
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
}

