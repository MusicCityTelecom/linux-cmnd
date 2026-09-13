/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributeScriptDao
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import org.apereo.services.persondir.IPersonAttributeScriptDao;

public abstract class BaseGroovyScriptDaoImpl
implements IPersonAttributeScriptDao {
    public Map<String, Object> getAttributesForUser(String username) {
        throw new UnsupportedOperationException();
    }

    public Map<String, List<Object>> getPersonAttributesFromMultivaluedAttributes(Map<String, List<Object>> attributes) {
        throw new UnsupportedOperationException();
    }
}

