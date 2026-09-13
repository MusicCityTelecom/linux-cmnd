/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509.dn;

import org.cryptacular.x509.dn.AttributeType;

public class Attribute {
    private final AttributeType type;
    private final String value;

    public Attribute(AttributeType type, String value) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null.");
        }
        this.type = type;
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        this.value = value;
    }

    public AttributeType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }
}

