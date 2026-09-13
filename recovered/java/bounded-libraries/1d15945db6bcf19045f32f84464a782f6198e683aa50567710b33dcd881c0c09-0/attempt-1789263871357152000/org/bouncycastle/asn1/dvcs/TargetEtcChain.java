/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.asn1.dvcs;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.dvcs.CertEtcToken;
import org.bouncycastle.asn1.dvcs.PathProcInput;

public class TargetEtcChain
extends ASN1Object {
    private CertEtcToken target;
    private ASN1Sequence chain;
    private PathProcInput pathProcInput;

    public TargetEtcChain(CertEtcToken certEtcToken) {
        this(certEtcToken, null, null);
    }

    public TargetEtcChain(CertEtcToken certEtcToken, CertEtcToken[] certEtcTokenArray) {
        this(certEtcToken, certEtcTokenArray, null);
    }

    public TargetEtcChain(CertEtcToken certEtcToken, PathProcInput pathProcInput) {
        this(certEtcToken, null, pathProcInput);
    }

    public TargetEtcChain(CertEtcToken certEtcToken, CertEtcToken[] certEtcTokenArray, PathProcInput pathProcInput) {
        this.target = certEtcToken;
        if (certEtcTokenArray != null) {
            this.chain = new DERSequence((ASN1Encodable[])certEtcTokenArray);
        }
        this.pathProcInput = pathProcInput;
    }

    private TargetEtcChain(ASN1Sequence aSN1Sequence) {
        int n = 0;
        ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(n++);
        this.target = CertEtcToken.getInstance(aSN1Encodable);
        if (aSN1Sequence.size() > 1) {
            if ((aSN1Encodable = aSN1Sequence.getObjectAt(n++)) instanceof ASN1TaggedObject) {
                this.extractPathProcInput(aSN1Encodable);
            } else {
                this.chain = ASN1Sequence.getInstance((Object)aSN1Encodable);
                if (aSN1Sequence.size() > 2) {
                    aSN1Encodable = aSN1Sequence.getObjectAt(n);
                    this.extractPathProcInput(aSN1Encodable);
                }
            }
        }
    }

    private void extractPathProcInput(ASN1Encodable aSN1Encodable) {
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)aSN1Encodable);
        switch (aSN1TaggedObject.getTagNo()) {
            case 0: {
                this.pathProcInput = PathProcInput.getInstance(aSN1TaggedObject, false);
                break;
            }
            default: {
                throw new IllegalArgumentException("Unknown tag encountered: " + aSN1TaggedObject.getTagNo());
            }
        }
    }

    public static TargetEtcChain getInstance(Object object) {
        if (object instanceof TargetEtcChain) {
            return (TargetEtcChain)((Object)object);
        }
        if (object != null) {
            return new TargetEtcChain(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public static TargetEtcChain getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return TargetEtcChain.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)bl));
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(3);
        aSN1EncodableVector.add((ASN1Encodable)this.target);
        if (this.chain != null) {
            aSN1EncodableVector.add((ASN1Encodable)this.chain);
        }
        if (this.pathProcInput != null) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 0, (ASN1Encodable)this.pathProcInput));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("TargetEtcChain {\n");
        stringBuffer.append("target: " + (Object)((Object)this.target) + "\n");
        if (this.chain != null) {
            stringBuffer.append("chain: " + this.chain + "\n");
        }
        if (this.pathProcInput != null) {
            stringBuffer.append("pathProcInput: " + (Object)((Object)this.pathProcInput) + "\n");
        }
        stringBuffer.append("}\n");
        return stringBuffer.toString();
    }

    public CertEtcToken getTarget() {
        return this.target;
    }

    public CertEtcToken[] getChain() {
        if (this.chain != null) {
            return CertEtcToken.arrayFromSequence(this.chain);
        }
        return null;
    }

    public PathProcInput getPathProcInput() {
        return this.pathProcInput;
    }

    public static TargetEtcChain[] arrayFromSequence(ASN1Sequence aSN1Sequence) {
        TargetEtcChain[] targetEtcChainArray = new TargetEtcChain[aSN1Sequence.size()];
        for (int i = 0; i != targetEtcChainArray.length; ++i) {
            targetEtcChainArray[i] = TargetEtcChain.getInstance(aSN1Sequence.getObjectAt(i));
        }
        return targetEtcChainArray;
    }
}

