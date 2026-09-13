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
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.EtsiOriginatingHeaderInfoExtension;
import org.bouncycastle.oer.its.HeaderInfoContributorId;
import org.bouncycastle.oer.its.Utils;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class ContributedExtensionBlock
extends ASN1Object {
    private final HeaderInfoContributorId contributorId;
    private final List<EtsiOriginatingHeaderInfoExtension> extns;

    public ContributedExtensionBlock(HeaderInfoContributorId headerInfoContributorId, List<EtsiOriginatingHeaderInfoExtension> list) {
        this.contributorId = headerInfoContributorId;
        this.extns = list;
    }

    public static ContributedExtensionBlock getInstance(Object object) {
        if (object instanceof ContributedExtensionBlock) {
            return (ContributedExtensionBlock)((Object)object);
        }
        Iterator iterator = ASN1Sequence.getInstance((Object)object).iterator();
        HeaderInfoContributorId headerInfoContributorId = HeaderInfoContributorId.getInstance(iterator.next());
        ArrayList<EtsiOriginatingHeaderInfoExtension> arrayList = new ArrayList<EtsiOriginatingHeaderInfoExtension>();
        while (iterator.hasNext()) {
            arrayList.add(EtsiOriginatingHeaderInfoExtension.getInstance(iterator.next()));
        }
        return new ContributedExtensionBlock(headerInfoContributorId, arrayList);
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.contributorId, Utils.toSequence(this.extns)});
    }

    public HeaderInfoContributorId getContributorId() {
        return this.contributorId;
    }

    public List<EtsiOriginatingHeaderInfoExtension> getExtns() {
        return this.extns;
    }
}

