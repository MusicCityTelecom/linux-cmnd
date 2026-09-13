/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1TaggedObjectParser;

public interface ASN1ApplicationSpecificParser
extends ASN1TaggedObjectParser {
    public ASN1Encodable readObject() throws IOException;
}

