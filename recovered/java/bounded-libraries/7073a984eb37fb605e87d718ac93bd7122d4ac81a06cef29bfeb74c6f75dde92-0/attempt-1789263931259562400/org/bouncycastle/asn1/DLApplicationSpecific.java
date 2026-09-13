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
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DLFactory;
import org.bouncycastle.asn1.DLTaggedObject;

public class DLApplicationSpecific
extends ASN1ApplicationSpecific {
    public DLApplicationSpecific(int n, byte[] byArray) {
        super(new DLTaggedObject(false, 64, n, (ASN1Encodable)new DEROctetString(byArray)));
    }

    public DLApplicationSpecific(int n, ASN1Encodable aSN1Encodable) throws IOException {
        this(true, n, aSN1Encodable);
    }

    public DLApplicationSpecific(boolean bl, int n, ASN1Encodable aSN1Encodable) throws IOException {
        super(new DLTaggedObject(bl, 64, n, aSN1Encodable));
    }

    public DLApplicationSpecific(int n, ASN1EncodableVector aSN1EncodableVector) {
        super(new DLTaggedObject(false, 64, n, (ASN1Encodable)DLFactory.createSequence(aSN1EncodableVector)));
    }

    DLApplicationSpecific(ASN1TaggedObject aSN1TaggedObject) {
        super(aSN1TaggedObject);
    }

    ASN1Primitive toDLObject() {
        return this;
    }
}

