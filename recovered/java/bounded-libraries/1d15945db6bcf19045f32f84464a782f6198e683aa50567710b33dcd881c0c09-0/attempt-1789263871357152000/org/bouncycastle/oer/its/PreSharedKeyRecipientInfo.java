/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1OctetString
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.oer.its.HashedId;

public class PreSharedKeyRecipientInfo
extends HashedId {
    public PreSharedKeyRecipientInfo(byte[] byArray) {
        super(byArray);
    }

    public static PreSharedKeyRecipientInfo getInstance(Object object) {
        if (object instanceof PreSharedKeyRecipientInfo) {
            return (PreSharedKeyRecipientInfo)((Object)object);
        }
        ASN1OctetString aSN1OctetString = ASN1OctetString.getInstance((Object)object);
        return new PreSharedKeyRecipientInfo(aSN1OctetString.getOctets());
    }
}

