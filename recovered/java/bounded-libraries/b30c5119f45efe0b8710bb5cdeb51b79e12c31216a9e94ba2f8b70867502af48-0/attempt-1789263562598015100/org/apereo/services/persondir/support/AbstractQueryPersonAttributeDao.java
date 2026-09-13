/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.google.common.collect.ImmutableSet
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.AttributeNamedPersonImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.util.CaseCanonicalizationMode;
import org.apereo.services.persondir.util.CollectionsUtil;

public abstract class AbstractQueryPersonAttributeDao<QB>
extends AbstractDefaultAttributePersonAttributeDao {
    public static final CaseCanonicalizationMode DEFAULT_CASE_CANONICALIZATION_MODE = CaseCanonicalizationMode.LOWER;
    public static final CaseCanonicalizationMode DEFAULT_USERNAME_CASE_CANONICALIZATION_MODE = CaseCanonicalizationMode.NONE;
    private Map<String, Set<String>> queryAttributeMapping;
    private Map<String, Set<String>> resultAttributeMapping;
    private Map<String, CaseCanonicalizationMode> caseInsensitiveResultAttributes;
    private Map<String, CaseCanonicalizationMode> caseInsensitiveQueryAttributes;
    private CaseCanonicalizationMode defaultCaseCanonicalizationMode = DEFAULT_CASE_CANONICALIZATION_MODE;
    private CaseCanonicalizationMode usernameCaseCanonicalizationMode = DEFAULT_USERNAME_CASE_CANONICALIZATION_MODE;
    private Locale caseCanonicalizationLocale = Locale.getDefault();
    private Set<String> possibleUserAttributes;
    private boolean requireAllQueryAttributes = false;
    private boolean useAllQueryAttributes = true;
    private String unmappedUsernameAttribute = null;

    public boolean isUseAllQueryAttributes() {
        return this.useAllQueryAttributes;
    }

    public void setUseAllQueryAttributes(boolean useAllQueryAttributes) {
        this.useAllQueryAttributes = useAllQueryAttributes;
    }

    public Map<String, Set<String>> getQueryAttributeMapping() {
        return this.queryAttributeMapping;
    }

    public void setQueryAttributeMapping(Map<String, ?> queryAttributeMapping) {
        Map<String, Set<String>> parsedQueryAttributeMapping = MultivaluedPersonAttributeUtils.parseAttributeToAttributeMapping(queryAttributeMapping);
        if (parsedQueryAttributeMapping.containsKey("")) {
            throw new IllegalArgumentException("The map from attribute names to attributes must not have any empty keys.");
        }
        this.queryAttributeMapping = parsedQueryAttributeMapping;
    }

    public Map<String, Set<String>> getResultAttributeMapping() {
        return this.resultAttributeMapping;
    }

    public void setResultAttributeMapping(Map<String, ?> resultAttributeMapping) {
        Map<String, Set<String>> parsedResultAttributeMapping = MultivaluedPersonAttributeUtils.parseAttributeToAttributeMapping(resultAttributeMapping);
        if (parsedResultAttributeMapping.containsKey("")) {
            throw new IllegalArgumentException("The map from attribute names to attributes must not have any empty keys.");
        }
        Collection userAttributes = MultivaluedPersonAttributeUtils.flattenCollection(parsedResultAttributeMapping.values());
        this.resultAttributeMapping = parsedResultAttributeMapping;
        this.possibleUserAttributes = new LinkedHashSet(userAttributes);
    }

    public boolean isRequireAllQueryAttributes() {
        return this.requireAllQueryAttributes;
    }

    public void setRequireAllQueryAttributes(boolean requireAllQueryAttributes) {
        this.requireAllQueryAttributes = requireAllQueryAttributes;
    }

    public String getUnmappedUsernameAttribute() {
        return this.unmappedUsernameAttribute;
    }

    public void setUnmappedUsernameAttribute(String userNameAttribute) {
        this.unmappedUsernameAttribute = userNameAttribute;
    }

    public final Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        Validate.notNull(query, (String)"query may not be null.", (Object[])new Object[0]);
        QB queryBuilder = this.generateQuery(query);
        if (queryBuilder == null && (this.queryAttributeMapping != null || this.useAllQueryAttributes)) {
            this.logger.debug("No queryBuilder was generated for query " + query + ", null will be returned");
            return null;
        }
        IUsernameAttributeProvider usernameAttributeProvider = this.getUsernameAttributeProvider();
        String username = usernameAttributeProvider.getUsernameFromQuery(query);
        List<IPersonAttributes> unmappedPeople = this.getPeopleForQuery(queryBuilder, username);
        if (unmappedPeople == null) {
            return null;
        }
        LinkedHashSet<IPersonAttributes> mappedPeople = new LinkedHashSet<IPersonAttributes>();
        for (IPersonAttributes unmappedPerson : unmappedPeople) {
            IPersonAttributes mappedPerson = this.mapPersonAttributes(unmappedPerson);
            mappedPeople.add(mappedPerson);
        }
        return CollectionsUtil.safelyWrapAsUnmodifiableSet(mappedPeople);
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        if (this.queryAttributeMapping == null) {
            return new HashSet<String>();
        }
        return new HashSet<String>(this.queryAttributeMapping.keySet());
    }

    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return this.possibleUserAttributes;
    }

    protected abstract List<IPersonAttributes> getPeopleForQuery(QB var1, String var2);

    protected QB appendCanonicalizedAttributeToQuery(QB queryBuilder, String queryAttribute, String dataAttribute, List<Object> queryValues) {
        List<Object> canonicalizedQueryValues = this.canonicalizeAttribute(queryAttribute, queryValues, this.caseInsensitiveQueryAttributes);
        if (dataAttribute == null) {
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Adding attribute '" + queryAttribute + "' with value '" + queryValues + "' to query builder '" + queryBuilder + "'");
            }
            return this.appendAttributeToQuery(queryBuilder, dataAttribute, canonicalizedQueryValues);
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Adding attribute '" + dataAttribute + "' with value '" + queryValues + "' to query builder '" + queryBuilder + "'");
        }
        return this.appendAttributeToQuery(queryBuilder, dataAttribute, canonicalizedQueryValues);
    }

    protected abstract QB appendAttributeToQuery(QB var1, String var2, List<Object> var3);

    protected final QB generateQuery(Map<String, List<Object>> query) {
        Object queryBuilder = null;
        if (this.queryAttributeMapping != null && !this.queryAttributeMapping.isEmpty()) {
            for (Map.Entry<String, Set<String>> queryAttrEntry : this.queryAttributeMapping.entrySet()) {
                String queryAttr = queryAttrEntry.getKey();
                List<Object> queryValues = query.get(queryAttr);
                if (queryValues != null) {
                    Set<String> dataAttributes = queryAttrEntry.getValue();
                    if (dataAttributes == null) {
                        queryBuilder = this.appendCanonicalizedAttributeToQuery(queryBuilder, queryAttr, null, queryValues);
                        continue;
                    }
                    for (String dataAttribute : dataAttributes) {
                        queryBuilder = this.appendCanonicalizedAttributeToQuery(queryBuilder, queryAttr, dataAttribute, queryValues);
                    }
                    continue;
                }
                if (!this.requireAllQueryAttributes) continue;
                this.logger.debug("Query " + query + " does not contain all nessesary attributes as specified by queryAttributeMapping " + this.queryAttributeMapping + ", null will be returned for the queryBuilder");
                return null;
            }
        } else if (this.useAllQueryAttributes) {
            for (Map.Entry<String, List<Object>> queryAttrEntry : query.entrySet()) {
                String queryKey = queryAttrEntry.getKey();
                List<Object> queryValues = queryAttrEntry.getValue();
                queryBuilder = this.appendCanonicalizedAttributeToQuery(queryBuilder, queryKey, queryKey, queryValues);
            }
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Generated query builder '" + queryBuilder + "' from query Map " + query + ".");
        }
        return queryBuilder;
    }

    protected final IPersonAttributes mapPersonAttributes(IPersonAttributes person) {
        NamedPersonImpl newPerson;
        LinkedHashMap<String, List<Object>> mappedAttributes;
        Map personAttributes = person.getAttributes();
        if (this.resultAttributeMapping == null) {
            if (this.caseInsensitiveResultAttributes != null && !this.caseInsensitiveResultAttributes.isEmpty()) {
                mappedAttributes = new LinkedHashMap();
                for (Map.Entry<String, Set<String>> entry : personAttributes.entrySet()) {
                    String attributeName = entry.getKey();
                    mappedAttributes.put(attributeName, this.canonicalizeAttribute(attributeName, (List)((Object)entry.getValue()), this.caseInsensitiveResultAttributes));
                }
            } else {
                mappedAttributes = personAttributes;
            }
        } else {
            mappedAttributes = new LinkedHashMap();
            for (Map.Entry<String, Set<String>> entry : this.resultAttributeMapping.entrySet()) {
                String dataKey = entry.getKey();
                ImmutableSet resultKeys = entry.getValue();
                if (resultKeys == null) {
                    resultKeys = ImmutableSet.of((Object)dataKey);
                }
                if (resultKeys.size() == 1 && resultKeys.stream().allMatch(s -> s.endsWith(";"))) {
                    List allKeys = personAttributes.keySet().stream().filter(name -> name.startsWith(dataKey + ";")).collect(Collectors.toList());
                    for (String resultKey : allKeys) {
                        List<Object> value = (List<Object>)personAttributes.get(resultKey);
                        value = this.canonicalizeAttribute(resultKey, value, this.caseInsensitiveResultAttributes);
                        mappedAttributes.put(resultKey, value);
                    }
                    continue;
                }
                if (!personAttributes.containsKey(dataKey)) continue;
                List<Object> value = (List<Object>)personAttributes.get(dataKey);
                for (String resultKey : resultKeys) {
                    value = this.canonicalizeAttribute(resultKey, value, this.caseInsensitiveResultAttributes);
                    if (resultKey == null) {
                        mappedAttributes.put(dataKey, value);
                        continue;
                    }
                    mappedAttributes.put(resultKey, value);
                }
            }
        }
        String string = person.getName();
        if (string != null) {
            newPerson = new NamedPersonImpl(this.usernameCaseCanonicalizationMode.canonicalize(string), mappedAttributes);
        } else {
            String userNameAttribute = this.getConfiguredUserNameAttribute();
            AttributeNamedPersonImpl tmpNewPerson = new AttributeNamedPersonImpl(userNameAttribute, mappedAttributes);
            newPerson = new NamedPersonImpl(this.usernameCaseCanonicalizationMode.canonicalize(tmpNewPerson.getName()), mappedAttributes);
        }
        return newPerson;
    }

    protected List<Object> canonicalizeAttribute(String key, List<Object> value, Map<String, CaseCanonicalizationMode> config) {
        if (value == null || value.isEmpty() || config == null || !config.containsKey(key)) {
            return value;
        }
        CaseCanonicalizationMode canonicalizationMode = config.get(key);
        if (canonicalizationMode == null) {
            canonicalizationMode = this.defaultCaseCanonicalizationMode;
        }
        ArrayList<Object> canonicalizedValues = new ArrayList<Object>(value.size());
        for (Object origValue : value) {
            if (origValue instanceof String) {
                canonicalizedValues.add(canonicalizationMode.canonicalize((String)origValue, this.caseCanonicalizationLocale));
                continue;
            }
            canonicalizedValues.add(origValue);
        }
        return canonicalizedValues;
    }

    @JsonIgnore
    protected String getConfiguredUserNameAttribute() {
        if (this.unmappedUsernameAttribute != null) {
            return this.unmappedUsernameAttribute;
        }
        IUsernameAttributeProvider usernameAttributeProvider = this.getUsernameAttributeProvider();
        return usernameAttributeProvider.getUsernameAttribute();
    }

    @JsonIgnore
    protected boolean isUserNameAttributeConfigured() {
        return this.unmappedUsernameAttribute != null;
    }

    public Map<String, CaseCanonicalizationMode> getCaseInsensitiveResultAttributes() {
        return this.caseInsensitiveResultAttributes;
    }

    public void setCaseInsensitiveResultAttributes(Map<String, CaseCanonicalizationMode> caseInsensitiveResultAttributes) {
        this.caseInsensitiveResultAttributes = caseInsensitiveResultAttributes;
    }

    public void setCaseInsensitiveResultAttributesAsCollection(Collection<String> caseInsensitiveResultAttributes) {
        if (caseInsensitiveResultAttributes == null || caseInsensitiveResultAttributes.isEmpty()) {
            this.setCaseInsensitiveResultAttributes(null);
        } else {
            HashMap<String, CaseCanonicalizationMode> asMap = new HashMap<String, CaseCanonicalizationMode>();
            for (String attrib : caseInsensitiveResultAttributes) {
                asMap.put(attrib, null);
            }
            this.setCaseInsensitiveResultAttributes(asMap);
        }
    }

    public void setCaseInsensitiveQueryAttributesAsCollection(Collection<String> caseInsensitiveQueryAttributes) {
        if (caseInsensitiveQueryAttributes == null || caseInsensitiveQueryAttributes.isEmpty()) {
            this.setCaseInsensitiveQueryAttributes(null);
        } else {
            HashMap<String, CaseCanonicalizationMode> asMap = new HashMap<String, CaseCanonicalizationMode>();
            for (String attrib : caseInsensitiveQueryAttributes) {
                asMap.put(attrib, null);
            }
            this.setCaseInsensitiveQueryAttributes(asMap);
        }
    }

    public void setCaseInsensitiveQueryAttributes(Map<String, CaseCanonicalizationMode> caseInsensitiveQueryAttributes) {
        this.caseInsensitiveQueryAttributes = caseInsensitiveQueryAttributes;
    }

    public Map<String, CaseCanonicalizationMode> getCaseInsensitiveQueryAttributes() {
        return this.caseInsensitiveQueryAttributes;
    }

    public void setCaseCanonicalizationLocale(Locale caseCanonicalizationLocale) {
        this.caseCanonicalizationLocale = caseCanonicalizationLocale == null ? Locale.getDefault() : caseCanonicalizationLocale;
    }

    public Locale getCaseCanonicalizationLocale() {
        return this.caseCanonicalizationLocale;
    }

    public void setDefaultCaseCanonicalizationMode(CaseCanonicalizationMode defaultCaseCanonicalizationMode) {
        this.defaultCaseCanonicalizationMode = defaultCaseCanonicalizationMode == null ? DEFAULT_CASE_CANONICALIZATION_MODE : defaultCaseCanonicalizationMode;
    }

    public CaseCanonicalizationMode getDefaultCaseCanonicalizationMode() {
        return this.defaultCaseCanonicalizationMode;
    }

    public void setUsernameCaseCanonicalizationMode(CaseCanonicalizationMode usernameCaseCanonicalizationMode) {
        this.usernameCaseCanonicalizationMode = usernameCaseCanonicalizationMode == null ? DEFAULT_USERNAME_CASE_CANONICALIZATION_MODE : usernameCaseCanonicalizationMode;
    }

    public CaseCanonicalizationMode getUsernameCaseCanonicalizationMode() {
        return this.usernameCaseCanonicalizationMode;
    }
}

