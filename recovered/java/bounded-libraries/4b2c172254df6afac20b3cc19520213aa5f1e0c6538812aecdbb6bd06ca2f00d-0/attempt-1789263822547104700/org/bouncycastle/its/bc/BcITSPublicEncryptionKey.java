/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.nist.NISTNamedCurves
 *  org.bouncycastle.asn1.sec.SECObjectIdentifiers
 *  org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves
 *  org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers
 *  org.bouncycastle.asn1.x9.X9ECParameters
 *  org.bouncycastle.crypto.params.AsymmetricKeyParameter
 *  org.bouncycastle.crypto.params.ECDomainParameters
 *  org.bouncycastle.crypto.params.ECNamedDomainParameters
 *  org.bouncycastle.crypto.params.ECPublicKeyParameters
 *  org.bouncycastle.math.ec.ECCurve
 *  org.bouncycastle.math.ec.ECPoint
 *  org.bouncycastle.oer.its.BasePublicEncryptionKey
 *  org.bouncycastle.oer.its.BasePublicEncryptionKey$Builder
 *  org.bouncycastle.oer.its.EccCurvePoint
 *  org.bouncycastle.oer.its.EccP256CurvePoint
 *  org.bouncycastle.oer.its.EccP384CurvePoint
 *  org.bouncycastle.oer.its.PublicEncryptionKey
 *  org.bouncycastle.oer.its.SymmAlgorithm
 */
package org.bouncycastle.its.bc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTNamedCurves;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECNamedDomainParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.its.ITSPublicEncryptionKey;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.oer.its.BasePublicEncryptionKey;
import org.bouncycastle.oer.its.EccCurvePoint;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.EccP384CurvePoint;
import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.SymmAlgorithm;

public class BcITSPublicEncryptionKey
extends ITSPublicEncryptionKey {
    public BcITSPublicEncryptionKey(PublicEncryptionKey publicEncryptionKey) {
        super(publicEncryptionKey);
    }

    static PublicEncryptionKey fromKeyParameters(ECPublicKeyParameters eCPublicKeyParameters) {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = ((ECNamedDomainParameters)eCPublicKeyParameters.getParameters()).getName();
        ECPoint eCPoint = eCPublicKeyParameters.getQ();
        if (aSN1ObjectIdentifier.equals((ASN1Primitive)SECObjectIdentifiers.secp256r1)) {
            return new PublicEncryptionKey(SymmAlgorithm.aes128Ccm, new BasePublicEncryptionKey.Builder().setChoice(0).setValue((EccCurvePoint)EccP256CurvePoint.builder().createUncompressedP256(eCPoint.getAffineXCoord().toBigInteger(), eCPoint.getAffineYCoord().toBigInteger())).createBasePublicEncryptionKey());
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive)TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            return new PublicEncryptionKey(SymmAlgorithm.aes128Ccm, new BasePublicEncryptionKey.Builder().setChoice(1).setValue((EccCurvePoint)EccP256CurvePoint.builder().createUncompressedP256(eCPoint.getAffineXCoord().toBigInteger(), eCPoint.getAffineYCoord().toBigInteger())).createBasePublicEncryptionKey());
        }
        throw new IllegalArgumentException("unknown curve in public encryption key");
    }

    public BcITSPublicEncryptionKey(AsymmetricKeyParameter asymmetricKeyParameter) {
        super(BcITSPublicEncryptionKey.fromKeyParameters((ECPublicKeyParameters)asymmetricKeyParameter));
    }

    public AsymmetricKeyParameter getKey() {
        byte[] byArray;
        X9ECParameters x9ECParameters;
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        BasePublicEncryptionKey basePublicEncryptionKey = this.encryptionKey.getBasePublicEncryptionKey();
        switch (basePublicEncryptionKey.getChoice()) {
            case 0: {
                aSN1ObjectIdentifier = SECObjectIdentifiers.secp256r1;
                x9ECParameters = NISTNamedCurves.getByOID((ASN1ObjectIdentifier)SECObjectIdentifiers.secp256r1);
                break;
            }
            case 1: {
                aSN1ObjectIdentifier = TeleTrusTObjectIdentifiers.brainpoolP256r1;
                x9ECParameters = TeleTrusTNamedCurves.getByOID((ASN1ObjectIdentifier)TeleTrusTObjectIdentifiers.brainpoolP256r1);
                break;
            }
            default: {
                throw new IllegalStateException("unknown key type");
            }
        }
        ECCurve eCCurve = x9ECParameters.getCurve();
        ASN1Encodable aSN1Encodable = this.encryptionKey.getBasePublicEncryptionKey().getValue();
        if (!(aSN1Encodable instanceof EccCurvePoint)) {
            throw new IllegalStateException("extension to public verification key not supported");
        }
        EccCurvePoint eccCurvePoint = (EccCurvePoint)basePublicEncryptionKey.getValue();
        if (eccCurvePoint instanceof EccP256CurvePoint) {
            byArray = eccCurvePoint.getEncodedPoint();
        } else if (eccCurvePoint instanceof EccP384CurvePoint) {
            byArray = eccCurvePoint.getEncodedPoint();
        } else {
            throw new IllegalStateException("unknown key type");
        }
        ECPoint eCPoint = eCCurve.decodePoint(byArray).normalize();
        return new ECPublicKeyParameters(eCPoint, (ECDomainParameters)new ECNamedDomainParameters(aSN1ObjectIdentifier, x9ECParameters));
    }
}

