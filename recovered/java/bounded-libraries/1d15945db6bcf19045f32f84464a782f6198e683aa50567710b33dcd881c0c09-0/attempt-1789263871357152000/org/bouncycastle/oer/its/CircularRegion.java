/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.RegionInterface;
import org.bouncycastle.oer.its.TwoDLocation;
import org.bouncycastle.oer.its.Uint16;
import org.bouncycastle.oer.its.Utils;

public class CircularRegion
extends ASN1Object
implements RegionInterface {
    private final TwoDLocation center;
    private final Uint16 radius;

    public CircularRegion(TwoDLocation twoDLocation, Uint16 uint16) {
        this.center = twoDLocation;
        this.radius = uint16;
    }

    public static CircularRegion getInstance(Object object) {
        if (object instanceof CircularRegion) {
            return (CircularRegion)object;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new CircularRegion(TwoDLocation.getInstance(aSN1Sequence.getObjectAt(0)), Uint16.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public TwoDLocation getCenter() {
        return this.center;
    }

    public Uint16 getRadius() {
        return this.radius;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.center, this.radius});
    }

    public static class Builder {
        private TwoDLocation center;
        private Uint16 radius;

        public Builder setCenter(TwoDLocation twoDLocation) {
            this.center = twoDLocation;
            return this;
        }

        public Builder setRadius(Uint16 uint16) {
            this.radius = uint16;
            return this;
        }

        public CircularRegion createCircularRegion() {
            return new CircularRegion(this.center, this.radius);
        }
    }
}

