/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Generator
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1OctetStringParser
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1SequenceParser
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.ASN1SetParser
 *  org.bouncycastle.asn1.ASN1StreamParser
 *  org.bouncycastle.asn1.BERSequenceGenerator
 *  org.bouncycastle.asn1.BERSetParser
 *  org.bouncycastle.asn1.BERTaggedObject
 *  org.bouncycastle.asn1.DERSet
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.cms.CMSObjectIdentifiers
 *  org.bouncycastle.asn1.cms.ContentInfoParser
 *  org.bouncycastle.asn1.cms.SignedDataParser
 *  org.bouncycastle.asn1.cms.SignerInfo
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.util.Store
 *  org.bouncycastle.util.io.Streams
 */
package org.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Generator;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetStringParser;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1SequenceParser;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1SetParser;
import org.bouncycastle.asn1.ASN1StreamParser;
import org.bouncycastle.asn1.BERSequenceGenerator;
import org.bouncycastle.asn1.BERSetParser;
import org.bouncycastle.asn1.BERTaggedObject;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfoParser;
import org.bouncycastle.asn1.cms.SignedDataParser;
import org.bouncycastle.asn1.cms.SignerInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSContentInfoParser;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedHelper;
import org.bouncycastle.cms.CMSTypedStream;
import org.bouncycastle.cms.CMSUtils;
import org.bouncycastle.cms.PKCS7TypedStream;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.io.Streams;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CMSSignedDataParser
extends CMSContentInfoParser {
    private static final CMSSignedHelper HELPER = CMSSignedHelper.INSTANCE;
    private static final DefaultDigestAlgorithmIdentifierFinder dgstAlgFinder = new DefaultDigestAlgorithmIdentifierFinder();
    private SignedDataParser _signedData;
    private ASN1ObjectIdentifier _signedContentType;
    private CMSTypedStream _signedContent;
    private Map digests;
    private Set<AlgorithmIdentifier> digestAlgorithms;
    private SignerInformationStore _signerInfoStore;
    private ASN1Set _certSet;
    private ASN1Set _crlSet;
    private boolean _isCertCrlParsed;

    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, byte[] byArray) throws CMSException {
        this(digestCalculatorProvider, new ByteArrayInputStream(byArray));
    }

    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, CMSTypedStream cMSTypedStream, byte[] byArray) throws CMSException {
        this(digestCalculatorProvider, cMSTypedStream, new ByteArrayInputStream(byArray));
    }

    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, InputStream inputStream) throws CMSException {
        this(digestCalculatorProvider, null, inputStream);
    }

    public CMSSignedDataParser(DigestCalculatorProvider digestCalculatorProvider, CMSTypedStream cMSTypedStream, InputStream inputStream) throws CMSException {
        super(inputStream);
        try {
            DigestCalculator digestCalculator;
            AlgorithmIdentifier algorithmIdentifier;
            ASN1Encodable aSN1Encodable;
            this._signedContent = cMSTypedStream;
            this._signedData = SignedDataParser.getInstance((Object)this._contentInfo.getContent(16));
            this.digests = new HashMap();
            ASN1SetParser aSN1SetParser = this._signedData.getDigestAlgorithms();
            HashSet<AlgorithmIdentifier> hashSet = new HashSet<AlgorithmIdentifier>();
            while ((aSN1Encodable = aSN1SetParser.readObject()) != null) {
                algorithmIdentifier = AlgorithmIdentifier.getInstance((Object)aSN1Encodable);
                hashSet.add(algorithmIdentifier);
                try {
                    digestCalculator = digestCalculatorProvider.get(algorithmIdentifier);
                    if (digestCalculator == null) continue;
                    this.digests.put(algorithmIdentifier.getAlgorithm(), digestCalculator);
                }
                catch (OperatorCreationException operatorCreationException) {}
            }
            this.digestAlgorithms = Collections.unmodifiableSet(hashSet);
            algorithmIdentifier = this._signedData.getEncapContentInfo();
            digestCalculator = algorithmIdentifier.getContent(4);
            if (digestCalculator instanceof ASN1OctetStringParser) {
                ASN1OctetStringParser aSN1OctetStringParser = (ASN1OctetStringParser)digestCalculator;
                CMSTypedStream cMSTypedStream2 = new CMSTypedStream(algorithmIdentifier.getContentType(), aSN1OctetStringParser.getOctetStream());
                if (this._signedContent == null) {
                    this._signedContent = cMSTypedStream2;
                } else {
                    cMSTypedStream2.drain();
                }
            } else if (digestCalculator != null) {
                PKCS7TypedStream pKCS7TypedStream = new PKCS7TypedStream(algorithmIdentifier.getContentType(), (ASN1Encodable)digestCalculator);
                if (this._signedContent == null) {
                    this._signedContent = pKCS7TypedStream;
                } else {
                    pKCS7TypedStream.drain();
                }
            }
            this._signedContentType = cMSTypedStream == null ? algorithmIdentifier.getContentType() : this._signedContent.getContentType();
        }
        catch (IOException iOException) {
            throw new CMSException("io exception: " + iOException.getMessage(), iOException);
        }
    }

    public int getVersion() {
        return this._signedData.getVersion().intValueExact();
    }

    public Set<AlgorithmIdentifier> getDigestAlgorithmIDs() {
        return this.digestAlgorithms;
    }

    public SignerInformationStore getSignerInfos() throws CMSException {
        if (this._signerInfoStore == null) {
            this.populateCertCrlSets();
            ArrayList<SignerInformation> arrayList = new ArrayList<SignerInformation>();
            HashMap hashMap = new HashMap();
            for (Object object : this.digests.keySet()) {
                hashMap.put(object, ((DigestCalculator)this.digests.get(object)).getDigest());
            }
            try {
                ASN1Encodable aSN1Encodable;
                Object object;
                object = this._signedData.getSignerInfos();
                while ((aSN1Encodable = object.readObject()) != null) {
                    SignerInfo signerInfo = SignerInfo.getInstance((Object)aSN1Encodable.toASN1Primitive());
                    byte[] byArray = (byte[])hashMap.get(signerInfo.getDigestAlgorithm().getAlgorithm());
                    arrayList.add(new SignerInformation(signerInfo, this._signedContentType, null, byArray));
                }
            }
            catch (IOException iOException) {
                throw new CMSException("io exception: " + iOException.getMessage(), iOException);
            }
            this._signerInfoStore = new SignerInformationStore(arrayList);
        }
        return this._signerInfoStore;
    }

    public Store getCertificates() throws CMSException {
        this.populateCertCrlSets();
        return HELPER.getCertificates(this._certSet);
    }

    public Store getCRLs() throws CMSException {
        this.populateCertCrlSets();
        return HELPER.getCRLs(this._crlSet);
    }

    public Store getAttributeCertificates() throws CMSException {
        this.populateCertCrlSets();
        return HELPER.getAttributeCertificates(this._certSet);
    }

    public Store getOtherRevocationInfo(ASN1ObjectIdentifier aSN1ObjectIdentifier) throws CMSException {
        this.populateCertCrlSets();
        return HELPER.getOtherRevocationInfo(aSN1ObjectIdentifier, this._crlSet);
    }

    private void populateCertCrlSets() throws CMSException {
        if (this._isCertCrlParsed) {
            return;
        }
        this._isCertCrlParsed = true;
        try {
            this._certSet = CMSSignedDataParser.getASN1Set(this._signedData.getCertificates());
            this._crlSet = CMSSignedDataParser.getASN1Set(this._signedData.getCrls());
        }
        catch (IOException iOException) {
            throw new CMSException("problem parsing cert/crl sets", iOException);
        }
    }

    public String getSignedContentTypeOID() {
        return this._signedContentType.getId();
    }

    public CMSTypedStream getSignedContent() {
        if (this._signedContent == null) {
            return null;
        }
        InputStream inputStream = CMSUtils.attachDigestsToInputStream(this.digests.values(), this._signedContent.getContentStream());
        return new CMSTypedStream(this._signedContent.getContentType(), inputStream);
    }

    public static OutputStream replaceSigners(InputStream inputStream, SignerInformationStore signerInformationStore, OutputStream outputStream) throws CMSException, IOException {
        SignerInformation signerInformation2;
        ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(inputStream);
        ContentInfoParser contentInfoParser = new ContentInfoParser((ASN1SequenceParser)aSN1StreamParser.readObject());
        SignedDataParser signedDataParser = SignedDataParser.getInstance((Object)contentInfoParser.getContent(16));
        BERSequenceGenerator bERSequenceGenerator = new BERSequenceGenerator(outputStream);
        bERSequenceGenerator.addObject((ASN1Primitive)CMSObjectIdentifiers.signedData);
        BERSequenceGenerator bERSequenceGenerator2 = new BERSequenceGenerator(bERSequenceGenerator.getRawOutputStream(), 0, true);
        bERSequenceGenerator2.addObject((ASN1Primitive)signedDataParser.getVersion());
        signedDataParser.getDigestAlgorithms().toASN1Primitive();
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (SignerInformation signerInformation2 : signerInformationStore.getSigners()) {
            aSN1EncodableVector.add((ASN1Encodable)CMSSignedHelper.INSTANCE.fixDigestAlgID(signerInformation2.getDigestAlgorithmID(), dgstAlgFinder));
        }
        bERSequenceGenerator2.getRawOutputStream().write(new DERSet(aSN1EncodableVector).getEncoded());
        ContentInfoParser contentInfoParser2 = signedDataParser.getEncapContentInfo();
        signerInformation2 = new BERSequenceGenerator(bERSequenceGenerator2.getRawOutputStream());
        signerInformation2.addObject((ASN1Primitive)contentInfoParser2.getContentType());
        CMSSignedDataParser.pipeEncapsulatedOctetString(contentInfoParser2, signerInformation2.getRawOutputStream());
        signerInformation2.close();
        CMSSignedDataParser.writeSetToGeneratorTagged((ASN1Generator)bERSequenceGenerator2, signedDataParser.getCertificates(), 0);
        CMSSignedDataParser.writeSetToGeneratorTagged((ASN1Generator)bERSequenceGenerator2, signedDataParser.getCrls(), 1);
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        for (SignerInformation signerInformation3 : signerInformationStore.getSigners()) {
            aSN1EncodableVector2.add((ASN1Encodable)signerInformation3.toASN1Structure());
        }
        bERSequenceGenerator2.getRawOutputStream().write(new DERSet(aSN1EncodableVector2).getEncoded());
        bERSequenceGenerator2.close();
        bERSequenceGenerator.close();
        return outputStream;
    }

    public static OutputStream replaceCertificatesAndCRLs(InputStream inputStream, Store store, Store store2, Store store3, OutputStream outputStream) throws CMSException, IOException {
        ASN1Set aSN1Set;
        ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(inputStream);
        ContentInfoParser contentInfoParser = new ContentInfoParser((ASN1SequenceParser)aSN1StreamParser.readObject());
        SignedDataParser signedDataParser = SignedDataParser.getInstance((Object)contentInfoParser.getContent(16));
        BERSequenceGenerator bERSequenceGenerator = new BERSequenceGenerator(outputStream);
        bERSequenceGenerator.addObject((ASN1Primitive)CMSObjectIdentifiers.signedData);
        BERSequenceGenerator bERSequenceGenerator2 = new BERSequenceGenerator(bERSequenceGenerator.getRawOutputStream(), 0, true);
        bERSequenceGenerator2.addObject((ASN1Primitive)signedDataParser.getVersion());
        bERSequenceGenerator2.getRawOutputStream().write(signedDataParser.getDigestAlgorithms().toASN1Primitive().getEncoded());
        ContentInfoParser contentInfoParser2 = signedDataParser.getEncapContentInfo();
        BERSequenceGenerator bERSequenceGenerator3 = new BERSequenceGenerator(bERSequenceGenerator2.getRawOutputStream());
        bERSequenceGenerator3.addObject((ASN1Primitive)contentInfoParser2.getContentType());
        CMSSignedDataParser.pipeEncapsulatedOctetString(contentInfoParser2, bERSequenceGenerator3.getRawOutputStream());
        bERSequenceGenerator3.close();
        CMSSignedDataParser.getASN1Set(signedDataParser.getCertificates());
        CMSSignedDataParser.getASN1Set(signedDataParser.getCrls());
        if (store != null || store3 != null) {
            ASN1Set aSN1Set2;
            aSN1Set = new ArrayList();
            if (store != null) {
                aSN1Set.addAll(CMSUtils.getCertificatesFromStore(store));
            }
            if (store3 != null) {
                aSN1Set.addAll(CMSUtils.getAttributeCertificatesFromStore(store3));
            }
            if ((aSN1Set2 = CMSUtils.createBerSetFromList((List)aSN1Set)).size() > 0) {
                bERSequenceGenerator2.getRawOutputStream().write(new DERTaggedObject(false, 0, (ASN1Encodable)aSN1Set2).getEncoded());
            }
        }
        if (store2 != null && (aSN1Set = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(store2))).size() > 0) {
            bERSequenceGenerator2.getRawOutputStream().write(new DERTaggedObject(false, 1, (ASN1Encodable)aSN1Set).getEncoded());
        }
        bERSequenceGenerator2.getRawOutputStream().write(signedDataParser.getSignerInfos().toASN1Primitive().getEncoded());
        bERSequenceGenerator2.close();
        bERSequenceGenerator.close();
        return outputStream;
    }

    private static void writeSetToGeneratorTagged(ASN1Generator aSN1Generator, ASN1SetParser aSN1SetParser, int n) throws IOException {
        ASN1Set aSN1Set = CMSSignedDataParser.getASN1Set(aSN1SetParser);
        if (aSN1Set != null) {
            if (aSN1SetParser instanceof BERSetParser) {
                aSN1Generator.getRawOutputStream().write(new BERTaggedObject(false, n, (ASN1Encodable)aSN1Set).getEncoded());
            } else {
                aSN1Generator.getRawOutputStream().write(new DERTaggedObject(false, n, (ASN1Encodable)aSN1Set).getEncoded());
            }
        }
    }

    private static ASN1Set getASN1Set(ASN1SetParser aSN1SetParser) {
        return aSN1SetParser == null ? null : ASN1Set.getInstance((Object)aSN1SetParser.toASN1Primitive());
    }

    private static void pipeEncapsulatedOctetString(ContentInfoParser contentInfoParser, OutputStream outputStream) throws IOException {
        ASN1OctetStringParser aSN1OctetStringParser = (ASN1OctetStringParser)contentInfoParser.getContent(4);
        if (aSN1OctetStringParser != null) {
            CMSSignedDataParser.pipeOctetString(aSN1OctetStringParser, outputStream);
        }
    }

    private static void pipeOctetString(ASN1OctetStringParser aSN1OctetStringParser, OutputStream outputStream) throws IOException {
        OutputStream outputStream2 = CMSUtils.createBEROctetOutputStream(outputStream, 0, true, 0);
        Streams.pipeAll((InputStream)aSN1OctetStringParser.getOctetStream(), (OutputStream)outputStream2);
        outputStream2.close();
    }
}

