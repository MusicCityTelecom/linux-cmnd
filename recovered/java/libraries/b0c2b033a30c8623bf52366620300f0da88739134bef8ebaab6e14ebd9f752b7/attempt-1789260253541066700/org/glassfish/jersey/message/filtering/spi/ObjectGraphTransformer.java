/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import org.glassfish.jersey.message.filtering.spi.ObjectGraph;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface ObjectGraphTransformer<T> {
    public T transform(ObjectGraph var1);
}

