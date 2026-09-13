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
import org.bouncycastle.oer.its.Utils;

public class GroupLinkageValue
extends ASN1Object {
    private final ASN1OctetString jValue;
    private final ASN1OctetString value;

    private GroupLinkageValue(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() != 2) {
            throw new IllegalArgumentException("sequence not length 2");
        }
        this.jValue = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(0));
        this.value = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(1));
    }

    public static GroupLinkageValue getInstance(Object object) {
        if (object instanceof GroupLinkageValue) {
            return (GroupLinkageValue)((Object)object);
        }
        if (object != null) {
            return new GroupLinkageValue(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public ASN1OctetString getjValue() {
        return this.jValue;
    }

    public ASN1OctetString getValue() {
        return this.value;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.jValue, this.value});
    }
}

