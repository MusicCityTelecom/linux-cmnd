/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509.dn;

import java.util.regex.Pattern;
import org.cryptacular.x509.dn.AttributeType;

public class UnknownAttributeType
implements AttributeType {
    private static final Pattern PATTERN = Pattern.compile("[0-9]+(.[0-9]+)*");
    private final String oid;

    public UnknownAttributeType(String attributeTypeOid) {
        if (!PATTERN.matcher(attributeTypeOid).matches()) {
            throw new IllegalArgumentException(attributeTypeOid + " is not an OID");
        }
        this.oid = attributeTypeOid;
    }

    @Override
    public String getOid() {
        return this.oid;
    }

    @Override
    public String getName() {
        return this.oid;
    }

    public String toString() {
        return this.oid;
    }
}

