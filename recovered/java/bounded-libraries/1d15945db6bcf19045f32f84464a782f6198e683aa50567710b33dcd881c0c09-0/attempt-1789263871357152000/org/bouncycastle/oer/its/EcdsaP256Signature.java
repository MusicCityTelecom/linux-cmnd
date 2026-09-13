/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.EccP256CurvePoint;
import org.bouncycastle.oer.its.Utils;

public class EcdsaP256Signature
extends ASN1Object {
    private final EccP256CurvePoint rSig;
    private final ASN1OctetString sSig;

    public EcdsaP256Signature(EccP256CurvePoint eccP256CurvePoint, ASN1OctetString aSN1OctetString) {
        this.rSig = eccP256CurvePoint;
        this.sSig = aSN1OctetString;
    }

    public static EcdsaP256Signature getInstance(Object object) {
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new Builder().setrSig(EccP256CurvePoint.getInstance(aSN1Sequence.getObjectAt(0))).setsSig(ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(1))).createEcdsaP256Signature();
    }

    public static Builder builder() {
        return new Builder();
    }

    public EccP256CurvePoint getrSig() {
        return this.rSig;
    }

    public ASN1OctetString getsSig() {
        return this.sSig;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.rSig, this.sSig});
    }

    public static class Builder {
        private EccP256CurvePoint rSig;
        private ASN1OctetString sSig;

        public Builder setrSig(EccP256CurvePoint eccP256CurvePoint) {
            this.rSig = eccP256CurvePoint;
            return this;
        }

        public Builder setsSig(ASN1OctetString aSN1OctetString) {
            this.sSig = aSN1OctetString;
            return this;
        }

        public EcdsaP256Signature createEcdsaP256Signature() {
            return new EcdsaP256Signature(this.rSig, this.sSig);
        }
    }
}

