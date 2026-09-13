/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1;

import com.android.apksig.internal.asn1.Asn1Type;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Asn1Class {
    public Asn1Type type();
}

