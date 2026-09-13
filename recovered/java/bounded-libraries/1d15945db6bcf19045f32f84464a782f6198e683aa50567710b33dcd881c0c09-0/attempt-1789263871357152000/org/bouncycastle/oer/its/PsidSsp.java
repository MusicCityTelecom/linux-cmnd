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
import org.bouncycastle.oer.OEROptional;
import org.bouncycastle.oer.its.Psid;
import org.bouncycastle.oer.its.ServiceSpecificPermissions;
import org.bouncycastle.oer.its.Utils;

public class PsidSsp
extends ASN1Object {
    private final Psid psid;
    private final ServiceSpecificPermissions ssp;

    public PsidSsp(Psid psid, ServiceSpecificPermissions serviceSpecificPermissions) {
        this.psid = psid;
        this.ssp = serviceSpecificPermissions;
    }

    public static PsidSsp getInstance(Object object) {
        if (object instanceof PsidSsp) {
            return (PsidSsp)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new PsidSsp(Psid.getInstance(aSN1Sequence.getObjectAt(0)), OEROptional.getValue(ServiceSpecificPermissions.class, aSN1Sequence.getObjectAt(1)));
    }

    public static Builder builder() {
        return new Builder();
    }

    public Psid getPsid() {
        return this.psid;
    }

    public ServiceSpecificPermissions getSsp() {
        return this.ssp;
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.psid, OEROptional.getInstance((Object)this.ssp)});
    }

    public static class Builder {
        private Psid psid;
        private ServiceSpecificPermissions ssp;

        public Builder setPsid(Psid psid) {
            this.psid = psid;
            return this;
        }

        public Builder setSsp(ServiceSpecificPermissions serviceSpecificPermissions) {
            this.ssp = serviceSpecificPermissions;
            return this;
        }

        public PsidSsp createPsidSsp() {
            return new PsidSsp(this.psid, this.ssp);
        }
    }
}

