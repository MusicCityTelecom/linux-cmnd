/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AbstractDefaultAttributePersonAttributeDao;
import org.apereo.services.persondir.support.AttributeNamedPersonImpl;

public class EchoPersonAttributeDaoImpl
extends AbstractDefaultAttributePersonAttributeDao {
    public Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        if (query == null) {
            throw new IllegalArgumentException("seed may not be null");
        }
        return Collections.singleton(new AttributeNamedPersonImpl(this.getUsernameAttributeProvider().getUsernameAttribute(), query));
    }

    public Set<String> getPossibleUserAttributeNames(IPersonAttributeDaoFilter filter) {
        return null;
    }

    public Set<String> getAvailableQueryAttributes(IPersonAttributeDaoFilter filter) {
        return null;
    }
}

