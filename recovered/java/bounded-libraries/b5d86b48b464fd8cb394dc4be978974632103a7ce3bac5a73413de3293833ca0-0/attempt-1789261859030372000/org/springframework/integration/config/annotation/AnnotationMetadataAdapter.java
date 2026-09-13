/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.annotation.MergedAnnotations
 *  org.springframework.core.type.AnnotationMetadata
 *  org.springframework.core.type.MethodMetadata
 */
package org.springframework.integration.config.annotation;

import java.util.Set;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.MethodMetadata;

public abstract class AnnotationMetadataAdapter
implements AnnotationMetadata {
    private static final RuntimeException UNSUPPORTED_OPERATION = new UnsupportedOperationException("The class doesn't support this operation");

    public Set<MethodMetadata> getAnnotatedMethods(String annotationName) {
        throw UNSUPPORTED_OPERATION;
    }

    public MergedAnnotations getAnnotations() {
        throw UNSUPPORTED_OPERATION;
    }

    public String getClassName() {
        throw UNSUPPORTED_OPERATION;
    }

    public boolean isInterface() {
        throw UNSUPPORTED_OPERATION;
    }

    public boolean isAnnotation() {
        throw UNSUPPORTED_OPERATION;
    }

    public boolean isAbstract() {
        throw UNSUPPORTED_OPERATION;
    }

    public boolean isFinal() {
        throw UNSUPPORTED_OPERATION;
    }

    public boolean isIndependent() {
        throw UNSUPPORTED_OPERATION;
    }

    public String getEnclosingClassName() {
        throw UNSUPPORTED_OPERATION;
    }

    public String getSuperClassName() {
        throw UNSUPPORTED_OPERATION;
    }

    public String[] getInterfaceNames() {
        throw UNSUPPORTED_OPERATION;
    }

    public String[] getMemberClassNames() {
        throw UNSUPPORTED_OPERATION;
    }
}

