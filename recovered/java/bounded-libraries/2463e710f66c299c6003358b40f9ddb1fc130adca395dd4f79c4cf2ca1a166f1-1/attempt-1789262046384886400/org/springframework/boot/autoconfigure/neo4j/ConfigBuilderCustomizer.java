/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.neo4j.driver.Config$ConfigBuilder
 */
package org.springframework.boot.autoconfigure.neo4j;

import org.neo4j.driver.Config;

@FunctionalInterface
public interface ConfigBuilderCustomizer {
    public void customize(Config.ConfigBuilder var1);
}

