/*
 * Decompiled with CFR 0.152.
 */
package com.android.apksig.internal.pkcs7;

import com.android.apksig.internal.asn1.Asn1Class;
import com.android.apksig.internal.asn1.Asn1Field;
import com.android.apksig.internal.asn1.Asn1OpaqueObject;
import com.android.apksig.internal.asn1.Asn1Type;

@Asn1Class(type=Asn1Type.SEQUENCE)
public class AlgorithmIdentifier {
    @Asn1Field(index=0, type=Asn1Type.OBJECT_IDENTIFIER)
    public String algorithm;
    @Asn1Field(index=1, type=Asn1Type.ANY, optional=true)
    public Asn1OpaqueObject parameters;

    public AlgorithmIdentifier() {
    }

    public AlgorithmIdentifier(String algorithmOid, Asn1OpaqueObject parameters) {
        this.algorithm = algorithmOid;
        this.parameters = parameters;
    }
}

