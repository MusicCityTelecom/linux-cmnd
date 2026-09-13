/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.core.instrument.Tag
 *  io.micrometer.core.instrument.Tags
 *  org.springframework.data.repository.core.support.RepositoryMethodInvocationListener$RepositoryMethodInvocation
 *  org.springframework.util.StringUtils
 */
package org.springframework.boot.actuate.metrics.data;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import java.lang.reflect.Method;
import java.util.function.Function;
import org.springframework.boot.actuate.metrics.data.RepositoryTagsProvider;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.util.StringUtils;

public class DefaultRepositoryTagsProvider
implements RepositoryTagsProvider {
    private static final Tag EXCEPTION_NONE = Tag.of((String)"exception", (String)"None");

    @Override
    public Iterable<Tag> repositoryTags(RepositoryMethodInvocationListener.RepositoryMethodInvocation invocation) {
        Tags tags = Tags.empty();
        tags = this.and(tags, invocation.getRepositoryInterface(), "repository", this::getSimpleClassName);
        tags = this.and(tags, invocation.getMethod(), "method", Method::getName);
        tags = this.and(tags, invocation.getResult().getState(), "state", Enum::name);
        tags = this.and(tags, invocation.getResult().getError(), "exception", this::getExceptionName, EXCEPTION_NONE);
        return tags;
    }

    private <T> Tags and(Tags tags, T instance, String key, Function<T, String> value) {
        return this.and(tags, instance, key, value, null);
    }

    private <T> Tags and(Tags tags, T instance, String key, Function<T, String> value, Tag fallback) {
        if (instance != null) {
            return tags.and(key, value.apply(instance));
        }
        return fallback != null ? tags.and(new Tag[]{fallback}) : tags;
    }

    private String getExceptionName(Throwable error) {
        return this.getSimpleClassName(error.getClass());
    }

    private String getSimpleClassName(Class<?> type) {
        String simpleName = type.getSimpleName();
        return !StringUtils.hasText((String)simpleName) ? type.getName() : simpleName;
    }
}

