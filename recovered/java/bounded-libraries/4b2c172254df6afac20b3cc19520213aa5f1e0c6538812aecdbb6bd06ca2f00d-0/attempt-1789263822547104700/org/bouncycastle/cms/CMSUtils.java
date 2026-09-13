/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1InputStream
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Set
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.BEROctetStringGenerator
 *  org.bouncycastle.asn1.BERSet
 *  org.bouncycastle.asn1.DERNull
 *  org.bouncycastle.asn1.DERSet
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.DLSet
 *  org.bouncycastle.asn1.cms.CMSObjectIdentifiers
 *  org.bouncycastle.asn1.cms.ContentInfo
 *  org.bouncycastle.asn1.cms.OtherRevocationInfoFormat
 *  org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers
 *  org.bouncycastle.asn1.ocsp.OCSPResponse
 *  org.bouncycastle.asn1.oiw.OIWObjectIdentifiers
 *  org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers
 *  org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers
 *  org.bouncycastle.asn1.sec.SECObjectIdentifiers
 *  org.bouncycastle.asn1.x509.AlgorithmIdentifier
 *  org.bouncycastle.asn1.x509.Certificate
 *  org.bouncycastle.asn1.x9.X9ObjectIdentifiers
 *  org.bouncycastle.util.Store
 *  org.bouncycastle.util.Strings
 *  org.bouncycastle.util.io.Streams
 *  org.bouncycastle.util.io.TeeInputStream
 *  org.bouncycastle.util.io.TeeOutputStream
 */
package org.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.BEROctetStringGenerator;
import org.bouncycastle.asn1.BERSet;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DERSet;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DLSet;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cms.OtherRevocationInfoFormat;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.ocsp.OCSPResponse;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedHelper;
import org.bouncycastle.cms.NullOutputStream;
import org.bouncycastle.cms.SignerInfoGenerator;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.operator.DigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.Store;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.Streams;
import org.bouncycastle.util.io.TeeInputStream;
import org.bouncycastle.util.io.TeeOutputStream;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
class CMSUtils {
    private static final Set<String> des = new HashSet<String>();
    private static final Set mqvAlgs = new HashSet();
    private static final Set ecAlgs = new HashSet();
    private static final Set gostAlgs = new HashSet();

    CMSUtils() {
    }

    static boolean isMQV(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return mqvAlgs.contains(aSN1ObjectIdentifier);
    }

    static boolean isEC(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return ecAlgs.contains(aSN1ObjectIdentifier);
    }

    static boolean isGOST(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return gostAlgs.contains(aSN1ObjectIdentifier);
    }

    static boolean isRFC2631(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return aSN1ObjectIdentifier.equals((ASN1Primitive)PKCSObjectIdentifiers.id_alg_ESDH) || aSN1ObjectIdentifier.equals((ASN1Primitive)PKCSObjectIdentifiers.id_alg_SSDH);
    }

    static boolean isDES(String string) {
        String string2 = Strings.toUpperCase((String)string);
        return des.contains(string2);
    }

    static boolean isEquivalent(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2) {
        if (algorithmIdentifier == null || algorithmIdentifier2 == null) {
            return false;
        }
        if (!algorithmIdentifier.getAlgorithm().equals((ASN1Primitive)algorithmIdentifier2.getAlgorithm())) {
            return false;
        }
        ASN1Encodable aSN1Encodable = algorithmIdentifier.getParameters();
        ASN1Encodable aSN1Encodable2 = algorithmIdentifier2.getParameters();
        if (aSN1Encodable != null) {
            return aSN1Encodable.equals(aSN1Encodable2) || aSN1Encodable.equals(DERNull.INSTANCE) && aSN1Encodable2 == null;
        }
        return aSN1Encodable2 == null || aSN1Encodable2.equals(DERNull.INSTANCE);
    }

    static ContentInfo readContentInfo(byte[] byArray) throws CMSException {
        return CMSUtils.readContentInfo(new ASN1InputStream(byArray));
    }

    static ContentInfo readContentInfo(InputStream inputStream) throws CMSException {
        return CMSUtils.readContentInfo(new ASN1InputStream(inputStream));
    }

