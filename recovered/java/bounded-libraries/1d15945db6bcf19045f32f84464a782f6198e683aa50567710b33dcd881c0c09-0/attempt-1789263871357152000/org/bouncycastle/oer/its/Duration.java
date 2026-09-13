/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERTaggedObject;

public class Duration
extends ASN1Object
implements ASN1Choice {
    public static final int microseconds = 0;
    public static final int milliseconds = 1;
    public static final int seconds = 2;
    public static final int minutes = 3;
    public static final int hours = 4;
    public static final int sixtyHours = 5;
    public static final int years = 6;
    private final int tag;
    private final int value;

    public Duration(int n, int n2) {
        this.tag = n;
        this.value = n2;
    }

    public static Duration getInstance(Object object) {
        if (object instanceof Duration) {
            return (Duration)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        int n = aSN1TaggedObject.getTagNo();
        switch (n) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: {
                try {
                    return new Duration(n, ASN1Integer.getInstance((Object)aSN1TaggedObject.getObject()).getValue().intValue());
                }
                catch (Exception exception) {
                    throw new IllegalStateException(exception.getMessage(), exception);
                }
            }
        }
        throw new IllegalArgumentException("invalid choice value " + n);
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.tag, (ASN1Encodable)new ASN1Integer((long)this.value));
    }

    public int getTag() {
        return this.tag;
    }

    public int getValue() {
        return this.value;
    }
}

