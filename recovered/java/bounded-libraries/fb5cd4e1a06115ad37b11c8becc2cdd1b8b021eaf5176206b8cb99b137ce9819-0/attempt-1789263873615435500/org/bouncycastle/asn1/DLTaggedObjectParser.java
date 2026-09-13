/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;
import org.bouncycastle.asn1.BERTaggedObjectParser;
import org.bouncycastle.asn1.DLApplicationSpecific;

class DLTaggedObjectParser
extends BERTaggedObjectParser {
    private final boolean _constructed;

    DLTaggedObjectParser(int n, int n2, boolean bl, ASN1StreamParser aSN1StreamParser) {
        super(n, n2, aSN1StreamParser);
        this._constructed = bl;
    }

    @Override
    public boolean isConstructed() {
        return this._constructed;
    }

    @Override
    public ASN1Primitive getLoadedObject() throws IOException {
        return this._parser.loadTaggedDL(this._tagClass, this._tagNo, this._constructed);
    }

    @Override
    public ASN1Encodable parseBaseUniversal(boolean bl, int n) throws IOException {
        if (bl) {
            if (!this._constructed) {
                throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
            }
            return this._parser.parseObject(n);
        }
        return this._constructed ? this._parser.parseImplicitConstructedDL(n) : this._parser.parseImplicitPrimitive(n);
    }

    @Override
    public ASN1Encodable parseExplicitBaseObject() throws IOException {
        if (!this._constructed) {
            throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
        }
        return this._parser.readObject();
    }

    @Override
    public ASN1TaggedObjectParser parseExplicitBaseTagged() throws IOException {
        if (!this._constructed) {
            throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
        }
        return this._parser.parseTaggedObject();
    }

    @Override
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int n, int n2) throws IOException {
        if (64 == n) {
            return (DLApplicationSpecific)this._parser.loadTaggedDL(n, n2, this._constructed);
        }
        return new DLTaggedObjectParser(n, n2, this._constructed, this._parser);
    }
}

