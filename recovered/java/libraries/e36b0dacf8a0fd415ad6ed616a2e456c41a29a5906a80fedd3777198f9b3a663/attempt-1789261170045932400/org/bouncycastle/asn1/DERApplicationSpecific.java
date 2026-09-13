/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1ApplicationSpecific;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERFactory;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;

public class DERApplicationSpecific
extends ASN1ApplicationSpecific {
    public DERApplicationSpecific(int n, byte[] byArray) {
        super(new DERTaggedObject(false, 64, n, (ASN1Encodable)new DEROctetString(byArray)));
    }

    public DERApplicationSpecific(int n, ASN1Encodable aSN1Encodable) throws IOException {
        this(true, n, aSN1Encodable);
    }

    public DERApplicationSpecific(boolean bl, int n, ASN1Encodable aSN1Encodable) throws IOException {
        super(new DERTaggedObject(bl, 64, n, aSN1Encodable));
    }

    public DERApplicationSpecific(int n, ASN1EncodableVector aSN1EncodableVector) {
        super(new DERTaggedObject(false, 64, n, (ASN1Encodable)DERFactory.createSequence(aSN1EncodableVector)));
    }

    DERApplicationSpecific(ASN1TaggedObject aSN1TaggedObject) {
        super(aSN1TaggedObject);
    }

    @Override
    ASN1Primitive toDERObject() {
        return this;
    }

    @Override
    ASN1Primitive toDLObject() {
        return this;
    }
}

