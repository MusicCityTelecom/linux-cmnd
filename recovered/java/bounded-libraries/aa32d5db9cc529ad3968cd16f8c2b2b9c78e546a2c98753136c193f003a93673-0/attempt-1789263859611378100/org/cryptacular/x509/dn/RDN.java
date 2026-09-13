/*
 * Decompiled with CFR 0.152.
 */
package org.cryptacular.x509.dn;

import org.cryptacular.x509.dn.Attributes;

public class RDN {
    private final Attributes attributes;

    public RDN(Attributes attributes) {
        if (attributes == null) {
            throw new IllegalArgumentException("Attributes cannot be null");
        }
        this.attributes = attributes;
    }

    public Attributes getAttributes() {
        return this.attributes;
    }
}

