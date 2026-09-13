/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.asn1.esf;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.esf.CrlOcspRef;

public class CompleteRevocationRefs
extends ASN1Object {
    private ASN1Sequence crlOcspRefs;

    public static CompleteRevocationRefs getInstance(Object object) {
        if (object instanceof CompleteRevocationRefs) {
            return (CompleteRevocationRefs)((Object)object);
        }
        if (object != null) {
            return new CompleteRevocationRefs(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private CompleteRevocationRefs(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        while (enumeration.hasMoreElements()) {
            CrlOcspRef.getInstance(enumeration.nextElement());
        }
        this.crlOcspRefs = aSN1Sequence;
    }

    public CompleteRevocationRefs(CrlOcspRef[] crlOcspRefArray) {
        this.crlOcspRefs = new DERSequence((ASN1Encodable[])crlOcspRefArray);
    }

    public CrlOcspRef[] getCrlOcspRefs() {
        CrlOcspRef[] crlOcspRefArray = new CrlOcspRef[this.crlOcspRefs.size()];
        for (int i = 0; i < crlOcspRefArray.length; ++i) {
            crlOcspRefArray[i] = CrlOcspRef.getInstance(this.crlOcspRefs.getObjectAt(i));
        }
        return crlOcspRefArray;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.crlOcspRefs;
    }
}

