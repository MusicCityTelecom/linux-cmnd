/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.RegionInterface;
import org.bouncycastle.oer.its.TwoDLocation;
import org.bouncycastle.oer.its.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class PolygonalRegion
extends ASN1Object
implements RegionInterface {
    private final List<TwoDLocation> points;

    public PolygonalRegion(List<TwoDLocation> list) {
        this.points = Collections.unmodifiableList(list);
    }

    public static PolygonalRegion getInstance(Object object) {
        if (object instanceof PolygonalRegion) {
            return (PolygonalRegion)object;
        }
        if (object != null) {
            return new PolygonalRegion(Utils.fillList(TwoDLocation.class, ASN1Sequence.getInstance((Object)object)));
        }
        return null;
    }

    public List<TwoDLocation> getPoints() {
        return this.points;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.points);
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder {
        private List<TwoDLocation> locations = new ArrayList<TwoDLocation>();

        public Builder setLocations(List<TwoDLocation> list) {
            this.locations = list;
            return this;
        }

        public Builder setLocations(TwoDLocation ... twoDLocationArray) {
            this.locations.addAll(Arrays.asList(twoDLocationArray));
            return this;
        }

        public PolygonalRegion createPolygonalRegion() {
            return new PolygonalRegion(this.locations);
        }
    }
}

