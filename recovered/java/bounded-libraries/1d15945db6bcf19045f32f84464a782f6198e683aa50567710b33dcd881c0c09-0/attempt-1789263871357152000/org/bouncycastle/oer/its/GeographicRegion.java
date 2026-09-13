/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.CircularRegion;
import org.bouncycastle.oer.its.PolygonalRegion;
import org.bouncycastle.oer.its.SequenceOfIdentifiedRegion;
import org.bouncycastle.oer.its.SequenceOfRectangularRegion;

public class GeographicRegion
extends ASN1Object
implements ASN1Choice {
    public static final int circularRegion = 0;
    public static final int rectangularRegion = 1;
    public static final int polygonalRegion = 2;
    public static final int identifiedRegion = 3;
    public static final int extension = 4;
    private int choice;
    private ASN1Encodable region;

    public GeographicRegion(int n, ASN1Encodable aSN1Encodable) {
        this.choice = n;
        this.region = aSN1Encodable;
    }

    public static GeographicRegion getInstance(Object object) {
        if (object instanceof GeographicRegion) {
            return (GeographicRegion)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        int n = aSN1TaggedObject.getTagNo();
        switch (n) {
            case 0: {
                return new GeographicRegion(n, (ASN1Encodable)CircularRegion.getInstance(aSN1TaggedObject.getObject()));
            }
            case 1: {
                return new GeographicRegion(n, (ASN1Encodable)SequenceOfRectangularRegion.getInstance(aSN1TaggedObject.getObject()));
            }
            case 2: {
                return new GeographicRegion(n, (ASN1Encodable)PolygonalRegion.getInstance(aSN1TaggedObject.getObject()));
            }
            case 3: {
                return new GeographicRegion(n, (ASN1Encodable)SequenceOfIdentifiedRegion.getInstance(aSN1TaggedObject.getObject()));
            }
            case 4: {
                return new GeographicRegion(n, (ASN1Encodable)DEROctetString.getInstance((Object)aSN1TaggedObject.getObject()));
            }
        }
        throw new IllegalStateException("unknown region choice " + n);
    }

    public int getChoice() {
        return this.choice;
    }

    public ASN1Encodable getRegion() {
        return this.region;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.region);
    }
}

