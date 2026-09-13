/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.core.type.AnnotationMetadata
 */
package org.springframework.boot.context.annotation;

import java.util.Set;
import org.springframework.core.type.AnnotationMetadata;

@FunctionalInterface
public interface DeterminableImports {
    public Set<Object> determineImports(AnnotationMetadata var1);
}

