/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.asn1;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1OutputStream;
import org.bouncycastle.asn1.ASN1Primitive;

public class DLBitString
extends ASN1BitString {
    public DLBitString(byte[] byArray) {
        this(byArray, 0);
    }

    public DLBitString(byte by, int n) {
        super(by, n);
    }

    public DLBitString(byte[] byArray, int n) {
        super(byArray, n);
    }

    public DLBitString(int n) {
        super(DLBitString.getBytes(n), DLBitString.getPadBits(n));
    }

    public DLBitString(ASN1Encodable aSN1Encodable) throws IOException {
        super(aSN1Encodable.toASN1Primitive().getEncoded("DER"), 0);
    }

    DLBitString(byte[] byArray, boolean bl) {
        super(byArray, bl);
    }

    boolean encodeConstructed() {
        return false;
    }

    int encodedLength(boolean bl) {
        return ASN1OutputStream.getLengthOfEncodingDL(bl, this.contents.length);
    }

    void encode(ASN1OutputStream aSN1OutputStream, boolean bl) throws IOException {
        aSN1OutputStream.writeEncodingDL(bl, 3, this.contents);
    }

    ASN1Primitive toDLObject() {
        return this;
    }

    static int encodedLength(boolean bl, int n) {
        return ASN1OutputStream.getLengthOfEncodingDL(bl, n);
    }

    static void encode(ASN1OutputStream aSN1OutputStream, boolean bl, byte[] byArray, int n, int n2) throws IOException {
        aSN1OutputStream.writeEncodingDL(bl, 3, byArray, n, n2);
    }

    static void encode(ASN1OutputStream aSN1OutputStream, boolean bl, byte by, byte[] byArray, int n, int n2) throws IOException {
        aSN1OutputStream.writeEncodingDL(bl, 3, by, byArray, n, n2);
    }

    static DLBitString fromOctetString(ASN1OctetString aSN1OctetString) {
        return new DLBitString(aSN1OctetString.getOctets(), true);
    }
}

