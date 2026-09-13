/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.api;

import org.glassfish.hk2.api.Filter;

public interface IndexedFilter
extends Filter {
    public String getAdvertisedContract();

    public String getName();
}

