/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  org.apache.commons.lang3.Validate
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.springframework.beans.factory.annotation.Required
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.merger.IAttributeMerger;
import org.apereo.services.persondir.support.merger.MultivaluedAttributeMerger;
import org.apereo.services.persondir.util.CollectionsUtil;
import org.springframework.beans.factory.annotation.Required;

public abstract class AbstractAggregatingDefaultQueryPersonAttributeDao
extends AbstractDefaultAttributePersonAttributeDao {
    protected List<IPersonAttributeDao> personAttributeDaos;
    protected IAttributeMerger attrMerger = new MultivaluedAttributeMerger();
    protected boolean recoverExceptions = true;
    protected boolean stopOnSuccess = false;
    protected boolean requireAll;

    @Override
    public String[] getId() {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(super.getClass().getSimpleName());
        this.personAttributeDaos.forEach(dao -> ids.addAll(Arrays.asList(dao.getId())));
        return ids.toArray(new String[0]);
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        Validate.notNull(query, (String)"query may not be null.", (Object[])new Object[0]);
        Set<IPersonAttributes> resultPeople = null;
        boolean isFirstQuery = true;
        if (this.personAttributeDaos == null) {
            throw new IllegalStateException("personAttributeDaos must be set");
        }
        for (IPersonAttributeDao currentlyConsidering : this.personAttributeDaos) {
            if (filter != null && !filter.choosePersonAttributeDao(currentlyConsidering)) continue;
            boolean handledException = false;
            Set<IPersonAttributes> currentPeople = null;
            try {
                currentPeople = this.getAttributesFromDao(query, isFirstQuery, currentlyConsidering, resultPeople, filter);
                isFirstQuery = false;
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Retrieved attributes='" + currentPeople + "' for query='" + query + "', isFirstQuery=" + isFirstQuery + ", currentlyConsidering='" + currentlyConsidering + "', resultAttributes='" + resultPeople + "'");
                }
            }
            catch (RuntimeException rte) {
                handledException |= this.handleRuntimeException(currentlyConsidering, rte);
            }
            if (currentPeople != null) {
                resultPeople = resultPeople == null ? new LinkedHashSet<IPersonAttributes>(currentPeople) : this.attrMerger.mergeResults(resultPeople, currentPeople);
            } else if (this.requireAll) {
                this.logger.debug("Attribute repository dao {} did not resolve a person and configuration requires all sources to produce valid results. Short-circuiting the execution and returning null instead", (Object)currentlyConsidering);
                return null;
            }
            if (!this.stopOnSuccess || handledException) continue;
            if (!this.logger.isDebugEnabled()) break;
            this.logger.debug("Successfully retrieved attributes from a child DAO and stopOnSuccess is true, stopping iteration of child DAOs");
            break;
        }
        if (resultPeople == null) {
            return null;
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Aggregated search results '" + resultPeople + "' for query='" + query + "'");
        }
        return CollectionsUtil.safelyWrapAsUnmodifiableSet(resultPeople);
    }

    private boolean handleRuntimeException(IPersonAttributeDao currentlyConsidering, RuntimeException rte) {
        if (this.recoverExceptions) {
            this.logger.warn("Recovering From Exception thrown by '" + currentlyConsidering + "'", (Throwable)rte);
            return true;
        }
        this.logger.error("Failing From Exception thrown by '" + currentlyConsidering + "'", (Throwable)rte);
        throw rte;
    }

    protected abstract Set<IPersonAttributes> getAttributesFromDao(Map<String, List<Object>> var1, boolean var2, IPersonAttributeDao var3, Set<IPersonAttributes> var4, IPersonAttributeDaoFilter var5);

    @JsonIgnore
    public final Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        Set<String> attrNames = null;
        for (IPersonAttributeDao currentDao : this.personAttributeDaos) {
            if (filter != null && !filter.choosePersonAttributeDao(currentDao)) continue;
            boolean handledException = false;
            Set currentDaoAttrNames = null;
            try {
                currentDaoAttrNames = currentDao.getPossibleUserAttributeNames(filter);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Retrieved possible attribute names '" + currentDaoAttrNames + "' from '" + currentDao + "'");
                }
            }
            catch (RuntimeException rte) {
                handledException |= this.handleRuntimeException(currentDao, rte);
            }
            if (currentDaoAttrNames != null) {
                if (attrNames == null) {
                    attrNames = new LinkedHashSet<String>();
                }
                attrNames = this.attrMerger.mergePossibleUserAttributeNames(attrNames, currentDaoAttrNames);
            }
            if (!this.stopOnSuccess || handledException) continue;
            if (!this.logger.isDebugEnabled()) break;
            this.logger.debug("Successfully retrieved possible user attributes from a child DAO and stopOnSuccess is true, stopping iteration of child DAOs");
            break;
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Aggregated possible attribute names '" + attrNames + "'");
        }
        if (attrNames == null) {
            return null;
        }
        return CollectionsUtil.safelyWrapAsUnmodifiableSet(attrNames);
    }

    @JsonIgnore
    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        Set<String> queryAttrs = null;
        for (IPersonAttributeDao currentDao : this.personAttributeDaos) {
            if (filter != null && !filter.choosePersonAttributeDao(currentDao)) continue;
            boolean handledException = false;
            Set currentDaoQueryAttrs = null;
            try {
                currentDaoQueryAttrs = currentDao.getAvailableQueryAttributes(filter);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Retrieved possible query attributes '" + currentDaoQueryAttrs + "' from '" + currentDao + "'");
                }
            }
            catch (RuntimeException rte) {
                handledException |= this.handleRuntimeException(currentDao, rte);
            }
            if (currentDaoQueryAttrs != null) {
                if (queryAttrs == null) {
                    queryAttrs = new LinkedHashSet<String>();
                }
                queryAttrs = this.attrMerger.mergeAvailableQueryAttributes(queryAttrs, currentDaoQueryAttrs);
            }
            if (!this.stopOnSuccess || handledException) continue;
            if (!this.logger.isDebugEnabled()) break;
            this.logger.debug("Successfully retrieved available query attributes from a child DAO and stopOnSuccess is true, stopping iteration of child DAOs");
            break;
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Aggregated possible query attributes '" + queryAttrs + "'");
        }
        if (queryAttrs == null) {
            return null;
        }
        return CollectionsUtil.safelyWrapAsUnmodifiableSet(queryAttrs);
    }

    public final IAttributeMerger getMerger() {
        return this.attrMerger;
    }

    public final void setMerger(IAttributeMerger merger) {
        Validate.notNull((Object)merger, (String)"The IAttributeMerger cannot be null", (Object[])new Object[0]);
        this.attrMerger = merger;
    }

    public final List<IPersonAttributeDao> getPersonAttributeDaos() {
        return this.personAttributeDaos;
    }

    @Required
    public final void setPersonAttributeDaos(List<IPersonAttributeDao> daos) {
        Validate.notNull(daos, (String)"The IPersonAttributeDao List cannot be null", (Object[])new Object[0]);
        this.personAttributeDaos = CollectionsUtil.safelyWrapAsUnmodifiableList(daos);
    }

    public final boolean isRecoverExceptions() {
        return this.recoverExceptions;
    }

    public final void setRecoverExceptions(boolean recover) {
        this.recoverExceptions = recover;
    }

    public boolean isStopOnSuccess() {
        return this.stopOnSuccess;
    }

    public void setStopOnSuccess(boolean stopOnSuccess) {
        this.stopOnSuccess = stopOnSuccess;
    }

    public boolean isRequireAll() {
        return this.requireAll;
    }

    public void setRequireAll(boolean requireAll) {
        this.requireAll = requireAll;
    }
}

