/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.x509.Extension
 */
package org.bouncycastle.asn1.cmc;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cmc.Utils;
import org.bouncycastle.asn1.x509.Extension;

public class ExtensionReq
extends ASN1Object {
    private final Extension[] extensions;

    public static ExtensionReq getInstance(Object object) {
        if (object instanceof ExtensionReq) {
            return (ExtensionReq)((Object)object);
        }
        if (object != null) {
            return new ExtensionReq(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public static ExtensionReq getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return ExtensionReq.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)bl));
    }

    public ExtensionReq(Extension extension) {
        this.extensions = new Extension[]{extension};
    }

    public ExtensionReq(Extension[] extensionArray) {
        this.extensions = Utils.clone(extensionArray);
    }

    private ExtensionReq(ASN1Sequence aSN1Sequence) {
        this.extensions = new Extension[aSN1Sequence.size()];
        for (int i = 0; i != aSN1Sequence.size(); ++i) {
            this.extensions[i] = Extension.getInstance((Object)aSN1Sequence.getObjectAt(i));
        }
    }

    public Extension[] getExtensions() {
        return Utils.clone(this.extensions);
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[])this.extensions);
    }
}

