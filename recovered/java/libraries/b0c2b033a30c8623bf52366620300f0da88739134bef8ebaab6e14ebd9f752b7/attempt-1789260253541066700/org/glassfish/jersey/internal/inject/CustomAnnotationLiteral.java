/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.inject;

import org.glassfish.jersey.internal.inject.AnnotationLiteral;
import org.glassfish.jersey.internal.inject.Custom;

public final class CustomAnnotationLiteral
extends AnnotationLiteral<Custom>
implements Custom {
    public static final Custom INSTANCE = new CustomAnnotationLiteral();

    private CustomAnnotationLiteral() {
    }
}

