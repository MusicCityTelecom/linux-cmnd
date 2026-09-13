/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  lombok.Generated
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.cas.authentication.principal.Principal
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.cas.authentication.attribute;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.Generated;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apereo.cas.authentication.principal.Principal;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrincipalAttributeRepositoryFetcher {
    @Generated
    private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalAttributeRepositoryFetcher.class);
    private final IPersonAttributeDao attributeRepository;
    private final String principalId;
    private final Set<String> activeAttributeRepositoryIdentifiers;
    private final Map<String, List<Object>> queryAttributes;
    private final Principal currentPrincipal;

    public Map<String, List<Object>> retrieve() {
        IPersonAttributeDaoFilter filter = IPersonAttributeDaoFilter.alwaysChoose();
        if (!this.activeAttributeRepositoryIdentifiers.isEmpty()) {
            String[] repoIdsArray = this.activeAttributeRepositoryIdentifiers.toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            LOGGER.trace("Active attribute repository identifiers [{}]", this.activeAttributeRepositoryIdentifiers);
            filter = dao -> Arrays.stream(dao.getId()).anyMatch(daoId -> daoId.equalsIgnoreCase("*") || StringUtils.equalsAnyIgnoreCase((CharSequence)daoId, (CharSequence[])repoIdsArray) || StringUtils.equalsAnyIgnoreCase((CharSequence)"*", (CharSequence[])repoIdsArray));
        }
        LinkedHashMap<String, Object> query = new LinkedHashMap<String, Object>();
        if (this.currentPrincipal != null) {
            query.put("principal", this.currentPrincipal.getId());
            query.putAll(this.currentPrincipal.getAttributes());
        }
        query.putAll(this.queryAttributes);
        query.put("username", this.principalId.trim());
        LOGGER.debug("Fetching person attributes for query [{}]", query);
        Set people = this.attributeRepository.getPeople(query, filter);
        if (people == null || people.isEmpty()) {
            LOGGER.warn("No person records were fetched from attribute repositories for [{}]", query);
            return new HashMap<String, List<Object>>(0);
        }
        if (people.size() > 1) {
            LOGGER.warn("Multiple records were found for [{}] from attribute repositories for query [{}]. The records are [{}], and CAS will only pick the first person record from the results.", new Object[]{this.principalId, query, people});
        }
        IPersonAttributes person = (IPersonAttributes)people.iterator().next();
        LOGGER.debug("Retrieved person [{}] from attribute repositories for query [{}]", (Object)person, query);
        return person.getAttributes();
    }

    @Generated
    private static Set<String> $default$activeAttributeRepositoryIdentifiers() {
        return new HashSet<String>();
    }

    @Generated
    private static Map<String, List<Object>> $default$queryAttributes() {
        return new HashMap<String, List<Object>>();
    }

    @Generated
    protected PrincipalAttributeRepositoryFetcher(PrincipalAttributeRepositoryFetcherBuilder<?, ?> b) {
        this.attributeRepository = b.attributeRepository;
        this.principalId = b.principalId;
        this.activeAttributeRepositoryIdentifiers = b.activeAttributeRepositoryIdentifiers$set ? b.activeAttributeRepositoryIdentifiers$value : PrincipalAttributeRepositoryFetcher.$default$activeAttributeRepositoryIdentifiers();
        this.queryAttributes = b.queryAttributes$set ? b.queryAttributes$value : PrincipalAttributeRepositoryFetcher.$default$queryAttributes();
        this.currentPrincipal = b.currentPrincipal;
    }

    @Generated
    public static PrincipalAttributeRepositoryFetcherBuilder<?, ?> builder() {
        return new PrincipalAttributeRepositoryFetcherBuilderImpl();
    }

    @Generated
    public IPersonAttributeDao getAttributeRepository() {
        return this.attributeRepository;
    }

    @Generated
    public String getPrincipalId() {
        return this.principalId;
    }

    @Generated
    public Set<String> getActiveAttributeRepositoryIdentifiers() {
        return this.activeAttributeRepositoryIdentifiers;
    }

    @Generated
    public Map<String, List<Object>> getQueryAttributes() {
        return this.queryAttributes;
    }

    @Generated
    public Principal getCurrentPrincipal() {
        return this.currentPrincipal;
    }

    @Generated
    private static final class PrincipalAttributeRepositoryFetcherBuilderImpl
    extends PrincipalAttributeRepositoryFetcherBuilder<PrincipalAttributeRepositoryFetcher, PrincipalAttributeRepositoryFetcherBuilderImpl> {
        @Generated
        private PrincipalAttributeRepositoryFetcherBuilderImpl() {
        }

        @Override
        @Generated
        protected PrincipalAttributeRepositoryFetcherBuilderImpl self() {
            return this;
        }

        @Override
        @Generated
        public PrincipalAttributeRepositoryFetcher build() {
            return new PrincipalAttributeRepositoryFetcher(this);
        }
    }

    @Generated
    public static abstract class PrincipalAttributeRepositoryFetcherBuilder<C extends PrincipalAttributeRepositoryFetcher, B extends PrincipalAttributeRepositoryFetcherBuilder<C, B>> {
        @Generated
        private IPersonAttributeDao attributeRepository;
        @Generated
        private String principalId;
        @Generated
        private boolean activeAttributeRepositoryIdentifiers$set;
        @Generated
        private Set<String> activeAttributeRepositoryIdentifiers$value;
        @Generated
        private boolean queryAttributes$set;
        @Generated
        private Map<String, List<Object>> queryAttributes$value;
        @Generated
        private Principal currentPrincipal;

        @Generated
        protected abstract B self();

        @Generated
        public abstract C build();

        @Generated
        public B attributeRepository(IPersonAttributeDao attributeRepository) {
            this.attributeRepository = attributeRepository;
            return this.self();
        }

        @Generated
        public B principalId(String principalId) {
            this.principalId = principalId;
            return this.self();
        }

        @Generated
        public B activeAttributeRepositoryIdentifiers(Set<String> activeAttributeRepositoryIdentifiers) {
            this.activeAttributeRepositoryIdentifiers$value = activeAttributeRepositoryIdentifiers;
            this.activeAttributeRepositoryIdentifiers$set = true;
            return this.self();
        }

        @Generated
        public B queryAttributes(Map<String, List<Object>> queryAttributes) {
            this.queryAttributes$value = queryAttributes;
            this.queryAttributes$set = true;
            return this.self();
        }

        @Generated
        public B currentPrincipal(Principal currentPrincipal) {
            this.currentPrincipal = currentPrincipal;
            return this.self();
        }

        @Generated
        public String toString() {
            return "PrincipalAttributeRepositoryFetcher.PrincipalAttributeRepositoryFetcherBuilder(attributeRepository=" + this.attributeRepository + ", principalId=" + this.principalId + ", activeAttributeRepositoryIdentifiers$value=" + this.activeAttributeRepositoryIdentifiers$value + ", queryAttributes$value=" + this.queryAttributes$value + ", currentPrincipal=" + this.currentPrincipal + ")";
        }
    }
}

