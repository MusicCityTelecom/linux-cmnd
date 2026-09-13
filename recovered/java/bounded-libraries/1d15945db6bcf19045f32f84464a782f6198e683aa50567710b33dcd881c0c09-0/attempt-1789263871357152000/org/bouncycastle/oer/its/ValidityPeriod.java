/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.Duration;

public class ValidityPeriod
extends ASN1Object {
    private final ASN1Integer time32;
    private final Duration duration;

    public ValidityPeriod(ASN1Integer aSN1Integer, Duration duration) {
        this.time32 = aSN1Integer;
        this.duration = duration;
    }

    public static ValidityPeriod getInstance(Object object) {
        if (object instanceof ValidityPeriod) {
            return (ValidityPeriod)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        return new Builder().setTime32(ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0))).setDuration(Duration.getInstance(aSN1Sequence.getObjectAt(1))).createValidityPeriod();
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Integer getTime32() {
        return this.time32;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(new ASN1Encodable[]{this.time32, this.duration});
    }

    public static class Builder {
        private ASN1Integer time32;
        private Duration duration;

        public Builder setTime32(ASN1Integer aSN1Integer) {
            this.time32 = aSN1Integer;
            return this;
        }

        public Builder setDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public ValidityPeriod createValidityPeriod() {
            return new ValidityPeriod(this.time32, this.duration);
        }
    }
}

