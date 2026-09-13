/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
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
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.oer.its.PsidGroupPermissions;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SequenceOfPsidGroupPermissions
extends ASN1Object {
    private final List<PsidGroupPermissions> groupPermissions;

    public SequenceOfPsidGroupPermissions(List<PsidGroupPermissions> list) {
        this.groupPermissions = Collections.unmodifiableList(list);
    }

    public static SequenceOfPsidGroupPermissions getInstance(Object object) {
        if (object instanceof SequenceOfPsidGroupPermissions) {
            return (SequenceOfPsidGroupPermissions)((Object)object);
        }
        ASN1Sequence aSN1Sequence = ASN1Sequence.getInstance((Object)object);
        ArrayList<PsidGroupPermissions> arrayList = new ArrayList<PsidGroupPermissions>();
        Enumeration enumeration = aSN1Sequence.getObjects();
        while (enumeration.hasMoreElements()) {
            arrayList.add(PsidGroupPermissions.getInstance(enumeration.nextElement()));
        }
        return new Builder().setGroupPermissions(arrayList).createSequenceOfPsidGroupPermissions();
    }

    public static Builder builder() {
        return new Builder();
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERSequence((ASN1Encodable[])this.groupPermissions.toArray(new PsidGroupPermissions[0]));
    }

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Builder {
        private List<PsidGroupPermissions> groupPermissions = new ArrayList<PsidGroupPermissions>();

        public Builder setGroupPermissions(List<PsidGroupPermissions> list) {
            this.groupPermissions.addAll(list);
            return this;
        }

        public Builder addGroupPermission(PsidGroupPermissions ... psidGroupPermissionsArray) {
            this.groupPermissions.addAll(Arrays.asList(psidGroupPermissionsArray));
            return this;
        }

        public SequenceOfPsidGroupPermissions createSequenceOfPsidGroupPermissions() {
            return new SequenceOfPsidGroupPermissions(this.groupPermissions);
        }
    }
}

