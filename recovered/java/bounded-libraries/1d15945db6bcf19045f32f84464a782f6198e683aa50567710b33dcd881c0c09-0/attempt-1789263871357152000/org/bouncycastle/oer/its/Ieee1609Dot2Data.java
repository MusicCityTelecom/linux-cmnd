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

import java.util.Iterator;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.oer.its.Ieee1609Dot2Content;
import org.bouncycastle.oer.its.Uint8;
import org.bouncycastle.oer.its.Utils;

public class Ieee1609Dot2Data
extends ASN1Object {
    private final Uint8 protocolVersion;
    private final Ieee1609Dot2Content content;

    public Ieee1609Dot2Data(Uint8 uint8, Ieee1609Dot2Content ieee1609Dot2Content) {
        this.protocolVersion = uint8;
        this.content = ieee1609Dot2Content;
    }

    public static Ieee1609Dot2Data getInstance(Object object) {
        if (object instanceof Ieee1609Dot2Data) {
            return (Ieee1609Dot2Data)((Object)object);
        }
        Iterator iterator = ASN1Sequence.getInstance((Object)object).iterator();
        return new Ieee1609Dot2Data(Uint8.getInstance(iterator.next()), Ieee1609Dot2Content.getInstance(iterator.next()));
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Primitive toASN1Primitive() {
        return Utils.toSequence(new ASN1Encodable[]{this.protocolVersion, this.content});
    }

    public Uint8 getProtocolVersion() {
        return this.protocolVersion;
    }

    public Ieee1609Dot2Content getContent() {
        return this.content;
    }

    public static class Builder {
        private Uint8 protocolVersion;
        private Ieee1609Dot2Content content;

        public Builder setProtocolVersion(Uint8 uint8) {
            this.protocolVersion = uint8;
            return this;
        }

        public Builder setContent(Ieee1609Dot2Content ieee1609Dot2Content) {
            this.content = ieee1609Dot2Content;
            return this;
        }

        public Ieee1609Dot2Data build() {
            return new Ieee1609Dot2Data(this.protocolVersion, this.content);
        }
    }
}

