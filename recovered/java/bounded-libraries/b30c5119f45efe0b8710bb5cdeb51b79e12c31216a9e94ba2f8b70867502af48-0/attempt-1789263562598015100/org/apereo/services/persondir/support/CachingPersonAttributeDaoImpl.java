/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.aopalliance.intercept.MethodInvocation
 *  org.apache.commons.lang3.Validate
 *  org.apache.commons.logging.Log
 *  org.apache.commons.logging.LogFactory
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.beans.factory.BeanNameAware
 *  org.springframework.beans.factory.InitializingBean
 *  org.springmodules.cache.key.CacheKeyGenerator
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.AttributeBasedCacheKeyGenerator;
import org.apereo.services.persondir.support.BasePersonImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springmodules.cache.key.CacheKeyGenerator;

public class CachingPersonAttributeDaoImpl
extends AbstractDefaultAttributePersonAttributeDao
implements InitializingBean,
BeanNameAware {
    private final Object objectMonitor = new Object();
    protected static final Set<IPersonAttributes> NULL_RESULTS_OBJECT = new HashSet<IPersonAttributes>();
    protected Log statsLogger = LogFactory.getLog((String)(this.getClass().getName() + ".statistics"));
    private long queries = 0L;
    private long misses = 0L;
    private IPersonAttributeDao cachedPersonAttributesDao = null;
    private CacheKeyGenerator cacheKeyGenerator = null;
    private Map<Serializable, Set<IPersonAttributes>> userInfoCache = null;
    private boolean cacheNullResults = false;
    private Set<IPersonAttributes> nullResultsObject = NULL_RESULTS_OBJECT;
    private String beanName;

    public IPersonAttributeDao getCachedPersonAttributesDao() {
        return this.cachedPersonAttributesDao;
    }

    public void setCachedPersonAttributesDao(IPersonAttributeDao cachedPersonAttributesDao) {
        if (cachedPersonAttributesDao == null) {
            throw new IllegalArgumentException("cachedPersonAttributesDao may not be null");
        }
        this.cachedPersonAttributesDao = cachedPersonAttributesDao;
    }

    @JsonIgnore
    public Map<Serializable, Set<IPersonAttributes>> getUserInfoCache() {
        return this.userInfoCache;
    }

    @JsonIgnore
    public void setUserInfoCache(Map<Serializable, Set<IPersonAttributes>> userInfoCache) {
        if (userInfoCache == null) {
            throw new IllegalArgumentException("userInfoCache may not be null");
        }
        this.userInfoCache = userInfoCache;
    }

    public boolean isCacheNullResults() {
        return this.cacheNullResults;
    }

    public void setCacheNullResults(boolean cacheNullResults) {
        this.cacheNullResults = cacheNullResults;
    }

    public Set<IPersonAttributes> getNullResultsObject() {
        return this.nullResultsObject;
    }

    @JsonIgnore
    public void setNullResultsObject(Set<IPersonAttributes> nullResultsObject) {
        if (nullResultsObject == null) {
            throw new IllegalArgumentException("nullResultsObject may not be null");
        }
        this.nullResultsObject = nullResultsObject;
    }

    public CacheKeyGenerator getCacheKeyGenerator() {
        return this.cacheKeyGenerator;
    }

    public void setCacheKeyGenerator(CacheKeyGenerator cacheKeyGenerator) {
        this.cacheKeyGenerator = cacheKeyGenerator;
    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void afterPropertiesSet() {
        if (this.cacheKeyGenerator == null) {
            AttributeBasedCacheKeyGenerator cacheKeyGenerator = new AttributeBasedCacheKeyGenerator();
            IUsernameAttributeProvider usernameAttributeProvider = this.getUsernameAttributeProvider();
            String usernameAttribute = usernameAttributeProvider.getUsernameAttribute();
            cacheKeyGenerator.setDefaultAttributeName(usernameAttribute);
            this.cacheKeyGenerator = cacheKeyGenerator;
        }
    }

    public long getMisses() {
        return this.misses;
    }

    public long getQueries() {
        return this.queries;
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> seed, IPersonAttributeDaoFilter filter) {
        Set<IPersonAttributes> cacheResults;
        if (seed == null) {
            throw new IllegalArgumentException("The query seed Map cannot be null.");
        }
        if (this.cachedPersonAttributesDao == null) {
            throw new IllegalStateException("No 'cachedPersonAttributesDao' has been specified.");
        }
        if (this.userInfoCache == null) {
            throw new IllegalStateException("No 'userInfoCache' has been specified.");
        }
        PersonAttributeDaoMethodInvocation methodInvocation = new PersonAttributeDaoMethodInvocation(seed);
        Serializable cacheKey = this.cacheKeyGenerator.generateKey((MethodInvocation)methodInvocation);
        if (cacheKey != null && (cacheResults = this.userInfoCache.get(cacheKey)) != null) {
            if (this.nullResultsObject.equals(cacheResults)) {
                cacheResults = null;
            }
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Retrieved query from cache for " + this.beanName + ". key='" + cacheKey + "', results='" + cacheResults + "'");
            }
            ++this.queries;
            if (this.statsLogger.isDebugEnabled()) {
                this.statsLogger.debug((Object)("Cache Stats " + this.beanName + ": queries=" + this.queries + ", hits=" + (this.queries - this.misses) + ", misses=" + this.misses));
            }
            return cacheResults;
        }
        Set queryResults = this.cachedPersonAttributesDao.getPeopleWithMultivaluedAttributes(seed, filter);
        if (cacheKey != null) {
            if (queryResults != null) {
                this.userInfoCache.put(cacheKey, queryResults);
            } else if (this.cacheNullResults) {
                this.userInfoCache.put(cacheKey, this.nullResultsObject);
            }
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Retrieved query from wrapped IPersonAttributeDao and stored in cache for " + this.beanName + ". key='" + cacheKey + "', results='" + queryResults + "'");
            }
            ++this.queries;
            ++this.misses;
            if (this.statsLogger.isDebugEnabled()) {
                this.statsLogger.debug((Object)("Cache Stats " + this.beanName + ": queries=" + this.queries + ", hits=" + (this.queries - this.misses) + ", misses=" + this.misses));
            }
        }
        return queryResults;
    }

    public void removeUserAttributes(String uid) {
        Validate.notNull((Object)uid, (String)"uid may not be null.", (Object[])new Object[0]);
        Map<String, List<Object>> seed = this.toSeedMap(uid);
        this.removeUserAttributesMultivaluedSeed(seed);
    }

    public void removeUserAttributes(Map<String, Object> seed) {
        Map<String, List<Object>> multiSeed = MultivaluedPersonAttributeUtils.toMultivaluedMap(seed);
        this.removeUserAttributesMultivaluedSeed(multiSeed);
    }

    public void removeUserAttributesMultivaluedSeed(Map<String, List<Object>> seed) {
        PersonAttributeDaoMethodInvocation methodInvocation = new PersonAttributeDaoMethodInvocation(seed);
        Serializable cacheKey = this.cacheKeyGenerator.generateKey((MethodInvocation)methodInvocation);
        this.userInfoCache.remove(cacheKey);
    }

    @Override
    public String[] getId() {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(super.getClass().getSimpleName());
        ids.addAll(Arrays.asList(this.cachedPersonAttributesDao.getId()));
        return ids.toArray(new String[0]);
    }

    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return this.cachedPersonAttributesDao.getPossibleUserAttributeNames(filter);
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return this.cachedPersonAttributesDao.getAvailableQueryAttributes(filter);
    }

    static {
        NULL_RESULTS_OBJECT.add(new SingletonPersonImpl());
    }

    private static final class SingletonPersonImpl
    extends BasePersonImpl
    implements Serializable {
        private static final long serialVersionUID = 1L;

        public SingletonPersonImpl() {
            super(new HashMap<String, List<Object>>());
        }

        public String getName() {
            return CachingPersonAttributeDaoImpl.class.getName() + "UNIQUE_NULL_RESULTS";
        }
    }

    private static class PersonAttributeDaoMethodInvocation
    implements MethodInvocation {
        private static final Method getPeopleWithMultivaluedAttributesMethod;
        private final Object[] args;

        public PersonAttributeDaoMethodInvocation(Object ... args) {
            this.args = args;
        }

        public Method getMethod() {
            return getPeopleWithMultivaluedAttributesMethod;
        }

        public Object[] getArguments() {
            return this.args;
        }

        public AccessibleObject getStaticPart() {
            throw new UnsupportedOperationException("This is a fake MethodInvocation, getStaticPart() is not supported.");
        }

        public Object getThis() {
            throw new UnsupportedOperationException("This is a fake MethodInvocation, getThis() is not supported.");
        }

        public Object proceed() {
            throw new UnsupportedOperationException("This is a fake MethodInvocation, proceed() is not supported.");
        }

        static {
            try {
                getPeopleWithMultivaluedAttributesMethod = IPersonAttributeDao.class.getMethod("getPeopleWithMultivaluedAttributes", Map.class, IPersonAttributeDaoFilter.class);
            }
            catch (SecurityException e) {
                NoSuchMethodError nsme = new NoSuchMethodError("The 'getPeopleWithMultivaluedAttributes(" + Map.class + ")' method on the '" + IPersonAttributeDao.class + "' is not accessible due to a security policy.");
                nsme.initCause(e);
                throw nsme;
            }
            catch (NoSuchMethodException e) {
                NoSuchMethodError nsme = new NoSuchMethodError("The 'getPeopleWithMultivaluedAttributes(" + Map.class + ")' method on the '" + IPersonAttributeDao.class + "' does not exist.");
                nsme.initCause(e);
                throw nsme;
            }
        }
    }
}

