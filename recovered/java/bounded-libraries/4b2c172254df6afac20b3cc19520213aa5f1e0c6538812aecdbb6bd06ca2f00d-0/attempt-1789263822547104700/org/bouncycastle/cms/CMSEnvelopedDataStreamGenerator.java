/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.BERSequenceGenerator
 *  org.bouncycastle.asn1.BERSet
 *  org.bouncycastle.asn1.DERSet
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.DLSet
 *  org.bouncycastle.asn1.cms.AttributeTable
 *  org.bouncycastle.asn1.cms.CMSObjectIdentifiers
 *  org.bouncycastle.asn1.cms.EnvelopedData
 *  org.bouncycastle.asn1.cms.OriginatorInfo
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 */
package org.bouncycastle.cms;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSet;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.EnvelopedData;
import org.bouncycastle.asn1.cms.OriginatorInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSEnvelopedGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSUtils;
import org.bouncycastle.cms.RecipientInfoGenerator;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OutputAEADEncryptor;
import org.bouncycastle.operator.OutputEncryptor;

public class CMSEnvelopedDataStreamGenerator
extends CMSEnvelopedGenerator {
    private int _bufferSize;
    private boolean _berEncodeRecipientSet;

    public void setBufferSize(int n) {
        this._bufferSize = n;
    }

    public void setBEREncodeRecipients(boolean bl) {
        this._berEncodeRecipientSet = bl;
    }

    private ASN1Integer getVersion(ASN1EncodableVector aSN1EncodableVector) {
        if (this.unprotectedAttributeGenerator != null) {
            return new ASN1Integer((long)EnvelopedData.calculateVersion((OriginatorInfo)this.originatorInfo, (ASN1Set)new DLSet(aSN1EncodableVector), (ASN1Set)new DLSet()));
        }
        return new ASN1Integer((long)EnvelopedData.calculateVersion((OriginatorInfo)this.originatorInfo, (ASN1Set)new DLSet(aSN1EncodableVector), null));
    }

    private OutputStream doOpen(ASN1ObjectIdentifier aSN1ObjectIdentifier, OutputStream outputStream, OutputEncryptor outputEncryptor) throws IOException, CMSException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        GenericKey genericKey = outputEncryptor.getKey();
        for (RecipientInfoGenerator recipientInfoGenerator : this.recipientInfoGenerators) {
            aSN1EncodableVector.add((ASN1Encodable)recipientInfoGenerator.generate(genericKey));
        }
        return this.open(aSN1ObjectIdentifier, outputStream, aSN1EncodableVector, outputEncryptor);
    }

    protected OutputStream open(ASN1ObjectIdentifier aSN1ObjectIdentifier, OutputStream outputStream, ASN1EncodableVector aSN1EncodableVector, OutputEncryptor outputEncryptor) throws IOException {
        BERSequenceGenerator bERSequenceGenerator = new BERSequenceGenerator(outputStream);
        bERSequenceGenerator.addObject((ASN1Primitive)CMSObjectIdentifiers.envelopedData);
        BERSequenceGenerator bERSequenceGenerator2 = new BERSequenceGenerator(bERSequenceGenerator.getRawOutputStream(), 0, true);
        bERSequenceGenerator2.addObject((ASN1Primitive)this.getVersion(aSN1EncodableVector));
        if (this.originatorInfo != null) {
            bERSequenceGenerator2.addObject((ASN1Primitive)new DERTaggedObject(false, 0, (ASN1Encodable)this.originatorInfo));
        }
        if (this._berEncodeRecipientSet) {
            bERSequenceGenerator2.getRawOutputStream().write(new BERSet(aSN1EncodableVector).getEncoded());
        } else {
            bERSequenceGenerator2.getRawOutputStream().write(new DERSet(aSN1EncodableVector).getEncoded());
        }
        BERSequenceGenerator bERSequenceGenerator3 = new BERSequenceGenerator(bERSequenceGenerator2.getRawOutputStream());
        bERSequenceGenerator3.addObject((ASN1Primitive)aSN1ObjectIdentifier);
        AlgorithmIdentifier algorithmIdentifier = outputEncryptor.getAlgorithmIdentifier();
        bERSequenceGenerator3.getRawOutputStream().write(algorithmIdentifier.getEncoded());
        OutputStream outputStream2 = CMSUtils.createBEROctetOutputStream(bERSequenceGenerator3.getRawOutputStream(), 0, false, this._bufferSize);
        return new CmsEnvelopedDataOutputStream(outputEncryptor, outputStream2, bERSequenceGenerator, bERSequenceGenerator2, bERSequenceGenerator3);
    }

    protected OutputStream open(OutputStream outputStream, ASN1EncodableVector aSN1EncodableVector, OutputEncryptor outputEncryptor) throws CMSException {
        try {
            BERSequenceGenerator bERSequenceGenerator = new BERSequenceGenerator(outputStream);
            bERSequenceGenerator.addObject((ASN1Primitive)CMSObjectIdentifiers.envelopedData);
            BERSequenceGenerator bERSequenceGenerator2 = new BERSequenceGenerator(bERSequenceGenerator.getRawOutputStream(), 0, true);
            Object object = this._berEncodeRecipientSet ? new BERSet(aSN1EncodableVector) : new DERSet(aSN1EncodableVector);
            bERSequenceGenerator2.addObject((ASN1Primitive)this.getVersion(aSN1EncodableVector));
            if (this.originatorInfo != null) {
                bERSequenceGenerator2.addObject((ASN1Primitive)new DERTaggedObject(false, 0, (ASN1Encodable)this.originatorInfo));
            }
            bERSequenceGenerator2.getRawOutputStream().write(object.getEncoded());
            BERSequenceGenerator bERSequenceGenerator3 = new BERSequenceGenerator(bERSequenceGenerator2.getRawOutputStream());
            bERSequenceGenerator3.addObject((ASN1Primitive)CMSObjectIdentifiers.data);
            AlgorithmIdentifier algorithmIdentifier = outputEncryptor.getAlgorithmIdentifier();
            bERSequenceGenerator3.getRawOutputStream().write(algorithmIdentifier.getEncoded());
            OutputStream outputStream2 = CMSUtils.createBEROctetOutputStream(bERSequenceGenerator3.getRawOutputStream(), 0, false, this._bufferSize);
            return new CmsEnvelopedDataOutputStream(outputEncryptor, outputStream2, bERSequenceGenerator, bERSequenceGenerator2, bERSequenceGenerator3);
        }
        catch (IOException iOException) {
            throw new CMSException("exception decoding algorithm parameters.", iOException);
        }
    }

    public OutputStream open(OutputStream outputStream, OutputEncryptor outputEncryptor) throws CMSException, IOException {
        return this.doOpen(new ASN1ObjectIdentifier(CMSObjectIdentifiers.data.getId()), outputStream, outputEncryptor);
    }

    public OutputStream open(ASN1ObjectIdentifier aSN1ObjectIdentifier, OutputStream outputStream, OutputEncryptor outputEncryptor) throws CMSException, IOException {
        return this.doOpen(aSN1ObjectIdentifier, outputStream, outputEncryptor);
    }

    private class CmsEnvelopedDataOutputStream
    extends OutputStream {
        private final OutputEncryptor _encryptor;
        private final OutputStream _cOut;
        private OutputStream _octetStream;
        private BERSequenceGenerator _cGen;
        private BERSequenceGenerator _envGen;
        private BERSequenceGenerator _eiGen;

        public CmsEnvelopedDataOutputStream(OutputEncryptor outputEncryptor, OutputStream outputStream, BERSequenceGenerator bERSequenceGenerator, BERSequenceGenerator bERSequenceGenerator2, BERSequenceGenerator bERSequenceGenerator3) {
            this._encryptor = outputEncryptor;
            this._octetStream = outputStream;
            this._cOut = outputEncryptor.getOutputStream(outputStream);
            this._cGen = bERSequenceGenerator;
            this._envGen = bERSequenceGenerator2;
            this._eiGen = bERSequenceGenerator3;
        }

        public void write(int n) throws IOException {
            this._cOut.write(n);
        }

        public void write(byte[] byArray, int n, int n2) throws IOException {
            this._cOut.write(byArray, n, n2);
        }

        public void write(byte[] byArray) throws IOException {
            this._cOut.write(byArray);
        }

        public void close() throws IOException {
            this._cOut.close();
            if (this._encryptor instanceof OutputAEADEncryptor) {
                this._octetStream.write(((OutputAEADEncryptor)this._encryptor).getMAC());
                this._octetStream.close();
            }
            this._eiGen.close();
            if (CMSEnvelopedDataStreamGenerator.this.unprotectedAttributeGenerator != null) {
                AttributeTable attributeTable = CMSEnvelopedDataStreamGenerator.this.unprotectedAttributeGenerator.getAttributes(Collections.EMPTY_MAP);
                BERSet bERSet = new BERSet(attributeTable.toASN1EncodableVector());
                this._envGen.addObject((ASN1Primitive)new DERTaggedObject(false, 1, (ASN1Encodable)bERSet));
            }
            this._envGen.close();
            this._cGen.close();
        }
    }
}

