/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.x509.CertificateList
 */
package org.bouncycastle.asn1.cmp;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.x509.CertificateList;

public class CRLAnnContent
extends ASN1Object {
    private ASN1Sequence content;

    private CRLAnnContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public static CRLAnnContent getInstance(Object object) {
        if (object instanceof CRLAnnContent) {
            return (CRLAnnContent)((Object)object);
        }
        if (object != null) {
            return new CRLAnnContent(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public CRLAnnContent(CertificateList certificateList) {
        this.content = new DERSequence((ASN1Encodable)certificateList);
    }

    public CertificateList[] getCertificateLists() {
        CertificateList[] certificateListArray = new CertificateList[this.content.size()];
        for (int i = 0; i != certificateListArray.length; ++i) {
            certificateListArray[i] = CertificateList.getInstance((Object)this.content.getObjectAt(i));
        }
        return certificateListArray;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }
}

