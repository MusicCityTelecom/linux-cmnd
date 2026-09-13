/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.asn1.icao;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class DataGroupHash
extends ASN1Object {
    ASN1Integer dataGroupNumber;
    ASN1OctetString dataGroupHashValue;

    public static DataGroupHash getInstance(Object object) {
        if (object instanceof DataGroupHash) {
            return (DataGroupHash)((Object)object);
        }
        if (object != null) {
            return new DataGroupHash(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private DataGroupHash(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        this.dataGroupNumber = ASN1Integer.getInstance(enumeration.nextElement());
        this.dataGroupHashValue = ASN1OctetString.getInstance(enumeration.nextElement());
    }

    public DataGroupHash(int n, ASN1OctetString aSN1OctetString) {
        this.dataGroupNumber = new ASN1Integer((long)n);
        this.dataGroupHashValue = aSN1OctetString;
    }

    public int getDataGroupNumber() {
        return this.dataGroupNumber.intValueExact();
    }

    public ASN1OctetString getDataGroupHashValue() {
        return this.dataGroupHashValue;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        aSN1EncodableVector.add((ASN1Encodable)this.dataGroupNumber);
        aSN1EncodableVector.add((ASN1Encodable)this.dataGroupHashValue);
        return new DERSequence(aSN1EncodableVector);
    }
}

