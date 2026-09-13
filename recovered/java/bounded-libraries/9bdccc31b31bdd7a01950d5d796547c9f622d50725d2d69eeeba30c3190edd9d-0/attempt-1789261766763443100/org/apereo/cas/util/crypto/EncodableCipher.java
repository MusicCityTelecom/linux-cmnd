/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.ArrayUtils
 */
package org.apereo.cas.util.crypto;

import org.apache.commons.lang3.ArrayUtils;

@FunctionalInterface
public interface EncodableCipher<I, O> {
    public O encode(I var1, Object[] var2);

    default public O encode(I value) {
        return this.encode(value, ArrayUtils.EMPTY_OBJECT_ARRAY);
    }
}

