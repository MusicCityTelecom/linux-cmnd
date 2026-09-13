/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.BEROctetString
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSet
 *  org.bouncycastle.asn1.DLSet
 *  org.bouncycastle.asn1.cms.AttributeTable
 *  org.bouncycastle.asn1.cms.AuthEnvelopedData
 *  org.bouncycastle.asn1.cms.CMSObjectIdentifiers
 *  org.bouncycastle.asn1.cms.ContentInfo
 *  org.bouncycastle.asn1.cms.EncryptedContentInfo
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DLSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.AuthEnvelopedData;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAuthEnvelopedData;
import org.bouncycastle.cms.CMSAuthEnvelopedGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.RecipientInfoGenerator;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OutputAEADEncryptor;

public class CMSAuthEnvelopedDataGenerator
extends CMSAuthEnvelopedGenerator {
    private CMSAuthEnvelopedData doGenerate(CMSTypedData cMSTypedData, OutputAEADEncryptor outputAEADEncryptor) throws CMSException {
        AttributeTable attributeTable;
        RecipientInfoGenerator recipientInfoGenerator2;
        Object object;
        Object object2;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DERSet dERSet = null;
        try {
            object2 = outputAEADEncryptor.getOutputStream(byteArrayOutputStream);
            cMSTypedData.write((OutputStream)object2);
            if (this.authAttrsGenerator != null) {
                object = this.authAttrsGenerator.getAttributes(Collections.EMPTY_MAP);
                dERSet = new DERSet(object.toASN1EncodableVector());
                outputAEADEncryptor.getAADStream().write(dERSet.getEncoded("DER"));
            }
            ((OutputStream)object2).close();
        }
        catch (IOException iOException) {
            throw new CMSException("unable to process authenticated content: " + iOException.getMessage(), iOException);
        }
        object2 = byteArrayOutputStream.toByteArray();
        object = outputAEADEncryptor.getMAC();
        AlgorithmIdentifier algorithmIdentifier = outputAEADEncryptor.getAlgorithmIdentifier();
        BEROctetString bEROctetString = new BEROctetString((byte[])object2);
        GenericKey genericKey = outputAEADEncryptor.getKey();
        for (RecipientInfoGenerator recipientInfoGenerator2 : this.recipientInfoGenerators) {
            aSN1EncodableVector.add((ASN1Encodable)recipientInfoGenerator2.generate(genericKey));
        }
        EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(cMSTypedData.getContentType(), algorithmIdentifier, (ASN1OctetString)bEROctetString);
        recipientInfoGenerator2 = null;
        if (this.unauthAttrsGenerator != null) {
            attributeTable = this.unauthAttrsGenerator.getAttributes(Collections.EMPTY_MAP);
            recipientInfoGenerator2 = new DLSet(attributeTable.toASN1EncodableVector());
        }
        attributeTable = new ContentInfo(CMSObjectIdentifiers.authEnvelopedData, (ASN1Encodable)new AuthEnvelopedData(this.originatorInfo, (ASN1Set)new DERSet(aSN1EncodableVector), encryptedContentInfo, (ASN1Set)dERSet, (ASN1OctetString)new DEROctetString((byte[])object), (ASN1Set)recipientInfoGenerator2));
        return new CMSAuthEnvelopedData((ContentInfo)attributeTable);
    }

    public CMSAuthEnvelopedData generate(CMSTypedData cMSTypedData, OutputAEADEncryptor outputAEADEncryptor) throws CMSException {
        return this.doGenerate(cMSTypedData, outputAEADEncryptor);
    }
}

