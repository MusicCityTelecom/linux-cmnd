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
import org.bouncycastle.oer.its.CrlSeries;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.Utils;

public class MissingCrlIdentifier
extends ASN1Object {
    private final HashedId.HashedId3 cracaId;
    private final CrlSeries crlSeries;

    public MissingCrlIdentifier(HashedId.HashedId3 hashedId3, CrlSeries crlSeries) {
        this.cracaId = hashedId3;
        this.crlSeries = crlSeries;
    }

    public static MissingCrlIdentifier getInstance(Object object) {
        if (object instanceof MissingCrlIdentifier) {
            return (MissingCrlIdentifier)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        HashedId hashedId = HashedId.getInstance(aSN1Sequence.getObjectAt(0));
        CrlSeries crlSeries = CrlSeries.getInstance(aSN1Sequence.getObjectAt(1));
        return new MissingCrlIdentifier((HashedId.HashedId3)hashedId, crlSeries);
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.cracaId, this.crlSeries});
    }

    public HashedId.HashedId3 getCracaId() {
        return this.cracaId;
    }

    public CrlSeries getCrlSeries() {
        return this.crlSeries;
    }
}

