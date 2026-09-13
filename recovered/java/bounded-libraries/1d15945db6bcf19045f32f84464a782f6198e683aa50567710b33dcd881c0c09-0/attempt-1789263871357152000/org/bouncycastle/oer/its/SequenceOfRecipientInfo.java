/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1EncodableVector
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1Sequence
 *  org.bouncycastle.asn1.DERSequence
 */
package org.bouncycastle.oer.its;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.RecipientInfo;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SequenceOfRecipientInfo
extends ASN1Object {
    private final List<RecipientInfo> recipientInfos;

    public SequenceOfRecipientInfo(List<RecipientInfo> list) {
        this.recipientInfos = Collections.unmodifiableList(list);
    }

    public static SequenceOfRecipientInfo getInstance(Object object) {
        if (object instanceof SequenceOfRecipientInfo) {
            return (SequenceOfRecipientInfo)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        Enumeration enumeration = aSN1Sequence.getObjects();
        ArrayList<RecipientInfo> arrayList = new ArrayList<RecipientInfo>();
        while (enumeration.hasMoreElements()) {
            arrayList.add(RecipientInfo.getInstance(enumeration.nextElement()));
        }
        return new Builder().setRecipientInfos(arrayList).createSequenceOfRecipientInfo();
    }

    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        return new DERSequence(aSN1EncodableVector);
    }

    public List<RecipientInfo> getRecipientInfos() {
        return this.recipientInfos;
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder {
        private List<RecipientInfo> recipientInfos;

        public Builder setRecipientInfos(List<RecipientInfo> list) {
            this.recipientInfos = list;
            return this;
        }

        public Builder addRecipients(RecipientInfo ... recipientInfoArray) {
            if (this.recipientInfos == null) {
                this.recipientInfos = new ArrayList<RecipientInfo>();
            }
            this.recipientInfos.addAll(Arrays.asList(recipientInfoArray));
            return this;
        }

        public SequenceOfRecipientInfo createSequenceOfRecipientInfo() {
            return new SequenceOfRecipientInfo(this.recipientInfos);
        }
    }
}

