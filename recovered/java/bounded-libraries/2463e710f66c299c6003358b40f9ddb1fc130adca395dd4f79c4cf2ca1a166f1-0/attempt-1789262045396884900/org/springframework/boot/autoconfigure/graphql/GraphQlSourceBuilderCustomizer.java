/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.graphql.execution.GraphQlSource$SchemaResourceBuilder
 */
package org.springframework.boot.autoconfigure.graphql;

import org.springframework.graphql.execution.GraphQlSource;

@FunctionalInterface
public interface GraphQlSourceBuilderCustomizer {
    public void customize(GraphQlSource.SchemaResourceBuilder var1);
}

