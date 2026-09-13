/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Boolean
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1GeneralizedTime
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.cms.AttributeTable
 *  org.bouncycastle.asn1.ess.ESSCertID
 *  org.bouncycastle.asn1.ess.ESSCertIDv2
 *  org.bouncycastle.asn1.ess.SigningCertificate
 *  org.bouncycastle.asn1.ess.SigningCertificateV2
 *  org.bouncycastle.asn1.oiw.OIWObjectIdentifiers
 *  org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
 *  org.bouncycastle.asn1.tsp.Accuracy
 *  org.bouncycastle.asn1.tsp.MessageImprint
 *  org.bouncycastle.asn1.tsp.TSTInfo
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.asn1.x509.Extensions
 *  org.bouncycastle.asn1.x509.ExtensionsGenerator
 *  org.bouncycastle.asn1.x509.GeneralName
 *  org.bouncycastle.asn1.x509.GeneralNames
 *  org.bouncycastle.asn1.x509.IssuerSerial
 *  org.bouncycastle.util.CollectionStore
 *  org.bouncycastle.util.Store
 */
package org.bouncycastle.tsp;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1GeneralizedTime;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.ess.ESSCertID;
import org.bouncycastle.asn1.ess.ESSCertIDv2;
import org.bouncycastle.asn1.ess.SigningCertificate;
import org.bouncycastle.asn1.ess.SigningCertificateV2;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.tsp.Accuracy;
import org.bouncycastle.asn1.tsp.MessageImprint;
import org.bouncycastle.asn1.tsp.TSTInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.IssuerSerial;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSAttributeTableGenerationException;
import org.bouncycastle.cms.CMSAttributeTableGenerator;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.tsp.TSPException;
import org.bouncycastle.tsp.TSPUtil;
import org.bouncycastle.tsp.TimeStampRequest;
import org.bouncycastle.tsp.TimeStampToken;
import org.bouncycastle.util.CollectionStore;
import org.bouncycastle.util.Store;

public class TimeStampTokenGenerator {
    public static final int R_SECONDS = 0;
    public static final int R_TENTHS_OF_SECONDS = 1;
    public static final int R_HUNDREDTHS_OF_SECONDS = 2;
    public static final int R_MICROSECONDS = 2;
    public static final int R_MILLISECONDS = 3;
    private int resolution = 0;
    private Locale locale = null;
    private int accuracySeconds = -1;
    private int accuracyMillis = -1;
    private int accuracyMicros = -1;
    boolean ordering = false;
    GeneralName tsa = null;
    private ASN1ObjectIdentifier tsaPolicyOID;
    private List certs = new ArrayList();
    private List crls = new ArrayList();
    private List attrCerts = new ArrayList();
    private Map otherRevoc = new HashMap();
    private SignerInfoGenerator signerInfoGen;

    public TimeStampTokenGenerator(SignerInfoGenerator signerInfoGenerator, DigestCalculator digestCalculator, ASN1ObjectIdentifier aSN1ObjectIdentifier) throws IllegalArgumentException, TSPException {
        this(signerInfoGenerator, digestCalculator, aSN1ObjectIdentifier, false);
    }

