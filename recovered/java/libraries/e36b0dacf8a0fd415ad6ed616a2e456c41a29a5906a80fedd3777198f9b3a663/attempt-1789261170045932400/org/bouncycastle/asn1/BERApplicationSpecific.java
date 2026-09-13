/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BERFactory;
import org.bouncycastle.asn1.BERTaggedObject;

public class BERApplicationSpecific
extends ASN1ApplicationSpecific {
    public BERApplicationSpecific(int n, ASN1Encodable aSN1Encodable) throws IOException {
        this(true, n, aSN1Encodable);
    }

    public BERApplicationSpecific(boolean bl, int n, ASN1Encodable aSN1Encodable) throws IOException {
        super(new BERTaggedObject(bl, 64, n, aSN1Encodable));
    }

    public BERApplicationSpecific(int n, ASN1EncodableVector aSN1EncodableVector) {
        super(new BERTaggedObject(false, 64, n, (ASN1Encodable)BERFactory.createSequence(aSN1EncodableVector)));
    }

    BERApplicationSpecific(ASN1TaggedObject aSN1TaggedObject) {
        super(aSN1TaggedObject);
    }
}

