/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.SignedData;
import org.bouncycastle.oer.its.Uint8;
import org.bouncycastle.oer.its.Utils;

public class CounterSignature
extends ASN1Object {
    private final Uint8 protocolVersion;
    private final SignedData signedData;

    public CounterSignature(Uint8 uint8, SignedData signedData) {
        this.protocolVersion = uint8;
        this.signedData = signedData;
    }

    public CounterSignature getInstance(Object object) {
        if (object instanceof CounterSignature) {
            return (CounterSignature)((Object)object);
        }
        Iterator iterator = ASN1Sequence.getInstance((Object)object).iterator();
        return new CounterSignature(Uint8.getInstance(iterator.next()), SignedData.getInstance(iterator.next()));
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.protocolVersion, this.signedData});
    }

    public Uint8 getProtocolVersion() {
        return this.protocolVersion;
    }

    public SignedData getSignedData() {
        return this.signedData;
    }
}

