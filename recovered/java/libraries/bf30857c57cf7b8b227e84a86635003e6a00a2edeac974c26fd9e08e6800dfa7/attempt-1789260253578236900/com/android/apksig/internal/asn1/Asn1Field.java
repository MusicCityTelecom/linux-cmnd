/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.asn1;

import com.android.apksig.internal.asn1.Asn1TagClass;
import com.android.apksig.internal.asn1.Asn1Tagging;
import com.android.apksig.internal.asn1.Asn1Type;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.FIELD})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Asn1Field {
    public int index() default 0;

    public Asn1TagClass cls() default Asn1TagClass.AUTOMATIC;

    public Asn1Type type();

    public Asn1Tagging tagging() default Asn1Tagging.NORMAL;

    public int tagNumber() default -1;

    public boolean optional() default false;

    public Asn1Type elementType() default Asn1Type.ANY;
}

