/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.builder.ToStringBuilder
 *  org.apereo.services.persondir.IPersonAttributeDao
 *  org.apereo.services.persondir.IPersonAttributes
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 *  org.springframework.dao.IncorrectResultSizeDataAccessException
 *  org.springframework.dao.support.DataAccessUtils
 */
package org.apereo.services.persondir.support;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apereo.services.persondir.IPersonAttributeDao;
import org.apereo.services.persondir.IPersonAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.dao.support.DataAccessUtils;

public abstract class BasePersonAttributeDao
implements IPersonAttributeDao {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private int order;
    private String[] id = new String[]{this.getClass().getSimpleName()};
    private boolean enabled = true;
    private Map<String, Object> tags = new LinkedHashMap<String, Object>();

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int compareTo(IPersonAttributeDao o) {
        if (this.order == o.getOrder()) {
            return 0;
        }
        if (this.order > o.getOrder()) {
            return 1;
        }
        return -1;
    }

    public String[] getId() {
        return this.id;
    }

    public void setId(String ... id) {
        this.id = id;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString((Object)this);
    }

    public Map<String, Object> getTags() {
        return this.tags;
    }

    public void setTags(Map<String, Object> tags) {
        this.tags = tags;
    }

    public BasePersonAttributeDao putTag(String name, Object value) {
        this.tags.put(name, value);
        return this;
    }

    protected IPersonAttributes getSinglePerson(Set<IPersonAttributes> people) {
        IPersonAttributes person;
        try {
            person = (IPersonAttributes)DataAccessUtils.singleResult(people);
        }
        catch (IncorrectResultSizeDataAccessException e) {
            this.logger.warn("Unexpected multiple people returned from person attribute DAO: {} : {} ", (Object)((Object)((Object)e)).getClass().getName(), (Object)e.getMessage());
            people.forEach(p -> this.logger.debug("Person: {}", p));
            throw e;
        }
        return person;
    }
}

