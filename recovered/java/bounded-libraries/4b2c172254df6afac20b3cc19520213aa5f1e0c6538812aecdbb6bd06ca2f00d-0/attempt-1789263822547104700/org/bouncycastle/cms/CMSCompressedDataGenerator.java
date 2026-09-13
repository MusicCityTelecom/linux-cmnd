/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.BEROctetString
 *  org.bouncycastle.asn1.cms.CMSObjectIdentifiers
 *  org.bouncycastle.asn1.cms.CompressedData
 *  org.bouncycastle.asn1.cms.ContentInfo
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.CompressedData;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSCompressedData;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.operator.OutputCompressor;

public class CMSCompressedDataGenerator {
    public static final String ZLIB = "1.2.840.113549.1.9.16.3.8";

    public CMSCompressedData generate(CMSTypedData cMSTypedData, OutputCompressor outputCompressor) throws CMSException {
        BEROctetString bEROctetString;
        AlgorithmIdentifier algorithmIdentifier;
        OutputStream outputStream;
        ByteArrayOutputStream byteArrayOutputStream;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            outputStream = outputCompressor.getOutputStream(byteArrayOutputStream);
            cMSTypedData.write(outputStream);
            outputStream.close();
            algorithmIdentifier = outputCompressor.getAlgorithmIdentifier();
            bEROctetString = new BEROctetString(byteArrayOutputStream.toByteArray());
        }
        catch (IOException iOException) {
            throw new CMSException("exception encoding data.", iOException);
        }
        byteArrayOutputStream = new ContentInfo(cMSTypedData.getContentType(), (ASN1Encodable)bEROctetString);
        outputStream = new ContentInfo(CMSObjectIdentifiers.compressedData, (ASN1Encodable)new CompressedData(algorithmIdentifier, (ContentInfo)byteArrayOutputStream));
        return new CMSCompressedData((ContentInfo)outputStream);
    }
}

