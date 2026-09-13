/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.util.Map;
import java.util.Set;

public interface EntityGraph {
    public EntityGraph addField(String var1);

    public EntityGraph addField(String var1, String ... var2);

    public EntityGraph addField(String var1, Set<String> var2);

    public EntityGraph addSubgraph(String var1, Class<?> var2);

    public EntityGraph addSubgraph(String var1, Class<?> var2, String ... var3);

    public EntityGraph addSubgraph(String var1, Class<?> var2, Set<String> var3);

    public EntityGraph addFilteringScopes(Set<String> var1);

    public boolean presentInScope(String var1, String var2);

    public boolean presentInScopes(String var1);

    public Class<?> getEntityClass();

    public Set<String> getFields(String var1);

    public Set<String> getFields(String ... var1);

    public Set<String> getFields(Set<String> var1);

    public Set<String> getFilteringScopes();

    public Set<String> getClassFilteringScopes();

    public Map<String, Class<?>> getSubgraphs(String var1);

    public Map<String, Class<?>> getSubgraphs(String ... var1);

    public Map<String, Class<?>> getSubgraphs(Set<String> var1);

    public EntityGraph remove(String var1);
}

