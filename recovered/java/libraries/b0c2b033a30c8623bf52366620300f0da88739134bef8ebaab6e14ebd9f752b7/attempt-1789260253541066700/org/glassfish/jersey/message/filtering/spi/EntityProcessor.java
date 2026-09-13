/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import org.glassfish.jersey.message.filtering.spi.EntityProcessorContext;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface EntityProcessor {
    public Result process(EntityProcessorContext var1);

    public static enum Result {
        APPLY,
        SKIP,
        ROLLBACK;

    }
}

