/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.beans.factory.BeanCreationException
 *  org.springframework.beans.factory.InitializingBean
 *  org.springframework.ldap.core.AttributesMapper
 *  org.springframework.ldap.core.ContextSource
 *  org.springframework.ldap.core.LdapTemplate
 *  org.springframework.ldap.filter.EqualsFilter
 *  org.springframework.ldap.filter.Filter
 *  org.springframework.ldap.filter.LikeFilter
 *  org.springframework.util.Assert
 */
package org.apereo.services.persondir.support.ldap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.directory.SearchControls;
import org.apache.commons.lang3.StringUtils;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractQueryPersonAttributeDao;
import org.apereo.services.persondir.support.BasePersonImpl;
import org.apereo.services.persondir.support.CaseInsensitiveAttributeNamedPersonImpl;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.apereo.services.persondir.support.QueryType;
import org.apereo.services.persondir.support.ldap.AttributeMapAttributesMapper;
import org.apereo.services.persondir.support.ldap.LogicalFilterWrapper;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.util.Assert;

@Deprecated
public class LdapPersonAttributeDao
extends AbstractQueryPersonAttributeDao<LogicalFilterWrapper>
implements InitializingBean {
    private static final Pattern QUERY_PLACEHOLDER = Pattern.compile("\\{0\\}");
    private static final AttributesMapper MAPPER = new AttributeMapAttributesMapper();
    private LdapTemplate ldapTemplate = null;
    private String baseDN = "";
    private String queryTemplate = null;
    private ContextSource contextSource = null;
    private SearchControls searchControls = new SearchControls();
    private final boolean setReturningAttributes = true;
    private QueryType queryType = QueryType.AND;

    public LdapPersonAttributeDao() {
        this.searchControls.setSearchScope(2);
        this.searchControls.setReturningObjFlag(false);
    }

    public void afterPropertiesSet() {
        Map<String, Set<String>> resultAttributeMapping = this.getResultAttributeMapping();
        Objects.requireNonNull(this);
        if (resultAttributeMapping != null) {
            this.searchControls.setReturningAttributes(resultAttributeMapping.keySet().toArray(new String[resultAttributeMapping.size()]));
        }
        if (this.contextSource == null) {
            throw new BeanCreationException("contextSource must be set");
        }
    }

    @Override
    protected LogicalFilterWrapper appendAttributeToQuery(LogicalFilterWrapper queryBuilder, String dataAttribute, List<Object> queryValues) {
        if (queryBuilder == null) {
            queryBuilder = new LogicalFilterWrapper(this.queryType);
        }
        for (Object queryValue : queryValues) {
            String queryValueString = queryValue == null ? null : queryValue.toString();
            if (!StringUtils.isNotBlank((CharSequence)queryValueString)) continue;
            Object filter = !queryValueString.contains("*") ? new EqualsFilter(dataAttribute, queryValueString) : new LikeFilter(dataAttribute, queryValueString);
            queryBuilder.append((Filter)filter);
        }
        return queryBuilder;
    }

    @Override
    protected List<IPersonAttributes> getPeopleForQuery(LogicalFilterWrapper queryBuilder, String queryUserName) {
        String ldapQuery;
        String generatedLdapQuery = queryBuilder.encode();
        if (StringUtils.isBlank((CharSequence)generatedLdapQuery)) {
            return null;
        }
        if (this.queryTemplate == null) {
            ldapQuery = generatedLdapQuery;
        } else {
            Matcher queryMatcher = QUERY_PLACEHOLDER.matcher(this.queryTemplate);
            ldapQuery = queryMatcher.replaceAll(generatedLdapQuery);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Final ldapQuery after applying queryTemplate: '" + ldapQuery + "'");
            }
        }
        List queryResults = this.ldapTemplate.search(this.baseDN, ldapQuery, this.searchControls, MAPPER);
        ArrayList<IPersonAttributes> peopleAttributes = new ArrayList<IPersonAttributes>(queryResults.size());
        for (Map queryResult : queryResults) {
            String userNameAttribute = this.getConfiguredUserNameAttribute();
            BasePersonImpl person = this.isUserNameAttributeConfigured() && queryResult.containsKey(userNameAttribute) ? new CaseInsensitiveAttributeNamedPersonImpl(userNameAttribute, queryResult) : (queryUserName != null ? new CaseInsensitiveNamedPersonImpl(queryUserName, queryResult) : new CaseInsensitiveAttributeNamedPersonImpl(userNameAttribute, queryResult));
            peopleAttributes.add(person);
        }
        return peopleAttributes;
    }

    public String getBaseDN() {
        return this.baseDN;
    }

    public void setBaseDN(String baseDN) {
        if (baseDN == null) {
            baseDN = "";
        }
        this.baseDN = baseDN;
    }

    public ContextSource getContextSource() {
        return this.contextSource;
    }

    public synchronized void setContextSource(ContextSource contextSource) {
        Assert.notNull((Object)contextSource, (String)"contextSource can not be null");
        this.contextSource = contextSource;
        this.ldapTemplate = new LdapTemplate(this.contextSource);
    }

    public synchronized void setLdapTemplate(LdapTemplate ldapTemplate) {
        Assert.notNull((Object)ldapTemplate, (String)"ldapTemplate cannot be null");
        this.ldapTemplate = ldapTemplate;
        this.contextSource = this.ldapTemplate.getContextSource();
    }

    public SearchControls getSearchControls() {
        return this.searchControls;
    }

    public void setSearchControls(SearchControls searchControls) {
        Assert.notNull((Object)searchControls, (String)"searchControls can not be null");
        this.searchControls = searchControls;
    }

    public QueryType getQueryType() {
        return this.queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public String getQueryTemplate() {
        return this.queryTemplate;
    }

    public void setQueryTemplate(String queryTemplate) {
        this.queryTemplate = queryTemplate;
    }
}

