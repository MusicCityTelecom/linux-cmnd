/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 */
package org.apereo.cas.validation;

import lombok.Generated;

public enum ValidationResponseType {
    XML(true),
    JSON(false);

    private final boolean encodingNecessary;

    @Generated
    private ValidationResponseType(boolean encodingNecessary) {
        this.encodingNecessary = encodingNecessary;
    }

    @Generated
    public boolean isEncodingNecessary() {
        return this.encodingNecessary;
    }
}

