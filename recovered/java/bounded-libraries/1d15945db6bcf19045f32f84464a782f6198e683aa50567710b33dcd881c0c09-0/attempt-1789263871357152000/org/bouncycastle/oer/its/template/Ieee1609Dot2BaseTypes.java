/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.oer.its.template;

import java.math.BigInteger;
import org.bouncycastle.oer.OERDefinition;

public class Ieee1609Dot2BaseTypes {
    public static final OERDefinition.Builder UINT3 = OERDefinition.integer(0L, 7L);
    public static final OERDefinition.Builder UINT8 = OERDefinition.integer(0L, 255L);
    public static final OERDefinition.Builder UINT16 = OERDefinition.integer(0L, 65535L);
    public static final OERDefinition.Builder UINT32 = OERDefinition.integer(0L, 0xFFFFFFFFL);
    public static final OERDefinition.Builder UINT64 = OERDefinition.integer(BigInteger.ZERO, new BigInteger("18446744073709551615"));
    public static final OERDefinition.Builder SequenceOfUint16 = OERDefinition.seqof(UINT16);
    public static final OERDefinition.Builder HashedId3 = OERDefinition.octets(3).label("HashId3");
    public static final OERDefinition.Builder SequenceOfHashedId3 = OERDefinition.seqof(HashedId3).label("SequenceOfHashedId3");
    public static final OERDefinition.Builder HashedId8 = OERDefinition.octets(8).label("HashId8");
    public static final OERDefinition.Builder HashedId10 = OERDefinition.octets(10).label("HashId10");
    public static final OERDefinition.Builder HashedId32 = OERDefinition.octets(32).label("HashId32");
    public static final OERDefinition.Builder HashedId48 = OERDefinition.octets(48).label("HashId48");
    public static final OERDefinition.Builder Time32 = UINT32.label("Time32");
    public static final OERDefinition.Builder Time64 = UINT64.label("Time64");
    public static final OERDefinition.Builder Duration = OERDefinition.choice(UINT16.label("microseconds"), UINT16.label("milliseconds"), UINT16.label("seconds"), UINT16.label("minutes"), UINT16.label("hours"), UINT16.label("sixtyHours"), UINT16.label("years")).label("Duration");
    public static final OERDefinition.Builder ValidityPeriod = OERDefinition.seq(Time32, Duration).label("ValidityPeriod");
    public static final OERDefinition.Builder IValue = UINT16.copy().label("IValue");
    public static final OERDefinition.Builder Hostname = OERDefinition.utf8String(0, 255).label("Hostname");
    public static final OERDefinition.Builder LinkageValue = OERDefinition.octets(9).label("LinkageValue");
    public static final OERDefinition.Builder GroupLinkageValue = OERDefinition.seq(OERDefinition.octets(4), OERDefinition.octets(9)).label("GroupLinkageValue");
    public static final OERDefinition.Builder LaId = OERDefinition.octets(2).label("LaId");
    public static final OERDefinition.Builder LinkageSeed = OERDefinition.octets(16).label("LinkageSeed");
    public static final OERDefinition.Builder EccP256CurvePoint = OERDefinition.choice(OERDefinition.octets(32), OERDefinition.nullValue(), OERDefinition.octets(32), OERDefinition.octets(32), OERDefinition.seq(OERDefinition.octets(32), OERDefinition.octets(32))).label("EccP256CurvePoint");
    public static final OERDefinition.Builder EcdsaP256Signature = OERDefinition.seq(EccP256CurvePoint, OERDefinition.octets(32)).label("EcdsaP256Signature");
    public static final OERDefinition.Builder EccP384CurvePoint = OERDefinition.choice(OERDefinition.octets(48), OERDefinition.nullValue(), OERDefinition.octets(48), OERDefinition.octets(48), OERDefinition.seq(OERDefinition.octets(48), OERDefinition.octets(48))).label("EccP384CurvePoint");
    public static final OERDefinition.Builder EcdsaP384Signature = OERDefinition.seq(EccP384CurvePoint, OERDefinition.octets(48)).label("EcdsaP384Signature");
    public static final OERDefinition.Builder Signature = OERDefinition.choice(EcdsaP256Signature, EcdsaP256Signature, OERDefinition.extension(), EcdsaP384Signature).label("Signature");
    public static final OERDefinition.Builder SymmAlgorithm = OERDefinition.enumeration(OERDefinition.enumItem("aes128Ccm"), OERDefinition.extension()).label("SymmAlgorithm");
    public static final OERDefinition.Builder HashAlgorithm = OERDefinition.enumeration(OERDefinition.enumItem("sha256"), OERDefinition.extension(), OERDefinition.enumItem("sha384")).label("HashAlgorithm");
    public static final OERDefinition.Builder EciesP256EncryptedKey = OERDefinition.seq(EccP256CurvePoint.copy().label("v(EccP256CurvePoint)"), OERDefinition.octets(16).label("c"), OERDefinition.octets(16).label("t")).label("EciesP256EncryptedKey");
    public static final OERDefinition.Builder BasePublicEncryptionKey = OERDefinition.choice(EccP256CurvePoint, EccP256CurvePoint, OERDefinition.extension()).label("BasePublicEncryptionKey");
    public static final OERDefinition.Builder PublicEncryptionKey = OERDefinition.seq(SymmAlgorithm, BasePublicEncryptionKey).label("PublicEncryptionKey");
    public static final OERDefinition.Builder SymmetricEncryptionKey = OERDefinition.choice(OERDefinition.octets(16).label("aes128Ccm"), OERDefinition.extension()).label("SymmetricEncryptionKey");
    public static final OERDefinition.Builder EncryptionKey = OERDefinition.choice(PublicEncryptionKey.label("public"), SymmetricEncryptionKey.label("symmetric")).label("EncryptionKey");
    public static final OERDefinition.Builder PublicVerificationKey = OERDefinition.choice(EccP256CurvePoint.label("ecdsaNistP256"), EccP256CurvePoint.label("ecdsaBrainpoolP256r1"), OERDefinition.extension(), EccP384CurvePoint.label("ecdsaBrainpoolP384r1")).label("PublicVerificationKey");
    public static final OERDefinition.Builder Psid = OERDefinition.integer().rangeToMAXFrom(0L).label("Psid");
    public static final OERDefinition.Builder BitmapSsp = OERDefinition.octets(0, 31).label("BitmapSsp");
    public static final OERDefinition.Builder ServiceSpecificPermissions = OERDefinition.choice(OERDefinition.octets().unbounded().label("opaque"), OERDefinition.extension(), BitmapSsp).label("ServiceSpecificPermissions");
    public static final OERDefinition.Builder PsidSsp = OERDefinition.seq(Psid, OERDefinition.optional(ServiceSpecificPermissions)).label("PsidSsp");
    public static final OERDefinition.Builder SequenceOfPsidSsp = OERDefinition.seqof(PsidSsp).label("SequenceOfPsidSsp");
    public static final OERDefinition.Builder SequenceOfPsid = OERDefinition.seqof(Psid).label("SequenceOfPsid");
    public static final OERDefinition.Builder SequenceOfOctetString = OERDefinition.seqof(OERDefinition.octets().rangeToMAXFrom(0L)).label("SequenceOfOctetString");
    public static final OERDefinition.Builder BitmapSspRange = OERDefinition.seq(OERDefinition.octets(1, 32).label("sspValue"), OERDefinition.octets(1, 32).label("sspBitMask")).label("BitmapSspRange");
    public static final OERDefinition.Builder SspRange = OERDefinition.choice(SequenceOfOctetString.label("opaque"), OERDefinition.nullValue().label("all"), OERDefinition.extension(), BitmapSspRange.label("bitmapSspRange")).label("SspRange");
    public static final OERDefinition.Builder PsidSspRange = OERDefinition.seq(Psid.label("psid"), OERDefinition.optional(SspRange.label("sspRange"))).label("PsidSspRange");
    public static final OERDefinition.Builder SequenceOfPsidSspRange = OERDefinition.seqof(PsidSspRange).label("SequenceOfPsidSspRange");
    public static final OERDefinition.Builder SubjectAssurance = OERDefinition.octets(1).label("SubjectAssurance");
    public static final OERDefinition.Builder CrlSeries = UINT16.label("CrlSeries");
    public static OERDefinition.Builder CountryOnly = UINT16.label("CountryOnly");
    public static OERDefinition.Builder CountryAndRegions = OERDefinition.seq(CountryOnly, OERDefinition.seqof(UINT8)).label("CountryAndRegions");
    public static OERDefinition.Builder RegionAndSubregions = OERDefinition.seq(UINT8, OERDefinition.seqof(UINT16)).label("RegionAndSubregions");
    public static OERDefinition.Builder SequenceOfRegionAndSubregions = OERDefinition.seqof(RegionAndSubregions).label("SequenceOfRegionAndSubregions");
    public static OERDefinition.Builder CountryAndSubregions = OERDefinition.seq(CountryOnly, SequenceOfRegionAndSubregions).label("CountryAndSubregions");
    public static OERDefinition.Builder OneEightyDegreeInt = OERDefinition.integer(-1799999999L, 1800000001L).label("OneEightyDegreeInt");
    public static OERDefinition.Builder KnownLongitude = OneEightyDegreeInt.copy().label("KnownLongitude(OneEightyDegreeInt)");
    public static OERDefinition.Builder UnknownLongitude = OERDefinition.integer(1800000001L).label("UnknownLongitude");
    public static OERDefinition.Builder NinetyDegreeInt = OERDefinition.integer(-900000000L, 900000001L).label("NinetyDegreeInt");
    public static OERDefinition.Builder KnownLatitude = NinetyDegreeInt.copy().label("KnownLatitude(NinetyDegreeInt)");
    public static OERDefinition.Builder UnknownLatitude = OERDefinition.integer(900000001L);
    public static OERDefinition.Builder Elevation = UINT16.label("Elevation");
    public static OERDefinition.Builder Longitude = OneEightyDegreeInt.copy().label("Longitude(OneEightyDegreeInt)");
    public static OERDefinition.Builder Latitude = NinetyDegreeInt.copy().label("Latitude(NinetyDegreeInt)");
    public static OERDefinition.Builder ThreeDLocation = OERDefinition.seq(Latitude, Longitude, Elevation).label("ThreeDLocation");
    public static OERDefinition.Builder TwoDLocation = OERDefinition.seq(Latitude, Longitude).label("TwoDLocation");
    public static OERDefinition.Builder RectangularRegion = OERDefinition.seq(TwoDLocation, TwoDLocation).label("RectangularRegion");
    public static OERDefinition.Builder SequenceOfRectangularRegion = OERDefinition.seqof(RectangularRegion).label("SequenceOfRectangularRegion");
    public static OERDefinition.Builder CircularRegion = OERDefinition.seq(TwoDLocation, UINT16).label("CircularRegion");
    public static OERDefinition.Builder PolygonalRegion = OERDefinition.seqof(TwoDLocation).rangeToMAXFrom(3L).label("PolygonalRegion");
    public static OERDefinition.Builder IdentifiedRegion = OERDefinition.choice(CountryOnly, CountryAndRegions, CountryAndSubregions, OERDefinition.extension()).label("IdentifiedRegion");
    public static OERDefinition.Builder SequenceOfIdentifiedRegion = OERDefinition.seqof(IdentifiedRegion).label("SequenceOfIdentifiedRegion");
    public static OERDefinition.Builder GeographicRegion = OERDefinition.choice(CircularRegion, SequenceOfRectangularRegion, PolygonalRegion, SequenceOfIdentifiedRegion, OERDefinition.extension()).label("GeographicRegion");
}

