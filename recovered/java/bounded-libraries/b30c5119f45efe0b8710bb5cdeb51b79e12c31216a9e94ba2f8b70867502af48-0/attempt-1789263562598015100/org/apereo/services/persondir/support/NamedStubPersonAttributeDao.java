/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeDaoFilter
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apereo.services.persondir.IPersonAttributeDaoFilter;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.StubPersonAttributeDao;

public class NamedStubPersonAttributeDao
extends StubPersonAttributeDao {
    public NamedStubPersonAttributeDao() {
    }

    public NamedStubPersonAttributeDao(Map backingMap) {
        super(backingMap);
    }

    @Override
    public final Set<IPersonAttributes> getPeopleWithMultivaluedAttributes(Map<String, List<Object>> query, IPersonAttributeDaoFilter filter) {
        List<Object> list = query.get("username");
        HashMap<String, List<Object>> m = new HashMap<String, List<Object>>(this.getBackingMap());
        m.put("username", list);
        this.setBackingMap(m);
        return super.getPeopleWithMultivaluedAttributes(query, filter);
    }
}

