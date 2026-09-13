/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.BasePersonAttributeDao;
import org.apereo.services.persondir.support.MultivaluedPersonAttributeUtils;

public abstract class AbstractFlatteningPersonAttributeDao
extends BasePersonAttributeDao {
    public final Set<IPersonAttributes> getPeople(Map<String, Object> query, IPersonAttributeDaoFilter filter) {
        Map<String, List<Object>> multivaluedSeed = MultivaluedPersonAttributeUtils.toMultivaluedMap(query);
        return this.getPeopleWithMultivaluedAttributes(multivaluedSeed, filter);
    }
}

