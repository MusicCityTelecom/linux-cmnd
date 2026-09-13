/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractQueryPersonAttributeDao;
import org.apereo.services.persondir.support.AttributeNamedPersonImpl;
import org.apereo.services.persondir.support.BasePersonImpl;
import org.apereo.services.persondir.support.IUsernameAttributeProvider;
import org.apereo.services.persondir.support.NamedPersonImpl;
import org.apereo.services.persondir.util.CollectionsUtil;
import org.apereo.services.persondir.util.PatternHelper;

public class ComplexStubPersonAttributeDao
extends AbstractQueryPersonAttributeDao<String> {
    private Map<String, Map<String, List<Object>>> backingMap = Collections.emptyMap();
    private Set<String> possibleUserAttributeNames = Collections.emptySet();
    private String queryAttributeName = null;

    public ComplexStubPersonAttributeDao() {
    }

    public ComplexStubPersonAttributeDao(Map<String, Map<String, List<Object>>> backingMap) {
        this.setBackingMap(backingMap);
    }

    public ComplexStubPersonAttributeDao(String queryAttributeName, Map<String, Map<String, List<Object>>> backingMap) {
        this.setQueryAttributeName(queryAttributeName);
        this.setBackingMap(backingMap);
    }

    public String getQueryAttributeName() {
        return this.queryAttributeName;
    }

    public void setQueryAttributeName(String queryAttributeName) {
        this.queryAttributeName = queryAttributeName;
    }

    public Map<String, Map<String, List<Object>>> getBackingMap() {
        return this.backingMap;
    }

    public void setBackingMap(Map<String, Map<String, List<Object>>> backingMap) {
        if (backingMap == null) {
            this.backingMap = new HashMap<String, Map<String, List<Object>>>();
            this.possibleUserAttributeNames = new HashSet<String>();
        } else {
            this.backingMap = new LinkedHashMap<String, Map<String, List<Object>>>(backingMap);
            this.initializePossibleAttributeNames();
        }
    }

    @Override
    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return this.possibleUserAttributeNames;
    }

    @Override
    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        IUsernameAttributeProvider usernameAttributeProvider = this.getUsernameAttributeProvider();
        String usernameAttribute = usernameAttributeProvider.getUsernameAttribute();
        HashSet<String> list = new HashSet<String>();
        list.add(usernameAttribute);
        return list;
    }

    @Override
    protected String appendAttributeToQuery(String queryBuilder, String dataAttribute, List<Object> queryValues) {
        String keyAttributeName;
        if (queryBuilder != null) {
            return queryBuilder;
        }
        if (this.queryAttributeName != null) {
            keyAttributeName = this.queryAttributeName;
        } else {
            IUsernameAttributeProvider usernameAttributeProvider = this.getUsernameAttributeProvider();
            keyAttributeName = usernameAttributeProvider.getUsernameAttribute();
        }
        if (keyAttributeName.equals(dataAttribute)) {
            return String.valueOf(queryValues.get(0));
        }
        return null;
    }

    @Override
    protected List<IPersonAttributes> getPeopleForQuery(String seedValue, String queryUserName) {
        if (seedValue != null && seedValue.contains("*")) {
            Pattern seedPattern = PatternHelper.compilePattern(seedValue);
            LinkedList<IPersonAttributes> results = new LinkedList<IPersonAttributes>();
            for (Map.Entry<String, Map<String, List<Object>>> attributesEntry : this.backingMap.entrySet()) {
                Map<String, List<Object>> attributes;
                String attributesKey = attributesEntry.getKey();
                Matcher keyMatcher = seedPattern.matcher(attributesKey);
                if (!keyMatcher.matches() || (attributes = attributesEntry.getValue()) == null) continue;
                IPersonAttributes person = this.createPerson(null, queryUserName, attributes);
                results.add(person);
            }
            if (results.size() == 0) {
                return null;
            }
            return results;
        }
        Map<String, List<Object>> attributes = this.backingMap.get(seedValue);
        if (attributes == null) {
            return null;
        }
        IPersonAttributes person = this.createPerson(seedValue, queryUserName, attributes);
        ArrayList<IPersonAttributes> list = new ArrayList<IPersonAttributes>();
        list.add(person);
        return list;
    }

    private IPersonAttributes createPerson(String seedValue, String queryUserName, Map<String, List<Object>> attributes) {
        String userNameAttribute = this.getConfiguredUserNameAttribute();
        BasePersonImpl person = this.isUserNameAttributeConfigured() && attributes.containsKey(userNameAttribute) ? new AttributeNamedPersonImpl(userNameAttribute, attributes) : (queryUserName != null ? new NamedPersonImpl(queryUserName, attributes) : (seedValue != null && userNameAttribute.equals(this.queryAttributeName) ? new NamedPersonImpl(seedValue, attributes) : new AttributeNamedPersonImpl(userNameAttribute, attributes)));
        return person;
    }

    private void initializePossibleAttributeNames() {
        LinkedHashSet<String> possibleAttribNames = new LinkedHashSet<String>();
        for (Map<String, List<Object>> attributeMapForSomeUser : this.backingMap.values()) {
            Set<String> keySet = attributeMapForSomeUser.keySet();
            possibleAttribNames.addAll(keySet);
        }
        this.possibleUserAttributeNames = CollectionsUtil.safelyWrapAsUnmodifiableSet(possibleAttribNames);
    }
}

