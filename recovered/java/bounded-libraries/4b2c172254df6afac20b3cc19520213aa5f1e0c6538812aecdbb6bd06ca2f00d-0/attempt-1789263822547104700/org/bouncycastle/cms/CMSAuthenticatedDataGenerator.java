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
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSet
 *  org.bouncycastle.asn1.cms.AuthenticatedData
 *  org.bouncycastle.asn1.cms.CMSObjectIdentifiers
 *  org.bouncycastle.asn1.cms.ContentInfo
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.util.io.TeeOutputStream
 */
package org.bouncycastle.cms;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.BEROctetString;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.cms.AuthenticatedData;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSAuthenticatedData;
import org.bouncycastle.cms.CMSAuthenticatedGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.DefaultAuthenticatedAttributeTableGenerator;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.io.TeeOutputStream;

public class CMSAuthenticatedDataGenerator
extends CMSAuthenticatedGenerator {
    public CMSAuthenticatedData generate(CMSTypedData cMSTypedData, MacCalculator macCalculator) throws CMSException {
        return this.generate(cMSTypedData, macCalculator, null);
    }

    public CMSAuthenticatedData generate(CMSTypedData cMSTypedData, MacCalculator macCalculator, final DigestCalculator digestCalculator) throws CMSException {
        AuthenticatedData authenticatedData;
        ContentInfo contentInfo2;
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (ContentInfo contentInfo2 : this.recipientInfoGenerators) {
            aSN1EncodableVector.add((ASN1Encodable)contentInfo2.generate(macCalculator.getKey()));
        }
        if (digestCalculator != null) {
            DEROctetString dEROctetString;
            OutputStream outputStream;
            BEROctetString bEROctetString;
            TeeOutputStream teeOutputStream;
            try {
                contentInfo2 = new ByteArrayOutputStream();
                teeOutputStream = new TeeOutputStream(digestCalculator.getOutputStream(), (OutputStream)contentInfo2);
                cMSTypedData.write((OutputStream)teeOutputStream);
                teeOutputStream.close();
                bEROctetString = new BEROctetString(contentInfo2.toByteArray());
            }
            catch (IOException iOException) {
                throw new CMSException("unable to perform digest calculation: " + iOException.getMessage(), iOException);
            }
            contentInfo2 = Collections.unmodifiableMap(this.getBaseParameters(cMSTypedData.getContentType(), digestCalculator.getAlgorithmIdentifier(), macCalculator.getAlgorithmIdentifier(), digestCalculator.getDigest()));
            if (this.authGen == null) {
                this.authGen = new DefaultAuthenticatedAttributeTableGenerator();
            }
            teeOutputStream = new DERSet(this.authGen.getAttributes((Map)contentInfo2).toASN1EncodableVector());
            try {
                outputStream = macCalculator.getOutputStream();
                outputStream.write(teeOutputStream.getEncoded("DER"));
                outputStream.close();
                dEROctetString = new DEROctetString(macCalculator.getMac());
            }
            catch (IOException iOException) {
                throw new CMSException("unable to perform MAC calculation: " + iOException.getMessage(), iOException);
            }
            outputStream = this.unauthGen != null ? new BERSet(this.unauthGen.getAttributes((Map)contentInfo2).toASN1EncodableVector()) : null;
            ContentInfo contentInfo3 = new ContentInfo(cMSTypedData.getContentType(), (ASN1Encodable)bEROctetString);
            authenticatedData = new AuthenticatedData(this.originatorInfo, (ASN1Set)new DERSet(aSN1EncodableVector), macCalculator.getAlgorithmIdentifier(), digestCalculator.getAlgorithmIdentifier(), contentInfo3, (ASN1Set)teeOutputStream, (ASN1OctetString)dEROctetString, (ASN1Set)outputStream);
        } else {
            DEROctetString dEROctetString;
            BEROctetString bEROctetString;
            TeeOutputStream teeOutputStream;
            try {
                contentInfo2 = new ByteArrayOutputStream();
                teeOutputStream = new TeeOutputStream((OutputStream)contentInfo2, macCalculator.getOutputStream());
                cMSTypedData.write((OutputStream)teeOutputStream);
                teeOutputStream.close();
                bEROctetString = new BEROctetString(contentInfo2.toByteArray());
                dEROctetString = new DEROctetString(macCalculator.getMac());
            }
            catch (IOException iOException) {
                throw new CMSException("unable to perform MAC calculation: " + iOException.getMessage(), iOException);
            }
            contentInfo2 = this.unauthGen != null ? new BERSet(this.unauthGen.getAttributes(Collections.EMPTY_MAP).toASN1EncodableVector()) : null;
            teeOutputStream = new ContentInfo(cMSTypedData.getContentType(), (ASN1Encodable)bEROctetString);
            authenticatedData = new AuthenticatedData(this.originatorInfo, (ASN1Set)new DERSet(aSN1EncodableVector), macCalculator.getAlgorithmIdentifier(), null, (ContentInfo)teeOutputStream, null, (ASN1OctetString)dEROctetString, contentInfo2);
        }
        contentInfo2 = new ContentInfo(CMSObjectIdentifiers.authenticatedData, (ASN1Encodable)authenticatedData);
        return new CMSAuthenticatedData(contentInfo2, new DigestCalculatorProvider(){

            public DigestCalculator get(AlgorithmIdentifier algorithmIdentifier) throws OperatorCreationException {
                return digestCalculator;
            }
        });
    }
}

