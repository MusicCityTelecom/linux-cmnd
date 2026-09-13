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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.CountryOnly;
import org.bouncycastle.oer.its.Region;
import org.bouncycastle.oer.its.RegionInterface;
import org.bouncycastle.oer.its.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class CountryAndRegions
extends ASN1Object
implements RegionInterface {
    private final CountryOnly countryOnly;
    private final List<Region> regions;

    public CountryAndRegions(CountryOnly countryOnly, List<Region> list) {
        this.countryOnly = countryOnly;
        this.regions = Collections.unmodifiableList(list);
    }

    public static CountryAndRegions getInstance(Object object) {
        if (object instanceof CountryAndRegions) {
            return (CountryAndRegions)object;
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        CountryOnly countryOnly = CountryOnly.getInstance(aSN1Sequence.getObjectAt(0));
        ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance((Object)aSN1Sequence.getObjectAt(1));
        return new CountryAndRegions(countryOnly, Utils.fillList(Region.class, aSN1Sequence2));
    }

    public static CountryAndRegionsBuilder builder() {
        return new CountryAndRegionsBuilder();
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.countryOnly, Utils.toSequence(this.regions)});
    }

    public CountryOnly getCountryOnly() {
        return this.countryOnly;
    }

    public List<Region> getRegions() {
        return this.regions;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class CountryAndRegionsBuilder {
        private CountryOnly countryOnly;
        private List<Region> regionList = new ArrayList<Region>();

        public CountryAndRegionsBuilder setCountryOnly(CountryOnly countryOnly) {
            this.countryOnly = countryOnly;
            return this;
        }

        public CountryAndRegionsBuilder setRegionList(List<Region> list) {
            this.regionList.addAll(list);
            return this;
        }

        public CountryAndRegionsBuilder addRegion(Region region) {
            this.regionList.add(region);
            return this;
        }

        public CountryAndRegions build() {
            return new CountryAndRegions(this.countryOnly, this.regionList);
        }
    }
}

