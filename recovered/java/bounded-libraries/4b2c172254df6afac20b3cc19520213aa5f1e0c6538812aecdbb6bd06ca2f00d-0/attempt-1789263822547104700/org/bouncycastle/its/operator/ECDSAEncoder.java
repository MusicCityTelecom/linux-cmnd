/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.sec.SECObjectIdentifiers
 *  org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers
 *  org.bouncycastle.oer.its.EccP256CurvePoint
 *  org.bouncycastle.oer.its.EccP384CurvePoint
 *  org.bouncycastle.oer.its.EcdsaP256Signature
 *  org.bouncycastle.oer.its.EcdsaP384Signature
 *  org.bouncycastle.oer.its.Signature
 *  org.bouncycastle.util.BigIntegers
 */
package org.bouncycastle.its.operator;

import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.sec.SECObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.EccP384CurvePoint;
import org.bouncycastle.oer.its.EcdsaP256Signature;
import org.bouncycastle.oer.its.EcdsaP384Signature;
import org.bouncycastle.oer.its.Signature;
import org.bouncycastle.util.BigIntegers;

public class ECDSAEncoder {
    public static byte[] toX962(Signature signature) {
        byte[] byArray;
        byte[] byArray2;
        if (signature.getChoice() == 0 || signature.getChoice() == 1) {
            EcdsaP256Signature ecdsaP256Signature = EcdsaP256Signature.getInstance((Object)signature.getValue());
            byArray2 = ASN1OctetString.getInstance((Object)ecdsaP256Signature.getrSig().getValue()).getOctets();
            byArray = ecdsaP256Signature.getsSig().getOctets();
        } else {
            EcdsaP384Signature ecdsaP384Signature = EcdsaP384Signature.getInstance((Object)signature.getValue());
            byArray2 = ASN1OctetString.getInstance((Object)ecdsaP384Signature.getrSig().getValue()).getOctets();
            byArray = ecdsaP384Signature.getsSig().getOctets();
        }
        try {
            return new DERSequence(new ASN1Encodable[]{new ASN1Integer(BigIntegers.fromUnsignedByteArray((byte[])byArray2)), new ASN1Integer(BigIntegers.fromUnsignedByteArray((byte[])byArray))}).getEncoded();
        }
        catch (IOException iOException) {
            throw new RuntimeException("der encoding r & s");
        }
    }

    public static Signature toITS(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] byArray) {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)byArray);
        if (aSN1ObjectIdentifier.equals((ASN1Primitive)SECObjectIdentifiers.secp256r1)) {
            return new Signature(0, (ASN1Encodable)new EcdsaP256Signature(new EccP256CurvePoint(0, (ASN1Encodable)new DEROctetString(BigIntegers.asUnsignedByteArray((int)32, (BigInteger)ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0)).getValue()))), (ASN1OctetString)new DEROctetString(BigIntegers.asUnsignedByteArray((int)32, (BigInteger)ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(1)).getValue()))));
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive)TeleTrusTObjectIdentifiers.brainpoolP256r1)) {
            return new Signature(1, (ASN1Encodable)new EcdsaP256Signature(new EccP256CurvePoint(0, (ASN1Encodable)new DEROctetString(BigIntegers.asUnsignedByteArray((int)32, (BigInteger)ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0)).getValue()))), (ASN1OctetString)new DEROctetString(BigIntegers.asUnsignedByteArray((int)32, (BigInteger)ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(1)).getValue()))));
        }
        if (aSN1ObjectIdentifier.equals((ASN1Primitive)TeleTrusTObjectIdentifiers.brainpoolP384r1)) {
            return new Signature(3, (ASN1Encodable)new EcdsaP384Signature(new EccP384CurvePoint(0, (ASN1Encodable)new DEROctetString(BigIntegers.asUnsignedByteArray((int)48, (BigInteger)ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0)).getValue()))), (ASN1OctetString)new DEROctetString(BigIntegers.asUnsignedByteArray((int)48, (BigInteger)ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(1)).getValue()))));
        }
        throw new IllegalArgumentException("unknown curveID");
    }
}

