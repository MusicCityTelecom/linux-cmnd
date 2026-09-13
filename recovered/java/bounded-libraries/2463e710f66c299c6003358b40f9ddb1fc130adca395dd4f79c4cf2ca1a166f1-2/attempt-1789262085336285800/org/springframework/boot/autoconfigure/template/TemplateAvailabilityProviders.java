/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.springframework.context.ApplicationContext
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.SpringFactoriesLoader
 *  org.springframework.util.Assert
 */
package org.springframework.boot.autoconfigure.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.Assert;

public class TemplateAvailabilityProviders {
    private final List<TemplateAvailabilityProvider> providers;
    private static final int CACHE_LIMIT = 1024;
    private static final TemplateAvailabilityProvider NONE = new NoTemplateAvailabilityProvider();
    private final Map<String, TemplateAvailabilityProvider> resolved = new ConcurrentHashMap<String, TemplateAvailabilityProvider>(1024);
    private final Map<String, TemplateAvailabilityProvider> cache = new LinkedHashMap<String, TemplateAvailabilityProvider>(1024, 0.75f, true){

        @Override
        protected boolean removeEldestEntry(Map.Entry<String, TemplateAvailabilityProvider> eldest) {
            if (this.size() > 1024) {
                TemplateAvailabilityProviders.this.resolved.remove(eldest.getKey());
                return true;
            }
            return false;
        }
    };

    public TemplateAvailabilityProviders(ApplicationContext applicationContext) {
        this(applicationContext != null ? applicationContext.getClassLoader() : null);
    }

    public TemplateAvailabilityProviders(ClassLoader classLoader) {
        Assert.notNull((Object)classLoader, (String)"ClassLoader must not be null");
        this.providers = SpringFactoriesLoader.loadFactories(TemplateAvailabilityProvider.class, (ClassLoader)classLoader);
    }

    protected TemplateAvailabilityProviders(Collection<? extends TemplateAvailabilityProvider> providers) {
        Assert.notNull(providers, (String)"Providers must not be null");
        this.providers = new ArrayList<TemplateAvailabilityProvider>(providers);
    }

    public List<TemplateAvailabilityProvider> getProviders() {
        return this.providers;
    }

    public TemplateAvailabilityProvider getProvider(String view, ApplicationContext applicationContext) {
        Assert.notNull((Object)applicationContext, (String)"ApplicationContext must not be null");
        return this.getProvider(view, applicationContext.getEnvironment(), applicationContext.getClassLoader(), (ResourceLoader)applicationContext);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TemplateAvailabilityProvider getProvider(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        Assert.notNull((Object)view, (String)"View must not be null");
        Assert.notNull((Object)environment, (String)"Environment must not be null");
        Assert.notNull((Object)classLoader, (String)"ClassLoader must not be null");
        Assert.notNull((Object)resourceLoader, (String)"ResourceLoader must not be null");
        Boolean useCache = (Boolean)environment.getProperty("spring.template.provider.cache", Boolean.class, (Object)true);
        if (!useCache.booleanValue()) {
            return this.findProvider(view, environment, classLoader, resourceLoader);
        }
        TemplateAvailabilityProvider provider = this.resolved.get(view);
        if (provider == null) {
            Map<String, TemplateAvailabilityProvider> map = this.cache;
            synchronized (map) {
                provider = this.findProvider(view, environment, classLoader, resourceLoader);
                provider = provider != null ? provider : NONE;
                this.resolved.put(view, provider);
                this.cache.put(view, provider);
            }
        }
        return provider != NONE ? provider : null;
    }

    private TemplateAvailabilityProvider findProvider(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        for (TemplateAvailabilityProvider candidate : this.providers) {
            if (!candidate.isTemplateAvailable(view, environment, classLoader, resourceLoader)) continue;
            return candidate;
        }
        return null;
    }

    private static class NoTemplateAvailabilityProvider
    implements TemplateAvailabilityProvider {
        private NoTemplateAvailabilityProvider() {
        }

        @Override
        public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
            return false;
        }
    }
}

