/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1ApplicationSpecificParser;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.BERTaggedObjectParser;

public class BERApplicationSpecificParser
extends BERTaggedObjectParser
implements ASN1ApplicationSpecificParser {
    BERApplicationSpecificParser(int n, ASN1StreamParser aSN1StreamParser) {
        super(64, n, aSN1StreamParser);
    }

    public ASN1Encodable readObject() throws IOException {
        return this.parseExplicitBaseObject();
    }
}

