/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.internal.util.collection;

import org.glassfish.jersey.internal.util.collection.Value;

public interface LazyValue<T>
extends Value<T> {
    public boolean isInitialized();
}

