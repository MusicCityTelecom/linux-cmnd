/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.hk2.utilities;

import org.glassfish.hk2.utilities.RethrowErrorService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class EnableLookupExceptionsModule
extends AbstractBinder {
    @Override
    protected void configure() {
        this.addActiveDescriptor(RethrowErrorService.class);
    }
}

