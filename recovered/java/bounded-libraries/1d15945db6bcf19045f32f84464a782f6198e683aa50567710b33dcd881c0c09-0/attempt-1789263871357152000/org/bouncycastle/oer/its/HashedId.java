/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.util.Arrays;

public class HashedId
extends ASN1Object {
    private final byte[] string;

    protected HashedId(byte[] byArray) {
        this.string = Arrays.clone((byte[])byArray);
    }

    public static HashedId getInstance(Object object) {
        if (object instanceof HashedId) {
            return (HashedId)((Object)object);
        }
        byte[] byArray = ASN1OctetString.getInstance((Object)object).getOctets();
        switch (byArray.length) {
            case 3: {
                return new HashedId3(byArray);
            }
            case 8: {
                return new HashedId8(byArray);
            }
            case 10: {
                return new HashedId10(byArray);
            }
            case 32: {
                return new HashedId32(byArray);
            }
        }
        throw new IllegalStateException("hash id of unsupported length, length was: " + byArray.length);
    }

    public ASN1Primitive toASN1Primitive() {
        return new DEROctetString(this.string);
    }

    public static class HashedId10
    extends HashedId {
        public HashedId10(byte[] byArray) {
            super(byArray);
            if (byArray.length != 10) {
                throw new IllegalArgumentException("hash id not 10 bytes");
            }
        }
    }

    public static class HashedId3
    extends HashedId {
        public HashedId3(byte[] byArray) {
            super(byArray);
            if (byArray.length != 3) {
                throw new IllegalArgumentException("hash id not 3 bytes");
            }
        }
    }

    public static class HashedId32
    extends HashedId {
        public HashedId32(byte[] byArray) {
            super(byArray);
            if (byArray.length != 32) {
                throw new IllegalArgumentException("hash id not 32 bytes");
            }
        }
    }

    public static class HashedId8
    extends HashedId {
        public HashedId8(byte[] byArray) {
            super(byArray);
            if (byArray.length != 8) {
                throw new IllegalArgumentException("hash id not 8 bytes");
            }
        }
    }
}

