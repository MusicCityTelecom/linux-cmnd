/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.pkcs.ContentInfo
 *  org.bouncycastle.asn1.pkcs.MacData
 *  org.bouncycastle.asn1.pkcs.PKCS12PBEParams
 *  org.bouncycastle.asn1.pkcs.Pfx
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.util.Arrays
 */
package org.bouncycastle.pkcs;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.ContentInfo;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.pkcs.MacDataGenerator;
import org.bouncycastle.pkcs.PKCS12MacCalculatorBuilderProvider;
import org.bouncycastle.pkcs.PKCSException;
import org.bouncycastle.pkcs.PKCSIOException;
import org.bouncycastle.util.Arrays;

public class PKCS12PfxPdu {
    private Pfx pfx;

    private static Pfx parseBytes(byte[] byArray) throws IOException {
        try {
            return Pfx.getInstance((Object)ASN1Primitive.fromByteArray((byte[])byArray));
        }
        catch (ClassCastException classCastException) {
            throw new PKCSIOException("malformed data: " + classCastException.getMessage(), classCastException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new PKCSIOException("malformed data: " + illegalArgumentException.getMessage(), illegalArgumentException);
        }
    }

    public PKCS12PfxPdu(Pfx pfx) {
        this.pfx = pfx;
    }

    public PKCS12PfxPdu(byte[] byArray) throws IOException {
        this(PKCS12PfxPdu.parseBytes(byArray));
    }

    public ContentInfo[] getContentInfos() {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)ASN1OctetString.getInstance((Object)this.pfx.getAuthSafe().getContent()).getOctets());
        ContentInfo[] contentInfoArray = new ContentInfo[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            contentInfoArray[i] = ContentInfo.getInstance((Object)aSN1Sequence.getObjectAt(i));
        }
        return contentInfoArray;
    }

    public boolean hasMac() {
        return this.pfx.getMacData() != null;
    }

    public AlgorithmIdentifier getMacAlgorithmID() {
        MacData macData = this.pfx.getMacData();
        if (macData != null) {
            return macData.getMac().getAlgorithmId();
        }
        return null;
    }

    public boolean isMacValid(PKCS12MacCalculatorBuilderProvider pKCS12MacCalculatorBuilderProvider, char[] cArray) throws PKCSException {
        if (this.hasMac()) {
            MacData macData = this.pfx.getMacData();
            MacDataGenerator macDataGenerator = new MacDataGenerator(pKCS12MacCalculatorBuilderProvider.get(new AlgorithmIdentifier(macData.getMac().getAlgorithmId().getAlgorithm(), (ASN1Encodable)new PKCS12PBEParams(macData.getSalt(), macData.getIterationCount().intValue()))));
            try {
                MacData macData2 = macDataGenerator.build(cArray, ASN1OctetString.getInstance((Object)this.pfx.getAuthSafe().getContent()).getOctets());
                return Arrays.constantTimeAreEqual((byte[])macData2.getEncoded(), (byte[])this.pfx.getMacData().getEncoded());
            }
            catch (IOException iOException) {
                throw new PKCSException("unable to process AuthSafe: " + iOException.getMessage());
            }
        }
        throw new IllegalStateException("no MAC present on PFX");
    }

    public Pfx toASN1Structure() {
        return this.pfx;
    }

    public byte[] getEncoded() throws IOException {
        return this.toASN1Structure().getEncoded();
    }

    public byte[] getEncoded(String string) throws IOException {
        return this.toASN1Structure().getEncoded(string);
    }
}

