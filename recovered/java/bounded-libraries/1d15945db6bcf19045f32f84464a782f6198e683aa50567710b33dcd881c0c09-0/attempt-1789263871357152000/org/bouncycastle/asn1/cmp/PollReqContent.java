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
package org.bouncycastle.asn1.cmp;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;

public class PollReqContent
extends ASN1Object {
    private ASN1Sequence content;

    private PollReqContent(ASN1Sequence aSN1Sequence) {
        this.content = aSN1Sequence;
    }

    public static PollReqContent getInstance(Object object) {
        if (object instanceof PollReqContent) {
            return (PollReqContent)((Object)object);
        }
        if (object != null) {
            return new PollReqContent(ASN1Sequence.getInstance((Object)object));
        }
        return null;
    }

    public PollReqContent(ASN1Integer aSN1Integer) {
        this((ASN1Sequence)new DERSequence((ASN1Encodable)new DERSequence((ASN1Encodable)aSN1Integer)));
    }

    public PollReqContent(ASN1Integer[] aSN1IntegerArray) {
        this((ASN1Sequence)new DERSequence((ASN1Encodable[])PollReqContent.intsToSequence(aSN1IntegerArray)));
    }

    public PollReqContent(BigInteger bigInteger) {
        this(new ASN1Integer(bigInteger));
    }

    public PollReqContent(BigInteger[] bigIntegerArray) {
        this(PollReqContent.intsToASN1(bigIntegerArray));
    }

    public ASN1Integer[][] getCertReqIds() {
        ASN1Integer[][] aSN1IntegerArrayArray = new ASN1Integer[this.content.size()][];
        for (int i = 0; i != aSN1IntegerArrayArray.length; ++i) {
            aSN1IntegerArrayArray[i] = PollReqContent.sequenceToASN1IntegerArray((ASN1Sequence)this.content.getObjectAt(i));
        }
        return aSN1IntegerArrayArray;
    }

    public BigInteger[] getCertReqIdValues() {
        BigInteger[] bigIntegerArray = new BigInteger[this.content.size()];
        for (int i = 0; i != bigIntegerArray.length; ++i) {
            bigIntegerArray[i] = ASN1Integer.getInstance((Object)ASN1Sequence.getInstance((Object)this.content.getObjectAt(i)).getObjectAt(0)).getValue();
        }
        return bigIntegerArray;
    }

    private static ASN1Integer[] sequenceToASN1IntegerArray(ASN1Sequence aSN1Sequence) {
        ASN1Integer[] aSN1IntegerArray = new ASN1Integer[aSN1Sequence.size()];
        for (int i = 0; i != aSN1IntegerArray.length; ++i) {
            aSN1IntegerArray[i] = ASN1Integer.getInstance((Object)aSN1Sequence.getObjectAt(i));
        }
        return aSN1IntegerArray;
    }

    private static DERSequence[] intsToSequence(ASN1Integer[] aSN1IntegerArray) {
        DERSequence[] dERSequenceArray = new DERSequence[aSN1IntegerArray.length];
        for (int i = 0; i != dERSequenceArray.length; ++i) {
            dERSequenceArray[i] = new DERSequence((ASN1Encodable)aSN1IntegerArray[i]);
        }
        return dERSequenceArray;
    }

    private static ASN1Integer[] intsToASN1(BigInteger[] bigIntegerArray) {
        ASN1Integer[] aSN1IntegerArray = new ASN1Integer[bigIntegerArray.length];
        for (int i = 0; i != aSN1IntegerArray.length; ++i) {
            aSN1IntegerArray[i] = new ASN1Integer(bigIntegerArray[i]);
        }
        return aSN1IntegerArray;
    }

    public ASN1Primitive toASN1Primitive() {
        return this.content;
    }
}

