/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.internal;

import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.Immediate;

public class ImmediateLocalLocatorFilter
implements Filter {
    private final long locatorId;

    public ImmediateLocalLocatorFilter(long locatorId) {
        this.locatorId = locatorId;
    }

    @Override
    public boolean matches(Descriptor d) {
        String scope = d.getScope();
        if (scope == null) {
            return false;
        }
        if (d.getLocatorId() != this.locatorId) {
            return false;
        }
        return Immediate.class.getName().equals(scope);
    }
}

