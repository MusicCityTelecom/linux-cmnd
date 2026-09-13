/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1UTF8String
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERUTF8String
 */
package org.bouncycastle.asn1.ess;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1UTF8String;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERUTF8String;

public class ContentHints
extends ASN1Object {
    private ASN1UTF8String contentDescription;
    private ASN1ObjectIdentifier contentType;

    public static ContentHints getInstance(Object object) {
        if (object instanceof ContentHints) {
            return (ContentHints)((Object)object);
        }
        if (object != null) {
            return new ContentHints(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    private ContentHints(ASN1Sequence aSN1Sequence) {
        ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(0);
        if (aSN1Encodable.toASN1Primitive() instanceof ASN1UTF8String) {
            this.contentDescription = ASN1UTF8String.getInstance((Object)aSN1Encodable);
            this.contentType = ASN1ObjectIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(1));
        } else {
            this.contentType = ASN1ObjectIdentifier.getInstance((Object)aSN1Sequence.getObjectAt(0));
        }
    }

    public ContentHints(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.contentType = aSN1ObjectIdentifier;
        this.contentDescription = null;
    }

    public ContentHints(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1UTF8String aSN1UTF8String) {
        this.contentType = aSN1ObjectIdentifier;
        this.contentDescription = aSN1UTF8String;
    }

    public ASN1ObjectIdentifier getContentType() {
        return this.contentType;
    }

    public DERUTF8String getContentDescription() {
        return null == this.contentDescription || this.contentDescription instanceof DERUTF8String ? (DERUTF8String)this.contentDescription : new DERUTF8String(this.contentDescription.getString());
    }

    public ASN1UTF8String getContentDescriptionUTF8() {
        return this.contentDescription;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(2);
        if (this.contentDescription != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.contentDescription);
        }
        aSN1EncodableVector.add((ASN1Encodable)this.contentType);
        return new DERSequence(aSN1EncodableVector);
    }
}

