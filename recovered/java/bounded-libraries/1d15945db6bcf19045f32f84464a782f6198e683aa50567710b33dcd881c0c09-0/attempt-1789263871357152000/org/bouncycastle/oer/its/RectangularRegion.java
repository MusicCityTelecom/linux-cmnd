/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.RegionInterface;
import org.bouncycastle.oer.its.TwoDLocation;

public class RectangularRegion
extends ASN1Object
implements RegionInterface {
    private final TwoDLocation northWest;
    private final TwoDLocation southEast;

    public RectangularRegion(TwoDLocation twoDLocation, TwoDLocation twoDLocation2) {
        this.northWest = twoDLocation;
        this.southEast = twoDLocation2;
    }

    public static RectangularRegion getInstance(Object object) {
        if (object instanceof RectangularRegion) {
            return (RectangularRegion)object;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new RectangularRegion(TwoDLocation.getInstance(aSN1Sequence.getObjectAt(0)), TwoDLocation.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public TwoDLocation getNorthWest() {
        return this.northWest;
    }

    public TwoDLocation getSouthEast() {
        return this.southEast;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1Encodable[]{this.northWest, this.southEast});
    }
}

