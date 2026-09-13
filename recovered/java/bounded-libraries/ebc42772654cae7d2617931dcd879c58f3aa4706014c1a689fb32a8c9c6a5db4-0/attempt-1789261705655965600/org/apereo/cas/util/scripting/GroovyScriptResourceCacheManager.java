/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.github.benmanes.caffeine.cache.Cache
 *  com.github.benmanes.caffeine.cache.Caffeine
 *  com.google.errorprone.annotations.CanIgnoreReturnValue
 *  lombok.Generated
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.core.io.AbstractResource
 *  org.springframework.core.io.Resource
 */
package org.apereo.cas.util.scripting;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import lombok.Generated;
import org.apereo.cas.util.LoggingUtils;
import org.apereo.cas.util.ResourceUtils;
import org.apereo.cas.util.scripting.ExecutableCompiledGroovyScript;
import org.apereo.cas.util.scripting.GroovyShellScript;
import org.apereo.cas.util.scripting.ScriptResourceCacheManager;
import org.apereo.cas.util.scripting.ScriptingUtils;
import org.apereo.cas.util.scripting.WatchableGroovyScriptResource;
import org.apereo.cas.util.spring.SpringExpressionLanguageValueResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.AbstractResource;
import org.springframework.core.io.Resource;

public class GroovyScriptResourceCacheManager
implements ScriptResourceCacheManager<String, ExecutableCompiledGroovyScript> {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyScriptResourceCacheManager.class);
    private static final Duration EXPIRATION_AFTER_ACCESS = Duration.ofHours(8L);
    private final Cache<String, ExecutableCompiledGroovyScript> cache = Caffeine.newBuilder().initialCapacity(100).maximumSize(1000L).expireAfterAccess(EXPIRATION_AFTER_ACCESS).removalListener((key, value, cause) -> {
        LOGGER.trace("Removing script [{}] from cache under [{}]; removal cause is [{}]", new Object[]{value, key, cause});
        Objects.requireNonNull(value).close();
    }).build();

    @Override
    public ExecutableCompiledGroovyScript get(String key) {
        return (ExecutableCompiledGroovyScript)this.cache.getIfPresent((Object)key);
    }

    @Override
    public boolean containsKey(String key) {
        return this.get(key) != null;
    }

    @Override
    @CanIgnoreReturnValue
    public ScriptResourceCacheManager<String, ExecutableCompiledGroovyScript> put(String key, ExecutableCompiledGroovyScript value) {
        this.cache.put((Object)key, (Object)value);
        return this;
    }

    @Override
    @CanIgnoreReturnValue
    public ScriptResourceCacheManager<String, ExecutableCompiledGroovyScript> remove(String key) {
        this.cache.invalidate((Object)key);
        return this;
    }

    @Override
    public void close() {
        this.cache.invalidateAll();
    }

    @Override
    public boolean isEmpty() {
        return this.cache.asMap().isEmpty();
    }

    @Override
    public ExecutableCompiledGroovyScript resolveScriptableResource(String scriptResource, String ... keys) {
        String cacheKey = ScriptResourceCacheManager.computeKey(keys);
        LOGGER.trace("Constructed cache key [{}] for keys [{}] mapped as groovy script", (Object)cacheKey, (Object)keys);
        ExecutableCompiledGroovyScript script = null;
        if (this.containsKey(cacheKey)) {
            script = this.get(cacheKey);
            LOGGER.trace("Located cached groovy script [{}] for key [{}]", (Object)script, (Object)cacheKey);
        } else {
            try {
                if (ScriptingUtils.isExternalGroovyScript(scriptResource)) {
                    String scriptPath = SpringExpressionLanguageValueResolver.getInstance().resolve(scriptResource);
                    AbstractResource resource = ResourceUtils.getResourceFrom(scriptPath);
                    script = new WatchableGroovyScriptResource((Resource)resource);
                } else {
                    Matcher matcher;
                    String resourceToUse = scriptResource;
                    if (ScriptingUtils.isInlineGroovyScript(resourceToUse) && (matcher = ScriptingUtils.getMatcherForInlineGroovyScript(resourceToUse)).find()) {
                        resourceToUse = matcher.group(1);
                    }
                    script = new GroovyShellScript(resourceToUse);
                }
                LOGGER.trace("Groovy script [{}] for key [{}] is not cached", (Object)scriptResource, (Object)cacheKey);
                this.put(cacheKey, script);
                LOGGER.trace("Cached groovy script [{}] for key [{}]", (Object)script, (Object)cacheKey);
            }
            catch (Exception e) {
                LoggingUtils.error(LOGGER, e);
            }
        }
        return script;
    }

    @Override
    public Set<String> getKeys() {
        return this.cache.asMap().keySet();
    }
}

