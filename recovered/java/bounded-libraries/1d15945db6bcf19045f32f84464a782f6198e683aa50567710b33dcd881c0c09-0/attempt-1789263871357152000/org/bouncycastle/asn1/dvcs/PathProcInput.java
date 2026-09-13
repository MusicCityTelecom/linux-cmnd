/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Boolean
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERSequence
 *  org.bouncycastle.asn1.DERTaggedObject
 *  org.bouncycastle.asn1.x509.PolicyInformation
 */
package org.bouncycastle.asn1.dvcs;

import java.util.Arrays;
import org.bouncycastle.asn1.ASN1Boolean;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.x509.PolicyInformation;

public class PathProcInput
extends ASN1Object {
    private PolicyInformation[] acceptablePolicySet;
    private boolean inhibitPolicyMapping = false;
    private boolean explicitPolicyReqd = false;
    private boolean inhibitAnyPolicy = false;

    public PathProcInput(PolicyInformation[] policyInformationArray) {
        this.acceptablePolicySet = this.copy(policyInformationArray);
    }

    public PathProcInput(PolicyInformation[] policyInformationArray, boolean bl, boolean bl2, boolean bl3) {
        this.acceptablePolicySet = this.copy(policyInformationArray);
        this.inhibitPolicyMapping = bl;
        this.explicitPolicyReqd = bl2;
        this.inhibitAnyPolicy = bl3;
    }

    private static PolicyInformation[] fromSequence(ASN1Sequence aSN1Sequence) {
        PolicyInformation[] policyInformationArray = new PolicyInformation[aSN1Sequence.size()];
        for (int i = 0; i != policyInformationArray.length; ++i) {
            policyInformationArray[i] = PolicyInformation.getInstance((Object)aSN1Sequence.getObjectAt(i));
        }
        return policyInformationArray;
    }

    public static PathProcInput getInstance(Object object) {
        if (object instanceof PathProcInput) {
            return (PathProcInput)((Object)object);
        }
        if (object != null) {
            ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
            ASN1Sequence aSN1Sequence2 = ASN1Sequence.getInstance((Object)aSN1Sequence.getObjectAt(0));
            PathProcInput pathProcInput = new PathProcInput(PathProcInput.fromSequence(aSN1Sequence2));
            block4: for (int i = 1; i < aSN1Sequence.size(); ++i) {
                ASN1Boolean aSN1Boolean;
                ASN1Encodable aSN1Encodable = aSN1Sequence.getObjectAt(i);
                if (aSN1Encodable instanceof ASN1Boolean) {
                    aSN1Boolean = ASN1Boolean.getInstance((Object)aSN1Encodable);
                    pathProcInput.setInhibitPolicyMapping(aSN1Boolean.isTrue());
                    continue;
                }
                if (!(aSN1Encodable instanceof ASN1TaggedObject)) continue;
                aSN1Boolean = ASN1TaggedObject.getInstance((Object)aSN1Encodable);
                switch (aSN1Boolean.getTagNo()) {
                    case 0: {
                        ASN1Boolean aSN1Boolean2 = ASN1Boolean.getInstance((ASN1TaggedObject)aSN1Boolean, (boolean)false);
                        pathProcInput.setExplicitPolicyReqd(aSN1Boolean2.isTrue());
                        continue block4;
                    }
                    case 1: {
                        ASN1Boolean aSN1Boolean2 = ASN1Boolean.getInstance((ASN1TaggedObject)aSN1Boolean, (boolean)false);
                        pathProcInput.setInhibitAnyPolicy(aSN1Boolean2.isTrue());
                        continue block4;
                    }
                    default: {
                        throw new IllegalArgumentException("Unknown tag encountered: " + aSN1Boolean.getTagNo());
                    }
                }
            }
            return pathProcInput;
        }
        return null;
    }

    public static PathProcInput getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return PathProcInput.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1TaggedObject, (boolean)bl));
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector(4);
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector(this.acceptablePolicySet.length);
        for (int i = 0; i != this.acceptablePolicySet.length; ++i) {
            aSN1EncodableVector2.add((ASN1Encodable)this.acceptablePolicySet[i]);
        }
        aSN1EncodableVector.add((ASN1Encodable)new DERSequence(aSN1EncodableVector2));
        if (this.inhibitPolicyMapping) {
            aSN1EncodableVector.add((ASN1Encodable)ASN1Boolean.getInstance((boolean)this.inhibitPolicyMapping));
        }
        if (this.explicitPolicyReqd) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 0, (ASN1Encodable)ASN1Boolean.getInstance((boolean)this.explicitPolicyReqd)));
        }
        if (this.inhibitAnyPolicy) {
            aSN1EncodableVector.add((ASN1Encodable)new DERTaggedObject(false, 1, (ASN1Encodable)ASN1Boolean.getInstance((boolean)this.inhibitAnyPolicy)));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public String toString() {
        return "PathProcInput: {\nacceptablePolicySet: " + Arrays.asList(this.acceptablePolicySet) + "\ninhibitPolicyMapping: " + this.inhibitPolicyMapping + "\nexplicitPolicyReqd: " + this.explicitPolicyReqd + "\ninhibitAnyPolicy: " + this.inhibitAnyPolicy + "\n}\n";
    }

    public PolicyInformation[] getAcceptablePolicySet() {
        return this.copy(this.acceptablePolicySet);
    }

    public boolean isInhibitPolicyMapping() {
        return this.inhibitPolicyMapping;
    }

    private void setInhibitPolicyMapping(boolean bl) {
        this.inhibitPolicyMapping = bl;
    }

    public boolean isExplicitPolicyReqd() {
        return this.explicitPolicyReqd;
    }

    private void setExplicitPolicyReqd(boolean bl) {
        this.explicitPolicyReqd = bl;
    }

    public boolean isInhibitAnyPolicy() {
        return this.inhibitAnyPolicy;
    }

    private void setInhibitAnyPolicy(boolean bl) {
        this.inhibitAnyPolicy = bl;
    }

    private PolicyInformation[] copy(PolicyInformation[] policyInformationArray) {
        PolicyInformation[] policyInformationArray2 = new PolicyInformation[policyInformationArray.length];
        System.arraycopy(policyInformationArray, 0, policyInformationArray2, 0, policyInformationArray2.length);
        return policyInformationArray2;
    }
}

