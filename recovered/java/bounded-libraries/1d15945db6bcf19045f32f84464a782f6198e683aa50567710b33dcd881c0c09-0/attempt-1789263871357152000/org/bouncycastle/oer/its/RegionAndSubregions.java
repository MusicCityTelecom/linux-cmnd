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

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.Region;
import org.bouncycastle.oer.its.RegionInterface;
import org.bouncycastle.oer.its.Uint16;
import org.bouncycastle.oer.its.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class RegionAndSubregions
extends ASN1Object
implements RegionInterface {
    private final Region region;
    private final List<Uint16> subRegions;

    public RegionAndSubregions(Region region, List<Uint16> list) {
        this.region = region;
        this.subRegions = Collections.unmodifiableList(list);
    }

    public static RegionAndSubregions getInstance(Object object) {
        if (object instanceof RegionAndSubregions) {
            return (RegionAndSubregions)object;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)0);
        Builder builder = new Builder();
        builder.setRegion(Region.getInstance(aSN1Sequence.getObjectAt(0)));
        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance((Object)aSN1Sequence.getObjectAt(1));
        Iterator iterator = aSN1Sequence2.iterator();
        while (iterator.hasNext()) {
            builder.setSubRegion(Uint16.getInstance(iterator.next()));
        }
        return builder.createRegionAndSubregions();
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.region, Utils.toSequence(this.subRegions)});
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder {
        private Region region;
        private List<Uint16> subRegions;

        public Builder setRegion(Region region) {
            this.region = region;
            return this;
        }

        public Builder setSubRegions(List<Uint16> list) {
            this.subRegions = list;
            return this;
        }

        public Builder setSubRegion(Uint16 ... uint16Array) {
            this.subRegions.addAll(Arrays.asList(uint16Array));
            return this;
        }

        public RegionAndSubregions createRegionAndSubregions() {
            return new RegionAndSubregions(this.region, this.subRegions);
        }
    }
}

