/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.aopalliance.intercept.MethodInvocation
 *  org.apache.commons.codec.digest.DigestUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.commons.lang3.Validate
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.springframework.util.CollectionUtils
 *  org.springmodules.cache.key.CacheKeyGenerator
 *  org.springmodules.cache.key.HashCodeCacheKey
 *  org.springmodules.cache.key.HashCodeCalculator
 */
package org.apereo.services.persondir.support;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.springframework.util.CollectionUtils;
import org.springmodules.cache.key.CacheKeyGenerator;
import org.springmodules.cache.key.HashCodeCacheKey;
import org.springmodules.cache.key.HashCodeCalculator;

public class AttributeBasedCacheKeyGenerator
implements CacheKeyGenerator {
    private static final Map<String, Object> POSSIBLE_USER_ATTRIBUTE_NAMES_SEED_MAP = Collections.singletonMap("getPossibleUserAttributeNames_seedMap", new Serializable(){
        private static final long serialVersionUID = 1L;
    });
    private static final Map<String, Object> AVAILABLE_QUERY_ATTRIBUTES_SEED_MAP = Collections.singletonMap("getAvailableQueryAttributes_seedMap", new Serializable(){
        private static final long serialVersionUID = 1L;
    });
    protected final Log logger = LogFactory.getLog(this.getClass());
    private Set<String> cacheKeyAttributes = null;
    private String defaultAttributeName = "username";
    private Set<String> defaultAttributeNameSet = Collections.singleton(this.defaultAttributeName);
    private boolean useAllAttributes = false;
    private boolean ignoreEmptyAttributes = false;

    public Set<String> getCacheKeyAttributes() {
        return this.cacheKeyAttributes;
    }

    public void setCacheKeyAttributes(Set<String> cacheKeyAttributes) {
        this.cacheKeyAttributes = cacheKeyAttributes;
    }

    public String getDefaultAttributeName() {
        return this.defaultAttributeName;
    }

    public void setDefaultAttributeName(String defaultAttributeName) {
        Validate.notNull((Object)defaultAttributeName);
        this.defaultAttributeName = defaultAttributeName;
        this.defaultAttributeNameSet = Collections.singleton(this.defaultAttributeName);
    }

    public boolean isUseAllAttributes() {
        return this.useAllAttributes;
    }

    public void setUseAllAttributes(boolean useAllAttributes) {
        this.useAllAttributes = useAllAttributes;
    }

    public boolean isIgnoreEmptyAttributes() {
        return this.ignoreEmptyAttributes;
    }

    public void setIgnoreEmptyAttributes(boolean ignoreEmptyAttributes) {
        this.ignoreEmptyAttributes = ignoreEmptyAttributes;
    }

    public Serializable generateKey(MethodInvocation methodInvocation) {
        CachableMethod cachableMethod = this.resolveCacheableMethod(methodInvocation);
        Object[] methodArguments = methodInvocation.getArguments();
        Map<String, Object> seed = this.getSeed(methodArguments, cachableMethod);
        Integer keyHashCode = this.getKeyHash(seed);
        if (keyHashCode == null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug((Object)("No cache key generated for MethodInvocation='" + methodInvocation + "'"));
            }
            return null;
        }
        HashCodeCalculator hashCodeCalculator = new HashCodeCalculator();
        hashCodeCalculator.append(keyHashCode.intValue());
        long checkSum = hashCodeCalculator.getCheckSum();
        int hashCode = hashCodeCalculator.getHashCode();
        HashCodeCacheKey hashCodeCacheKey = new HashCodeCacheKey(checkSum, hashCode);
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Generated cache key '" + hashCodeCacheKey + "' for MethodInvocation='" + methodInvocation + "'"));
        }
        return cachableMethod.getName() + "|" + hashCodeCacheKey;
    }

    protected Map<String, Object> getSeed(Object[] methodArguments, CachableMethod cachableMethod) {
        Map<String, String> seed;
        switch (cachableMethod) {
            case PEOPLE_MAP: 
            case PEOPLE_MULTIVALUED_MAP: {
                seed = (Map<String, String>)methodArguments[0];
                break;
            }
            case PERSON_STR: {
                String uid = (String)methodArguments[0];
                seed = Collections.singletonMap(this.defaultAttributeName, uid);
                break;
            }
            case POSSIBLE_USER_ATTRIBUTE_NAMES: {
                seed = POSSIBLE_USER_ATTRIBUTE_NAMES_SEED_MAP;
                break;
            }
            case AVAILABLE_QUERY_ATTRIBUTES: {
                seed = AVAILABLE_QUERY_ATTRIBUTES_SEED_MAP;
                break;
            }
            default: {
                throw new IllegalArgumentException("Unsupported CachableMethod resolved: '" + cachableMethod + "'");
            }
        }
        return seed;
    }

    protected Integer getKeyHash(Map<String, Object> seed) {
        Set<String> cacheAttributes = this.useAllAttributes ? seed.keySet() : (this.cacheKeyAttributes != null ? this.cacheKeyAttributes : this.defaultAttributeNameSet);
        HashMap<String, Object> cacheKey = new HashMap<String, Object>(cacheAttributes.size());
        for (String attr : cacheAttributes) {
            if (!seed.containsKey(attr)) continue;
            Object value = seed.get(attr);
            if (!this.ignoreEmptyAttributes) {
                this.putAttributeInCache(cacheKey, attr, value);
                continue;
            }
            if (value instanceof Collection) {
                if (CollectionUtils.isEmpty((Collection)((Collection)value))) continue;
                this.putAttributeInCache(cacheKey, attr, value);
                continue;
            }
            if (value instanceof String) {
                if (!StringUtils.isNotEmpty((CharSequence)((String)value))) continue;
                this.putAttributeInCache(cacheKey, attr, value);
                continue;
            }
            if (value == null) continue;
            this.putAttributeInCache(cacheKey, attr, value);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((Object)("Generated cache Map " + cacheKey + " from seed Map " + seed));
        }
        if (cacheKey.isEmpty()) {
            return null;
        }
        return cacheKey.hashCode();
    }

    private void putAttributeInCache(Map<String, Object> cacheKey, String attr, Object value) {
        String hexed = new DigestUtils("SHA-512").digestAsHex(value.toString());
        cacheKey.put(hexed, value);
    }

    protected CachableMethod resolveCacheableMethod(MethodInvocation methodInvocation) {
        Method targetMethod = methodInvocation.getMethod();
        Class<?> targetClass = targetMethod.getDeclaringClass();
        for (CachableMethod cachableMethod : CachableMethod.values()) {
            Method cacheableMethod = null;
            try {
                cacheableMethod = targetClass.getMethod(cachableMethod.getName(), cachableMethod.getArgs());
            }
            catch (SecurityException e) {
                this.logger.warn((Object)("Security exception while attempting to if the target class '" + targetClass + "' implements the cachable method '" + cachableMethod + "'"), (Throwable)e);
            }
            catch (NoSuchMethodException e) {
                String message = "Taret class '" + targetClass + "' does not implement possible cachable method '" + cachableMethod + "'. Is the advice applied to the correct bean and methods?";
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug((Object)message, (Throwable)e);
                }
                this.logger.warn((Object)message);
            }
            if (!targetMethod.equals(cacheableMethod)) continue;
            return cachableMethod;
        }
        throw new IllegalArgumentException("Do not know how to generate a cache for for '" + targetMethod + "' on class '" + targetClass + "'. Is the advice applied to the correct bean and methods?");
    }

    public static enum CachableMethod {
        PERSON_STR("getPerson", String.class, IPersonAttributeDaoFilter.class),
        PEOPLE_MAP("getPeople", Map.class, IPersonAttributeDaoFilter.class),
        PEOPLE_MULTIVALUED_MAP("getPeopleWithMultivaluedAttributes", Map.class, IPersonAttributeDaoFilter.class),
        POSSIBLE_USER_ATTRIBUTE_NAMES("getPossibleUserAttributeNames", IPersonAttributeDaoFilter.class),
        AVAILABLE_QUERY_ATTRIBUTES("getAvailableQueryAttributes", IPersonAttributeDaoFilter.class);

        private final String name;
        private final Class<?>[] args;

        private CachableMethod(String name, Class<?> ... args) {
            this.name = name;
            this.args = args;
        }

        public String getName() {
            return this.name;
        }

        public Class<?>[] getArgs() {
            return this.args;
        }

        public String toString() {
            return this.name + "(" + Arrays.asList(this.args) + ")";
        }
    }
}

