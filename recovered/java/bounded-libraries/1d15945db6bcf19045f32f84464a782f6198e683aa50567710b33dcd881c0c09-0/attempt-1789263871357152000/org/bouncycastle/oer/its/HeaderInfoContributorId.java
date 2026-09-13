/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Integer
 */
package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

public class HeaderInfoContributorId
extends ASN1Integer {
    public HeaderInfoContributorId(long l) {
        super(l);
    }

    public HeaderInfoContributorId(BigInteger bigInteger) {
        super(bigInteger);
    }

    public HeaderInfoContributorId(byte[] byArray) {
        super(byArray);
    }

    public static HeaderInfoContributorId getInstance(Object object) {
        if (object instanceof HeaderInfoContributorId) {
            return (HeaderInfoContributorId)((Object)object);
        }
        ASN1Integer aSN1Integer = ASN1Integer.getInstance((Object)object);
        return new HeaderInfoContributorId(aSN1Integer.getValue());
    }
}