    public TimeStampTokenGenerator(final SignerInfoGenerator signerInfoGenerator, DigestCalculator digestCalculator, ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl) throws IllegalArgumentException, TSPException {
        this.signerInfoGen = signerInfoGenerator;
        this.tsaPolicyOID = aSN1ObjectIdentifier;
        if (!signerInfoGenerator.hasAssociatedCertificate()) {
            throw new IllegalArgumentException("SignerInfoGenerator must have an associated certificate");
        }
        X509CertificateHolder x509CertificateHolder = signerInfoGenerator.getAssociatedCertificate();
        TSPUtil.validateCertificate(x509CertificateHolder);
        try {
            OutputStream outputStream = digestCalculator.getOutputStream();
            outputStream.write(x509CertificateHolder.getEncoded());
            outputStream.close();
            if (digestCalculator.getAlgorithmIdentifier().getAlgorithm().equals((ASN1Primitive)OIWObjectIdentifiers.idSHA1)) {
                final ESSCertID eSSCertID = new ESSCertID(digestCalculator.getDigest(), bl ? new IssuerSerial(new GeneralNames(new GeneralName(x509CertificateHolder.getIssuer())), x509CertificateHolder.getSerialNumber()) : null);
                this.signerInfoGen = new SignerInfoGenerator(signerInfoGenerator, new CMSAttributeTableGenerator(){

                    public AttributeTable getAttributes(Map map) throws CMSAttributeTableGenerationException {
                        AttributeTable attributeTable = signerInfoGenerator.getSignedAttributeTableGenerator().getAttributes(map);
                        if (attributeTable.get(PKCSObjectIdentifiers.id_aa_signingCertificate) == null) {
                            return attributeTable.add(PKCSObjectIdentifiers.id_aa_signingCertificate, (ASN1Encodable)new SigningCertificate(eSSCertID));
                        }
                        return attributeTable;
                    }
                }, signerInfoGenerator.getUnsignedAttributeTableGenerator());
            } else {
                AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(digestCalculator.getAlgorithmIdentifier().getAlgorithm());
                final ESSCertIDv2 eSSCertIDv2 = new ESSCertIDv2(algorithmIdentifier, digestCalculator.getDigest(), bl ? new IssuerSerial(new GeneralNames(new GeneralName(x509CertificateHolder.getIssuer())), new ASN1Integer(x509CertificateHolder.getSerialNumber())) : null);
                this.signerInfoGen = new SignerInfoGenerator(signerInfoGenerator, new CMSAttributeTableGenerator(){

                    public AttributeTable getAttributes(Map map) throws CMSAttributeTableGenerationException {
                        AttributeTable attributeTable = signerInfoGenerator.getSignedAttributeTableGenerator().getAttributes(map);
                        if (attributeTable.get(PKCSObjectIdentifiers.id_aa_signingCertificateV2) == null) {
                            return attributeTable.add(PKCSObjectIdentifiers.id_aa_signingCertificateV2, (ASN1Encodable)new SigningCertificateV2(eSSCertIDv2));
                        }
                        return attributeTable;
                    }
                }, signerInfoGenerator.getUnsignedAttributeTableGenerator());
            }
        }
        catch (IOException iOException) {
            throw new TSPException("Exception processing certificate.", iOException);
        }
    }

    public void addCertificates(Store store) {
        this.certs.addAll(store.getMatches(null));
    }

    public void addCRLs(Store store) {
        this.crls.addAll(store.getMatches(null));
    }

    public void addAttributeCertificates(Store store) {
        this.attrCerts.addAll(store.getMatches(null));
    }

