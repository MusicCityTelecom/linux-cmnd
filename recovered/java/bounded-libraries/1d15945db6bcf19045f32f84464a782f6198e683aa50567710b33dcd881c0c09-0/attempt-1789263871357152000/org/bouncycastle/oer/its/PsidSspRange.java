/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.OEROptional;
import org.bouncycastle.oer.its.SspRange;

public class PsidSspRange
extends ASN1Object {
    private final ASN1Integer psid;
    private final OEROptional sspRange;

    public PsidSspRange(ASN1Integer aSN1Integer, OEROptional oEROptional) {
        this.psid = aSN1Integer;
        this.sspRange = oEROptional;
    }

    public PsidSspRange(ASN1Integer aSN1Integer, SspRange sspRange) {
        this.psid = aSN1Integer;
        this.sspRange = OEROptional.getInstance((Object)sspRange);
    }

    public static PsidSspRange getInstance(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof PsidSspRange) {
            return (PsidSspRange)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new PsidSspRange(ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0)), OEROptional.getInstance(aSN1Sequence.getObjectAt(1)));
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Integer getPsid() {
        return this.psid;
    }

    public OEROptional getSspRange() {
        return this.sspRange;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add((ASN1Encodable)this.psid);
        if (this.sspRange != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.sspRange);
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public static class Builder {
        private ASN1Integer psid;
        private OEROptional sspRange = OEROptional.ABSENT;

        public Builder setPsid(ASN1Integer aSN1Integer) {
            this.psid = aSN1Integer;
            return this;
        }

        public Builder setPsid(long l) {
            this.psid = new ASN1Integer(l);
            return this;
        }

        public Builder setSspRange(SspRange sspRange) {
            this.sspRange = OEROptional.getInstance((Object)sspRange);
            return this;
        }

        public PsidSspRange createPsidSspRange() {
            return new PsidSspRange(this.psid, this.sspRange);
        }
    }
}

