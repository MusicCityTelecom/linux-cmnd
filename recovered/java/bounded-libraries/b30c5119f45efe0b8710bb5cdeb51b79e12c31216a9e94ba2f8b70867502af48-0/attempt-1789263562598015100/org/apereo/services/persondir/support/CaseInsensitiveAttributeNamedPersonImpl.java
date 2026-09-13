/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apereo.services.persondir.IPersonAttributes
 */
package org.apereo.services.persondir.support;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apereo.services.persondir.IPersonAttributes;
import org.apereo.services.persondir.support.AttributeNamedPersonImpl;

public class CaseInsensitiveAttributeNamedPersonImpl
extends AttributeNamedPersonImpl {
    private static final long serialVersionUID = 1L;

    public CaseInsensitiveAttributeNamedPersonImpl(Map<String, List<Object>> attributes) {
        super(attributes);
    }

    public CaseInsensitiveAttributeNamedPersonImpl(String userNameAttribute, Map<String, List<Object>> attributes) {
        super(userNameAttribute, attributes);
    }

    public CaseInsensitiveAttributeNamedPersonImpl(IPersonAttributes personAttributes) {
        super(personAttributes);
    }

    @Override
    protected Map<String, List<Object>> createImmutableAttributeMap(int size) {
        return new TreeMap<String, List<Object>>(String.CASE_INSENSITIVE_ORDER);
    }
}

