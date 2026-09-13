/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractAggregatingDefaultQueryPersonAttributeDao;
import org.apereo.services.persondir.support.merger.ReplacingAttributeAdder;

public class CascadingPersonAttributeDao
extends AbstractAggregatingDefaultQueryPersonAttributeDao {
    private boolean stopIfFirstDaoReturnsNull = false;
    private boolean addOriginalAttributesToQuery = false;

    public void setStopIfFirstDaoReturnsNull(boolean stopIfFirstDaoReturnsNull) {
        this.stopIfFirstDaoReturnsNull = stopIfFirstDaoReturnsNull;
    }

    public CascadingPersonAttributeDao() {
        this.attrMerger = new ReplacingAttributeAdder();
    }

    @Override
    protected Set<IPersonAttributes> getAttributesFromDao(Map<String, List<Object>> seed, boolean isFirstQuery, IPersonAttributeDao currentlyConsidering, Set<IPersonAttributes> resultPeople, IPersonAttributeDaoFilter filter) {
        if (isFirstQuery || !this.stopIfFirstDaoReturnsNull && (resultPeople == null || resultPeople.size() == 0)) {
            return currentlyConsidering.getPeopleWithMultivaluedAttributes(seed, filter);
        }
        if (this.stopIfFirstDaoReturnsNull && !isFirstQuery && (resultPeople == null || resultPeople.size() == 0)) {
            return null;
        }
        Set<IPersonAttributes> mergedPeopleResults = null;
        for (IPersonAttributes person : resultPeople) {
            Set newResults;
            LinkedHashMap<String, List<Object>> queryAttributes = new LinkedHashMap<String, List<Object>>();
            String userName = person.getName();
            if (userName != null) {
                Map<String, List<Object>> userNameMap = this.toSeedMap(userName);
                queryAttributes.putAll(userNameMap);
            }
            Map personAttributes = person.getAttributes();
            queryAttributes.putAll(personAttributes);
            if (this.addOriginalAttributesToQuery) {
                queryAttributes.putAll(seed);
            }
            if ((newResults = currentlyConsidering.getPeopleWithMultivaluedAttributes(queryAttributes, filter)) == null) continue;
            if (mergedPeopleResults == null) {
                mergedPeopleResults = new LinkedHashSet<IPersonAttributes>(newResults);
                continue;
            }
            mergedPeopleResults = this.attrMerger.mergeResults(mergedPeopleResults, newResults);
        }
        return mergedPeopleResults;
    }

    public void setAddOriginalAttributesToQuery(boolean addOriginalAttributesToQuery) {
        this.addOriginalAttributesToQuery = addOriginalAttributesToQuery;
    }
}

