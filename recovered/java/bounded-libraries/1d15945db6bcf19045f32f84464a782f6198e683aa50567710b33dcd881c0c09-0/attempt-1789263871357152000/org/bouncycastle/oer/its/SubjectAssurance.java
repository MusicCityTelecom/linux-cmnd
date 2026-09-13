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

public class SubjectAssurance
extends DEROctetString {
    public SubjectAssurance(byte[] byArray) {
        super(byArray);
    }

    public SubjectAssurance(ASN1Encodable aSN1Encodable) throws IOException {
        super(aSN1Encodable);
    }

    public static SubjectAssurance getInstance(Object object) {
        if (object instanceof SubjectAssurance) {
            return (SubjectAssurance)((Object)object);
        }
        return new SubjectAssurance(DEROctetString.getInstance((Object)object).getOctets());
    }
}

