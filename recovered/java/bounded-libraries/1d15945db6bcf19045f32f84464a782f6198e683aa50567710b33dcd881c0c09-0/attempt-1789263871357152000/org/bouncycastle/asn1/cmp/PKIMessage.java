/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1BitString
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERBitString
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.asn1.cmp;

import java.util.Enumeration;
import org.bouncycastle.asn1.ASN1BitString;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.cmp.CMPCertificate;
import org.bouncycastle.asn1.cmp.PKIBody;
import org.bouncycastle.asn1.cmp.PKIHeader;

public class PKIMessage
extends ASN1Object {
    private PKIHeader header;
    private PKIBody body;
    private ASN1BitString protection;
    private ASN1Sequence extraCerts;

    private PKIMessage(ASN1Sequence aSN1Sequence) {
        Enumeration enumeration = aSN1Sequence.getObjects();
        this.header = PKIHeader.getInstance(enumeration.nextElement());
        this.body = PKIBody.getInstance(enumeration.nextElement());
        while (enumeration.hasMoreElements()) {
            ASN1TaggedObject aSN1TaggedObject = (ASN1TaggedObject)enumeration.nextElement();
            if (aSN1TaggedObject.getTagNo() == 0) {
                this.protection = DERBitString.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)true);
                continue;
            }
            this.extraCerts = ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)true);
        }
    }

    public static PKIMessage getInstance(Object object) {
        if (object instanceof PKIMessage) {
            return (PKIMessage)((Object)object);
        }
        if (object != null) {
            return new PKIMessage(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public PKIMessage(PKIHeader pKIHeader, PKIBody pKIBody, ASN1BitString aSN1BitString, CMPCertificate[] cMPCertificateArray) {
        this.header = pKIHeader;
        this.body = pKIBody;
        this.protection = aSN1BitString;
        if (cMPCertificateArray != null) {
            this.extraCerts = new DERSequence((ASN1Encodable[])cMPCertificateArray);
        }
    }

    public PKIMessage(PKIHeader pKIHeader, PKIBody pKIBody, ASN1BitString aSN1BitString) {
        this(pKIHeader, pKIBody, aSN1BitString, null);
    }

    public PKIMessage(PKIHeader pKIHeader, PKIBody pKIBody) {
        this(pKIHeader, pKIBody, null, null);
    }

    public PKIHeader getHeader() {
        return this.header;
    }

    public PKIBody getBody() {
        return this.body;
    }

    public ASN1BitString getProtection() {
        return this.protection;
    }

    public CMPCertificate[] getExtraCerts() {
        if (this.extraCerts == null) {
            return null;
        }
        CMPCertificate[] cMPCertificateArray = new CMPCertificate[this.extraCerts.size()];
        for (int i = 0; i < cMPCertificateArray.length; ++i) {
            cMPCertificateArray[i] = CMPCertificate.getInstance(this.extraCerts.getObjectAt(i));
        }
        return cMPCertificateArray;
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        aSN1EncodableVector.add((ASN1Encodable)this.header);
        aSN1EncodableVector.add((ASN1Encodable)this.body);
        this.addOptional(aSN1EncodableVector, 0, (ASN1Encodable)this.protection);
        this.addOptional(aSN1EncodableVector, 1, (ASN1Encodable)this.extraCerts);
        return new DERSequence(aSN1EncodableVector);
    }

    private void addOptional(ASN1EncodableVector aSN1EncodableVector, int n, ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable != null) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(true, n, aSN1Encodable));
        }
    }
}