    public void addOtherRevocationInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier, Store store) {
        this.otherRevoc.put(aSN1ObjectIdentifier, store.getMatches(null));
    }

    public void setResolution(int n) {
        this.resolution = n;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setAccuracySeconds(int n) {
        this.accuracySeconds = n;
    }

    public void setAccuracyMillis(int n) {
        this.accuracyMillis = n;
    }

    public void setAccuracyMicros(int n) {
        this.accuracyMicros = n;
    }

    public void setOrdering(boolean bl) {
        this.ordering = bl;
    }

    public void setTSA(GeneralName generalName) {
        this.tsa = generalName;
    }

    public TimeStampToken generate(TimeStampRequest timeStampRequest, BigInteger bigInteger, Date date) throws TSPException {
        return this.generate(timeStampRequest, bigInteger, date, null);
    }

    public TimeStampToken generate(TimeStampRequest timeStampRequest, BigInteger bigInteger, Date date, Extensions extensions) throws TSPException {
        Enumeration enumeration;
        ExtensionsGenerator extensionsGenerator;
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        ASN1Integer aSN1Integer;
        ASN1Integer aSN1Integer2;
        AlgorithmIdentifier algorithmIdentifier = timeStampRequest.getMessageImprintAlgID();
        MessageImprint messageImprint = new MessageImprint(algorithmIdentifier, timeStampRequest.getMessageImprintDigest());
        Accuracy accuracy = null;
        if (this.accuracySeconds > 0 || this.accuracyMillis > 0 || this.accuracyMicros > 0) {
            aSN1Integer2 = null;
            if (this.accuracySeconds > 0) {
                aSN1Integer2 = new ASN1Integer((long)this.accuracySeconds);
            }
            aSN1Integer = null;
            if (this.accuracyMillis > 0) {
                aSN1Integer = new ASN1Integer((long)this.accuracyMillis);
            }
            aSN1ObjectIdentifier = null;
            if (this.accuracyMicros > 0) {
                aSN1ObjectIdentifier = new ASN1Integer((long)this.accuracyMicros);
            }
            accuracy = new Accuracy(aSN1Integer2, aSN1Integer, (ASN1Integer)aSN1ObjectIdentifier);
        }
        aSN1Integer2 = null;
        if (this.ordering) {
            aSN1Integer2 = ASN1Boolean.getInstance((boolean)this.ordering);
        }
        aSN1Integer = null;
        if (timeStampRequest.getNonce() != null) {
            aSN1Integer = new ASN1Integer(timeStampRequest.getNonce());
        }
        aSN1ObjectIdentifier = this.tsaPolicyOID;
        if (timeStampRequest.getReqPolicy() != null) {
            aSN1ObjectIdentifier = timeStampRequest.getReqPolicy();
        }
        Extensions extensions2 = timeStampRequest.getExtensions();
        if (extensions != null) {
            extensionsGenerator = new ExtensionsGenerator();
            if (extensions2 != null) {
                enumeration = extensions2.oids();
                while (enumeration.hasMoreElements()) {
                    extensionsGenerator.addExtension(extensions2.getExtension(ASN1ObjectIdentifier.getInstance(enumeration.nextElement())));
                }
            }
            enumeration = extensions.oids();
            while (enumeration.hasMoreElements()) {
                extensionsGenerator.addExtension(extensions.getExtension(ASN1ObjectIdentifier.getInstance(enumeration.nextElement())));
            }
            extensions2 = extensionsGenerator.generate();
        }
        extensionsGenerator = this.resolution == 0 ? (this.locale == null ? new ASN1GeneralizedTime(date) : new ASN1GeneralizedTime(date, this.locale)) : this.createGeneralizedTime(date);
        enumeration = new TSTInfo(aSN1ObjectIdentifier, messageImprint, new ASN1Integer(bigInteger), (ASN1GeneralizedTime)extensionsGenerator, accuracy, (ASN1Boolean)aSN1Integer2, aSN1Integer, this.tsa, extensions2);
        try {
            Object object2;
            CMSSignedDataGenerator cMSSignedDataGenerator = new CMSSignedDataGenerator();
            if (timeStampRequest.getCertReq()) {
                cMSSignedDataGenerator.addCertificates((Store)new CollectionStore((Collection)this.certs));
                cMSSignedDataGenerator.addAttributeCertificates((Store)new CollectionStore((Collection)this.attrCerts));
            }
            cMSSignedDataGenerator.addCRLs((Store)new CollectionStore((Collection)this.crls));
            if (!this.otherRevoc.isEmpty()) {
                for (Object object2 : this.otherRevoc.keySet()) {
                    cMSSignedDataGenerator.addOtherRevocationInfo((ASN1ObjectIdentifier)object2, (Store)new CollectionStore((Collection)this.otherRevoc.get(object2)));
                }
            }
            cMSSignedDataGenerator.addSignerInfoGenerator(this.signerInfoGen);
            Object object3 = enumeration.getEncoded("DER");
            object2 = cMSSignedDataGenerator.generate(new CMSProcessableByteArray(PKCSObjectIdentifiers.id_ct_TSTInfo, (byte[])object3), true);
            return new TimeStampToken((CMSSignedData)object2);
        }
        catch (CMSException cMSException) {
            throw new TSPException("Error generating time-stamp token", cMSException);
        }
        catch (IOException iOException) {
            throw new TSPException("Exception encoding info", iOException);
        }
    }

    private ASN1GeneralizedTime createGeneralizedTime(Date date) throws TSPException {
        String string = "yyyyMMddHHmmss.SSS";
        SimpleDateFormat simpleDateFormat = this.locale == null ? new SimpleDateFormat(string) : new SimpleDateFormat(string, this.locale);
        simpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        StringBuilder stringBuilder = new StringBuilder(simpleDateFormat.format(date));
        int n = stringBuilder.indexOf(".");
        if (n < 0) {
            stringBuilder.append("Z");
            return new ASN1GeneralizedTime(stringBuilder.toString());
        }
        switch (this.resolution) {
            case 1: {
                if (stringBuilder.length() <= n + 2) break;
                stringBuilder.delete(n + 2, stringBuilder.length());
                break;
            }
            case 2: {
                if (stringBuilder.length() <= n + 3) break;
                stringBuilder.delete(n + 3, stringBuilder.length());
                break;
            }
            case 3: {
                break;
            }
            default: {
                throw new TSPException("unknown time-stamp resolution: " + this.resolution);
            }
        }
        while (stringBuilder.charAt(stringBuilder.length() - 1) == '0') {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        if (stringBuilder.length() - 1 == n) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        stringBuilder.append("Z");
        return new ASN1GeneralizedTime(stringBuilder.toString());
    }
}

