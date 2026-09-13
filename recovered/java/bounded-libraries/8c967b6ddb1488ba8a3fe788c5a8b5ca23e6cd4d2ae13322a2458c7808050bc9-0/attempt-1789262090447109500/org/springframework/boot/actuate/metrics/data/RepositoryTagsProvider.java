/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  org.springframework.data.repository.core.support.RepositoryMethodInvocationListener$RepositoryMethodInvocation
 */
package org.springframework.boot.actuate.metrics.data;

import io.micrometer.core.instrument.Tag;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;

@FunctionalInterface
public interface RepositoryTagsProvider {
    public Iterable<Tag> repositoryTags(RepositoryMethodInvocationListener.RepositoryMethodInvocation var1);
}

