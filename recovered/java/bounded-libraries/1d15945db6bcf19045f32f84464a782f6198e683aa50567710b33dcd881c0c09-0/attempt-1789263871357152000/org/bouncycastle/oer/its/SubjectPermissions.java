/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Choice
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1OctetString
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1TaggedObject
 *  org.bouncycastle.asn1.DERNull
 *  org.bouncycastle.asn1.DEROctetString
 *  org.bouncycastle.asn1.DERTaggedObject
 */
package org.bouncycastle.oer.its;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Choice;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1TaggedObject;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.oer.its.SequenceOfPsidSspRange;

public class SubjectPermissions
extends ASN1Object
implements ASN1Choice {
    public static final int explicit = 0;
    public static final int all = 1;
    public static final int extension = 3;
    private final ASN1Encodable value;
    private final int choice;

    SubjectPermissions(int n, ASN1Encodable aSN1Encodable) {
        this.value = aSN1Encodable;
        this.choice = n;
    }

    public static SubjectPermissions getInstance(Object object) {
        if (object instanceof SubjectPermissions) {
            return (SubjectPermissions)((Object)object);
        }
        ASN1TaggedObject aSN1TaggedObject = ASN1TaggedObject.getInstance((Object)object);
        int n = aSN1TaggedObject.getTagNo();
        switch (n) {
            case 0: {
                return new SubjectPermissions(0, (ASN1Encodable)SequenceOfPsidSspRange.getInstance(aSN1TaggedObject.getObject()));
            }
            case 1: {
                return new SubjectPermissions(1, (ASN1Encodable)DERNull.INSTANCE);
            }
            case 3: {
                try {
                    return new SubjectPermissions(3, (ASN1Encodable)new DEROctetString(aSN1TaggedObject.getObject().getEncoded()));
                }
                catch (IOException iOException) {
                    throw new RuntimeException(iOException.getMessage(), iOException);
                }
            }
        }
        return null;
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERTaggedObject(this.choice, this.value);
    }

    public static class Builder {
        int choice;
        ASN1Encodable value;

        public Builder choice(int n) {
            this.choice = n;
            return this;
        }

        public Builder value(ASN1Encodable aSN1Encodable) {
            this.value = aSN1Encodable;
            return this;
        }

        public Builder explicit(SequenceOfPsidSspRange sequenceOfPsidSspRange) {
            this.choice = 0;
            this.value = sequenceOfPsidSspRange;
            return this;
        }

        public Builder all() {
            this.choice = 1;
            this.value = DERNull.INSTANCE;
            return this;
        }

        public Builder extension(ASN1Encodable aSN1Encodable) {
            this.choice = 3;
            if (aSN1Encodable instanceof ASN1OctetString) {
                this.value = aSN1Encodable;
            } else {
                try {
                    this.value = new DEROctetString(aSN1Encodable.toASN1Primitive().getEncoded());
                }
                catch (IOException iOException) {
                    throw new RuntimeException(iOException.getMessage(), iOException);
                }
            }
            return this;
        }

        public SubjectPermissions createSubjectPermissions() {
            return new SubjectPermissions(this.choice, this.value);
        }
    }
}

