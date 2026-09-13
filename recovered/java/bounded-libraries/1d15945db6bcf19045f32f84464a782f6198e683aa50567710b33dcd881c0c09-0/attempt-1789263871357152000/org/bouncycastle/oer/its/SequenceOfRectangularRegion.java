/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 */
package org.bouncycastle.oer.its;

import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.RectangularRegion;
import org.bouncycastle.oer.its.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SequenceOfRectangularRegion
extends ASN1Object {
    private final List<RectangularRegion> rectangularRegions;

    public SequenceOfRectangularRegion(List<RectangularRegion> list) {
        this.rectangularRegions = Collections.unmodifiableList(list);
    }

    public static SequenceOfRectangularRegion getInstance(Object object) {
        if (object instanceof SequenceOfRectangularRegion) {
            return (SequenceOfRectangularRegion)((Object)object);
        }
        return new SequenceOfRectangularRegion(Utils.fillList(RectangularRegion.class, ASN1Sequence.getInstance((Object)object)));
    }

    public List<RectangularRegion> getRectangularRegions() {
        return this.rectangularRegions;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(this.rectangularRegions);
    }
}

