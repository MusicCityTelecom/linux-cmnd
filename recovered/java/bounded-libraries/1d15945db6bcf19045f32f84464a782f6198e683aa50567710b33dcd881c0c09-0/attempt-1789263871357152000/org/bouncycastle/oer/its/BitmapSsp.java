/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.DEROctetString
 */
package org.bouncycastle.oer.its;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DEROctetString;

public class BitmapSsp
extends DEROctetString {
    public BitmapSsp(byte[] byArray) {
        super(byArray);
    }

    public BitmapSsp(ASN1Encodable aSN1Encodable) throws IOException {
        super(aSN1Encodable);
    }
}