    static ASN1Set convertToBERSet(Set<AlgorithmIdentifier> set) {
        return new DLSet((ASN1Encodable[])set.toArray(new AlgorithmIdentifier[set.size()]));
    }

    static void addDigestAlgs(Set<AlgorithmIdentifier> set, SignerInformation signerInformation, DigestAlgorithmIdentifierFinder digestAlgorithmIdentifierFinder) {
        set.add(CMSSignedHelper.INSTANCE.fixDigestAlgID(signerInformation.getDigestAlgorithmID(), digestAlgorithmIdentifierFinder));
        SignerInformationStore signerInformationStore = signerInformation.getCounterSignatures();
        Iterator<SignerInformation> iterator = signerInformationStore.iterator();
        while (iterator.hasNext()) {
            SignerInformation signerInformation2 = iterator.next();
            set.add(CMSSignedHelper.INSTANCE.fixDigestAlgID(signerInformation2.getDigestAlgorithmID(), digestAlgorithmIdentifierFinder));
        }
    }

    static List getCertificatesFromStore(Store store) throws CMSException {
        ArrayList<Certificate> arrayList = new ArrayList<Certificate>();
        try {
            for (X509CertificateHolder x509CertificateHolder : store.getMatches(null)) {
                arrayList.add(x509CertificateHolder.toASN1Structure());
            }
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            throw new CMSException("error processing certs", classCastException);
        }
    }

    static List getAttributeCertificatesFromStore(Store store) throws CMSException {
        ArrayList<DERTaggedObject> arrayList = new ArrayList<DERTaggedObject>();
        try {
            for (X509AttributeCertificateHolder x509AttributeCertificateHolder : store.getMatches(null)) {
                arrayList.add(new DERTaggedObject(false, 2, (ASN1Encodable)x509AttributeCertificateHolder.toASN1Structure()));
            }
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            throw new CMSException("error processing certs", classCastException);
        }
    }

    static List getCRLsFromStore(Store store) throws CMSException {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        try {
            for (Object e : store.getMatches(null)) {
                X509CRLHolder x509CRLHolder;
                if (e instanceof X509CRLHolder) {
                    x509CRLHolder = (X509CRLHolder)e;
                    arrayList.add(x509CRLHolder.toASN1Structure());
                    continue;
                }
                if (e instanceof OtherRevocationInfoFormat) {
                    x509CRLHolder = OtherRevocationInfoFormat.getInstance(e);
                    CMSUtils.validateInfoFormat((OtherRevocationInfoFormat)x509CRLHolder);
                    arrayList.add(new DERTaggedObject(false, 1, (ASN1Encodable)x509CRLHolder));
                    continue;
                }
                if (!(e instanceof ASN1TaggedObject)) continue;
                arrayList.add(e);
            }
            return arrayList;
        }
        catch (ClassCastException classCastException) {
            throw new CMSException("error processing certs", classCastException);
        }
    }

    private static void validateInfoFormat(OtherRevocationInfoFormat otherRevocationInfoFormat) {
        OCSPResponse oCSPResponse;
        if (CMSObjectIdentifiers.id_ri_ocsp_response.equals((ASN1Primitive)otherRevocationInfoFormat.getInfoFormat()) && 0 != (oCSPResponse = OCSPResponse.getInstance((Object)otherRevocationInfoFormat.getInfo())).getResponseStatus().getIntValue()) {
            throw new IllegalArgumentException("cannot add unsuccessful OCSP response to CMS SignedData");
        }
    }

    static Collection getOthersFromStore(ASN1ObjectIdentifier aSN1ObjectIdentifier, Store store) {
        ArrayList<DERTaggedObject> arrayList = new ArrayList<DERTaggedObject>();
        for (ASN1Encodable aSN1Encodable : store.getMatches(null)) {
            OtherRevocationInfoFormat otherRevocationInfoFormat = new OtherRevocationInfoFormat(aSN1ObjectIdentifier, aSN1Encodable);
            CMSUtils.validateInfoFormat(otherRevocationInfoFormat);
            arrayList.add(new DERTaggedObject(false, 1, (ASN1Encodable)otherRevocationInfoFormat));
        }
        return arrayList;
    }

