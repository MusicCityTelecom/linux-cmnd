/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.context.ApplicationContextAware
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.support.ResourcePatternResolver
 *  org.springframework.messaging.MessagingException
 *  org.springframework.util.Assert
 *  org.springframework.util.CollectionUtils
 *  org.springframework.util.ObjectUtils
 */
package org.springframework.integration.resource;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.integration.util.CollectionFilter;
import org.springframework.messaging.MessagingException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

public class ResourceRetrievingMessageSource
extends AbstractMessageSource<Resource[]>
implements ApplicationContextAware {
    private final String pattern;
    private volatile ApplicationContext applicationContext;
    private volatile ResourcePatternResolver patternResolver;
    private volatile CollectionFilter<Resource> filter;

    public ResourceRetrievingMessageSource(String pattern) {
        Assert.hasText((String)pattern, (String)"pattern must not be empty");
        this.pattern = pattern;
    }

    public void setPatternResolver(ResourcePatternResolver patternResolver) {
        this.patternResolver = patternResolver;
    }

    public void setFilter(CollectionFilter<Resource> filter) {
        this.filter = filter;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public String getComponentType() {
        return "resource-inbound-channel-adapter";
    }

    @Override
    protected void onInit() {
        if (this.patternResolver == null) {
            this.patternResolver = this.applicationContext;
        }
        Assert.notNull((Object)this.patternResolver, (String)"no 'patternResolver' available");
    }

    protected Resource[] doReceive() {
        try {
            Object resources = this.patternResolver.getResources(this.pattern);
            if (ObjectUtils.isEmpty((Object[])resources)) {
                resources = null;
            } else if (this.filter != null) {
                Collection<Object> filteredResources = this.filter.filter(Arrays.asList(resources));
                resources = CollectionUtils.isEmpty(filteredResources) ? null : filteredResources.toArray(new Resource[0]);
            }
            return resources;
        }
        catch (Exception e) {
            throw new MessagingException("Attempt to retrieve Resources failed", (Throwable)e);
        }
    }
}

