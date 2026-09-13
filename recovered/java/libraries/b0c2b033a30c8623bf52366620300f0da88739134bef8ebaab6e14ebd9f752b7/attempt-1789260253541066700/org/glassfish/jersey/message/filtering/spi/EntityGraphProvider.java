/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.util.Set;
import org.glassfish.jersey.message.filtering.spi.EntityGraph;
import org.glassfish.jersey.message.filtering.spi.ObjectGraph;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface EntityGraphProvider {
    public EntityGraph getOrCreateEntityGraph(Class<?> var1, boolean var2);

    public EntityGraph getOrCreateEmptyEntityGraph(Class<?> var1, boolean var2);

    public boolean containsEntityGraph(Class<?> var1, boolean var2);

    public ObjectGraph createObjectGraph(Class<?> var1, Set<String> var2, boolean var3);

    public EntityGraph putIfAbsent(Class<?> var1, EntityGraph var2, boolean var3);
}

