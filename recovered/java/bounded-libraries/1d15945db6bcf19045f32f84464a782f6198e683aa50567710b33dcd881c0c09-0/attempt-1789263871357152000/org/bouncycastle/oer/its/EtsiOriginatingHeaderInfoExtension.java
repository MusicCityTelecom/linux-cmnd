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

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.HeaderInfoContributorId;
import org.bouncycastle.oer.its.Utils;

public class EtsiOriginatingHeaderInfoExtension
extends ASN1Object {
    private final HeaderInfoContributorId etsiHeaderInfoContributorId;
    private final ASN1OctetString extension;

    public EtsiOriginatingHeaderInfoExtension(HeaderInfoContributorId headerInfoContributorId, ASN1OctetString aSN1OctetString) {
        this.etsiHeaderInfoContributorId = headerInfoContributorId;
        this.extension = aSN1OctetString;
    }

    public static EtsiOriginatingHeaderInfoExtension getInstance(Object object) {
        if (object instanceof EtsiOriginatingHeaderInfoExtension) {
            return (EtsiOriginatingHeaderInfoExtension)((Object)object);
        }
        Iterator iterator = ASN1Sequence.getInstance((Object)object).iterator();
        HeaderInfoContributorId headerInfoContributorId = HeaderInfoContributorId.getInstance(iterator.next());
        if (iterator.hasNext()) {
            return new EtsiOriginatingHeaderInfoExtension(headerInfoContributorId, ASN1OctetString.getInstance(iterator.next()));
        }
        return new EtsiOriginatingHeaderInfoExtension(headerInfoContributorId, null);
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.etsiHeaderInfoContributorId, this.extension});
    }

    public HeaderInfoContributorId getEtsiHeaderInfoContributorId() {
        return this.etsiHeaderInfoContributorId;
    }

    public ASN1OctetString getExtension() {
        return this.extension;
    }
}