    static ASN1Set createBerSetFromList(List list) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            aSN1EncodableVector.add((ASN1Encodable)iterator.next());
        }
        return new BERSet(aSN1EncodableVector);
    }

    static ASN1Set createDerSetFromList(List list) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            aSN1EncodableVector.add((ASN1Encodable)iterator.next());
        }
        return new DERSet(aSN1EncodableVector);
    }

    static OutputStream createBEROctetOutputStream(OutputStream outputStream, int n, boolean bl, int n2) throws IOException {
        BEROctetStringGenerator bEROctetStringGenerator = new BEROctetStringGenerator(outputStream, n, bl);
        if (n2 != 0) {
            return bEROctetStringGenerator.getOctetOutputStream(new byte[n2]);
        }
        return bEROctetStringGenerator.getOctetOutputStream();
    }

    private static ContentInfo readContentInfo(ASN1InputStream aSN1InputStream) throws CMSException {
        try {
            ContentInfo contentInfo = ContentInfo.getInstance((Object)aSN1InputStream.readObject());
            if (contentInfo == null) {
                throw new CMSException("No content found.");
            }
            return contentInfo;
        }
        catch (IOException iOException) {
            throw new CMSException("IOException reading content.", iOException);
        }
        catch (ClassCastException classCastException) {
            throw new CMSException("Malformed content.", classCastException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CMSException("Malformed content.", illegalArgumentException);
        }
    }

    public static byte[] streamToByteArray(InputStream inputStream) throws IOException {
        return Streams.readAll((InputStream)inputStream);
    }

    public static byte[] streamToByteArray(InputStream inputStream, int n) throws IOException {
        return Streams.readAllLimited((InputStream)inputStream, (int)n);
    }

    static InputStream attachDigestsToInputStream(Collection collection, InputStream inputStream) {
        InputStream inputStream2 = inputStream;
        for (DigestCalculator digestCalculator : collection) {
            inputStream2 = new TeeInputStream(inputStream2, digestCalculator.getOutputStream());
        }
        return inputStream2;
    }

    static OutputStream attachSignersToOutputStream(Collection collection, OutputStream outputStream) {
        OutputStream outputStream2 = outputStream;
        for (SignerInfoGenerator signerInfoGenerator : collection) {
            outputStream2 = CMSUtils.getSafeTeeOutputStream(outputStream2, signerInfoGenerator.getCalculatingOutputStream());
        }
        return outputStream2;
    }

    static OutputStream getSafeOutputStream(OutputStream outputStream) {
        return outputStream == null ? new NullOutputStream() : outputStream;
    }

    static OutputStream getSafeTeeOutputStream(OutputStream outputStream, OutputStream outputStream2) {
        return outputStream == null ? CMSUtils.getSafeOutputStream(outputStream2) : (outputStream2 == null ? CMSUtils.getSafeOutputStream(outputStream) : new TeeOutputStream(outputStream, outputStream2));
    }

    static {
        des.add("DES");
        des.add("DESEDE");
        des.add(OIWObjectIdentifiers.desCBC.getId());
        des.add(PKCSObjectIdentifiers.des_EDE3_CBC.getId());
        des.add(PKCSObjectIdentifiers.id_alg_CMS3DESwrap.getId());
        mqvAlgs.add(X9ObjectIdentifiers.mqvSinglePass_sha1kdf_scheme);
        mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha224kdf_scheme);
        mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha256kdf_scheme);
        mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha384kdf_scheme);
        mqvAlgs.add(SECObjectIdentifiers.mqvSinglePass_sha512kdf_scheme);
        ecAlgs.add(X9ObjectIdentifiers.dhSinglePass_cofactorDH_sha1kdf_scheme);
        ecAlgs.add(X9ObjectIdentifiers.dhSinglePass_stdDH_sha1kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha224kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha224kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha256kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha256kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha384kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha384kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_cofactorDH_sha512kdf_scheme);
        ecAlgs.add(SECObjectIdentifiers.dhSinglePass_stdDH_sha512kdf_scheme);
        gostAlgs.add(CryptoProObjectIdentifiers.gostR3410_2001_CryptoPro_ESDH);
        gostAlgs.add(RosstandartObjectIdentifiers.id_tc26_agreement_gost_3410_12_256);
        gostAlgs.add(RosstandartObjectIdentifiers.id_tc26_agreement_gost_3410_12_512);
    }
}

