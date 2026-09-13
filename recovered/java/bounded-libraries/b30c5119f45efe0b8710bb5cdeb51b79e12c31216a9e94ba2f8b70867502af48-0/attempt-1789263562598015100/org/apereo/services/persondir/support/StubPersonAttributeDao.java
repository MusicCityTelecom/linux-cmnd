/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.google.common.collect.ImmutableSet
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractFlatteningPersonAttributeDao;
import org.apereo.services.persondir.support.AttributeNamedPersonImpl;

public class StubPersonAttributeDao
extends AbstractFlatteningPersonAttributeDao {
    private IPersonAttributes backingPerson = null;

    public StubPersonAttributeDao() {
    }

    public StubPersonAttributeDao(Map<String, List<Object>> backingMap) {
        this.setBackingMap(backingMap);
    }

    @JsonIgnore
    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        if (this.backingPerson == null) {
            return new HashSet<String>();
        }
        return ImmutableSet.copyOf(this.backingPerson.getAttributes().keySet());
    }

    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return null;
    }

    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        if (query == null) {
            throw new IllegalArgumentException("Illegal to invoke getPeople(Map) with a null argument.");
        }
        if (this.backingPerson == null) {
            return null;
        }
        return Collections.singleton(this.backingPerson);
    }

    public IPersonAttributes getPerson(String uid, IPersonAttributeDaoFilter filter) {
        if (!this.isEnabled()) {
            return null;
        }
        if (uid == null) {
            throw new IllegalArgumentException("Illegal to invoke getPerson(String) with a null argument.");
        }
        return this.backingPerson;
    }

    public Map<String, List<Object>> getBackingMap() {
        return new HashMap<String, List<Object>>(this.backingPerson.getAttributes());
    }

    public void setBackingMap(Map<String, List<Object>> backingMap) {
        this.backingPerson = new AttributeNamedPersonImpl(backingMap);
    }
}

