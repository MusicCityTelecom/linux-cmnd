/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.message.filtering.spi;

import java.util.Map;
import java.util.Set;

public interface ObjectGraph {
    public Class<?> getEntityClass();

    public Set<String> getFields();

    public Set<String> getFields(String var1);

    public Map<String, ObjectGraph> getSubgraphs();

    public Map<String, ObjectGraph> getSubgraphs(String var1);
}

