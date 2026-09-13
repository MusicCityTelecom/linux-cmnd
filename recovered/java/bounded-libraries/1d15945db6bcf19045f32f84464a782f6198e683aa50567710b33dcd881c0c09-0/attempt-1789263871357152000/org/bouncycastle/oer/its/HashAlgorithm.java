/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Enumerated
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Enumerated;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;

public class HashAlgorithm
extends ASN1Object {
    public static final HashAlgorithm sha256 = new HashAlgorithm(0);
    public static final HashAlgorithm sha384 = new HashAlgorithm(1);
    public static final HashAlgorithm extension = new HashAlgorithm(2);
    private final ASN1Enumerated enumerated;

    protected HashAlgorithm(int n) {
        this.enumerated = new ASN1Enumerated(n);
    }

    private HashAlgorithm(ASN1Enumerated aSN1Enumerated) {
        this.enumerated = aSN1Enumerated;
    }

    public static HashAlgorithm getInstance(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof HashAlgorithm) {
            return (HashAlgorithm)((Object)object);
        }
        return new HashAlgorithm(ASN1Enumerated.getInstance((Object)object));
    }

    public ASN1Primitive toASN1Primitive() {
        return this.enumerated;
    }
}

