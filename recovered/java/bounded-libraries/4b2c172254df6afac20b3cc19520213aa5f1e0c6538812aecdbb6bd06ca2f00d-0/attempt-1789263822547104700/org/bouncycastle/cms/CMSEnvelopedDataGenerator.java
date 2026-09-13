/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.BEROctetString
 *  org.bouncycastle.asn1.BERSet
 *  org.bouncycastle.asn1.DERSet
 *  org.bouncycastle.asn1.cms.AttributeTable
 *  org.bouncycastle.asn1.cms.CMSObjectIdentifiers
 *  org.bouncycastle.asn1.cms.ContentInfo
 *  org.bouncycastle.asn1.cms.EncryptedContentInfo
 *  org.bouncycastle.asn1.cms.EnvelopedData
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
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSEnvelopedGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.RecipientInfoGenerator;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OutputAEADEncryptor;
import org.bouncycastle.operator.OutputEncryptor;

public class CMSEnvelopedDataGenerator
extends CMSEnvelopedGenerator {
    private CMSEnvelopedData doGenerate(CMSTypedData cMSTypedData, OutputEncryptor outputEncryptor) throws CMSException {
        AttributeTable attributeTable;
        RecipientInfoGenerator recipientInfoGenerator2;
        Object object;
        Object object2;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            object2 = outputEncryptor.getOutputStream(byteArrayOutputStream);
            cMSTypedData.write((OutputStream)object2);
            ((OutputStream)object2).close();
            if (outputEncryptor instanceof OutputAEADEncryptor) {
                object = ((OutputAEADEncryptor)outputEncryptor).getMAC();
                byteArrayOutputStream.write((byte[])object, 0, ((byte[])object).length);
            }
        }
        catch (IOException iOException) {
            throw new CMSException("");
        }
        object2 = byteArrayOutputStream.toByteArray();
        AlgorithmIdentifier algorithmIdentifier = outputEncryptor.getAlgorithmIdentifier();
        BEROctetString bEROctetString = new BEROctetString((byte[])object2);
        object = outputEncryptor.getKey();
        for (RecipientInfoGenerator recipientInfoGenerator2 : this.recipientInfoGenerators) {
            aSN1EncodableVector.add((ASN1Encodable)recipientInfoGenerator2.generate((GenericKey)object));
        }
        EncryptedContentInfo encryptedContentInfo = new EncryptedContentInfo(cMSTypedData.getContentType(), algorithmIdentifier, (ASN1OctetString)bEROctetString);
        recipientInfoGenerator2 = null;
        if (this.unprotectedAttributeGenerator != null) {
            attributeTable = this.unprotectedAttributeGenerator.getAttributes(Collections.EMPTY_MAP);
            recipientInfoGenerator2 = new BERSet(attributeTable.toASN1EncodableVector());
        }
        attributeTable = new ContentInfo(CMSObjectIdentifiers.envelopedData, (ASN1Encodable)new EnvelopedData(this.originatorInfo, (ASN1Set)new DERSet(aSN1EncodableVector), encryptedContentInfo, (ASN1Set)recipientInfoGenerator2));
        return new CMSEnvelopedData((ContentInfo)attributeTable);
    }

    public CMSEnvelopedData generate(CMSTypedData cMSTypedData, OutputEncryptor outputEncryptor) throws CMSException {
        return this.doGenerate(cMSTypedData, outputEncryptor);
    }
}

