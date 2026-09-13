/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1IA5String
 *  org.bouncycastle.asn1.ASN1Integer
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.BERSequence
 *  org.bouncycastle.asn1.DERIA5String
 */
package org.bouncycastle.asn1.cms;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1IA5String;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.BERSequence;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.cms.Evidence;
import org.bouncycastle.asn1.cms.MetaData;

public class TimeStampedData
extends ASN1Object {
    private ASN1Integer version;
    private ASN1IA5String dataUri;
    private MetaData metaData;
    private ASN1OctetString content;
    private Evidence temporalEvidence;

    public TimeStampedData(ASN1IA5String aSN1IA5String, MetaData metaData, ASN1OctetString aSN1OctetString, Evidence evidence) {
        this.version = new ASN1Integer(1L);
        this.dataUri = aSN1IA5String;
        this.metaData = metaData;
        this.content = aSN1OctetString;
        this.temporalEvidence = evidence;
    }

    private TimeStampedData(ASN1Sequence aSN1Sequence) {
        this.version = ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(0));
        int n = 1;
        if (aSN1Sequence.getObjectAt(n) instanceof ASN1IA5String) {
            this.dataUri = ASN1IA5String.getInstance((Object)aSN1Sequence.getObjectAt(n++));
        }
        if (aSN1Sequence.getObjectAt(n) instanceof MetaData || aSN1Sequence.getObjectAt(n) instanceof ASN1Sequence) {
            this.metaData = MetaData.getInstance(aSN1Sequence.getObjectAt(n++));
        }
        if (aSN1Sequence.getObjectAt(n) instanceof ASN1OctetString) {
            this.content = ASN1OctetString.getInstance((Object)aSN1Sequence.getObjectAt(n++));
        }
        this.temporalEvidence = Evidence.getInstance(aSN1Sequence.getObjectAt(n));
    }

    public static TimeStampedData getInstance(Object object) {
        if (object == null || object instanceof TimeStampedData) {
            return (TimeStampedData)((Object)object);
        }
        return new TimeStampedData(ASN1Sequence.getInstance((Object)object));
    }

    public DERIA5String getDataUri() {
        return null == this.dataUri || this.dataUri instanceof DERIA5String ? (DERIA5String)this.dataUri : new DERIA5String(this.dataUri.getString(), false);
    }

    public ASN1IA5String getDataUriIA5() {
        return this.dataUri;
    }

    public MetaData getMetaData() {
        return this.metaData;
    }

    public ASN1OctetString getContent() {
        return this.content;
    }

    public Evidence getTemporalEvidence() {
        return this.temporalEvidence;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(5);
        aSN1EncodableVector.add((ASN1Encodable)this.version);
        if (this.dataUri != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.dataUri);
        }
        if (this.metaData != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.metaData);
        }
        if (this.content != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.content);
        }
        aSN1EncodableVector.add((ASN1Encodable)this.temporalEvidence);
        return new BERSequence(aSN1EncodableVector);
    }
}

