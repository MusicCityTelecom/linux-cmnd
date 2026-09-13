/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1Tag;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.ASN1Type;
import org.bouncycastle.asn1.DEROctetString;

abstract class ASN1UniversalType
extends ASN1Type {
    final ASN1Tag tag;

    ASN1UniversalType(Class clazz, int n) {
        super(clazz);
        this.tag = ASN1Tag.create(0, n);
    }

    final ASN1Primitive checkedCast(ASN1Primitive aSN1Primitive) {
        if (this.javaClass.isInstance(aSN1Primitive)) {
            return aSN1Primitive;
        }
        throw new IllegalStateException("unexpected object: " + aSN1Primitive.getClass().getName());
    }

    ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
        throw new IllegalStateException("unexpected implicit primitive encoding");
    }

    ASN1Primitive fromImplicitConstructed(ASN1Sequence aSN1Sequence) {
        throw new IllegalStateException("unexpected implicit constructed encoding");
    }

    final ASN1Primitive fromByteArray(byte[] byArray) throws IOException {
        return this.checkedCast(ASN1Primitive.fromByteArray(byArray));
    }

    final ASN1Primitive getContextInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        if (128 != aSN1TaggedObject.getTagClass()) {
            throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
        }
        return this.checkedCast(aSN1TaggedObject.getBaseUniversal(bl, this));
    }

    final ASN1Tag getTag() {
        return this.tag;
    }
}

