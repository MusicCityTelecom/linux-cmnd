/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.DERBitString
 */
package org.bouncycastle.oer.its.template;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.oer.OERDefinition;
import org.bouncycastle.oer.its.template.Ieee1609Dot2BaseTypes;

public class IEEE1609dot2 {
    public static final OERDefinition.Builder PduFunctionalType = OERDefinition.integer(0L, 255L);
    public static final OERDefinition.Builder HashedData = OERDefinition.choice(OERDefinition.octets(32).label("sha256HashedData"), OERDefinition.extension(), OERDefinition.octets(48).label("sha384HashedData"), OERDefinition.octets(32).label("reserved"));
    public static final OERDefinition.Builder MissingCrlIdentifier = OERDefinition.seq(Ieee1609Dot2BaseTypes.HashedId3.label("cracaId"), Ieee1609Dot2BaseTypes.CrlSeries.label("crlSeries"), OERDefinition.extension());
    public static final OERDefinition.Builder HeaderInfoContributorId = OERDefinition.integer(0L, 255L);
    public static final OERDefinition.Builder EtsiOriginatingHeaderInfoExtension = OERDefinition.seq(HeaderInfoContributorId.label("id"), OERDefinition.extension());
    public static final OERDefinition.Builder ContributedExtensionBlock = OERDefinition.seq(HeaderInfoContributorId, OERDefinition.seqof(EtsiOriginatingHeaderInfoExtension));
    public static final OERDefinition.Builder PreSharedKeyRecipientInfo = Ieee1609Dot2BaseTypes.HashedId8;
    public static final OERDefinition.Builder EncryptedDataEncryptionKey = OERDefinition.choice(Ieee1609Dot2BaseTypes.EciesP256EncryptedKey.label("eciesNistP256"), Ieee1609Dot2BaseTypes.EciesP256EncryptedKey.label("eciesBrainpoolP256r1"), OERDefinition.extension());
    public static final OERDefinition.Builder PKRecipientInfo = OERDefinition.seq(Ieee1609Dot2BaseTypes.HashedId8.label("recipientId"), EncryptedDataEncryptionKey.label("encKey"));
    public static final OERDefinition.Builder AesCcmCiphertext = OERDefinition.seq(OERDefinition.octets(12).label("nonce"), OERDefinition.opaque().label("ccmCiphertext"));
    public static final OERDefinition.Builder SymmetricCiphertext = OERDefinition.choice(AesCcmCiphertext.label("aes128ccm"), OERDefinition.extension());
    public static final OERDefinition.Builder SymmRecipientInfo = OERDefinition.seq(Ieee1609Dot2BaseTypes.HashedId8.label("recipientId"), SymmetricCiphertext.label("encKey"));
    public static final OERDefinition.Builder RecipientInfo = OERDefinition.choice(PreSharedKeyRecipientInfo.label("pskRecipInfo"), SymmRecipientInfo.label("symmRecipInfo"), PKRecipientInfo.label("certRecipInfo"), PKRecipientInfo.label("signedDataRecipInfo"), PKRecipientInfo.label("rekRecipInfo"));
    public static final OERDefinition.Builder SequenceOfRecipientInfo = OERDefinition.seqof(RecipientInfo);
    public static final OERDefinition.Builder EncryptedData = OERDefinition.seq(SequenceOfRecipientInfo.label("recipients"), SymmetricCiphertext.label("ciphertext"));
    public static final OERDefinition.Builder EndEntityType = OERDefinition.bitString(8L).defaultValue((ASN1Encodable)new DERBitString(new byte[]{0}, 0));
    public static final OERDefinition.Builder SubjectPermissions = OERDefinition.choice(Ieee1609Dot2BaseTypes.SequenceOfPsidSspRange, OERDefinition.nullValue(), OERDefinition.extension()).label("SubjectPermissions");
    public static final OERDefinition.Builder VerificationKeyIndicator = OERDefinition.choice(Ieee1609Dot2BaseTypes.PublicVerificationKey, Ieee1609Dot2BaseTypes.EccP256CurvePoint, OERDefinition.extension()).label("VerificationKeyIndicator");
    public static final OERDefinition.Builder PsidGroupPermissions = OERDefinition.seq(SubjectPermissions, OERDefinition.integer(1L), OERDefinition.integer(0L), EndEntityType).label("PsidGroupPermissions");
    public static final OERDefinition.Builder SequenceOfPsidGroupPermissions = OERDefinition.seqof(PsidGroupPermissions).label("SequenceOfPsidGroupPermissions");
    public static final OERDefinition.Builder LinkageData = OERDefinition.seq(Ieee1609Dot2BaseTypes.IValue, Ieee1609Dot2BaseTypes.LinkageValue, OERDefinition.optional(Ieee1609Dot2BaseTypes.GroupLinkageValue), OERDefinition.extension()).label("LinkageData");
    public static final OERDefinition.Builder CertificateId = OERDefinition.choice(LinkageData, Ieee1609Dot2BaseTypes.Hostname, OERDefinition.octets(1, 64).label("binaryId"), OERDefinition.nullValue(), OERDefinition.extension()).label("CertificateId");
    public static final OERDefinition.Builder ToBeSignedCertificate = OERDefinition.seq(CertificateId.labelPrefix("id"), Ieee1609Dot2BaseTypes.HashedId3.labelPrefix("cracaId"), Ieee1609Dot2BaseTypes.CrlSeries.labelPrefix("crlSeries"), Ieee1609Dot2BaseTypes.ValidityPeriod.labelPrefix("validityPeriod"), OERDefinition.optional(Ieee1609Dot2BaseTypes.GeographicRegion.labelPrefix("region"), Ieee1609Dot2BaseTypes.SubjectAssurance.labelPrefix("assuranceLevel"), Ieee1609Dot2BaseTypes.SequenceOfPsidSsp.labelPrefix("appPermissions"), SequenceOfPsidGroupPermissions.labelPrefix("certIssuePermissions"), SequenceOfPsidGroupPermissions.labelPrefix("certRequestPermissions"), OERDefinition.nullValue().labelPrefix("canRequestRollover"), Ieee1609Dot2BaseTypes.PublicEncryptionKey.labelPrefix("encryptionKey")), VerificationKeyIndicator.labelPrefix("verifyKeyIndicator"), OERDefinition.extension()).label("ToBeSignedCertificate");
    public static final OERDefinition.Builder IssuerIdentifier = OERDefinition.choice(Ieee1609Dot2BaseTypes.HashedId8, Ieee1609Dot2BaseTypes.HashAlgorithm, OERDefinition.extension(), Ieee1609Dot2BaseTypes.HashedId8).label("IssuerIdentifier");
    public static final OERDefinition.Builder CertificateType = OERDefinition.enumeration(OERDefinition.enumItem("explicit"), OERDefinition.enumItem("implicit"), OERDefinition.extension()).label("CertificateType");
    public static final OERDefinition.Builder CertificateBase = OERDefinition.seq(Ieee1609Dot2BaseTypes.UINT8, CertificateType, IssuerIdentifier, ToBeSignedCertificate, OERDefinition.optional(Ieee1609Dot2BaseTypes.Signature)).label("CertificateBase");
    public static final OERDefinition.Builder Certificate = CertificateBase.copy().label("Certificate(CertificateBase)");
    public static final OERDefinition.Builder SequenceOfCertificate = OERDefinition.seqof(Certificate);
    public static final OERDefinition.Builder HeaderInfo = OERDefinition.seq(Ieee1609Dot2BaseTypes.Psid.label("psid"), OERDefinition.optional(Ieee1609Dot2BaseTypes.Time64.label("generationTime"), Ieee1609Dot2BaseTypes.Time64.label("expiryTime"), Ieee1609Dot2BaseTypes.ThreeDLocation.label("generationLocation"), Ieee1609Dot2BaseTypes.HashedId3.label("p2pcdLearningRequest"), MissingCrlIdentifier.label("missingCrlIdentifier"), Ieee1609Dot2BaseTypes.EncryptionKey.label("encryptionKey")), OERDefinition.extension(), OERDefinition.optional(Ieee1609Dot2BaseTypes.SequenceOfHashedId3.label("inlineP2pcdRequest"), Certificate.label("requestedCertificate"), PduFunctionalType.label("pduFunctionalType"), ContributedExtensionBlock.label("contributedExtensions")));
    public static final OERDefinition.Builder SignerIdentifier = OERDefinition.choice(Ieee1609Dot2BaseTypes.HashedId8.label("digest"), SequenceOfCertificate, OERDefinition.nullValue().label("self"), OERDefinition.extension());
    public static final OERDefinition.Builder ToBeSignedData = new OERDefinition.MutableBuilder(OERDefinition.BaseType.SEQ);
    public static final OERDefinition.Builder SignedData = OERDefinition.seq(Ieee1609Dot2BaseTypes.HashAlgorithm.label("hashId"), ToBeSignedData.label("tbsData"), SignerIdentifier.label("signer"), Ieee1609Dot2BaseTypes.Signature.label("signature"));
    public static final OERDefinition.Builder Ieee1609Dot2Content = OERDefinition.choice(OERDefinition.opaque().label("unsecuredData"), SignedData.label("signedData"), EncryptedData.label("encryptedData"), OERDefinition.opaque().label("signedCertificateRequest"), OERDefinition.extension());
    public static final OERDefinition.Builder Countersignature = OERDefinition.seq(Ieee1609Dot2BaseTypes.UINT8.label("protocolVersion"), Ieee1609Dot2Content.label("content"));
    public static final OERDefinition.Builder Ieee1609Dot2Data = OERDefinition.seq(Ieee1609Dot2BaseTypes.UINT8.label("protocolVersion"), Ieee1609Dot2Content.label("content"));
    public static final OERDefinition.Builder SignedDataPayload = OERDefinition.seq(OERDefinition.optional(Ieee1609Dot2Data.label("data"), HashedData.label("extDataHash")), OERDefinition.extension());
    public static final OERDefinition.Element certificate = Certificate.build();
    public static final OERDefinition.Element tbsCertificate = ToBeSignedCertificate.build();

    static {
        ((OERDefinition.MutableBuilder)ToBeSignedData).addItemsAndFreeze(SignedDataPayload.label("payload"), HeaderInfo.label("headerInfo"));
    }
}

