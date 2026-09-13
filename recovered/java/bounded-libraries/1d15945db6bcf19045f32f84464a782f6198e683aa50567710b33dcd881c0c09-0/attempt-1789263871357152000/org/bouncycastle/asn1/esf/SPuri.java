/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1IA5String
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.DERIA5String
 */
package org.bouncycastle.asn1.esf;

import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERIA5String;

public class SPuri {
    private ASN1IA5String uri;

    public static SPuri getInstance(Object object) {
        if (object instanceof SPuri) {
            return (SPuri)object;
        }
        if (object instanceof ASN1IA5String) {
            return new SPuri(ASN1IA5String.getInstance((Object)object));
        }
        return null;
    }

    public SPuri(ASN1IA5String aSN1IA5String) {
        this.uri = aSN1IA5String;
    }

    public DERIA5String getUri() {
        return null == this.uri || this.uri instanceof DERIA5String ? (DERIA5String)this.uri : new DERIA5String(this.uri.getString(), false);
    }

    public ASN1IA5String getUriIA5() {
        return this.uri;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.uri.toASN1Primitive();
    }
}

