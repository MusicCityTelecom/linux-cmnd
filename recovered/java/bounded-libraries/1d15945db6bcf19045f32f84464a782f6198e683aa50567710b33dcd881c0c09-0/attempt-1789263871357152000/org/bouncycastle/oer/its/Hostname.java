/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Object
 *  org.bouncycastle.asn1.ASN1Primitive
 *  org.bouncycastle.asn1.ASN1String
 *  org.bouncycastle.asn1.DERUTF8String
 */
package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DERUTF8String;

public class Hostname
extends ASN1Object {
    private final String hostName;

    public Hostname(String string) {
        this.hostName = string;
    }

    public static Hostname getInstance(Object object) {
        if (object instanceof Hostname) {
            return (Hostname)((Object)object);
        }
        if (object instanceof String) {
            return new Hostname((String)object);
        }
        if (object instanceof ASN1String) {
            return new Hostname(((ASN1String)object).getString());
        }
        throw new IllegalArgumentException("hostname accepts Hostname, String and ASN1String");
    }

    public ASN1Primitive toASN1Primitive() {
        return new DERUTF8String(this.hostName);
    }
}

