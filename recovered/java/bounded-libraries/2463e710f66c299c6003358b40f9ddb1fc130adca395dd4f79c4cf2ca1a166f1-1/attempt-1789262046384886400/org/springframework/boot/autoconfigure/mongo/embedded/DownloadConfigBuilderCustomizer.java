/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  de.flapdoodle.embed.process.config.store.ImmutableDownloadConfig$Builder
 */
package org.springframework.boot.autoconfigure.mongo.embedded;

import de.flapdoodle.embed.process.config.store.ImmutableDownloadConfig;

@FunctionalInterface
public interface DownloadConfigBuilderCustomizer {
    public void customize(ImmutableDownloadConfig.Builder var1);
}

