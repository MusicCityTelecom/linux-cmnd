/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.ldaptive.ConnectionFactory
 *  org.ldaptive.FilterTemplate
 *  org.ldaptive.LdapAttribute
 *  org.ldaptive.LdapEntry
 *  org.ldaptive.LdapException
 *  org.ldaptive.ReturnAttributes
 *  org.ldaptive.SearchOperation
 *  org.ldaptive.SearchRequest
 *  org.ldaptive.SearchResponse
 *  org.ldaptive.SearchScope
 *  org.ldaptive.handler.LdapEntryHandler
 *  org.ldaptive.handler.SearchResultHandler
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package org.apereo.services.persondir.support.ldap;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.naming.directory.SearchControls;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractQueryPersonAttributeDao;
import org.apereo.services.persondir.support.BasePersonImpl;
import org.apereo.services.persondir.support.CaseInsensitiveAttributeNamedPersonImpl;
import org.apereo.services.persondir.support.CaseInsensitiveNamedPersonImpl;
import org.ldaptive.ConnectionFactory;
import org.ldaptive.FilterTemplate;
import org.ldaptive.LdapAttribute;
import org.ldaptive.LdapEntry;
import org.ldaptive.LdapException;
import org.ldaptive.ReturnAttributes;
import org.ldaptive.SearchOperation;
import org.ldaptive.SearchRequest;
import org.ldaptive.SearchResponse;
import org.ldaptive.SearchScope;
import org.ldaptive.handler.LdapEntryHandler;
import org.ldaptive.handler.SearchResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LdaptivePersonAttributeDao
extends AbstractQueryPersonAttributeDao<FilterTemplate>
implements AutoCloseable {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String baseDN;
    private SearchControls searchControls;
    private ConnectionFactory connectionFactory;
    private String searchFilter;
    private String[] binaryAttributes;
    private LdapEntryHandler[] entryHandlers;
    private SearchResultHandler[] searchResultHandlers;

    public void setBaseDN(String dn) {
        this.baseDN = dn;
    }

    public void setSearchFilter(String filter) {
        this.searchFilter = filter;
    }

    public void setSearchControls(SearchControls searchControls) {
        this.searchControls = searchControls;
    }

    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void setBinaryAttributes(String[] binaryAttributes) {
        this.binaryAttributes = binaryAttributes;
    }

    public void setEntryHandlers(LdapEntryHandler[] handlers) {
        this.entryHandlers = handlers;
    }

    public void setSearchResultHandlers(SearchResultHandler[] handlers) {
        this.searchResultHandlers = handlers;
    }

    @Override
    protected List<IPersonAttributes> getPeopleForQuery(FilterTemplate filter, String userName) {
        SearchResponse response;
        try {
            SearchOperation search = new SearchOperation(this.connectionFactory);
            search.setEntryHandlers(this.entryHandlers);
            search.setSearchResultHandlers(this.searchResultHandlers);
            response = search.execute(this.createRequest(filter));
        }
        catch (LdapException e) {
            throw new RuntimeException("Failed executing LDAP query " + filter, e);
        }
        ArrayList<IPersonAttributes> peopleAttributes = new ArrayList<IPersonAttributes>(response.entrySize());
        for (LdapEntry entry : response.getEntries()) {
            ArrayList<String> values;
            String userNameAttribute = this.getConfiguredUserNameAttribute();
            Map<String, List<Object>> attributes = this.convertLdapEntryToMap(entry);
            if (response.getDiagnosticMessage() != null && !response.getDiagnosticMessage().isEmpty()) {
                values = new ArrayList<String>();
                values.add(response.getDiagnosticMessage());
                attributes.put("diagnosticMessage", values);
            }
            if (response.getMatchedDN() != null && !response.getMatchedDN().isEmpty()) {
                values = new ArrayList();
                values.add(response.getMatchedDN());
                attributes.put("matchedDN", values);
            }
            BasePersonImpl person = attributes.containsKey(userNameAttribute) ? new CaseInsensitiveAttributeNamedPersonImpl(userNameAttribute, attributes) : new CaseInsensitiveNamedPersonImpl(userName, attributes);
            peopleAttributes.add(person);
        }
        return peopleAttributes;
    }

    @Override
    protected FilterTemplate appendAttributeToQuery(FilterTemplate filter, String attribute, List<Object> values) {
        FilterTemplate query = filter == null ? new FilterTemplate(this.searchFilter) : filter;
        if (this.isUseAllQueryAttributes() && values.size() > 1 && (this.searchFilter.contains("{0}") || this.searchFilter.contains("{user}"))) {
            this.logger.warn("Query value will be indeterminate due to multiple attributes and no username indicator. Use attribute [{}] in query instead of {0} or {user}", (Object)attribute);
        }
        if (values.size() > 0) {
            if (this.searchFilter.contains("{0}")) {
                query.setParameter(0, (Object)values.get(0).toString());
            } else if (this.searchFilter.contains("{user}")) {
                query.setParameter("user", (Object)values.get(0).toString());
            } else if (this.searchFilter.contains("{" + attribute + "}")) {
                query.setParameter(attribute, (Object)values.get(0).toString());
            }
            this.logger.debug("Constructed LDAP search query [{}]", (Object)query.format());
        }
        return query;
    }

    protected SearchRequest createRequest(FilterTemplate filter) {
        SearchRequest request = new SearchRequest();
        request.setBaseDn(this.baseDN);
        request.setFilter(filter);
        request.setBinaryAttributes(this.binaryAttributes);
        if (this.getResultAttributeMapping() != null && !this.getResultAttributeMapping().isEmpty()) {
            String[] attributes = this.getResultAttributeMapping().keySet().toArray(new String[this.getResultAttributeMapping().size()]);
            request.setReturnAttributes(attributes);
        } else if (this.searchControls.getReturningAttributes() != null && this.searchControls.getReturningAttributes().length > 0) {
            request.setReturnAttributes(this.searchControls.getReturningAttributes());
        } else {
            request.setReturnAttributes(ReturnAttributes.ALL_USER.value());
        }
        SearchScope searchScope = SearchScope.SUBTREE;
        for (SearchScope scope : SearchScope.values()) {
            if (scope.ordinal() != this.searchControls.getSearchScope()) continue;
            searchScope = scope;
        }
        request.setSearchScope(searchScope);
        request.setSizeLimit(Long.valueOf(this.searchControls.getCountLimit()).intValue());
        request.setTimeLimit(Duration.ofSeconds(this.searchControls.getTimeLimit()));
        return request;
    }

    protected Map<String, List<Object>> convertLdapEntryToMap(LdapEntry entry) {
        if (entry.getAttribute("userAccountControl") != null) {
            int n = Integer.parseInt(entry.getAttribute("userAccountControl").getStringValue());
        }
        LinkedHashMap<String, List<Object>> attributeMap = new LinkedHashMap<String, List<Object>>(entry.size());
        for (LdapAttribute attr : entry.getAttributes()) {
            attributeMap.put(attr.getName(), new ArrayList(attr.getStringValues()));
        }
        this.logger.debug("Converted ldap DN entry [{}] to attribute map {}", (Object)entry.getDn(), attributeMap);
        return attributeMap;
    }

    @Override
    public void close() {
        if (this.connectionFactory != null) {
            this.connectionFactory.close();
        }
    }
}

